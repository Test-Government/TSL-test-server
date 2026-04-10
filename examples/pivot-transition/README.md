# Example files for simulating the transition period before pivot reset

This directory contains example files for simulating the transition period before the reset of the chain of pivot LOTLs.

Configurations files:
* [`chain-4-no-overlap.yaml`](chain-4-no-overlap.yml) - Serves a chain of 4 LOTLs: 3 previous pivot LOTLs and a
  currently active LOTL.
  No overlap between the first set of LOTL signing certificates (the first pivot LOTL after the first OJ URI) and the
  set used after the second OJ URI.
* [`chain-4-overlapped.yaml`](chain-4-overlapped.yml) - Serves a chain of 4 LOTLs: 3 previous pivot LOTLs and a
  currently active LOTL.
  There is overlap between the first set of LOTL signing certificates (the first pivot LOTL after the first OJ URI)
  and the set used after the second OJ URI.
  The pivot LOTL after the second OJ URI has been signed by a certificate that is present in the initial set of signing
  certificates.

Keystores and truststores:
* Keystore `numbered-signers.keystore.p12` contains various key-pairs and certificates for signing different LOTLs.
* Truststores `numbered-signers*.truststore.p12` contains trusted certificates of various signers.
  The truststore without numerical suffix contains all signers; numbered truststores contain only specified signers.
* All passwords are `changeit`.
