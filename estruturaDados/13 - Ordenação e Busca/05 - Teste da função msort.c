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


void ms(int v[], int p, int u) {
    if( p==u ) return;
    int m = (p+u)/2;
    ms(v,p,m);
    ms(v,m+1,u);
    intercala(v,p,m,u);
}
void msort(int v[], int n) {
    ms(v,0,n-1);
}

int main(void) {
    int v[10] = {83,31,91,46,27,20,96,25,96,80};
    msort(v,10);
    exibe(v,10);
    return 0;
}
