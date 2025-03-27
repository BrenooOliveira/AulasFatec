/*
Crie a função recursiva pal(s,p,u), que informa se a string s, cujo primeiro caractere está na
 posição p e cujo último caractere está na posição u, é palíndroma (ignorando brancos)

*/



#include <stdio.h>
#include <string.h>

int pal(char s[], int p, int u) {
    // base: se os índices se cruzarem ou se encontrarem
    if (p >= u) return 1;

    // Se os caracteres forem diferentes, não é palíndromo
    if (s[p] != s[u]) return 0;

    // Recursão: verifica o até chegar no miobo
    return pal(s, p + 1, u - 1);
}

int main(void) {
    char s[] = "ovo";

    if (pal(s, 0, sizeof(s) - 2)) // sizeof(s)-2 porque -1 é '\0'
        printf("Eh palindromo\n");
    else
        printf("Nao eh palindromo\n");

    return 0;
}
