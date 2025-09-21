/*
 Crie a função recursiva inv(s,p,u), que inverte a string s, cujo primeiro caractere está na
 posição p e cujo último caractere está na posição u. A função deve devolver s como resposta.

*/



#include <stdio.h>
#include <string.h>

char* inv(char s[], int p, int u) {
    if (p >= u) return s;
    // Troca de Caracteres
    char temp = s[p];
    s[p] = s[u];
    s[u] = temp;

    return inv(s, p+1, u-1);
}

int main() {
    char str[] = "ABCDE";
    printf("Original: %s\n", str);
    inv(str, 0, strlen(str) - 1);
    printf("Invertida: %s\n", str);
    return 0;
}
