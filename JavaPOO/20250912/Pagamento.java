// ------------------------ Classe Pagamento ------------------------
class Pagamento {
    private String tipoPagamento;
    private double valorCompra;
    private String status;

    public Pagamento(String tipoPagamento, double valorCompra) {
        this.tipoPagamento = tipoPagamento;
        this.valorCompra = valorCompra;
        this.status = "Pendente";
    }

    public void processarPagamento() {
        System.out.println("Processando pagamento de R$ " + valorCompra + " via " + tipoPagamento);
        status = "Confirmado";
    }

    public String getStatus() {
        return status;
    }
}
