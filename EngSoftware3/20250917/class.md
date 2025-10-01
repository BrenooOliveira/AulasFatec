``` mermaid
classDiagram
    class Cliente {
        - idCliente
        - nome
        - cpf
        - credito
        - dataCadastro
        --
        %% Endereço
        - logradouro
        - numero
        - cep
        - cidade
        - estado
        - complemento
        --
        %% Dependentes (mapa: parentesco -> nome)
        - dependentes : HashMap<String,String>
        --
        + createClient()
        + readClient(idCliente)
        + readClientDetails(idCliente)
        + updateClient(idCliente)
        + deleteClient(idCliente)
        + associateDependent(parentesco, nome)
        + insertCredit(valor)
        + validateCpf(cpf)
        + logTimestamp()
    }
```
