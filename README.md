# TSL-test-server

Spring Boot application for creating, signing and serving trust lists for testing purposes.

## Prerequisites

For building and running this application, Java 21 is needed.

## Running the application on command line

The application package itself contains only [minimal configuration](src/main/resources/application.yml).
To be able to run the application, additional configuration must be provided via `spring.config.additional-location`.
For more information about available configuration options, see [here](#configuring-the-application).

This example builds the application JAR and runs it using the specified additional configuration.

1. Build the application JAR
   ```shell
   ./mvnw clean package
   ```
2. Start the application
   ```shell
   java -jar target/tsl-test-server-0.1.0-SNAPSHOT.jar --spring.config.additional-location=file:/path/to/my/configuration.yml
   ```

By default, the application is accessible on port `8080`, i.e. http://localhost:8080

## Configuring the application

The following chapters describe application-specific configuration parameters.
For common Spring Boot properties see [here](https://docs.spring.io/spring-boot/appendix/application-properties/index.html).

### Trust List factory

The following configuration parameters specify the base implementation classes for generating and signing trust lists.

| Configuration parameter | Required | Description | Example |
| ----------------------- | -------- | ----------- | ------- |
| `tsl.trust-list-factory.template-factory` | YES | Implementing<sup>1</sup> class for loading and processing trust list templates. | `ee.test_gov.tsl.server.template.SimpleTemplateFactory` |
| `tsl.trust-list-factory.signer` | YES | Implementing<sup>2</sup> class for signing trust list templates. | `ee.test_gov.tsl.server.signature.DefaultTrustedListSigner` |
| `tsl.trust-list-factory.media-type-resolver` | YES | Implementing<sup>3</SUP> class for resolving trust list mime types. | `ee.test_gov.tsl.server.processor.ApplicationXmlMediaTypeResolver` |

<sup>1</sup> An implementation of a template factory must implement `Function<TemplateConfigurationProvider, DSSDocument>`.
For available options see [here](#functiontemplateconfigurationproviderdssdocument).

<sup>2</sup> An implementation of a trust list signer must implement `BiFunction<DSSDocument, SignerConfigurationProvider, DSSDocument>`.
For available options see [here](#bifunctiondssdocumentsignerconfigurationproviderdssdocument).

<sup>3</sup> An implementation of a media type resolver must implement `Function<TrustListConfigurationProvider, MediaType>`.
For available options see [here](#functiontrustlistconfigurationprovidermediatype).

### Individual Trust Lists

The `tsl.trust-lists` is an array of configuration parameter blocks for configuring an arbitrary amount of individual trust lists.

| Configuration parameter | Required | Description | Example |
| ----------------------- | -------- | ----------- | ------- |
| `tsl.trust-lists[i].serve-as` | YES | A string specifying the path<sup>1</sup> for serving this trust list. | `my-trust-list.xml` |
| `tsl.trust-lists[i].template` | YES | A block of parameters specifying how the template for this trust list is loaded and processed. | - |
| `tsl.trust-lists[i].signer` | NO | A block of parameters specifying how the template for this trust list is signed. If not specified, then the template won't be signed. | - |

<sup>1</sup> For example, if the path of the trust list is `my-trust-list.xml` and the server runs on port `8080`,
then the trust list will be available on `http://localhost:8080/my-trust-list.xml`.

The `tsl.trust-lists[i].template` blocks specify the following configuration parameters:

| Configuration parameter | Required | Description | Example |
| ----------------------- | -------- | ----------- | ------- |
| `tsl.trust-lists[i].template.source` | YES | The source of the template. | `file:/path/to/my-trust-list.xml.template` |
| `tsl.trust-lists[i].template.charset` | NO | The charset of the template source for when the template needs to be processed as text. Defaults to `UTF-8` if not specified. | `UTF-8` |
| `tsl.trust-lists[i].template.placeholder-mappings` | NO | A block of key-value<sup>2</sup> pairs for mapping template placeholder strings to concrete value strings. | - |
| `tsl.trust-lists[i].template.placeholder-processors` | NO | A block of key-value<sup>2</sup> pairs for specifying implementing<sup>3</sup> classes for processing specific placeholders. | - |

<sup>2</sup> In case a placeholder key contains special characters, it must be quoted and wrapped inside square brackets.
For example, a placeholder key `{PLACEHOLDER}` must be formatted as `'[{PLACEHOLDER}]'`.
An example YAML configuration:

```yaml
placeholder-mappings:
  '[{PLACEHOLDER}]': 'value'
placeholder-processors:
  '[{PLACEHOLDER}]': ee.test_gov.tsl.server.processor.KeyStoreResourceToBase64CertificateMapper
```

<sup>3</sup> An implementation of a placeholder processor must implement `UnaryOperator<String>`.
For available options see [here](#unaryoperatorstring).

The `tsl.trust-lists[i].signer` blocks, if present, specify the following configuration parameters:

| Configuration parameter | Required | Description | Example |
| ----------------------- | -------- | ----------- | ------- |
| `tsl.trust-lists[i].signer.keystore` | YES | The keystore containing the key-pair for singing a trust list. | `file:/path/to/my-keystore.p12` |
| `tsl.trust-lists[i].signer.keystore-password` | YES | Password for the signer keystore. | `changeit` |
| `tsl.trust-lists[i].signer.keystore-type` | NO | Signer keystore type. Defaults to `PKCS12` if not specified. | `PKCS12` |
| `tsl.trust-lists[i].signer.key-alias` | NO | Alias for the signer key from the specified keystore. Picks the first available key if not specified. | `mykey` |
| `tsl.trust-lists[i].signer.signature-parameters-builder` | YES | Implementing<sup>4</sup> class for creating signature parameter builders. | `ee.test_gov.tsl.server.signature.TrustedListV6SignatureParametersBuilderFactory` |
| `tsl.trust-lists[i].signer.signature-digest-algorithm` | NO | Signature digest algorithm. If not specified, the implementation-specific digest algorithm will be used. | `SHA256` |
| `tsl.trust-lists[i].signer.reference-digest-algorithm` | NO | Reference digest algorithm. If not specified, the implementation-specific digest algorithm will be used. | `SHA256` |
| `tsl.trust-lists[i].signer.claimed-signing-time` | NO | Claimed signing time as ISO instant. If not specified, the current time will be used. | `2024-08-22T10:32:56Z` |

<sup>4</sup> An implementation of a signature parameter builder factory must implement `BiFunction<CertificateToken, DSSDocument, AbstractTrustedListSignatureParametersBuilder>`.
For available options see [here](#bifunctioncertificatetokendssdocumentabstracttrustedlistsignatureparametersbuilder).

## Available implementations

The following chapters list available implementing classes for specific interfaces.

### BiFunction<CertificateToken,DSSDocument,AbstractTrustedListSignatureParametersBuilder>

* `ee.test_gov.tsl.server.signature.TrustedListV5SignatureParametersBuilderFactory` - creates a signature parameters
  builder of type `eu.europa.esig.dss.xades.tsl.TrustedListV5SignatureParametersBuilder` for creating TLv5 signatures.
* `ee.test_gov.tsl.server.signature.TrustedListV6SignatureParametersBuilderFactory` - creates a signature parameters
  builder of type `eu.europa.esig.dss.xades.tsl.TrustedListV6SignatureParametersBuilder` for creating TLv6 signatures.

### BiFunction<DSSDocument,SignerConfigurationProvider,DSSDocument>

* `ee.test_gov.tsl.server.signature.DefaultTrustedListSigner` - signs a trust list template according to the
  configuration. Uses all the configuration parameters of the `tsl.trust-lists[i].signer` block.

### Function<TemplateConfigurationProvider,DSSDocument>

* `ee.test_gov.tsl.server.template.PlaceholderValueProcessingTemplateFactory` - loads a template as a string and
  replaces its placeholders with concrete values according to the configured mappings.
  In case a placeholder processor is configured for a placeholder, its mapped value will be run through that processor
  before injecting it into the template.
  Uses the following configuration parameters:
  - `tsl.trust-lists[i].template.source` for locating the resource to load the template from.
  - `tsl.trust-lists[i].template.charset` for parsing the template as a string and re-serializing the processed template
    string.
  - `tsl.trust-lists[i].template.placeholder-mappings` for mapping the specified placeholders to specific values.
  - `tsl.trust-lists[i].template.placeholder-processors` for processing the specified placeholder values before
    injecting them into the template.
* `ee.test_gov.tsl.server.template.SimpleTemplateFactory` - loads a template as a binary without performing any
  additional processing.
  Uses the following configuration parameters:
  - `tsl.trust-lists[i].template.source` for locating the resource to load the template from.

### Function<TrustListConfigurationProvider,MediaType>

* `ee.test_gov.tsl.server.processor.ApplicationXmlMediaTypeResolver` - always returns `application/xml`.

### UnaryOperator\<String>

* `ee.test_gov.tsl.server.processor.KeyStoreResourceToBase64CertificateMapper` - loads a single certificate from a
  keystore/truststore specified in the input string, and returns it as a base64-encoded string.
  The input string is expected to be in one of two supported formats:
  - A resource, e.g. `file:/path/to/my-truststore.p12`.
    The first available certificate from the specified keystore/truststore is returned.
    **NB:** While Java should be able to open keystores without providing a password, accessing certificate entries
    might fail if not supported by the keystore implementation!
  - A pipe-separated (`|`) list of values, e.g. `file:/path/to/my-truststore.p12|pass:changeit|alias:mycert`.
    Exactly one such value must define a resource as specified above.
    Other optional values are as follows:
    - Alias of the certificate to extract. Must be prefixed with `alias:`, e.g. `alias:mycert`.
    - Password of the keystore/truststore. Must be prefixed with `pass:`, e.g. `pass:changeit`.
    - Type of the keystore/truststore. Must be prefixed with `type:`, e.g. `type:PKCS12`.
      Defaults to `PKCS12` if type is not specified.
