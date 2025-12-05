``` mermaid
classDiagram
direction TB

class JpaRepository { <<interface>> }

class HospedeRepository {
  <<interface>>
}
HospedeRepository ..|> JpaRepository

class QuartoRepository {
  <<interface>>
}
QuartoRepository ..|> JpaRepository

class PromocaoRepository {
  <<interface>>
}
PromocaoRepository ..|> JpaRepository

class PoliticaCancelRepository {
  <<interface>>
}
PoliticaCancelRepository ..|> JpaRepository

class ReservaRepository {
  <<interface>>
}
ReservaRepository ..|> JpaRepository

class PagamentoRepository {
  <<interface>>
}
PagamentoRepository ..|> JpaRepository

class NotificacaoRepository {
  <<interface>>
}
NotificacaoRepository ..|> JpaRepository

class LogsRepository {
  <<interface>>
}
LogsRepository ..|> JpaRepository


```
