COMPARAÇÃO DE BANCOS VETORIAIS APLICADOS A SOLUÇÃO DE GERAÇÃO AUMENTADA DE RECUPERAÇÃO
# 1 INTRODUÇÃO

A última década testemunhou um avanço sem precedentes no campo do Processamento de Linguagem Natural (PLN), impulsionado sobretudo pelo desenvolvimento dos Grandes Modelos de Linguagem conhecidos pela sigla em inglês Large Language Models (LLMs). Com o surgimento dos LLMs, consolidou-se uma mudança de paradigma significativa no PLN, com modelos como GPT, LLaMA e Claude alcançando desempenho notável em tarefas de compreensão e geração de linguagem. Esses sistemas, treinados sobre vastos dados textuais, demonstraram capacidade de responder a perguntas, resumir documentos, gerar código e realizar raciocínio em linguagem natural com elevado grau de fluência.

Não obstante seus avanços, os LLMs apresentam limitações estruturais que impõem barreiras à sua aplicação em cenários que exigem acurácia factual. A mais discutida delas é o fenômeno da alucinação: a geração de conteúdo por um LLM que é fluente e sintaticamente correto, porém factualmente impreciso ou sem respaldo em evidências externas (JI et al., 2023), fenômeno que compromete a confiabilidade dos modelos, especialmente em domínios que requerem precisão factual. Além disso, esses modelos possuem uma janela de contexto finita, ou seja, um limite na quantidade de texto que podem processar de uma só vez, o que restringe sua capacidade de raciocinar sobre documentos extensos. A terceira limitação diz respeito ao conhecimento estático: os LLMs são treinados até uma determinada data de corte, tornando-se incapazes de incorporar informações geradas após esse ponto sem um processo de retreinamento, oneroso em tempo e custo computacional.

Diante desse cenário, emergiu um paradigma arquitetural concebido para superar tais limitações: a Geração Aumentada de Recuperação, amplamente referida pela sigla RAG (Retrieval-Augmented Generation). Hallucinations são particularmente críticas por comprometer a confiabilidade dos resultados, e o RAG foi proposto como novo arcabouço de IA que busca integrar conhecimento adicional às respostas dos modelos, ancorando-as em fontes verificáveis. O termo e o framework foram formalizados por Patrick Lewis e colaboradores em seu artigo de 2020 , que introduziu o RAG como abordagem que combina memória paramétrica com memória não paramétrica, composta por bases de dados externas consultadas em tempo de inferência. (LEWIS et al., 2020)

No fluxo típico de uma aplicação RAG, o sistema converte a consulta do usuário em um vetor numérico por meio de um modelo de embeddings, realiza uma busca por similaridade semântica em um repositório indexado de documentos, recupera os trechos (chunks) mais relevantes e os fornece como contexto adicional ao LLM no momento da geração da resposta. Esse mecanismo permite que o modelo acesse informações externas atualizadas sem necessidade de retreinamento, reduzindo significativamente a ocorrência de alucinações em contextos de domínio específico.

O componente central desse paradigma é a busca vetorial por similaridade semântica, executada sobre um repositório especializado denominado banco de vetores (vector database). O mercado de bancos de vetores cresceu de 1,73 bilhão de dólares em 2024 (GRAND VIEW RESEARCH, 2024) para uma projeção de 10,6 bilhões de dólares até 2032, refletindo a adoção acelerada de RAG e busca semântica em aplicações de produção. Entre as soluções disponíveis, destacam-se alternativas com características arquiteturais bastante distintas: FAISS, desenvolvido pela Meta Research, oferece desempenho bruto e flexibilidade algorítmica voltados a cenários de alta performance; Milvus foi projetado para implantações em escala empresarial com arquitetura distribuída; e Chroma prioriza a experiência do desenvolvedor e a facilidade de integração, sendo especialmente adequado para prototipagem rápida.

Apesar da proliferação dessas ferramentas, persiste uma lacuna relevante: existe alta produção prática sobre bancos vetoriais, porém pouca comparação formal e sistemática entre eles no contexto de pipelines RAG. As avaliações disponíveis tendem a ser fragmentadas, produzidas por fornecedores com potencial viés, ou realizadas em escalas e condições que inviabilizam a generalização. Esse cenário impõe um desafio concreto a engenheiros e equipes que precisam tomar decisões arquiteturais fundamentadas ao construir sistemas de IA baseados em recuperação semântica.

Nesse contexto, o presente trabalho propõe uma comparação sistemática e reprodutível entre três soluções de recuperação vetorial de código aberto, avaliadas como o componente de busca de pipelines RAG padronizados. A questão norteadora que orienta a investigação pode ser enunciada da seguinte forma: dado que o desempenho de uma solução RAG depende da qualidade da recuperação vetorial, qual solução apresenta o melhor equilíbrio entre desempenho, escalabilidade e preservação da vizinhança vetorial de referência em diferentes volumes de dados?

---

## 1.1 APRESENTAÇÃO DO PROBLEMA EM ESTUDO

O paradigma de Geração Aumentada de Recuperação fundamenta-se em uma premissa estrutural: a qualidade da resposta gerada pelo modelo de linguagem é diretamente proporcional à qualidade do contexto recuperado pelo sistema de busca vetorial. Nessa arquitetura, o modelo de linguagem não opera sobre conhecimento próprio, mas sobre um conjunto de trechos de documentos recuperados em tempo de inferência e fornecidos como contexto adicional.

Consequentemente, eventuais falhas no processo de recuperação propagam-se diretamente para a camada de geração, comprometendo a precisão factual da resposta final independentemente da capacidade do modelo utilizado. Essa dependência assimétrica confere ao componente de busca vetorial um papel determinante na arquitetura RAG, tornando a escolha da solução de armazenamento e recuperação de vetores uma decisão de impacto sistêmico sobre o desempenho, a escalabilidade e a qualidade das respostas produzidas pela aplicação.

O processo de busca vetorial em sistemas RAG opera sobre o princípio da similaridade semântica no espaço vetorial. Quando um usuário submete uma consulta, o sistema a transforma em um vetor numérico de alta dimensionalidade por meio de um modelo de embeddings. Esse vetor representa a semântica da consulta em um espaço matemático contínuo, no qual expressões semanticamente próximas ocupam regiões vizinhas, independentemente de compartilharem os mesmos termos lexicais. O banco de vetores, por sua vez, mantém indexados os embeddings de todos os documentos previamente processados e é responsável por identificar, de forma eficiente, os vetores mais próximos ao da consulta. Os trechos de documentos correspondentes a esses vetores são então recuperados e fornecidos ao modelo de linguagem como contexto para a geração da resposta. A eficácia dessa etapa de recuperação é, portanto, condicionada tanto pela qualidade dos embeddings quanto pela eficiência e precisão do mecanismo de indexação e busca implementado pelo banco de vetores.

O mercado atual oferece múltiplas soluções para esse componente, cada uma sustentada por arquiteturas internas e estratégias de indexação substancialmente distintas. O FAISS, desenvolvido pela Meta AI Research, é uma biblioteca otimizada para busca eficiente de similaridade em grandes coleções de vetores densos. Seu funcionamento baseia-se em estruturas de índice configuráveis, entre as quais se destacam o IVF (Inverted File Index), que particiona o espaço vetorial em células de Voronoi para restringir a busca a subconjuntos relevantes, o PQ (Product Quantization), que comprime vetores em representações compactas para reduzir o consumo de memória com perda controlada de precisão, e o HNSW (Hierarchical Navigable Small World), que organiza os vetores em uma estrutura de grafo hierárquico para viabilizar buscas aproximadas com latência reduzida e alto recall. Embora o FAISS ofereça desempenho bruto e flexibilidade algorítmica excepcionais, opera predominantemente em memória e não provê, em sua forma nativa, recursos de persistência, escalabilidade distribuída ou gerenciamento de metadados, características que limitam sua aplicabilidade direta em ambientes de produção sem camadas de abstração adicionais.

O Milvus, mantido pela LF AI and Data Foundation, foi concebido para implantações em escala empresarial e suporta nativamente os mesmos algoritmos de indexação disponíveis no FAISS, acrescentando suporte a persistência em disco, particionamento horizontal dos dados e gerenciamento de coleções e metadados. Sua arquitetura distribuída permite escalar horizontalmente à medida que o volume de dados cresce, tornando-o adequado para cenários que demandam alta disponibilidade e processamento de coleções com centenas de milhões de vetores.

Em contrapartida, essa complexidade arquitetural implica maior custo operacional de configuração e manutenção. O Chroma, por sua vez, foi projetado com foco na experiência do desenvolvedor, oferecendo uma interface de programação simplificada e integração direta com frameworks como LangChain e LlamaIndex. Sua adoção é especialmente frequente em fases de prototipagem e desenvolvimento, dada a facilidade de inicialização e a abstração dos detalhes de configuração do índice. Cada uma dessas soluções implica, portanto, trade-offs específicos e distintos entre latência de busca, recall semântico, custo de indexação, consumo de memória e facilidade de operação, trade-offs cujos impactos se manifestam de forma diferenciada conforme o volume de dados e os requisitos do pipeline.

Apesar da relevância dessa escolha arquitetural, constata-se uma lacuna significativa na literatura técnica e acadêmica: não existe, até o presente, uma comparação sistemática, reprodutível e metodologicamente isenta entre os principais bancos de vetores de código aberto no contexto de pipelines RAG completos. As avaliações disponíveis apresentam limitações que comprometem sua utilidade para decisões arquiteturais fundamentadas em evidência. Benchmarks publicados pelas equipes de desenvolvimento dos próprios bancos, carecem de neutralidade metodológica por definição, uma vez que o ambiente experimental pode ser configurado de forma a favorecer a solução avaliada. Iniciativas independentes de referência, como o ANN-Benchmarks, avaliam exclusivamente a camada de busca vetorial bruta em termos de velocidade e recall de vizinhos mais próximos, desconsiderando as dimensões de qualidade semântica, integração com modelos de linguagem e comportamento em pipelines RAG de ponta a ponta. As avaliações publicadas em artigos técnicos e postagens da comunidade frequentemente divergem em condições experimentais, incluindo hardware utilizado, volume de dados testado, modelo de embedding adotado e métricas de avaliação empregadas, o que inviabiliza comparações cruzadas confiáveis entre esses trabalhos.

