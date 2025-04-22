package ee.test_gov.tsl.server.configuration.provider;

import eu.europa.esig.dss.model.DSSDocument;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;

import java.util.function.BiFunction;
import java.util.function.Function;

@Getter
@Validated
@Setter(AccessLevel.PACKAGE)
public class FactoryConfigurationProvider {

    @NotNull
    private Class<Function<TemplateConfigurationProvider, DSSDocument>> templateFactory;
    @NotNull
    private Class<BiFunction<DSSDocument, SignerConfigurationProvider, DSSDocument>> signer;
    @NotNull
    private Class<Function<TrustListConfigurationProvider, MediaType>> mediaTypeResolver;

}
