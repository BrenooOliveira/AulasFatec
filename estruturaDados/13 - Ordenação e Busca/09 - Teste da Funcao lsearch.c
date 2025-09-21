#include <stdio.h>
#include <stdlib.h>
#include <time.h>

// Busca Linear
int lsearch(int v[], int n, int x) {
    for (int i = 0; i < n; i++) {
        if (v[i] == x) return i;
    }
    return -1;
}

int main(void) {
    int n = 10;
    int v[10] = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
    int x = 98;
    int result = lsearch(v, n, x);

    if (result != -1) {
        printf("Elemento %d encontrado na posição %d\n", x, result);
    } else {
        printf("Elemento %d não encontrado\n", x);
    }

    return 0;
}
// O código acima implementa uma busca linear em um vetor de inteiros.
// A função lsearch percorre o vetor e retorna a posição do elemento procurado ou -1 se não encontrado.
// O main testa a função lsearch com um vetor de 10 elementos e procura pelo número 5.
// A busca linear é simples, mas ineficiente para vetores grandes, pois tem complexidade O(n).
// Para vetores pequenos, como o exemplo, a busca linear é rápida e fácil de implementar.
// A busca linear é útil quando o vetor não está ordenado ou quando a lista é pequena.
