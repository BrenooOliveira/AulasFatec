// CONVERSÃO
package thirdClass.ex2;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);

        System.out.println("Primeiro Salário: ");
        String v1 = entrada.next();

        System.out.println("Segundo Salário: ");
        String v2 = entrada.next();

        System.out.println("Terceiro Salário: ");
        String v3 = entrada.next();

        double s1 = Double.parseDouble(v1);
        double s2 = Double.parseDouble(v2);
        double s3 = Double.parseDouble(v3);

        double media = (s1+s2+s3) / 3;

        System.out.printf("Média: %.2f%n", media);

    }

}
