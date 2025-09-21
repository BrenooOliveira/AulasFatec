#include <stdio.h>

int terminal(int n ){
    if( n == 0) return 0;
    return terminal(n-1) + n;
}

int main(void){
    printf("Termial: %d\n", terminal(10));
    return 0;
}
