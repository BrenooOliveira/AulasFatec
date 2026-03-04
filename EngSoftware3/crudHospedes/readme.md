# Documento de REQUISITOS — Sistema de Reserva de Hotel
    Essa aplicação deve implementar todos os padrões aprendidos em aula. Deveremos ter um diagrama de classes de domínio e de implementação, além de um diagrama de sequência para o cenário de inclusão.

## Requisitos Funcionais

### **Grupo: Cadastro de Hóspedes**

| ID     | Nome                         | Descrição                                                                                      |
| ------ | ---------------------------- | ---------------------------------------------------------------------------------------------- |
| RF0101 | Cadastrar hóspede            | O sistema deve possibilitar o cadastro de hóspedes.                                            |
| RF0102 | Alterar cadastro de hóspede  | O sistema deve possibilitar a alteração de dados cadastrais de hóspedes.                       |
| RF0103 | Inativar cadastro de hóspede | O sistema deve possibilitar a inativação de hóspedes.                                          |
| RF0104 | Consultar hóspedes           | O sistema deve possibilitar a consulta de hóspedes a partir de filtros definidos pelo usuário. |

---

### **Grupo: Cadastro de Quartos**

| ID     | Nome                        | Descrição                                                                                     |
| ------ | --------------------------- | --------------------------------------------------------------------------------------------- |
| RF0111 | Cadastrar quarto            | O sistema deve possibilitar o cadastro de quartos do hotel.                                   |
| RF0112 | Alterar cadastro de quarto  | O sistema deve possibilitar a alteração de dados de quartos.                                  |
| RF0113 | Inativar cadastro de quarto | O sistema deve possibilitar a inativação de quartos.                                          |
| RF0114 | Consultar quartos           | O sistema deve possibilitar a consulta de quartos a partir de filtros definidos pelo usuário. |

---

### **Grupo: Gestão de Promoções**

| ID     | Nome                | Descrição                                                                 |
| ------ | ------------------- | ------------------------------------------------------------------------- |
| RF0121 | Cadastrar promoção  | O sistema deve possibilitar o cadastro de promoções.                      |
| RF0122 | Alterar promoção    | O sistema deve possibilitar a alteração de promoções.                     |
| RF0123 | Inativar promoção   | O sistema deve possibilitar a inativação de promoções.                    |
| RF0124 | Consultar promoções | O sistema deve possibilitar a consulta de promoções vigentes e histórico. |

---

### **Grupo: Políticas de Cancelamento**

| ID     | Nome                                | Descrição                                                              |
| ------ | ----------------------------------- | ---------------------------------------------------------------------- |
| RF0131 | Cadastrar política de cancelamento  | O sistema deve possibilitar o cadastro de políticas de cancelamento.   |
| RF0132 | Alterar política de cancelamento    | O sistema deve possibilitar a alteração de políticas de cancelamento.  |
| RF0133 | Inativar política de cancelamento   | O sistema deve possibilitar a inativação de políticas de cancelamento. |
| RF0134 | Consultar políticas de cancelamento | O sistema deve possibilitar a consulta de políticas de cancelamento.   |

---

### **Grupo: Reservas (Disponibilidade, Criação, Alteração, Cancelamento)**

| ID     | Nome                      | Descrição                                                                                                            |
| ------ | ------------------------- | -------------------------------------------------------------------------------------------------------------------- |
| RF0201 | Consultar disponibilidade | O sistema deve possibilitar a consulta de disponibilidade de quartos a partir de parâmetros informados pelo usuário. |
| RF0202 | Criar reserva (proposta)  | O sistema deve possibilitar a criação de reserva com status inicial de proposta.                                     |
| RF0203 | Confirmar reserva         | O sistema deve possibilitar a confirmação de reserva.                                                                |
| RF0204 | Alterar reserva           | O sistema deve possibilitar a alteração de reservas.                                                                 |
| RF0205 | Cancelar reserva          | O sistema deve possibilitar o cancelamento de reservas.                                                              |
| RF0206 | Consultar reservas        | O sistema deve possibilitar a consulta de reservas por filtros (período, status, hóspede, quarto, canal).            |
| RF0207 | Marcar no-show            | O sistema deve possibilitar a marcação de reservas como no-show.                                                     |
| RF0208 | Registrar check-in        | O sistema deve possibilitar o registro de check-in de reservas confirmadas.                                          |
| RF0209 | Registrar check-out       | O sistema deve possibilitar o registro de check-out de reservas em estadia.                                          |

