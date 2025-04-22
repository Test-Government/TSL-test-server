package ee.test_gov.tsl.server.configuration.provider;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Getter
@Validated
@Configuration
@Setter(AccessLevel.PACKAGE)
@ConfigurationProperties(prefix = "tsl")
public class TslConfigurationProvider {

    @Valid
    @NotNull
    private FactoryConfigurationProvider trustListFactory;
    @NotNull
    private List<@NotNull @Valid TrustListConfigurationProvider> trustLists;

}
