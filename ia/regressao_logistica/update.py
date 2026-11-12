import json

path = 'aula15_logit(churn) - Parte2.ipynb'
with open(path, 'r', encoding='utf-8') as f:
    nb = json.load(f)

# Cell 37: CONCLUSÃO detalhada
cell_conclusao = {
    'cell_type': 'markdown',
    'id': 'conclusao_detalhada',
    'metadata': {'id': 'conclusao_detalhada'},
    'source': [
        'Com base nos resultados obtidos no **Classification Report** e na **Matriz de Confusão**, podemos extrair as seguintes conclusões fundamentais sobre a performance inicial do modelo de Regressão Logística:\n',
        '\n',
        '1. **Acurácia Geral (~73%)**:\n',
        '   - À primeira vista, uma acurácia de aproximadamente 73% parece satisfatória, mas ela é fortemente influenciada pela predominância da classe majoritária no conjunto de teste (3.054 clientes da classe 0 contra 1.176 da classe 1).\n',
        '\n',
        '2. **Desempenho por Classe (O Problema do Desbalanceamento)**:\n',
        '   - **Classe 0 (Clientes que Ficaram)**: Precisão de **75%** e Recall de **94%** (F1-Score: 0.84). O classificador identifica quase todos os clientes que não pretendem sair.\n',
        '   - **Classe 1 (Clientes que Cancelaram - Churn)**: Precisão de **55%** e Recall de apenas **18%** (F1-Score: 0.27). O modelo sofre com uma **baixa sensibilidade**, detectando corretamente apenas 207 dos 1.176 cancelamentos reais e gerando **969 Falsos Negativos** no limiar padrão (0.50).\n',
        '\n',
        '3. **Impacto de Negócio para a MegaTelCo**:\n',
        '   - Em telecomunicações, o custo de perder um cliente (*Falso Negativo*) costuma ser significativamente superior ao custo de ofertar um benefício ou campanha preventiva a quem não iria cancelar (*Falso Positivo*).\n',
        '   - O limiar padrão de 50% não atende plenamente ao objetivo do negócio, sendo necessária uma exploração da probabilidade predita e calibração de limiares.\n',
        '\n',
        '4. **Aprofundamento a seguir**:\n',
        '   - Nas seções abaixo, realizamos a **exploração aprofundada da performance** (Matriz de confusão visual, Curva ROC e AUC, calibração do limiar de corte para aumentar o Recall e análise de Odds Ratios) e efetuamos a **serialização com Pickle** para permitir que o modelo seja reutilizado em novos dados.'
    ]
}

