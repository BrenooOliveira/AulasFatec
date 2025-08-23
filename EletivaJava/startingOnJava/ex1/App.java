package startingOnJava.ex1;
import java.util.Scanner;

// Classe principal que contém o método main, ponto de entrada do programa
public class App {
    public static void main(String[] args) {
        // Cria um objeto da classe Travel para armazenar os dados da viagem
        Travel travel = new Travel();

        // Cria um Scanner para ler entradas do usuário pelo teclado
        Scanner userInput = new Scanner(System.in);

        // Solicita e lê o nome do trajeto/usuário
        System.out.println("Informe o seu nome: ");
        travel.name = userInput.nextLine();

        // Solicita e lê a distância da viagem (em km) como double
        System.out.println("Informe os kms a serem percorridos: ");
        travel.distance = userInput.nextDouble();

        // Solicita e lê o consumo médio do veículo (km/l) como double
        System.out.println("Informe o consumo médio do veiculo: ");
        travel.avgConsumption = userInput.nextDouble();

        // Solicita e lê o preço médio do combustível (R$/litro) como double
        System.out.println("Informe o valor medio do combustível: ");
        travel.fuelPrice = userInput.nextDouble();

        // Chama o método da classe Travel que imprime todas as informações da viagem
        travel.showTravelInfo();

        // Calcula o custo aproximado da viagem usando o método aproxCost() da classe Travel
        Double cost = travel.aproxCost();
        System.out.println("Gastos Aproximados: " + cost);

        // Fecha o Scanner para liberar recursos do sistema
        userInput.close();
    }
}
