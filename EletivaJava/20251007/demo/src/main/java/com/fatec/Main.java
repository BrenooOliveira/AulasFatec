package com.fatec;

import java.util.List;

import com.fatec.dao.ProdutoDAO;
import com.fatec.model.Produto;
import com.fatec.dao.ProdutoDAOimpl;


public class Main {
    public static void main(String[] args) {
        ProdutoDAO dao = new ProdutoDAOimpl();

        // Produto novoProduto = new Produto("Notebook", 3500.00, 10);
        //dao.salvar(novoProduto);

        List<Produto> produtos = dao.buscarTodos();
        for (Produto p : produtos) {
            System.out.println(p.getId() + " - " + p.getNome() +
             " - R$" + p.getPreco() + " - Estoque: " + p.getEstoque());
        }

        dao.transferirEstoque(1, 2, 5); // Transferir 5 unidades do produto com ID 1 para o produto com ID 2

        // Agora busca novamente para pegar os valores atualizados
        produtos = dao.buscarTodos();

        for (Produto p : produtos) {
            System.out.println(p.getId() + " - " + p.getNome() +
             " - R$" + p.getPreco() + " - Estoque: " + p.getEstoque());
        }

    }
}
