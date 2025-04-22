package ee.test_gov.tsl.server.template;

import ee.test_gov.tsl.server.configuration.provider.TemplateConfigurationProvider;
import ee.test_gov.tsl.server.util.ReflectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.function.UnaryOperator;

@Slf4j
public class PlaceholderValueProcessingTemplateFactory extends AbstractProcessingTemplateFactory {

    @Override
    protected String processTemplateString(String template, TemplateConfigurationProvider configurationProvider) {
        final Map<String, Class<UnaryOperator<String>>> preprocessors = configurationProvider.getPlaceholderProcessors();

        for (final Map.Entry<String, String> placeholder : configurationProvider.getPlaceholderMappings().entrySet()) {
            final String key = placeholder.getKey();
            final Class<UnaryOperator<String>> preprocessorType = preprocessors.get(key);
            String value = placeholder.getValue();

            if (preprocessorType != null) {
                log.info("Replacing trust list template placeholder '{}' with a value derived from '{}' using '{}'", key, value, preprocessorType);
                value = ReflectionUtils.newInstance(preprocessorType).apply(value);
            } else {
                log.info("Replacing trust list template placeholder '{}' with value '{}'", key, value);
            }

            template = StringUtils.replace(template, key, value);
        }

        return template;
    }

}
