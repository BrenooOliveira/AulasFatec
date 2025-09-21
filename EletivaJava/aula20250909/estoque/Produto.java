package estoque;

public class Produto {
    private int qtde; // qtde do estoque
    private double valorUnit; // valor que o item sera vendido
    private double custoUnit; // custo do item
    private int qtdeAlerta; // qtde miníma para alerta


    public Produto(int qtde,double valorUnit, double custoUnit){
        this.qtde = qtde;
        this.valorUnit = valorUnit;
        this.custoUnit = custoUnit;
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
    public double getCustoUnit(){
        return this.custoUnit;
    }

    // metodos
    public void soldProduct(int unitsSold){
        // a partir da quantidade de vendas, vai retirar da qtde
        this.qtde -= unitsSold;
    }

}
