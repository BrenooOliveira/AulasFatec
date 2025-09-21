package JavaPOO.aula20250822;

import java.util.ArrayList;
import java.util.List;

public class Aluno {
    private String matricula;
    private String nome;
    private String email;
    private String status;
    private String dataNascimento;
    public List<Materia> materiasMatriculadas = new ArrayList<>();

// construtor
public Aluno(String matricula,String nome,String email, String status, String dataNascimento){
    this.matricula = matricula;
    this.nome = nome;
    this.email = email;
    this.status = status;
    this.dataNascimento = dataNascimento;
}

// getters
public String getMatricula(){
    return matricula;
}
public String getNome(){
    return nome;
}
public String getEmail(){
    return email;
}
public String getStatus(){
    return status;
}
public String getDataNascimento(){
    return dataNascimento;
}

//setters
public void setMatricula (String matricula){
    this.matricula = matricula;
}

public void setNome (String nome){
    this.nome = nome;
}

public void setEmail (String email){
    this.email = email;
}
public void setStatus (String status){
    this.status = status;
}
public void setDataNascimento (String dataNascimento){
    this.dataNascimento = dataNascimento;
}

// metodos
public void matricularEm(Materia materia){
    materiasMatriculadas.add(materia);
    //System.out.println("Aluno: " + this.nome + " Matriculado em: " + materia.getTitulo());
}

public void listarMateriasMatriculadas(){
    for (Materia materia : materiasMatriculadas) {
        System.out.println("Aluno: " + this.nome + " Matriculado em: " + materia.getTitulo() + " Status: " + this.status);
    }
}

}