---

### **Grupo: Pagamentos**

| ID     | Nome                             | Descrição                                                                                         |
| ------ | -------------------------------- | ------------------------------------------------------------------------------------------------- |
| RF0211 | Iniciar pagamento                | O sistema deve possibilitar a iniciação de pagamentos associados à reserva.                       |
| RF0212 | Registrar resultado de pagamento | O sistema deve possibilitar o registro do resultado do pagamento (aprovado/negado).               |
| RF0213 | Estornar pagamento               | O sistema deve possibilitar o registro de estorno de pagamentos associados a reservas canceladas. |
| RF0214 | Consultar pagamentos             | O sistema deve possibilitar a consulta de pagamentos por filtros.                                 |

---

### **Grupo: Notificações**

| ID     | Nome                               | Descrição                                                                |
| ------ | ---------------------------------- | ------------------------------------------------------------------------ |
| RF0221 | Enviar confirmação de reserva      | O sistema deve enviar notificação de confirmação de reserva ao hóspede.  |
| RF0222 | Enviar confirmação de cancelamento | O sistema deve enviar notificação de cancelamento de reserva ao hóspede. |
| RF0223 | Enviar lembretes de estadia        | O sistema deve enviar notificações relacionadas a lembretes de estadia.  |
| RF0224 | Enviar mensagens pós-estadia       | O sistema deve enviar notificações pós-estadia.                          |

---

### **Grupo: Relatórios e Análise**

| ID     | Nome                            | Descrição                                                                             |
| ------ | ------------------------------- | ------------------------------------------------------------------------------------- |
| RF0231 | Relatório de ocupação           | O sistema deve disponibilizar relatório de ocupação por período e por tipo de quarto. |
| RF0232 | Relatório financeiro            | O sistema deve disponibilizar relatório financeiro por período e forma de pagamento.  |
| RF0233 | Relatório de origem de reservas | O sistema deve disponibilizar relatório por canal de origem de reservas.              |
| RF0234 | Desempenho de promoções         | O sistema deve disponibilizar relatório de desempenho de promoções.                   |

---

## Requisitos Não Funcionais

### **Grupo: Desempenho e Disponibilidade**

| ID      | Nome                                      | Descrição                                                          |
| ------- | ----------------------------------------- | ------------------------------------------------------------------ |
| RNF0101 | Tempo de resposta para disponibilidade    | A consulta de disponibilidade deve ter resposta em até 3 segundos. |
| RNF0102 | Tempo de resposta para criação de reserva | A criação de reserva deve ter resposta em até 5 segundos.          |
| RNF0103 | Disponibilidade do sistema                | O sistema deve manter disponibilidade mínima de 99,9% ao mês.      |

---

### **Grupo: Segurança e Conformidade**

| ID      | Nome                         | Descrição                                                                            |
| ------- | ---------------------------- | ------------------------------------------------------------------------------------ |
| RNF0111 | Comunicação segura           | Toda comunicação deve ocorrer via HTTPS.                                             |
| RNF0112 | Armazenamento de credenciais | Senhas devem ser armazenadas com hash e salt.                                        |
| RNF0113 | Privacidade e LGPD           | O sistema deve atender aos princípios da LGPD, incluindo consentimento e finalidade. |

---

### **Grupo: Usabilidade e Acesso**

| ID      | Nome                  | Descrição                                                              |
| ------- | --------------------- | ---------------------------------------------------------------------- |
| RNF0121 | Interface responsiva  | A aplicação deve ser responsiva para desktop e dispositivos móveis.    |
| RNF0122 | Suporte a navegadores | O sistema deve suportar Chrome, Firefox e Edge nas versões suportadas. |
| RNF0123 | Internacionalização   | O sistema deve disponibilizar interface em português e inglês.         |

---

### **Grupo: Observabilidade e Auditoria**

