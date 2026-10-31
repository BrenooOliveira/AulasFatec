# SecureDocTec
## Solução de Segurança Criptográfica para Documentos Acadêmicos
**Integrante 3: Arquitetura da solução**

### 1. Objetivo desta seção
Esta seção descreve, em nível conceitual, como o SecureDocTec funcionaria: os componentes da arquitetura, os perfis de usuário e suas permissões, os fluxos de emissão e de validação de um documento, a forma como o documento é cifrado e assinado, e os cuidados com armazenamento, transmissão, recuperação de chaves e auditoria. Não se trata de uma implementação: o foco é mostrar como os mecanismos criptográficos definidos pelo grupo se encaixam em um fluxo operacional coerente.

### 2. Visão geral da arquitetura
A solução é organizada em módulos com responsabilidades bem separadas. A separação segue o princípio de que nenhum componente isolado deve conseguir, sozinho, emitir um documento falso ou ler o conteúdo de todos os documentos.

```text
 [Titular]          [Secretaria]           [Verificador externo]
     |                   |                          |
     v                   v                          v
 +-----------------------------------------------------------+
 |     Portal / API do SecureDocTec  (canal TLS 1.3)        |
 +-----------------------------------------------------------+
    |            |                |                 |
    v            v                v                 v
 [Autenticação] [Emissão e    [Validação]        [Auditoria]
  e controle    cifragem]     (verificação       (log com
  de acesso]       |           de assinatura)    encadeamento)
                   |                |
          +--------+--------+       |
          v                 v       v
 [Serviço de assinatura] [Repositório de documentos]
   (chave privada em      (somente envelopes cifrados)
    HSM, sem exportação)          |
                                  v
                        [Gestão de chaves (KMS)]
                        KEKs, DEKs encapsuladas,
                        custódia de chaves de recuperação
```

A tabela a seguir resume a responsabilidade de cada componente.

| Componente | Responsabilidade | Entradas | Saídas |
| :--- | :--- | :--- | :--- |
| Portal / API | Ponto de entrada único; aplica autenticação, autorização e TLS. | Requisições de usuários e sistemas | Respostas autorizadas |
| Autenticação e controle de acesso | Verifica identidade (com MFA para perfis administrativos) e aplica permissões por perfil. | Credenciais, papel do usuário | Token de sessão de curta duração |
| Emissão e cifragem | Gera o hash, solicita a assinatura, cifra o documento e grava o envelope. | Dados do documento acadêmico | Envelope cifrado e assinado |
| Serviço de assinatura | Assina o hash do documento com a chave privada institucional, dentro de um HSM. | Hash SHA-256 | Assinatura digital |
| Repositório | Armazena somente envelopes cifrados e metadados não sensíveis. | Envelopes | Envelopes sob consulta autorizada |
| Gestão de chaves (KMS) | Gera, guarda, encapsula, rotaciona e revoga chaves; controla a custódia de recuperação. | Pedidos de chave e de rotação | Chaves encapsuladas, estados de chave |
| Validação | Verifica documento apresentado contra a assinatura e o status da chave. | Documento e código de validação | Resultado: válido, alterado, inválido ou revogado |
| Auditoria | Registra eventos em log append-only com encadeamento de hashes. | Eventos de todos os módulos | Trilha consultável por auditores |

### 3. Perfis de usuário e permissões
Foram definidos cinco perfis. A separação de funções (separation of duties) é um princípio central: quem emite não gerencia as chaves, quem gerencia as chaves não lê os documentos e quem audita apenas consulta.

| Perfil | Descrição | Pode | Não pode |
| :--- | :--- | :--- | :--- |
| Titular (aluno) | Pessoa a quem o documento pertence. | Solicitar emissão; ver e baixar os próprios documentos; gerar código de validação para compartilhar. | Ver documentos de terceiros; alterar registros emitidos. |
| Emissor (secretaria) | Servidor responsável por emitir documentos. | Emitir e cifrar documentos; solicitar assinatura. | Acessar chaves privadas; alterar logs; emitir sem autenticação forte. |
| Verificador externo | Empresa, instituição ou pessoa que precisa validar um documento. | Validar um documento apresentado e consultar o resultado. | Acessar o repositório ou o conteúdo de documentos não apresentados por ele. |
| Custodiante de chaves | Administrador de segurança responsável pelo ciclo de vida das chaves. | Rotacionar, revogar e participar da recuperação de chaves (com quórum). | Ler documentos; emitir documentos. |
| Auditor | Responsável pela conferência da conformidade. | Consultar logs e relatórios em modo somente leitura. | Alterar qualquer registro ou chave. |

