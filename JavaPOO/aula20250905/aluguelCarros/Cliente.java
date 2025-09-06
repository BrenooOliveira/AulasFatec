package aluguelCarros;


public class Cliente{
    private String nome;
    private int idade;
    private String numCnh;
    public String modeloDesejado;
    public int numDiarias;

    // construtor
    public Cliente(String nome,int idade, String numCnh, String modeloDesejado, int numDiarias){
        this.nome = nome;
        this.idade = idade;
        this.numCnh = numCnh;
        this.modeloDesejado = modeloDesejado;
        this.numDiarias = numDiarias;
    }

    // getters
    public String getNome(){
        return nome;
    }
    public int getIdade(){
        return idade;
    }
    public String getNumCnh(){
        return numCnh;
    }
}