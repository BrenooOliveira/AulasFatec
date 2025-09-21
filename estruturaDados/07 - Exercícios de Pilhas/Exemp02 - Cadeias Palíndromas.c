#include <stdio.h>
#include <ctype.h>
#include "pilha.h"
#include "fila.h"

int main(void) {
    char s[256];       // Declara um array de caracteres para armazenar a entrada do usuário
    Fila F = fila(256); // Cria uma fila com capacidade para 256 elementos
    Pilha P = pilha(256); // Cria uma pilha com capacidade para 256 elementos

    // Solicita ao usuário que insira uma frase
    printf("\nFrase? ");
    gets(s);

    // Percorre a string e adiciona apenas caracteres alfabéticos à fila e à pilha
    for (int i = 0; s[i]; i++) {
        if (isalpha(s[i])) { // Verifica se o caractere é uma letra
            enfileira(s[i], F); // Adiciona à fila
            empilha(s[i], P); // Adiciona à pilha
        }
    }

    // Compara os elementos removidos da fila e da pilha
    // Se forem iguais até a fila esvaziar, a frase é palíndroma
    while (!vaziaf(F) && desenfileira(F) == desempilha(P));

    // Se a fila esvaziou completamente, significa que é um palíndromo
    if (vaziaf(F)) {
        puts("A frase e palindroma");
    } else {
        puts("A frase NAO e palindroma");
    }

    // Libera a memória alocada para a fila e a pilha
    destroif(&F);
    destroip(&P);

    return 0;
}
