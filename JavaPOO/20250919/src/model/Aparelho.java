import enums.StatusAparelho;

public class Aparelho {
    private int id_aparelho;
    private int id_cliente;
    private String tipo;
    private String marca;
    private String modelo;
    private String imei;

    public Aparelho(String tipo, String marca, String modelo, String imei) {
        this.tipo = tipo;
        this.marca = marca;
        this.modelo = modelo;
        this.imei = imei;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public static void cadastrarAparelho(int id_cliente, String marca, String modelo, String categoria ,String imei) {
        AparelhoDAO.criarTabela();
        StatusAparelho status = StatusAparelho.NOVO; // status inicial sempre normalizado

        int id_gerado = AparelhoDAO.insertInto(id_cliente, marca, modelo,categoria, imei,status.toString());

        if (id_gerado != -1) {
            System.out.println("Aparelho cadastrado com sucesso! ID: " + id_gerado);
        } else {
            System.out.println("Erro ao cadastrar aparelho.");
        }
    }
}
