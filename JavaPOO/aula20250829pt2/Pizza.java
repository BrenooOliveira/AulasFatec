package aula20250829pt2;

public class Pizza extends Salgado {
    public Boolean rodizio;

    public Pizza(Boolean rodizio, String tamanho,String sabor, Double preco){
        super(tamanho,sabor,preco);
        this.rodizio = rodizio;
    }

}
