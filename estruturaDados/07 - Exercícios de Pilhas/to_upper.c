#include <stdio.h>
#include <ctype.h>
#include "pilha.h"
#include "fila.h"

void strMaiuscula(char *str){
    for(int i=0; str[i] != '\0'; i++){
        str[i] = toupper(str[i]);
    }
}

int main(void) {
    char s[256];       // Declara um array de caracteres para armazenar a entrada do usuário
    Fila F = fila(256); // Cria uma fila com capacidade para 256 elementos
    Pilha P = pilha(256); // Cria uma pilha com capacidade para 256 elementos
    
    // Solicita ao usuário que insira uma frase
    printf("\nFrase? ");
    gets(s);  
    strMaiuscula(s);
    printf("maiuscula: %s",s);
}
