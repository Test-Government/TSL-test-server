package ee.test_gov.tsl.server.configuration.provider;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

@Getter
@Validated
@Setter(AccessLevel.PACKAGE)
public class TrustListConfigurationProvider {

    @NotBlank
    private String serveAs;

    @Valid
    private SignerConfigurationProvider signer;

    @Valid
    @NotNull
    private TemplateConfigurationProvider template;

}
