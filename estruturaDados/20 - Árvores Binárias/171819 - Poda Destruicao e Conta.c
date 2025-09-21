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

    // Conta quantos nós existem na árvore
    int nos(Arv A) {
        if (A == NULL) return 0;
        return 1 + nos(A->esq) + nos(A->dir);
    }

    // Soma todos os itens da árvore
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

    // Verifica se um valor x está presente na árvore
    int pertence(int x, Arv A) {
        if (A == NULL) return 0;
        if (A->item == x) return 1;
        return pertence(x, A->esq) || pertence(x, A->dir);
    }

    // ------------------ PERCURSOS ------------------ //
    void pre_ordem(Arv A) {
        if (A == NULL) return;
        printf("%d ", A->item);
        pre_ordem(A->esq);
        pre_ordem(A->dir);
    }

    void em_ordem(Arv A) {
        if (A == NULL) return;
        em_ordem(A->esq);
        printf("%d ", A->item);
        em_ordem(A->dir);
    }

    void pos_ordem(Arv A) {
        if (A == NULL) return;
        pos_ordem(A->esq);
        pos_ordem(A->dir);
        printf("%d ", A->item);
    }

    // Remove todas as folhas da árvore (poda)
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

    // Destroi completamente a árvore
    void destroi(Arv *A) {
        if (*A == NULL) return;
        destroi(&(*A)->esq);
        destroi(&(*A)->dir);
        free(*A);
        *A = NULL;
    }

    // Conta quantas vezes o item x aparece na árvore
    int conta(int x, Arv A) {
        if (A == NULL) return 0;
        return (A->item == x) + conta(x, A->esq) + conta(x, A->dir);
    }

    // ------------------------- PROGRAMA PRINCIPAL ------------------------- //
    int main() {
        srand(time(NULL));

        printf("---- ARVORE BALANCEADA ----\n");
        Arv B = balanceada(7);
        exibe(B, 0);

        printf("\nPercursos da árvore:\n");
        printf("Pré-ordem: "); pre_ordem(B); printf("\n");
        printf("Em ordem:  "); em_ordem(B);  printf("\n");
        printf("Pós-ordem: "); pos_ordem(B); printf("\n");

        printf("\nEstatísticas:\n");
        printf("Nº de nós: %d\n", nos(B));
        printf("Soma: %d\n", soma(B));
        printf("Nº de folhas: %d\n", folhas(B));
        printf("Altura: %d\n", altura(B));
        printf("Ocorrências do número 42: %d\n", conta(42, B));

        printf("\nPoda da árvore...\n");
        poda(&B);
        exibe(B, 0);

        printf("\nDestruindo árvore...\n");
        destroi(&B);
        exibe(B, 0);

        return 0;
    }
