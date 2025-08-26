# Banco API (Desafio)
Implementa /conta e /transacao conforme especificação (taxas D=3%, C=5%, P=0).

## Rodar
- Java 17 e Maven
- `mvn spring-boot:run`

## Exemplos
POST /conta  -> 201  { "numero_conta": 234, "saldo": 180.37 }
GET  /conta?numero_conta=234 -> 200 { "numero_conta": 234, "saldo": 180.37 }
POST /transacao {"forma_pagamento":"DEBITO","numero_conta":234,"valor":10} -> 201 { "numero_conta":234, "saldo":170.07 }
