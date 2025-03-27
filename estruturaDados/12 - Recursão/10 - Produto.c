/*
 Crie a função recursiva prod(m,n), que devolve o produto de dois números naturais m e n,
 usando apenas soma e subtração.

 Base: n = 1
 Base: n = 0

 */

 

// Função recursiva para determinar se um número é par
int par(int n) {
    if (n == 0) return 1; // Caso base: 0 é par
    if (n == 1) return 0; // Caso base: 1 é ímpar
    return par(n - 2);    // Chamada recursiva reduzindo n de 2 em 2
}

int prod(int m,int n){
    if ( n == 0) return 0;
    if ( n == 1) return m;
    return m + prod(m,n-1);

}

int main(void) {
    int r = prod(5, 2); // Calcula 2 * 2

    printf("%d\n", r);  // Imprime o resultado corretamente
    return 0;
}