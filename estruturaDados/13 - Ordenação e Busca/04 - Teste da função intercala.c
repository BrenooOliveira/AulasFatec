/*

Complete o programa a seguir, execute-o e analise os resultados.


*/


#include <stdio.h>
#include <stdlib.h>

// Função para exibir os elementos do vetor
void exibe(int v[], int n) {
    for (int i = 0; i < n; i++) {
        printf("%d ", v[i]); // Imprime cada elemento do vetor seguido de um espaço
    }
    printf("\n"); // Quebra de linha para melhor formatação
}

// Função que intercala duas partes ordenadas de um vetor
void intercala(int v[], int p, int m, int u) {
    // Criamos um vetor temporário 'w' para armazenar os elementos intercalados
    int *w = malloc((u - p + 1) * sizeof(int));

    // Índices auxiliares para percorrer cada parte do vetor
    int i = p, j = m + 1, k = 0;

    // Enquanto houver elementos nas duas metades, compará-los e escolher o menor
    while (i <= m && j <= u) {
        if (v[i] < v[j]) {
            w[k++] = v[i++]; // Adiciona o menor elemento e avança no subvetor correspondente
        } else {
            w[k++] = v[j++];
        }
    }

    // Se ainda restarem elementos na primeira metade, adicionamos ao vetor temporário
    while (i <= m) {
        w[k++] = v[i++];
    }

    // Se ainda restarem elementos na segunda metade, adicionamos ao vetor temporário
    while (j <= u) {
        w[k++] = v[j++];
    }

    // Copia os elementos de 'w' de volta para o vetor original 'v'
    for (k = 0; k <= u - p; k++) {
        v[p + k] = w[k];
    }

    // Libera a memória alocada para o vetor temporário
    free(w);
}

// Função principal para testar 'intercala'
int main(void) {
    // Vetor parcialmente ordenado: as duas primeiras metades são ordenadas
    int v[8] = {31, 48, 60, 80, 19, 27, 52, 75};
    intercala(v, 0, 3, 7); // Intercala os elementos entre p=0, m=3 e u=7
    exibe(v, 8); // Deve exibir: {19, 27, 31, 48, 52, 60, 75, 80}

    // Outro vetor parcialmente ordenado
    int w[9] = {10, 82, 27, 38, 41, 53, 60, 75, 99};
    intercala(w, 0, 1, 8); // Intercala os elementos entre p=0, m=1 e u=8
    exibe(w, 9); // Deve exibir: {10, 27, 38, 41, 53, 60, 75, 82, 99}

    return 0;
}
