package ex4;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        String cpf = "33410781005"; // Exemplo de CPF válido
        ArrayList<Integer> produtoSequencia = new ArrayList<>();

        /* ------------------ INICIO PRIMEIRO DIGITO ------------------ */
        // pegar os 9 primeiros digitos (sem os DV)
        for (int i = 0; i < 9; i++){
            int numero = Character.getNumericValue(cpf.charAt(i)); // numero da sequencia em int
            int peso = 10 - i;
            int produto = numero * peso;

            System.out.println(numero + " * " + peso + " : " + produto);
            produtoSequencia.add(produto); // adiciona cada produto na lista
        }
        System.out.println(produtoSequencia);

        // soma todos os produtos do array
        int soma = 0;
        for (Integer i : produtoSequencia) {
            soma += i;
        }
        System.out.println("Soma: " + soma);

        int resto = soma % 11; // RN: 11 eh o numero total de digitos no cpf
        System.out.println("Resto: " + resto);

        int primeiroDigito; // agora será atribuído em qualquer caso
        if (resto < 2){
            primeiroDigito = 0;
        } else {
            primeiroDigito = 11 - resto;
        }
        System.out.println("Primeiro digito: " + primeiroDigito);

        /* ------------------ INICIO SEGUNDO DIGITO ------------------ */
        produtoSequencia.clear(); // limpa o array para reutilizar
        // pegar os 10 primeiros digitos (com o primeiro DV)
        for (int i = 0; i < 10; i++){
            int numero = Character.getNumericValue(cpf.charAt(i)); // numero da sequencia em int
            int peso = 11 - i;
            int produto = numero * peso;

            System.out.println(numero + " * " + peso + " : " + produto);
            produtoSequencia.add(produto); // adiciona cada produto na lista
        }
        System.out.println(produtoSequencia);

        // soma todos os produtos do array
        soma = 0;
        for (Integer i : produtoSequencia) {
            soma += i;
        }
        System.out.println("Soma: " + soma);

        resto = soma % 11; // RN: 11 eh o numero total de digitos no cpf
        System.out.println("Resto: " + resto);

        int segundoDigito; // agora será atribuído em qualquer caso
        if (resto < 2){
            segundoDigito = 0;
        } else {
            segundoDigito = 11 - resto;
        }
        System.out.println("Segundo digito: " + segundoDigito);

        /* ------------------ VERIFICAÇÃO FINAL ------------------ */
        String digitosVerificadoresInformado = cpf.substring(9, 11);
        String digitosCalculados = Integer.toString(primeiroDigito) + Integer.toString(segundoDigito);

        System.out.println("DVs informados: " + digitosVerificadoresInformado
        + " | DVs calculados: " + digitosCalculados);

        if (digitosVerificadoresInformado.equals(digitosCalculados)){
            System.out.println("CPF VÁLIDO");
        } else {
            System.out.println("CPF INVÁLIDO");
        }
    }
}
