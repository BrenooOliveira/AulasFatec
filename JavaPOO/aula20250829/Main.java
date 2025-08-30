package aula20250829;

public class Main {
    public static void main(String[] args) {
        Pessoa pessoa1 = new Pessoa("Ana", 25);
        System.out.println("Nome: " + pessoa1.getNome());
        System.out.println("Idade: " + pessoa1.getIdade());
        pessoa1.exibirDados();
        
        Trabalhavel pessoa2 = new Trabalhavel.Pessoa();
        pessoa2.trabalhar();
    }
}
