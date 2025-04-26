#include <stdio.h>
#include <stdlib.h>
#include <time.h>

typedef int Item;

// Estrutura de nó de lista encadeada
typedef struct no {
    Item item;
    struct no *prox;
} *Lista;

// Cria um novo nó com valor x e aponta para p
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

// Exibe a lista no formato [1, 2, 3]
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

/*
 * Função recursiva que retorna o tamanho da lista L
 */
int len(Lista L) {
    if (L == NULL) return 0;         // Caso base: lista vazia tem tamanho 0
    return 1 + len(L->prox);         // Soma 1 e chama recursivamente para o próximo nó
}

/*
 * Função recursiva que retorna a soma dos itens da lista L
 */
int sum(Lista L) {
    if (L == NULL) return 0;         // Caso base: lista vazia soma 0
    return L->item + sum(L->prox);   // Soma item atual com soma do restante da lista
}

/*
 * Função recursiva que cria uma cópia da lista L
 */
Lista clone(Lista L) {
    if (L == NULL) return NULL;               // Caso base: lista vazia retorna NULL
    return no(L->item, clone(L->prox));       // Cria novo nó com item atual e clona o resto da lista
}

/*
 * Função recursiva que gera uma lista com n itens aleatórios de 0 até m-1
 */
Lista rnd(int n, int m) {
    if (n <= 0) return NULL;                  // Caso base: 0 elementos gera lista vazia
    return no(rand() % m, rnd(n - 1, m));     // Cria nó com valor aleatório e chama recursivamente
}

/*
 * Função recursiva que devolve o último item da lista L
 */
int last(Lista L) {
    if (L == NULL) {
        printf("Erro: lista vazia\n");
        exit(1);
    }
    if (L->prox == NULL) return L->item;      // Caso base: último nó encontrado
    return last(L->prox);                     // Continua procurando no próximo nó
}

/*
 * Função recursiva que verifica se x está presente na lista L
 */
int in(Item x, Lista L) {
    if (L == NULL) return 0;                  // Caso base: chegou ao fim sem encontrar
    if (L->item == x) return 1;               // Item encontrado
    return in(x, L->prox);                    // Verifica no restante da lista
}

// Exemplo de uso no main
int main(void) {
    srand(time(NULL)); // Inicializa semente aleatória

    Lista L = rnd(3, 10); // Cria lista com 5 números aleatórios entre 0 e 99
    printf("Lista: ");
    exibeComColchetes(L);

    printf("Tamanho: %d\n", len(L));
    printf("Soma: %d\n", sum(L));

    Lista copia = clone(L);
    printf("Clone: ");
    exibeComColchetes(copia);

    printf("Último item: %d\n", last(L));
    int x = 5;
    printf("O item %d está na lista? %s\n", x, in(x, L) ? "Sim" : "Não");

    return 0;
}
