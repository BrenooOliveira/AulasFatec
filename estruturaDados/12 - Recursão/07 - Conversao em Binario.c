#include <stdio.h>

void bin(n){
    if( n<2 ) printf("%d",n);
    else{
        bin(n/2);
        printf("%d",n%2);
    }
}

int main(void){
    bin(13);
    return 0;
}
