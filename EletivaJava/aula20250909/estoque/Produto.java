package estoque;

public class Produto {
    private int qtde;
    private double valorUnit;
    private int qtdeAlerta;

    public Produto(int qtde,double valorUnit){
        this.qtde = qtde;
        this.valorUnit = valorUnit;
        this.qtdeAlerta = (int) (qtde*0.2); // RN: 20% do estoque é uma quantidade de alerta
    }

    //getters
    public int getQtde(){
        return this.qtde;
    }
    public double getValorUnit(){
        return this.valorUnit;
    }
    public int getQtdeAlerta(){
        return this.qtdeAlerta;
    }
    
    // metodos
    public void soldProduct(int unitsSold){
        // a partir da quantidade de vendas, vai retirar da qtde
        this.qtde -= unitsSold;
    }
    
}
