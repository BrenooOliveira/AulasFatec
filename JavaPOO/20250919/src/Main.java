public class Main {
    public static void main(String[] args) {
        EstoquePecasDAO.dropTable();
        EstoquePecasDAO.criarTabela(); // cria tabela, se nao existir

        // insere alguns produtos
        EstoquePecasDAO.inserir("peca1",1,1);
        EstoquePecasDAO.inserir("peca2",2,2);

        // lista os produtos
        EstoquePecasDAO.listar();
    }
}
