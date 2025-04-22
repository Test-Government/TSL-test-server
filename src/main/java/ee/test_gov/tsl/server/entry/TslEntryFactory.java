package ee.test_gov.tsl.server.entry;

import com.google.protobuf.ByteString;
import ee.test_gov.tsl.server.configuration.provider.SignerConfigurationProvider;
import ee.test_gov.tsl.server.configuration.provider.TemplateConfigurationProvider;
import ee.test_gov.tsl.server.configuration.provider.TrustListConfigurationProvider;
import eu.europa.esig.dss.model.DSSDocument;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.io.InputStream;
import java.util.function.BiFunction;
import java.util.function.Function;

@Slf4j
@RequiredArgsConstructor
public class TslEntryFactory implements Function<TrustListConfigurationProvider, TslEntry> {

    private final @NonNull Function<TemplateConfigurationProvider, DSSDocument> templateFactory;
    private final @NonNull BiFunction<DSSDocument, SignerConfigurationProvider, DSSDocument> tlSigner;
    private final @NonNull Function<TrustListConfigurationProvider, MediaType> mediaTypeResolver;

    @Override
    public TslEntry apply(TrustListConfigurationProvider configurationProvider) {
        log.info("Creating new trust list '{}' ...", configurationProvider.getServeAs());
        DSSDocument trustListDocument = templateFactory.apply(configurationProvider.getTemplate());
        trustListDocument.setName(configurationProvider.getServeAs());

        if (configurationProvider.getSigner() != null) {
            trustListDocument = tlSigner.apply(trustListDocument, configurationProvider.getSigner());
        }

        log.info("Trust list '{}' done", configurationProvider.getServeAs());

        return TslEntry.builder()
                .content(toByteString(trustListDocument))
                .fileName(configurationProvider.getServeAs())
                .mediaType(mediaTypeResolver.apply(configurationProvider))
                .build();
    }

    private static ByteString toByteString(DSSDocument document) {
        try (InputStream inputStream = document.openStream()) {
            return ByteString.readFrom(inputStream);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to serialize document", e);
        }
    }

}
