#include <stdio.h>
#include <ctype.h>
#include <string.h>
#include "pilha.h"

/*
Crie um programa completo para ler uma expressão aritmética na forma infixa e exibir sua forma
posfixa correspondente, bem como seu valor numérico.

Por exemplo, para a expressão infixa "2*3+8/4", o programa deve apresentar como saída a
forma posfixa "23*84/+" e o valor numérico 8

*/


int prio(char o) {
    switch(o) {
        case '(' : return 0;
        case '+':
        case '-': return 1;
        case '*':
        case '/': return 2;
    }
    return -1; // operador inválido!
}

#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include <string.h>

// Função que converte uma expressão infixa para pós-fixa
char *posfixa(char *e) {
    static char s[256]; // Vetor para armazenar a expressão pós-fixa
    int j = 0; // Índice para inserção no vetor s
    Pilha P = pilha(256); // Cria uma pilha com capacidade para 256 elementos

    // Percorre a string de entrada (expressão infixa)
    for (int i = 0; e[i]; i++) {
        if (e[i] == '(')  
            empilha('(', P); // Se for um parêntese de abertura, empilha
        else if (isdigit(e[i]))  
            s[j++] = e[i]; // Se for um número, adiciona diretamente à saída
        else if (strchr("+-/*", e[i])) { // Se for um operador
            // Desempilha operadores de maior ou igual precedência e adiciona à saída
            while (!vaziap(P) && prio(topo(P)) >= prio(e[i]))
                s[j++] = desempilha(P);
            empilha(e[i], P); // Empilha o operador atual
        }
        else if (e[i] == ')') { 
            // Se for um parêntese de fechamento, desempilha até encontrar '('
            while (topo(P) != '(')
                s[j++] = desempilha(P);
            desempilha(P); // Remove o '(' da pilha
        }
    }

    // Desempilha quaisquer operadores restantes na pilha
    while (!vaziap(P))
        s[j++] = desempilha(P);

    s[j] = '\0'; // Finaliza a string com caractere nulo
    destroip(&P); // Libera a memória da pilha
    return s; // Retorna a expressão pós-fixa
}


int avalia_posfixa(char *expr) {
    Pilha P = pilha(256);
    for(int i = 0; expr[i]; i++) {
        if(isdigit(expr[i])) {
            empilha(expr[i] - '0', P); // subtraimos o numero que vem como str pelo '0', assim, na tabela ASCII, irá resultar no numero inteiro correspondente 
        } else {
            int b = desempilha(P);
            int a = desempilha(P);
            switch(expr[i]) {
                case '+': empilha(a + b, P); break;
                case '-': empilha(a - b, P); break;
                case '*': empilha(a * b, P); break;
                case '/': empilha(a / b, P); break;
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
