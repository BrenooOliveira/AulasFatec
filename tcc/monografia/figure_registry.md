# FIGURE_REGISTRY — Monografia TCC
# Todas as figuras do projeto · controle de citações ABNT

> **Convenção alt text markdown:**
> `![Descrição objetiva da figura. Fonte: CITAÇÃO ABNT.](caminho/arquivo)`
>
> - Figura criada pelo autor → `Elaborado pelo autor, 2026.`
> - Figura adaptada → `Adaptado de SOBRENOME, Ano, p. XX.`
> - Figura reproduzida → `Disponível em: URL. Acesso em: DD mmm. AAAA.`

---

## Cap. 3 — Metodologia

### Fig 3.1 — Pipeline Experimental
| Campo | Valor |
|-------|-------|
| **Arquivo** | `fig_3_1_pipeline.mermaid` |
| **Seção** | 3.2 Pipeline Padronizado |
| **Tipo** | Mermaid (específico do projeto) |
| **Citação ABNT** | Elaborado pelo autor, 2026. |
| **Alt text** | `Pipeline experimental completo do benchmark, dividido em três fases: Indexação (corpus → embedding → banco de vetores), Busca (queries → similaridade → top-k) e Avaliação (comparação com ground truth → métricas). Elaborado pelo autor, 2026.` |
| **Status** | ✅ Pronto |

---

### Fig 3.2 — Cenários de Escala (S1, S2, S3)
| Campo | Valor |
|-------|-------|
| **Arquivo** | `fig_3_2_cenarios.mermaid` |
| **Seção** | 3.3 Cenários de Escala |
| **Tipo** | Mermaid (específico do projeto) |
| **Citação ABNT** | Elaborado pelo autor, 2026. |
| **Alt text** | `Progressão dos três cenários de escala avaliados: S1 com 10 mil vetores do corpus AG News, S2 com 100 mil vetores sintéticos e S3 com 500 mil vetores sintéticos, cada cenário representando um porte distinto de aplicação RAG. Elaborado pelo autor, 2026.` |
| **Status** | ✅ Pronto |

---

### Fig 3.3 — Construção do Ground Truth
| Campo | Valor |
|-------|-------|
| **Arquivo** | `fig_3_3_ground_truth.mermaid` |
| **Seção** | 3.5 Ground Truth |
| **Tipo** | Mermaid (específico do projeto) |
| **Citação ABNT** | Elaborado pelo autor, 2026. |
| **Alt text** | `Processo de construção do oráculo de busca exata: o FAISS IndexFlatIP processa o corpus completo e as queries offline gerando o ground truth; os índices aproximados (FAISS HNSW, Milvus HNSW, Chroma HNSW) têm seus resultados comparados ao oráculo para cálculo do Recall@k. Elaborado pelo autor, 2026.` |
| **Status** | ✅ Pronto |

---

### Fig 3.4 — Recall@k como taxa de acerto (exemplo)
| Campo | Valor |
|-------|-------|
| **Arquivo** | `fig_3_4_recall_acerto.svg` (substitui `fig_3_4_recall_acerto.svg`) |
| **Seção** | 3.6.1 Recall@k |
| **Tipo** | SVG (criado pelo autor) |
| **Citação ABNT** | Elaborado pelo autor, 2026. |
| **Alt text** | `Exemplo de cálculo do Recall@k como taxa de acerto, com k igual a 5 e cinco consultas ilustrativas: para cada consulta, compara-se o ground truth com os itens recuperados; a consulta conta como acerto quando a interseção não é vazia, resultando em Recall@5 igual a 0,80. Elaborado pelo autor, 2026.` |
| **Status** | ✅ Pronto |

-------|-------|
| **Arquivo** | `fig_3_4_recall_acerto.svg` |
| **Seção** | 3.6.1 Recall@k |
| **Tipo** | SVG (criado pelo autor) |
| **Citação ABNT** | Elaborado pelo autor, 2026. |
| **Alt text** | `Diagrama de conjuntos ilustrando o cálculo do Recall@k com k igual a 5: o conjunto Relevantes contém os cinco vizinhos exatos do ground truth; o conjunto Recuperados@k contém os cinco itens retornados pelo índice aproximado; a interseção de três elementos corresponde aos acertos, resultando em Recall@5 igual a 0,60. Elaborado pelo autor, 2026.` |
| **Status** | ✅ Pronto |

---

## Figuras Externas (conhecimento público)

> Metadados confirmados via fetch das páginas originais em 15 set. 2026.
> As figuras externas listadas abaixo foram avaliadas, mas não são utilizadas na fonte canônica atual. As definições correspondentes permanecem em texto e em LaTeX.

