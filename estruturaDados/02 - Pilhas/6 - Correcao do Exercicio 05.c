#include <stdio.h>
#include <stdlib.h>  // Para malloc, free e _strdup
#include <string.h>
#include "pilha.h" // pilha de char *

int main(void) {
    Pilha P = pilha(5);
    char s[11];

    for (int i = 1; i <= 3; i++) {
        printf("? ");
        gets(s);

        char *str = _strdup(s);  // Duplicamos a string corretamente

        empilha(str, P);
    }

    while (!vaziap(P)) {
        char *str = desempilha(P);
        puts(str);
        free(str);  // Libera a memória alocada
    }

    return 0;
}
