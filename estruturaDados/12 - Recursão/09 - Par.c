/*
 Crie a função recursiva par(n), que determina se o natural n é par, usando apenas subtração.


*/

#include <stdio.h>


// Função recursiva para determinar se um número é par
int par(int n) {
    if (n == 0) return 1; // Caso base: 0 é par
    if (n == 1) return 0; // Caso base: 1 é ímpar
    return par(n - 2);    // Chamada recursiva reduzindo n de 2 em 2
}

int main(void) {
    int n;

    printf("Digite um número: ");
    scanf("%d", &n);

    if (par(n))
        printf("Par\n");
    else
        printf("Ímpar\n");

    return 0;
}
