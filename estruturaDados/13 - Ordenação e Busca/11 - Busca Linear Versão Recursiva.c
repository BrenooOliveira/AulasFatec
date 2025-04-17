/*
Crie a função recursiva rlsearch(x,v,n), que faz uma busca linear para determinar se o
item x está no vetor v, que tem n itens
*/

#include <stdio.h>

int lsearch(int v[], int n, int x) {
    for (int i = 0; i < n; i++) {
        if (v[i] == x) return i;
    }
    return -1;
}

int rlsearch(int v[], int n, int x, int i){
    if(i == n) return -1; // nao achou
    if(v[i] == x) return i;
    return rlsearch(v,n,x,i+1);
}


int main() {
    int v[] = {30, 20, 30, 40, 50};
    int n = 5;
    int x = 30;
    int pos = rlsearch(v, n, x, 0); // Começamos procurando a partir do índice 0

    if (pos != -1)
        printf("Valor encontrado na posição %d\n", pos);
    else
        printf("Valor não encontrado\n");

    return 0;
}
