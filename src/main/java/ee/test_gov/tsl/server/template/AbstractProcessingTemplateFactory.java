package ee.test_gov.tsl.server.template;

import ee.test_gov.tsl.server.configuration.provider.TemplateConfigurationProvider;
import eu.europa.esig.dss.model.DSSDocument;
import eu.europa.esig.dss.model.InMemoryDocument;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.function.Function;

@Slf4j
abstract class AbstractProcessingTemplateFactory implements Function<TemplateConfigurationProvider, DSSDocument> {

    protected abstract String processTemplateString(String template, TemplateConfigurationProvider configurationProvider);

    @Override
    public DSSDocument apply(TemplateConfigurationProvider configurationProvider) {
        log.info("Loading trust list template from '{}'", configurationProvider.getSource());
        String template;

        try (InputStream inputStream = configurationProvider.getSource().getInputStream()) {
            template = IOUtils.toString(inputStream, configurationProvider.getCharset());
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load template: " + configurationProvider.getSource(), e);
        }

        template = processTemplateString(template, configurationProvider);

        return new InMemoryDocument(template.getBytes(configurationProvider.getCharset()));
    }

}
