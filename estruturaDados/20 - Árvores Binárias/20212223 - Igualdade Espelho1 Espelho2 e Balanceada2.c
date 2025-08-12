#include <stdio.h>
#include <stdlib.h>
#include <time.h>

// -------------------- MOLDE DA ARVORE BINÁRIA -------------------- //
typedef struct arv {
    struct arv *esq;
    int item;
    struct arv *dir;
} *Arv;

// Cria um novo nó com valor x e filhos esquerdo e direito
Arv arv(Arv e, int x, Arv d) {
    Arv n = malloc(sizeof(struct arv));
    n->esq = e;
    n->item = x;
    n->dir = d;
    return n;
}

// Exibe a árvore rotacionada no terminal (raiz no topo, direita à direita)
void exibe(Arv A, int n) {
    if (A == NULL) return;
    exibe(A->dir, n + 1);
    printf("%*s%d\n", 3 * n, "", A->item);
    exibe(A->esq, n + 1);
}

// Cria uma árvore binária aleatória com n nós
Arv aleatoria(int n) {
    if (n == 0) return NULL;
    int esquerda = rand() % n;
    int direita = n - 1 - esquerda;
    return arv(
        aleatoria(esquerda),
        rand() % 100,
        aleatoria(direita)
    );
}

// Cria uma árvore binária balanceada com n nós
Arv balanceada(int n) {
    if (n == 0) return NULL;
    int esquerda = (n - 1) / 2;
    int direita = n - 1 - esquerda;
    return arv(
        balanceada(esquerda),
        rand() % 100,
        balanceada(direita)
    );
}

// ------------------ FUNÇÕES COMPLEMENTARES ------------------ //
// Conta a quantidade de nós da árvore
int nos(Arv A) {
    if (A == NULL) return 0;
    return 1 + nos(A->esq) + nos(A->dir);
}
// Soma os valores dos nós da árvore
int soma(Arv A) {
    if (A == NULL) return 0;
    return A->item + soma(A->esq) + soma(A->dir);
}
// Conta a quantidade de folhas (nós sem filhos)
int folhas(Arv A) {
    if (A == NULL) return 0;
    if (A->esq == NULL && A->dir == NULL) return 1;
    return folhas(A->esq) + folhas(A->dir);
}
// Calcula a altura da árvore
int altura(Arv A) {
    if (A == NULL) return 0;
    int h_esq = altura(A->esq);
    int h_dir = altura(A->dir);
    return 1 + (h_esq > h_dir ? h_esq : h_dir);
}
// Clona a árvore A
Arv clone(Arv A) {
    if (A == NULL) return NULL;
    return arv(clone(A->esq), A->item, clone(A->dir));
}
// Verifica se o item x pertence à árvore A
int pertence(int x, Arv A) {
    if (A == NULL) return 0;
    if (A->item == x) return 1;
    return pertence(x, A->esq) || pertence(x, A->dir);
}

// ------------------ PERCURSOS ------------------ //
// RED
void pre_ordem(Arv A) {
    if (A == NULL) return;
    printf("%d ", A->item);
    pre_ordem(A->esq);
    pre_ordem(A->dir);
}
// ERD
void em_ordem(Arv A) {
    if (A == NULL) return;
    em_ordem(A->esq);
    printf("%d ", A->item);
    em_ordem(A->dir);
}
// EDR
void pos_ordem(Arv A) {
    if (A == NULL) return;
    pos_ordem(A->esq);
    pos_ordem(A->dir);
    printf("%d ", A->item);
}

void poda(Arv *A) {
    if (*A == NULL) return;
    if ((*A)->esq == NULL && (*A)->dir == NULL) {
        free(*A);
        *A = NULL;
        return;
    }
    poda(&(*A)->esq);
    poda(&(*A)->dir);
}

void destroi(Arv *A) {
    if (*A == NULL) return;
    destroi(&(*A)->esq);
    destroi(&(*A)->dir);
    free(*A);
    *A = NULL;
}

int conta(int x, Arv A) {
    if (A == NULL) return 0;
    return (A->item == x) + conta(x, A->esq) + conta(x, A->dir);
}

// Verifica se duas árvores são estruturalmente e valorativamente iguais
int iguais(Arv A, Arv B) {
    if (A == NULL && B == NULL) return 1;
    if (A == NULL || B == NULL) return 0;
    return (A->item == B->item && iguais(A->esq, B->esq) && iguais(A->dir, B->dir));
}

// Cria uma cópia espelhada da árvore
Arv espelhar(Arv A) {
    if (A == NULL) return NULL;
    return arv(espelhar(A->dir), A->item, espelhar(A->esq));
}

// Verifica se A é espelho de B
int eh_espelho(Arv A, Arv B) {
    if (A == NULL && B == NULL) return 1;
    if (A == NULL || B == NULL) return 0;
    return (A->item == B->item && eh_espelho(A->esq, B->dir) && eh_espelho(A->dir, B->esq));
}

// Cria árvore balanceada com os itens do vetor v[p..u]
Arv balanceada_vetor(int v[], int p, int u) {
    if (p > u) return NULL;
    int m = (p + u) / 2;
    return arv(
        balanceada_vetor(v, p, m - 1),
        v[m],
        balanceada_vetor(v, m + 1, u)
    );
}
int main() {
    srand(time(NULL));

    printf("---- ARVORE BALANCEADA ----\n");
    Arv B = balanceada(7);
    exibe(B, 0);

    Arv C = clone(B);
    printf("\nAs árvores B e C são iguais? %s\n", iguais(B, C) ? "Sim" : "Não");

    Arv E = espelhar(B);
    printf("\nÁrvore espelhada de B:\n");
    exibe(E, 0);
    printf("\nB e E são espelhos? %s\n", eh_espelho(B, E) ? "Sim" : "Não");

    int v[] = {10, 20, 30, 40, 50, 60, 70};
    Arv V = balanceada_vetor(v, 0, 6);
    printf("\nÁrvore balanceada a partir de vetor ordenado:\n");
    exibe(V, 0);

    printf("\nDestruindo todas as árvores...\n");
    destroi(&B);
    destroi(&C);
    destroi(&E);
    destroi(&V);

    return 0;
}
