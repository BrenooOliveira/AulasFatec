package aula20250829pt2;

public class Salgado {
    private String tamanho;
    private String sabor;
    private Double preco;

    // construtor
    public Salgado(String tamanho, String sabor, Double preco){
        this.tamanho = tamanho;
        this.sabor = sabor;
        this.preco = preco;
    }

    //getters
    public String getTamanho(){
        return tamanho;
    }
    public String getSabor(){
        return sabor;
    } 
    public Double getPreco(){
        return preco;
    }
    //setters
    public void setTamanho(String tamanho){
        this.tamanho = tamanho;
    }
    public void setSabor(String sabor){
        this.sabor = sabor;
    }
    public void setPreco(Double preco){
        this.preco = preco;
    }
}
