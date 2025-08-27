package thirdClass.ex3;

public class Fibonacci {
    public static int fibonacci(int n){
        if (n==0) return 0; // base
        if (n==1) return 1; // base
        return fibonacci(n-1) + fibonacci(n-2); 
    }
}
