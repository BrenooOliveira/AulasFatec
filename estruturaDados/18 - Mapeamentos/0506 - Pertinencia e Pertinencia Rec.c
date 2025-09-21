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

// ------------------------- PERTINENCIA -------------------------
// Função para verificar se uma chave pertence ao Map
int pertm(Chave c,Valor v, Map M) {
    while (M && c > M->chave)
        M = M->prox;
    if (!M || c != M->chave)
        return 0; // não pertence
    strcpy(v, M->valor);
    return 1; // pertence
}

// Função recursiva para verificar se chave 'c' está presente em *M
// Se estiver, copia o valor correspondente para 'v' e retorna 1
// Caso contrário, retorna 0
int pertmr(Chave c, Valor v, Map *M) {
    if (*M == NULL) {
        // Caso base: chegou ao fim da lista sem encontrar
        return 0;
    }
    if (c == (*M)->chave) {
        // Encontrou a chave, copia o valor e retorna 1
        strcpy(v, (*M)->valor);
        return 1;
    }
    if (c < (*M)->chave) {
        // Como a lista é ordenada, chave não está presente
        return 0;
    }
    // Continua a busca recursiva no próximo nó
    return pertmr(c, v, &(*M)->prox);
}




int main(void) {
    Valor w;
    Map I = NULL;
    insm(36,"Leo",&I); insm(15,"Ivo",&I); insm(42,"Eva",&I);
    exibem(I);

    if( pertm(42,w,I) ) printf("Valor da chave 42: %s\n",w); // Versão Interativa
    else puts("Chave 42 inexistente!");

    if( pertmr(10,w,&I) ) printf("Valor da chave 10: %s\n",w); // Versão recursiva
    else puts("Chave 10 inexistente!");

    return 0;
}
