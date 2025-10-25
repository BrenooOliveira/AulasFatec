# 📚 Sistema de Gerenciamento de Biblioteca Digital

### Projeto Prático de POO em Java

---

## 🧩 Descrição do Projeto

O **Sistema de Gerenciamento de Biblioteca Digital** é uma aplicação desenvolvida em **Java** que implementa os principais conceitos de **Programação Orientada a Objetos (POO)** — **Encapsulamento, Herança, Polimorfismo e Abstração** — aplicados em um sistema completo de **CRUD** (Create, Read, Update, Delete).

O objetivo do sistema é **gerenciar livros físicos e digitais (Ebooks)**, bem como **controlar empréstimos** realizados por diferentes tipos de usuários (Alunos e Professores). O projeto utiliza o **padrão de arquitetura MVC (Model-View-Controller)** e **classes DAO** para persistência dos dados em banco de dados relacional.

---

## 🧠 Conceitos de POO Aplicados

| Conceito                  | Aplicação no Sistema                                                                                        |
| ------------------------- | ----------------------------------------------------------------------------------------------------------- |
| **Herança**               | Classe `Livro` (base) com subclasses `LivroFisico` e `Ebook`.                                               |
| **Polimorfismo**          | Método `calcularMulta()` implementado de forma diferente conforme o tipo de usuário (`Aluno`, `Professor`). |
| **Abstração / Interface** | Interface `GerenciadorEmprestimo` com métodos `emprestar()` e `devolver()`.                                 |
| **Encapsulamento**        | Uso de getters e setters nas entidades para proteger os atributos.                                          |

---

## 🏗️ Arquitetura do Sistema

O projeto segue o **padrão MVC**, composto por:

* **Model:** Contém as classes de entidade (`Livro`, `Usuario`, `Emprestimo`) e os DAOs (`LivroDAO`, `UsuarioDAO`, etc.).
* **View:** Interface gráfica desenvolvida com **JavaFX**, permitindo o cadastro, consulta e gerenciamento de empréstimos.
* **Controller:** Controla o fluxo entre a interface e as regras de negócio.

---

## 🗄️ Banco de Dados

* **Tecnologia:** SQLite (pode ser adaptado para MySQL ou PostgreSQL).
* **Camada de persistência:** Implementada via **JDBC** e **DAO Pattern**.
* **Entidades Principais:**

  * `Livro`
  * `Usuario`
  * `Emprestimo`

---

## ⚙️ Funcionalidades Principais

- ✅ Cadastro, consulta, atualização e exclusão de livros
- ✅ Empréstimo e devolução de exemplares
- ✅ Controle de estoque e disponibilidade
- ✅ Cálculo de multa por atraso (diferenciado por tipo de - usuário)
- ✅ Tratamento de exceções — `LivroNaoDisponivelException`
- ✅ Interface gráfica amigável com **JavaFX**

---

## 🧱 Tecnologias Utilizadas

* **Java 21+**
* **JavaFX 21**
* **JDBC**
* **SQLite (ou MySQL/PostgreSQL)**
* **Maven**
* **Padrão DAO**
* **Padrão MVC**

---

## 📦 Entregáveis

* Código-fonte completo
* Banco de dados com massa de testes
* Aplicação executável (rodando sem erros)
* Diagrama de Classes
* Dicionário de Dados
* Manual do Usuário em PDF

---

## 🚀 Execução

Para executar o projeto via Maven:

```bash
mvn clean javafx:run
```

---

## 🧾 Licença

Projeto acadêmico desenvolvido para fins educacionais na disciplina de **Programação Orientada a Objetos Intermediária em Java**.
