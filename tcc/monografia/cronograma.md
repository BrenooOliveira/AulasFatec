# Cronograma e TODOs — Monografia TCC
**Título:** Comparação de Bancos Vetoriais Aplicados a Solução de Geração Aumentada de Recuperação
**Autor:** Breno Alves
**Entrega final:** 01/nov/2026
**Início:** 16/ago/2026
**Total de dias:** 77

---

## ⚙️ Decisões de escopo (travadas — não alterar sem justificativa)

| # | Decisão | Justificativa |
|---|---------|---------------|
| D1 | S3 fixado em **500k vetores** (não 1M) | Viabilidade no Colab gratuito; resultado ainda representa escala empresarial |
| D2 | **LLM removida** do experimento | Introduziria segunda variável independente; Recall@k é métrica padrão ANN suficiente |
| D3 | Índices por banco: FAISS (Flat + HNSW), Milvus (Flat + HNSW), Chroma (HNSW automático) | IVF incluído só no S3 como config adicional do FAISS |
| D4 | `N_RUNS = 10` (repetições por cenário) | Necessário para IC 95% e teste Wilcoxon que a banca exigiu |
| D5 | Objetivo (e) do Cap. 1.2.2 deve ser **reescrito** | Remover referência a "qualidade das respostas do LLM"; manter foco em Recall@k como proxy de qualidade semântica |
| D6 | Falha do Chroma em alta escala é **um resultado**, não omissão | Documentar limites operacionais do Chroma em S3 como achado experimental |

---

## 📊 Estado atual dos capítulos

| Capítulo | Status | Observação |
|----------|--------|------------|
| Cap. 1 — Introdução | ✅ Completo | Gap analysis adicionado; objetivo (e) reescrito; justificativa 1.3 revisada |
| Cap. 1.2.2 — Obj. específico (e) | ✅ Concluído | Reescrito — foco em Recall@k, LLM removido do escopo (D2/D5) |
| Cap. 2 — Fundamentação | ✅ Completo | Diagrama RAG (WOLFF 2023); subseção 2.4.1 critérios de seleção; figura HNSW (MALKOV 2020) |
| Cap. 3 — Metodologia | ❌ Ausente | Escrever do zero — **próximo passo** |
| Cap. 4 — Desenvolvimento | ❌ Ausente | Depende dos dados do experimento |
| Conclusão | ❌ Ausente | Escrever após Cap. 4 |
| Referências | ✅ Completo | 10 entradas, todas com URL, sem duplicatas; inclui GRAND VIEW RESEARCH, WOLFF, MALKOV & YASHUNIN |
| Apêndices | ❌ Ausente | Código, tabelas completas |
| Notebook | ✅ Completo | `benchmark_rag_vectordbs_v2.ipynb` — Colab-ready, 43 células |
| GitHub | ✅ Completo | README, requirements.txt, results/, LICENSE |

---

## 🗓️ Fases e TODOs

---

### FASE 1 — Experimento e infraestrutura
**Período:** 16/ago – 30/ago (15 dias)
**Responsável:** Breno
**Bloqueador para:** Cap. 4 (não dá para escrever resultados sem dados)

#### 1.1 Notebook (reescrever para Colab)
- [x] Adicionar parâmetro `COLAB_MODE = True` no topo (S3 = 500k quando True)
- [x] Adicionar mount do Google Drive para persistência de resultados
- [x] Ajustar `N_RUNS = 10` (era 5)
- [x] Reduzir índices: FAISS (Flat + HNSW-32), Milvus (Flat + HNSW-16), Chroma (HNSW auto)
- [x] Adicionar IVF apenas no S3 para FAISS (como configuração extra comparativa)
- [x] Adicionar `gc.collect()` + `del corpus` entre cenários para liberar RAM
- [x] Salvar CSV parcial após cada banco/cenário (ex: `resultados_faiss_S1.csv`)
- [x] Adicionar bloco de análise estatística:
  - [x] Média e desvio padrão das latências por banco/cenário
  - [x] Intervalo de confiança 95% (t-distribution, N=10)
  - [x] Teste Wilcoxon signed-rank entre bancos para latência p50
