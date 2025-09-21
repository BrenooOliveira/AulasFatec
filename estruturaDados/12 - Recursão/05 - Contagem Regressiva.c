#include <stdio.h>

void cr(int n){
    if(n == 0) return;
    printf("%d",n);
    cr(n-1);

}

int main(void){
    cr(3);
    return 0;
}
