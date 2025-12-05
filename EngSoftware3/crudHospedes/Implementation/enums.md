``` mermaid
classDiagram

class StatusReserva { <<enumeration>> PROPOSTA CONFIRMADA CANCELADA EM_ESTADIA FINALIZADA NO_SHOW }
class TpQuarto { <<enumeration>> STANDARD DELUXE MASTER }
class TpPagamento { <<enumeration>> PIX CARTAO_CREDITO CARTAO_DEBITO }
class StatusPagamento { <<enumeration>> APROVADO NEGADO ESTORNADO }
class TipoNotificacao { <<enumeration>> CONFIRMACAO CANCELAMENTO LEMBRETE POS_ESTADIA }
class PromocaoStatus { <<enumeration>> ATIVA INATIVA EXPIRADA }
class TpPolitica { <<enumeration>> PADRAO_ESPECIAL }
class StatusPolitica { <<enumeration>> ATIVA INATIVA }


```
