import java.util.*;
// ------------------------ Classe Venda ------------------------
class Venda {
    private List<Pagamento> pagamentos = new ArrayList<>();
    private CarrinhoCompras carrinho;
    private Date dataVenda;

    public Venda(CarrinhoCompras carrinho) {
        this.carrinho = carrinho;
        this.dataVenda = new Date();
        System.out.println("Venda criada em: " + dataVenda);
    }

    public void adicionarPagamento(Pagamento pagamento) {
        pagamentos.add(pagamento);
    }

    public void finalizarVenda() {
        System.out.println("Finalizando venda com " + pagamentos.size() + " pagamento(s).");
        for (Pagamento p : pagamentos) {
            p.processarPagamento();
            System.out.println("Status do pagamento: " + p.getStatus());
        }
        System.out.println("Venda finalizada. Total: R$ " + carrinho.getValorTotal());
    }
}
