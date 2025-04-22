package ee.test_gov.tsl.server.configuration.provider;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.core.io.Resource;
import org.springframework.validation.annotation.Validated;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;
import java.util.function.UnaryOperator;

@Getter
@Validated
@Setter(AccessLevel.PACKAGE)
public class TemplateConfigurationProvider {

    @NotNull
    private Resource source;
    @NotNull
    private Charset charset = StandardCharsets.UTF_8;

    @NotNull
    private Map<@NotBlank String, @NotNull String> placeholderMappings = Collections.emptyMap();
    @NotNull
    private Map<@NotBlank String, @NotNull Class<UnaryOperator<String>>> placeholderProcessors = Collections.emptyMap();

}
