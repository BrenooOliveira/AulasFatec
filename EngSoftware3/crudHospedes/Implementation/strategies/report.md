``` mermaid
classDiagram

class IRelatorioStrategy {
  <<interface>>
  +execute()
}

class RelatorioOcupacao
class RelatorioFinanceiro
class RelatorioOrigemReservas
class DesempenhoPromocoes

RelatorioOcupacao ..|> IRelatorioStrategy
RelatorioFinanceiro ..|> IRelatorioStrategy
RelatorioOrigemReservas ..|> IRelatorioStrategy
DesempenhoPromocoes ..|> IRelatorioStrategy


```