### Fig 3.5 — Precision@k: fórmula e interpretação
| Campo | Valor |
|-------|-------|
| **Arquivo local** | — |
| **Uso** | Seção 3.6.2 — Precision@k |
| **Autor institucional** | EVIDENTLY AI |
| **Título da página** | Precision and recall at K in ranking and recommendations |
| **Plataforma** | Evidently AI |
| **Data de publicação** | 9 jan. 2025 |
| **Data de acesso** | 15 set. 2026 |
| **URL da página** | https://www.evidentlyai.com/ranking-metrics/precision-recall-at-k |
| **URL direta da imagem** | https://cdn.prod.website-files.com/660ef16a9e0687d9cc27474a/662c4327f27ee08d3e4d4b34_65777ee1fd55288155f28d37_precision_recall_k2.png |
| **ABNT completo** | `EVIDENTLY AI. Precision and recall at K in ranking and recommendations. Evidently AI, 9 jan. 2025. Disponível em: https://www.evidentlyai.com/ranking-metrics/precision-recall-at-k. Acesso em: 15 set. 2026.` |
| **Alt text monografia** | `Fórmula e definição de Precision@k: razão entre os itens relevantes recuperados e o total de itens retornados no top-k. Adaptado de: EVIDENTLY AI, 2025.` |
| **Status** | Não utilizada na fonte canônica; definição mantida em LaTeX |

---

### Fig 3.6 — Recall@k: fórmula e interpretação
| Campo | Valor |
|-------|-------|
| **Arquivo local** | — |
| **Uso** | Seção 3.6.1 — Recall@k (complementar ao SVG Fig 3.4) |
| **Autor institucional** | EVIDENTLY AI |
| **Título da página** | Precision and recall at K in ranking and recommendations |
| **Plataforma** | Evidently AI |
| **Data de publicação** | 9 jan. 2025 |
| **Data de acesso** | 15 set. 2026 |
| **URL da página** | https://www.evidentlyai.com/ranking-metrics/precision-recall-at-k |
| **URL direta da imagem** | https://cdn.prod.website-files.com/660ef16a9e0687d9cc27474a/662c4327f27ee08d3e4d4b2e_6577812c4d677925f1ab5f84_precision_recall_k9.png |
| **ABNT completo** | `EVIDENTLY AI. Precision and recall at K in ranking and recommendations. Evidently AI, 9 jan. 2025. Disponível em: https://www.evidentlyai.com/ranking-metrics/precision-recall-at-k. Acesso em: 15 set. 2026.` |
| **Alt text monografia** | `Fórmula e definição de Recall@k: razão entre os itens relevantes recuperados no top-k e o total de itens relevantes existentes no conjunto. Adaptado de: EVIDENTLY AI, 2025.` |
| **Status** | Não utilizada na fonte canônica; definição mantida em LaTeX |

---

### Fig 3.7 — Cosine Similarity: ângulo entre vetores
| Campo | Valor |
|-------|-------|
| **Arquivo local** | — |
| **Uso** | Seção 3.2 — Inner Product (após tabela de métricas de similaridade) |
| **Autor** | SHIVANANDHAN, Manish |
| **Título** | How does cosine similarity work? The math behind LLMs explained |
| **Plataforma** | freeCodeCamp |
| **Data de publicação** | 18 set. 2025 |
| **Data de acesso** | 15 set. 2026 |
| **URL da página** | https://www.freecodecamp.org/news/how-does-cosine-similarity-work/ |
| **URL direta da imagem** | https://cdn.hashnode.com/res/hashnode/image/upload/v1757481567784/ccd74ce9-d561-47b5-a5e8-c3e77ed36e79.png |
| **ABNT completo** | `SHIVANANDHAN, Manish. How does cosine similarity work? The math behind LLMs explained. freeCodeCamp, 18 set. 2025. Disponível em: https://www.freecodecamp.org/news/how-does-cosine-similarity-work/. Acesso em: 15 set. 2026.` |
| **Alt text monografia** | `Representação geométrica da similaridade coseno: dois vetores partindo da mesma origem formam um ângulo θ; quanto menor o ângulo, maior a similaridade entre os vetores. Adaptado de: SHIVANANDHAN, 2025.` |
| **Status** | Não utilizada na fonte canônica; definição mantida em LaTeX |

---

## Cap. 2 — Referência

> Figuras do Capítulo 2 auditadas: os assets da Figura 1/RAG e da Figura 2/HNSW existem localmente e são referenciados pela fonte canônica.

## Cap. 4 — Desenvolvimento e Resultados

