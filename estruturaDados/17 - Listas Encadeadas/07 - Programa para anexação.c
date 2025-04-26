// Exercício 07 - Programa para anexação

#include <stdio.h>
#include <stdlib.h>
#include <time.h>

typedef int Item;  // Define Item como um tipo alternativo para 'int', facilitando mudanças futuras

// Estrutura de um nó da lista encadeada
typedef struct no {
    Item item;         // Valor armazenado no nó
    struct no *prox;   // Ponteiro para o próximo nó
} *Lista;              // Define 'Lista' como um ponteiro para struct no (facilita o uso)

/* 
 * Cria um novo nó com valor x e aponta para p como próximo.
 * Retorna o novo nó criado.
 */
Lista no(Item x, Lista p) {
    Lista n = malloc(sizeof(struct no)); // Aloca memória para um novo nó
    if (n == NULL) {
        printf("Erro: Memória insuficiente\n");
        exit(1); // Encerra o programa em caso de falha na alocação
    }
    n->item = x;   // Atribui o valor x ao item do nó
    n->prox = p;   // Faz o novo nó apontar para o início da lista atual
    return n;      // Retorna o novo nó como início da nova lista
}

/*
 * Exibe os elementos da lista, um por linha.
 */
void exibe(Lista L) {
    while (L != NULL) {
        printf("%d\n", L->item); // Mostra o valor do nó atual
        L = L->prox;             // Avança para o próximo nó
    }
}

/*
 * Exibe os elementos da lista no formato de vetor: [1, 2, 3]
 */
void exibeComColchetes(Lista L) {
    printf("[");
    if (L != NULL) {
        printf("%d", L->item);  // Imprime o primeiro item
        L = L->prox;
    }
    while (L != NULL) {
        printf(", %d", L->item); // Imprime os demais itens com vírgula
        L = L->prox;
    }
    printf("]\n");
}

/*
 * Retorna o número de elementos da lista.
 */
int tamanho(Lista L) {
    int cont = 0;
    while (L != NULL) {
        cont++;      // Incrementa o contador para cada nó
        L = L->prox; // Avança na lista
    }
    return cont;     // Retorna o total de nós
}

/*
 * Retorna a soma de todos os valores da lista.
 */
int soma(Lista L) {
    int soma = 0;
    while (L != NULL) {
        soma += L->item; // Soma o valor do nó atual
        L = L->prox;     // Avança para o próximo nó
    }
    return soma; // Retorna a soma final
}

/*
 * Cria uma lista com n elementos aleatórios no intervalo de 0 até max-1.
 */
Lista aleatoria(int n, int max) {
    Lista L = NULL;
    for (int i = 0; i < n; i++) {
        int x = rand() % max; // Gera número aleatório entre 0 e max-1
        L = no(x, L);         // Insere esse número no início da lista
    }
    return L;
}

/*
 * Cria uma lista contendo os números de 1 até n.
 */
Lista intervalo(int n) {
    Lista L = NULL;
    for (int i = n; i > 0; i--) {
        L = no(i, L);  // Insere cada número no início da lista (ordem crescente)
    }
    return L;
}

/*
 * Anexa a lista I ao final da lista H.
 */
void anexa(Lista *H, Lista I) {
    if (*H == NULL) { // Se H estiver vazia, apenas atribui I a H
        *H = I;
    } else {
        Lista p = *H; // Ponteiro para percorrer a lista H
        while (p->prox != NULL) { // Percorre até o último nó de H
            p = p->prox;
        }
        p->prox = I; // Anexa I ao final de H
    }
}

int main(void) {
    Lista H = no(10,no(11,NULL));
    Lista I = no(0,no(1,no(2,NULL)));
    printf("H = "); exibeComColchetes(H);
    printf("I = "); exibeComColchetes(I);
    printf("Pressione enter");
    getchar();
    anexa(&H,I);
    printf("H = "); exibeComColchetes(H);
    printf("I = "); exibeComColchetes(I);
    return 0;
}
