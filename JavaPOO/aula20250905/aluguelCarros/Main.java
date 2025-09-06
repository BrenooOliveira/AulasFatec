package aluguelCarros;

public class Main {
    public static void main(String[] args) {
        Locadora locadora = new Locadora();

        System.out.println(locadora.getModelosPrecos());
        
        
        Cliente cliente = new Cliente(
                                        "Breno",
                                        20 ,
                                        "123456789",
                                        "Basico",
                                        1
                                    );
        // ------------------------ VALIDACOES ------------------------ //
        // se o modelo desejado nao existe na locadora
        if (!locadora.getModelosPrecos().containsKey(cliente.modeloDesejado)){
            System.out.println("Modelo de Carro Invalido!");
            System.exit(0); // finaliza o programa
        }
        // validacao simples do length da cnh
        if(cliente.getNumCnh().length() != 11){
            System.out.println("Numero de CNH Inválido!");
            System.exit(0);
        }
        // validacao numero de diarias
        if(cliente.numDiarias < 1){
            System.out.println("Numero de Inválido!");
        }

        // core do programa: calcular o valor total e exibir dados
    }   
}
