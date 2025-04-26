#include <stdio.h>
#include <stdlib.h>

/*
Adicione no programa do exercício anterior uma função para calcular a soma dos itens da lista.
Por exemplo, considerando que I aponta a lista de inteiros [3,1,5], a chamada soma(I)
deve devolver a resposta 9.

*/


typedef int Item;

typedef struct no {
    Item item;
    struct no *prox;
} *Lista;

Lista no(Item x, Lista p) {
    Lista n = malloc(sizeof(struct no));
    if (n == NULL) {
        printf("Erro: Memória insuficiente\n");
        exit(1);
    }
    n->item = x;
    n->prox = p;
    return n;
}

void exibe(Lista L) {
    while (L != NULL) {
        printf("%d\n", L->item);
        L = L->prox;
    }
}

void exibeComColchetes(Lista L) {
    printf("[");
    if (L != NULL) {
        printf("%d", L->item);
        L = L->prox;
    }
    while (L != NULL) {
        printf(", %d", L->item);
        L = L->prox;
    }
    printf("]\n");
}

int tamanho(Lista L) {
    int cont = 0;
    while (L != NULL) {
        cont++;        // Conta o nó atual
        L = L->prox;   // Avança para o próximo nó
    }
    return cont;
}

int soma(Lista L) {
    int soma = 0;
    while (L != NULL) {
        soma += L->item; // Adiciona o valor do nó atual à soma
        L = L->prox;     // Avança para o próximo nó
    }
    return soma; // Retorna a soma total
}


// Exercício 04 - Soma dos itens da lista

int main(void) {
    printf("Exercício 04 - Soma dos itens da lista\n");
    Lista I = no(3,no(1,no(5,NULL)));
    exibe(I);
    printf("Soma = %d\n",soma(I));

    return 0;
}
