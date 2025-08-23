package startingOnJava.ex2;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Churrasco churrasco = new Churrasco();
        
        Scanner numConvidados = new Scanner(System.in);
        System.out.println("Quantos convidados? ");
        
        churrasco.consumoTotal((Integer) numConvidados.nextLine());


    }
}
