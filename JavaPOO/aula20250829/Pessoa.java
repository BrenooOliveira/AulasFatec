package aula20250829;

public class Pessoa {
    private String nome;
    private int idade;

    // construtor
    public Pessoa(String nome, int idade){

    }

    // getters
    public String getNome(){
        return nome;
    }
    public int getIdade(){
        return idade;
    }

    //setters
    public void setNome(String nome){
        this.nome = nome;
    }
    public void setIdade(int idade){
        this.idade = idade;
    }

    // metodos
    public void exibirDados(){
        System.out.println("Nome: " + nome + "Idade" + "idade");
    }
}
