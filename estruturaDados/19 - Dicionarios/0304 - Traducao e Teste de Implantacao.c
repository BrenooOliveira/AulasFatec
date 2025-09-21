#include <stdio.h>
#include <stdlib.h>
#include <string.h>

// ------------------------- DEFINIÇÃO -------------------------
typedef char Chave[32];     // Chave agora é uma string de até 31 caracteres
typedef char Valor[22];     // Valor associado (string curta)

// Estrutura de um nó da lista encadeada (Map)
typedef struct map {
    Chave chave;
    Valor valor;
    struct map *prox;
} *Map;

// Estrutura do Dicionário (hash table)
typedef struct dic {
    int tam;     // Tamanho do vetor
    Map *vet;    // Vetor de ponteiros para Map
} *Dic;

// ------------------------- FUNÇÕES AUXILIARES -------------------------

// Cria novo nó Map
Map no_map(Chave c, Valor v, Map p) {
    Map n = malloc(sizeof *n);
    if (!n) { perror("malloc"); exit(1); }
    strcpy(n->chave, c);
    strcpy(n->valor, v);
    n->prox = p;
    return n;
}

// Função de hash para strings
unsigned hash(Chave c, int m) {
    unsigned s = 0;
    for (int i = 0; c[i]; i++)
        s += (i + 1) * c[i]; // (posição+1) * caractere ASCII
    return s % m;
}

// Cria dicionário com m buckets
Dic dic(int m) {
    Dic d = malloc(sizeof *d);
    d->tam = m;
    d->vet = malloc(m * sizeof(Map));
    for (int i = 0; i < m; i++) d->vet[i] = NULL;
    return d;
}

// ------------------------- INSERÇÃO -------------------------
void insd(Chave c, Valor v, Dic d) {
    int i = hash(c, d->tam);
    Map *M = &d->vet[i];
    while (*M && strcmp(c, (*M)->chave) > 0)
        M = &(*M)->prox;
    if (*M && strcmp(c, (*M)->chave) == 0)
        strcpy((*M)->valor, v); // atualiza
    else
        *M = no_map(c, v, *M); // insere
}

// ------------------------- REMOÇÃO -------------------------
void remd(Chave c, Dic d) {
    int i = hash(c, d->tam);
    Map *M = &d->vet[i];
    while (*M && strcmp(c, (*M)->chave) > 0)
        M = &(*M)->prox;
    if (*M && strcmp(c, (*M)->chave) == 0) {
        Map n = *M;
        *M = n->prox;
        free(n);
    }
}

// ------------------------- PERTINÊNCIA -------------------------
int pertm(Chave c, Valor v, Map M) {
    while (M && strcmp(c, M->chave) > 0)
        M = M->prox;
    if (M && strcmp(c, M->chave) == 0) {
        strcpy(v, M->valor);
        return 1;
    }
    return 0;
}

int pertd(Chave c, Valor v, Dic d) {
    return pertm(c, v, d->vet[hash(c, d->tam)]);
}

// ------------------------- EXIBIÇÃO -------------------------
void exibed(Dic d) {
    for (int i = 0; i < d->tam; i++) {
        printf("%d: [", i);
        Map M = d->vet[i];
        while (M) {
            printf("(%s,%s)", M->chave, M->valor);
            if (M->prox) printf(", ");
            M = M->prox;
        }
        printf("]\n");
    }
}

// ------------------------- DESTRUIÇÃO -------------------------
void destroim(Map *M) {
    while (*M) {
        Map n = *M;
        *M = n->prox;
        free(n);
    }
}

void destroid(Dic *d) {
    for (int i = 0; i < (*d)->tam; i++)
        destroim(&(*d)->vet[i]);
    free((*d)->vet);
    free(*d);
    *d = NULL;
}

// ------------------------- MAIN -------------------------
int main(void) {
    Dic D = dic(5); // cria dicionário com 5 buckets

    insd("lista", "list", D);
    insd("mapa", "map", D);
    insd("dicionario", "dictionary", D);
    insd("arvore binaria", "binary tree", D);

    printf("Dicionario inicial:\n");
    exibed(D);

    Valor v;
    if (pertd("lista", v, D))
        printf("\nValor da chave 'lista': %s\n", v);
    else
        printf("\nChave 'lista' nao encontrada.\n");

    remd("lista", D);
    printf("\nDicionario apos remover 'lista':\n");
    exibed(D);

    destroid(&D);
    return 0;
}
