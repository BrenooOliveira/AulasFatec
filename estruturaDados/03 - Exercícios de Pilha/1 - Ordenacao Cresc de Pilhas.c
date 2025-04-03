#include <stdio.h>
#include "pilha.h"
#include <stdlib.h>
/*
Crie um programa que usa duas pilhas A e B
para ordenar uma sequ�ncia de n n�meros dados pelo usu�rio.

A ideia � organizar a pilha A de modo que nenhum item seja empilhado sobre outro menor (use a pilha B apenas para manobra)
e, depois, descarregar e exibir os itens da pilha A.
*/



void ordena_pilha(Pilha *A, int n) {
    Pilha *B = pilha(n);

    while(!vaziap(A)){
        int atual = desempilha(A);
        while(!vaziap(B) && topo(B) > atual){
                    empilha(desempilha(B),A);

        };
        empilha(atual,B);

    }

    while(!vaziap(B)){
        empilha(desempilha(B),A);

    }

destroip(&B);

} // fim funcao ordena


int main() {
    int n;
    printf("Quantos numeros deseja ordenar? ");
    scanf("%d", &n);

    Pilha *A = pilha(n);


    for (int i = 0; i < n; i++) {
        int num;
        printf("Digite o %d o numero: ", i+1);
        scanf("%d", &num);
        empilha(num, A);
    }

    ordena_pilha(A, n);

    // Exibir os n�meros ordenados (desempilhando A)
    printf("\n \n Numeros ordenados: ");
    while (!vaziap(A)) {
        printf("%d ", desempilha(A));
    }
    printf("\n");

    destroip(&A);
    return 0;
}
