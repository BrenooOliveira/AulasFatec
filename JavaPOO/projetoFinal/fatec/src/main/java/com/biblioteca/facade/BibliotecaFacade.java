package com.biblioteca.facade;

import com.biblioteca.dao.LivroDAO;
import com.biblioteca.model.*;

import java.time.LocalDate;
import java.util.List;

public class BibliotecaFacade {

    private final LivroDAO livroDAO = new LivroDAO();

    public void adicionarLivro(String nome, int qtd) {
        Livro l = new Livro();
        l.setNomeLivro(nome);
        l.setDataCadastro(LocalDate.now());

        Estoque e = new Estoque();
        e.setQuantidade(qtd);

        livroDAO.inserir(l, e);
    }

    public void atualizarLivro(int id, String nome, int qtd) {
        livroDAO.atualizar(id, nome, qtd);
    }

    public void deletarLivro(int id) {
        livroDAO.deletar(id);
    }

    public List<Object[]> listarEstoque() {
        return livroDAO.listar();
    }
}
