# Programa de Simulação de Votos
Este programa simula uma eleição com 60 votos. Ele utiliza o conceito de orientação a objetos para representar um Candidato e a UrnaEletronica, que gerencia a votação.

Conceitos importantes no código:

- Classe Candidato: Representa um candidato com nome e um contador de votos.

- Classe UrnaEletronica: Responsável por todo o processo de votação, incluindo a inicialização dos candidatos, a contagem dos votos e a exibição dos resultados.

- Encapsulamento: Os atributos das classes são privados (private), e o acesso e modificação são feitos através de métodos públicos (public), como getNome() e adicionarVoto(). Isso garante que os dados sejam manipulados de forma controlada.
