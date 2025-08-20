#include <stdio.h>
#include <stdlib.h>
#include <string.h>

void limparBuffer() {
    int c;
    while ((c = getchar()) != '\n' && c != EOF);
}

int main() {
    int month, day;
    char entrada[20];

    while (1) {
        printf("\nDigite o mes de nascimento (1-12) ou 'sair' para encerrar: ");
        if (scanf("%19s", entrada) != 1) { 
            limparBuffer();
            continue;
        }

        // Se usuário digitou "sair"
        if (strcmp(entrada, "sair") == 0) {
            printf("Encerrando...\n");
            break;
        }

        // Tenta converter para número
        if (sscanf(entrada, "%d", &month) != 1) {
            printf("Entrada inválida! Digite um número ou 'sair'.\n");
            continue;   
        }

        printf("Digite o dia de nascimento (1-31): ");
        if (scanf("%d", &day) != 1) {
            printf("Entrada inválida! Digite apenas números.\n");
            limparBuffer();
            continue;
        }

        // valida formato
        if (month < 1 || month > 12 || day < 1 || day > 31) {
            printf("Data invalida.\n");
            continue;
        }

        // valida meses com 30 dias
        if ((month == 4 || month == 6 || month == 9 || month == 11) && day > 30) {
            printf("Data invalida.\n");
            continue;
        }

        // valida fevereiro
        if (month == 2 && day > 29) {
            printf("Data invalida.\n");
            continue;
        }

        // determina signo
        if ((month == 1 && day >= 20) || (month == 2 && day <= 18)) {
            printf("Aquarius\n");
        }
        else if ((month == 2 && day >= 19) || (month == 3 && day <= 20)) {
            printf("Pisces\n");
        }
        else if ((month == 3 && day >= 21) || (month == 4 && day <= 19)) {
            printf("Aries\n");
        }
        else if ((month == 4 && day >= 20) || (month == 5 && day <= 20)) {
            printf("Taurus\n");
        }
        else if ((month == 5 && day >= 21) || (month == 6 && day <= 20)) {
            printf("Gemini\n");
        }
        else if ((month == 6 && day >= 21) || (month == 7 && day <= 22)) {
            printf("Cancer\n");
        }
        else if ((month == 7 && day >= 23) || (month == 8 && day <= 22)) {
            printf("Leo\n");
        }
        else if ((month == 8 && day >= 23) || (month == 9 && day <= 22)) {
            printf("Virgo\n");
        }
        else if ((month == 9 && day >= 23) || (month == 10 && day <= 22)) {
            printf("Libra\n");
        }
        else if ((month == 10 && day >= 23) || (month == 11 && day <= 21)) {
            printf("Scorpio\n");
        }
        else if ((month == 11 && day >= 22) || (month == 12 && day <= 21)) {
            printf("Sagittarius\n");
        }
        else if ((month == 12 && day >= 22) || (month == 1 && day <= 19)) {
            printf("Capricorn\n");
        }
    }

    return 0;
}
