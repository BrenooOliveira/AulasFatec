#include <stdio.h>
#include <ctype.h>
#include <string.h>
#include "pilha.h"

/*
inversao de strings
*/
char *inverte_infixa(char *e){
    int tamanho = strlen(e);
    Pilha P = pilha(tamanho);
    char *s = malloc(strlen(e) + 1); // Vetor para armazenar a expressão pós-fixa
    int j = 0; // Índice para inserção no vetor s
    
    for(int i = 0; e[i]; i++){ // para cada elemento da expressão
        if(e[i] == '('){
            empilha(')',P);
        }
        else if(e[i] == ')'){
            empilha('(',P);
        }
        else empilha(e[i],P);
    }
    while (!vaziap(P))
    {
        s[j++] = desempilha(P);
    };

    s[j] = '\0'; // Finaliza a string
    destroip(&P);
    return s;
}


//(1*2)+3
// 3+(2*1)  
int main(void){
    char e[512] = "(1*2)+3";

    char *invertida = inverte_infixa(e);
    printf("char: %s\n",e);
    printf("invetida: %s",invertida);

    free(invertida); // Liberar a memória alocada

}