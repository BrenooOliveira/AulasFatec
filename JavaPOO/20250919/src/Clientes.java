public class Clientes{
    private String nome;
    private String telefone;
    private String email;

    public Clientes(String nome, String telefone, String email) {
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
    }
    // ------------ Getters e Setters ------------ //
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getTelefone() {
        return telefone;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    // ------------ Metodos ------------ //
    public String CadastroCliente() {
        return "Cliente cadastrado: " + nome + ", Telefone: " + telefone + ", Email: " + email;
    }

}
