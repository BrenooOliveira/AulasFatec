# Para cadastros:


HospedeRepository implements JpaRepository
QuartoRepository implements JpaRepository
PromoctaoRepository implements JpaRepository
PoliticaCancelRepository implements JpaRepository
ReservaRepository implements JpaRepository

# Modelos - Dados Obrigatorios
HospedeModel{
	String nome
	String cpf
	LocalDate dtNascimento
	String telefone
	String email // validar email
	String logradouro
}
QuartoModel{
	Integer numero
	Enum tpQuarto
	Integer capacidade
	Float precoBase
	Boolean disponivel
}

PromocaoModel{
	Enum status
	LocalDate dtInicio
	LocalDate dtFim
}

PoliticaCancelModel{
    LocalDate dtCriacao
    String descPolitica
    ENUM tpPolitica
    ENUM status
}

ReservaModel{
	String hospede
	String quarto
	LocalDate dtInicio
	LocalDate dtFim
	int quantidade
	Enum status // proposta, confirmada cancelada, em estadia
	Boolean noShow
	Float valorTotal
}

PagamentoModel{
    ReservaModel reserva
    String tpPgmt
    Float valor
    ENUM resultado
    LocalDate dtAtual

}

NotificacaoModel {
    ReservaModel reserva
    HospedeModel hospede
    QuartoModel quarto
    PoliticaCancelModel politicaCancel
}

LogsModel{
    LocalDate dtOperacao
    ReservaModel reserva // RNF0131
    PagamentoModel pagamento // RNF0131

}


# Services - Regras de Negócio
HospedeService{
    IValidacaoStrategy() // validar CPF e campos obrigatorios
    cadastrar(HospedeModel) // RF0101
    alterarCadastro(HospedeModel) // RF0102
    inativarCadastro(HospedeModel) // RF0103
    consultar(HospedeModel) // RF0104
}



QuartoService{
    IValidacaoStrategy() // validar campos obrigatorios

    cadastrar(QuartoModel) // RF0111
    alterarCadastro(QuartoModel) // RF0112
    inativarCadastro(QuartoModel) // RF0113
    consultar(QuartoModel) // RF0114
	getById(QuartoModel) // TODO: RF0201
}

PromocaoService{
    cadastrar(PromocaoModel) // RF0121
    alterarPromocao(PromocaoModel) // RF0122
    inativarPromocao(PromocaoModel) // RF0123
    consultar(PromocaoModel) // RF0124
}

PoliticaCancelService{
    cadastrar(PoliticaCancelModel) // RF0131
    alterarPolitica(PoliticaCancelModel) // RF0132
    inativarPolitica(PoliticaCancelModel) // RF0133
    consultar(PoliticaCancelModel) // RF0134

}

RelatorioService{
    exibirRelatorio(IRelatorioStrategy)
}



ReservaService{
    IValidacaoStrategy() // validar campos obrigatorios
	criarReserva() // RF0202
	confirmarReserva() // RF0203
	marcarNoShow() // RF0207
	registCheckIn() // RF0208
	registCheckOut() // RF0209


}

PagamentoService{
    IValidacaoStrategy() // validar campos obrigatorios
    iniciarPagamento(ReservaModel) // RF0211
    registrarResultado(ReservaModel) // RF0212
    estornarPagamento(ReservaModel) // RF0213
    consultarPagamentos(ReservaModel) // RF0211
}

NotificacaoService {
    enviarConfirmacao(NotificaoStrategy) // RF0221
    enviarConfirmacaoCancelamento(NotificaoStrategy) // RF0222
    enviarLembrete(NotificaoStrategy) // RF0223
    enviarPosEstadia(NotificaoStrategy) // RF0224

}

LogsService{
    loggar(LogsModel)

}

# Strategies
## Notificacao
IConteudoNotificacaoStrategy{
    execute(NotificacaoModel)
}
ConfirmarReserva implements IConteudoNotificacaoStrategy{}
CancelamentoReserva implements IConteudoNotificacaoStrategy{}
LembreteReserva implements IConteudoNotificacaoStrategy{}

## Pagamento
ITipoPagamentoStrategy{
    execute(PagamentoModel)
}
PgmtPix implements ITipoPagamentoStrategy{}
PgmtCartCredito implements ITipoPagamentoStrategy{}
PgmtCarDebito implements ITipoPagamentoStrategy{}

## Cancelmento
ICancelamentoStrategy{
    <<interface>>
    execute(PoliticaCancelModel)
}

CancelSemMulta implements ICancelamentoStrategy{}
CancelComMulta implements ICancelamentoStrategy{}
Estorno implements ICancelamentoStrategy{}

## Relatorios
IRelatorioStrategy{
    execute()
}
RelatorioOcupacao implements IRelatorioStrategy {}
RelatorioFinanceiro implements IRelatorioStrategy {}
RelatorioOrigemReservas implements IRelatorioStrategy {}
DesempenhoPromocoes implements IRelatorioStrategy {}

## Validacoes e Dados Obrigatorios
IValidacaoStrategy{
    <<interface>>
    execute()
}
// validacoes hospede
ValidarCamposHospede implements IValidacaoStrategy{}
ValidarUnicidadeCpf implements IValidacaoStrategy{}
ValidarEmailHospede implements IValidacaoStrategy{}
// validacoes de quarto
ValidarCamposQuarto implements IValidacaoStrategy{}
ValidarCapacidadeQuarto implements IValidacaoStrategy{}
ValidarLimiteQuarto implements IValidacaoStrategy{}
// validacoes de reserva
ValidarCamposReserva implements IValidacaoStrategy{}
ValidarJanelaDatas implements IValidacaoStrategy{}
ValidarDiariasFeriado implements IValidacaoStrategy{}
// validacoes de pagamento
ValidarCamposPagamento implements IValidacaoStrategy{}
ValidarRealizacaoPagamento implements IValidacaoStrategy{} // RN0222

PAREI NA RN0222
