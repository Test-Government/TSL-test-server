package ee.test_gov.tsl.server.signature;

import eu.europa.esig.dss.model.DSSDocument;
import eu.europa.esig.dss.model.x509.CertificateToken;
import eu.europa.esig.dss.xades.tsl.AbstractTrustedListSignatureParametersBuilder;
import eu.europa.esig.dss.xades.tsl.TrustedListV5SignatureParametersBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.function.BiFunction;

@Slf4j
public class TrustedListV5SignatureParametersBuilderFactory implements BiFunction<CertificateToken, DSSDocument, AbstractTrustedListSignatureParametersBuilder> {

    @Override
    public TrustedListV5SignatureParametersBuilder apply(CertificateToken signingCertificate, DSSDocument tlXmlDocument) {
        log.info(
                "Creating {} for signing '{}' by '{}'",
                TrustedListV5SignatureParametersBuilder.class.getSimpleName(),
                tlXmlDocument.getName(),
                signingCertificate.getSubject().getRFC2253()
        );
        return new TrustedListV5SignatureParametersBuilder(signingCertificate, tlXmlDocument);
    }

}
