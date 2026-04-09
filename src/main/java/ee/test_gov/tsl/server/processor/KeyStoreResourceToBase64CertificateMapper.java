package ee.test_gov.tsl.server.processor;

import ee.test_gov.tsl.server.util.Constants;
import ee.test_gov.tsl.server.util.KeyStoreUtils;
import lombok.Builder;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;

import java.security.KeyStore;
import java.security.cert.X509Certificate;
import java.util.Optional;
import java.util.function.UnaryOperator;

public class KeyStoreResourceToBase64CertificateMapper implements UnaryOperator<String> {

    private static final String DEFAULT_KEYSTORE_TYPE = Constants.KEYSTORE_TYPE_PKCS12;
    private static final String ALIAS_PREFIX = "alias:";
    private static final String PASS_PREFIX = "pass:";
    private static final String TYPE_PREFIX = "type:";

    @Override
    public String apply(String inputString) {
        final Input parsedInput = parseInput(inputString);
        final KeyStore keyStore = KeyStoreUtils.loadKeyStore(parsedInput);
        return Optional
                .ofNullable(parsedInput.alias())
                .map(alias -> KeyStoreUtils.getCertificatesByAlias(keyStore, alias))
                .orElseGet(() -> KeyStoreUtils.getAllListedCertificates(keyStore))
                .filter(X509Certificate.class::isInstance)
                .map(KeyStoreUtils::encodeCertificateToBase64)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(
                        "Failed to extract a certificate from input: " + inputString
                ));
    }

    private static Input parseInput(String inputString) {
        final Input.InputBuilder builder = Input.builder().type(DEFAULT_KEYSTORE_TYPE);
        for (String part : StringUtils.split(inputString, '|')) {
            if (Strings.CS.startsWith(part, ALIAS_PREFIX)) {
                builder.alias(Strings.CS.removeStart(part, ALIAS_PREFIX));
            } else if (Strings.CS.startsWith(part, PASS_PREFIX)) {
                builder.pass(Strings.CS.removeStart(part, PASS_PREFIX).toCharArray());
            } else if (Strings.CS.startsWith(part, TYPE_PREFIX)) {
                builder.type(Strings.CS.removeStart(part, TYPE_PREFIX));
            } else {
                builder.resource(part);
            }
        }
        return builder.build();
    }

    @Builder
    private record Input(
            @NonNull String resource,
            @NonNull String type,
            String alias,
            char[] pass
    ) implements KeyStoreUtils.Input {
    }

}
