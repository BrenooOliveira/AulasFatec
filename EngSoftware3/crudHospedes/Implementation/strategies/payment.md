``` mermaid
classDiagram

class ITipoPagamentoStrategy {
  <<interface>>
  +execute(PagamentoModel)
}

class PgmtPix
class PgmtCartCredito
class PgmtCarDebito

PgmtPix ..|> ITipoPagamentoStrategy
PgmtCartCredito ..|> ITipoPagamentoStrategy
PgmtCarDebito ..|> ITipoPagamentoStrategy


```
