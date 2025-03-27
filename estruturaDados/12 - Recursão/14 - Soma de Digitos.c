/*
Crie a função recursiva sd(n), que devolve a soma dos dígitos do número natural n. Por exem
plo, a chamada sd(7859)deve devolver 29 (pois 7+8+5+9 = 29).

r = 7859/10
resto: 9
quociente: 785

*/


int sd(int n){
    if(n < 10) return n;
    return n%10 + sd(n/10);
}



int main(void) {
    int r = sd(1234);

    printf("%d \n", r);  // Imprime o resultado corretamente
    return 0;
}