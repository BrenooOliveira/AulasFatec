``` mermaid
classDiagram
  class Estado {
    +uf: String
    +nome: String
  }

  class Cidade {
    +nome: String
  }

  class Logradouro {
    +cep: String
    +bairro: String
    +rua: String
  }

  class Hospede {
    +idHospede: int
    +nome: String
    +sobrenome: String
    +cpf: String
    +dtNascimento: Date
    +telefone: String
    +email: String
    +numero: String
    ---
    +validarEmail()
    +validarCpf()
    +create()
    +read()
    +update()
    +delete()
  }

  class Quarto {
    +idQuarto: int
    +numero: String
    +tipo: String
    +capacidadeAdultos: int
    +capacidadeCriancas: int
    +status: String
    +precoDiaria: double
    ---
    +validarCapacidade()
    +consultarDisponibilidade()
    +create()
    +update()
    +delete()
  }

  class Promocao {
    +idPromo: int
    +descricao: String
    +inicio: Date
    +fim: Date
    +percentual: double
    +ativa: boolean
    ---
    +create()
    +update()
    +delete()
  }

  class PoliticaCancelamento {
    +idPolitica: int
    +descricao: String
    +ativa: boolean
    ---
    +create()
    +update()
    +delete()
  }

  class Reserva {
    +idReserva: int
    +dataCheckin: Date
    +dataCheckout: Date
    +qtdAdultos: int
    +qtdCriancas: int
    +status: String
    +valorTotal: double
    ---
    +validarLimite()
    +validarDatas()
    +registrarCheckin()
    +registrarCheckout()
    +marcarNoShow()
    +confirmar()
    +create()
    +update()
    +delete()
  }

  class Pagamento {
    +idPagamento: int
    +forma: String
    +valor: double
    +dataOperacao: Date
    +status: String
    ---
    +iniciar()
    +registrarResultado()
    +estornar()
    +consultar()
  }

  class Notificacao {
    +idNotif: int
    +tipo: String
    +conteudo: String
    +dataEnvio: Date
    ---
    +enviarConfirmacao()
    +enviarCancelamento()
    +enviarLembrete()
    +enviarPosEstadia()
  }

  class Relatorio {
    +gerarOcupacao()
    +gerarFinanceiro()
    +gerarPromocoes()
    +gerarOrigemReservas()
  }

  class LogAuditoria {
    +idLog: int
    +user: String
    +operacao: String
    +timestamp: Date
    ---
    +registrar()
  }

  %% RELACIONAMENTOS
  Hospede "1" *-- "1" Logradouro : composição
  Logradouro "1" *-- "1" Cidade : composição
  Cidade "1" *-- "1" Estado : composição

  Reserva "1" --> "1" Hospede : associação
  Reserva "1" --o "1..*" Quarto : agregação
  Reserva "1" --> "0..1" Promocao : dependência
  Reserva "1" --> "1" PoliticaCancelamento : associação obrigatória
  Reserva "1" --> "1" Pagamento : associação
  Reserva "1" --> "0..*" Notificacao : dependência
  Reserva "1" --> "0..*" LogAuditoria : dependência

  Relatorio ..> Reserva : depende de dados
  Relatorio ..> Pagamento
  Relatorio ..> Promocao

```
