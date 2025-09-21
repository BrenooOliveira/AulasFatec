/*
Crie a função intervalo(p,u), que cria e devolve uma lista contendo inteiros consecutivos
de p até u (para p  u). Por exemplo, a chamada intervalo(-2,3) deve devolver a lista
[-2,-1,0,1,2,3]. Se p  u, a função deve devolver uma lista v
*/

#include <stdio.h>
#include <stdlib.h>

typedef int Item;

// Estrutura do nó da lista encadeada
typedef struct no {
    Item item;
    struct no *prox;
} *Lista;

// Cria um novo nó com valor x e próximo apontando para p
Lista no(Item x, Lista p) {
    Lista n = malloc(sizeof(struct no));
    if (n == NULL) {
        printf("Erro: memória insuficiente\n");
        exit(1);
    }
    n->item = x;
    n->prox = p;
    return n;
}

// Função intervalo: cria lista com inteiros de p até u
Lista intervalo(int p, int u) {
    if (p > u) {
        return NULL; // Lista vazia se p > u
    } else {
        // Cria nó com p e chama recursivamente com p + 1
        return no(p, intervalo(p + 1, u));
    }
}

// Função para exibir a lista com colchetes
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


int main(void) {
    Lista L = intervalo(-2, 3);
    printf("Lista gerada: ");
    exibeComColchetes(L);
    return 0;
}
