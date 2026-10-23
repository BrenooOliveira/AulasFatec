---
name: tcc-benchmark-evidence
description: Use when grounding TCC claims, tables, discussions, or conclusions in the existing FAISS, Milvus Lite, and Chroma benchmark CSVs without rerunning the expensive notebook.
---

# TCC Benchmark Evidence

## Evidence policy

- Treat `results/resultados_completos.csv` as the consolidated source for one row per solution and scenario.
- Treat `results/*_detalhado.csv` as the source for index-level configurations and raw repeated latency values.
- Use `results/resultados_ic95.csv` for confidence intervals and variability, and `results/resultados_wilcoxon.csv` for paired latency comparisons.
- Use `results/ambiente_execucao.json` for the actual runtime, package versions, query count, repetitions, dimensions, and scenarios.
- Never invent, silently round, or replace a missing value.
- Preserve the Chroma S3 `timeout_insercao` status as an experimental result; do not convert it into a fabricated latency or recall value.

## Interpretation boundaries

- FAISS Flat is the exact-search baseline/oracle; FAISS HNSW and IVF are approximate configurations.
- Milvus Flat is the exact local Milvus Lite baseline; Milvus HNSW is an approximate local configuration.
- Chroma uses automatic HNSW configuration in the executed benchmark.
- S1 uses real AG News text embeddings; S2 and S3 use synthetic Gaussian normalized vectors.
- The notebook's `Recall@k` is hit-based: a query is counted as successful when the retrieved top-k intersects the exact top-k. Do not interpret it as recovering every relevant item.
- Latency is measured for a batch of 200 queries, divided into milliseconds per query; it is not isolated single-query production latency.
- Wilcoxon comparisons use the ten repeated latency measurements and do not establish superiority for quality, disk, RAM, or distributed deployment.
- Do not generalize Milvus Lite results to a Milvus cluster or Chroma behavior beyond the tested local environment.

## Safe workflow

1. Inspect the relevant CSV rows before writing prose.
2. Cross-check units, index names, status, and missing values.
3. Write the claim with its scenario, configuration, and limitation.
4. Record unresolved evidence gaps as explicit questions or limitations.
5. Do not execute the full notebook as a validation shortcut; it is expensive and the existing artifacts are the evidence baseline.