- [x] Atualizar célula de ambiente: registrar versões exatas de todas as libs
- [x] Testar notebook do início ao fim no Colab gratuito sem erros
#### 1.2 Execução do experimento
- [x] Rodar S1 (10k, embeddings reais) — ag_news + all-MiniLM-L6-v2
- [x] Rodar S2 (100k, sintético)
- [x] Rodar S3 (500k, sintético) — Chroma: timeout registrado como achado (D6); taxa de inserção e tempo estimado capturados
- [x] Verificar e salvar todos os CSVs de resultado
- [x] Salvar todos os gráficos gerados (fig1 a fig5)
- [x] Registrar informações do ambiente de execução do Colab (RAM, CPU, Python, libs)
#### 1.3 GitHub
- [x] Criar `README.md` com: descrição, pré-requisitos, como executar, hardware recomendado
- [x] Criar `requirements.txt` com versões fixadas (numpy/pandas sem pin — compatibilidade Colab)
- [x] Criar pasta `results/` com os CSVs e figuras
- [x] Criar pasta `data/` ou documentar como baixar o ag_news (documentado no README)
- [x] Adicionar licença (MIT)
- [x] Commitar notebook revisado (`benchmark_rag_vectordbs_v2.ipynb`)
---

### FASE 2 — Revisão do Cap. 1 e Cap. 2
**Período:** 01/set – 12/set (12 dias)
**Pode iniciar:** Em paralelo com a Fase 1

#### 2.1 Cap. 1 — Introdução (ajustes)
- [x] Reescrever parágrafo final da introdução com gap analysis explícito (contribuição diferencial vs. ANN-Benchmarks e benchmarks de fornecedores)
- [x] Reescrever objetivo específico (b): remover LangChain → "bibliotecas nativas de cada banco de vetores" (D3)
- [x] Reescrever objetivo específico (c): "um milhão" → "500 mil vetores" (D1)
- [x] Reescrever objetivo específico (e): remover avaliação de LLM; foco em Recall@k (k=1,5,10) com oráculo de busca exata (D2/D5)
- [x] Revisar 1.3 Justificativa: Recall@k e 500k explicitados; diferencial vs. ANN-Benchmarks
#### 2.2.1 ⚠️ Pendência transversal — Definição formal de métricas (Cap. 3)
- [ ] **OBRIGATÓRIO antes da entrega:** criar subseção de métricas no Cap. 3 (Metodologia) com definição formal e didática de:
  - **Recall@k** — fração dos resultados relevantes recuperados dentro dos k primeiros; fórmula: `Recall@k = |Relevantes ∩ Recuperados@k| / |Relevantes|`; incluir exemplo numérico
  - **Precision@k** — fração dos k primeiros resultados retornados que são de fato relevantes
  - **ground truth** — conjunto de resultados corretos obtido via oráculo de busca exata (FAISS IndexFlatIP)
  - **oráculo de busca exata** — busca sem aproximações que garante resultado matematicamente ótimo
  - Valores de k avaliados: k = 1, 5 e 10
