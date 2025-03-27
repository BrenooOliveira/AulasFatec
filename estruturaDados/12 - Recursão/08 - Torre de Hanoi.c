/*
Crie a função recursiva hanoi(n,origem,auxiliar,destino), que resolve o problema das Torres de
Hanói, movendo n discos da torre origem, para a torre destino, usando a torre auxiliar. Por
exemplo, a chamada hanoi(3, 'A', 'B', 'C') deve resolver o problema discutido no Exemplo 6.

hanoi(3, 'A', 'B', 'C')
    - 3: peças
    - Origem A
    - Aux B
    - Destino C

*/

#include <stdio.h>

// Função recursiva para resolver as Torres de Hanói
void hanoi(int n, char origem, char auxiliar, char destino) {
    // Caso base: se houver apenas 1 disco, mova diretamente para o destino
    if (n == 1) {
        printf("Mover disco 1 de %c para %c\n", origem, destino);
        return;
    }

    // Passo 1: Mover n-1 discos da origem para auxiliar, usando destino como apoio
    hanoi(n - 1, origem, destino, auxiliar);

    // Passo 2: Mover o maior disco restante da origem para o destino
    printf("Mover disco %d de %c para %c\n", n, origem, destino);

    // Passo 3: Mover n-1 discos da auxiliar para destino, usando origem como apoio
    hanoi(n - 1, auxiliar, origem, destino);
}




// Função principal
int main() {
    int n;

    // Solicita ao usuário a quantidade de discos
    printf("Digite o número de discos: ");
    scanf("%d", &n);

    // Chama a função recursiva
    hanoi(n, 'A', 'B', 'C');

    return 0;
}
