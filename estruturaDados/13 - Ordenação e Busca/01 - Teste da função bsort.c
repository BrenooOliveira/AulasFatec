/*
 Crie a função exibe(), complete e execute o programa a seguir.

*/

#include <stdio.h>

void troca(int v[], int i, int j) {
    // troca i por j
    int x = v[i]; // auxiliar
    v[i] = v[j];
    v[j] = x;
}

void bsort(int v[], int n) {
    for(int i=1; i<n; i++) // para cada i no range do vetor (n)
        for(int j=0; j<n-i; j++) // para cada j no range de n-i (ou seja, até chegar no indice que o valor de i esta)
            if( v[j]>v[j+1] ) // se o indice de j for menor que o proximo j
                troca(v,j,j+1); // troca j por j+1
}

void exibe(int v[],int n){
    for (int i=0; i<n; i++){
        printf("%d, ", v[i]);
    }
}


int main(void) {
    int v[10] = {83,31,91,46,27,20,96,25,96,80};
    bsort(v,10);
    exibe(v,10);
return 0;
}
