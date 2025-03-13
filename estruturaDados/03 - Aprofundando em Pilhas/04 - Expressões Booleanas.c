#include <stdio.h>
#include <ctype.h>
#include <string.h>
#include "pilha.h"

/*
Com base nos algoritmos descritos, crie um programa para ler uma expressão booleana comple
tamente parentesiada e exibir sua forma posfixa correspondente, bem como seu valor numérico.

Considere que as expressões são compostas por:
    -Operandos: letras maiúsculas F e V, com valores numéricos 0 e 1, respectivamente.
    -  Operadores: ! (não), & (e) e | (ou), da maior para a menor prioridade.
    - Delimitadores: parênteses de abertura e fechamento.
 
    -Por exemplo, para a expressão booleana infixa parentesiada "((!F)|(F&V))", o programa
    deve apresentar como saída a forma posfixa "F!FV&|" e o valor numérico 1.
 



*/

int prio(char o) {
    switch(o) {
        case '!' : return 0;
        case '&': return 1;
        case '|': return 2
    }
    return -1; // operador inválido!
}

