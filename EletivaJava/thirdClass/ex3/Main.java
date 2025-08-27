package thirdClass.ex3;

public class Main {
    public static void main(String[] args) {
        Fibonacci fibonacci = new Fibonacci();

        System.out.println("Sequência de Fibonacci até 10 termos:");

        for (int i = 0; i < 10; i++) {
            System.out.print(fibonacci.fibonacci(i) + " ");
        }
    }
}
