package startingOnJava.ex2;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Churrasco churrasco = new Churrasco();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Quantos convidados? ");
        int numConvidados = scanner.nextInt();

        churrasco.consumoTotal(numConvidados);


    }
}
