#include <stdio.h>
#include <stdlib.h>

// ------------------------- DEFINIÇÃO -------------------------
typedef int   Chave;   // tipo das chaves
typedef int   Valor;   // tipo dos valores

typedef struct map {
    Chave      chave;
    Valor      valor;
    struct map *prox;
} *Map;               // Map é ponteiro para o primeiro nó

// no_map: cria um novo nó com chave c, valor v e próximo nó p
Map no_map(Chave c, Valor v, Map p) {
    Map n = malloc(sizeof *n);
    if (!n) { perror("malloc"); exit(1); }
    n->chave = c;
    n->valor = v;
    n->prox  = p;
    return n;
}

// ------------------------- INSERÇÃO ITERATIVA -------------------------
void insm(Chave c, Valor v, Map *M) {
    // avança até posição de inserir ou encontrar
    while (*M && c > (*M)->chave)
        M = &(*M)->prox;
    // se encontrou chave igual, atualiza
    if (*M && c == (*M)->chave)
        (*M)->valor = v;
    else
        // insere novo nó antes de *M
        *M = no_map(c, v, *M);
}

// ------------------------- INSERÇÃO RECURSIVA -------------------------
void insmr(Chave c, Valor v, Map *M) {
    if (!*M || c < (*M)->chave) {
        *M = no_map(c, v, *M);
    }
    else if (c == (*M)->chave) {
        (*M)->valor = v;
    }
    else {
        insmr(c, v, &(*M)->prox);
    }
}

// ------------------------- EXIBIÇÃO -------------------------
void exibem(Map M) {
    printf("[");
    while (M) {
        printf("(%d,%d)", M->chave, M->valor);
        if (M->prox) printf(",");
        M = M->prox;
    }
    printf("]\n");
}

// ------------------------- REMOÇÃO ITERATIVA -------------------------
void remm(Chave c, Map *M) {
    while (*M && c > (*M)->chave)
        M = &(*M)->prox;
    if (!*M || c != (*M)->chave)
        return;                  // não encontrou
    Map n = *M;
    *M = n->prox;
    free(n);
}

// ------------------------- REMOÇÃO RECURSIVA -------------------------
void remmr(Chave c, Map *M) {
    if (!*M) return;             // lista vazia
    if (c < (*M)->chave) return; // não existe
    if (c == (*M)->chave) {
        Map n = *M;
        *M = n->prox;
        free(n);
    }
    else {
        remmr(c, &(*M)->prox);
    }
}

// ------------------------- PERTINÊNCIA ITERATIVA -------------------------
// agora v é ponteiro para Valor, para copiar o valor encontrado
int pertm(Chave c, Valor *v, Map M) {
    while (M && c > M->chave)
        M = M->prox;
    if (M && c == M->chave) {
        *v = M->valor;
        return 1;
    }
    return 0;
}

// ------------------------- PERTINÊNCIA RECURSIVA -------------------------
int pertmr(Chave c, Valor *v, Map M) {
    if (!M || c < M->chave)
        return 0;
    if (c == M->chave) {
        *v = M->valor;
        return 1;
    }
    return pertmr(c, v, M->prox);
}

// ------------------------- DESTRUIÇÃO ITERATIVA -------------------------
void destroim(Map *M) {
    Map cur = *M;
    while (cur) {
        Map next = cur->prox;
        free(cur);
        cur = next;
    }
    *M = NULL;
}

// ------------------------- DESTRUIÇÃO RECURSIVA -------------------------
void destroimr(Map *M) {
    if (!*M) return;
    destroimr(&(*M)->prox);
    free(*M);
    *M = NULL;
}

// ------------------------- EXEMPLO DE USO -------------------------
int main(void) {
    Map I = NULL;
    Valor w;

    // inserções
    insm(36, 100, &I);
    insm(15, 200, &I);
    insmr(42, 300, &I);
    insmr(29, 400, &I);
    exibem(I);

    // atualização e remoções
    insmr(29, 450, &I); // inserção recursiva
    exibem(I);  // [(15,200),(29,450),(36,100),(42,300)]
    remmr(36, &I); // remocao recursiva
    exibem(I);  // [(15,200),(29,450),(42,300)]

    // pertinência
    if (pertm(42, &w, I))
        printf("Chave 42: %d\n", w);
    else
        puts("Chave 42 inexistente!");

    // destruição
    destroim(&I);
    return 0;
}
