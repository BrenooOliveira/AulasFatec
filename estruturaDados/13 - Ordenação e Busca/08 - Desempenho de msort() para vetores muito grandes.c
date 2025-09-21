#include <time.h>
#include <stdlib.h>
#include <stdio.h>

// Função para preencher o vetor com números aleatórios
void preenche(int v[], int n, int s) {
    srand(s); // Define a semente para geração de números aleatórios
    for (int i = 0; i < n; i++) {
        v[i] = rand() % 1000; // Gera valores entre 0 e 999
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

int main(void) {
    // precisamos usar malloc para criar vetores muito grandes!
    int *v = malloc(1e8 * sizeof(int));
    puts(" n msort");
    for (int n = 10000000; n <= 100000000; n += 10000000) {
        preenche(v, n, 1);
        double t = (double)clock(); // Cast clock() to double
        msort(v, n);
        double m = ((double)clock() - t) / CLOCKS_PER_SEC; // Cast clock() to double for accurate division
        printf("%9d %5.1f\n", n, m);
    }
    free(v);
    return 0;
}
