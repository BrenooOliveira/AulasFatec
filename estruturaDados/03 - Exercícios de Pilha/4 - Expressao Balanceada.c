/*
usando pilha, crie uma funcao para verif se uma expressao composta por chaves, colchetes
e parenteses, representada por uma cadeira de cadeia de caracteres
est� ou nao balanceada.

por exemplo:
{[[]} n�o est� balanceada
[({)]} est� balanceada
*/

#include <stdio.h>
#include <string.h>
#include "pilha.h"

// Fun��o para verificar se os caracteres formam pares v�lidos
int par_correspondente(char abre, char fecha) {
    return (abre == '(' && fecha == ')') ||
           (abre == '[' && fecha == ']') ||
           (abre == '{' && fecha == '}');
}

// Fun��o para verificar se a express�o est� balanceada
int verifica_balanceamento(char *expressao) {
    // se encontrar um abre, empilhamos
    // se encontrar uma fecha, analisamos
    int n = strlen(expressao);
    Pilha *P = pilha(n);

    for (int i = 0; i < n; i++) {
        char c = expressao[i];

        if (c == '(' || c == '[' || c == '{') {
            empilha(c, P);
        } else if (c == ')' || c == ']' || c == '}') {
            if (vaziap(P) || !par_correspondente(desempilha(P), c)) {
                destroip(&P);
                return 0; // N�o balanceado
            }
        }
    }

    int resultado = vaziap(P); // A pilha deve estar vazia no final
    destroip(&P);
    return resultado;
}

int main() {
    char expressao[100];

    printf("Digite a expressao: ");
    scanf("%s", &expressao);

    if (verifica_balanceamento(expressao)) {
        printf("A expressao esta balanceada.\n");
    } else {
        printf("A expressao nao esta balanceada.\n");
    }

    return 0;
}
