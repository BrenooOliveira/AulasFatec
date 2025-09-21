// ------------------------ Classe ItemCarrinho ------------------------
class ItemCarrinho {
    private Produto produto; // composição
    private int quantidade;

    public ItemCarrinho(Produto produto, int quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public void mostrarItem() {
        System.out.println(quantidade + "x " + produto.getNome() + " (R$ " + produto.getPrecoUnitario() + ")");
    }

    public double getSubtotal() {
        return quantidade * produto.getPrecoUnitario();
    }
}
