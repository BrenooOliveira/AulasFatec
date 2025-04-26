#include <stdio.h>
#include <stdlib.h>

/*
    Altere a função exibe(), de modo que os itens da lista sejam exibidos entre colchetes e
separados por vírgulas. A lista vazia deve ser exibida como [].
Por exemplo, após a alteração da função exibe(), a execução do código abaixo deve produzir
a saída indicada a seguir:
    Lista: [10, 20, 30]
    Lista: []
    Lista: [10]
    Lista: [10, 20]
    Lista: [10, 20, 30]
    Lista: [10, 20, 30, 40]
    Lista: [10, 20, 30, 40, 50]
    Lista: [10, 20, 30, 40, 50, 60]
    Lista: [10, 20, 30, 40, 50, 60, 70]
    Lista: [10, 20, 30, 40, 50, 60, 70]    
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

// Exercício 02 - Outra forma de exibição

int main(void) {
    printf("Exercício 02 - Outra forma de exibição\n");
    Lista I = no(3, no(1, no(5, NULL)));
    exibeComColchetes(I);
    
    return 0;
}
