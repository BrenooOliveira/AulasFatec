package JavaPOO.aula20250822;


public class Materia {
    private String cargaHoraria;
    private String titulo;
    private String codigo;
    private String professor;
    private String horario;
    private Boolean optativa;


// construtor
public Materia(String cargaHoraria,String titulo,String codigo,String professor,String horario,Boolean optativa) {
    this.cargaHoraria = cargaHoraria;
    this.titulo = titulo;
    this.codigo = codigo;
    this.professor = professor;
    this.horario = horario;
    this.optativa = optativa;

}

// getters
public String getCargaHoraria() {
    return cargaHoraria;
}
public String getTitulo() {
    return titulo;
}
public String getCodigo() {
    return codigo;
}
public String getProfessor() {
    return professor;
}
public String getHorario() {
    return horario;
}
public Boolean getOptativa(){
    return optativa;
}   

//setters
public void setCargaHoraria (String cargaHoraria){
    this.cargaHoraria = cargaHoraria;
}

public void setTitulo (String titulo){
    this.titulo = titulo;
}

public void setCodigo (String codigo){
    this.codigo = codigo;
}

public void setProfessor (String professor){
    this.professor = professor;
}

public void setHorario (String horario){
    this.horario = horario;
}


public void setOptativa (Boolean optativa){
    this.optativa = optativa;
}
}