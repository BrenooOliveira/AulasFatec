#include <stdio.h>
#include "fila.h"

#define TIMESLICE 3 // Tempo máximo de uso ininterrupto de CPU

int main(void) {
    Fila F = fila(5); // Cria uma fila com capacidade para 5 elementos

    // Enfileira processos, onde os números representam (ID * 10 + Tempo de execução)
    enfileira(17, F); // Processo 1 requer 7s para conclusão
    enfileira(25, F); // Processo 2 requer 5s
    enfileira(39, F); // Processo 3 requer 9s
    enfileira(46, F); // Processo 4 requer 6s

    // Simulação do compartilhamento da CPU entre os processos
    while (!vaziaf(F)) {
        int x = desenfileira(F); // Retira um processo da fila
        int p = x / 10; // Obtém o identificador do processo (parte inteira)
        int t = x % 10; // Obtém o tempo restante de execução

        if (t > TIMESLICE)
            enfileira(p * 10 + (t - TIMESLICE), F); // Se o processo precisa de mais tempo, volta para a fila
        else
            printf("Processo %d concluido\n", p); // Se o tempo restante for menor ou igual ao slice, ele finaliza
    }

    destroif(&F); // Libera a memória da fila
    return 0;
}
