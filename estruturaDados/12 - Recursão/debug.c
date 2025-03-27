/*
 Crie a função recursiva quoc(m,n), que devolve o quociente da divisão inteira do número natural
 pelo número natural n0, usando apenas soma e subtraçã

 */

 int prod(int m,int n){
    if ( n == 0) return 0;
    if ( n == 1) return m;
    return m + prod(m,n-1);

}

int quoc(int m,int n){
    if (m < n) return 0;
    return 1 + quoc(m-n,n);
}


int main(void) {
    int r = 10%20;

    printf("%d\n", r);  // Imprime o resultado corretamente
    return 0;
}