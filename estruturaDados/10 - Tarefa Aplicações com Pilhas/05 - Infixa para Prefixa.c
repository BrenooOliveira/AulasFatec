    #include <stdio.h>
    #include <ctype.h>
    #include <string.h>
    #include "pilha.h"

    /*
    Crie a função prefixa(e), que devolve a forma prefixa da expressão aritmética
    completamente parentesiada e. Em seguida, faça um programa para testar a função.

        ((2*3)+(8/4))
        + * 2 3 / 8 4

        (1*2)+3
        +*123


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


    int main(void) {
        char e[513] = "(1*2)+3";


        char *posfixa_expr = posfixa(e);

        char *invertida = inverte_infixa(e); // invertemos a infixa
        char *posfixa_expr_invertida = posfixa(invertida); // convertemos a invertida para posfixa
        // inverter a *posfixa_expr_invertida

        printf("Expressao: %s\n", e);
        printf("Expressao Invertida: %s\n", invertida);

        printf("Posfixa: %s\n", posfixa_expr);
        printf("Posfixa Invertida: %s\n", _strrev(posfixa_expr_invertida));



        return 0;
    }
