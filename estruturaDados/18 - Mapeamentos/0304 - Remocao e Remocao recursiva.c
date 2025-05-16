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

// ------------------------- REMOÇÃO -------------------------
// Função para remover (chave, valor) de Map ordenado por chave
void remm(Chave c, Map *M) {
    while (*M && c > (*M)->chave)
        M = &(*M)->prox;
    if (!*M || c != (*M)->chave)
        return;                  // não encontrou
    Map n = *M;
    *M = n->prox;
    free(n);
}
// Função recursiva para remover (chave, valor) de Map ordenado por chave
void remmr(Chave c, Map *M) {
    if (*M == NULL) return; // Caso base: lista vazia
    if (c < (*M)->chave) {
        // Chave não encontrada, continua a busca recursiva
        remmr(c, &(*M)->prox);
    } else if (c == (*M)->chave) {
        // Chave encontrada, remove o nó atual
        Map n = *M;
        *M = n->prox;
        free(n);
    } else {
        // Chave ainda não encontrada, continua a busca recursiva
        remmr(c, &(*M)->prox);
    }
}


int main(void) {
    Map I = NULL;
    
    insmr(36,"Leo",&I); insmr(15,"Ivo",&I);
    insmr(42,"Eva",&I); insmr(29,"Ana",&I);

    exibem(I);

    remm(29,&I); // remove (29,"Ana") de I

    exibem(I);

    remmr(42,&I); // remove (42,"Eva") de I recursivamente

    exibem(I);
    
    return 0;
}