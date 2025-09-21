package aula20250829;
/*  interface -> "contrato"
- não exite lógica
"quem me implementar, tem que ter esse metodo trabalhar()"

usa-se quando:
- Quando a classe Pessoa só existe em função daquela interface.
- Para organização: deixa claro que Pessoa é uma implementação padrão de Trabalhavel.
*/
public interface Trabalhavel {
    void trabalhar();

    public class Pessoa implements Trabalhavel{
        public void trabalhar(){
            System.out.println("Pessoa is working");
        }
    }
}
