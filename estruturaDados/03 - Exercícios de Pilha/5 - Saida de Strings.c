
// Qual sera a saída, se o usuario digite as cadeias "um", "dois" e "tres"? Por quê?
#include <stdio.h>
#include "pilha.h" // pilha de char *

int main (void) {
    Pilha P = pilha (5);
    char s[11]; // AQUI ESTÁ O PROBLEMA!!!

    for(int i=1; i <= 3; i++) {
        printf("? ");
        gets(s) ;
        empilha(s,P);
    }
    while (!vaziap (P)) puts(desempilha(P));

return 0;
}

/*
EXPLICAÇÃO:
char s possui um endereço fixo na memória, ou seja,
os três registros que empilhamos em tempo de execução vao apontar para O MESMO ENDEREÇO DE MEMORIA
isso, por sua vez, faz com que o ultimo elemento escrito sobrescreva todos os outros
pois estão no mesmo endereço de memoria.


/*
