v1: 21/09/2025: Necessário estar em uma só classe.

# Requisitos do Cadastro de Cliente

## Requisitos Funcionais (RF)

- **RF01**: O sistema deve possibilitar o registro de clientes.
- **RF02**: O sistema deve possibilitar que dados de um cliente cadastrado sejam alterados.
- **RF03**: O sistema deve permitir que um cliente seja excluído.
- **RF04**: O sistema deve possibilitar a consulta de clientes.
- **RF05**: O sistema deve possibilitar que os dados dos clientes sejam visualizados.
- **RF06**: O sistema deve permitir que dependentes sejam associados a clientes.



## Regras de Negócio (RN)

- **RN01**: O sistema deve garantir que para um cliente ser cadastrado, todos os dados obrigatórios não estejam em branco.
  - Dados obrigatórios: **nome, CPF, crédito, endereço (logradouro, número, CEP, cidade e estado)**.

- **RN02**: O complemento do endereço não é um dado obrigatório.

- **RN03**: Todo cliente deve ter no máximo **dois dependentes associados**.

- **RN04**: Para o registro de dependentes, é necessário informar **nome e parentesco**.

- **RN05**: Os parentescos permitidos para dependentes são apenas: **FILHO(A)** e **CÔNJUGE**.

- **RN06**: O crédito de um cliente não pode ser **menor que R$ 1.000,00**.

- **RN07**: O CPF de um cliente deve ser válido.



## ⚙️ Requisitos Não Funcionais (RNF)

- **RNF01**: Para salvar um cliente, a **data de cadastro** deve ser associada ao registro.

- **RNF02**: Ao salvar um cliente, os **dados, data e hora da operação** devem ser persistidos em um **log de auditoria**.

- **RNF03**: Todo cliente salvo deve ter um **ID gerado automaticamente pelo sistema**.
