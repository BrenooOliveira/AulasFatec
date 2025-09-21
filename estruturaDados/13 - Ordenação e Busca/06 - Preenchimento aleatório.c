
/*
Faça um programa para testar o funcionamento da função a seguir, que preenche um vetor v
com n inteiros aleatórios, gerados a partir da semente s, escolhidos no intervalo [0,999].
*/

#include <stdio.h>
#include <stdlib.h>
#include <time.h>

// Função que preenche um vetor com números aleatórios de 0 a 999
void preenche(int v[], int n, int s) {
    srand(s); // Define a semente para garantir reprodutibilidade dos números aleatórios
    for(int i = 0; i < n; i++) {
        v[i] = rand() % 1000; // Gera um número aleatório entre 0 e 999
    }
}

// Função para exibir o vetor
void exibe(int v[], int n) {
    for(int i = 0; i < n; i++) {
        printf("%d ", v[i]);
    }
    printf("\n");
}

// Função principal para testar a função preenche()
int main() {
    int n = 10; // Tamanho do vetor
    int v[n];   // Declaração do vetor
    int semente = 10; // Escolhe um valor fixo para a semente

    // Preenche o vetor com números aleatórios
    preenche(v, n, semente);

    // Exibe o vetor preenchido
    printf("Vetor gerado com semente %d:\n", semente);
    exibe(v, n);

    return 0;
}
