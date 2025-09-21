#include <stdio.h>

void cp(int n){
    if(n == 0) return;
    cp(n-1);
    printf("%d",n);
}

int main(void){
    cp(3);
    return 0;
}
