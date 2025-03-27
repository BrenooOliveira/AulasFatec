#include <stdio.h>

int fat(int n){
    if (n == 0) return 1;
    return n*fat(n-1);
}

int main()
{
    int n;
    printf("Numero? ");
    scanf("%d",&n);
    printf("Fatorial: %d", fat(n));
    return 0;
}
