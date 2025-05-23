#include <stdio.h>
#include <stdlib.h>
#include <string.h>

typedef int Chave;           // Tipo da chave (aqui é um número inteiro)
typedef char Valor[22];      // Valor associado à chave (uma string com até 21 caracteres + \0)

// ------------------------- DEFINIÇÃO -------------------------

// Definição de um nó da lista (Map)
typedef struct map {
    Chave chave;
    Valor valor;
    struct map *prox;
} *Map;

// Estrutura do Dicionário (tabela de hash com encadeamento)
typedef struct dic {
    int tam;    // Tamanho da tabela hash
    Map *vet;   // Vetor de listas (cada posição armazena um Map encadeado)
} *Dic;

// ------------------------- FUNÇÕES AUXILIARES -------------------------

// Cria um novo nó com chave c, valor v e próximo p
Map no_map(Chave c, Valor v, Map p) {
    Map n = malloc(sizeof *n);
    if (!n) { perror("malloc"); exit(1); }
    n->chave = c;
    strcpy(n->valor, v);
    n->prox = p;
    return n;
}

// Função de hashing simples: resto da divisão pela capacidade da tabela
int hash(Chave c, int m) {
    return c % m;
}

// Cria um novo dicionário com m posições
Dic dic(int m) {
    Dic d = malloc(sizeof *d);
    d->tam = m;
    d->vet = malloc(m * sizeof(Map));
    for (int i = 0; i < m; i++) d->vet[i] = NULL;
    return d;
}

// ------------------------- INSERÇÃO -------------------------

// Insere (chave, valor) no dicionário
void insd(Chave c, Valor v, Dic d) {
    int i = hash(c, d->tam);
    Map *M = &d->vet[i];
    while (*M && c > (*M)->chave)
        M = &(*M)->prox;
    if (*M && c == (*M)->chave)
        strcpy((*M)->valor, v); // Atualiza valor se a chave já existir
    else
        *M = no_map(c, v, *M);  // Insere novo nó na lista
}

// ------------------------- REMOÇÃO -------------------------

// Remove uma chave do dicionário
void remd(Chave c, Dic d) {
    int i = hash(c, d->tam);
    Map *M = &d->vet[i];
    while (*M && c > (*M)->chave)
        M = &(*M)->prox;
    if (*M && c == (*M)->chave) {
        Map n = *M;
        *M = n->prox;
        free(n);
    }
}

// ------------------------- PERTINÊNCIA -------------------------

// Verifica se chave está presente na lista encadeada Map
int pertm(Chave c, Valor v, Map M) {
    while (M && c > M->chave)
        M = M->prox;
    if (M && c == M->chave) {
        strcpy(v, M->valor);
        return 1; // Achou
    }
    return 0; // Não achou
}

// Verifica se chave está presente no dicionário
int pertd(Chave c, Valor v, Dic d) {
    return pertm(c, v, d->vet[hash(c, d->tam)]);
}

// ------------------------- EXIBIÇÃO -------------------------

// Exibe o conteúdo do dicionário
void exibed(Dic d) {
    for (int i = 0; i < d->tam; i++) {
        printf("%d: [", i);
        Map M = d->vet[i];
        while (M) {
            printf("(%d,%s)", M->chave, M->valor);
            if (M->prox) printf(", ");
            M = M->prox;
        }
        printf("]\n");
    }
}

// ------------------------- DESTRUIÇÃO -------------------------

// Destroi uma lista Map
void destroim(Map *M) {
    while (*M) {
        Map n = *M;
        *M = n->prox;
        free(n);
    }
}

// Destroi todo o dicionário
void destroid(Dic *d) {
    for (int i = 0; i < (*d)->tam; i++)
        destroim(&(*d)->vet[i]);
    free((*d)->vet);
    free(*d);
    *d = NULL;
}

// ------------------------- MAIN -------------------------

int main(void) {
    Dic D = dic(5); // cria dicionário com 5 posições

    // Inserção
    insd(0,"Breno",D);
    insd(1,"Fretz",D);
    insd(2,"Anne",D);
    insd(3,"Bianca",D);
    insd(4,"Day",D);

    printf("Dicionário:\n");
    exibed(D);

    printf("\nRemovendo 1, 2 e 3:\n");
    remd(1,D);
    remd(2,D);
    remd(3,D);
    exibed(D);

    printf("\nVerificando pertinência da chave 4:\n");
    Valor nome;
    if (pertd(4, nome, D))
        printf("Chave 4 encontrada com valor: %s\n", nome);
    else
        printf("Chave 4 não encontrada.\n");

    printf("\nVerificando pertinência da chave 3:\n");
    if (pertd(3, nome, D))
        printf("Chave 3 encontrada com valor: %s\n", nome);
    else
        printf("Chave 3 não encontrada.\n");

    // Destruir o dicionário
    destroid(&D);

    return 0;
}
