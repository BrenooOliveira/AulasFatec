package calculadora;

public class Calculadora{
    Double num1;
    Double num2;

    // construtor
    public Calculadora(Double num1, Double num2){
        this.num1 = num1;
        this.num2 = num2;
    }

    /**
     * Exibe o resultado de todas as operações disponíveis na calculadora.
     */
    public void exibeResultados() {
        System.out.println("--- Resultados das Operações ---");
        System.out.println("Números utilizados: " + this.num1 + " e " + this.num2);
        System.out.println("Soma: " + this.soma());
        System.out.println("Subtração: " + this.subtracao());
        System.out.println("Multiplicação: " + this.multiplicacao());
        System.out.println("Divisão: " + this.divisao());

        // Exibe a raiz quadrada do primeiro número
        System.out.println("Raiz quadrada do primeiro número (" + this.num1 + "): " + this._sqrt(this.num1)     );

        // Exibe a exponenciação
        System.out.println("Exponenciação (" + this.num1 + " elevado a " + this.num2 + "): " + this.exponential());
        System.out.println("--------------------------------");
    }

    // Métodos das operações
    Double soma(){
        return num1 + num2;
    }
    Double subtracao(){
        return num1 - num2;
    }
    Double multiplicacao(){
        return num1*num2;
    }
    Double divisao(){
        return num1/num2;
    }
    Double _sqrt(Double n){
        return Math.floor( Math.sqrt(n));
    }
    Double exponential(){
        return Math.floor(Math.pow(num1,num2));
    }
}
