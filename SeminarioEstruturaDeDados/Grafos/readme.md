
# Estudo de Caso com Grafos: Análise de Influenciadores do Instagram

## Objetivo
Demonstrar a aplicação prática dos conceitos de **grafos** utilizando dados reais de influenciadores do Instagram. O foco é identificar **comunidades de audiência** baseadas no país predominante do público desses influenciadores.

---

## Etapas do Estudo

### 1. **Carregamento e Preparação dos Dados**

- Foi utilizado um dataset real com informações de influenciadores do Instagram.
- A coluna principal usada foi `Audience country(mostly)`, que indica o país com maior audiência para cada influenciador.
- Os dados foram limpos e padronizados para garantir consistência nos nomes dos países.

### 2. **Construção do Grafo**

- Utilizamos a biblioteca `NetworkX` para modelar um grafo:
    - Cada **nó** representa um influenciador.
    - Cada **aresta** conecta dois influenciadores com o mesmo país de audiência.

- Resultado:
    - **976 nós**
    - **69.176 arestas**

---

##  Análise com Algoritmos Clássicos de Grafos

### 3. **Busca em Largura (BFS)**
- Simula uma exploração por “camadas” a partir de um nó inicial.
- Usamos como ponto de partida o influenciador **Justin Bieber**.
- O algoritmo retornou todos os influenciadores conectados diretamente ou indiretamente a ele, que compartilham o mesmo país de audiência.
- Resultado: **várias centenas de conexões diretas**.

### 4. **Busca em Profundidade (DFS)**
- Explora o grafo indo o mais fundo possível em cada ramificação antes de voltar.
- Foi aplicada a partir do mesmo ponto inicial para fins comparativos com o BFS.
- Resultado: mesma quantidade de nós conectados, porém ordem de exploração diferente.

---

##  Interpretação
- A rede criada representa **comunidades geográficas de influência**.
- A partir de um influenciador global, como Justin Bieber, conseguimos visualizar um subconjunto de influenciadores que compartilham uma base comum de audiência.
- Essa informação pode ser usada para:
    - Estratégias de co-marketing.
    - Expansão de campanhas regionais.
    - Estudo de clusters de influência.

---

##  Visualização

- Foi gerado um subgrafo com os **20 primeiros influenciadores conectados** a Justin Bieber.
- A visualização mostra como esses influenciadores estão interconectados por meio de um público-alvo comum.
- As conexões refletem **proximidade geográfica** e **potencial de colaboração** entre os influenciadores.

---

## Conclusões

- Este estudo mostra a força dos **grafos como estrutura de dados** para análise de redes sociais.
- Com poucos algoritmos clássicos, conseguimos descobrir relações não triviais entre influenciadores.
- A visualização da rede permite **insights estratégicos**, mesmo com um modelo simples.

---

##  Tecnologias Utilizadas

- Python
- Pandas (manipulação de dados)
- NetworkX (construção e análise de grafos)
- Matplotlib (visualização)
