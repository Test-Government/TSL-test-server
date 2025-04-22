package ee.test_gov.tsl.server.signature;

import ee.test_gov.tsl.server.configuration.provider.SignerConfigurationProvider;
import ee.test_gov.tsl.server.util.Constants;
import ee.test_gov.tsl.server.util.ReflectionUtils;
import eu.europa.esig.dss.alert.LogOnStatusAlert;
import eu.europa.esig.dss.model.DSSDocument;
import eu.europa.esig.dss.model.SignatureValue;
import eu.europa.esig.dss.model.ToBeSigned;
import eu.europa.esig.dss.spi.validation.CommonCertificateVerifier;
import eu.europa.esig.dss.token.DSSPrivateKeyEntry;
import eu.europa.esig.dss.token.JKSSignatureToken;
import eu.europa.esig.dss.token.KSPrivateKeyEntry;
import eu.europa.esig.dss.token.Pkcs12SignatureToken;
import eu.europa.esig.dss.token.SignatureTokenConnection;
import eu.europa.esig.dss.xades.XAdESSignatureParameters;
import eu.europa.esig.dss.xades.signature.XAdESService;
import eu.europa.esig.dss.xades.tsl.AbstractTrustedListSignatureParametersBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.event.Level;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.util.Date;
import java.util.Optional;
import java.util.function.BiFunction;

@Slf4j
public class DefaultTrustedListSigner implements BiFunction<DSSDocument, SignerConfigurationProvider, DSSDocument> {

    @Override
    public DSSDocument apply(DSSDocument documentToSign, SignerConfigurationProvider configurationProvider) {
        try (SignatureTokenConnection signatureToken = openSignatureToken(configurationProvider)) {
            DSSPrivateKeyEntry keyEntry = getKeyEntry(signatureToken, configurationProvider.getKeyAlias());
            AbstractTrustedListSignatureParametersBuilder builder = ReflectionUtils
                    .newInstance(configurationProvider.getSignatureParametersBuilder())
                    .apply(keyEntry.getCertificate(), documentToSign);

            Optional.ofNullable(configurationProvider.getClaimedSigningTime()).map(Date::from).ifPresent(builder.bLevel()::setSigningDate);
            Optional.ofNullable(configurationProvider.getReferenceDigestAlgorithm()).ifPresent(builder::setReferenceDigestAlgorithm);
            Optional.ofNullable(configurationProvider.getSignatureDigestAlgorithm()).ifPresent(builder::setDigestAlgorithm);
            XAdESSignatureParameters signatureParameters = builder.build();

            CommonCertificateVerifier certificateVerifier = new CommonCertificateVerifier();
            certificateVerifier.setAlertOnExpiredCertificate(new LogOnStatusAlert(Level.WARN));
            certificateVerifier.setAlertOnNotYetValidCertificate(new LogOnStatusAlert(Level.WARN));
            XAdESService signingService = new XAdESService(certificateVerifier);

            log.info(
                    "Signing trust list '{}' by '{}' using {} signature algorithm",
                    documentToSign.getName(),
                    keyEntry.getCertificate().getSubject().getRFC2253(),
                    signatureParameters.getSignatureAlgorithm().getName()
            );

            ToBeSigned toBeSigned = signingService.getDataToSign(documentToSign, signatureParameters);
            SignatureValue signatureValue = signatureToken.sign(toBeSigned, signatureParameters.getDigestAlgorithm(), keyEntry);
            return signingService.signDocument(documentToSign, signatureParameters, signatureValue);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to sign document: " + documentToSign.getName(), e);
        }
    }

    private static SignatureTokenConnection openSignatureToken(SignerConfigurationProvider configurationProvider) {
        try (InputStream inputStream = configurationProvider.getKeystore().getInputStream()) {
            KeyStore.PasswordProtection password = new KeyStore.PasswordProtection(configurationProvider.getKeystorePassword());
            return switch (configurationProvider.getKeystoreType()) {
                case Constants.KEYSTORE_TYPE_JKS -> new JKSSignatureToken(inputStream, password);
                case Constants.KEYSTORE_TYPE_PKCS12 -> new Pkcs12SignatureToken(inputStream, password);
                default -> throw new IllegalStateException("Unsupported keystore type: " + configurationProvider.getKeystoreType());
            };
        } catch (IOException e) {
            throw new IllegalStateException("Failed to open signature token connection for: " + configurationProvider.getKeystore(), e);
        }
    }

    private static DSSPrivateKeyEntry getKeyEntry(SignatureTokenConnection signatureToken, String keyAlias) {
        for (DSSPrivateKeyEntry keyEntry : signatureToken.getKeys()) {
            if (keyAlias == null || (keyEntry instanceof KSPrivateKeyEntry ksEntry && StringUtils.equals(ksEntry.getAlias(), keyAlias))) {
                return keyEntry;
            }
        }
        throw new IllegalStateException("No suitable key entry found");
    }

}
