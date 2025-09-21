import java.util.*;

// ------------------------ Classe CarrinhoCompras ------------------------
class CarrinhoCompras {
    private List<ItemCarrinho> itens = new ArrayList<>();
    private double valorTotal;

    public void inserirItem(Produto produto, int quantidade) {
        ItemCarrinho item = new ItemCarrinho(produto, quantidade);
        itens.add(item);
        System.out.println("Adicionado ao carrinho: " + quantidade + "x " + produto.getNome());
    }

    public void mostrarCarrinho() {
        System.out.println("\nItens no carrinho:");
        valorTotal = 0;
        for (ItemCarrinho item : itens) {
            item.mostrarItem();
            valorTotal += item.getSubtotal();
        }
        System.out.println("Valor total: R$ " + valorTotal);
    }

    public double getValorTotal() {
        return valorTotal;
    }
}
