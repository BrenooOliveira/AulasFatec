
// ------------------------ Main para simular ------------------------
public class Main {
    public static void main(String[] args) {
        // Criando produtos
        Produto livro1 = new Produto("SKU001", "Java para Iniciantes", 50.0);
        Produto livro2 = new Produto("SKU002", "POO Avançado", 80.0);

        // Criando estoque
        EstoqueProduto estoque1 = new EstoqueProduto(livro1, 10);
        EstoqueProduto estoque2 = new EstoqueProduto(livro2, 5);

        estoque1.consultarEstoque();
        estoque2.consultarEstoque();

        // Criando usuário
        Usuario usuario = new Usuario("Breno");

        // Criando carrinho e adicionando produtos
        CarrinhoCompras carrinho = new CarrinhoCompras();
        usuario.criarCarrinho(carrinho);
        carrinho.inserirItem(livro1, 2);
        carrinho.inserirItem(livro2, 1);

        // Mostrando carrinho
        carrinho.mostrarCarrinho();

        // Criando venda
        Venda venda = new Venda(carrinho);
        Pagamento pagamento = new Pagamento("Cartão de Crédito", carrinho.getValorTotal());
        venda.adicionarPagamento(pagamento);

        // Finalizando venda
        venda.finalizarVenda();
    }
}
