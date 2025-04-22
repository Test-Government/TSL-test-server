package ee.test_gov.tsl.server.template;

import ee.test_gov.tsl.server.configuration.provider.TemplateConfigurationProvider;
import eu.europa.esig.dss.model.DSSDocument;
import eu.europa.esig.dss.model.InMemoryDocument;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.function.Function;

@Slf4j
public class SimpleTemplateFactory implements Function<TemplateConfigurationProvider, DSSDocument> {

    @Override
    public DSSDocument apply(TemplateConfigurationProvider configurationProvider) {
        log.info("Loading trust list template from '{}'", configurationProvider.getSource());

        try (InputStream inputStream = configurationProvider.getSource().getInputStream()) {
            return new InMemoryDocument(inputStream, configurationProvider.getSource().getFilename());
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load template: " + configurationProvider.getSource());
        }
    }

}