Esse cenário impõe um desafio concreto a engenheiros, arquitetos de software e equipes de ciência de dados que precisam selecionar a solução de armazenamento vetorial mais adequada para suas aplicações. A ausência de evidência experimental comparativa os obriga a basear suas decisões em documentação de fornecedores, benchmarks isolados ou recomendações da comunidade técnica, fontes com validade externa limitada e frequentemente associadas a contextos experimentais não generalizáveis. A escolha inadequada do banco de vetores pode implicar degradação de desempenho em produção, aumento de custos de infraestrutura, limitações de escalabilidade e, fundamentalmente, redução da qualidade das recuperações semânticas e das respostas geradas pelo sistema de inteligência artificial.

**Contribuição específica deste trabalho:** o presente estudo diferencia-se dos trabalhos existentes por oferecer uma comparação sistemática, metodologicamente controlada e reprodutível entre três soluções de recuperação vetorial de código aberto (FAISS, Milvus e Chroma), utilizadas como componente de busca de pipelines RAG padronizados. Enquanto benchmarks de fornecedores tendem ao viés favorável, e o ANN-Benchmarks não considera o custo de ingestão e persistência dessas soluções, este trabalho avalia métricas operacionais (latência, throughput e consumo de recursos) e a preservação da vizinhança vetorial de referência por meio da taxa de acerto no top-k, em múltiplos cenários de escala (10k, 100k e 500k vetores), com intervalo de confiança de 95% e testes de significância. O código-fonte, os dados e os resultados são disponibilizados publicamente, permitindo replicação e extensão por pesquisadores e profissionais.

---

## 1.2 OBJETIVOS

### 1.2.1 Objetivo Geral

Realizar uma comparação sistemática e reprodutível entre os bancos de vetores de código aberto FAISS, Milvus e Chroma, avaliando seu desempenho, escalabilidade e qualidade de recuperação semântica quando integrados a pipelines de Geração Aumentada de Recuperação, com o propósito de fornecer evidência experimental que oriente decisões arquiteturais em sistemas de inteligência artificial baseados em recuperação de informação.

### 1.2.2 Objetivos Específicos

- **a)** Caracterizar o estado da arte em bancos de vetores de código aberto, descrevendo as arquiteturas internas, os algoritmos de indexação e os trade-offs entre precisão, velocidade e consumo de recursos de cada solução investigada, com ênfase nas estruturas HNSW, IVF e Product Quantization.
- **b)** Implementar um pipeline padronizado de recuperação vetorial, análogo à etapa de busca de uma aplicação RAG, utilizando Python e as bibliotecas nativas de cada solução, de modo a manter constantes as condições de comparação dentro de cada cenário experimental.
- **c)** Construir três cenários experimentais com volumes de dados distintos, aproximadamente 10 mil, 100 mil e 500 mil vetores, de forma a observar como o comportamento de cada banco de vetores se modifica em função do crescimento do conjunto de dados indexado.
- **d)** Mensurar, para cada solução e em cada cenário de volume, métricas operacionais e de recuperação, incluindo latência de busca, throughput de consultas, tempo de indexação e ingestão, consumo de memória RAM, tamanho em disco e taxa de acerto no top-k, permitindo uma análise multidimensional do desempenho.
- **e)** Avaliar a recuperação vetorial de cada solução por meio da taxa de acerto no top-k, operacionalizada nos arquivos como Recall@k (k=1, 5, 10), verificando em que medida os resultados preservam a vizinhança obtida pela busca exata conforme o volume de dados cresce.
- **f)** Identificar, com base nos resultados experimentais obtidos, em quais condições de volume e requisito operacional cada banco de vetores apresenta vantagem relativa, de modo a subsidiar recomendações práticas para engenheiros e arquitetos de software que precisam selecionar a solução mais adequada ao seu contexto de aplicação.
- **g)** Disponibilizar publicamente o código-fonte dos experimentos, os conjuntos de dados utilizados e os scripts de avaliação, garantindo que os resultados sejam reprodutíveis por pesquisadores e profissionais interessados em replicar ou estender a comparação realizada.
---

## 1.3 JUSTIFICATIVA

A presente pesquisa justifica-se em três dimensões complementares: científica, tecnológica e econômica.

Do ponto de vista científico, a literatura sobre Geração Aumentada de Recuperação tem avançado de forma expressiva nos aspectos relativos à arquitetura do pipeline, à qualidade dos modelos de linguagem e às estratégias de chunking e reranking de documentos. Contudo, persiste uma lacuna metodológica específica: a ausência de comparações sistemáticas, controladas e reprodutíveis entre bancos de vetores de código aberto no contexto de pipelines RAG padronizados. As iniciativas existentes de benchmarking vetorial, como o ANN-Benchmarks (AUMULLER et al., 2020), restringem-se à camada de busca de vizinhos mais próximos, avaliando exclusivamente métricas de velocidade e recall bruto de vizinhança, sem considerar o custo operacional de ingestão, o comportamento sob diferentes volumes de dados, ou a variação sistemática de qualidade de recuperação semântica (Recall@k) conforme a escala cresce. O presente trabalho preenche essa lacuna ao conduzir uma comparação ponta a ponta entre FAISS, Milvus e Chroma, com metodologia transparente, cenários de escala progressivos (10k, 100k, 500k vetores) e métricas que abrangem tanto desempenho operacional (latência, throughput, recursos) quanto qualidade de recuperação semântica via Recall@k, contribuindo com evidência experimental reprodutível para a comunidade de pesquisa em recuperação de informação e inteligência artificial.

Do ponto de vista tecnológico, a escolha do banco de vetores é uma decisão arquitetural de impacto sistêmico em aplicações de inteligência artificial baseadas em recuperação semântica. Engenheiros e arquitetos de software frequentemente precisam tomar essa decisão em fases iniciais do projeto, quando os requisitos de volume e desempenho ainda não estão completamente definidos. A ausência de referências comparativas independentes os obriga a recorrer à documentação dos próprios fornecedores, a postagens da comunidade técnica sem controle experimental, ou a intuições derivadas de casos de uso não generalizáveis. Este trabalho produz uma análise comparativa que cobre três soluções representativas de posicionamentos arquiteturais distintos (FAISS, voltado para desempenho bruto; Milvus, orientado à escala empresarial; e Chroma, otimizado para a experiência do desenvolvedor) e fornece orientações práticas sobre em quais condições de volume e requisito cada solução apresenta vantagem relativa. Essa contribuição tem valor direto para equipes que desenvolvem assistentes conversacionais, sistemas de busca semântica corporativa, plataformas de análise documental e demais aplicações que dependem da qualidade da recuperação vetorial.

Do ponto de vista econômico, o mercado global de bancos de vetores foi avaliado em 1,73 bilhão de dólares em 2024 e projeta-se que alcance 10,6 bilhões de dólares até 2032 (GRAND VIEW RESEARCH, 2024), crescimento impulsionado pela adoção acelerada de sistemas RAG em ambientes corporativos. Nesse contexto, a seleção inadequada da solução de armazenamento vetorial pode implicar custos substanciais: degradação de desempenho em produção que demanda refatoração arquitetural, subdimensionamento de infraestrutura que exige migração de dados custosa, ou superdimensionamento de recursos computacionais que onera desnecessariamente o orçamento operacional. A evidência experimental produzida neste trabalho contribui para a redução desse risco decisório, subsidiando escolhas mais fundamentadas e economicamente eficientes no ciclo de vida de desenvolvimento de sistemas de inteligência artificial.

---

## 1.4 METODOLOGIA

A metodologia da pesquisa adota uma abordagem experimental de natureza quantitativa, estruturada em torno da construção e execução de um benchmark controlado. O estudo parte de uma revisão da literatura para fundamentar a caracterização teórica dos componentes avaliados, seguida pela implementação de um pipeline experimental padronizado que compara as soluções e suas configurações de índice, mantendo constantes as demais condições dentro de cada cenário.

O pipeline é implementado em Python, utilizando as bibliotecas nativas de cada solução, o modelo de embeddings sentence-transformers/all-MiniLM-L6-v2 para geração de representações vetoriais de 384 dimensões, e a biblioteca FAISS como oráculo de busca exata para o cômputo do ground truth. Os experimentos são organizados em três cenários de escala progressiva: aproximadamente 10 mil, 100 mil e 500 mil vetores, permitindo observar como o comportamento das configurações avaliadas se modifica em função do crescimento do volume indexado. Para cada cenário e solução, são coletadas métricas operacionais: tempo de indexação, latência de busca nos percentis p50 e p99, throughput em consultas por segundo, consumo de memória RAM e tamanho do índice em disco, além da taxa de acerto no top-k para k igual a 1, 5 e 10. Os resultados são consolidados em estruturas tabulares e visualizados por meio de gráficos comparativos. O detalhamento completo da metodologia experimental, incluindo configurações específicas, parâmetros dos índices, procedimentos de medição e critérios de reprodutibilidade, é apresentado no Capítulo 3.

---

## 1.5 CAPÍTULOS DO TRABALHO DE GRADUAÇÃO

O presente trabalho está organizado em quatro capítulos, cujos conteúdos são brevemente descritos a seguir.

O presente capítulo, **Capítulo 1 — Introdução**, contextualiza o cenário de rápida evolução dos Grandes Modelos de Linguagem e apresenta as limitações estruturais que motivaram o surgimento do paradigma de Geração Aumentada de Recuperação. A partir desse contexto, delimita-se o problema de pesquisa, qual seja, a ausência de evidência experimental sistemática que oriente a seleção de bancos de vetores de código aberto para pipelines RAG. São então apresentados os objetivos geral e específicos da investigação, seguidos da justificativa científica, tecnológica e econômica que fundamenta a relevância do estudo, da descrição sumária da abordagem metodológica adotada e desta síntese organizacional do trabalho.

O **Capítulo 2 — Fundamentação Teórica** apresenta os conceitos e tecnologias que sustentam a pesquisa experimental desenvolvida nos capítulos subsequentes. A discussão inicia com os Grandes Modelos de Linguagem, abordando seu funcionamento, capacidades e limitações, e avança para o conceito de embeddings e representação vetorial de linguagem natural, que constitui o substrato sobre o qual os bancos de vetores operam. Em seguida, são discutidos os principais algoritmos de busca aproximada de vizinhos mais próximos (KNN) que determinam os o caminho à seguir baseando-se na precisão e desempenho nos índices avaliados. O capítulo prossegue com a caracterização do paradigma RAG, descrevendo sua arquitetura, seu fluxo de inferência e os mecanismos pelos quais a qualidade da recuperação vetorial impacta a qualidade da resposta gerada. Por fim, são descritas as três soluções de banco de vetores objeto desta comparação (FAISS, Milvus e Chroma) com análise de suas arquiteturas internas, modelos de indexação e posicionamentos no ecossistema de ferramentas de código aberto, complementada por uma discussão sobre estratégias de chunking de documentos e seu papel no pipeline RAG.

