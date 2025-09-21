/*
 O quadrado de um natural n é a soma dos n primeiros ímpares, i.e., n2  1  3  5    (2n1).
 Crie a função recursiva q(n), que devolve o quadrado de n, como base nesta informação.
 */




int q(int n){
    if (n == 0) return 0;
    if (n == 1) return 1;
    return q(n-1) + (2*n-1); // o anterior + o enésimo caso
}



int main(void) {
    int r = q(3);

    printf("%d \n", r);  // Imprime o resultado corretamente
    return 0;
}