| ID      | Nome                   | Descrição                                                                                                        |
| ------- | ---------------------- | ---------------------------------------------------------------------------------------------------------------- |
| RNF0131 | Auditoria de operações | Registro de data, hora, usuário e operação para eventos de criação, alteração e status de reservas e pagamentos. |
| RNF0132 | Métricas e alertas     | O sistema deve expor métricas e configurar alertas para falhas e degradação de desempenho.                       |

---

### **Grupo: Integrações**

| ID      | Nome                           | Descrição                                                               |
| ------- | ------------------------------ | ----------------------------------------------------------------------- |
| RNF0141 | Resiliência de integrações     | Chamadas externas devem implementar retentativa e circuito de proteção. |
| RNF0142 | Disponibilidade de integrações | Falhas externas não devem impedir operações internas independentes.     |

---

## Regras de Negócio

### **Grupo: Composição e obrigatoriedade de dados**

| ID     | Nome                            | Descrição                                                            |
| ------ | ------------------------------- | -------------------------------------------------------------------- |
| RN0201 | Dados obrigatórios do hóspede   | Nome, CPF, data de nascimento, telefone, e-mail e endereço composto por: Logradouro, Número, CEP, Bairro, Complemento, Cidade e Estado|
| RN0202 | Unicidade de CPF                | O CPF deve ser único.                                                |
| RN0203 | Dados obrigatórios do quarto    | Número, tipo, capacidade e preço-base.                               |
| RN0204 | Dados obrigatórios da reserva   | Hóspede, quarto, datas, quantidades, status e valor total.           |
| RN0205 | Dados obrigatórios de pagamento | Reserva, forma de pagamento, valor e data.                           |

---

### **Grupo: Validações de conteúdo**

| ID     | Nome                         | Descrição                                        |
| ------ | ---------------------------- | ------------------------------------------------ |
| RN0211 | E-mail válido do hóspede     | Deve estar em formato válido.                    |
| RN0212 | Janela de datas válida       | A data de saída deve ser posterior à de entrada. |
| RN0213 | Capacidade do quarto         | Adultos + crianças não pode exceder capacidade.  |
| RN0214 | Mínimo de diárias em feriado | Mínimo de 2 diárias.                             |
| RN0215 | Limite padrão por quarto     | Máximo: 2 adultos e 2 crianças.                  |

---

### **Grupo: Regras operacionais de reserva**

| ID     | Nome                                 | Descrição                                                |
| ------ | ------------------------------------ | -------------------------------------------------------- |
| RN0221 | Horário de diárias                   | Check-in 14:00, check-out 12:00.                         |
| RN0222 | Confirmação condicionada a pagamento | Somente com pagamento aprovado.                          |
| RN0223 | No-show                              | Reservas não realizadas devem ser marcadas como no-show. |
| RN0224 | Contabilização de ocupação           | Só reservas confirmadas compõem relatórios.              |

---

### **Grupo: Cancelamento e política**

| ID     | Nome                   | Descrição                      |
| ------ | ---------------------- | ------------------------------ |
| RN0231 | Cancelamento sem multa | Até 48h antes da entrada.      |
| RN0232 | Cancelamento com multa | 50% do valor com menos de 48h. |
| RN0233 | Estorno proporcional   | Conforme política vinculada.   |

---

### **Grupo: Benefícios e exceções**

| ID     | Nome                  | Descrição                                    |
| ------ | --------------------- | -------------------------------------------- |
| RN0241 | Crianças até 5 anos   | Não pagam diária no quarto dos responsáveis. |
| RN0242 | Promoções aplicáveis  | Não cumulativas por padrão.                  |
| RN0243 | Documento no check-in | Documento com foto obrigatório.              |

---

### **Grupo: Notificações (conteúdo mínimo)**

| ID     | Nome                               | Descrição                                                                                                         |
| ------ | ---------------------------------- | ----------------------------------------------------------------------------------------------------------------- |
| RN0251 | Conteúdo de confirmação de reserva | Código da reserva, dados do hóspede, datas, identificação do quarto, valor total, política aplicada e instruções. |
| RN0252 | Conteúdo de cancelamento           | Código da reserva, valores cobrados/estornados e referência à política aplicada.                                  |
| RN0253 | Conteúdo de lembrete               | Data e hora de check-in, endereço do hotel e contatos.                                                            |

---