### Fig 4.1 — Desempenho por cenário
| Campo | Valor |
|-------|-------|
| **Arquivo** | `results/fig1_desempenho.png` |
| **Seção** | 4.2 Resultados do cenário S1 |
| **Tipo** | PNG gerado pelo notebook a partir dos CSVs |
| **Citação ABNT** | Elaborado pelo autor, 2026. |
| **Alt text** | `Desempenho de indexação, latência p50 e throughput dos bancos nos três cenários. Fonte: Elaborado pelo autor, 2026.` |
| **Status** | ✅ Presente |

### Fig 4.2 — Recall@k por cenário
| Campo | Valor |
|-------|-------|
| **Arquivo** | `results/fig2_recall_k.png` |
| **Seção** | 4.3 Resultados do cenário S2 |
| **Tipo** | PNG gerado pelo notebook a partir dos CSVs |
| **Citação ABNT** | Elaborado pelo autor, 2026. |
| **Alt text** | `Recall@1, Recall@5 e Recall@10 dos bancos nos cenários avaliados. Fonte: Elaborado pelo autor, 2026.` |
| **Status** | ✅ Presente |

### Fig 4.3 — Recursos de indexação
| Campo | Valor |
|-------|-------|
| **Arquivo** | `results/fig3_recursos.png` |
| **Seção** | 4.4 Resultados do cenário S3 |
| **Tipo** | PNG gerado pelo notebook a partir dos CSVs |
| **Citação ABNT** | Elaborado pelo autor, 2026. |
| **Alt text** | `Consumo de RAM e tamanho em disco durante a indexação. Fonte: Elaborado pelo autor, 2026.` |
| **Status** | ✅ Presente; Chroma/S3 representa valores parciais até o timeout |

### Fig 4.4 — Distribuição de latência
| Campo | Valor |
|-------|-------|
| **Arquivo** | `results/fig4_latencia.png` |
| **Seção** | 4.5 Incerteza e análise estatística |
| **Tipo** | PNG gerado pelo notebook a partir dos CSVs |
| **Citação ABNT** | Elaborado pelo autor, 2026. |
| **Alt text** | `Latência p50 e p99 por banco e cenário. Fonte: Elaborado pelo autor, 2026.` |
| **Status** | ✅ Presente; Chroma/S3 não possui barras de latência |

### Fig 4.5 — Perfil multidimensional
| Campo | Valor |
|-------|-------|
| **Arquivo** | `results/fig5_radar.png` |
| **Seção** | 4.6 Discussão dos trade-offs |
| **Tipo** | PNG gerado pelo notebook a partir dos CSVs |
| **Citação ABNT** | Elaborado pelo autor, 2026. |
| **Alt text** | `Perfil multidimensional normalizado dos bancos nos cenários S1 e S2. Fonte: Elaborado pelo autor, 2026.` |
| **Status** | ✅ Presente; normalização min-max exploratória, sem S3 |

---

## Resumo de Status

| Fig | Título | Arquivo | ABNT | Inserida no doc | Download |
|-----|--------|---------|------|----------------|---------|
| 3.1 | Pipeline Experimental | `fig_3_1_pipeline.mermaid` | ✅ Autor | ✅ Seção 3.2 | — |
| 3.2 | Cenários S1/S2/S3 | `fig_3_2_cenarios.mermaid` | ✅ Autor | ✅ Seção 3.3 | — |
| 3.3 | Ground Truth | `fig_3_3_ground_truth.mermaid` | ✅ Autor | ✅ Seção 3.5 | — |
| 3.4 | Recall@k Venn | `fig_3_4_recall_acerto.svg` | ✅ Autor | ✅ Seção 3.6.1 | — |
| 3.5 | Precision@k fórmula | — | Conceito mantido em texto; não é métrica coletada | Não utilizada | — |
| 3.6 | Recall@k fórmula | — | Fórmula mantida em LaTeX na seção 3.6.1 | Não utilizada | — |
| 3.7 | Cosine Similarity | — | Equivalência com Inner Product explicada em texto | Não utilizada | — |

---

## Referências Bibliográficas das Figuras Externas
*(para incluir na seção de Referências da monografia — ABNT NBR 6023:2018)*

```
EVIDENTLY AI. Precision and recall at K in ranking and recommendations.
Evidently AI, 9 jan. 2025. Disponível em:
https://www.evidentlyai.com/ranking-metrics/precision-recall-at-k.
Acesso em: 15 set. 2026.

SHIVANANDHAN, Manish. How does cosine similarity work? The math behind
LLMs explained. freeCodeCamp, 18 set. 2025. Disponível em:
https://www.freecodecamp.org/news/how-does-cosine-similarity-work/.
Acesso em: 15 set. 2026.
```

---