- **Motivo:** os termos aparecem 9+ vezes na monografia sem definição formal — risco crítico de rejeição pela banca
#### 2.2 Cap. 2 — Fundamentação Teórica (complementos)
- [x] **Seção 2.2 RAG** — Figura NVIDIA WOLFF (2023) inserida em markdown com ALT text ABNT; parágrafo expandido para dois fluxos (ingestão offline + consulta online)
- [x] **Seção 2.3 HNSW** — Placeholder "(figura abaixo)" removido; figura Malkov & Yashunin (2020) inserida com placeholder de URL (⚠️ extrair Figura 1 do PDF https://arxiv.org/pdf/1603.09320 e hospedar no GitHub)
- [x] **Seção 2.4.1** — "Critérios de seleção dos bancos avaliados" inserida: FAISS/Milvus/Chroma justificados; Qdrant/Weaviate/Pinecone excluídos com justificativa; *oráculo de busca exata* e *ground truth* explicados inline
- [x] Referências: 10 entradas, todas com URL, sem duplicatas — adicionados WOLFF (2023), GRAND VIEW RESEARCH (2024), MALKOV & YASHUNIN (2020)
---

### FASE 3 — Cap. 3: Metodologia detalhada
**Período:** 13/set – 26/set (14 dias)
**Pode iniciar:** Após definir resultados do experimento (Fase 1 concluída)

#### 3.1 Estrutura do capítulo a escrever
- [ ] **3.1 Caracterização da pesquisa** — pesquisa experimental quantitativa, abordagem de benchmark controlado
- [ ] **3.2 Pipeline padronizado** — descrição de cada componente fixo:
  - Modelo de embeddings: all-MiniLM-L6-v2, 384 dims, normalizado, justificativa da escolha
  - Métrica de similaridade: Inner Product (equivalente a cosine em vetores normalizados)
  - Framework: Python 3.10, sem LangChain (para isolar o banco vetorial)
  - Variável independente: banco de vetores; variáveis controladas: embedding, queries, seed
- [ ] **3.3 Cenários de escala**:
  - S1 (10k): embeddings reais, corpus ag_news, justify por ser próximo a produção pequena
  - S2 (100k): vetores sintéticos normalizados, justify por representar produção média
  - S3 (500k): vetores sintéticos normalizados, justify por representar carga empresarial
  - Justificativa metodológica dos sintéticos (replicam distribuição gaussiana normalizada dos embeddings reais)
- [ ] **3.4 Configurações de índice avaliadas** — tabela com parâmetros de cada índice por banco
- [ ] **3.5 Ground truth** — oráculo via IndexFlatIP, justificativa (prática padrão ANN-Benchmarks)
- [ ] **3.6 Métricas de avaliação** — definição formal de cada métrica:
  - Tempo de indexação (s)
  - Latência p50 e p99 (ms por query)
  - Throughput QPS
  - Consumo de RAM (delta em MB)
  - Tamanho em disco (MB)
  - Recall@k (k=1,5,10) — definição matemática
- [ ] **3.7 Análise estatística** — N=10 repetições, IC 95%, teste Wilcoxon para comparações pareadas de latência
- [ ] **3.8 Ambiente e reprodutibilidade**:
  - Tabela de versões de bibliotecas (preencher após experimento)
  - Especificações do ambiente de execução (Colab: RAM, CPU, Python)
  - Seed de reprodutibilidade: 42
  - Link do repositório GitHub
  - Instruções resumidas para replicação
---

### FASE 4 — Cap. 4: Desenvolvimento e Resultados
**Período:** 27/set – 17/out (21 dias)
**Depende de:** Fase 1 (dados do experimento)

#### 4.1 Resultados operacionais
- [ ] **4.1 Cenário S1 (10k)** — tabela + análise narrativa por banco
- [ ] **4.2 Cenário S2 (100k)** — tabela + análise narrativa por banco
- [ ] **4.3 Cenário S3 (500k)** — tabela + análise + observação sobre limites do Chroma
- [ ] Inserir figuras geradas pelo notebook:
  - [ ] Fig. 1 — Desempenho por cenário (indexação, latência p50, QPS)
  - [ ] Fig. 2 — Recall@k por cenário
  - [ ] Fig. 3 — Heatmap de consumo de recursos (RAM, disco)
  - [ ] Fig. 4 — Distribuição de latência p50 e p99
  - [ ] Fig. 5 — Radar multidimensional (S1+S2 normalizados)
#### 4.2 Análise estatística
- [ ] Apresentar IC 95% para latência p50 de cada banco/cenário
- [ ] Apresentar resultados do teste Wilcoxon para pares significativos
- [ ] Discussão sobre variabilidade: qual banco é mais previsível (menor desvio)?
#### 4.3 Análise qualitativa e discussão
- [ ] Comparar trade-offs: latência vs. recall vs. consumo vs. facilidade operacional
- [ ] Discutir comportamento de cada banco por volume:
  - FAISS: como escala? Qual índice performa melhor em cada cenário?
  - Milvus: overhead de configuração vs. ganho de escala
  - Chroma: quando é a escolha certa e quando não é
- [ ] Discutir achado sobre Chroma no S3 (limite operacional)
#### 4.4 Recomendações práticas (tabela decisória — exigência da banca)
- [ ] Criar matriz de decisão: Volume × Requisito → Banco recomendado
  - Eixo de volume: prototipagem (<50k) / produção leve (<200k) / produção intensa (>200k)
  - Eixo de requisito: latência crítica / recall máximo / facilidade operacional / custo mínimo
- [ ] Para cada célula da matriz: banco recomendado + justificativa em 1-2 frases
- [ ] Adicionar nota sobre quando usar busca híbrida (vetorial + BM25)
---

### FASE 5 — Conclusão e ameaças à validade
**Período:** 18/out – 24/out (7 dias)

#### 5.1 Conclusão
- [ ] Retomar a questão norteadora e respondê-la diretamente com base nos resultados
- [ ] Sumarizar principais evidências experimentais por objetivo específico
- [ ] Declaração formal da contribuição científica:
  - O que foi produzido
  - Qual lacuna preenche
  - O que não existia antes deste trabalho
- [ ] Limitações do estudo (transição para ameaças)
- [ ] Trabalhos futuros:
  - Diferentes modelos de embedding
  - Busca híbrida (densa + esparsa)
  - Avaliação com LLM (ex: RAGAS framework) — deixar como trabalho futuro
  - Outros bancos: Qdrant, Weaviate
  - Hardware com GPU
#### 5.2 Ameaças à validade (framework de Wohlin — exigência da banca)
- [ ] **Validade interna** — o que pode ter causado viés nos resultados medidos:
  - Vetores sintéticos em S2/S3 (discutir diferença em relação a embeddings reais)
  - Milvus Lite vs. Milvus distribuído (discutir extrapolação para produção)
  - Medição de RAM via psutil (overhead de Python, GC não determinístico)
- [ ] **Validade externa** — limitações de generalização:
  - Embedding fixo (all-MiniLM-L6-v2); outros modelos podem gerar distribuições diferentes
  - Corpus em inglês (ag_news); comportamento pode variar em português
  - Hardware CPU-only; ambientes GPU podem inverter rankings de latência
- [ ] **Validade de construto** — as métricas medem o que deveriam:
  - Recall@k como proxy de qualidade semântica (sem avaliação end-to-end com LLM)
  - Latência de batch vs. latência de query real em produção
- [ ] **Validade de conclusão** — robustez estatística:
  - N=10 repetições (suficiente para IC 95% com t-distribution)
  - Warm-up descartado (1 execução inicial não contabilizada)
---

### FASE 6 — ABNT, apêndices e revisão final
**Período:** 25/out – 01/nov (8 dias)

#### 6.1 Formatação ABNT
- [ ] Aplicar normas ABNT NBR 14724 para formatação geral
- [ ] Capa, folha de rosto, sumário, listas de figuras e tabelas
- [ ] Numeração de páginas, margens, espaçamento
- [ ] Formatar todas as referências segundo ABNT NBR 6023
- [ ] Verificar citações no texto (AUTOR, ano) vs. (AUTOR, ano, p. X)
- [ ] Revisar numeração de figuras e tabelas e legendas
  - ✅ Decisão registrada: Figura 1 = RAG pipeline (Cap. 2); Figura 2 = HNSW (Cap. 2); Figuras 3–7 = Cap. 4
  - ⚠️ Figura 2 (HNSW) tem URL placeholder — substituir pela URL real antes da entrega
#### 6.2 Apêndices
- [ ] **Apêndice A** — Código completo do notebook (ou link para GitHub)
- [ ] **Apêndice B** — Tabelas completas de resultados por índice (todas configs de FAISS e Milvus)
- [ ] **Apêndice C** — Configurações detalhadas de cada índice (parâmetros, justificativas)
#### 6.3 Revisão de conteúdo
- [ ] Leitura completa do documento — coerência entre capítulos
- [ ] Verificar se todos os objetivos específicos foram respondidos na Conclusão
- [ ] Verificar se todos os pontos da banca foram endereçados:
  - [ ] Contribuição explícita ("O que este trabalho entrega que não existe hoje?")
  - [ ] Diagrama RAG no Cap. 2
  - [ ] Reprodutibilidade completa (GitHub, hardware, versões)
  - [ ] Justificativa da escolha dos bancos
  - [ ] Análise estatística com IC e Wilcoxon
  - [ ] Ameaças à validade
  - [ ] Recomendações práticas (matriz decisória)
  - [ ] Contribuição científica explícita
- [ ] Revisão ortográfica e gramatical
#### 6.4 Preparação para apresentação
- [ ] Slides de apresentação (estrutura sugerida):
  - Contexto e problema (2 slides)
  - Objetivos e questão norteadora (1 slide)
  - Metodologia e pipeline experimental (2 slides)
  - Resultados por cenário com gráficos (3-4 slides)
  - Análise estatística (1 slide)
  - Recomendações práticas — matriz decisória (1 slide)
  - Conclusão e contribuição científica (1 slide)
  - Ameaças à validade e trabalhos futuros (1 slide)
- [ ] Preparar resposta para pergunta da banca: "Por que não avaliou com LLM?"
  - Resposta: isolamento da variável; Recall@k é métrica padrão ANN; LLM introduziria segunda variável independente; deixado como trabalho futuro com RAGAS
- [ ] Preparar resposta para: "Por que 500k e não 1M?"
  - Resposta: viabilidade de reprodutibilidade (Colab gratuito); resultados mostram tendência clara antes de 500k; documentado nas ameaças à validade
---

## 📅 Visão geral do cronograma

```
AGO                SET                OUT             NOV
16  23  30 | 06  13  20  27 | 04  11  18  25 | 01
|---|---|---|---|---|---|---|---|---|---|---|---|
████████████|                                        FASE 1 — Experimento
            |████████████|                           FASE 2 — Cap. 2
                    |████████████|                   FASE 3 — Cap. 3
                             |███████████████|       FASE 4 — Cap. 4
                                        |███████|    FASE 5 — Conclusão
                                               |████ FASE 6 — ABNT + Revisão
```

---

## 📝 Log de sessões (atualizar a cada sessão de trabalho)

### Sessão 1 — 16/ago/2026
- [x] Diagnóstico completo da monografia
- [x] Definição de escopo (D1 a D6)
- [x] Criação deste cronograma
- [x] **Próximo passo:** Reescrita do notebook para Colab (Fase 1.1)
### Sessão 3 — 20/ago/2026
- [x] Correção de compatibilidade: removido pin de numpy/pandas (conflito Colab 2026)
- [x] **Fase 1.2 concluída:** experimento executado no Colab — S1 (10k), S2 (100k), S3 (500k); Chroma S3 timeout registrado como achado experimental (D6); CSVs, figuras e `ambiente_execucao.json` salvos no Drive
- [x] **Fase 1.3 concluída:** repositório GitHub estruturado com README, requirements.txt, results/
- [x] **Próximo passo:** Fase 2.1 — gap analysis + reescrita objetivos
### Sessão 4 — 26/ago/2026
- [x] **Fase 2.1 concluída:** objetivos (b), (c), (e) reescritos; gap analysis adicionado; 1.3 Justificativa revisada
- [x] **Fase 2.2 concluída:** figura RAG (WOLFF 2023) + figura HNSW (MALKOV 2020 — URL pendente) + 2.4.1 critérios de seleção + referências limpas (10 entradas)
- [x] **Fase 2 100% concluída** — antecipada em relação ao prazo (01–12/set)
- [x] **Pendência transversal registrada (2.2.1):** definição formal de Recall@k, Precision@k, ground truth e oráculo — subseção obrigatória no Cap. 3
- ⚠️ **Nota operacional:** `/mnt/project/` é cópia somente-leitura — edições devem ser feitas em `/home/claude/` e entregues via `present_files`
- [x] **Próximo passo:** Cap. 3 — Metodologia (Fase 3)
### Sessão 2 — 17/ago/2026
- [x] **Fase 1.1 concluída:** notebook reescrito como `benchmark_rag_vectordbs_v2.ipynb`
  - 43 células (20 code + 23 markdown), 51 KB
  - COLAB_MODE=True, S3=500k, N_RUNS=10, versões fixadas
  - Drive mount, save_checkpoint incremental, timeout Chroma (D6)
  - IC 95% (scipy t-distribution), Wilcoxon signed-rank, CV por banco
  - Célula de verificação automática (9.1), ambiente.json (9.2), cópia Drive (9.3)
  - Figs 1–5: desempenho, recall@k, heatmap recursos, latência percentílica, radar
- [x] **Fase 1.2 (parcial):** células 9.1–9.3 automatizam verificação, registro e exportação
### Sessão 3 — 20/ago/2026
- [x] **Correção de compatibilidade:** removido pin de numpy/pandas do `%pip install` (conflito com Colab 2026 que já usa numpy>=2.0); adicionada verificação de versão na célula de configuração
- [x] **Fase 1.2 concluída:** experimento executado por completo no Colab
  - S1 (10k, embeddings reais), S2 (100k, sintético), S3 (500k, sintético)
  - Chroma S3: timeout de inserção registrado como achado experimental (D6)
  - Bloco de timeout enriquecido com: `vetores_inseridos`, `taxa_insercao_vps`, `proporcao_feita_pct`, `tempo_estimado_min`
  - Correção de `UniqueConstraintError` via `_reset_chroma_client()` + `clear_system_cache()`
  - CSVs, figuras e `ambiente_execucao.json` salvos no Drive
  - Ambiente: Linux 6.6.122+, Python 3.12.13, numpy 2.0.2, 12.67 GB RAM
- [x] **Fase 1.3 concluída:** repositório GitHub estruturado
  - `README.md` completo com tabela Wilcoxon, radar embed, seção de reprodução dual (Colab + local)
  - `requirements.txt` com versões do experimento real (numpy/pandas com pin mínimo `>=`)
  - Estrutura `results/` documentada
- [x] **Próximo passo:** Fase 2.1 — gap analysis na Introdução + reescrita do objetivo específico (e)
---

## 🤖 Instruções para agentes

Ao retomar o trabalho neste projeto:

1. **Leia este arquivo primeiro** para entender o estado atual e qual é o próximo TODO
2. **Marque os itens concluídos** com `[x]` e adicione a data no Log de sessões
3. **Não altere as Decisões de escopo** (seção ⚙️) sem registrar justificativa
4. **Ao escrever conteúdo da monografia**, siga o estilo já estabelecido no Cap. 1 e 2: formal, técnico, sem bullet points no corpo do texto, com citações ABNT
5. **Ao reescrever o notebook**, manter compatibilidade com Colab gratuito (Python 3.12, sem GPU, RAM ~12 GB, numpy>=2.0)
6. **Arquivo de monografia** está em `/mnt/project/Monografia`
7. **Notebook atualizado** está em `/mnt/project/benchmark_rag_vectordbs.ipynb` (original) — versão v2 em outputs
### Contexto rápido do projeto
- TCC sobre comparação de bancos vetoriais (FAISS, Milvus, Chroma) em pipelines RAG
- Experimento: 3 cenários (10k, 100k, 500k vetores), métricas operacionais + Recall@k
- **Sem LLM** no experimento (decisão D2)
- Banca pediu: diagrama RAG, justificativa dos bancos, análise estatística, ameaças à validade, recomendações práticas, contribuição explícita
