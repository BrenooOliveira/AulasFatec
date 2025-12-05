``` mermaid
classDiagram
direction LR

class HospedeController {
  +cadastrar(HospedeModel)
  +alterar(HospedeModel)
  +inativar(String id)
  +consultar(...)
}

class QuartoController {
  +cadastrar(QuartoModel)
  +alterar(QuartoModel)
  +inativar(String id)
  +consultar(...)
}

class PromocaoController {
  +cadastrar(PromocaoModel)
  +alterar(PromocaoModel)
  +inativar(String id)
  +consultar(...)
}

class PoliticaCancelController {
  +cadastrar(PoliticaCancelModel)
  +alterar(PoliticaCancelModel)
  +inativar(String id)
  +consultar(...)
}

class ReservaController {
  +consultarDisponibilidade(...)
  +criarReserva(ReservaModel)
  +confirmarReserva(String id)
  +alterarReserva(ReservaModel)
  +cancelarReserva(String id)
  +marcarNoShow(String id)
  +checkIn(String id)
  +checkOut(String id)
}

class PagamentoController {
  +iniciarPagamento(ReservaModel)
  +registrarResultado(PagamentoModel)
  +estornarPagamento(String id)
  +consultarPagamentos(...)
}

class NotificacaoController {
  +enviarConfirmacao(Reserva reserva)
  +enviarCancelamento(Reserva reserva)
  +enviarLembrete(Reserva reserva)
  +enviarPosEstadia(Reserva reserva)
}

class RelatorioController {
  +relatorioOcupacao(...)
  +relatorioFinanceiro(...)
  +relatorioOrigemReservas(...)
  +desempenhoPromocoes(...)
}

%% Ligações

HospedeController --> HospedeService
QuartoController --> QuartoService
PromocaoController --> PromocaoService
PoliticaCancelController --> PoliticaCancelService

ReservaController --> ReservaService
PagamentoController --> PagamentoService

NotificacaoController --> NotificacaoService
RelatorioController --> RelatorioService


```
