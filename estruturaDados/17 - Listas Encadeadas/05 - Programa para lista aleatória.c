#include <stdio.h>
#include <stdlib.h>
#include <time.h>

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
Lista aleatoria(int n, int max) {
    Lista L = NULL;
    for (int i = 0; i < n; i++) {
        int x = rand() % max; // Gera um número aleatório entre 0 e max-1
        L = no(x, L); // Insere o número na lista
    }
    return L;
}


int main(void) {
    srand(time(NULL));
    Lista A = aleatoria(10,10);
    exibe(A);
    return 0;
}
