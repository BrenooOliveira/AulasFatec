#include <stdio.h>
#include <ctype.h>
#include <string.h>
#include "pilha.h"

/*
Com base nos algoritmos descritos, crie um programa para ler uma expressão booleana
completamente parentesiada e exibir sua forma posfixa correspondente, bem como seu valor numérico.

Considere que as expressões são compostas por:
    -Operandos: letras maiúsculas F e V, com valores numéricos 0 e 1, respectivamente.
    -  Operadores: ! (não), & (e) e | (ou), da maior para a menor prioridade.
    - Delimitadores: parênteses de abertura e fechamento.

    -Por exemplo, para a expressão booleana infixa parentesiada "((!F)|(F&V))", o programa
    deve apresentar como saída a forma posfixa "F!FV&|" e o valor numérico 1.






*/

int prio(char o) {
    switch(o) {
        case '!' : return 3;
        case '&': return 2;
        case '|': return 1;
        case '(': return 0; // ( tem menor prioridade para controle

    }
    return -1; // operador inválido!
}

// Função que converte uma expressão infixa para pós-fixa
// in : ((!F)|(F&V))
// out: F!FV&|
char *posfixa(char *e){
    static char s[256];  // vetor que armazenará a expressão pos fixa
    int j = 0; // indice para inserção em s
    Pilha P = pilha(256);

    // percorre a infixa
    for(int i=0; e[i]; i++){
        if(e[i] == '('){
            empilha(e[i], P);
        }
        else if(e[i] == 'F' || e[i] == 'V'){
            s[j++] = e[i]; // operandos direto para a saida
        }
        else if(strchr("!&|", e[i])){ // se for um operador
            while (!vaziap(P) && prio(topo(P)) >= prio(e[i]))
            {
                s[j++] = desempilha(P); // desemp operadores de maior ou igual prioridade
            }
            empilha(e[i],P); // empilha o operador atual
        }
        else if(e[i] == ')'){
            // desemp ate encontrar '('
            while(topo(P) != '('){
                s[j++] = desempilha(P);
            }
            desempilha(P); // remove o ( da pilha
        }
    }
    // desemp operadors restantes
    while(!vaziap(P)){
        s[j++] = desempilha(P);
    }
    s[j] = '\0'; //finaliza a string
    destroip(&P);
    return s;
}

int avalia_posfixa(char *expr){
    Pilha(P) = pilha(256);
    for(int i = 0; expr[i]; i++){
        if(expr[i] == 'F'){
            empilha(0,P);
        }
        else if (expr[i] == 'V'){
            empilha(1,P);
        }
        else if (expr[i] == '!'){
            int a = desempilha(P);
            empilha(!a, P);// aplica negacao
        }else{
            int b = desempilha(P);
            int a = desempilha(P);
            switch (expr[i])
            {
            case '&':
                empilha(a & b,P); break;
            case '|':
                empilha(a | b, P); break;
            }
        }
    }
    int resultado = desempilha(P);
    destroip(&P);
    return resultado;
}

int main(void) {
    char e[513];

    printf("Infixa? ");
    gets(e);

    char *posfixa_expr = posfixa(e);
    printf("Posfixa: %s\n", posfixa_expr);
    printf("Resultado: %d\n", avalia_posfixa(posfixa_expr));

    return 0;
}