O controle de acesso aos documentos deve ser feito em dois níveis: a autorização para obter o envelope (quem pode pedir o arquivo) e a capacidade de decifrá-lo (quem consegue recuperar a chave de dados). Mesmo que um atacante obtenha um envelope, sem a chave de encapsulamento ele não consegue ler o conteúdo.

### 4. Fluxo de emissão
O fluxo a seguir descreve a emissão de um documento, como um histórico escolar, a partir do sistema acadêmico.

* **Passo 1** — O emissor autentica-se com MFA e o sistema verifica seu papel.
* **Passo 2** — O sistema acadêmico fornece os dados do documento (D), com identificador único, versão, data e identificação do emissor.
* **Passo 3** — É calculado o hash H = SHA-256(D).
* **Passo 4** — O serviço de assinatura assina H com a chave privada institucional, dentro do HSM, gerando a assinatura σ.
* **Passo 5** — É gerada uma chave de dados (DEK) aleatória de 256 bits, exclusiva para este documento, e um nonce N aleatório de 96 bits.
* **Passo 6** — O documento é cifrado com AES-256-GCM, produzindo o texto cifrado C e a tag de autenticação T. Os metadados (identificador, versão e emissor) entram como dados associados autenticados (AAD), ou seja, são protegidos contra alteração sem serem cifrados.
* **Passo 7** — A DEK é encapsulada (wrap) com uma chave de encapsulamento (KEK) do repositório, usando AES Key Wrap. A DEK em claro é descartada da memória.
* **Passo 8** — O envelope {C, T, N, DEK encapsulada, σ, metadados} é gravado no repositório, e o evento é registrado na auditoria.
* **Passo 9** — O titular é notificado de que o documento está disponível para acesso autenticado. A notificação contém um código de validação, nunca o conteúdo do documento.

### 5. Fluxo de validação
A validação pode ser feita por um verificador externo, que não tem acesso ao repositório. Nesse caso, o próprio titular apresenta uma cópia do documento.