novas_celulas = [
    # Seção 8
    {
        'cell_type': 'markdown',
        'id': 'header_sec_8',
        'metadata': {'id': 'header_sec_8'},
        'source': [
            '-----\n',
            '## **(8) Exploração Avançada da Performance do Modelo**\n',
            '-----\n',
            '\n',
            'Nesta etapa, iremos aprofundar a avaliação do modelo através de:\n',
            '1. Visualização gráfica detalhada da **Matriz de Confusão** (contagens absolutas e percentuais normalizados);\n',
            '2. Análise da distribuição de probabilidades e **Curva ROC com métrica AUC**;\n',
            '3. **Calibração do Limiar de Decisão (Threshold Tuning)** para equilibrar Precisão e Recall conforme a estratégia da MegaTelCo;\n',
            '4. Avaliação do impacto de cada variável explicativa através dos **Coeficientes e Razão de Chances (Odds Ratio)**.'
        ]
    },
    {
        'cell_type': 'markdown',
        'id': 'sec_8_1_md',
        'metadata': {'id': 'sec_8_1_md'},
        'source': [
            '### *8.1 Visualização da Matriz de Confusão (Absoluta e Normalizada)*\n',
            '\n',
            '* **Verdadeiros Negativos (VN)**: Clientes que permaneceram e foram corretamente previstos como 0.\n',
            '* **Falsos Positivos (FP)**: Clientes que permaneceram, mas foram classificados como risco de churn (alarme falso).\n',
            '* **Falsos Negativos (FN)**: Clientes que cancelaram e o modelo não conseguiu detectar (custo crítico).\n',
            '* **Verdadeiros Positivos (VP)**: Clientes que cancelaram e foram detectados a tempo pelo modelo.'
        ]
    },
    {
        'cell_type': 'code',
        'execution_count': None,
        'id': 'sec_8_1_code',
        'metadata': {'id': 'sec_8_1_code'},
        'outputs': [],
        'source': [
            'from sklearn.metrics import ConfusionMatrixDisplay\n',
            '\n',
            'fig, ax = plt.subplots(1, 2, figsize=(14, 5))\n',
            '\n',
            '# 1. Matriz de Confusão com contagens absolutas\n',
            'ConfusionMatrixDisplay.from_predictions(\n',
            '    true_labels,\n',
            '    pred_labels,\n',
            '    display_labels=[\'Ficou (0)\', \'Saiu (1)\'],\n',
            '    cmap=\'Blues\',\n',
            '    ax=ax[0]\n',
            ')\n',
            'ax[0].set_title(\'Matriz de Confusão - Contagens Absolutas\', fontsize=12, fontweight=\'bold\')\n',
            'ax[0].grid(False)\n',
            '\n',
            '# 2. Matriz de Confusão normalizada por linha (Recall de cada classe)\n',
            'ConfusionMatrixDisplay.from_predictions(\n',
            '    true_labels,\n',
            '    pred_labels,\n',
            '    display_labels=[\'Ficou (0)\', \'Saiu (1)\'],\n',
            '    cmap=\'Blues\',\n',
            '    normalize=\'true\',\n',
            '    values_format=\'.2%\',\n',
            '    ax=ax[1]\n',
            ')\n',
            'ax[1].set_title(\'Matriz de Confusão - Normalizada por Classe Real (%)\', fontsize=12, fontweight=\'bold\')\n',
            'ax[1].grid(False)\n',
            '\n',
            'plt.tight_layout()\n',
            'plt.show()'
        ]
    },
    {
        'cell_type': 'markdown',
        'id': 'sec_8_2_md',
        'metadata': {'id': 'sec_8_2_md'},
        'source': [
            '### *8.2 Probabilidades Estimadas e Curva ROC (com AUC)*\n',
            '\n',
            'A regressão logística calcula uma probabilidade $P(Y=1|X) \\in [0, 1]$.\n',
            '\n',
            'A **Curva ROC (Receiver Operating Characteristic)** compara o compromisso entre:\n',
            '* **Sensibilidade (True Positive Rate - TPR)**: fração de churners reais que o modelo detecta.\n',
            '* **1 - Especificidade (False Positive Rate - FPR)**: fração de clientes fiéis classificados erroneamente como churn.\n',
            '\n',
            'A métrica **AUC (Área sob a Curva)** quantifica a capacidade discriminatória geral do classificador (variando de 0.5 a 1.0).'
        ]
    },
    {
        'cell_type': 'code',
        'execution_count': None,
        'id': 'sec_8_2_code',
        'metadata': {'id': 'sec_8_2_code'},
        'outputs': [],
        'source': [
            'from sklearn.metrics import roc_curve, roc_auc_score, auc\n',
            '\n',
            '# Calculando as probabilidades estimadas para a classe positiva (1 = Saiu / Churn)\n',
            'pred_probs = classificador.predict_proba(Z)[:, 1]\n',
            '\n',
            '# Cálculo de FPR, TPR e limiares de corte\n',
            'fpr, tpr, limiares_roc = roc_curve(true_labels, pred_probs)\n',
            'roc_auc = roc_auc_score(true_labels, pred_probs)\n',
            '\n',
            'plt.figure(figsize=(8, 6))\n',
            'plt.plot(fpr, tpr, color=\'#1f77b4\', lw=2.5, label=f\'Regressão Logística (AUC = {roc_auc:.4f})\')\n',
            'plt.plot([0, 1], [0, 1], color=\'gray\', lw=1.5, linestyle=\'--\', label=\'Classificador Aleatório (AUC = 0.50)\')\n',
            '\n',
            'plt.xlim([0.0, 1.0])\n',
            'plt.ylim([0.0, 1.05])\n',
            'plt.xlabel(\'Taxa de Falsos Positivos (1 - Especificidade)\', fontsize=11)\n',
            'plt.ylabel(\'Taxa de Verdadeiros Positivos (Sensibilidade / Recall)\', fontsize=11)\n',
            'plt.title(\'Curva ROC - Detecção de Churn\', fontsize=13, fontweight=\'bold\')\n',
            'plt.legend(loc=\'lower right\', fontsize=11)\n',
            'plt.grid(True, alpha=0.3)\n',
            'plt.show()\n',
            '\n',
            'print(f\"-> Área sob a Curva ROC (AUC-ROC): {roc_auc:.4f}\")'
        ]
    },
    {
        'cell_type': 'markdown',
        'id': 'sec_8_3_md',
        'metadata': {'id': 'sec_8_3_md'},
        'source': [
            '### *8.3 Ajuste do Limiar de Decisão (Decision Threshold Tuning)*\n',
            '\n',
            'O método `.predict()` utiliza por padrão o corte de $0.50$ (50% de probabilidade):\n',
            '$$\\hat{y} = 1 \\iff P(Y=1|X) \\ge 0.50$$\n',
            '\n',
            'Ao diminuir o limiar (por exemplo, para $0.35$ ou $0.30$), o modelo passa a sinalizar mais clientes em risco de cancelamento, elevando consideravelmente o **Recall** (captura de churners).'
        ]
    },
    {
        'cell_type': 'code',
        'execution_count': None,
        'id': 'sec_8_3_code_table',
        'metadata': {'id': 'sec_8_3_code_table'},
        'outputs': [],
        'source': [
            '# Avaliando o desempenho do modelo sob múltiplos limiares de probabilidade\n',
            'limiares_teste = [0.20, 0.25, 0.30, 0.35, 0.40, 0.45, 0.50, 0.55, 0.60]\n',
            'tabela_limiares = []\n',
            '\n',
            'for t in limiares_teste:\n',
            '    pred_t = (pred_probs >= t).astype(int)\n',
            '    tn, fp, fn, tp = confusion_matrix(true_labels, pred_t).ravel()\n',
            '    \n',
            '    prec = tp / (tp + fp) if (tp + fp) > 0 else 0\n',
            '    rec = tp / (tp + fn) if (tp + fn) > 0 else 0\n',
            '    f1 = 2 * (prec * rec) / (prec + rec) if (prec + rec) > 0 else 0\n',
            '    acc = (tp + tn) / len(true_labels)\n',
            '    \n',
            '    tabela_limiares.append({\n',
            '        \'Limiar\': t,\n',
            '        \'Acurácia\': f\'{acc:.2%}\',\n',
            '        \'Precisão (Churn)\': f\'{prec:.2%}\',\n',
            '        \'Recall (Churn)\': f\'{rec:.2%}\',\n',
            '        \'F1-Score\': f\'{f1:.4f}\',\n',
            '        \'Churns Detectados (VP)\': tp,\n',
            '        \'Churns Perdidos (FN)\': fn,\n',
            '        \'Alarmes Falsos (FP)\': fp\n',
            '    })\n',
            '\n',
            'df_tabela_limiares = pd.DataFrame(tabela_limiares)\n',
            'df_tabela_limiares'
        ]
    },
    {
        'cell_type': 'code',
        'execution_count': None,
        'id': 'sec_8_3_code_plot',
        'metadata': {'id': 'sec_8_3_code_plot'},
        'outputs': [],
        'source': [
            '# Visualização gráfica do Trade-off: Precisão vs. Recall vs. F1-Score\n',
            'pontos_t = np.linspace(0.1, 0.85, 76)\n',
            'curva_rec, curva_prec, curva_f1 = [], [], []\n',
            '\n',
            'for t in pontos_t:\n',
            '    p = (pred_probs >= t).astype(int)\n',
            '    tn, fp, fn, tp = confusion_matrix(true_labels, p).ravel()\n',
            '    prec = tp / (tp + fp) if (tp + fp) > 0 else 0\n',
            '    rec = tp / (tp + fn) if (tp + fn) > 0 else 0\n',
            '    f1 = 2 * (prec * rec) / (prec + rec) if (prec + rec) > 0 else 0\n',
            '    curva_rec.append(rec)\n',
            '    curva_prec.append(prec)\n',
            '    curva_f1.append(f1)\n',
            '\n',
            'plt.figure(figsize=(10, 5))\n',
            'plt.plot(pontos_t, curva_prec, label=\'Precisão (Churn)\', color=\'#2ca02c\', lw=2.5)\n',
            'plt.plot(pontos_t, curva_rec, label=\'Recall / Sensibilidade (Churn)\', color=\'#d62728\', lw=2.5)\n',
            'plt.plot(pontos_t, curva_f1, label=\'F1-Score\', color=\'#1f77b4\', lw=2, linestyle=\'--\')\n',
            'plt.axvline(x=0.50, color=\'gray\', linestyle=\':\', label=\'Limiar Padrão (0.50)\')\n',
            'plt.axvline(x=0.35, color=\'orange\', linestyle=\'--\', label=\'Limiar Operacional Sugerido (0.35)\')\n',
            '\n',
            'plt.title(\'Trade-off entre Precisão, Recall e F1-Score por Limiar\', fontsize=12, fontweight=\'bold\')\n',
            'plt.xlabel(\'Limiar de Probabilidade (Threshold)\', fontsize=11)\n',
            'plt.ylabel(\'Pontuação da Métrica\', fontsize=11)\n',
            'plt.legend(fontsize=10)\n',
            'plt.grid(True, alpha=0.3)\n',
            'plt.show()'
        ]
    },
    {
        'cell_type': 'markdown',
        'id': 'sec_8_4_md',
        'metadata': {'id': 'sec_8_4_md'},
        'source': [
            '### *8.4 Interpretação dos Coeficientes e Razão de Chances (Odds Ratio)*\n',
            '\n',
            'Na Regressão Logística, a magnitude e o sinal de cada coeficiente $\\beta$ explicam a influência da variável na chance de churn:\n',
            '$$\\text{Odds Ratio} = e^{\\beta}$$\n',
            '\n',
            '* $\\text{Odds Ratio} > 1$ ($\\beta > 0$): O aumento da variável **aumenta** a probabilidade de churn (ex: chamadas longas `over_15mins_calls_per_month`, minutos restantes `leftover`).\n',
            '* $\\text{Odds Ratio} < 1$ ($\\beta < 0$): O aumento da variável **reduz** a probabilidade de churn (ex: patrimônio `house`).'
        ]
    },
    {
        'cell_type': 'code',
        'execution_count': None,
        'id': 'sec_8_4_code',
        'metadata': {'id': 'sec_8_4_code'},
        'outputs': [],
        'source': [
            '# Construção da tabela de coeficientes e Odds Ratio\n',
            'df_coef = pd.DataFrame({\n',
            '    \'Feature\': X.columns,\n',
            '    \'Coeficiente (Beta)\': classificador.coef_[0],\n',
            '    \'Odds Ratio (e^Beta)\': np.exp(classificador.coef_[0])\n',
            '}).sort_values(by=\'Coeficiente (Beta)\', ascending=True)\n',
            '\n',
            '# Plot dos coeficientes\n',
            'plt.figure(figsize=(10, 5))\n',
            'cores = [\'#d9534f\' if b < 0 else \'#5cb85c\' for b in df_coef[\'Coeficiente (Beta)\']]\n',
            'plt.barh(df_coef[\'Feature\'], df_coef[\'Coeficiente (Beta)\'], color=cores)\n',
            'plt.axvline(0, color=\'black\', lw=1, linestyle=\'--\')\n',
            'plt.title(\'Impacto das Features no Log-Odds de Churn (Coeficientes Beta)\', fontsize=12, fontweight=\'bold\')\n',
            'plt.xlabel(\'Coeficiente Beta\', fontsize=11)\n',
            'plt.grid(True, alpha=0.3, axis=\'x\')\n',
            'plt.tight_layout()\n',
            'plt.show()\n',
            '\n',
            'df_coef.sort_values(by=\'Coeficiente (Beta)\', ascending=False).reset_index(drop=True)'
        ]
    },
    # Seção 9
    {
        'cell_type': 'markdown',
        'id': 'header_sec_9',
        'metadata': {'id': 'header_sec_9'},
        'source': [
            '-----\n',
            '## **(9) Salvando e Reutilizando o Modelo com Pickle**\n',
            '-----\n',
            '\n',
            'O módulo `pickle` permite persistir o estimador treinado em disco para reutilização futura sem a necessidade de reprocessar os dados e retreinar o algoritmo.\n',
            '\n',
            '> **Boa prática de Engenharia de Machine Learning**: Em vez de serializar apenas o objeto estimador, encapsulamos também **metadados essenciais para inferência** (ordem das features esperadas, limiar recomendado de negócio e data de geração).'
        ]
    },
    {
        'cell_type': 'code',
        'execution_count': None,
        'id': 'sec_9_1_code',
        'metadata': {'id': 'sec_9_1_code'},
        'outputs': [],
        'source': [
            'import pickle\n',
            'from datetime import datetime\n',
            '\n',
            '# Criando o pacote completo para exportação\n',
            'artefato_modelo = {\n',
            '    \'modelo\': classificador,\n',
            '    \'features\': list(X.columns),\n',
            '    \'target\': \'leave\',\n',
            '    \'algoritmo\': \'LogisticRegression(solver=liblinear, C=1)\',\n',
            '    \'limiar_recomendado\': 0.35,\n',
            '    \'data_treinamento\': datetime.now().strftime(\'%Y-%m-%d %H:%M:%S\')\n',
            '}\n',
            '\n',
            'nome_arquivo_pickle = \'modelo_regressao_logistica_churn.pkl\'\n',
            '\n',
            '# Salvando em disco em formato binário (wb = write binary)\n',
            'with open(nome_arquivo_pickle, \'wb\') as arquivo:\n',
            '    pickle.dump(artefato_modelo, arquivo)\n',
            '\n',
            'print(f\"-> Modelo serializado com sucesso no arquivo: \'{nome_arquivo_pickle}\'\")\n',
            'print(f\"-> Features esperadas salvas no artefato: {artefato_modelo[\'features\']}\")\n',
            'print(f\"-> Limiar operacional recomendado: {artefato_modelo[\'limiar_recomendado\']}\")'
        ]
    },
    {
        'cell_type': 'markdown',
        'id': 'sec_9_2_md',
        'metadata': {'id': 'sec_9_2_md'},
        'source': [
            '### *9.2 Carregando o Modelo e Realizando Inferência em Novos Dados*\n',
            '\n',
            'Simulando o consumo do modelo em ambiente de produção: carregamos o arquivo `.pkl` e aplicamos as predições para novos clientes, gerando probabilidades e sugestões de ações para a equipe de retenção.'
        ]
    },
    {
        'cell_type': 'code',
        'execution_count': None,
        'id': 'sec_9_2_code',
        'metadata': {'id': 'sec_9_2_code'},
        'outputs': [],
        'source': [
            '# 1. Carregando o arquivo serializado (rb = read binary)\n',
            'with open(\'modelo_regressao_logistica_churn.pkl\', \'rb\') as arquivo:\n',
            '    pacote_carregado = pickle.load(arquivo)\n',
            '\n',
            'modelo_carregado = pacote_carregado[\'modelo\']\n',
            'features_modelo = pacote_carregado[\'features\']\n',
            'limiar_negocio = pacote_carregado[\'limiar_recomendado\']\n',
            '\n',
            'print(\"=== Artefato do Modelo Carregado ===\")\n',
            'print(f\"Algoritmo: {pacote_carregado[\'algoritmo\']}\")\n',
            'print(f\"Data de Geração: {pacote_carregado[\'data_treinamento\']}\")\n',
            'print(f\"Features Necessárias: {features_modelo}\")\n',
            'print(f\"Limiar de Negócio: {limiar_negocio}\\n\")\n',
            '\n',
            '# 2. Simulando novos clientes para escoragem (amostra de teste)\n',
            'novos_clientes = Z.head(8).copy()\n',
            '\n',
            '# 3. Estimando probabilidades de churn\n',
            'probabilidades_churn = modelo_carregado.predict_proba(novos_clientes[features_modelo])[:, 1]\n',
            '\n',
            '# 4. Classificação com limiar padrão (0.50) e limiar operacional (0.35)\n',
            'resultado_inferencia = novos_clientes.copy()\n',
            'resultado_inferencia[\'Prob_Churn (%)\'] = (probabilidades_churn * 100).round(2)\n',
            'resultado_inferencia[\'Pred_Padrao_50\'] = (probabilidades_churn >= 0.50).astype(int)\n',
            'resultado_inferencia[\'Pred_Negocio_35\'] = (probabilidades_churn >= limiar_negocio).astype(int)\n',
            'resultado_inferencia[\'Acao_Sugerida\'] = resultado_inferencia[\'Pred_Negocio_35\'].map({\n',
            '    0: \'Monitoramento Normal\',\n',
            '    1: \'Acionar Ação de Retenção!\'\n',
            '})\n',
            '\n',
            'resultado_inferencia[[\'income\', \'house\', \'handset_price\', \'leftover\', \'Prob_Churn (%)\', \'Pred_Padrao_50\', \'Pred_Negocio_35\', \'Acao_Sugerida\']]'
        ]
    },
    # Célula final
    {
        'cell_type': 'markdown',
        'id': 'fim_notebook',
        'metadata': {'id': 'fim_notebook'},
        'source': [
            '# ***FIM***'
        ]
    }
]

nb['cells'] = nb['cells'][:37] + [cell_conclusao] + novas_celulas

with open(path, 'w', encoding='utf-8') as f:
    json.dump(nb, f, indent=1, ensure_ascii=False)

print('Arquivo gravado com sucesso! Total de células:', len(nb['cells']))
