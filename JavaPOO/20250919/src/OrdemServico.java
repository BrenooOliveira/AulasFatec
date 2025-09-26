
public class OrdemServico {
    private String id_os;
    private String id_cliente;
    private Double valor_total;
    private String status;

    public OrdemServico(String id_cliente, Double valor_total, String status) {
        this.id_cliente = id_cliente;
        this.valor_total = valor_total;
        this.status = status;
    }

    static void abrirOrdemServico(String id_cliente, Double valor_total, String status) {
        OrdemServico os = new OrdemServico(id_cliente, valor_total, status);
        System.out.println("Ordem de Serviço aberta com sucesso!");
    }

}
