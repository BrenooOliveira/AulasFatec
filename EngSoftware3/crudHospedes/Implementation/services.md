``` mermaid
---
config:
  layout: elk
---
classDiagram
direction RL

class HospedeService {
  +cadastrar(HospedeModel)
  +alterar(HospedeModel)
  +inativar(String)
  +consultar(filters)
}

class QuartoService {
  +cadastrar(QuartoModel)
  +alterar(QuartoModel)
  +inativar(Integer)
  +consultar(filters)
  +getById(String)
}

class PromocaoService {
  +cadastrar(PromocaoModel)
  +alterar(PromocaoModel)
  +inativar(String)
  +consultar(filters)
}

class PoliticaCancelService {
  +cadastrar(PoliticaCancelModel)
  +alterar(PoliticaCancelModel)
  +inativar(String)
  +consultar(filters)
}

class ReservaService {
  +criarReserva(ReservaModel)
  +confirmarReserva(String)
  +marcarNoShow(String)
  +registCheckIn(String)
  +registCheckOut(String)
  +consultar(filters)
}

class PagamentoService {
  +iniciarPagamento(ReservaModel)
  +registrarResultado(PagamentoModel)
  +estornarPagamento(String)
  +consultarPagamentos(filters)
}

class NotificacaoService {
  +enviarConfirmacao(String)
  +enviarConfirmacaoCancelamento(String)
  +enviarLembrete(String)
  +enviarPosEstadia(String)
}

class LogsService {
  +loggar(LogsModel)
}

class HospedeServiceImpl
HospedeServiceImpl --> HospedeRepository
HospedeServiceImpl --> IValidacaoStrategy
HospedeServiceImpl ..|> HospedeService

class QuartoServiceImpl
QuartoServiceImpl --> QuartoRepository
QuartoServiceImpl --> IValidacaoStrategy
QuartoServiceImpl ..|> QuartoService

class PromocaoServiceImpl
PromocaoServiceImpl --> PromocaoRepository
PromocaoServiceImpl --> IValidacaoStrategy
PromocaoServiceImpl ..|> PromocaoService

class PoliticaCancelServiceImpl
PoliticaCancelServiceImpl --> PoliticaCancelRepository
PoliticaCancelServiceImpl ..|> PoliticaCancelService

class ReservaServiceImpl
ReservaServiceImpl --> ReservaRepository
ReservaServiceImpl --> QuartoRepository
ReservaServiceImpl --> HospedeRepository
ReservaServiceImpl --> PoliticaCancelRepository
ReservaServiceImpl --> IValidacaoStrategy
ReservaServiceImpl --> NotificacaoService
ReservaServiceImpl --> LogsService
ReservaServiceImpl ..|> ReservaService

class PagamentoServiceImpl
PagamentoServiceImpl --> PagamentoRepository
PagamentoServiceImpl --> ITipoPagamentoStrategy
PagamentoServiceImpl --> LogsService
PagamentoServiceImpl ..|> PagamentoService

class NotificacaoServiceImpl
NotificacaoServiceImpl --> NotificacaoRepository
NotificacaoServiceImpl --> IConteudoNotificacaoStrategy
NotificacaoServiceImpl ..|> NotificacaoService

class LogsServiceImpl
LogsServiceImpl --> LogsRepository
LogsServiceImpl ..|> LogsService

```
