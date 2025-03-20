#include <stdio.h>
#include "fila.h"

int main(void) {
    Fila F = fila(5);  // Cria uma fila com capacidade para 5 elementos

    // Enfileira os caracteres 'A', 'B', 'C' e 'D'
    for (int i = 0; i <= 3; i++)
        enfileira('A' + i, F);

    // Desenfileira e imprime cada caractere da fila
    while (!vaziaf(F))
        printf("%c\n", desenfileira(F));

    destroif(&F);  // Libera a memória da fila
    return 0;
}
