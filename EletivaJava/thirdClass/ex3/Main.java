package thirdClass.ex3;

public class Main {
    public static void main(String[] args) {

        System.out.println("Sequência de Fibonacci até 10 termos:");

        for (int i = 0; i < 10; i++) {
            System.out.print(Fibonacci.fibonacci(i) + " ");
        }
    }
}
