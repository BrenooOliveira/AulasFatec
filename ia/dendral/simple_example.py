class ToyDendral:
    def __init__(self):
        # BASE DE CONHECIMENTO (As heurísticas extraídas do especialista humano)
        # Na química real, diferentes moléculas "quebram" de formas específicas 
        # no espectrômetro de massa, gerando "picos" em números específicos.
        self.regras_especialista = {
            31: "álcool primário",   # Pico em m/z 31 indica fragmento -CH2OH
            45: "álcool secundário", # Pico em m/z 45 indica fragmento -CH(OH)-
            59: "éter"               # Pico em 59 pode indicar um éter dependendo da quebra
        }

    def planejar_e_gerar(self, formula_quimica):
        """
        Fase 1: GERAR (O trabalho do algoritmo original de Joshua Lederberg).
        Gera todas as estruturas topologicamente possíveis para a fórmula.
        """
        print(f"1. Gerando todas as estruturas possíveis para a fórmula {formula_quimica}...")
        
        # Simulação: para C3H8O, existem exatamente 3 isômeros possíveis.
        if formula_quimica == "C3H8O":
            return [
                {"nome": "Propan-1-ol", "classe": "álcool primário"},
                {"nome": "Propan-2-ol", "classe": "álcool secundário"},
                {"nome": "Metoxietano", "classe": "éter"}
            ]
        return []

    def testar(self, candidatos, dados_laboratorio):
        """
        Fase 2: TESTAR (A contribuição da Inteligência Artificial de Feigenbaum).
        Usa as regras de conhecimento para eliminar os candidatos que não batem com os dados.
        """
        print("2. Aplicando regras heurísticas para podar a árvore de busca...")
        candidatos_validos = []

        for candidato in candidatos:
            candidato_valido = False
            
            # O sistema olha para os dados brutos e usa suas regras para "raciocinar"
            for pico in dados_laboratorio:
                if pico in self.regras_especialista:
                    # Se a classe do candidato bate com a regra do especialista para aquele pico
                    if candidato["classe"] == self.regras_especialista[pico]:
                        candidato_valido = True
                        
            if candidato_valido:
                candidatos_validos.append(candidato)

        return candidatos_validos


# ==========================================
# EXECUTANDO O SISTEMA (Simulação de Uso)
# ==========================================

# O problema: O químico isolou uma molécula C3H8O, mas não sabe sua estrutura.
formula = "C3H8O"

# Os dados: O espectrômetro de massa cuspiu uma lista de números. 
# Repare que há um pico importante no número 45.
dados_espectrometro = [15, 27, 43, 45, 60] 

# Inicializa a Inteligência Artificial
sistema = ToyDendral()

# Passo 1: Gerar possibilidades
possibilidades = sistema.planejar_e_gerar(formula)
for p in possibilidades:
    print(f"   -> Encontrado: {p['nome']} ({p['classe']})")

print("-" * 40)

# Passo 2: Testar e filtrar
resultado = sistema.testar(possibilidades, dados_espectrometro)

print("-" * 40)
print("RESULTADO FINAL DA INFERÊNCIA:")
for r in resultado:
    print(f"A molécula desconhecida é estruturalmente o: {r['nome']}")
