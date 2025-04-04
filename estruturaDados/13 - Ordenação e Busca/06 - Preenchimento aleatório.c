/*
Faça um programa para testar o funcionamento da função a seguir, que preenche um vetor v
com n inteiros aleatórios, gerados a partir da semente s, escolhidos no intervalo [0,999].
*/
#include <stdio.h>
#include <stdlib.h>
#include <time.h>

// Função para preencher o vetor com números aleatórios
void preenche(int v[], int n, int s) {
    srand(s); // Define a semente para geração de números aleatórios
    for (int i = 0; i < n; i++) {
        v[i] = rand() % 1000; // Gera valores entre 0 e 999
    }
}

// Função para trocar dois elementos no vetor
void troca(int v[], int i, int j) {
    int temp = v[i]; 
    v[i] = v[j]; 
    v[j] = temp;
}

// Implementação do Bubble Sort
void bsort(int v[], int n) {
    for (int i = 1; i < n; i++) {  // Passa pelo vetor n vezes
        for (int j = 0; j < n - i; j++) { // Percorre os elementos não ordenados
            if (v[j] > v[j + 1]) {  // Se estiver fora de ordem, troca
                troca(v, j, j + 1);
            }
        }
    }
}

// Função que intercala duas partes ordenadas de um vetor (Merge Sort)
void intercala(int v[], int p, int m, int u) {
    int *w = malloc((u - p + 1) * sizeof(int));
    int i = p, j = m + 1, k = 0;

    while (i <= m && j <= u) {  // Comparação entre os elementos das duas metades
        w[k++] = (v[i] < v[j]) ? v[i++] : v[j++];
    }

    while (i <= m) w[k++] = v[i++];  // Copia o restante da primeira metade
    while (j <= u) w[k++] = v[j++];  // Copia o restante da segunda metade

    for (k = 0; k <= u - p; k++) v[p + k] = w[k]; // Copia para o vetor original

    free(w);
}

// Função recursiva para dividir o vetor e chamar intercala()
void ms(int v[], int p, int u) {
    if (p == u) return;
    int m = (p + u) / 2;
    ms(v, p, m);
    ms(v, m + 1, u);
    intercala(v, p, m, u);
}

// Função de ordenação Merge Sort
void msort(int v[], int n) {
    ms(v, 0, n - 1);
}

// Função para exibir os elementos do vetor
void exibe(int v[], int n) {
    for (int i = 0; i < n; i++) {
        printf("%d ", v[i]);
    }
    printf("\n");
}

// Função principal para comparar os algoritmos
int main(void) {
    int v[100000];  // Vetor para armazenar os números (tamanho máximo 100000)
    double t, b, m; // Variáveis para armazenar os tempos

    puts("     n   bsort  msort");
    
    for (int n = 10000; n <= 100000; n += 10000) {
        preenche(v, n, 42); // Preenche o vetor com a mesma sequência aleatória
        t = clock();
        bsort(v, n);
        b = (clock() - t) / (double) CLOCKS_PER_SEC;

        preenche(v, n, 42); // Preenche novamente para que os algoritmos ordenem os mesmos números
        t = clock();
        msort(v, n);
        m = (clock() - t) / (double) CLOCKS_PER_SEC;

        printf("%6d %5.2f %5.2f\n", n, b, m);
    }

    return 0;
}
