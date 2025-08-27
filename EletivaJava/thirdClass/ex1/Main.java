package thirdClass.ex1;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Entrada de dados
        System.out.print("Seu nome completo: ");
        String nome = scanner.nextLine().trim();

        System.out.print("Salário Bruto: ");
        Double salarioBruto = scanner.nextDouble();

        // Processamento
        CalculaSalario salario = new CalculaSalario(salarioBruto);

        // Saída formatada
        System.out.println("\n--- Resumo Financeiro de " + nome + " ---");
        System.out.printf("Salário Bruto: R$ %.2f%n", salarioBruto);
        System.out.printf("Total de Descontos: R$ %.2f%n", salario.calculaDescontos());
        System.out.printf("Salário Líquido: R$ %.2f%n", salario.salarioLiquido());
        System.out.printf("FGTS (8%%): R$ %.2f%n", salario.fgts());
        System.out.printf("Salário Liquido Férias: R$ %.2f%n", salario.salarioLiquidoFerias());

        scanner.close();
    }
}
