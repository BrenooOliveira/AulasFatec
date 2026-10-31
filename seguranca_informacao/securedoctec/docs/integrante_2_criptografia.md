# Criptografia e gestão de chaves

## 1. Fundamentação da Criptografia Simétrica
A criptografia simétrica (ou criptografia de chave secreta) utiliza um único segredo compartilhado para realizar tanto a operação de cifragem (transformação do dado legível em texto cifrado) quanto a de decifragem (restauração ao formato original).

**Funcionamento Detalhado**
* **Geração e Distribuição:** O remetente e o destinatário negociam ou derivam uma chave secreta idêntica por meio de um canal seguro (ex: protocolo ECDH).
* **Cifragem no Remetente:** O remetente aplica a chave secreta e um vetor de inicialização (IV é um bloco de dados aleatório ou não repetido passado junto com a chave secreta ao iniciar uma operação de criptografia simétrica. Sua função principal é impedir o determinismo: garantir que, se a mesma mensagem for criptografada duas vezes com a mesma chave, o texto cifrado gerado seja completamente diferente em cada execução.) ao algoritmo simétrico para cifrar o bloco ou fluxo de dados.
* **Decifragem no Destinatário:** O destinatário utiliza a mesma chave secreta para inverter as operações matemáticas do algoritmo e recuperar a informação original.

**Vantagens e Limitações**
* **Desempenho Criptográfico:** Alta taxa de transferência de dados com baixo consumo de processamento e latência reduzida, viabilizada pela aceleração por hardware moderna (como o conjunto de instruções AES-NI em processadores x86/ARM).
* **Eficiência em Larga Escala:** Ideal para proteger grandes volumes de dados persistidos (Data at Rest) ou fluxos contínuos de rede (Data in Transit).
* **Desafio de Gestão:** O principal ponto crítico reside na distribuição e armazenamento seguro da chave: se a chave for comprometida, toda a confidencialidade do sistema é anulada.

## 2. O Algoritmo AES-256
O AES (Advanced Encryption Standard) é uma cifra de bloco simétrica adotada globalmente como padrão de segurança.

* **Tamanho do Bloco:** Opera em blocos fixos de 128 bits (16 bytes), organizados internamente em uma matriz $4 \times 4$ de bytes.
* **Tamanho da Chave (256 bits):** O sufixo 256 especifica o comprimento da chave criptográfica. Chaves de 256 bits exigem um espaço de busca exaustiva de $2^{256}$ combinações possíveis, tornando o algoritmo imune a ataques de força bruta computacional atual.
* **Rodadas de Substituição e Permutação:** Para chaves de 256 bits, o AES executa 14 rodadas de transformações matemáticas complexas (SubBytes, ShiftRows, MixColumns e AddRoundKey).

## 3. O Modo GCM (Galois/Counter Mode) e a Cifragem AEAD
Utilizar apenas o AES puro (ou modos antigos como ECB e CBC) garante confidencialidade, mas não previne modificações maliciosas nos dados em trânsito. O modo GCM resolve este problema ao transformar o AES em um algoritmo AEAD (Authenticated Encryption with Associated Data).

| Componente Interno do GCM | Função Técnica |
| :--- | :--- |
| Modo Contador (CTR) | Converte a cifra de bloco em uma cifra de fluxo. Cifra o texto limpo realizando uma operação XOR com um bloco gerador alimentado por uma chave e um Nonce/IV incremental. |
| Multiplicação em Campo de Galois (GMAC) | Computa um código de autenticação de mensagem (Authentication Tag) de 128 bits sobre o texto cifrado e sobre os dados associados. |
| Dados Associados (AAD) | Permite autenticar dados que precisam trafegar em texto claro (como cabeçalhos de rede, metadados de protocolo ou IDs de pacote) sem cifrá-los, garantindo que não sejam adulterados. |

**Por que o AES-256-GCM é o mecanismo ideal para a proposta?**
* **Confidencialidade + Autenticidade Simultâneas:** O receptor valida a integridade do pacote antes de decifrar o conteúdo. Se um único bit do texto cifrado for alterado, a validação do Tag falha e o pacote é rejeitado imediatamente.
* **Paralelização:** O modo CTR e a aritmética no Campo de Galois permitem processamento paralelo em CPUs multi-core, entregando velocidades superiores ao modo CBC.

## 4. Diferenciação das Primitivas e Assinaturas Digitais (SHA-256, Ed25519 e RSA)
Para garantir a segurança completa da aplicação, o sistema combina três mecanismos criptográficos complementares com finalidades distintas:

| Primitiva | Chaves Utilizadas | Propriedade Garantida | Aplicação no Sistema |
| :--- | :--- | :--- | :--- |
| Criptografia Simétrica (AES-256-GCM) | Uma única chave secreta compartilhada. | Confidencialidade e Autenticidade (AEAD). | Cifragem do payload da aplicação e dados persistidos. |
| Função Hash (SHA-256) | Nenhuma chave (matemática determinística). | Integridade dos dados. | Resumo criptográfico de arquivos e validação de payloads. |
| Assinatura Digital (Ed25519 / RSA) | Par assimétrico (Privada assina, Pública verifica). | Autenticidade e Não-Repúdio. | Assinatura de requisições, tokens e auditoria de autoria. |

