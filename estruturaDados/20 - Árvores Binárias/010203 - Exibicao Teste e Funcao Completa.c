#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h> // Para usar srand e rand

// -------------------- MOLDE DA ÁRVORE BINÁRIA -------------------- //
// Cada nó da árvore binária contém:
// - um ponteiro para o filho à esquerda
// - um valor inteiro (item)
// - um ponteiro para o filho à direita
typedef struct arv {
    struct arv *esq;  // Galho da esquerda
    int item;         // Valor armazenado no nó
    struct arv *dir;  // Galho da direita
} *Arv;

// -------------------- CRIAÇÃO DE NÓ -------------------- //
// Cria um novo nó de árvore binária com subárvore esquerda 'e', valor 'x' e subárvore direita 'd'
Arv arv(Arv e, int x, Arv d) {
    Arv n = malloc(sizeof(struct arv));
    if (!n) {
        perror("Erro ao alocar nó");
        exit(1);
    }
    n->esq = e;
    n->item = x;
    n->dir = d;
    return n;
}

// -------------------- EXIBIÇÃO EM FORMATO DE ÁRVORE -------------------- //
// Exibe a árvore de forma estruturada (visualização vertical em ordem decrescente)
void exibe(Arv A, int n) {
    if (A == NULL) return;
    exibe(A->dir, n + 1);                      // Primeiro exibe a subárvore direita
    printf("%*s%d\n", 3 * n, "", A->item);     // Imprime o valor com indentação proporcional ao nível
    exibe(A->esq, n + 1);                      // Depois exibe a subárvore esquerda
}

// -------------------- ÁRVORE BINÁRIA COMPLETA -------------------- //
// Gera uma árvore binária completa de altura 'h' com valores aleatórios
Arv completa(int h) {
    if (h == 0) return NULL; // Altura 0 significa árvore vazia
    return arv(completa(h - 1), rand() % 100, completa(h - 1));
}

// -------------------- DESTRUIÇÃO (LIBERAÇÃO DE MEMÓRIA) -------------------- //
void destroi(Arv *A) {
    if (*A == NULL) return;
    destroi(&(*A)->esq);
    destroi(&(*A)->dir);
    free(*A);
    *A = NULL;
}

// -------------------- MAIN -------------------- //
int main() {
    srand(time(NULL)); // Semente para números aleatórios

    printf("Árvore Manual:\n");
    Arv A = arv(arv(NULL, 1, NULL), 2, arv(NULL, 3, NULL)); // árvore simples: 1 <- 2 -> 3
    exibe(A, 0);
    destroi(&A);

    printf("\nÁrvore Aleatória Completa (altura 3):\n");
    Arv B = completa(3); // altura 3 = 7 nós
    exibe(B, 0);
    destroi(&B);

    return 0;
}
