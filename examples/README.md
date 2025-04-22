# Example files for TSL test server

This directory contains the following types of example files.

* [Example configurations](#example-configurations)
  - [v5-vs-v6.yml](#v5-vs-v6yml)
* [Keystores and truststores](#keystores-and-truststores)
* [Trust List template files](#trust-list-template-files)

## Example configurations

The following subchapters describe specific example configurations.

### v5-vs-v6.yml

[Configures](v5-vs-v6.yml) the TSL server to serve various TLv5 and TLv6 trust lists.
* By default, the trust lists are served on port `8080`. This can be overridden via the `server.port` parameter.
* By default, the base URL of other local trust list locations, injected into LOTL-is, is `http://localhost:8080`.
  This can be overridden via the `TSL_BASE_URL` environment variable.

The application can be run using this configuration from the project root directory via the following command:

```shell
java -jar target/tsl-test-server-0.1.0-SNAPSHOT.jar --spring.config.additional-location=file:examples/v5-vs-v6.yml
```

Available trust lists:
* http://localhost:8080/EE_T-v5-ec.xml
  - generated from `test-tl-EE_T-30.xml.template` template with version number `5`.
  - signed by `CN=Signer EC,OU=TSL,C=EE` using v5 signature parameters and `SHA384` signature digest algorithm.
* http://localhost:8080/EE_T-v5-rsa.xml
  - generated from `test-tl-EE_T-30.xml.template` template with version number `5`.
  - signed by `CN=Signer RSA,C=EE,OU=TSL` using v5 signature parameters and `SHA256` signature digest algorithm.
* http://localhost:8080/EE_T-v5-unsigned.xml
  - generated from `test-tl-EE_T-30.xml.template` template with version number `5`.
* http://localhost:8080/EE_T-v6-ec.xml
  - generated from `test-tl-EE_T-30.xml.template` template with version number `6`.
  - signed by `CN=Signer EC,OU=TSL,C=EE` using v6 signature parameters and `SHA384` signature digest algorithm.
* http://localhost:8080/EE_T-v6-rsa.xml
  - generated from `test-tl-EE_T-30.xml.template` template with version number `6`.
  - signed by `CN=Signer RSA,C=EE,OU=TSL` using v6 signature parameters and `SHA512` signature digest algorithm.
* http://localhost:8080/EE_T-v6-unsigned.xml
  - generated from `test-tl-EE_T-30.xml.template` template with version number `6`.

Available LOTL-s:
* http://localhost:8080/lotl-v5-ec.xml
  - generated from `test-lotl-EE-4.xml.template` template with version number `5`.
  - points to http://localhost:8080/EE_T-v5-ec.xml trusting `CN=Signer EC,OU=TSL,C=EE` signer.
  - signed by `CN=Signer EC,OU=TSL,C=EE` using v5 signature parameters and `SHA384` signature digest algorithm.
* http://localhost:8080/lotl-v5-v6-mixed-ec.xml
  - generated from `test-lotl-EE-4.xml.template` template with version number `5`.
  - points to http://localhost:8080/EE_T-v6-ec.xml trusting `CN=Signer EC,OU=TSL,C=EE` signer.
  - signed by `CN=Signer EC,OU=TSL,C=EE` using v5 signature parameters and `SHA384` signature digest algorithm.
* http://localhost:8080/lotl-v5-ec-rsa-mixed.xml
  - generated from `test-lotl-EE-4.xml.template` template with version number `5`.
  - points to http://localhost:8080/EE_T-v5-rsa.xml trusting `CN=Signer RSA,C=EE,OU=TSL` signer.
  - signed by `CN=Signer EC,OU=TSL,C=EE` using v5 signature parameters and `SHA384` signature digest algorithm.
* http://localhost:8080/lotl-v5-rsa.xml
  - generated from `test-lotl-EE-4.xml.template` template with version number `5`.
  - points to http://localhost:8080/EE_T-v5-rsa.xml trusting `CN=Signer RSA,C=EE,OU=TSL` signer.
  - signed by `CN=Signer RSA,C=EE,OU=TSL` using v5 signature parameters and `SHA256` signature digest algorithm.
* http://localhost:8080/lotl-v5-v6-mixed-rsa.xml
  - generated from `test-lotl-EE-4.xml.template` template with version number `5`.
  - points to http://localhost:8080/EE_T-v6-rsa.xml trusting `CN=Signer RSA,C=EE,OU=TSL` signer.
  - signed by `CN=Signer RSA,C=EE,OU=TSL` using v5 signature parameters and `SHA256` signature digest algorithm.
* http://localhost:8080/lotl-v5-rsa-ec-mixed.xml
  - generated from `test-lotl-EE-4.xml.template` template with version number `5`.
  - points to http://localhost:8080/EE_T-v5-ec.xml trusting `CN=Signer EC,OU=TSL,C=EE` signer.
  - signed by `CN=Signer RSA,C=EE,OU=TSL` using v5 signature parameters and `SHA256` signature digest algorithm.
* http://localhost:8080/lotl-v5-unsigned.xml
  - generated from `test-lotl-EE-4.xml.template` template with version number `5`.
  - points to http://localhost:8080/EE_T-v5-unsigned.xml trusting `CN=Signer EC,OU=TSL,C=EE` signer.
* http://localhost:8080/lotl-v6-ec.xml
  - generated from `test-lotl-EE-4.xml.template` template with version number `6`.
  - points to http://localhost:8080/EE_T-v6-ec.xml trusting `CN=Signer EC,OU=TSL,C=EE` signer.
  - signed by `CN=Signer EC,OU=TSL,C=EE` using v6 signature parameters and `SHA384` signature digest algorithm.
* http://localhost:8080/lotl-v6-v5-mixed-ec.xml
  - generated from `test-lotl-EE-4.xml.template` template with version number `6`.
  - points to http://localhost:8080/EE_T-v5-ec.xml trusting `CN=Signer EC,OU=TSL,C=EE` signer.
  - signed by `CN=Signer EC,OU=TSL,C=EE` using v6 signature parameters and `SHA384` signature digest algorithm.
* http://localhost:8080/lotl-v6-ec-rsa-mixed.xml
  - generated from `test-lotl-EE-4.xml.template` template with version number `6`.
  - points to http://localhost:8080/EE_T-v6-rsa.xml trusting `CN=Signer RSA,C=EE,OU=TSL` signer.
  - signed by `CN=Signer EC,OU=TSL,C=EE` using v6 signature parameters and `SHA384` signature digest algorithm.
* http://localhost:8080/lotl-v6-rsa.xml
  - generated from `test-lotl-EE-4.xml.template` template with version number `6`.
  - points to http://localhost:8080/EE_T-v6-rsa.xml trusting `CN=Signer RSA,C=EE,OU=TSL` signer.
  - signed by `CN=Signer RSA,C=EE,OU=TSL` using v6 signature parameters and `SHA512` signature digest algorithm.
* http://localhost:8080/lotl-v6-v5-mixed-rsa.xml
  - generated from `test-lotl-EE-4.xml.template` template with version number `6`.
  - points to http://localhost:8080/EE_T-v5-rsa.xml trusting `CN=Signer RSA,C=EE,OU=TSL` signer.
  - signed by `CN=Signer RSA,C=EE,OU=TSL` using v6 signature parameters and `SHA512` signature digest algorithm.
* http://localhost:8080/lotl-v6-rsa-ec-mixed.xml
  - generated from `test-lotl-EE-4.xml.template` template with version number `6`.
  - points to http://localhost:8080/EE_T-v6-ec.xml trusting `CN=Signer EC,OU=TSL,C=EE` signer.
  - signed by `CN=Signer RSA,C=EE,OU=TSL` using v6 signature parameters and `SHA512` signature digest algorithm.
* http://localhost:8080/lotl-v6-unsigned.xml
  - generated from `test-lotl-EE-4.xml.template` template with version number `6`.
  - points to http://localhost:8080/EE_T-v6-unsigned.xml trusting `CN=Signer EC,OU=TSL,C=EE` signer.

## Keystores and truststores

* Keystore `signer.keystore.p12` and truststore `signer.truststore.p12` contain the following signers:
  - With alias `ec`:
    - Subject: `CN=Signer EC,OU=TSL,C=EE`
    - Issuer: `CN=Signer EC,OU=TSL,C=EE`
    - Key type: EC 384 bits (P-384)
    - Valid from: 01.01.2025 00:00:00 EET
    - Valid until: 01.01.2050 00:00:00 EET
    - Key usage: Digital Signature, Non-Repudiation
    - Extended key usage: Trusted-service Status List (TSL) Signing (0.4.0.2231.3.0)
  - With alias `rsa`:
    - Subject: `CN=Signer RSA,C=EE,OU=TSL`
    - Issuer: `CN=Signer RSA,C=EE,OU=TSL`
    - Key type: RSA 4096 bits
    - Valid from: 01.01.2025 00:00:00 EET
    - Valid until: 01.01.2050 00:00:00 EET
    - Key usage: Digital Signature, Non-Repudiation
    - Extended key usage: Trusted-service Status List (TSL) Signing (0.4.0.2231.3.0)

All passwords are `changeit`.

## Trust List template files

* [`test-lotl-EE-4.xml.template`](test-lotl-EE-4.xml.template) contains an unsigned copy of the
  [Estonian test LOTL](https://open-eid.github.io/test-TL/tl-mp-test-EE.xml) with *TSLSequenceNumber* 4.
  The template contains the following placeholders:
  - `{TSL_VERSION_IDENTIFIER}` as the value of *TrustServiceStatusList* > *SchemeInformation* > *TSLVersionIdentifier*
    tag.
  - `{OTHER_TSL_CERTIFICATE_B64}` as the value of *TrustServiceStatusList* > *SchemeInformation* > *PointersToOtherTSL*
    \> *OtherTSLPointer* > *ServiceDigitalIdentities* > *ServiceDigitalIdentity* > *DigitalId* > *X509Certificate* tag
    for the `EE_T` territory.
  - `{OTHER_TSL_LOCATION}` as the value of *TrustServiceStatusList* > *SchemeInformation* > *PointersToOtherTSL* >
    *OtherTSLPointer* > *TSLLocation* tag for the `EE_T` territory.
* [`test-tl-EE_T-30.xml.template`](test-tl-EE_T-30.xml.template) contains an unsigned copy of the
  [Estonian test trust list](https://open-eid.github.io/test-TL/EE_T.xml) with *TSLSequenceNumber* 30.
  The template contains the following placeholder:
  - `{TSL_VERSION_IDENTIFIER}` as the value of *TrustServiceStatusList* > *SchemeInformation* > *TSLVersionIdentifier*
    tag.
