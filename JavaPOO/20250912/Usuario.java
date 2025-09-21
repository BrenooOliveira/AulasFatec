import java.util.*;

// ------------------------ Classe Usuario ------------------------
class Usuario {
    private String nome;
    private List<CarrinhoCompras> carrinhos = new ArrayList<>();

    public Usuario(String nome) {
        this.nome = nome;
    }

    public void criarCarrinho(CarrinhoCompras carrinho) {
        carrinhos.add(carrinho);
        System.out.println("Usuário " + nome + " criou um novo carrinho.");
    }
}
