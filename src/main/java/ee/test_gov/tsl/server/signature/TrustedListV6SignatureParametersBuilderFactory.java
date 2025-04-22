package ee.test_gov.tsl.server.signature;

import eu.europa.esig.dss.model.DSSDocument;
import eu.europa.esig.dss.model.x509.CertificateToken;
import eu.europa.esig.dss.xades.tsl.AbstractTrustedListSignatureParametersBuilder;
import eu.europa.esig.dss.xades.tsl.TrustedListV6SignatureParametersBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.function.BiFunction;

@Slf4j
public class TrustedListV6SignatureParametersBuilderFactory implements BiFunction<CertificateToken, DSSDocument, AbstractTrustedListSignatureParametersBuilder> {

    @Override
    public TrustedListV6SignatureParametersBuilder apply(CertificateToken signingCertificate, DSSDocument tlXmlDocument) {
        log.info(
                "Creating {} for signing '{}' by '{}'",
                TrustedListV6SignatureParametersBuilder.class.getSimpleName(),
                tlXmlDocument.getName(),
                signingCertificate.getSubject().getRFC2253()
        );
        return new TrustedListV6SignatureParametersBuilder(signingCertificate, tlXmlDocument);
    }

}
