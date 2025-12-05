``` mermaid
classDiagram

class ICancelamentoStrategy {
  <<interface>>
  +execute(PoliticaCancelModel, ReservaModel)
}

class CancelSemMulta
class CancelComMulta
class Estorno

CancelSemMulta ..|> ICancelamentoStrategy
CancelComMulta ..|> ICancelamentoStrategy
Estorno ..|> ICancelamentoStrategy


```
