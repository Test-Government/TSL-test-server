package ee.test_gov.tsl.server.configuration;

import ee.test_gov.tsl.server.configuration.provider.TrustListConfigurationProvider;
import ee.test_gov.tsl.server.configuration.provider.TslConfigurationProvider;
import ee.test_gov.tsl.server.entry.TslEntry;
import ee.test_gov.tsl.server.entry.TslEntryFactory;
import ee.test_gov.tsl.server.util.ReflectionUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class TslConfiguration {

    @Bean
    Map<String, TslEntry> tslRegistry(TslConfigurationProvider configurationProvider) {
        return configurationProvider.getTrustLists().stream().collect(Collectors.toUnmodifiableMap(
                TrustListConfigurationProvider::getServeAs,
                new TslEntryFactory(
                        ReflectionUtils.newInstance(configurationProvider.getTrustListFactory().getTemplateFactory()),
                        ReflectionUtils.newInstance(configurationProvider.getTrustListFactory().getSigner()),
                        ReflectionUtils.newInstance(configurationProvider.getTrustListFactory().getMediaTypeResolver())
                )
        ));
    }

}
