package JavaPOO.aula20250822;

public class Main {
    public static void main(String[] args) {
        // objetos
        Materia poo = new Materia(
                    "60h",
                    "Programação Orientada a Objetos",
                    "POO123",
                    "Wellington",
                    "Segunda 19:00-22:00",
                    false);
        Materia engsoftw = new Materia(
                    "120h",
                    "Engenharia de Software",
                    "ENGSOFT456",
                    "Rogerio",
                    "Terça 19:00-22:00",
                    true);
        Materia bd = new Materia(
                    "40h",
                    "Banco de Dados",
                    "BD789",
                    "Mariangela",
                    "Quarta 19:00-22:00",
                    false);
        Materia java = new Materia(
                    "160h",
                    "Java",
                    "JAVA101",
                    "Wellington",
                    "Quinta 19:00-22:00",
                    false);

        Aluno breno = new Aluno(
                    "1",
                    "Breno",
                    "breno.fatec",
                    "Ativo",
                    "01/01/2004");
        Aluno joao = new Aluno(
                    "2",
                    "João",
                    "joao.fatec",
                    "Ativo",
                    "02/02/2004");
        Aluno maria = new Aluno(
                    "3",
                    "Maria",
                    "maria.fatec",
                    "Inativo",
                    "03/03/2004");

        // chamada de método (agora dentro do main)
        breno.matricularEm(poo);
        breno.matricularEm(engsoftw);

        joao.matricularEm(java);
        joao.matricularEm(poo);

        maria.matricularEm(bd);
        maria.matricularEm(engsoftw);

        breno.listarMateriasMatriculadas();
        joao.listarMateriasMatriculadas();
        maria.listarMateriasMatriculadas();
    }
}
