/*
 Crie a função recursiva h(n), que devolve o número mínimo de movimentos para resolver o
 problema das Torres de Hanói com n discos. Por exemplo, h(3) deve devolver 7.

*/


int h(int n){
    if(n == 1) return 1; // se for um disco, um movimento
    return 2*h(n-1)+1;
}



int main(void) {
    int r = h(3);

    printf("%d \n", r);  // Imprime o resultado corretamente
    return 0;
}