O **Capítulo 3 — Metodologia** apresenta o desenho experimental desta pesquisa: o benchmark comparativo implementado em Python entre FAISS, Milvus e Chroma. O capítulo detalha as decisões de design do pipeline de recuperação vetorial, a estratégia de geração de dados para os três cenários de escala, o cômputo do ground truth via busca exata, as configurações de índice avaliadas e as métricas utilizadas. Os resultados e sua discussão são apresentados no capítulo seguinte.

O **Capítulo 4 — Desenvolvimento e Resultados** apresentará os resultados do benchmark por cenário e solução, sua análise estatística, os trade-offs observados e as recomendações práticas. O **Capítulo 5 — Conclusão** retomará a questão norteadora, discutirá as limitações do estudo e apresentará sugestões de trabalhos futuros, como a avaliação de diferentes modelos de embedding, estratégias de chunking, busca híbrida e geração com LLM.

Por fim, o **Capítulo 6 — Referências** reunirá todas as obras, artigos, documentações técnicas e demais fontes citadas ao longo do trabalho, organizadas segundo as normas da Associação Brasileira de Normas Técnicas.

---

# 2 REFERENCIAL TEÓRICO

## 2.1 Bancos de Dados Vetoriais

Bancos de dados vetoriais são sistemas especializados projetados para armazenar e consultar de forma eficiente vetores de alta dimensão. Eles convertem objetos (textos, imagens, áudio etc.) em representações numéricas (vetores) por meio de modelos de embedding e armazenam esses vetores em estruturas otimizadas para operações de similaridade. Esse processo envolve etapas como:

- **Conversão de objetos em vetores:** o conteúdo bruto é processado por modelos de incorporação (“embedding models”) que geram vetores numéricos representando o significado semântico do conteúdo. Palavras ou conceitos semanticamente próximos produzem vetores próximos no espaço multidimensional, como discutido na literatura de embeddings.
- **Armazenamento especializado:** os vetores gerados são armazenados em formatos e estruturas de dados especiais que permitem consultas rápidas. Diferentemente de bancos de dados relacionais, os bancos vetoriais são otimizados para operações sobre dados de alta dimensão e para consultas de similaridade, suportando escalabilidade horizontal e balanceamento de carga em clusters.
- **Consulta por similaridade:** o banco vetorial oferece consultas de similaridade eficientes, encontrando de forma rápida os k-vizinhos mais próximos (k-NN) ou todos os vetores dentro de um raio dado. Essa capacidade é fundamental para aplicações de Busca Aumentada de Recuperação (RAG), em que é preciso recuperar documentos semanticamente relevantes para compor a base de conhecimento de um modelo gerador.
Os bancos vetoriais apresentam vantagens claras em cenários de dados não estruturados: além de suportarem pesquisas semânticas que superam a simples busca textual, eles escalam para bilhões de vetores usando infraestrutura distribuída. Em resumo, “um banco de dados vetoriais é um sistema especializado que armazena e consulta vetores de alta dimensão de forma eficiente” e são “fundamentais para aplicativos RAG”. Esse papel central dos bancos vetoriais no pipeline RAG foi destacado em guias técnicos da AWS, por exemplo, que ilustram como um modelo de embedding transforma conteúdo bruto em vetores guardados em um banco otimizado para recuperação rápida.

---

## 2.2 Geração Aumentada de Recuperação (RAG)

RAG (“Retrieval-Augmented Generation”) é uma estratégia que combina modelos de linguagem pré-treinados (memória paramétrica) com memória externa não-paramétrica (um índice vetorial). Na prática, um modelo seq2seq (como BART ou T5) gera texto a partir de um prompt, mas antes disso emite uma consulta que é respondida por um mecanismo de recuperação. Esse mecanismo usa o banco vetorial para buscar passagens relevantes com base no embedding da consulta. Em seguida, as passagens recuperadas são incluídas como contexto suplementar ao LLM para a geração final.

Por exemplo, Lewis et al. (2020) propuseram um modelo RAG em que o “modelo seq2seq pré-treinado” se alia a um “índice denso de vetores da Wikipedia, acessado por um recuperador neural pré-treinado”. Na avaliação deles, esse modelo de pesquisa-acrescentada superou o desempenho de modelos puramente paramétricos em tarefas de perguntas e respostas de domínio aberto, produzindo respostas mais específicas e factuais. Em termos gerais, a RAG permite que o gerador tenha acesso a conhecimento “atualizável” fora de seus parâmetros internos, mitigando problemas como alucinação de fatos. Estudos recentes mostram que incluir recuperação externa em tarefas de diálogo ou QA reduz significativamente as alucinações do modelo.

O pipeline RAG opera em duas fases distintas e complementares, conforme ilustrado na Figura 1. A primeira, denominada **fluxo de ingestão**, ocorre de forma offline: documentos brutos provenientes de fontes diversas são carregados e segmentados em trechos menores (chunks); cada chunk é convertido em um vetor de alta dimensão por um modelo de embeddings e armazenado, junto com seus metadados, em um banco de dados vetorial estruturado para busca eficiente. A segunda fase, o **fluxo de consulta**, ocorre em tempo real: a consulta do usuário é transformada em um vetor pelo mesmo modelo de embeddings utilizado na ingestão; esse vetor é utilizado para buscar, no banco vetorial, os chunks semanticamente mais próximos; os trechos recuperados são então concatenados ao prompt original e fornecidos como contexto ao LLM, que gera a resposta final fundamentada nas informações recuperadas. Essa separação entre as fases de ingestão e consulta é característica central da arquitetura proposta por Lewis et al. (2020) e detalhada operacionalmente por Wolff (2023):

