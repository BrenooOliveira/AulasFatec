
// Qual sera a sa�da, se o usuario digite as cadeias "um", "dois" e "tres"? Por qu�?
#include <stdio.h>
#include "pilha.h" // pilha de char *

int main (void) {
    Pilha P = pilha (5);
    char s[11]; // AQUI EST� O PROBLEMA!!!

    for(int i=1; i <= 3; i++) {
        printf("? ");
        gets(s) ;
        empilha(s,P);
    }
    while (!vaziap (P)) puts(desempilha(P));

return 0;
}

/*
EXPLICA��O:
char s possui um endere�o fixo na mem�ria, ou seja,
os tr�s registros que empilhamos em tempo de execu��o vao apontar para O MESMO ENDERE�O DE MEMORIA
isso, por sua vez, faz com que o ultimo elemento escrito sobrescreva todos os outros
pois est�o no mesmo endere�o de memoria.


/*
