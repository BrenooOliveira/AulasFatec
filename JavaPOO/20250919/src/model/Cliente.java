public class Cliente {
    private int id_cliente;
    private String nome;
    private String telefone;
    private String email;

    public Cliente(String nome, String telefone, String email) {
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
    }

    public static int cadastrarCliente(String nome, String telefone, String email) {
        ClienteDAO.criarTabela();
        int id_gerado = ClienteDAO.insertInto(nome, telefone, email);

        if (id_gerado != -1) {
            System.out.println("Cliente cadastrado com sucesso! ID: " + id_gerado);
            return id_gerado;
        } else {
            System.out.println("Erro ao cadastrar cliente.");
        }
        return -1; // se retornar -1 deu erro
    }

    public void associarAparelho(Aparelho aparelho) {
        aparelho.setId_cliente(this.id_cliente);
        aparelho.cadastrarAparelho();
    }

    public static void visualizarClienteEspecifico(int id_cliente) {
        ClienteDAO.listSpecificClient(id_cliente);;
    }




}