O SHA-256 converte qualquer entrada de dados de tamanho arbitrário em um digest fixo de 256 bits (32 bytes). Ele possui duas propriedades essenciais para a arquitetura:
* **Resistência à Pré-Imagem (Unidirecionalidade):** É computacionalmente impossível reverter o hash para obter os dados originais.
* **Resistência a Colisões:** É inviável encontrar duas entradas diferentes que produzam o mesmo hash.

**Papel no Fluxo:** Assinar arquivos longos com criptografia assimétrica exige alto custo de processamento. O sistema calcula primeiro o hash SHA-256 do dado e assina apenas esse resumo de 32 bytes, otimizando o desempenho sem perda de segurança.

As assinaturas digitais garantem o não-repúdio: apenas o detentor da chave privada pode ter assinado o documento, enquanto qualquer entidade com acesso à chave pública pode verificar a autenticidade.

| Parâmetro Técnico | RSA-2048 / RSA-4096 | Ed25519 (EdDSA sobre Curve25519) |
| :--- | :--- | :--- |
| Base Matemática | Fatoração de inteiros primos gigantes. | Criptografia de Curvas Elípticas (ECC). |
| Tamanho da Chave Pública | 256 a 512 bytes. | 32 bytes. |
| Tamanho da Assinatura | 256 a 512 bytes. | 64 bytes. |
| Resistência a Side-Channel | Exige padding complexo (RSA-PSS) para mitigar ataques de timing. | Imune por design (tempo de execução constante). |
| Desempenho | Lento na geração de chaves e assinatura. | Ultra-rápido na assinatura e verificação. |

**Justificativa de Escolha:** O projeto adota o algoritmo Ed25519. Suas chaves e assinaturas ultra-compactas reduzem o consumo de banda e tornam o processamento de requisições mais rápido, além de oferecer proteção nativa contra ataques de análise de tempo (side-channel attacks).

## 5. Gestão e Ciclo de Vida das Chaves (KMS)
A segurança do AES-256-GCM e do Ed25519 depende inteiramente do controle sobre as chaves criptográficas. O ciclo de vida é estruturado em quatro etapas:

* **Geração Segura:** Todas as chaves simétricas (256 bits) e pares assimétricos são gerados utilizando CSPRNG (Cryptographically Secure Pseudo-Random Number Generator), garantindo entropia máxima proveniente do sistema operacional (ex: `/dev/urandom` ou `java.security.SecureRandom`).
* **Armazenamento e Envelope Encryption (Cifragem em Envelope):** Nenhuma chave é armazenada em texto limpo no código-fonte ou em arquivos de configuração. Utiliza-se a técnica de Envelope Encryption: os dados da aplicação são cifrados com uma Data Encryption Key (DEK). A DEK, por sua vez, é cifrada por uma Key Encryption Key (KEK / Master Key) mantida em um ambiente isolado de hardware/software como HashiCorp Vault, AWS KMS ou HSM (Hardware Security Module).
* **Distribuição em Trânsito:** A troca de chaves de sessão entre cliente e servidor é realizada via protocolo TLS 1.3 ou negociação de chaves ECDH (Elliptic Curve Diffie-Hellman), garantindo Forward Secrecy (se a chave mestra for comprometida no futuro, as sessões passadas continuam indecifráveis).
* **Proteção, Rotação e Revogação:**
  * *Política de Rotação:* As DEKs são rotacionadas automaticamente a cada 90 dias ou ao atingir o limite de dados cifrados ($2^{32}$ blocos).
  * *Princípio do Menor Privilégio:* O acesso às operações do KMS é restrito via IAM com controle de acesso baseado em funções (RBAC) e auditoria por logs imutáveis.

## 6. Análise e Mitigação de Riscos Criptográficos

| Ameaça / Risco | Impacto Técnico e Operacional | Estratégia de Mitigação Adotada |
| :--- | :--- | :--- |
| Reutilização de Nonce (IV) no AES-GCM | CRÍTICO: Se o mesmo Nonce (IV) for usado duas vezes com a mesma chave no modo GCM, um atacante pode realizar operações XOR entre as saídas, revelando o texto limpo e derivando a chave de autenticação (GHASH). | Uso de Nonces de 96 bits gerados via CSPRNG para cada operação de cifragem, com validação no KMS para impedir duplicação no mesmo ciclo de chave. |
| Vazamento de Chaves Simétricas | ALTO: A exposição da chave AES permite a decifragem de dados passados e a falsificação de novos payloads cifrados. | Aplicação de Envelope Encryption, isolamento de memória durante a cifragem e armazenamento exclusivo em cofres de segredos (KMS/Vault) sem exportação de chaves puras. |
| Comprometimento da Chave Privada (Ed25519) | CRÍTICO: Invasores podem assinar transações e requisições falsas se passando por usuários legítimos, quebrando o não-repúdio. | Armazenamento de chaves privadas dentro de módulos HSM/KMS com restrição de exportação, e implementação de infraestrutura de revogação imediata via listas CRL/OCSP. |
