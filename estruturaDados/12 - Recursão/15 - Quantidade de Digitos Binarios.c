/*
 Crie a função recursiva qd(n), que devolve a quantidade de dígitos binários para representar o
 natural n. Por exemplo, a chamada qd(13) deve devolver 4 (pois 13 em binário é 1101)

 12/2 = 6 resto (0)- 1d
 6/2 = 3 resto (0) - 2d
 3 / 2 = 1 resto (1) 3d
 1 / 2 = 0 resto(1) - 4d

 n/2 =
 n-1/2 =
 ...
 n/2 = 0

*/



int qd(int n){
    if(n == 0) return 0;
    return 1 + qd(n/2); // utilzamos 1 + para "contar quantas vezes passamos pela funcao" e assim acumular
}



int main(void) {
    int r = qd(13);

    printf("%d \n", r);  // Imprime o resultado corretamente
    return 0;
}
