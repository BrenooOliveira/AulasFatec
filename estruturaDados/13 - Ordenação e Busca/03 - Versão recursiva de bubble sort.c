/*
Crie a função recursiva bsr(v,n), que usa a função empurra() e a estratégia do bubble
 sort, para organizar os n itens do vetor v em ordem crescente.
 int v[9] = {51,82,38,99,75,19,69,46,27};
 bsr(v,9);
 exibe(v,9);
 Saída: {19,27,38,46,51,69,75,82,99}

*/

#include <stdio.h>

// Função para trocar dois elementos no vetor
void troca(int v[], int i, int j) {
    int temp = v[i]; // Armazena temporariamente o valor de v[i]
    v[i] = v[j];     // Atribui o valor de v[j] para v[i]
    v[j] = temp;     // Atribui o valor temporário (antigo v[i]) para v[j]
}

// Função para exibir os elementos do vetor
void exibe(int v[], int n) {
    for (int i = 0; i < n; i++) {
        printf("%d ", v[i]); // Imprime cada elemento do vetor seguido de um espaço
    }
    printf("\n"); // Quebra de linha para melhor formatação
}

// Função recursiva para empurrar o maior elemento até a posição u
void empurra(int v[], int u) {
    if (u == 0) return; // Caso base: Se u for 0, não há mais nada a empurrar, então retorna

    // Inicializa max_idx como o primeiro índice
    int max_idx = 0;
    
    // Percorre o vetor até a posição u para encontrar o maior elemento
    for (int i = 1; i <= u; i++) { 
        if (v[i] > v[max_idx]) { // Se encontrar um elemento maior
            max_idx = i; // Atualiza o índice do maior elemento encontrado
        }
    }

    // Troca o maior elemento encontrado com o elemento na posição u
    troca(v, max_idx, u);
}

// Função recursiva para ordenar o vetor usando Bubble Sort Recursivo
void bsr(int v[], int n) {
    if (n == 1) return; // Caso base: Se o tamanho do vetor for 1, já está ordenado

    // Empurra o maior elemento para a última posição da faixa analisada
    empurra(v, n - 1);

    // Chamada recursiva para ordenar os elementos restantes
    bsr(v, n - 1);
}

// Função principal
int main(void) {
    int v[9] = {51, 82, 38, 99, 75, 19, 69, 46, 27}; // Vetor de entrada

    bsr(v, 9); // Ordena o vetor usando Bubble Sort Recursivo

    exibe(v, 9); // Exibe o vetor após a ordenação

    return 0;
}
