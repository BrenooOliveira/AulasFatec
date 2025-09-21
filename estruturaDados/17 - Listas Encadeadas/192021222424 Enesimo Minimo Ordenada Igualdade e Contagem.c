#include <stdio.h>
#include <stdlib.h>
#include <time.h>

typedef int Item;

typedef struct no {
    Item item;
    struct no *prox;
} *Lista;

// Cria novo nó com item x apontando para p
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

// Exibe lista no formato [1, 2, 3]
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

// Função recursiva que retorna o n-ésimo item da lista (n ≥ 1)
Item nth(int n, Lista L) {
    if (L == NULL || n < 1) {
        printf("Erro: índice inválido ou lista vazia\n");
        exit(1);
    }
    if (n == 1) return L->item;        // Caso base: primeiro item
    if (L->prox == NULL) {
        printf("Erro: índice maior que o tamanho da lista\n");
        exit(1);
    }
    return nth(n - 1, L->prox);        // Chama recursivamente para o próximo nó

}

// Função recursiva que devolve o menor item da lista
Item minimum(Lista L) {
    if (L == NULL) {
        printf("Erro: lista vazia\n");
        exit(1);
    }
    if (L->prox == NULL) return L->item;  // Caso base: último item
    int minRest = minimum(L->prox);       // Menor do restante
    return (L->item < minRest) ? L->item : minRest; // Retorna o menor
}

// Verifica se a lista está ordenada (ordem crescente)
int sorted(Lista L) {
    if (L == NULL || L->prox == NULL) return 1;      // Lista vazia ou com 1 item: ordenada
    if (L->item > L->prox->item) return 0;           // Desordenado
    return sorted(L->prox);                          // Verifica restante
}

// Verifica se duas listas são iguais
int equal(Lista A, Lista B) {
    if (A == NULL && B == NULL) return 1;            // Ambas acabaram
    if (A == NULL || B == NULL) return 0;            // Uma acabou e outra não
    if (A->item != B->item) return 0;                // Itens diferentes
    return equal(A->prox, B->prox);                  // Verifica o resto
}

// Conta quantas vezes x aparece na lista
int count(Item x, Lista L) {
    if (L == NULL) return 0; // Caso base: lista vazia
    return (L->item == x ? 1 : 0) + count(x, L->prox); // Conta o restante
}

// Cria nova lista substituindo x por y
// Se x não estiver na lista, retorna a lista original
// Se y já estiver na lista, não faz nada
// Se y for igual a x, não faz nada
Lista replace(Item x, Item y, Lista L) {
    if (L == NULL) return NULL; // Caso base: lista vazia
    Item novoValor = (L->item == x) ? y : L->item; // Substitui x por y
    return no(novoValor, replace(x, y, L->prox));
}

// Gera lista de tamanho n com números aleatórios entre 0 e max-1
Lista rnd(int n, int max) {
    if (n <= 0) return NULL;
    return no(rand() % max, rnd(n - 1, max));
}

// Exemplo de uso
int main(void) {
    srand(time(NULL));

    Lista L = no(1, no(2, no(3, no(4, no(5, NULL)))));
    printf("Lista: ");
    exibeComColchetes(L);

    printf("3º item: %d\n", nth(3, L));
    printf("Menor item: %d\n", minimum(L));
    printf("Está ordenada? %s\n", sorted(L) ? "Sim" : "Não");

    Lista clone = replace(5, 99, L);  // Substitui 5 por 99
    printf("Lista após replace(5,99): ");
    exibeComColchetes(clone);

    printf("O número 3 aparece %d vezes\n", count(3, L));

    Lista copia = rnd(5, 6); // Nova lista para comparação
    printf("Outra lista: ");
    exibeComColchetes(copia);
    printf("Listas são iguais? %s\n", equal(L, copia) ? "Sim" : "Não");

    return 0;
}
