/*
 Crie a função recursiva empurra(v,u), que “empurra” um item máximo do vetor v para a
 posição u de v, possivelmente alterando a ordem dos demais itens do vetor. Por exemplo, o
 código abaixo deve produzir a saída indicada a seguir:
 int v[9] = {51,82,38,99,75,19,69,46,27};
 empurra(v,8);
 exibe(v,9);
 Saída: {51,38,82,75,19,69,46,27,99}
*/

#include <stdio.h>

void troca(int v[], int i, int j) {
    // i e j: indices que serao trocados
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
        printf(" %d ", v[i]);
    }
}

void empurra(int v[], int u){
    if (u == 0) return;
    
    int max_item = 0;
    for(int i=1; i<u; i++){
        if(v[i] > v[max_item]) max_item = i;
    };

    troca(v,max_item,u);
}


int main(void) {
    int v[9] = {51,82,38,99,75,19,69,46,27};
    empurra(v,8);
    exibe(v,9);
return 0;
}