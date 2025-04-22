package ee.test_gov.tsl.server.configuration.provider;

import ee.test_gov.tsl.server.util.Constants;
import eu.europa.esig.dss.enumerations.DigestAlgorithm;
import eu.europa.esig.dss.model.DSSDocument;
import eu.europa.esig.dss.model.x509.CertificateToken;
import eu.europa.esig.dss.xades.tsl.AbstractTrustedListSignatureParametersBuilder;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.core.io.Resource;
import org.springframework.validation.annotation.Validated;

import java.time.Instant;
import java.util.function.BiFunction;

@Getter
@Validated
@Setter(AccessLevel.PACKAGE)
public class SignerConfigurationProvider {

    @NotNull
    private Resource keystore;
    @NotNull
    private char[] keystorePassword;
    @NotNull
    private String keystoreType = Constants.KEYSTORE_TYPE_PKCS12;

    private String keyAlias;

    @NotNull
    private Class<BiFunction<CertificateToken, DSSDocument, AbstractTrustedListSignatureParametersBuilder>> signatureParametersBuilder;

    private DigestAlgorithm signatureDigestAlgorithm;

    private DigestAlgorithm referenceDigestAlgorithm;

    private Instant claimedSigningTime;

}
