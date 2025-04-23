# Example files for TSL test server

This directory contains the following types of example files.

* [Example configurations](#example-configurations)
  - [v5-vs-v6.yml](#v5-vs-v6yml)
  - [v6-identifiers.yml](#v6-identifiersyml)
* [Keystores and truststores](#keystores-and-truststores)
* [Trust List template files](#trust-list-template-files)

## Example configurations

The following subchapters describe specific example configurations.

### v5-vs-v6.yml

[Configures](v5-vs-v6.yml) the TSL server to serve various TLv5 and TLv6 trust lists.
* By default, the trust lists are served on port `8080`. This can be overridden via the `server.port` parameter.
* By default, the base URL of other local trust list locations, injected into LOTL-s, is `http://localhost:8080`.
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

### v6-identifiers.yml

[Configures](v6-identifiers.yml) the TSL server to serve TLv6 trust lists with various combinations of trust service
identifiers.
* By default, the trust lists are served on port `8080`. This can be overridden via the `server.port` parameter.
* By default, the base URL of other local trust list locations, injected into LOTL-s, is `http://localhost:8080`.
  This can be overridden via the `TSL_BASE_URL` environment variable.

The application can be run using this configuration from the project root directory via the following command:

```shell
java -jar target/tsl-test-server-0.1.0-SNAPSHOT.jar --spring.config.additional-location=file:examples/v6-identifiers.yml
```

Available trust lists with default identifiers for reference:
* http://localhost:8080/lotl-v6-default-identifiers.xml
  - generated from `test-lotl-EE-4.xml.template` template with version number `6`.
  - points to http://localhost:8080/EE_T-v6-default-identifiers.xml trusting `CN=Signer EC,OU=TSL,C=EE` signer.
  - signed by `CN=Signer EC,OU=TSL,C=EE` using v6 signature parameters and `SHA384` signature digest algorithm.
* http://localhost:8080/EE_T-v6-default-identifiers.xml
  - generated from `test-tl-EE_T-30-with-identifiers.xml.template` template with version number `6`.
  - all the values injected into service type identifier placeholders are the same as they were in the original trust
    list.
  - signed by `CN=Signer EC,OU=TSL,C=EE` using v6 signature parameters and `SHA384` signature digest algorithm.

Available trust lists with trust service type identifiers replaced by new TLv6 identifiers, except for "*TEST of
ESTEID2018*" and recent TSA-s (2023, 2025):
* http://localhost:8080/lotl-v6-new-identifiers-except-esteid2018-and-recent-tsa.xml
  - generated from `test-lotl-EE-4.xml.template` template with version number `6`.
  - points to http://localhost:8080/EE_T-v6-new-identifiers-except-esteid2018-and-recent-tsa.xml trusting
    `CN=Signer EC,OU=TSL,C=EE` signer.
  - signed by `CN=Signer EC,OU=TSL,C=EE` using v6 signature parameters and `SHA384` signature digest algorithm.
* http://localhost:8080/EE_T-v6-new-identifiers-except-esteid2018-and-recent-tsa.xml
  - generated from `test-tl-EE_T-30-with-identifiers.xml.template` template with version number `6`.
  - the values injected into all the service type identifier placeholders, except for the "*TEST of ESTEID2018*" and
    recent TSA services, are picked from the list of new TLv6 identifiers.
  - signed by `CN=Signer EC,OU=TSL,C=EE` using v6 signature parameters and `SHA384` signature digest algorithm.

Available trust lists with all trust service type identifiers replaced by new TLv6 identifiers:
* http://localhost:8080/lotl-v6-all-new-identifiers.xml
  - generated from `test-lotl-EE-4.xml.template` template with version number `6`.
  - points to http://localhost:8080/EE_T-v6-all-new-identifiers.xml trusting `CN=Signer EC,OU=TSL,C=EE` signer.
  - signed by `CN=Signer EC,OU=TSL,C=EE` using v6 signature parameters and `SHA384` signature digest algorithm.
* http://localhost:8080/EE_T-v6-all-new-identifiers.xml
  - generated from `test-tl-EE_T-30-with-identifiers.xml.template` template with version number `6`.
  - the values injected into all the service type identifier placeholders are picked from the list of new TLv6
    identifiers.
  - signed by `CN=Signer EC,OU=TSL,C=EE` using v6 signature parameters and `SHA384` signature digest algorithm.

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
* [`test-tl-EE_T-30-with-identifiers.xml.template`](test-tl-EE_T-30-with-identifiers.xml.template) contains an unsigned
  copy of the [Estonian test trust list](https://open-eid.github.io/test-TL/EE_T.xml) with *TSLSequenceNumber* 30.
  The template contains the following placeholders:
  - `{TSL_VERSION_IDENTIFIER}` as the value of *TrustServiceStatusList* > *SchemeInformation* > *TSLVersionIdentifier*
    tag.
  - `{TEST_of_ESTEID_SK_2011_TYPE_IDENTIFIER}` as the value of *TrustServiceStatusList* > *TrustServiceProviderList* >
    *TrustServiceProvider* > *TSPServices* > *TSPService* > *ServiceInformation* > *ServiceTypeIdentifier* and *...* >
    *TSPService* > *ServiceHistory* > *ServiceHistoryInstance* > *ServiceTypeIdentifier* tags for the service named
    "*TEST of ESTEID-SK 2011*".
  - `{TEST_of_ESTEID_SK_2015_TYPE_IDENTIFIER}` as the value of *TrustServiceStatusList* > *TrustServiceProviderList* >
    *TrustServiceProvider* > *TSPServices* > *TSPService* > *ServiceInformation* > *ServiceTypeIdentifier* and *...* >
    *TSPService* > *ServiceHistory* > *ServiceHistoryInstance* > *ServiceTypeIdentifier* tags for the service named
    "*TEST of ESTEID-SK 2015*".
  - `{TEST_of_EID_SK_2016_TYPE_IDENTIFIER}` as the value of *TrustServiceStatusList* > *TrustServiceProviderList* >
    *TrustServiceProvider* > *TSPServices* > *TSPService* > *ServiceInformation* > *ServiceTypeIdentifier* tag for the
    service named "*TEST of EID-SK 2016*".
  - `{TEST_of_NQ_SK_2016_TYPE_IDENTIFIER}` as the value of *TrustServiceStatusList* > *TrustServiceProviderList* >
    *TrustServiceProvider* > *TSPServices* > *TSPService* > *ServiceInformation* > *ServiceTypeIdentifier* tag for the
    service named "*TEST of NQ-SK 2016*".
  - `{TEST_of_ESTEID2018_TYPE_IDENTIFIER}` as the value of *TrustServiceStatusList* > *TrustServiceProviderList* >
    *TrustServiceProvider* > *TSPServices* > *TSPService* > *ServiceInformation* > *ServiceTypeIdentifier* tag for the
    service named "*TEST of ESTEID2018*".
  - `{TEST_of_SK_OCSP_RESPONDER_2011_TYPE_IDENTIFIER}` as the value of *TrustServiceStatusList* >
    *TrustServiceProviderList* > *TrustServiceProvider* > *TSPServices* > *TSPService* > *ServiceInformation* >
    *ServiceTypeIdentifier* and *...* > *TSPService* > *ServiceHistory* > *ServiceHistoryInstance* >
    *ServiceTypeIdentifier* tags for the service named "*TEST of SK OCSP RESPONDER 2011*".
  - `{TEST_of_SK_OCSP_RESPONDER_2021_EXPIRED_TYPE_IDENTIFIER}` as the value of *TrustServiceStatusList* >
    *TrustServiceProviderList* > *TrustServiceProvider* > *TSPServices* > *TSPService* > *ServiceInformation* >
    *ServiceTypeIdentifier* tag for the service named "*TEST of SK OCSP RESPONDER 2021 EXPIRED*".
  - `{TEST_of_SK_OCSP_RESPONDER_2020_TYPE_IDENTIFIER}` as the value of *TrustServiceStatusList* >
    *TrustServiceProviderList* > *TrustServiceProvider* > *TSPServices* > *TSPService* > *ServiceInformation* >
    *ServiceTypeIdentifier* tag for the service named "*TEST of SK OCSP RESPONDER 2020*".
  - `{DEMO_of_SK_TSA_TYPE_IDENTIFIER}` as the value of *TrustServiceStatusList* > *TrustServiceProviderList* >
    *TrustServiceProvider* > *TSPServices* > *TSPService* > *ServiceInformation* > *ServiceTypeIdentifier* and *...* >
    *TSPService* > *ServiceHistory* > *ServiceHistoryInstance* > *ServiceTypeIdentifier* tags for the service named
    "*DEMO of SK TSA*".
  - `{DEMO_SK_TIMESTAMPING_AUTHORITY_2020_TYPE_IDENTIFIER}` as the value of *TrustServiceStatusList* >
    *TrustServiceProviderList* > *TrustServiceProvider* > *TSPServices* > *TSPService* > *ServiceInformation* >
    *ServiceTypeIdentifier* tag for the service named "*DEMO SK TIMESTAMPING AUTHORITY 2020*".
  - `{TEST_of_KLASS3_2010_TYPE_IDENTIFIER}` as the value of *TrustServiceStatusList* > *TrustServiceProviderList* >
    *TrustServiceProvider* > *TSPServices* > *TSPService* > *ServiceInformation* > *ServiceTypeIdentifier* and *...* >
    *TSPService* > *ServiceHistory* > *ServiceHistoryInstance* > *ServiceTypeIdentifier* tags for the service named
    "*TEST of KLASS3 2010*".
  - `{TEST_of_KLASS3_SK_2016_TYPE_IDENTIFIER}` as the value of *TrustServiceStatusList* > *TrustServiceProviderList* >
    *TrustServiceProvider* > *TSPServices* > *TSPService* > *ServiceInformation* > *ServiceTypeIdentifier* tag for the
    service named "*TEST of KLASS3-SK 2016*".
  - `{TEST_of_SK_ID_Solutions_EID_NQ_2021E_TYPE_IDENTIFIER}` as the value of *TrustServiceStatusList* >
    *TrustServiceProviderList* > *TrustServiceProvider* > *TSPServices* > *TSPService* > *ServiceInformation* >
    *ServiceTypeIdentifier* tag for the service named "*TEST of SK ID Solutions EID-NQ 2021E*".
  - `{TEST_of_SK_ID_Solutions_EID_NQ_2021R_TYPE_IDENTIFIER}` as the value of *TrustServiceStatusList* >
    *TrustServiceProviderList* > *TrustServiceProvider* > *TSPServices* > *TSPService* > *ServiceInformation* >
    *ServiceTypeIdentifier* tag for the service named "*TEST of SK ID Solutions EID-NQ 2021R*".
  - `{TEST_of_SK_ID_Solutions_EID_Q_2021E_TYPE_IDENTIFIER}` as the value of *TrustServiceStatusList* >
    *TrustServiceProviderList* > *TrustServiceProvider* > *TSPServices* > *TSPService* > *ServiceInformation* >
    *ServiceTypeIdentifier* tag for the service named "*TEST of SK ID Solutions EID-Q 2021E*".
  - `{TEST_of_SK_ID_Solutions_EID_Q_2021R_TYPE_IDENTIFIER}` as the value of *TrustServiceStatusList* >
    *TrustServiceProviderList* > *TrustServiceProvider* > *TSPServices* > *TSPService* > *ServiceInformation* >
    *ServiceTypeIdentifier* tag for the service named "*TEST of SK ID Solutions EID-Q 2021R*".
  - `{TEST_of_SK_ID_Solutions_ORG_2021E_TYPE_IDENTIFIER}` as the value of *TrustServiceStatusList* >
    *TrustServiceProviderList* > *TrustServiceProvider* > *TSPServices* > *TSPService* > *ServiceInformation* >
    *ServiceTypeIdentifier* tag for the service named "*TEST of SK ID Solutions ORG 2021E*".
  - `{TEST_of_SK_ID_Solutions_ORG_2021R_TYPE_IDENTIFIER}` as the value of *TrustServiceStatusList* >
    *TrustServiceProviderList* > *TrustServiceProvider* > *TSPServices* > *TSPService* > *ServiceInformation* >
    *ServiceTypeIdentifier* tag for the service named "*TEST of SK ID Solutions ORG 2021R*".
  - `{TEST_of_SK_TSA_CA_2023E_TYPE_IDENTIFIER}` as the value of *TrustServiceStatusList* > *TrustServiceProviderList* >
    *TrustServiceProvider* > *TSPServices* > *TSPService* > *ServiceInformation* > *ServiceTypeIdentifier* tag for the
    service named "*TEST of SK TSA CA 2023E*".
  - `{TEST_of_SK_TSA_CA_2023R_TYPE_IDENTIFIER}` as the value of *TrustServiceStatusList* > *TrustServiceProviderList* >
    *TrustServiceProvider* > *TSPServices* > *TSPService* > *ServiceInformation* > *ServiceTypeIdentifier* tag for the
    service named "*TEST of SK TSA CA 2023R*".
  - `{DEMO_SK_TIMESTAMPING_AUTHORITY_2023E_TYPE_IDENTIFIER}` as the value of *TrustServiceStatusList* >
    *TrustServiceProviderList* > *TrustServiceProvider* > *TSPServices* > *TSPService* > *ServiceInformation* >
    *ServiceTypeIdentifier* tag for the service named "*DEMO SK TIMESTAMPING AUTHORITY 2023E*".
  - `{DEMO_SK_TIMESTAMPING_AUTHORITY_2023R_TYPE_IDENTIFIER}` as the value of *TrustServiceStatusList* >
    *TrustServiceProviderList* > *TrustServiceProvider* > *TSPServices* > *TSPService* > *ServiceInformation* >
    *ServiceTypeIdentifier* tag for the service named "*DEMO SK TIMESTAMPING AUTHORITY 2023R*".
  - `{TEST_of_SK_ID_Solutions_EID_Q_2024E_TYPE_IDENTIFIER}` as the value of *TrustServiceStatusList* >
    *TrustServiceProviderList* > *TrustServiceProvider* > *TSPServices* > *TSPService* > *ServiceInformation* >
    *ServiceTypeIdentifier* tag for the service named "*TEST of SK ID Solutions EID-Q 2024E*".
  - `{TEST_of_SK_ID_Solutions_EID_Q_2024R_TYPE_IDENTIFIER}` as the value of *TrustServiceStatusList* >
    *TrustServiceProviderList* > *TrustServiceProvider* > *TSPServices* > *TSPService* > *ServiceInformation* >
    *ServiceTypeIdentifier* tag for the service named "*TEST of SK ID Solutions EID-Q 2024R*".
  - `{DEMO_SK_TIMESTAMPING_UNIT_2025E_TYPE_IDENTIFIER}` as the value of *TrustServiceStatusList* >
    *TrustServiceProviderList* > *TrustServiceProvider* > *TSPServices* > *TSPService* > *ServiceInformation* >
    *ServiceTypeIdentifier* tag for the service named "*DEMO SK TIMESTAMPING UNIT 2025E*".
  - `{DEMO_SK_TIMESTAMPING_UNIT_2025R_TYPE_IDENTIFIER}` as the value of *TrustServiceStatusList* >
    *TrustServiceProviderList* > *TrustServiceProvider* > *TSPServices* > *TSPService* > *ServiceInformation* >
    *ServiceTypeIdentifier* tag for the service named "*DEMO SK TIMESTAMPING UNIT 2025R*".
  - `{Test_ESTEID2025_TYPE_IDENTIFIER}` as the value of *TrustServiceStatusList* > *TrustServiceProviderList* >
    *TrustServiceProvider* > *TSPServices* > *TSPService* > *ServiceInformation* > *ServiceTypeIdentifier* tag for the
    service named "*Test ESTEID2025*".
  - `{TEST_of_RIA_TIMESTAMPING_AUTHORITY_2018_G1_PROOV_1_TYPE_IDENTIFIER}` as the value of *TrustServiceStatusList* >
    *TrustServiceProviderList* > *TrustServiceProvider* > *TSPServices* > *TSPService* > *ServiceInformation* >
    *ServiceTypeIdentifier* tag for the service named "*TEST of RIA TIMESTAMPING AUTHORITY 2018 G1 PROOV 1*".
  - `{TEST_of_RIA_OCSP_RESPONDER_2019_G1_TYPE_IDENTIFIER}` as the value of *TrustServiceStatusList* >
    *TrustServiceProviderList* > *TrustServiceProvider* > *TSPServices* > *TSPService* > *ServiceInformation* >
    *ServiceTypeIdentifier* tag for the service named "*TEST of RIA OCSP RESPONDER 2019 G1*".
