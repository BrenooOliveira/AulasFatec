Crie uma classe chamada formasgeometrica
ela tera os metodos area , perimetro, volume

em seguida, você deve criar as seguintes classes que herdam os atributos de formageometrica :

retangulo
circulo
quadrado
pentagono
hexagono
triangulo

Cada objeto deverá ter seu método para calcular a área e o perímetro.

# Arquitetura proposta:

```mermaid
classDiagram
    FormaGeometrica <|-- Retangulo
    FormaGeometrica <|-- Circulo
    FormaGeometrica <|-- Cubo
    FormaGeometrica <|-- Cilindro

    Forma2D <|.. Retangulo
    Forma2D <|.. Circulo

    Forma3D <|.. Cubo
    Forma3D <|.. Cilindro

    Forma2D : +area()
    Forma2D : +perimetro()

    Forma3D : +area()
    Forma3D : +volume()
```
