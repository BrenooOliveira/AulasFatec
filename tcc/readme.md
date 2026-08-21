# Comparação de Bancos Vetoriais em Pipelines RAG

**Trabalho de Conclusão de Curso — Análise e Desenvolvimento de Sistemas**
**Autor:** Breno Alves
**Título completo:** *Comparação de Bancos Vetoriais Aplicados a Solução de Geração Aumentada de Recuperação*

---

## Visão geral

Este repositório contém o experimento computacional do TCC, que realiza uma comparação sistemática entre três bancos de vetores de código aberto — **FAISS**, **Milvus** e **Chroma** — integrados a um pipeline de Geração Aumentada de Recuperação (RAG) padronizado.

O experimento mede métricas operacionais e qualidade de recuperação semântica em três cenários de escala distintos, sem incluir um LLM (ver [Decisões de escopo](#decisões-de-escopo)).

![Perfil multidimensional dos bancos vetoriais](results/fig5_radar.png)

> Perfil multidimensional normalizado (S1 + S2). FAISS domina latência e throughput; Milvus lidera Recall@10 e eficiência de disco; Chroma apresenta perfil mais equilibrado em cenários de menor escala.

---

## Questão norteadora

> Dado que o desempenho de uma solução RAG é fortemente dependente da qualidade da recuperação vetorial, **qual banco de vetores apresenta o melhor equilíbrio entre desempenho, escalabilidade e qualidade de recuperação semântica** para diferentes volumes de dados?

---

## Estrutura do repositório

```
.
├── benchmark_rag_vectordbs_v2.ipynb   # Notebook principal (Colab-ready)
├── requirements.txt                   # Dependências com versões fixadas
├── results/
│   ├── resultados_completos.csv       # Métricas consolidadas (9 linhas: 3 bancos × 3 cenários)
│   ├── resultados_faiss_detalhado.csv # Todas as configs de índice do FAISS
│   ├── resultados_milvus_detalhado.csv
│   ├── resultados_chroma_detalhado.csv
│   ├── resultados_ic95.csv            # IC 95% e CV por banco/cenário
│   ├── resultados_wilcoxon.csv        # Teste Wilcoxon signed-rank entre pares
│   ├── ambiente_execucao.json         # Specs do hardware e versões de bibliotecas
│   ├── fig1_desempenho.png            # Tempo de indexação, latência p50, QPS
│   ├── fig2_recall_k.png              # Recall@k (k = 1, 5, 10) por cenário
│   ├── fig3_recursos.png              # Heatmap RAM e disco
│   ├── fig4_latencia.png              # Distribuição de latência p50 e p99
│   └── fig5_radar.png                 # Análise radar multidimensional
└── README.md
```

---

## Cenários experimentais

| Cenário | Vetores | Embeddings | Objetivo |
|---------|--------:|------------|----------|
| **S1** | 10.000 | Reais — corpus ag_news + all-MiniLM-L6-v2 | Prototipagem e aplicações pequenas |
| **S2** | 100.000 | Sintéticos normalizados (384 dims) | Produção leve / escala intermediária |
| **S3** | 500.000 | Sintéticos normalizados (384 dims) | Carga empresarial / limite de viabilidade |

**Por que vetores sintéticos em S2 e S3?** Vetores gaussianos normalizados replicam a distribuição de embeddings reais normalizados, permitindo avaliar o comportamento de indexação e busca em alta escala com reprodutibilidade garantida e viabilidade no Colab gratuito.

---

## Bancos e índices avaliados

| Banco | Índices avaliados | Posicionamento |
|-------|-------------------|----------------|
| **FAISS** | `IndexFlatIP` (exato), `IndexHNSWFlat` (M=32), `IndexIVFFlat` (S3 apenas) | Alta performance, sem servidor |
| **Milvus Lite** | `FLAT` (exato), `HNSW` (M=16, efConstruction=200, ef=64) | Escala empresarial, API distribuída |
| **Chroma** | `HNSW` automático (sem seleção de tipo) | Simplicidade e experiência do desenvolvedor |

---

## Métricas coletadas

| Categoria | Métricas |
|-----------|----------|
| **Qualidade** | Recall@1, Recall@5, Recall@10 |
| **Latência** | p50, p90, p99 (ms/query) com IC 95% |
| **Throughput** | Queries por segundo (QPS) |
| **Recursos** | Delta de RAM na indexação (MB), tamanho do índice em disco (MB) |
| **Indexação** | Tempo total de ingestão + construção do índice (s) |
| **Estatística** | Desvio padrão, IC 95% (t-distribution, N=10), Wilcoxon signed-rank |

O **ground truth** para Recall@k é gerado por `IndexFlatIP` (busca exata) do FAISS — prática padrão do ANN-Benchmarks.

---

## Principais achados

### Análise estatística (Wilcoxon signed-rank, N=10, α=0,05)

Todos os pares de bancos apresentaram diferenças de latência **estatisticamente significativas** nos cenários S1 e S2. No S3, a comparação envolvendo o Chroma não foi realizada em decorrência do timeout de inserção (ver [Decisão D6](#decisões-de-escopo)).

| Cenário | Par | p-valor | Significativo |
|---------|-----|--------:|:---:|
| S1 | FAISS × Milvus | 0,0020 | ✅ |
| S1 | FAISS × Chroma | 0,0020 | ✅ |
| S1 | Milvus × Chroma | 0,0195 | ✅ |
| S2 | FAISS × Milvus | 0,0020 | ✅ |
| S2 | FAISS × Chroma | 0,0020 | ✅ |
| S2 | Milvus × Chroma | 0,0020 | ✅ |
| S3 | FAISS × Milvus | 0,0020 | ✅ |
| S3 | FAISS × Chroma | — | ⚠️ Chroma: timeout de inserção |
| S3 | Milvus × Chroma | — | ⚠️ Chroma: timeout de inserção |

### Perfis por banco (S1 + S2, normalizados)

- **FAISS** — melhor throughput (QPS) e menor latência p99 de todos os cenários; maior consumo de RAM por manter o índice inteiro em memória.
- **Milvus** — maior Recall@10 e melhor eficiência de disco; overhead de inserção maior que o FAISS, compensado pela qualidade de recuperação.
- **Chroma** — perfil equilibrado em S1 e S2; comportamento de inserção degradou significativamente em S3 (500k vetores), atingindo o limite de timeout de 30 minutos — achado registrado como resultado experimental.

---

## Como reproduzir

### Opção 1 — Google Colab (recomendado)

1. Abra [`benchmark_rag_vectordbs_v2.ipynb`](benchmark_rag_vectordbs_v2.ipynb) no Google Colab
2. Conecte ao ambiente de execução padrão (CPU, ~12 GB RAM)
3. Execute todas as células em ordem
4. Os resultados são salvos automaticamente no Google Drive (`TCC_BenchmarkRAG/`)

> **Sem GPU necessária.** O experimento foi projetado para CPU-only.

### Opção 2 — Ambiente local

**Requisitos mínimos:** Python 3.10+, 16 GB RAM, ~5 GB de espaço em disco

```bash
git clone https://github.com/<seu-usuario>/<seu-repo>.git
cd <seu-repo>

python -m venv .venv
source .venv/bin/activate        # Windows: .venv\Scripts\activate

pip install -r requirements.txt
jupyter notebook benchmark_rag_vectordbs_v2.ipynb
```

> **Nota:** No Colab, `numpy` e `pandas` não são versionados no `requirements.txt` — o Colab já os fornece em versão compatível. Em ambiente local, instale `numpy>=2.0` e `pandas>=2.2.2` manualmente ou via `pip install -r requirements.txt`.

---

## Ambiente de execução (experimento original)

Os resultados publicados foram obtidos no seguinte ambiente:

| Parâmetro | Valor |
|-----------|-------|
| Sistema operacional | Linux 6.6.122+ (x86\_64) |
| Python | 3.12.13 |
| CPUs lógicos | 2 (1 físico) |
| RAM total | 12,67 GB |
| Plataforma | Google Colab (CPU) |

**Bibliotecas principais:**

| Biblioteca | Versão |
|------------|--------|
| faiss-cpu | 1.9.0 |
| pymilvus | 2.5.8 |
| chromadb | 0.6.3 |
| sentence-transformers | 3.4.1 |
| datasets | 3.2.0 |
| numpy | 2.0.2 |
| pandas | 2.2.3 |
| scipy | 1.15.2 |
| psutil | 6.1.1 |
| matplotlib | 3.10.0 |
| seaborn | 0.13.2 |

Arquivo completo: [`results/ambiente_execucao.json`](results/ambiente_execucao.json)

---

## Decisões de escopo

Decisões tomadas antes da execução do experimento e mantidas fixas ao longo de toda a pesquisa:

| ID | Decisão | Justificativa |
|----|---------|---------------|
| **D1** | S3 fixado em **500k vetores** (não 1M) | Viabilidade no Colab gratuito; tendência de escala observável antes desse limite |
| **D2** | **LLM removida** do experimento | Introduziria segunda variável independente; Recall@k é métrica padrão ANN suficiente |
| **D3** | IVF incluído apenas no FAISS/S3 | Configuração extra comparativa; não altera o índice principal avaliado |
| **D4** | `N_RUNS = 10` repetições | Necessário para IC 95% (t-distribution) e teste Wilcoxon |
| **D5** | Objetivo (e) focado em Recall@k | Proxy de qualidade semântica; sem avaliação de respostas de LLM |
| **D6** | Timeout do Chroma no S3 é **um resultado** | O limite operacional em alta escala é um achado experimental, não uma omissão |

---

## Modelo de embeddings

| Parâmetro | Valor |
|-----------|-------|
| Modelo | `sentence-transformers/all-MiniLM-L6-v2` |
| Dimensionalidade | 384 |
| Normalização | Sim (`normalize_embeddings=True`) |
| Métrica de similaridade | Inner Product (≡ cosine similarity em vetores normalizados) |
| Corpus (S1) | ag\_news — 10.000 textos de notícias em inglês |
| Ground truth | `IndexFlatIP` do FAISS (busca exata sem aproximação) |
| Seed de reprodutibilidade | 42 |

---

## Licença

Este projeto está licenciado sob a [MIT License](LICENSE).

---

## Referências principais

- Lewis, P. et al. *Retrieval-Augmented Generation for Knowledge-Intensive NLP Tasks*. NeurIPS, 2020. [arXiv:2005.11401](https://arxiv.org/abs/2005.11401)
- Meta AI Research. *FAISS: A Library for Efficient Similarity Search*. 2021. [faiss.ai](https://faiss.ai)
- LF AI & Data Foundation. *Milvus Documentation*. 2021. [milvus.io/docs](https://milvus.io/docs)
- Chroma. *Chroma Documentation*. 2023. [docs.trychroma.com](https://docs.trychroma.com)
- Aumuller, M. et al. *ANN-Benchmarks: A Benchmarking Tool for Approximate Nearest Neighbor Algorithms*. Information Systems, 2020.
