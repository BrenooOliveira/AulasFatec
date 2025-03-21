#include <stdio.h>

#define dim 9  // Define o tamanho da matriz

// Matriz que representa a imagem
int I[dim][dim] = {
    {0, 0, 0, 0, 0, 0, 0, 0, 0},
    {0, 0, 0, 0, 1, 0, 0, 0, 0},
    {0, 0, 0, 1, 1, 1, 0, 0, 0},
    {0, 0, 1, 1, 1, 1, 1, 0, 0},
    {0, 1, 1, 1, 1, 1, 1, 1, 0},
    {0, 0, 2, 0, 0, 0, 2, 0, 0},
    {0, 0, 2, 0, 0, 0, 2, 0, 0},
    {0, 0, 2, 2, 2, 2, 2, 0, 0},
    {0, 0, 0, 0, 0, 0, 0, 0, 0}
};

// Função para definir cores no terminal (Códigos ANSI)
void setColor(int color) {
    if (color == 1)
        printf("\033[34m"); // Azul para 1
    else if (color == 2)
        printf("\033[32m"); // Verde para 2
    else
        printf("\033[0m");  // Reset para padrão (cor normal)
}

// Função para exibir a matriz
void exiba(int I[dim][dim]) {
    for (int i = -1; i < dim; i++) {
        printf("\033[90m"); // Cinza para números do eixo

        for (int j = -1; j < dim; j++) {
            if (i < 0 && j < 0)
                printf("   "); // Espaço no canto superior esquerdo
            else if (i < 0)
                printf("%2d ", j); // Números do eixo X
            else if (j < 0)
                printf("\n%2d ", i); // Números do eixo Y
            else {
                setColor(I[i][j]); // Define cor com base no valor
                printf("█"); // Exibe bloco colorido
            }
        }
    }
    printf("\033[0m\n"); // Resetar cor no final
}

int main(void) {
    exiba(I); // Chama a função para exibir a matriz
    puts("\n"); // Nova linha no final
    return 0;
}
