``` mermaid
---
config:
  layout: elk
---
classDiagram
direction LR

class IValidacaoStrategy {
  <<interface>>
  +execute(Object)
}

%% validações de hóspede
class ValidarCamposHospede
class ValidarUnicidadeCpf
class ValidarEmailHospede
class ValidarIdadeDependentes

%% validações de quarto
class ValidarCamposQuarto
class ValidarCapacidadeQuarto
class ValidarLimiteQuarto

%% validações de reserva
class ValidarCamposReserva
class ValidarJanelaDatas
class ValidarDiariasFeriado
class ValidarDocumentoCheckin

%% validações de pagamento
class ValidarCamposPagamento
class ValidarRealizacaoPagamento

%% validações de promoção
class ValidarPromocoesAplicaveis

%% herança
ValidarCamposHospede ..|> IValidacaoStrategy
ValidarUnicidadeCpf ..|> IValidacaoStrategy
ValidarEmailHospede ..|> IValidacaoStrategy
ValidarIdadeDependentes ..|> IValidacaoStrategy

ValidarCamposQuarto ..|> IValidacaoStrategy
ValidarCapacidadeQuarto ..|> IValidacaoStrategy
ValidarLimiteQuarto ..|> IValidacaoStrategy

ValidarCamposReserva ..|> IValidacaoStrategy
ValidarJanelaDatas ..|> IValidacaoStrategy
ValidarDiariasFeriado ..|> IValidacaoStrategy
ValidarDocumentoCheckin ..|> IValidacaoStrategy

ValidarCamposPagamento ..|> IValidacaoStrategy
ValidarRealizacaoPagamento ..|> IValidacaoStrategy

ValidarPromocoesAplicaveis ..|> IValidacaoStrategy

```
