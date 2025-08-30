package aula20250829;

public class Aluno extends Pessoa{
    private String matricula;

    public Aluno(String nome, int idade, String matricula){
        super(nome, idade);
        this.matricula = matricula;
    }

    //getters
    public String getMatricula(){
        return matricula;
    }
    public void setMatricula(String matricula){
        this.matricula = matricula;
    }

}