* **Passo 1** — O verificador envia ao serviço de validação o código de validação e o documento apresentado pelo titular.
* **Passo 2** — O serviço localiza os metadados públicos do documento (identificador, chave pública utilizada e assinatura σ), sem expor o conteúdo.
* **Passo 3** — O serviço calcula H' = SHA-256(documento apresentado).
* **Passo 4** — A assinatura σ é verificada com a chave pública institucional sobre H'. Se a assinatura for válida, a integridade e a origem são confirmadas.
* **Passo 5** — É consultado o estado da chave (ativa, rotacionada ou revogada) na data de emissão.
* **Passo 6** — O resultado é apresentado ao verificador: válido, documento alterado (H' diferente de H), assinatura inválida, chave revogada ou código inexistente.

### 6. Processo de cifragem e decifragem

#### 6.1 Ordem entre assinatura e cifragem
Uma decisão de arquitetura importante é a ordem das operações. Optou-se por assinar o documento em claro e depois cifrá-lo, e não o contrário, pelos seguintes motivos:
* A assinatura sobre o hash do conteúdo original permite que um verificador externo confirme a integridade sem precisar da chave de decifragem, que nunca sai do sistema.
* A assinatura representa o conteúdo que a instituição efetivamente emitiu, o que sustenta a autenticidade e a não repudiação.
* A tag do GCM protege o envelope armazenado contra alteração, e a assinatura protege o documento quando ele circula fora do sistema. As duas proteções são complementares, não redundantes.

#### 6.2 Decifragem pelo titular ou por um agente autorizado
* **Passo 1** — O sistema verifica a autorização do solicitante.
* **Passo 2** — A DEK é recuperada desembrulhando-a com a KEK (no KMS).
* **Passo 3** — O texto cifrado é decifrado com AES-256-GCM. Se a tag T não conferir, a operação é abortada e o evento é registrado como possível adulteração. Nenhum conteúdo parcial é entregue.
* **Passo 4** — Recalcula-se H = SHA-256(D) e verifica-se σ com a chave pública institucional, como garantia final.
* **Passo 5** — O documento é entregue ao solicitante e o acesso é registrado na auditoria.

### 7. Gestão, armazenamento, transmissão e recuperação de chaves
A segurança da solução depende mais da gestão das chaves do que dos algoritmos em si. A tabela resume as regras propostas para cada tipo de chave.

| Chave | Função | Armazenamento | Ciclo de vida |
| :--- | :--- | :--- | :--- |
| Chave privada de assinatura | Assinar o hash dos documentos. | Dentro de HSM, sem possibilidade de exportação. | Rotação periódica definida na política; a chave antiga é mantida apenas para verificação. |
| Chave pública de assinatura | Verificar assinaturas. | Publicada no serviço de validação, com certificado. | Acompanha a chave privada correspondente. |
| DEK (por documento) | Cifrar o conteúdo de um documento. | Nunca em claro; sempre encapsulada pela KEK. | Gerada na emissão e descartada após o uso. |
| KEK | Encapsular as DEKs. | No KMS, em módulo protegido. | Rotacionada periodicamente; DEKs são reencapsuladas. |
| Chave de recuperação | Permitir recuperar a KEK em caso de perda. | Dividida em partes (esquema de limiar k de n) entre custodiantes distintos. | Só é reconstruída por quórum e em procedimento registrado. |

**Transmissão.** Toda comunicação entre usuários, portal, serviços e repositório ocorre sobre TLS 1.3. Isso protege a confidencialidade e a integridade em trânsito, mas não substitui a proteção do próprio documento: o envelope continua cifrado mesmo dentro da rede interna.

**Recuperação.** A perda de uma KEK tornaria inacessíveis todos os documentos protegidos por ela. Por isso, a KEK é protegida por um esquema de compartilhamento de segredo, no qual um número mínimo de custodiantes precisa participar para reconstruí-la. Assim, nenhuma pessoa isolada pode recuperar ou acessar os dados.

**Revogação.** Se a chave de assinatura for comprometida, ela é marcada como revogada no serviço de validação. Documentos assinados antes da data de comprometimento podem permanecer válidos, desde que a data de emissão possa ser comprovada; essa regra deve constar na política da instituição.

### 8. Auditoria
Todos os eventos relevantes são registrados: solicitações de emissão, emissões, acessos a documentos, validações (com resultado), falhas de verificação de tag, operações sobre chaves e tentativas de acesso negadas. Os registros são gravados em log append-only, em que cada entrada inclui o hash da entrada anterior. Com isso, qualquer remoção ou alteração de registros torna a cadeia inconsistente e pode ser detectada. Os logs não contêm conteúdo de documentos nem chaves.

### 9. Riscos arquiteturais e respostas propostas
A tabela a seguir relaciona os riscos mais relevantes da arquitetura com as medidas previstas. Os detalhes criptográficos desses riscos são tratados na seção de criptografia e gestão de chaves.

| Risco | Medida prevista na arquitetura |
| :--- | :--- |
| Comprometimento da chave privada de assinatura | Guarda em HSM sem exportação, controle de quórum para uso, rotação e revogação com registro de data. |
| Acesso indevido às KEKs | KMS separado do repositório, com acesso restrito ao serviço de decifragem e auditoria de cada uso. |
| Reuso de nonce no AES-GCM | Uma DEK por documento e nonce aleatório de 96 bits; limite de invocações por chave conforme NIST SP 800-38D. |
| Alteração do envelope armazenado | Verificação da tag GCM antes de qualquer entrega e verificação da assinatura na decifragem. |
| Documento impresso ou copiado e apresentado como original | Validação por código e assinatura: uma cópia alterada gera hash diferente e falha na verificação. |
| Adulteração ou apagamento de logs | Log com encadeamento de hashes e acesso somente leitura para auditores. |
| Perda de chave de recuperação | Divisão do segredo entre custodiantes com limiar, em procedimento documentado. |

### 10. Limitações conceituais da arquitetura
A arquitetura proposta não resolve todos os problemas. Ela depende de que a chave pública institucional seja obtida por um canal confiável; se um atacante substituir essa chave, ele poderá produzir assinaturas aparentemente válidas. Também não protege o documento contra fraude na origem dos dados, ou seja, se o sistema acadêmico já tiver registrado informações falsas, a assinatura apenas comprovará que a instituição as emitiu. Por fim, a solução não cobre o que acontece após a entrega do documento ao titular. Essas limitações serão retomadas na avaliação de riscos residuais.

### Referências
* BARKER, E. Recommendation for Key Management: Part 1 – General. NIST Special Publication 800-57 Part 1, Rev. 5. Gaithersburg: NIST, 2020.
* BERNSTEIN, D. J. et al. High-speed high-security signatures. Journal of Cryptographic Engineering, v. 2, n. 2, p. 77–89, 2012.
* BRASIL. Lei nº 14.063, de 23 de setembro de 2020. Dispõe sobre o uso de assinaturas eletrônicas em interações com entes públicos. Diário Oficial da União: Brasília, DF, 2020.
* BRASIL. Medida Provisória nº 2.200-2, de 24 de agosto de 2001. Institui a Infraestrutura de Chaves Públicas Brasileira – ICP-Brasil. Brasília, DF, 2001.
* COOPER, D. et al. Internet X.509 Public Key Infrastructure Certificate and Certificate Revocation List (CRL) Profile. RFC 5280. IETF, 2008.
* DWORKIN, M. J. Recommendation for Block Cipher Modes of Operation: Galois/Counter Mode (GCM) and GMAC. NIST Special Publication 800-38D. Gaithersburg: NIST, 2007.
* DWORKIN, M. J. Recommendation for Block Cipher Modes of Operation: The KW and KWP Modes (AES Key Wrap). NIST Special Publication 800-38F. Gaithersburg: NIST, 2012.
* FERGUSON, N.; SCHNEIER, B.; KOHNO, T. Cryptography Engineering: Design Principles and Practical Applications. Indianapolis: Wiley, 2010.
* INTERNATIONAL ORGANIZATION FOR STANDARDIZATION. ISO/IEC 27001:2022 – Information security, cybersecurity and privacy protection: Information security management systems – Requirements. Geneva: ISO, 2022.
* INTERNATIONAL ORGANIZATION FOR STANDARDIZATION. ISO/IEC 27002:2022 – Information security controls. Geneva: ISO, 2022.
* JOSEFSSON, S.; LIUSVAARA, I. Edwards-Curve Digital Signature Algorithm (EdDSA). RFC 8032. IETF, 2017.
* MCGREW, D. An Interface and Algorithms for Authenticated Encryption. RFC 5116. IETF, 2008.
* MCGREW, D. A.; VIEGA, J. The security and performance of the Galois/Counter Mode (GCM) of operation. In: Progress in Cryptology – INDOCRYPT 2004. Berlin: Springer, 2004.
* MENEZES, A. J.; VAN OORSCHOT, P. C.; VANSTONE, S. A. Handbook of Applied Cryptography. Boca Raton: CRC Press, 1996.
* NATIONAL INSTITUTE OF STANDARDS AND TECHNOLOGY. FIPS PUB 180-4: Secure Hash Standard (SHS). Gaithersburg: NIST, 2015.
* NATIONAL INSTITUTE OF STANDARDS AND TECHNOLOGY. FIPS PUB 186-5: Digital Signature Standard (DSS). Gaithersburg: NIST, 2023.
* NATIONAL INSTITUTE OF STANDARDS AND TECHNOLOGY. FIPS PUB 197: Advanced Encryption Standard (AES). Gaithersburg: NIST, 2001.
* RESCORLA, E. The Transport Layer Security (TLS) Protocol Version 1.3. RFC 8446. IETF, 2018.
* SHAMIR, A. How to share a secret. Communications of the ACM, v. 22, n. 11, p. 612–613, 1979.
* STALLINGS, W. Criptografia e segurança de redes: princípios e práticas. 6. ed. São Paulo: Pearson, 2015.
