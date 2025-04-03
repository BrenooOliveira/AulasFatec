#include <stdio.h>
#include "pilha.h"
#include <stdlib.h>
#include <string.h>

/*
usando uma pilha, crie um programa para inverter a ordem das letras nas plavras de uma frase,
sem inverter a order das palavras na frase.
Por exemplo: se for digitada: "apenas um teste", deve retornar "senepa mu etset"

*/

#define MAX_VALUE 256

void inverte_palavras(char *frase,int *len_frase){

    Pilha *A = pilha(len_frase);

    for(int i = 0; i < len_frase; i++){
        if(frase[i] != ' ' && frase[i] != '\n'){
            empilha(frase[i],A);
        }
        else{
            while(!vaziap(A)){
                printf("%c",desempilha(A));
            }
        printf(" "); // mantendo o espaco entre as palavras
        }

    }

    // imprimindo a ultima palavra
    while(!vaziap(A)){
        printf("%c",desempilha(A));
    }

return 0;

}

// Fun��o principal
int main() {
    char frase[MAX_VALUE];

    printf("Digite uma frase: ");
    gets(frase);
    int memoriaAloc, len_frase = strlen(frase);

    inverte_palavras(frase, len_frase);

return 0;
}
