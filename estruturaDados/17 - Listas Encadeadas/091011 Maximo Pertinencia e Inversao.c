#include <stdio.h>
#include <stdlib.h>

typedef int Item;

// Estrutura do nó da lista encadeada
typedef struct no {
    Item item;
    struct no *prox;
} *Lista;

// Cria um novo nó com o valor x e aponta para p
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

// Exibe os elementos da lista no formato de vetor
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

// Retorna o maior item da lista (erro fatal se vazia)
Item maximo(Lista L) {
    if (L == NULL) {
        printf("Erro: lista vazia!\n");
        exit(1);
    }
    Item max = L->item;
    L = L->prox;
    while (L != NULL) {
        if (L->item > max) {
            max = L->item;
        }
        L = L->prox;
    }
    return max;
}

// Verifica se o item x pertence à lista
int pertence(Item x, Lista L) {
    while (L != NULL) {
        if (L->item == x) {
            return 1; // Verdadeiro
        }
        L = L->prox;
    }
    return 0; // Falso
}

// Cria uma cópia invertida da lista L
Lista inversa(Lista L) {
    Lista inv = NULL;
    while (L != NULL) {
        inv = no(L->item, inv); // Insere sempre no início
        L = L->prox;
    }
    return inv;
}

// Testes
int main(void) {
    Lista L = no(1, no(2, no(3, no(4, no(5, NULL)))));

    printf("Lista original: ");
    exibeComColchetes(L);

    printf("Maior item: %d\n", maximo(L));
    printf("Pertence 3? %s\n", pertence(3, L) ? "Sim" : "Não");
    printf("Pertence 9? %s\n", pertence(9, L) ? "Sim" : "Não");

    Lista inv = inversa(L);
    printf("Lista invertida: ");
    exibeComColchetes(inv);

    return 0;
}
