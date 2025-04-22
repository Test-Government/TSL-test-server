package ee.test_gov.tsl.server.processor;

import ee.test_gov.tsl.server.configuration.provider.TrustListConfigurationProvider;
import org.springframework.http.MediaType;

import java.util.function.Function;

public class ApplicationXmlMediaTypeResolver implements Function<TrustListConfigurationProvider, MediaType> {

    @Override
    public MediaType apply(TrustListConfigurationProvider configurationProvider) {
        return MediaType.APPLICATION_XML;
    }

}
