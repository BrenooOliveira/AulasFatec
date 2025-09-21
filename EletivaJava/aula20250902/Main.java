/*
 * Pergunte a um usuário a sua idade e se ele sabe ler e escrever.
 *
 * caso a idade for maior que 18 anos e ele souber ler e escrever,
 * imprima na tela "Pode tirar carteira de habilitação".
 * Caso contrário, imprima "Não apto".
 *
 *
 */
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Pergunta se sabe ler e escrever e valida input
        String alfabetizado;
        do {
            System.out.println("Sabe ler e escrever? (S/N)");
            alfabetizado = scanner.nextLine().trim().toUpperCase();
            if (!alfabetizado.equals("S") && !alfabetizado.equals("N")) {
                System.out.println("Resposta inválida. Responda apenas S ou N.");
            }
        } while (!alfabetizado.equals("S") && !alfabetizado.equals("N"));

        // Pergunta a idade
        System.out.println("Qual a sua idade?");
        while (!scanner.hasNextInt()) {
            System.out.println("Digite um número válido para a idade.");
            scanner.next(); // descarta input inválido
        }
        int idade = scanner.nextInt();

        // Resultado final
        if (idade >= 18 && alfabetizado.equals("S")) {
            System.out.println("Pode tirar carteira de habilitação");
        } else {
            System.out.println("Não apto");
        }

        scanner.close();
    }
}
