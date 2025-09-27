import java.time.LocalDate;

public class OrdemServico {
    private String id_os;
    private int id_cliente;
    private Double valor_total;
    private String status;
    private String prev_entrega;

    public OrdemServico(int id_cliente, Double valor_total, String status,String prev_entrega) {
        this.id_cliente = id_cliente;
        this.valor_total = valor_total;
        this.status = status;
        this.prev_entrega = prev_entrega;
    }

    static int abrirOrdemServico(int id_cliente, Double valor_total, String prev_entrega) {
        String status = "Aberto";
        String criado_em = LocalDate.now().toString();

        OrdemServicoDAO.criarTabela();
        int id_os_gerado = OrdemServicoDAO.insertInto(id_cliente, valor_total, status, criado_em, prev_entrega);

        if (id_os_gerado != -1) {
            System.out.println("Ordem de Serviço aberta com sucesso! ID: " + id_os_gerado);
        } else {
            System.out.println("Erro ao abrir ordem de serviço.");
        }

        return id_os_gerado;
    }

    public static void consultarOSCliente(int id_cliente) {
        OrdemServicoDAO.getOSperClient(id_cliente);;
    }



}
