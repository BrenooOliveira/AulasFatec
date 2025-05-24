#include <stdio.h>
#include <stdlib.h>
#include <time.h>

// -------------------- MOLDE DA ARVORE BINÁRIA -------------------- //
// ------  Estrutura de um nó da árvore binária ------ //
typedef struct arv {
    struct arv *esq;  // galho da esquerda
    int item;         // o dado guardado
    struct arv *dir;  // galho da direita
} *Arv;

// ------------------------- GALHOS E FOLHAS ------------------------- //
// ------  Cria um nó com filhos esquerdo, direito e item ------ //
Arv arv(Arv e, int x, Arv d) {
    Arv n = malloc(sizeof(struct arv));
    n->esq = e;
    n->item = x;
    n->dir = d;
    return n;
}

// ------------------------- EXIBIÇÃO ------------------------- //
// ------  Exibe visualmente a árvore no terminal ------ //
void exibe(Arv A, int n) {
    if (A == NULL) return;
    exibe(A->dir, n + 1);
    printf("%*s%d\n", 3 * n, "", A->item);
    exibe(A->esq, n + 1);
}

// -------------- FUNÇÃO ALEATÓRIA --------------- //
// ------  Cria uma árvore binária aleatória com n nós ------ //
Arv aleatoria(int n) {
    if (n == 0) return NULL;
    int esquerda = rand() % n;              // Sorteia quantos vão pra esquerda
    int direita = n - 1 - esquerda;         // O resto vai pra direita
    return arv(
        aleatoria(esquerda),
        rand() % 100,                       // Número aleatório de 0 a 99
        aleatoria(direita)
    );
}

// -------------- FUNÇÃO BALANCEADA --------------- //
// ------  Cria uma árvore binária balanceada com n nós ------ //
Arv balanceada(int n) {
    if (n == 0) return NULL;
    int esquerda = (n - 1) / 2;             // Divide os nós para a esquerda
    int direita = n - 1 - esquerda;         // E o restante para a direita
    return arv(
        balanceada(esquerda),
        rand() % 100,                       // Número aleatório de 0 a 99
        balanceada(direita)
    );
}

// ------------------------- PROGRAMA PRINCIPAL ------------------------- //
int main() {
    srand(time(NULL)); // ------  Inicializa gerador de números aleatórios ------ //

    // ------  Cria e exibe uma árvore balanceada com 7 nós ------ //
    printf("---- ARVORE BALANCEADA ----\n");
    Arv B = balanceada(7);
    exibe(B, 0);

    printf("\n\n");

    // ------  Cria e exibe uma árvore aleatória com 7 nós ------ //
    printf("---- ARVORE ALEATORIA ----\n");
    Arv A = aleatoria(7);
    exibe(A, 0);

    return 0;
}
