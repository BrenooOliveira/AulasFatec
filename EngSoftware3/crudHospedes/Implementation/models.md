``` mermaid
classDiagram
direction TB

class HospedeModel {
  +String id
  +String nome
  +String cpf
  +LocalDate dtNascimento
  +String telefone
  +String email
  +String logradouro
  +Boolean ativo
}

class QuartoModel {
  +String id
  +Integer numero
  +TpQuarto tpQuarto
  +Integer capacidade
  +Float precoBase
  +Boolean disponivel
}

class PromocaoModel {
  +String id
  +PromocaoStatus status
  +LocalDate dtInicio
  +LocalDate dtFim
}

class PoliticaCancelModel {
  +String id
  +LocalDate dtCriacao
  +String descPolitica
  +TpPolitica tpPolitica
  +StatusPolitica status
}

class ReservaModel {
  +String id
  +HospedeModel hospede
  +QuartoModel quarto
  +LocalDate dtInicio
  +LocalDate dtFim
  +int quantidade
  +StatusReserva status
  +Boolean noShow
  +Float valorTotal
}

class PagamentoModel {
  +String id
  +ReservaModel reserva
  +TpPagamento tpPgmt
  +Float valor
  +StatusPagamento resultado
  +LocalDate dtAtual
}

class NotificacaoModel {
  +String id
  +ReservaModel reserva
  +HospedeModel hospede
  +QuartoModel quarto
  +PoliticaCancelModel politicaCancel
  +TipoNotificacao tipo
  +LocalDate dtEnvio
}

class LogsModel {
  +String id
  +LocalDate dtOperacao
  +String usuario
  +ReservaModel reserva
  +PagamentoModel pagamento
  +String operacao
}

HospedeModel "1" --o "0..*" ReservaModel
QuartoModel "1" --o "0..*" ReservaModel
PoliticaCancelModel "1" --o "0..*" ReservaModel : aplica
ReservaModel "1" --> "0..*" NotificacaoModel
ReservaModel "0..1" --o "1" PromocaoModel
ReservaModel "1" --> "0..*" PagamentoModel
ReservaModel "1" <.. "0..*" LogsModel


```
