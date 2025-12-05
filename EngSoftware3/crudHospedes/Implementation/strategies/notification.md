``` mermaid
classDiagram

class IConteudoNotificacaoStrategy {
  <<interface>>
  +execute(NotificacaoModel)
}

class ConfirmarReserva
class CancelamentoReserva
class LembreteReserva

ConfirmarReserva ..|> IConteudoNotificacaoStrategy
CancelamentoReserva ..|> IConteudoNotificacaoStrategy
LembreteReserva ..|> IConteudoNotificacaoStrategy


```
