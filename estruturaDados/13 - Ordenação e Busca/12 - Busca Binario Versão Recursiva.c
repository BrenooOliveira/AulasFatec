/*
Crie a função recursiva rbsearch(x,v,p,u), que faz uma busca binária para determinar
se o item x está no vetor crescente v, indexado de p até u.
*/
#include <stdio.h>

// Busca binária recursiva: busca x no vetor v entre os índices p e u
int rbsearch(int v[], int p, int u, int x){
    if (p > u) return -1; // Caso base: não encontrado

    int m = (p + u) / 2; // Calcula o meio

    if (v[m] == x) return m;             // Elemento encontrado
    else if (x < v[m])                   // Buscar na metade esquerda
        return rbsearch(v, p, m - 1, x);
    else                                 // Buscar na metade direita
        return rbsearch(v, m + 1, u, x);
}

int main() {
    int v[] = {10, 20, 30, 40, 50}; // vetor ordenado (necessário para busca binária)
    int n = 5;
    int x = 30;

    int pos = rbsearch(v, 0, n - 1, x); // busca de x de 0 até n-1

    if (pos != -1)
        printf("Valor %d encontrado na posição %d\n", x, pos);
    else
        printf("Valor %d não encontrado\n", x);

    return 0;
}
