package ee.test_gov.tsl.server.processor;

import ee.test_gov.tsl.server.util.Constants;
import lombok.Builder;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.Enumeration;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class KeyStoreResourceToBase64CertificateMapper implements UnaryOperator<String> {

    private static final String DEFAULT_KEYSTORE_TYPE = Constants.KEYSTORE_TYPE_PKCS12;
    private static final String ALIAS_PREFIX = "alias:";
    private static final String PASS_PREFIX = "pass:";
    private static final String TYPE_PREFIX = "type:";

    @Override
    public String apply(String inputString) {
        final Input parsedInput = parseInput(inputString);
        final KeyStore keyStore = loadKeyStore(parsedInput);
        return Optional
                .ofNullable(parsedInput.alias())
                .map(alias -> getCertificatesByAlias(keyStore, alias))
                .orElseGet(() -> getAllListedCertificates(keyStore))
                .filter(X509Certificate.class::isInstance)
                .map(KeyStoreResourceToBase64CertificateMapper::encodeCertificateToBase64)
                .findFirst().orElseThrow(() -> new IllegalStateException(
                        "Failed to extract a certificate from input: " + inputString
                ));
    }

    private static Input parseInput(String inputString) {
        final Input.InputBuilder builder = Input.builder().type(DEFAULT_KEYSTORE_TYPE);
        for (String part : StringUtils.split(inputString, '|')) {
            if (StringUtils.startsWith(part, ALIAS_PREFIX)) {
                builder.alias(StringUtils.removeStart(part, ALIAS_PREFIX));
            } else if (StringUtils.startsWith(part, PASS_PREFIX)) {
                builder.pass(StringUtils.removeStart(part, PASS_PREFIX).toCharArray());
            } else if (StringUtils.startsWith(part, TYPE_PREFIX)) {
                builder.type(StringUtils.removeStart(part, TYPE_PREFIX));
            } else {
                builder.resource(part);
            }
        }
        return builder.build();
    }

    private static KeyStore loadKeyStore(Input input) {
        final Resource resource = new DefaultResourceLoader().getResource(input.resource());
        try (InputStream inputStream = resource.getInputStream()) {
            KeyStore keyStore = KeyStore.getInstance(input.type());
            keyStore.load(inputStream, input.pass());
            return keyStore;
        } catch (IOException | NoSuchAlgorithmException | CertificateException e) {
            throw new IllegalStateException("Failed to load keystore: " + input.resource(), e);
        } catch (KeyStoreException e) {
            throw new IllegalStateException("Failed to instantiate keystore", e);
        }
    }

    private static Stream<Certificate> getAllListedCertificates(KeyStore keyStore) {
        Enumeration<String> aliases;
        try {
            aliases = keyStore.aliases();
        } catch (KeyStoreException e) {
            throw new IllegalStateException("Failed to list keystore aliases", e);
        }
        return StreamSupport
                .stream(Spliterators.spliteratorUnknownSize(aliases.asIterator(), Spliterator.ORDERED), false)
                .flatMap(a -> getCertificatesByAlias(keyStore, a));
    }

    private static Stream<Certificate> getCertificatesByAlias(KeyStore keyStore, String alias) {
        try {
            if (keyStore.isCertificateEntry(alias)) {
                return Optional.ofNullable(keyStore.getCertificate(alias)).stream();
            } else if (keyStore.isKeyEntry(alias)) {
                return Optional.ofNullable(keyStore.getCertificateChain(alias)).stream().flatMap(Stream::of);
            } else {
                return Stream.empty();
            }
        } catch (KeyStoreException e) {
            throw new IllegalStateException("Failed to extract certificates for alias: " + alias, e);
        }
    }

    private static String encodeCertificateToBase64(Certificate certificate) {
        try {
            return Base64.getEncoder().encodeToString(certificate.getEncoded());
        } catch (CertificateEncodingException e) {
            throw new IllegalStateException("Failed to encode certificate to base64", e);
        }
    }

    @Builder
    private record Input(@NonNull String resource, @NonNull String type, String alias, char[] pass) {
    }

}
