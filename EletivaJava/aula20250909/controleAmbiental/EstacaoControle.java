package controleAmbiental;

import java.util.Scanner;

public class EstacaoControle {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite a temperatura atual (em °C):");
        double temperatura = scanner.nextDouble();

        System.out.println("Digite a umidade atual (em %):");
        double umidade = scanner.nextDouble();

        determinarAcao(temperatura, umidade);

        scanner.close();
    }

    public static void determinarAcao(double temperatura, double umidade) {
        // 1. Condição de emergência para alta temperatura (maior prioridade)
        if (temperatura > 28) {
            System.out.println("Ação: Ligar resfriadores até 24 graus.");
            return; // Retorna para evitar que outras condições sejam verificadas
        }

        // 2. Condição para baixa umidade (segunda maior prioridade)
        if (umidade < 70) {
            System.out.println("Ação: Ligar umidificador.");
            return;
        }

        // 3. Condição para baixa temperatura
        if (temperatura < 17) {
            System.out.println("Ação: Ligar aquecedores até 20 graus.");
            return;
        }
        
        // 4. Condição para temperatura alta, mas não de emergência
        if (temperatura > 21 && umidade >= 70 && umidade <= 90) {
            System.out.println("Ação: Ligar ventilação.");
            return;
        }
        
        // 5. Condição de operação normal (nenhuma ação)
        if (temperatura >= 15 && temperatura <= 21 && umidade >= 70 && umidade <= 90) {
            System.out.println("Ação: Nenhuma.");
            return;
        }

        // Caso nenhuma das condições acima seja atendida
        System.out.println("Nenhuma ação definida para os valores de temperatura e umidade fornecidos.");
    }
}