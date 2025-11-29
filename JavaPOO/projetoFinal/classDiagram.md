```mermaid
---
r
config:
  theme: dark
---
classDiagram
direction LR
    class Livro {
	    -int idLivro
	    -String nomeLivro
	    -LocalDate dataCadastro
	    +getters()
	    +setters()
    }
    class Estoque {
	    -int idLivro
	    -int quantidade
	    +getters()
	    +setters()
    }
    class Emprestimo {
	    -int idEmprestimo
	    -int idLivro
	    -String nomeCliente
	    -String tipoCliente
	    -LocalDate dtEmprestimo
	    -LocalDate dtDevolucao
	    -String status
	    +getters()
	    +setters()
    }
    class LivroDAO {
	    +inserir(Livro, Estoque)
	    +atualizar(Livro, Estoque)
	    +listar() : List
	    +deletar(id)
    }
    class EmprestimoDAO {
	    +registrar(Emprestimo)
	    +listar()
    }
    class MultaStrategy {
	    +double calcularMulta(LocalDate devolucao)
    }
    class MultaAluno {
    }
    class MultaProfessor {
    }
    class BibliotecaFacade {
	    +adicionarLivro()
	    +atualizarLivro()
	    +deletarLivro()
	    +listarEstoque()
	    +registrarEmprestimo()
    }
    class HomeController {
	    +initialize()
	    +onClickAddLivro()
	    +onClickEditar()
	    +onClickDeletar()
	    +carregarTabela()
    }
    class HomeViewFXML {
    }

	<<interface>> MultaStrategy

    Livro "1" -- "1" Estoque : possui
    Livro "1" --o "N" Emprestimo : emprestado
    MultaAluno ..|> MultaStrategy
    MultaProfessor ..|> MultaStrategy
    LivroDAO ..|> Livro
    Livro --o BibliotecaFacade
    BibliotecaFacade --* HomeController
    EmprestimoDAO ..> Emprestimo
    Emprestimo --o BibliotecaFacade
    HomeController --> HomeViewFXML

```
