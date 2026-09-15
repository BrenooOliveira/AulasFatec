from easyAI import (
    TwoPlayerGame,
    Human_Player,
    AI_Player,
)

from easyAI.TwoPlayerGame import *

class TicTacToe(TwoPlayerGame):

    def __init__(self, players):
        self.players = players

        # Tabuleiro:
        #
        #  1 | 2 | 3
        # ---+---+---
        #  4 | 5 | 6
        # ---+---+---
        #  7 | 8 | 9
        #
        # 0 = vazio
        # 1 = jogador X
        # 2 = jogador O

        self.board = [0] * 9

        # Jogador 1 começa
        self.nplayer = 1

    # --------------------------------------------------
    # MOVIMENTOS POSSÍVEIS
    # --------------------------------------------------

    def possible_moves(self):
        """
        Retorna as posições livres.
        """

        return [
            i + 1
            for i, value in enumerate(self.board)
            if value == 0
        ]

    # --------------------------------------------------
    # FAZER MOVIMENTO
    # --------------------------------------------------

    def make_move(self, move):
        """
        Executa uma jogada.
        """

        position = int(move) - 1

        self.board[position] = self.nplayer

    # --------------------------------------------------
    # DESFAZER MOVIMENTO
    # --------------------------------------------------

    def unmake_move(self, move):
        """
        Desfaz uma jogada.

        O algoritmo de busca usa isso para
        explorar diferentes possibilidades.
        """

        position = int(move) - 1

        self.board[position] = 0

    # --------------------------------------------------
    # VERIFICAR DERROTA
    # --------------------------------------------------

    def lose(self):
        """
        Verifica se o adversário conseguiu
        formar três posições consecutivas.
        """

        winning_lines = [
            # linhas
            (1, 2, 3),
            (4, 5, 6),
            (7, 8, 9),

            # colunas
            (1, 4, 7),
            (2, 5, 8),
            (3, 6, 9),

            # diagonais
            (1, 5, 9),
            (3, 5, 7),
        ]

        return any(
            all(
                self.board[position - 1]
                == self.nopponent
                for position in line
            )
            for line in winning_lines
        )

    # --------------------------------------------------
    # FIM DO JOGO
    # --------------------------------------------------

    def is_over(self):
        """
        O jogo termina quando:

        1. alguém ganhou
        2. não existem mais movimentos
        """

        return (
            self.lose()
            or not self.possible_moves()
        )

    # --------------------------------------------------
    # EXIBIR TABULEIRO
    # --------------------------------------------------

    def show(self):

        symbols = {
            0: ".",
            1: "X",
            2: "O",
        }

        for row in range(3):

            start = row * 3

            print(
                " | ".join(
                    symbols[self.board[start + col]]
                    for col in range(3)
                )
            )

            if row < 2:
                print("---------")

    # --------------------------------------------------
    # FUNÇÃO DE AVALIAÇÃO
    # --------------------------------------------------

    def scoring(self):
        """
        Avalia o estado para o algoritmo Negamax.

        Se o jogador atual perdeu, retornamos
        uma pontuação muito negativa.

        Caso contrário, 0.
        """

        return -100 if self.lose() else 0


# ======================================================
# MAIN
# ======================================================

if __name__ == "__main__":

    # Algoritmo de IA
    ai_algo = Negamax(9)

    # Jogador humano = jogador 1
    # IA             = jogador 2
    game = TicTacToe([
        Human_Player(),
        AI_Player(ai_algo),
    ])

    game.play()
