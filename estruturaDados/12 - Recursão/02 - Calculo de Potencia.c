#include <stdio.h>

float pot(float x, int n){
    if(n == 0) return 1;
    return x*pot(x,n-1);
}

int main(void){
    printf("Potencia: %.1f\n", pot(2,10));
    return 0;
}
