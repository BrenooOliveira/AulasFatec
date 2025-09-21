#include <stdio.h>
#include <ctype.h>
#include <string.h>
#include "pilha.h"

/*
Digite e teste o programa do Exemplo 4, com as seguintes expressões infixas (coloque todos os
parênteses necessários para que o programa funcione corretamente):

(2 * 3) + (8 / 4)
((9 - 5) - 1)
(2 + (3 * 4)) - 5
((3 + 4) * (8 - 6)) / 2

*/


char *posfixa (char *e) {
    static char s[256];
    int j = 0;
    Pilha P = pilha (256) ;
    for (int i=0; e[i]; i++)
        if( isdigit(e[i]) ) s[j++] = e[i];
        else if ( strchr ("+ *- /",e[i]) ) empilha(e[i],P);
        else if ( e[i] == ')' ) s[j++] = desempilha (P);
    s [j] ='\0';
    destroip (&P);
    return s;

}


int main(void) {
    char e[513];

    for (int i = 0; i < 5; i++)
    {
        printf("Infixa? ");
        gets(e);
        printf("Posfixa: %s \n", posfixa(e));
    }

    return 0;
}
