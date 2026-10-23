---
name: tcc-monografia-coherence
description: Use when reviewing or editing this TCC monograph for coherence between chapters, alignment with the executed benchmark, methodological consistency, scope boundaries, figure references, and unresolved contradictions before continuing to the next chapter.
---

# TCC Monograph Coherence

## Scope

This repository compares FAISS, Milvus Lite, and Chroma as vector-retrieval solutions for the retrieval component of an RAG architecture. The executed experiment must not be changed merely to accommodate the prose.

## Review order

1. Treat `monografia/monografia.md` as the canonical editorial source.
2. Read `AGENTS.md`, `monografia/cronograma.md`, and `monografia/figure_registry.md` for project constraints.
3. Check the notebook and `results/` only as evidence of what was actually executed.
4. Review the document in order: research question, objectives, theoretical scope, methodology, metrics, environment, and chapter map.
5. Classify findings as blocker, required correction, or later editorial cleanup.

## Required consistency checks

- Do not call the experiment a complete end-to-end RAG pipeline: it has no LLM or generated-answer evaluation.
- Distinguish semantic retrieval on real AG News embeddings (S1) from geometric nearest-neighbor behavior on synthetic normalized vectors (S2/S3).
- State that Milvus Lite was evaluated locally; do not generalize local results to distributed Milvus deployments.
- Describe the experimental factor as solution plus index configuration and parameters, not only database brand.
- Name the implemented metric precisely: the notebook uses a hit-based top-k measure, recorded as `Recall@k`, where any non-empty intersection counts as a hit. Explain that this is more permissive than classical recall.
- Do not promise metrics that were not collected, especially `Precision@k`.
- Keep the canonical notebook name aligned with the repository state; do not reintroduce removed `v2` references.
- Check every figure path and remove or resolve placeholders before delivery.
- Do not rewrite notebook code or rerun the benchmark during a prose review unless the user explicitly authorizes it.

## Decision rule

The document is ready to advance from a chapter only when its claims are supported by the executed artifacts, its scope matches the experiment, and no later chapter is described as already complete when it does not exist.
