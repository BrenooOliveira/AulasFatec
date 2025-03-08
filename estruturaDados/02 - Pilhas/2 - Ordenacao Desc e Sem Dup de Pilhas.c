#include <stdio.h>
#include "pilha.h"
#include <stdlib.h>
/*

Vamos fazer o mesmo que o ex01:
    Crie um programa que usa duas pilhas A e B
    para ordenar uma sequ�ncia de n n�meros dados pelo usu�rio.

    A ideia � organizar a pilha A de modo que nenhum item seja empilhado sobre outro menor (use a pilha B apenas para manobra)
    e, depois, descarregar e exibir os itens da pilha A.
Por�m com o os adicionais:
    - ordene os numeros de forma decrescente
    - remova as duplicatas
*/

#define MAX_VALUE 100 // define que o maior valor inputado sera 100
remove_duplicates(Pilha *A, int n){
    Pilha *B = pilha(n);
    // array com numeros presentes
    int presente[MAX_VALUE] = {0}; // vetor booleado que funciona como set e marca os elementos j� vistos

    while(!vaziap(A)){
        int atual = desempilha(A);
        if(!presente[atual]){ // se atual N�O EST� em presente
            empilha(atual,B);
            presente[atual] = 1;
        }
    };

    while(!vaziap(B)){
        empilha(desempilha(B),A);
    };



    destroip(&B);
}


// funcao para ordenar de maneira desc uma pilha A
void sort_desc(Pilha *A, int n){
    Pilha *B = pilha(n);
    Pilha *C = pilha(n);

    // empilhamos A em B, portanto, B ter� o inverso de A
    while(!vaziap(A)){
        empilha(desempilha(A),B);

    }

    // empilhamos B em C, portanto, C ter� o inverso de B, que � igual a A
    while(!vaziap(B)){
        empilha(desempilha(B),C);
    }

    // por fim, C insere em A o conteudo inverso dela
    while(!vaziap(C)){
        empilha(desempilha(C),A);

    }


    destroip(&B);
    destroip(&C);
}



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

    // loop par ler a qtd de numeros para ser empilhados
    for (int i = 0; i < n; i++) {
        int num;
        printf("Digite o %d o numero: ", i+1);
        scanf("%d", &num);
        empilha(num, A);
    }

    ordena_pilha(A, n); // ordena a pilha em ordem crescente
    sort_desc(A,n); // ordena a pilha em ordem decrescente
    remove_duplicates(A,n); // remove as duplicatas da pilha

    printf("\nValores ordenados decrescente e sem duplicatas: \n");
    while(!vaziap(A)){
        printf(" %d", desempilha(A));
    }

    printf("\n");

    destroip(&A);
    return 0;
}
