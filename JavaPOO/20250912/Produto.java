

// ------------------------ Classe Produto ------------------------
class Produto {
    private String sku;
    private String nome;
    private String categoria;
    private int numPaginas;
    private String tamanho;
    private String autor;
    private String descricao;
    private String imagem;
    private double custoUnitario;
    private double precoUnitario;

    public Produto(String sku, String nome, double precoUnitario) {
        this.sku = sku;
        this.nome = nome;
        this.precoUnitario = precoUnitario;
    }

    public void consultar() {
        System.out.println("Consultando produto: " + nome + " (R$ " + precoUnitario + ")");
    }

    public String getNome() {
        return nome;
    }

    public double getPrecoUnitario() {
        return precoUnitario;
    }
}
