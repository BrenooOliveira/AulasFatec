#include <stdio.h>
//#include <stdlib.h>
#include <time.h>
#include <string.h>

int bsearch(int v[], int n, int x) {
    int p = 0;
    int u = n - 1;
    
    while (p <= u) {
        int m = (p + u) / 2;
        if (v[m] == x) return m;
        else if (v[m] < x) p = m + 1;
        else u = m - 1;
    }
    
    return 0;
} 

int main(void) {
    int n = 10;
    int v[10] = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
    int x = 5;
    int result = bsearch(v, n, x);
    
    if (result != 0) {
        printf("Elemento %d encontrado na posição %d\n", x, result);
    } else {
        printf("Elemento %d não encontrado\n", x);
    }
    
    return 0;
}