![Figura 1 — Arquitetura do pipeline RAG: fluxo de ingestão (offline) e fluxo de consulta (online). Fonte: Adaptado de WOLFF, Hayden. RAG 101: Demystifying Retrieval-Augmented Generation Pipelines. NVIDIA Developer Blog, dez. 2023. Disponível em: https://developer.nvidia.com/blog/rag-101-demystifying-retrieval-augmented-generation-pipelines/. Acesso em: ago. 2026.](https://developer-blogs.nvidia.com/wp-content/uploads/2023/12/rag-pipeline-ingest-query-flow-b.png)

---

## 2.3 Indexação Vetorial e Algoritmos de Busca

Uma etapa crucial em bancos de dados vetoriais é a indexação, i.e., a construção de uma estrutura de dados que acelera as buscas de similaridade. De acordo com a documentação do Milvus, “um índice é uma estrutura adicional construída sobre os dados que depende do algoritmo de busca aproximada de vizinhos mais próximos”. Essa estrutura sacrifica tempo de pré-processamento, uso de memória e até recall em troca de consultas muito mais rápidas. Por isso, a escolha do índice (“data structure”) é crítica: índices diferentes serão melhores para cenários distintos (por exemplo, em termos de latência vs. taxa de acertos).

Os algoritmos de indexação para busca em vetores podem ser categorizados em três classes principais:

- **Partição espacial (cluster-based, p.e. IVF):** divide o espaço vetorial em regiões/clusters. Cada região tem um centroide representativo e armazena os vetores mais próximos desse centroide. Na consulta, primeiramente encontra-se o centróide (ou centroids) mais próximo da consulta e, em seguida, varrem-se apenas os vetores daquele ou daqueles clusters. Essa estratégia (como o índice invertido IVF usado em Faiss) reduz drasticamente o número de comparações ao evitar escanear todo o banco de dados.
- **Grafos de vizinhança (p.ex. HNSW):** constroem um grafo hierárquico de vizinhança aproximada onde cada vetor é um nó conectado a vários vizinhos próximos. A busca começa em um nó de entrada no topo do grafo e usa uma busca gulosa para descer camada por camada em direção aos vetores mais próximos da consulta — camadas superiores atuam como atalhos de longo alcance e a camada base (layer 0) contém todos os vetores com conexões locais densas (MALKOV; YASHUNIN, 2020). Esse método oferece excelente precisão e baixa latência, ao custo de maior uso de memória, pois armazena múltiplas conexões por nó.
![Figura 2 — Estrutura hierárquica do grafo HNSW: camadas superiores realizam navegação de longo alcance entre poucos nós; a camada base (layer 0) contém todos os vetores com conexões locais densas. Fonte: Adaptado de MALKOV, Yu. A.; YASHUNIN, D. A. Efficient and robust approximate nearest neighbor search using Hierarchical Navigable Small World Graphs. IEEE Transactions on Pattern Analysis and Machine Intelligence, v. 42, n. 4, p. 824–836, 2020. DOI: 10.1109/tpami.2018.2889473.](PENDENTE: extrair Figura 1 do PDF em https://arxiv.org/pdf/1603.09320 e hospedar no GitHub do projeto)
- **Locality-Sensitive Hashing (LSH) e outras hashes:** usam funções de hash para mapear vetores em buckets de alta probabilidade de similaridade. Esse método era usado historicamente (p.ex. simHash), mas estudos recentes de mercado apontam que seu desempenho geral (em leitura, escrita e espaço) é geralmente inferior aos índices de grafos ou clusters.
**Algoritmos de busca:** Dado o índice, a consulta a vetores envolve métricas de similaridade. Tipicamente, busca-se resolver k-NN: encontrar o vetor armazenado $x_i$ que minimiza a distância para o vetor de consulta $q$ (por exemplo, distância Euclidiana $||q - x_i||$). Ferramentas como Faiss definem funções como $j = \arg\min_i |q - x_i|$ para identificar o vizinho mais próximo. Também são suportados outros critérios, como maximum inner product search (busca por produto interno máximo) em vez de distância Euclidiana. Em ambientes de produção, quase todos usam busca aproximada (ANN): sacrificando uma pequena precisão, reduzem enormemente o custo computacional. Faiss, por exemplo, oferece métodos para trocar precisão por velocidade, permitindo aceitar até ~10% de acertos incorretos em troca de consultas ~10× mais rápidas. Além disso, algoritmos como Product Quantization (PQ) comprimem vetores em códigos curtos mantendo a capacidade de cálculo de distância quase precisa. Em resumo, os principais componentes de um índice vetorial são a estrutura de dados (árvore, IVF, grafo HNSW etc.), opcionalmente quantização (PQ, OPQ) e refinadores pós-busca. Ajustar os parâmetros (por exemplo, valor de M e ef no HNSW) permite balancear precisão versus latência.

---

## 2.4 Ferramentas e Frameworks Relevantes

Entre as principais implementações de busca vetorial e RAG destacam-se algumas ferramentas de código aberto e bibliotecas de alto desempenho:

- **FAISS (Meta AI, 2021):** biblioteca de busca por similaridade desenvolvida pelo Facebook AI Research. Suporta diversos índices (IVF, PQ, HNSW etc.) e é otimizada para GPU. Permite buscar k-NN ou realizar busca vetorial em lote eficientemente. Por exemplo, sua implementação do índice invertido (IVF) segue a abordagem clássica de Sivic & Zisserman (2003) para buscas não-exaustivas. Faiss também inclui técnicas avançadas como quantização (PQ, OPQ) e o índice HNSW de Malkov et al. (2016). Essas otimizações permitem a Faiss “acalhar” o tempo de consulta contra a precisão, além de suportar métricas alternativas (produto interno, L1) e buscas em vetores binários.
- **Milvus (Zilliz/LF AI, 2021):** banco de dados vetoriais de alto desempenho e nuvem nativo. Milvus separa a camada de serviço dos motores de busca de vetores, suportando múltiplos tipos de índices conforme o caso de uso. Por exemplo, para um campo FLOAT_VECTOR, Milvus oferece índices FLAT (exata), IVF_FLAT, IVF_PQ, HNSW, DISKANN, SCANN, e até versões GPU (p.e. GPU_IVF_PQ). A documentação do Milvus enfatiza que o índice acelera a busca ao custo de memória e custo de pré-processamento. Seu índice HNSW (descrição no item anterior) permite busca rápida com alta acurácia. No geral, Milvus é projetado para casos de uso industriais que exigem escalabilidade, consistência ACID e vetorização em larga escala.
- **Chroma (Cognition, 2023):** banco de dados de vetores de fonte aberta otimizado para armazenamento em object storage. De acordo com a documentação, “os índices do Chroma são construídos e otimizados para object storage, oferecendo custo e desempenho incomparáveis”. Ele suporta não apenas busca vetorial (semântica) mas também busca textual tradicional (BM25) e indexação esparsa (SPLADE), permitindo consultas híbridas. Em cenários de RAG, Chroma destaca-se por escalabilidade serverless e por incorporar mecanismos de cache em memória e disco para acelerar consultas semânticas.
Essas ferramentas refletem diferentes estratégias de implementação, mas todas têm em comum o uso de índices vetoriais eficientes e compatibilidade com integração a modelos de embeddings e LLMs.

### 2.4.1 Critérios de Seleção dos Bancos Avaliados

A seleção dos três bancos de vetores avaliados neste trabalho — FAISS, Milvus e Chroma — foi orientada por dois critérios complementares: **representatividade arquitetural** e **reprodutibilidade**. O objetivo foi garantir que os bancos escolhidos cobrissem o espectro de abordagens técnicas disponíveis em soluções de código aberto, sem sobreposição arquitetural entre eles.

O **FAISS** representa o polo de desempenho máximo e controle algorítmico: trata-se de uma biblioteca sem servidor, sem camada de persistência nativa e sem abstrações de banco de dados, projetada para operar diretamente em memória ou em disco de forma determinística (FAISS, 2021). Sua inclusão justifica-se por seu papel fundacional na literatura de recuperação vetorial e, no contexto deste experimento, por sua função como **oráculo de busca exata** — mecanismo que compara o vetor de consulta com todos os vetores armazenados sem qualquer aproximação, produzindo os resultados matematicamente corretos. Esses resultados corretos formam o chamado ***ground truth*** (conjunto de referência de respostas esperadas), utilizado para medir com precisão o quão próximos os outros bancos chegam do resultado ideal.

O **Milvus** representa uma solução orientada à produção, com recursos que, em sua arquitetura distribuída, suportam escalabilidade horizontal e alta disponibilidade (MILVUS, 2021). Neste trabalho, entretanto, foi avaliado exclusivamente o **Milvus Lite**, executado localmente; portanto, os resultados não medem diretamente o comportamento de uma implantação distribuída do Milvus.

O **Chroma** representa o polo de simplicidade e integração voltado à experiência do desenvolvedor: possui interface de programação minimalista e integração com frameworks do ecossistema, como LangChain e LlamaIndex, conforme documentado em sua especificação oficial (CHROMA, 2023). Sua inclusão justifica-se por representar a classe de soluções orientadas à produtividade do desenvolvedor e à prototipagem rápida de pipelines.

Foram excluídos do escopo deste trabalho: o **Qdrant** e o **Weaviate**, por sobreposição arquitetural com o Milvus — ambos são servidores vetoriais distribuídos de código aberto com lógica de escalabilidade similar, sem diferença conceitual suficiente para representar uma terceira arquitetura distinta; e o **Pinecone**, por ser solução proprietária e gerenciada em nuvem, incompatível com o requisito de reprodutibilidade do experimento em ambiente local controlado.

Essa composição triangular — biblioteca de referência sem servidor (FAISS), solução orientada à produção avaliada em modo local (Milvus Lite) e banco orientado à experiência do desenvolvedor (Chroma) — permite comparar perfis distintos de recuperação vetorial, sem atribuir aos resultados locais do Milvus conclusões sobre clusters distribuídos.

---

## 2.5 Embeddings e Chunking de Documentos

Embeddings de texto são representações vetoriais de sentenças ou documentos que preservam relações semânticas. Em aplicações de RAG, cada trecho de texto (frase, parágrafo ou documento inteiro) é convertido em um vetor numérico por um modelo de incorporação (p.ex. BERT, Sentence-BERT, etc.). Palavras ou frases semânticamente similares produzem vetores próximos no espaço vetorial. Como observado na documentação da AWS, “o modelo de incorporação transforma os dados brutos em vetores numéricos, que representam o significado semântico do conteúdo”. Em seguida, as consultas do usuário também são convertidas em vetores pelo mesmo modelo, permitindo medir semelhança (cosseno, L2, produto interno) entre a consulta e cada elemento armazenado.

**Chunking (segmentação):** para documentos muito grandes, é comum partir o texto em blocos menores (chunks) antes de gerar embeddings. Essa técnica é crucial para RAG porque a qualidade da recuperação depende de como o contexto é subdividido. De acordo com Latif et al. (ICNLSP 2025), “o aspecto crítico no pipeline RAG é o chunking de documentos”, ou seja, dividir documentos em segmentos gerenciáveis. O chunking evita que um único embedding perca informação relevante ou extrapole o tamanho máximo do contexto do modelo. Estratégias de chunking podem ser estáticas (tamanho fixo) ou dinâmicas (baseadas em similaridade semântica entre frases). Estudos recentes apontam que chunking inteligente (por exemplo, recursivo ou semântico) melhora a precisão do sistema de recuperação, já que cada fragmento é coerente e rico em informação. Em suma, em RAG cada chunk de texto é transformado em embedding e inserido no banco vetorial; na hora da recuperação, os chunks mais similares são reinseridos como contexto no prompt do LLM para gerar a resposta final.

---

# 3 METODOLOGIA

---

## 3.1 Caracterização da Pesquisa

Esta investigação caracteriza-se como uma **pesquisa experimental quantitativa** de abordagem positivista, estruturada sob o paradigma de benchmark controlado. O objetivo central consiste em comparar sistemática e reprodutivelmente o desempenho operacional e a qualidade de recuperação semântica de três bancos de vetores de código aberto (FAISS, Milvus e Chroma) quando aplicados a pipelines RAG padronizados e isolados de variáveis confundidoras.

A natureza experimental da pesquisa decorre do fato de que a investigação manipula uma variável independente — o banco de vetores — mantendo controladas todas as demais condições relevantes (modelo de embeddings, métrica de similaridade, seed aleatória, queries e corpus). Essa estrutura permite isolar o efeito específico da solução de armazenamento e recuperação sobre as métricas de desempenho observadas.

**Justificativa metodológica:** A exclusão proposital de um modelo de linguagem (LLM) do experimento decorre de uma necessidade de controle experimental rigoroso. Introduzir um LLM adicionaria uma segunda variável independente (qualidade do modelo) cujo efeito sobre a métrica final seria confundido com o efeito do banco de vetores, violando o princípio de ceteris paribus. Consequentemente, emprega-se **Recall@k** como métrica substituta (proxy) de qualidade semântica, métrica consolidada na avaliação de busca aproximada de vizinhos mais próximos (AUMULLER et al., 2020), empregada aqui na variante de taxa de acerto definida na seção 3.6.1.

---

## 3.2 Pipeline Padronizado

O pipeline experimental foi projetado como um sistema de recuperação vetorial isolado, desprovido de componentes de geração textual, mas estruturalmente análogo ao componente de busca de uma aplicação RAG completa.

### 3.2.1 Componentes Fixos do Pipeline

**Modelo de embeddings:** All-MiniLM-L6-v2 (Sentence-Transformers)
- Dimensionalidade: 384
- Normalização: L2 (cos-similarity equivalente a inner product em vetores normalizados)
- Justificativa: modelo leve (33M parâmetros) da família Sentence-Transformers (REIMERS; GUREVYCH, 2019), amplamente adotado pela comunidade para tarefas de recuperação semântica e compatível nativamente com todos os bancos avaliados. A escolha privilegia a relação custo-benefício — desempenho competitivo com fração dos parâmetros de modelos maiores — e a reprodutibilidade em ambiente Colab com CPU
**Métrica de similaridade:** Inner Product (equivalente a cosine similarity após L2-norm)

Em sistemas de busca vetorial, a **métrica de similaridade** define como a proximidade entre dois vetores é calculada. As principais métricas utilizadas na literatura são:

| Métrica | Fórmula | Mede | Quando usar |
|---------|---------|------|------------|
| **Distância Euclidiana** | $\sqrt{\sum (a_i - b_i)^2}$ | Distância geométrica absoluta | Espaços onde magnitude importa |
| **Cosine Similarity** | $\frac{\mathbf{a} \cdot \mathbf{b}}{\|\mathbf{a}\| \|\mathbf{b}\|}$ | Ângulo entre vetores | Quando só a direção importa (textos) |
| **Inner Product (IP)** | $\mathbf{a} \cdot \mathbf{b} = \sum a_i \cdot b_i$ | Produto escalar direto | Vetores normalizados (≡ cosine) |

![Representação geométrica da similaridade coseno: dois vetores partindo da mesma origem formam um ângulo θ; quanto menor o ângulo, maior a similaridade entre os vetores. Adaptado de: SHIVANANDHAN, 2025.](fig_3_7_cosine_similarity.png)

O **Inner Product** corresponde ao somatório do produto elemento a elemento de dois vetores. Quando os vetores são previamente normalizados (norma L2 = 1 para todos), o Inner Product torna-se matematicamente equivalente ao Cosine Similarity, pois os denominadores da fórmula cosine valem 1. Essa equivalência é explorada no experimento: normalizando os embeddings na entrada, obtém-se a semântica do cosine com a eficiência computacional do produto escalar — operação altamente otimizada em hardware moderno via instruções SIMD e suporte nativo em bibliotecas como FAISS e NumPy

**Framework de desenvolvimento:** Python 3.12.13, bibliotecas nativas de cada banco
- Exclusão de abstrações universais (LangChain, LlamaIndex) para isolar implementações nativas
- Justificativa: evitar overhead de middleware que mascara características intrínsecas do banco
**Seed aleatória:** 42 (`np.random.seed` e geradores `np.random.default_rng`, que determinam os vetores e as consultas sintéticas)
- Justificativa: reprodutibilidade de dados sintéticos e amostragem de queries
![Pipeline experimental completo do benchmark, dividido em três fases: Indexação (corpus → embedding → banco de vetores), Busca (queries → similaridade → top-k) e Avaliação (comparação com ground truth → métricas). Elaborado pelo autor, 2026.](fig_3_1_pipeline.mermaid)

**Fator experimental:** solução de recuperação e configuração de índice (FAISS Flat/HNSW/IVF, Milvus Lite FLAT/HNSW e Chroma HNSW automático). A comparação não isola apenas a marca do banco, pois os índices e seus parâmetros também diferem entre as soluções.

**Variáveis controladas:**
- Embeddings e dimensionalidade
- Corpus de base e distribuição
- Set de queries (idêntico para todos os bancos)
- Seed aleatória
- Hardware (ambiente Colab registrado nos resultados)
---

## 3.3 Cenários de Escala (S1, S2, S3)

A avaliação abrange três cenários de escala representativos de contextos de aplicação distintos:

| Cenário | Vetores | Dados | Tipo | Justificativa |
|---------|---------|-------|------|--------------|
| **S1** | 10k | AG News (corpus real) | Embeddings reais | Próximo a produção pequena (startup, POC); avalia comportamento com dados naturais |
| **S2** | 100k | Sintético (gaussiano normalizado) | Vetores sintéticos | Produção média (PME, unidade dentro empresa); escalabilidade intermediária |
| **S3** | 500k | Sintético (gaussiano normalizado) | Vetores sintéticos | Carga empresarial (banco de dados corporativo); teste de limites operacionais |

![Progressão dos três cenários de escala avaliados: S1 com 10 mil vetores do corpus AG News, S2 com 100 mil vetores sintéticos e S3 com 500 mil vetores sintéticos, cada cenário representando um porte distinto de aplicação RAG. Elaborado pelo autor, 2026.](fig_3_2_cenarios.mermaid)

### 3.3.1 Justificativa dos Dados Sintéticos (S2, S3)

Para S2 e S3, utilizou-se dados sintéticos em vez de corpus reais por três motivos:

1. **Reprodutibilidade:** replicação exata da distribuição sem variância entre execuções
2. **Escalabilidade:** geração de 100k e 500k vetores em tempo razoável sem overhead de download/processamento
3. **Isolamento:** eliminação de confundidores ligados ao conteúdo específico (coerência intra-cluster, redundância textual)
Os vetores sintéticos foram gerados como amostras de uma **distribuição gaussiana multivariada normalizada**, processo composto por duas etapas:

**Etapa 1 — Amostragem gaussiana:** cada vetor de dimensão 384 é amostrado de uma distribuição normal multivariada com parâmetros:
- **μ = 0**: vetor de médias nulo — cada dimensão tem valor esperado zero, centrando a distribuição na origem do espaço vetorial
- **Σ = I**: matriz de covariância identidade — as 384 dimensões são estatisticamente independentes entre si e têm variância unitária, evitando que alguma dimensão domine artificialmente a busca
**Etapa 2 — Normalização L2:** cada vetor amostrado é dividido por sua norma euclidiana ($\mathbf{v} \leftarrow \mathbf{v} / \|\mathbf{v}\|_2$), projetando-o sobre a **hiperesfera unitária** de dimensão 383 (superfície de raio 1 em ℝ³⁸⁴). Essa etapa é necessária para que a busca por Inner Product produza resultados equivalentes ao cosine similarity.

**Validade da aproximação:** embeddings textuais reais de modelos baseados em transformers tendem a se distribuir de forma aproximadamente gaussiana antes da normalização, com dimensões relativamente independentes. A normalização L2 subsequente — padrão tanto no all-MiniLM-L6-v2 quanto nos bancos avaliados — garante que os vetores sintéticos e reais habitam o mesmo espaço geométrico de busca.


---

## 3.4 Configurações de Índice Avaliadas

Cada banco de vetores foi configurado com múltiplas estratégias de indexação para capturar a variância de escolhas arquiteturais realistas.

### 3.4.1 FAISS

| Índice | Parâmetros | S1 | S2 | S3 | Justificativa |
|--------|-----------|-----|-----|-----|--------------|
| **Flat** (`IndexFlatIP`) | — | ✅ | ✅ | ✅ | Busca exata; sem aproximação; usado também como oráculo de ground truth |
| **HNSW** (`IndexHNSWFlat`) | M=32 | ✅ | ✅ | ✅ | M controla o número de vizinhos por nó no grafo; parâmetros ef usam padrão FAISS |
| **IVF** (`IndexIVFFlat`) | nlist=707¹ | — | — | ✅ | S3 apenas; particiona espaço em √500.000 ≈ 707 células de Voronoi; nprobe usa padrão FAISS |

¹ `nlist = int(√500.000) = 707`; regra prática ANN: nlist ≈ √N para balancear recall e latência.

**⚠️ Nota metodológica:** os parâmetros `efSearch` (HNSW) e `nprobe` (IVF) não foram explicitamente definidos no notebook, usando os padrões da biblioteca FAISS. Isso implica configuração conservadora de recall para o HNSW e busca em apenas 1 célula no IVF — interpretação discutida na seção de Ameaças à Validade (Cap. 5).

**Justificativa de exclusão de IVF de S1/S2:** com corpora pequenos, nlist ≈ √N produz células muito pequenas, aumentando overhead de treinamento sem ganho de latência relevante.

### 3.4.2 Milvus

| Índice | Parâmetro | S1 | S2 | S3 | Justificativa |
|--------|-----------|-----|-----|-----|--------------|
| **Flat** (`FLAT`) | — | ✅ | ✅ | ✅ | Equivalente FAISS Flat; busca exata via produto interno |
| **HNSW** (`HNSW`) | M=16, efConstruction=200, ef=64 | ✅ | ✅ | ✅ | M=16 (menor que FAISS) equilibra memória e conectividade; efConstruction=200 garante grafo denso na indexação; ef=64 na busca controla o escopo de exploração |

**Justificativa de diferenças vs. FAISS:** Milvus expõe `efConstruction` e `ef` (busca) explicitamente via API, permitindo controle fino; M=16 em vez de 32 compensa o overhead de serialização da camada de persistência.

### 3.4.3 Chroma

| Índice | Parâmetro | S1 | S2 | S3 | Justificativa |
|--------|-----------|-----|-----|-----|--------------|
| **HNSW** | Automático (Chroma oculta parâmetros) | ✅ | ✅ | ⚠️ Ver Cap. 4 | Banco otimizado para facilidade de uso; não expõe parâmetros M e ef explicitamente ao usuário; comportamento em S3 discutido no Capítulo 4 |

---

## 3.5 Ground Truth (Oráculo de Busca Exata)

A avaliação de qualidade semântica repousa em um **oráculo de busca exata**, metodologia padrão em sistemas de recuperação de informação (Information Retrieval).

### 3.5.1 Definição

O **ground truth** para cada query é construído executando busca **sem aproximações** sobre o corpus completo utilizando **FAISS IndexFlatIP** (Inner Product sobre vetores L2-normalizados), que garante recuperação matematicamente ótima dos k vizinhos mais próximos.

Operacionalmente, em cada cenário o oráculo é executado uma única vez sobre as 200 consultas e retorna, para cada uma, os 10 vizinhos exatos (o maior k avaliado). Para um dado k ∈ {1, 5, 10}, o ground truth da consulta corresponde aos k primeiros desses 10 resultados:

```
ground_truth[q_i, :k] = k primeiros vizinhos exatos da consulta q_i (IndexFlatIP)
```

Esse conjunto permite verificar em que medida cada índice aproximado preserva a vizinhança exata das consultas. Como o oráculo não utiliza aproximações, seu próprio Recall@k vale 1,0 por definição, o que o torna a referência superior de comparação para as demais configurações.

![Processo de construção do oráculo de busca exata: o FAISS IndexFlatIP processa o corpus completo e as queries offline gerando o ground truth; os índices aproximados têm seus resultados comparados ao oráculo para cálculo do Recall@k. Elaborado pelo autor, 2026.](fig_3_3_ground_truth.mermaid)

### 3.5.2 Justificativa

1. **Padrão estabelecido:** ANN-Benchmarks utiliza busca exata como ground truth (AUMULLER et al., 2020)
2. **Independência:** o oráculo é executado uma única vez offline, não depende da implementação específica de cada banco
3. **Validade:** IndexFlatIP não aproxima; computação é exata em aritmética de ponto flutuante
---

## 3.6 Métricas de Avaliação

Foram coletadas cinco métricas operacionais — tempo de indexação, latência, throughput, consumo de memória e tamanho em disco — e uma métrica de recuperação, a taxa de acerto no top-k, registrada nos arquivos como Recall@k. Todas as métricas são calculadas para cada combinação de cenário, solução e configuração de índice.

### 3.6.1 Recall@k (Qualidade de Recuperação)

**Definição clássica.** Em recuperação de informação, o ***recall*** (revocação) mede a fração dos itens relevantes que o sistema consegue recuperar. Avaliado apenas nos k primeiros resultados retornados, denomina-se Recall@k e é expresso por:

$$\text{Recall@k} = \frac{| \text{Relevantes} \cap \text{Recuperados@k} |}{| \text{Relevantes} |}$$

onde **Relevantes** é o conjunto de itens corretos para a consulta, **Recuperados@k** é o conjunto dos k itens retornados pelo sistema e |·| denota a cardinalidade (número de elementos) de um conjunto. Neste trabalho, os itens relevantes de cada consulta são os k vizinhos mais próximos obtidos pelo oráculo de busca exata, isto é, o *ground truth* descrito na seção 3.5.

![Fórmula e definição de Recall@k: razão entre os itens relevantes recuperados no top-k e o total de itens relevantes existentes no conjunto. Adaptado de: EVIDENTLY AI, 2025.](fig_3_6_recall_at_k.png)

**Definição adotada neste trabalho (taxa de acerto).** O experimento utiliza uma variante do Recall@k que verifica, para cada consulta, se o índice aproximado preservou **ao menos um** dos k vizinhos exatos entre os k resultados que retornou. Sejam *N* o número de consultas (N = 200), *q_i* a i-ésima consulta, *GT_k(q_i)* o conjunto dos k vizinhos exatos fornecido pelo oráculo e *R_k(q_i)* o conjunto dos k resultados retornados pelo índice avaliado. O acerto da consulta e o Recall@k são definidos por:

$$\text{acerto}_k(q_i) = \begin{cases} 1, & \text{se } GT_k(q_i) \cap R_k(q_i) \neq \varnothing \\ 0, & \text{caso contrário} \end{cases}$$

$$\text{Recall@k} = \frac{1}{N} \sum_{i=1}^{N} \text{acerto}_k(q_i)$$

O Recall@k assim definido é a proporção de consultas para as quais o índice aproximado recuperou, no top-k, ao menos um dos verdadeiros k vizinhos mais próximos. Varia de 0 (nenhuma consulta com acerto) a 1 (todas as consultas com acerto). Nos arquivos de resultados e nas figuras do Capítulo 4, as colunas `recall@1`, `recall@5` e `recall@10` referem-se a esta definição.

**Exemplo numérico.** Considere k = 5 e N = 5 consultas com valores ilustrativos, conforme a tabela a seguir e a Figura 3.4.

| Consulta | Ground truth (top-5 exato) | Recuperados@5 | Interseção | Acerto |
|----------|----------------------------|---------------|------------|--------|
| q1 | {v1, v3, v5, v7, v9} | {v1, v3, v6, v7, v11} | {v1, v3, v7} | 1 |
| q2 | {v2, v4, v8, v12, v15} | {v4, v8, v9, v12, v20} | {v4, v8, v12} | 1 |
| q3 | {v10, v14, v16, v18, v21} | {v2, v3, v22, v23, v24} | vazia | 0 |
| q4 | {v1, v2, v3, v4, v5} | {v5, v9, v10, v11, v12} | {v5} | 1 |
| q5 | {v6, v7, v8, v9, v10} | {v6, v7, v8, v9, v10} | os 5 itens | 1 |

Quatro das cinco consultas apresentam interseção não vazia, logo Recall@5 = (1 + 1 + 0 + 1 + 1) / 5 = 4/5 = **0,80**. Pela definição clássica, os valores por consulta seriam 3/5 = 0,60 para q1, 0,60 para q2, 0 para q3, 0,20 para q4 e 1,00 para q5, com média de 0,48. A diferença ilustra que a taxa de acerto é uma medida mais permissiva.

![Exemplo de cálculo do Recall@k como taxa de acerto, com k igual a 5 e cinco consultas ilustrativas: para cada consulta, compara-se o ground truth com os itens recuperados; a consulta conta como acerto quando a interseção não é vazia, resultando em Recall@5 igual a 0,80. Elaborado pelo autor, 2026.](fig_3_4_recall_acerto.svg)

**Valores avaliados e interpretação.** Avaliam-se k = 1, 5 e 10. Para k = 1, o acerto exige que o vizinho mais próximo retornado seja exatamente o vizinho mais próximo verdadeiro, e as duas variantes coincidem; é, portanto, o valor mais restritivo. Para k = 5 e k = 10, basta que um dos vizinhos exatos esteja entre os k retornados, o que torna a métrica progressivamente mais permissiva: valores próximos de 1,0 em k = 10 indicam que o índice raramente perde toda a vizinhança de uma consulta, mas não que todos os vizinhos exatos foram recuperados. Por essa razão, os resultados do Capítulo 4 devem ser lidos de forma conjunta nos três valores de k, e esta característica é retomada como limitação na discussão sobre validade de construto.

**Papel como métrica substituta (*proxy*).** Em uma aplicação RAG, o modelo de linguagem não pode produzir respostas melhores do que o contexto que recebe. O Recall@k mede em que proporção o banco de vetores aproximado preserva a vizinhança que uma busca exata teria encontrado, funcionando como indicador da qualidade de recuperação sem a introdução de um modelo de linguagem como segunda variável.

### 3.6.2 Tempo de Indexação

**Definição:** tempo decorrido, em segundos, na construção do índice e na inserção dos vetores do corpus no banco, medido com `time.perf_counter()`.

**Escopo da medição:** no FAISS, inclui o treinamento (quando o índice o exige, como no IVF) e a adição dos vetores; no Milvus, a criação da coleção e a inserção dos vetores em lotes de 5.000; no Chroma, a construção da coleção e a inserção dos vetores. O tempo reflete, portanto, o procedimento nativo de cada banco, incluindo a ingestão.

**Unidade:** segundos (s).

### 3.6.3 Latência (p50, p90 e p99)

**Definição:** em cada execução, submete-se ao índice o lote das 200 consultas e mede-se o tempo total, que é dividido pelo número de consultas. O resultado é a latência média por consulta da execução, *L_r*, em milissegundos. Repetindo o procedimento por 10 execuções (após uma execução de aquecimento descartada), obtém-se a amostra {L_1, ..., L_10}, da qual são calculados os percentis:

- **p50 (mediana):** valor abaixo do qual está metade das execuções — representa o desempenho típico;
- **p90:** valor abaixo do qual estão 90% das execuções;
- **p99:** valor abaixo do qual estão 99% das execuções — indica o pior caso observado entre as execuções.
Os percentis são calculados por interpolação linear (`numpy.percentile`). Como a amostra possui 10 valores, o p99 aproxima-se do maior valor observado.

**Unidade:** milissegundos (ms) por consulta.

**Observação:** a latência é medida em lote, e não consulta a consulta; ela pode diferir da latência de uma consulta isolada em produção, o que é discutido como ameaça à validade.

**Relevância:** em sistemas RAG interativos, a latência de recuperação compõe o tempo total de resposta ao usuário, de modo que o p50 indica o desempenho típico e o p99, a variabilidade em situações adversas.

### 3.6.4 Throughput (QPS)

**Definição:** *throughput* é a quantidade de consultas processadas por unidade de tempo. Em consultas por segundo (*queries per second*, QPS), é obtido a partir da latência média por consulta, $\bar{L}$, em milissegundos:

$$\text{QPS} = \frac{1000}{\bar{L}}$$

onde $\bar{L}$ é a média das 10 latências por execução *L_r*.

**Unidade:** consultas por segundo.

**Relevância:** indica a capacidade de processamento do banco em consultas em lote. A métrica não mede concorrência de múltiplos clientes simultâneos.

### 3.6.5 Consumo de Memória (RAM)

**Definição:** variação da memória residente (*RSS*, *resident set size*) do processo Python, obtida com a biblioteca `psutil`, entre o início e o fim da etapa de indexação (treinamento, quando aplicável, e inserção dos vetores). A coleta de lixo (`gc.collect()`) é executada antes da primeira leitura e antes da leitura final; valores negativos, decorrentes de liberação de memória, são registrados como zero.

**Unidade:** megabytes (MB).

**Relevância:** em ambientes com memória limitada, como o Colab gratuito, o consumo de RAM é um fator limitante de escala.

### 3.6.6 Tamanho em Disco

**Definição:** tamanho total, em megabytes, dos arquivos que representam o índice ou a coleção persistidos em disco após a indexação: o arquivo de índice gravado pelo FAISS (`faiss.write_index`) e os diretórios de dados do Milvus Lite e do Chroma.

**Unidade:** megabytes (MB).

**Relevância:** determina custos de armazenamento e de leitura em pipelines de produção.

---

## 3.7 Protocolos de Confiabilidade

Para garantir rigor estatístico e reprodutibilidade, o experimento foi submetido aos seguintes protocolos:

### 3.7.1 Repetições Múltiplas (N=10)

Para cada cenário × banco × índice, o conjunto de consultas foi executado **10 vezes** sobre o índice já construído, precedidas de 1 execução de aquecimento (*warm-up*) descartada, com seed fixa (42) para dados e consultas.

**Justificativa:**
- Captura a variabilidade de medição de tempo entre execuções (escalonamento do sistema operacional, cache, coleta de lixo)
- Permite cálculo de intervalo de confiança 95% via t-distribuição (Student's t, df=9)
- Atende requisito explícito da banca por teste estatístico robusto
### 3.7.2 Intervalo de Confiança 95%

Para a latência média por consulta de cada configuração, calculada em cada uma das 10 execuções, determinou-se o intervalo de confiança:

$$IC_{95\%}(X) = \overline{X} \pm t_{0.025, 9} \cdot \frac{s_X}{\sqrt{10}}$$

onde:
- $\overline{X}$ = média amostral
- $s_X$ = desvio padrão amostral
- $t_{0.025, 9}$ = valor crítico da t-distribuição (df=9, bilateral) ≈ 2.262
**Interpretação:** há 95% de confiança de que a latência média populacional está nesse intervalo.

O Recall@k, por sua vez, é calculado uma única vez por configuração, sobre o índice construído e as 200 consultas fixas, e é reportado como valor único, sem intervalo de confiança.

### 3.7.3 Teste de Hipótese (Wilcoxon Signed-Rank)

Para validar se as diferenças de desempenho observadas entre bancos são estatisticamente significativas ou podem ser atribuídas a variações aleatórias, adotou-se o **teste de Wilcoxon Signed-Rank**. Trata-se de um teste estatístico **não-paramétrico** para amostras pareadas: compara duas séries de medições realizadas nas mesmas condições experimentais (ex.: as 10 latências por execução de FAISS vs. as 10 de Milvus no mesmo cenário, pareadas pelo índice da execução) e avalia se a distribuição das diferenças entre pares é sistematicamente deslocada de zero. Por ser não-paramétrico, **não assume que os dados seguem distribuição normal** — premissa frequentemente violada em medições de latência, que tendem a apresentar cauda pesada à direita (eventos ocasionais de lentidão extrema). O teste opera sobre os **ranques das diferenças absolutas** entre pares, sendo robusto a outliers.

Para comparações entre pares de bancos (ex.: latência por execução do FAISS vs. do Milvus), as hipóteses são:

$$H_0: \text{mediana}_{\text{FAISS}} = \text{mediana}_{\text{Milvus}}$$
$$H_1: \text{mediana}_{\text{FAISS}} \neq \text{mediana}_{\text{Milvus}}$$

**Justificativa de teste não-paramétrico:** distribuições de latência frequentemente violam normalidade (cauda pesada em p99); Wilcoxon não assume distribuição gaussiana.

**Limiar de significância:** α = 0.05 (p-value < 0.05 rejeita $H_0$)

### 3.7.4 Controle de Confundidores

- **Limpeza de cache:** entre cenários, garbage collection e destruição de objetos em memória
- **Isolamento de queries:** mesmas 200 consultas (`N_QUERIES = 200`) para todos os bancos em cada cenário; em S1, textos subsequentes do AG News não indexados no corpus; em S2 e S3, vetores sintéticos gerados com seeds próprias
- **Seeds:** `np.random.seed(42)`; corpora sintéticos com seed 42; consultas sintéticas com seed 43 (S2) e 44 (S3)
---

## 3.8 Ferramentas, Versões e Ambiente

### 3.8.1 Software

| Biblioteca | Versão fixada | Função no experimento |
|-----------|--------------|----------------------|
| Python | 3.12.13 | Ambiente de execução base |
| faiss-cpu | ≥ 1.9.0 | Indexação e busca vetorial; oráculo de ground truth |
| pymilvus | 2.5.8 | Cliente Milvus Lite; índices Flat e HNSW |
| chromadb | 0.6.3 | Banco vetorial com HNSW automático |
| sentence-transformers | 3.4.1 | Geração de embeddings all-MiniLM-L6-v2 |
| datasets | 3.2.0 | Carregamento do corpus AG News (S1) |
| numpy | 2.0.2 | Operações vetoriais e geração de sintéticos |
| psutil | 6.1.1 | Medição de consumo de RAM |
| scipy | 1.15.2 | IC 95% (t-distribuição) e teste Wilcoxon |
| matplotlib | ≥ 3.10.0 | Geração de figuras comparativas |
| seaborn | ≥ 0.13.2 | Heatmaps e visualizações estatísticas |
| tqdm | 4.67.1 | Barras de progresso na indexação |
| pandas | runtime¹ | Tabulação e exportação de resultados (CSV) |

¹ Pandas não foi fixado no `%pip install` — versão capturada em `ambiente_execucao.json` no momento da execução.

### 3.8.2 Ambiente de Execução (Google Colab)

| Recurso | Especificação |
|---------|--------------|
| Plataforma | Google Colab (CPU runtime gratuito) |
| CPU | Intel Xeon / AMD EPYC (varia por sessão) |
| RAM disponível | ~12,67 GB |
| Armazenamento | ~78 GB (Google Drive montado) |
| GPU | Nenhuma (CPU only) |
| Python | 3.12.13 |
| Sistema operacional | Linux 6.6.122+ |

**Justificativa de Colab gratuito:** garante reprodutibilidade pública (qualquer pesquisador pode replicar); limites de RAM e CPU refletem restrições práticas de recursos.

### 3.8.3 Repositório e Artefatos

- **Notebook:** `benchmark_rag_vectordbs.ipynb` (versão canônica, 45 células)
- **Dados:** corpus AG News (S1); vetores sintéticos (S2, S3)
- **Resultados:** CSVs por cenário (ex: `resultados_faiss_S1.csv`), figuras (PNG)
- **Código-fonte:** GitHub — https://github.com/BrenooOliveira/AulasFatec/blob/main/tcc/benchmark_rag_vectordbs.ipynb — com README, requirements.txt e licença MIT
- **Reprodutibilidade:** instruções dual (Colab + local)
---

# 4 DESENVOLVIMENTO E RESULTADOS

## 4.1 Protocolo de apresentação dos resultados

Este capítulo apresenta e discute os resultados do benchmark descrito no Capítulo 3. A fonte numérica principal é o arquivo `resultados_completos.csv`, que reúne uma configuração de busca aproximada por banco e cenário. Os arquivos detalhados são utilizados para preservar a comparação com as buscas exatas (`Flat_exato`) e com a configuração adicional `IVF707` do FAISS no cenário S3. Essa distinção é necessária porque o fator experimental não é apenas a marca do banco, mas a combinação entre solução, índice e parâmetros.

O experimento foi executado em três cenários, com 200 consultas por cenário e dez repetições de busca após uma execução de aquecimento descartada. O cenário S1 utiliza 10.000 embeddings reais derivados do corpus AG News; os cenários S2 e S3 utilizam, respectivamente, 100.000 e 500.000 vetores sintéticos normalizados. As latências são apresentadas em milissegundos por consulta, embora tenham sido medidas sobre lotes de 200 consultas. Portanto, elas não representam necessariamente a latência de uma consulta isolada em produção.

| Cenário | Vetores | Dados | FAISS | Milvus Lite | Chroma |
|---|---:|---|---|---|---|
| S1 | 10.000 | AG News e embeddings reais | HNSW-32 | HNSW-16 | HNSW automático |
| S2 | 100.000 | Sintéticos normalizados | HNSW-32 | HNSW-16 | HNSW automático |
| S3 | 500.000 | Sintéticos normalizados | HNSW-32 e IVF707 | HNSW-16 | HNSW automático |

As configurações HNSW e IVF são aproximadas. FAISS Flat é o oráculo de busca exata utilizado para construir o *ground truth*, enquanto Milvus Flat é uma referência exata adicional dentro do Milvus Lite. O Chroma concluiu a inserção em S1 e S2, mas atingiu `timeout_insercao` em S3; por isso, não há latência, QPS ou Recall@k válidos para esse caso.

## 4.2 Resultados do cenário S1 — 10 mil vetores

No cenário S1, todos os bancos concluíram a indexação e a busca. O FAISS HNSW-32 apresentou a menor latência mediana, de 0,0581 ms por consulta, e o maior throughput, de 17.486,4 consultas por segundo. O Milvus HNSW-16 apresentou p50 de 6,2066 ms e 156,4 QPS, enquanto o Chroma HNSW automático apresentou p50 de 7,8377 ms e 105,8 QPS.

| Banco e índice | Indexação (s) | RAM (MB) | Disco (MB) | p50 (ms) | p99 (ms) | QPS | Recall@1/@5/@10 |
|---|---:|---:|---:|---:|---:|---:|---|
| FAISS HNSW-32 | 1,41 | 0,3 | 17,2 | 0,0581 | 0,0600 | 17.486,4 | 0,980 / 0,995 / 1,000 |
| Milvus HNSW-16 | 7,94 | 222,6 | 15,4 | 6,2066 | 11,3456 | 156,4 | 0,970 / 1,000 / 1,000 |
| Chroma HNSW automático | 39,50 | 44,4 | 27,6 | 7,8377 | 15,7446 | 105,8 | 1,000 / 1,000 / 1,000 |

Os três resultados apresentam Recall@10 igual a 1,000 segundo a métrica implementada. Entretanto, esse valor indica apenas que cada consulta preservou pelo menos um dos dez vizinhos exatos no conjunto retornado, e não que todos os dez vizinhos foram recuperados. A diferença entre as soluções aparece principalmente na latência, no throughput e no custo de indexação. O FAISS HNSW-32 foi aproximadamente duas ordens de grandeza mais rápido que as alternativas avaliadas, mas essa vantagem deve ser interpretada juntamente com a diferença de abstração: FAISS é uma biblioteca de busca, enquanto Milvus Lite e Chroma incluem camadas de armazenamento e gerenciamento.

O resultado do S1 caracteriza o comportamento com embeddings textuais reais em um volume pequeno. Ele não deve ser usado, isoladamente, para inferir o comportamento nos cenários sintéticos de maior escala.

![Figura 4.1 — Desempenho de indexação, latência p50 e throughput dos bancos nos três cenários. Fonte: Elaborado pelo autor, 2026.](../results/fig1_desempenho.png)

## 4.3 Resultados do cenário S2 — 100 mil vetores

Com 100.000 vetores sintéticos normalizados, a diferença entre velocidade e preservação da vizinhança tornou-se mais evidente. O FAISS HNSW-32 manteve a menor latência, com p50 de 0,4799 ms e 2.139,2 QPS, mas obteve Recall@10 de apenas 0,395. O Chroma apresentou p50 de 11,7991 ms e Recall@10 de 0,685. O Milvus HNSW-16 alcançou Recall@10 igual a 1,000, porém com p50 de 31,7936 ms e 28,1 QPS.

| Banco e índice | Indexação (s) | RAM (MB) | Disco (MB) | p50 (ms) | p99 (ms) | QPS | Recall@1/@5/@10 |
|---|---:|---:|---:|---:|---:|---:|---|
| FAISS HNSW-32 | 179,92 | 146,6 | 172,4 | 0,4799 | 0,4995 | 2.139,2 | 0,045 / 0,215 / 0,395 |
| Milvus HNSW-16 | 251,54 | 531,4 | 150,1 | 31,7936 | 58,2442 | 28,1 | 1,000 / 1,000 / 1,000 |
| Chroma HNSW automático | 784,67 | 435,2 | 186,7 | 11,7991 | 19,3476 | 75,5 | 0,125 / 0,475 / 0,685 |

Os resultados detalhados ajudam a separar o efeito da estrutura de índice do efeito da solução. O FAISS Flat exato apresentou p50 de 1,7335 ms e Recall@10 igual a 1,000, enquanto o HNSW-32 reduziu a latência, mas perdeu parte da vizinhança de referência. No Milvus, o índice Flat exato apresentou p50 de 17,3536 ms, inferior ao HNSW-16 nesse cenário, com Recall@10 igual a 1,000. Assim, a configuração aproximada não foi automaticamente superior em todos os critérios: o HNSW preservou a qualidade, mas apresentou maior latência que o Flat nas condições medidas.

![Figura 4.2 — Recall@1, Recall@5 e Recall@10 dos bancos nos cenários avaliados. Fonte: Elaborado pelo autor, 2026.](../results/fig2_recall_k.png)

## 4.4 Resultados do cenário S3 — 500 mil vetores

O cenário S3 evidencia o compromisso entre latência, qualidade de recuperação e custo de operação. O FAISS HNSW-32 apresentou p50 de 0,8006 ms e 913,2 QPS, mas Recall@10 de 0,135. A configuração FAISS IVF707 foi ainda mais rápida, com p50 de 0,1815 ms e 5.467,24 QPS, porém seu Recall@10 foi 0,100. O Milvus HNSW-16 alcançou Recall@10 igual a 1,000, com p50 de 86,7920 ms e 11,6 QPS.

| Banco e índice | Indexação (s) | RAM (MB) | Disco (MB) | p50 (ms) | p99 (ms) | QPS | Recall@1/@5/@10 |
|---|---:|---:|---:|---:|---:|---:|---|
| FAISS HNSW-32 | 1.208,16 | 857,0 | 862,2 | 0,8006 | 2,1629 | 913,2 | 0,020 / 0,080 / 0,135 |
| FAISS IVF707 | 32,958 | 701,5 | 737,3 | 0,1815 | 0,2043 | 5.467,24 | 0,005 / 0,065 / 0,100 |
| Milvus HNSW-16 | 1.475,66 | 2.335,6 | 866,0 | 86,7920 | 92,5054 | 11,6 | 0,915 / 1,000 / 1,000 |
| FAISS Flat exato | 1,622 | 732,4 | 732,4 | 8,9815 | 18,6660 | 95,75 | 1,000 / 1,000 / 1,000 |
| Milvus Flat exato | 1.157,628 | 1.434,5 | 898,0 | 57,0858 | 65,6730 | 18,09 | 0,880 / 1,000 / 1,000 |

No FAISS, a redução de latência do IVF707 em relação ao Flat exato e ao HNSW-32 ocorreu acompanhada de Recall@10 inferior. Esse resultado é compatível com o uso de uma busca mais restrita no índice aproximado, mas não permite concluir que o IVF seja universalmente superior ou inferior: seus parâmetros devem ser ajustados conforme o requisito de qualidade. No Milvus, o HNSW apresentou maior latência que o Flat, embora ambos tenham preservado integralmente o top-10 segundo a métrica adotada.

O Chroma não concluiu a inserção dos 500.000 vetores dentro do limite operacional definido. O registro apresenta 1.810,98 s de indexação acumulada, 892,9 MB de RAM e 435,3 MB em disco até o timeout, mas esses valores não representam uma execução concluída e não devem ser comparados com uma busca válida. A ausência de p50, p99, QPS e Recall@k é, portanto, parte do resultado experimental: nas condições avaliadas, não foi possível completar o ciclo de ingestão e consulta.

![Figura 4.3 — Consumo de RAM e tamanho em disco durante a indexação. Fonte: Elaborado pelo autor, 2026.](../results/fig3_recursos.png)

## 4.5 Incerteza e análise estatística

Os intervalos de confiança foram calculados sobre as dez latências por configuração. No S1, o FAISS apresentou média de 0,0572 ms e IC95% entre 0,0555 e 0,0589 ms, enquanto o Milvus apresentou média de 6,3924 ms e IC95% entre 4,6149 e 8,1699 ms. No S2, os respectivos intervalos foram 0,4410–0,4939 ms para o FAISS, 28,4882–42,7243 ms para o Milvus e 10,9239–15,5793 ms para o Chroma.

No S3, a média do FAISS HNSW-32 foi 1,0950 ms, com IC95% entre 0,5550 e 1,6350 ms e coeficiente de variação de 68,94%. O Milvus HNSW-16 apresentou média de 86,5592 ms, IC95% entre 83,8578 e 89,2607 ms e coeficiente de variação de 4,36%. A maior variabilidade relativa do FAISS nesse cenário recomenda cautela ao resumir seu desempenho apenas pela mediana.

O teste de Wilcoxon pareado indicou diferenças estatisticamente significativas de latência entre os pares FAISS–Milvus, FAISS–Chroma e Milvus–Chroma em S1, com valores de p entre 0,002 e 0,0195. Em S2, os três pares também foram significativos, com p = 0,002. Em S3, a comparação FAISS–Milvus foi significativa, com p = 0,002; os pares envolvendo Chroma não puderam ser testados porque o Chroma não concluiu a inserção. Esses testes sustentam diferenças nas medições de latência, mas não demonstram superioridade em Recall@k, RAM, disco, tempo de indexação ou implantação distribuída.

![Figura 4.4 — Latência p50 e p99 por banco e cenário. Fonte: Elaborado pelo autor, 2026.](../results/fig4_latencia.png)

## 4.6 Discussão dos trade-offs

Os resultados mostram que não existe uma solução dominante em todos os critérios. O FAISS ofereceu as menores latências e os maiores throughputs entre as configurações HNSW comparadas, especialmente nos cenários S1 e S2. Em contrapartida, o Recall@10 do FAISS HNSW caiu para 0,395 em S2 e 0,135 em S3. O IVF707 foi ainda mais rápido em S3, mas apresentou Recall@10 de 0,100. Esses resultados caracterizam uma escolha adequada quando a prioridade é velocidade e quando a perda de vizinhança é aceitável ou pode ser compensada pelo ajuste de parâmetros.

O Milvus HNSW-16 preservou a vizinhança de referência nos cenários S2 e S3 segundo a taxa de acerto implementada, mas apresentou maior latência e maior consumo de RAM, particularmente em S3, com 2.335,6 MB. Essa combinação pode ser relevante quando a qualidade de recuperação é prioritária e há recursos computacionais disponíveis. A conclusão, entretanto, é restrita ao Milvus Lite local e aos parâmetros testados.

O Chroma apresentou indexação mais lenta que FAISS e Milvus nos cenários concluídos, mas teve latência inferior à do Milvus em S2 e superior à do FAISS. Seu Recall@10 foi 0,685 em S2, intermediário entre FAISS HNSW e Milvus HNSW. Em S3, o timeout de inserção revela uma limitação operacional observada no ambiente, sem permitir atribuir ao Chroma uma latência de consulta ou um Recall ausente.

O gráfico radar resume as métricas normalizadas apenas para S1 e S2. Ele é útil como visualização exploratória de perfis, mas não substitui a leitura das tabelas, não inclui o S3 e não estabelece uma função de utilidade única para decidir entre latência, qualidade, memória e armazenamento.

![Figura 4.5 — Perfil multidimensional normalizado dos bancos nos cenários S1 e S2. Fonte: Elaborado pelo autor, 2026.](../results/fig5_radar.png)

## 4.7 Síntese dos resultados e limites de interpretação

Em resposta parcial à questão norteadora, o FAISS apresentou a melhor eficiência de busca nas condições avaliadas, enquanto o Milvus HNSW apresentou maior preservação da vizinhança nos cenários sintéticos de maior escala. O Chroma ocupou uma posição intermediária nos cenários concluídos, mas não completou a ingestão em S3. Portanto, a seleção depende da prioridade entre latência, qualidade de recuperação, consumo de recursos e capacidade de concluir a ingestão.

Essa síntese não constitui uma avaliação completa de uma aplicação RAG. O experimento não inclui LLM, geração de respostas, avaliação factual, busca híbrida ou reranking. Além disso, S2 e S3 avaliam comportamento geométrico sobre vetores sintéticos, não qualidade semântica sobre um corpus textual real. As medições foram realizadas em CPU, sobre lotes de consultas, com memória RSS observada por `psutil`, e o Milvus foi executado em modo Lite local. As conclusões não devem ser generalizadas automaticamente para GPUs, outros modelos de embedding, corpora em português, consultas concorrentes ou clusters distribuídos.

# 5 REFERÊNCIAS

AUMULLER, Martin et al. ANN-Benchmarks: A benchmarking tool for approximate nearest neighbor algorithms. Information Systems, v. 87, p. 101374, 2020. DOI: 10.1016/j.is.2019.02.006. Disponível em: https://arxiv.org/abs/1807.05614. Acesso em: ago. 2026.

AWS PRESCRIPTIVE GUIDANCE. Overview of vector databases. AWS, 2023. Disponível em: https://docs.aws.amazon.com/prescriptive-guidance/latest/choosing-an-aws-vector-database-for-rag-use-cases/vector-databases.html. Acesso em: 28 abr. 2026.

CHROMA. Chroma documentation. [S.l.]: Chroma, 2023. Disponível em: https://docs.trychroma.com. Acesso em: 22 mar. 2026.

EVIDENTLY AI. Precision and recall at K in ranking and recommendations. Evidently AI, 9 jan. 2025. Disponível em: https://www.evidentlyai.com/ranking-metrics/precision-recall-at-k. Acesso em: 15 set. 2026.

FAISS. Faiss: a library for efficient similarity search. [S.l.]: Meta AI, 2021. Disponível em: https://faiss.ai. Acesso em: 28 mar. 2026.

GRAND VIEW RESEARCH. Vector database market size, share & trends analysis report. [S.l.]: Grand View Research, 2024. Disponível em: https://www.grandviewresearch.com/industry-analysis/vector-database-market-report. Acesso em: ago. 2026.

JI, Ziwei et al. Survey of hallucination in natural language generation. ACM Computing Surveys, New York, v. 55, n. 12, p. 1–38, mar. 2023. DOI: 10.1145/3571730. Disponível em: https://dl.acm.org/doi/10.1145/3571730. Acesso em: 28 mar. 2026.

LATIF, Seemab; AMEER, Huma; AKRAM, Muhammad Hannan; FATIMA, Mehwish. The Chunking Paradigm: Recursive Semantic for RAG Optimization. In: Proceedings of the 8th International Conference on Natural Language and Speech Processing (ICNLSP-2025), Odense, Denmark, Aug. 2025. Odense: Association for Computational Linguistics, 2025. p. 137–145. Disponível em: https://aclanthology.org/2025.icnlsp-1.15. Acesso em: 29 abr. 2026.

LEWIS, Patrick et al. Retrieval-augmented generation for knowledge-intensive NLP tasks. In: Advances in Neural Information Processing Systems, 33., 2020, [s.l.]: NeurIPS, 2020. Disponível em: https://arxiv.org/abs/2005.11401. Acesso em: 20 mar. 2026.

MALKOV, Yu. A.; YASHUNIN, D. A. Efficient and robust approximate nearest neighbor search using Hierarchical Navigable Small World Graphs. IEEE Transactions on Pattern Analysis and Machine Intelligence, v. 42, n. 4, p. 824–836, 2020. DOI: 10.1109/tpami.2018.2889473. Disponível em: https://arxiv.org/abs/1603.09320. Acesso em: ago. 2026.

MILVUS. Milvus documentation. [S.l.]: LF AI & Data Foundation, 2021. Disponível em: https://milvus.io/docs. Acesso em: 28 mar. 2026.

REIMERS, Nils; GUREVYCH, Iryna. Sentence-BERT: Sentence Embeddings using Siamese BERT-Networks. In: Proceedings of the 2019 Conference on Empirical Methods in Natural Language Processing and the 9th International Joint Conference on Natural Language Processing (EMNLP-IJCNLP), Hong Kong, China, 2019. [S.l.]: Association for Computational Linguistics, 2019. p. 3982–3992. Disponível em: https://aclanthology.org/D19-1410/. Acesso em: 12 out. 2026.

SHIVANANDHAN, Manish. How does cosine similarity work? The math behind LLMs explained. freeCodeCamp, 18 set. 2025. Disponível em: https://www.freecodecamp.org/news/how-does-cosine-similarity-work/. Acesso em: 15 set. 2026.

WOLFF, Hayden. RAG 101: Demystifying Retrieval-Augmented Generation Pipelines. NVIDIA Developer Blog, dez. 2023. Disponível em: https://developer.nvidia.com/blog/rag-101-demystifying-retrieval-augmented-generation-pipelines/. Acesso em: ago. 2026.
