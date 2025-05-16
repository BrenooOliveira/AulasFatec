#include <stdio.h>
#include <stdlib.h>
#include <string.h>

// ------------------------- DEFINIÇÃO -------------------------
typedef char Chave[22];  // tipo das chaves
typedef char Valor[22];  // tipo dos valores

typedef struct map {
    Chave chave;
    Valor valor;
    struct map *prox;
} *Map;

// no_map: cria um novo nó com chave c, valor v e próximo nó p
Map no_map(Chave c, Valor v, Map p) {
    Map n = malloc(sizeof *n);
    if (!n) { perror("malloc"); exit(1); }
    strcpy(n->chave, c);
    strcpy(n->valor, v);
    n->prox  = p;
    return n;
}

// ------------------------- INSERÇÃO ITERATIVA -------------------------
void insm(Chave c, Valor v, Map *M) {
    while (*M && strcmp(c, (*M)->chave) > 0)
        M = &(*M)->prox;

    if (*M && strcmp(c, (*M)->chave) == 0)
        strcpy((*M)->valor, v);
    else
        *M = no_map(c, v, *M);
}

// ------------------------- INSERÇÃO RECURSIVA -------------------------
void insmr(Chave c, Valor v, Map *M) {
    if (!*M || strcmp(c, (*M)->chave) < 0)
        *M = no_map(c, v, *M);
    else if (strcmp(c, (*M)->chave) == 0)
        strcpy((*M)->valor, v);
    else
        insmr(c, v, &(*M)->prox);
}

// ------------------------- EXIBIÇÃO -------------------------
void exibem(Map M) {
    printf("[");
    while (M) {
        printf("(%s,%s)", M->chave, M->valor);
        if (M->prox) printf(",");
        M = M->prox;
    }
    printf("]\n");
}

// ------------------------- REMOÇÃO ITERATIVA -------------------------
void remm(Chave c, Map *M) {
    while (*M && strcmp(c, (*M)->chave) > 0)
        M = &(*M)->prox;
    if (!*M || strcmp(c, (*M)->chave) != 0)
        return;
    Map n = *M;
    *M = n->prox;
    free(n);
}

// ------------------------- REMOÇÃO RECURSIVA -------------------------
void remmr(Chave c, Map *M) {
    if (!*M) return;
    if (strcmp(c, (*M)->chave) < 0) return;
    if (strcmp(c, (*M)->chave) == 0) {
        Map n = *M;
        *M = n->prox;
        free(n);
    } else {
        remmr(c, &(*M)->prox);
    }
}

// ------------------------- PERTINÊNCIA ITERATIVA -------------------------
int pertm(Chave c, Valor v, Map M) {
    while (M && strcmp(c, M->chave) > 0)
        M = M->prox;
    if (M && strcmp(c, M->chave) == 0) {
        strcpy(v, M->valor);
        return 1;
    }
    return 0;
}

// ------------------------- PERTINÊNCIA RECURSIVA -------------------------
int pertmr(Chave c, Valor v, Map M) {
    if (!M || strcmp(c, M->chave) < 0)
        return 0;
    if (strcmp(c, M->chave) == 0) {
        strcpy(v, M->valor);
        return 1;
    }
    return pertmr(c, v, M->prox);
}

// ------------------------- DESTRUIÇÃO -------------------------
void destroim(Map *M) {
    Map cur = *M;
    while (cur) {
        Map next = cur->prox;
        free(cur);
        cur = next;
    }
    *M = NULL;
}

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

    insm("z36", "Leo", &I);
    insm("a15", "Ivo", &I);
    insmr("x42", "Eva", &I);
    insmr("b29", "Ana", &I);
    exibem(I);  // [(a15,Ivo),(b29,Ana),(x42,Eva),(z36,Leo)]

    insmr("b29", "Bia", &I);     // atualização
    exibem(I);                  // [(a15,Ivo),(b29,Bia),(x42,Eva),(z36,Leo)]

    remmr("z36", &I);           // remoção recursiva
    exibem(I);                  // [(a15,Ivo),(b29,Bia),(x42,Eva)]

    if (pertm("x42", w, I))
        printf("Chave x42: %s\n", w);
    else
        puts("Chave x42 inexistente!");

    destroim(&I);               // destruição
    return 0;
}
