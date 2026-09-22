from rdkit import Chem
from rdkit.Chem import Descriptors

def exemplo_dendral_com_rdkit():
    # 1. FASE DE GERAÇÃO (Candidatos gerados e representados em SMILES)
    # CCCO = Propan-1-ol | CC(C)O = Propan-2-ol | CCOC = Metoxietano | CC(=O)C = Acetona
    candidatos_smiles = ["CCCO", "CC(C)O", "CCOC", "CC(=O)C"]
    
    print("1. Convertendo texto (SMILES) em Grafos Moleculares...")
    # O RDKit transforma a string em um objeto de Teoria dos Grafos com nós (átomos) e arestas (ligações)
    moleculas = [Chem.MolFromSmiles(smiles) for smiles in candidatos_smiles]

    # 2. DEFININDO AS REGRAS DO "ESPECIALISTA"
    massa_alvo = 60.1
    tolerancia = 0.5
    
    # SMARTS é a linguagem do RDKit para buscar "padrões" dentro do grafo.
    # "[CH](C)(C)O" significa: Um Carbono com 1 Hidrogênio, ligado a 2 outros Carbonos e 1 Oxigênio.
    # Essa é a assinatura topológica exata de um Álcool Secundário!
    padrao_alcool_secundario = Chem.MolFromSmarts("[CH](C)(C)O")
    
    # 3. FASE DE TESTE (Filtrando candidatos inválidos)
    print("2. Aplicando restrições baseadas nos dados do laboratório...")
    resultado_final = []

    for mol in moleculas:
        # Pega o nome em texto de volta para podermos imprimir
        nome_smiles = Chem.MolToSmiles(mol)
        
        # Regra A: A massa bate com o espectrômetro?
        massa_calculada = Descriptors.MolWt(mol)
        if abs(massa_calculada - massa_alvo) > tolerancia:
            print(f" ❌ {nome_smiles} descartado: Massa incorreta ({massa_calculada:.2f})")
            continue
            
        # Regra B: Possui a subestrutura (o grafo interno) do álcool secundário?
        # O método HasSubstructMatch varre a árvore da molécula procurando o padrão.
        if not mol.HasSubstructMatch(padrao_alcool_secundario):
            print(f" ❌ {nome_smiles} descartado: Não é um álcool secundário.")
            continue
            
        # Se passou por todas as regras, é a nossa molécula!
        print(f" ✅ {nome_smiles} APROVADO! Combina perfeitamente com os dados.")
        resultado_final.append(mol)

    print("\n--- RESULTADO DA INFERÊNCIA ---")
    print(f"A molécula desconhecida é o SMILES: {Chem.MolToSmiles(resultado_final[0])}")

# Executando o script
if __name__ == "__main__":
    exemplo_dendral_com_rdkit()
