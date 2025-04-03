/*
 Crie a função recursiva empurra(v,u), que “empurra” um item máximo do vetor v para a
 posição u de v, possivelmente alterando a ordem dos demais itens do vetor. Por exemplo, o
 código abaixo deve produzir a saída indicada a seguir:
 int v[9] = {51,82,38,99,75,19,69,46,27};
 empurra(v,8);
 exibe(v,9);
 Saída: {51,38,82,75,19,69,46,27,99}
*/

#include <stdio.h>

// Função para trocar dois elementos do vetor
void troca(int v[], int i, int j) {
    // i e j são os índices dos elementos que serão trocados
    int x = v[i]; // Armazena temporariamente o valor de v[i]
    v[i] = v[j];  // Substitui v[i] pelo valor de v[j]
    v[j] = x;     // Atribui o valor original de v[i] a v[j]
}

// Implementação do Bubble Sort para ordenar o vetor
void bsort(int v[], int n) {
    for (int i = 1; i < n; i++) // Laço externo controla o número de iterações
        for (int j = 0; j < n - i; j++) // Laço interno percorre o vetor até a posição correta
            if (v[j] > v[j + 1]) // Se o elemento atual for maior que o próximo
                troca(v, j, j + 1); // Troca os dois elementos
}

// Função para exibir o vetor
void exibe(int v[], int n) {
    for (int i = 0; i < n; i++) {
        printf(" %d ", v[i]); // Imprime cada elemento do vetor com espaço entre eles
    }
    printf("\n"); // Quebra de linha para melhor visualização
}

// Função para empurrar o maior elemento até a posição u do vetor
void empurra(int v[], int u) {
    if (u == 0) return; // Caso base: se u for 0, não há nada a empurrar

    int max_item = 0; // Índice do maior elemento encontrado
    for (int i = 1; i < u; i++) { // Percorre o vetor até a posição u
        if (v[i] > v[max_item]) max_item = i; // Atualiza max_item se encontrar um valor maior
    }

    troca(v, max_item, u); // Move o maior valor encontrado para a posição u
}

// Função principal
int main(void) {
    int v[9] = {51, 82, 38, 99, 75, 19, 69, 46, 27}; // Vetor de entrada

    empurra(v, 8); // Move o maior elemento para a posição 8 do vetor
    exibe(v, 9);   // Exibe o vetor atualizado

    return 0;
}
