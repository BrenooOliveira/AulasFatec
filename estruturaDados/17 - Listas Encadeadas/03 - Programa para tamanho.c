#include <stdio.h>
#include <stdlib.h>

// Exercício 03 - Programa para tamanho


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



int main(void) {
    Lista I = no(3,no(1,no(5,NULL)));
    exibe(I);
    printf("Tamanho = %d\n",tamanho(I));
    return 0;
}
