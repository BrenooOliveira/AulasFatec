#include <stdio.h>
#include <ctype.h>
#include <string.h>
#include <stdlib.h>
#include "pilha.h"

// Função para definir a precedência dos operadores
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

// Converte uma expressão infixa para pós-fixa com alocação dinâmica
char *posfixa(char *e) {
    char *s = malloc(strlen(e) + 1); // Aloca memória dinâmica para a saída
    int j = 0;
    Pilha P = pilha(strlen(e));

    for (int i = 0; e[i]; i++) {
        if (e[i] == '(')  
            empilha('(', P);
        else if (isdigit(e[i]))  
            s[j++] = e[i];
        else if (strchr("+-/*", e[i])) {
            while (!vaziap(P) && prio(topo(P)) >= prio(e[i]))
                s[j++] = desempilha(P);
            empilha(e[i], P);
        }
        else if (e[i] == ')') { 
            while (topo(P) != '(')
                s[j++] = desempilha(P);
            desempilha(P);
        }
    }

    while (!vaziap(P))
        s[j++] = desempilha(P);

    s[j] = '\0';
    destroip(&P);
    return s;
}

// Função para inverter a expressão infixa
char *inverte_infixa(char *e){
    int tamanho = strlen(e);
    Pilha P = pilha(tamanho);
    char *s = malloc(tamanho + 1);
    int j = 0;
    
    for(int i = 0; e[i]; i++){ 
        if(e[i] == '('){
            empilha(')', P);
        }
        else if(e[i] == ')'){
            empilha('(', P);
        }
        else empilha(e[i], P);
    }
    while (!vaziap(P))
        s[j++] = desempilha(P);

    s[j] = '\0';
    destroip(&P);
    return s;
}

// Função para inverter uma string alocando nova memória
char *strrev_malloc(char *str) {
    int len = strlen(str);
    char *rev = malloc(len + 1); // Nova string alocada
    for (int i = 0; i < len; i++)
        rev[i] = str[len - 1 - i];
    rev[len] = '\0';
    return rev;
}

// Função para validar o resultado da prefixa
int valpre(char *e) {
    Pilha P = pilha(strlen(e)); // Criamos uma pilha para armazenar os valores

    // Percorremos a expressão da DIREITA para a ESQUERDA
    for (int i = strlen(e) - 1; i >= 0; i--) {
        if (isdigit(e[i])) {
            // Se for um número, convertemos e empilhamos
            empilha(e[i] - '0', P);  
        } 
        else if (strchr("+-*/", e[i])) {
            // Se for um operador, desempilhamos os dois operandos
            int a = desempilha(P);
            int b = desempilha(P);
            int resultado;

            // Aplicamos a operação
            switch (e[i]) {
                case '+': resultado = a + b; break;
                case '-': resultado = a - b; break;
                case '*': resultado = a * b; break;
                case '/': 
                    if (b == 0) {
                        printf("Erro: Divisão por zero!\n");
                        destroip(&P);
                        return 0;
                    }
                    resultado = a / b; 
                    break;
            }
            // Empilhamos o resultado
            empilha(resultado, P);
        }
    }

    // O resultado final está no topo da pilha
    int resultado_final = desempilha(P);
    destroip(&P); // Liberamos a pilha
    return resultado_final;
}


int main(void) {
    char e[513] = "(1*2)+3"; // Expressão original em notação infixa

    // Passo 1: Inverter a expressão infixa
    char *invertida = inverte_infixa(e);
    if (!invertida) {
        printf("Erro de alocação de memória\n");
        return 1;
    }

    // Passo 2: Converter a expressão invertida para pós-fixa
    char *posfixa_expr_invertida = posfixa(invertida);
    if (!posfixa_expr_invertida) {
        printf("Erro de alocação de memória\n");
        free(invertida);
        return 1;
    }

    // Passo 3: Inverter a pós-fixa para obter a prefixa
    char *prefixa = strrev_malloc(posfixa_expr_invertida);
    if (!prefixa) {
        printf("Erro de alocação de memória\n");
        free(invertida);
        free(posfixa_expr_invertida);
        return 1;
    }

    // Exibir os resultados
    printf("Expressao: %s\n", e);
    printf("Expressao Invertida: %s\n", invertida);
    printf("Posfixa: %s\n", posfixa_expr_invertida);
    printf("Prefixa: %s\n", prefixa);
    printf("Valor da expressao: %d\n",valpre(prefixa));

    // Liberar memória alocada dinamicamente
    free(invertida);
    free(posfixa_expr_invertida);
    free(prefixa);

    return 0;
}
