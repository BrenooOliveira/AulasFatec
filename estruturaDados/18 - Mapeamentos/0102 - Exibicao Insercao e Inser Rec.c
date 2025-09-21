#include <stdio.h>
#include <stdlib.h>
#include <string.h>
// ------------------------- DEFINIÇAO -------------------------
// define MAP_H
typedef int   Chave;          // tipo das chaves
typedef char  Valor[22];      // tipo dos valores
typedef struct map {
    Chave      chave;
    Valor      valor;
    struct map *prox;
} *Map;                      // Map é ponteiro para o primeiro nó

// no_map: cria um novo nó com chave c, valor v e próximo nó p
Map no_map(Chave c, Valor v, Map p) {
    Map n = malloc(sizeof *n);
    if (!n) { perror("malloc"); exit(1); }
    n->chave = c;
    strcpy(n->valor, v);
    n->prox  = p;
    return n;
}

// ------------------------- INSERÇÃO -------------------------
//insere um novo nó na lista encadeada
void insm(Chave c, Valor v, Map *M) {
    // avança até posição de inserir ou encontrar
    while (*M && c > (*M)->chave)
        M = &(*M)->prox;
    // se encontrou chave igual, atualiza
    if (*M && c == (*M)->chave)
        strcpy((*M)->valor, v);
    else
        // insere novo nó antes de *M
        *M = no_map(c, v, *M);
}

// Função recursiva para inserir (chave, valor) em Map ordenado por chave
void insmr(Chave c, Valor v, Map *M) {
    if (*M == NULL || c < (*M)->chave) {
        // Caso base: insere novo nó aqui
        *M = no_map(c, v, *M);
    } else if (c == (*M)->chave) {
        // Atualiza valor se chave já existe
        strcpy((*M)->valor, v);
    } else {
        // Chave ainda não encontrada, continua a busca recursiva
        insmr(c, v, &(*M)->prox);
    }
}

// ------------------------- EXIBIÇÃO -------------------------
// Função para exibir o Map
void exibem(Map M) {
    printf("[");
    while (M) {
        printf("(%d,%s)", M->chave, M->valor);
        if (M->prox) printf(",");
        M = M->prox;
    }
    printf("]\n");
}


int main(void) {
    Map I = NULL;
    insm(36,"Leo",&I); // insere (36,"Leo") em I
    insmr(15,"Ivo",&I); // insere (15,"Ivo") em I recursivamente

    insm(42,"Eva",&I);
    insmr(29,"Ana",&I);

    exibem(I);

    insm(29,"Bia",&I);
    insmr(42,"Breno",&I); // Atualiza (42,"Eva") para (42,"Breno") recursivamente

    exibem(I);
    return 0;
}
