/*
 Crie a função recursiva resto(m,n), que devolve o resto da divisão inteira do número natural m
 pelo número natural n0, usando apenas subtração.
 */



int resto(int m,int n){
    if (m < n) return m;
    return resto(m-n,n);
}

int main(void) {
    int r = resto(10, 3);

    printf("%d \n", r);  // Imprime o resultado corretamente
    return 0;
}
