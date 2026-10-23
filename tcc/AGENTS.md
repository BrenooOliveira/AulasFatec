# Instruções para agentes

## Objetivo do repositório

Este projeto apoia a escrita e a entrega da monografia sobre a comparação de FAISS, Milvus e Chroma em pipelines RAG. Preserve a separação entre:

- `monografia/monografia.md`: fonte editorial canônica do texto.
- `results/`: evidências produzidas pelo benchmark; não é fonte para editar manualmente.
- `benchmark_rag_vectordbs_v2.ipynb`: notebook principal e versão recomendada para reproduzir o experimento.
- `docx_generator/`: gerador do DOCX final; arquivos `.docx` e `_pendencias.md` são derivados.

Use `readme.md` para a visão geral do experimento e `monografia/cronograma.md` para o estado editorial e as pendências. Consulte `monografia/figure_registry.md` antes de inserir, mover ou citar figuras.

## Fluxo de trabalho

1. Antes de redigir resultados, confirme os números nos CSVs correspondentes em `results/`, especialmente `resultados_completos.csv`, `*_detalhado.csv`, `resultados_ic95.csv` e `resultados_wilcoxon.csv`.
2. Não invente, arredonde silenciosamente ou altere valores experimentais. Se uma afirmação não puder ser confirmada nos artefatos, marque-a como pendência.
3. Mantenha as decisões de escopo registradas em `monografia/cronograma.md`: S1 = 10 mil, S2 = 100 mil, S3 = 500 mil vetores; `N_RUNS = 10`; não há LLM no experimento.
4. Preserve a distinção entre Recall@k implementado e a definição clássica: o benchmark contabiliza uma consulta como acerto quando há interseção não vazia entre ground truth e resultados recuperados. Explique essa variante ao redigir metodologia ou resultados.
5. Diferencie S1, com corpus `ag_news` e embeddings reais, de S2/S3, com vetores sintéticos normalizados. Não generalize resultados de Milvus Lite para um cluster distribuído sem declarar a limitação.
6. Ao alterar Markdown, preserve a numeração e a estrutura de títulos, citações autor-data, fórmulas e referências ABNT existentes. Evite reescrever seções não relacionadas à solicitação.

## Figuras, tabelas e fontes

- Use o texto alternativo das figuras para uma descrição objetiva e a fonte ABNT, conforme `monografia/figure_registry.md`.
- Para figuras autorais, use `Elaborado pelo autor, 2026.`; para figuras adaptadas ou externas, registre autor, ano, URL e data de acesso.
- Não deixe placeholders como `PENDENTE` em uma versão de entrega. A Figura 2/HNSW e os downloads listados no registro ainda exigem revisão.
- Verifique tabelas Markdown no DOCX gerado, pois o conversor não possui suporte dedicado completo a blocos de tabela.

## Benchmark e ambiente

O benchmark pode ser caro em tempo e memória, especialmente Chroma no cenário S3. Não execute o notebook completo automaticamente; peça confirmação antes de uma execução longa. A versão local documentada é:

```bash
python -m venv .venv
source .venv/bin/activate
pip install -r requirements.txt
jupyter notebook benchmark_rag_vectordbs_v2.ipynb
```

Há uma possível divergência entre `requirements.txt` e as versões instaladas no notebook, sobretudo NumPy. Ao investigar reprodução, registre a versão efetivamente usada em `results/ambiente_execucao.json` e não altere resultados existentes para resolver a divergência.

## Geração e validação do DOCX

O DOCX deve ser gerado a partir da fonte canônica, nunca editado como fonte editorial:

```bash
cd docx_generator
python monografia_full.py monografia_config.json ../monografia/monografia.md --output tcc_breno.docx --font Arial
```

Depois da geração, leia o relatório `tcc_breno_pendencias.md` e revise visualmente o DOCX. O conversor não baixa imagens externas; URLs e placeholders podem exigir tratamento manual. Não trate o DOCX como validado apenas porque o script terminou sem erro.

Antes da entrega, revise também os dados provisórios de banca, data e resultado em `docx_generator/monografia_config.json`.

## Limites de edição

- Não edite arquivos em `results/`, notebooks ou o DOCX gerado para corrigir texto da monografia.
- Alterações no benchmark devem ser feitas no notebook e acompanhadas de nova execução ou de justificativa explícita.
- Ignore `.venv/`, locks temporários e artefatos DOCX gerados em revisões de código.
- Não execute comandos destrutivos nem reverta alterações existentes sem solicitação explícita.

## Validação mínima

Para mudanças no texto, execute a geração do DOCX e examine o relatório de pendências. Para mudanças no gerador Python, compile ou execute o fluxo de geração e corrija apenas erros relacionados à alteração. Para mudanças no notebook, valide primeiro as células ou o trecho afetado; uma execução completa requer confirmação devido ao custo do experimento.
