// ------------------------ Classe EstoqueProduto ------------------------
class EstoqueProduto {
    private Produto produto; // agregação
    private int qtdeAtual;

    public EstoqueProduto(Produto produto, int qtdeInicial) {
        this.produto = produto;
        this.qtdeAtual = qtdeInicial;
    }

    public void consultarEstoque() {
        System.out.println("Estoque de " + produto.getNome() + ": " + qtdeAtual);
    }

    public void atualizarQuantidade(int quantidade) {
        qtdeAtual += quantidade;
        System.out.println("Atualizado estoque de " + produto.getNome() + ": " + qtdeAtual);
    }
}
