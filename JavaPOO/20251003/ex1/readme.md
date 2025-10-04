# Cadastro de Marido de Aluguel
Crie um programa de cadastro de serviços de marido de aluguel. O prog deverá:

- Cadastrar serviços (eletrica,hidraulica,alarme). Com nome do prestador, tel e bairro
- Ver a lista de serviços cadastrados.
- Ver os preços de mao de obra/hora dos serviços (polimorfismo).

O sistema deve permitir entrada via console (Scanner) e aplicar herança, sobrecarga, sobrescrita e polimorfismo.

## Serviços é a classe mãe (superclasse).
- Eletrica, hidraulica, alarme são subclasses que herdam de Serviços.

## A classe Serviços possui:
- Um atributo protegido nome.
- O método valorhora() que é sobrescrito nas subclasses.
- Uma sobrecarga de valorhora(double).
- O método veiculo(), também sobrescrito. (carro, moto)
- Métodos auxiliares getNome() e getGenero().
