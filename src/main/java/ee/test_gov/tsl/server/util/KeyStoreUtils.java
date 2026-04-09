package ee.test_gov.tsl.server.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
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
import java.util.Base64;
import java.util.Enumeration;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class KeyStoreUtils {

    public static KeyStore loadKeyStore(Input input) {
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

    public static Stream<Certificate> getAllListedCertificates(KeyStore keyStore) {
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

    public static Stream<Certificate> getCertificatesByAlias(KeyStore keyStore, String alias) {
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

    public static String encodeCertificateToBase64(Certificate certificate) {
        try {
            return Base64.getEncoder().encodeToString(certificate.getEncoded());
        } catch (CertificateEncodingException e) {
            throw new IllegalStateException("Failed to encode certificate to base64", e);
        }
    }

    public interface Input {
        @NonNull String resource();
        @NonNull String type();
        char[] pass();
    }

}
