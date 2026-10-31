# SecureDocTec

## 1. Introdução e contextualização
A transformação digital nas instituições de ensino modernizou a emissão e o compartilhamento de documentos, como históricos escolares, diplomas e certificados. Na prática, esses documentos passam por várias mãos: saem da secretaria, vão para o aluno e chegam a empresas ou outras faculdades que precisam checar se as informações são verdadeiras. O problema é que compartilhar um PDF comum ou uma via impressa abre brechas para fraudes e vazamentos. Sem segurança adequada, é difícil comprovar que um documento digital apresentado por um aluno foi realmente emitido pela instituição de ensino e não sofreu alterações posteriores. O projeto SecureDocTec surge para preencher esse espaço, atuando como um portal seguro para gerenciar esse fluxo.

## 2. O problema de segurança e justificativa
O problema central que o SecureDocTec propõe mitigar é a falsificação de documentos acadêmicos e o acesso não autorizado a dados dos estudantes. Sistemas tradicionais frequentemente falham ao separar a camada de armazenamento da camada de verificação. Se um verificador externo precisa checar a validade de um diploma, ele não deve ter acesso ao banco de dados ou ao repositório completo da instituição. A justificativa para este projeto é a necessidade de um sistema que permita a verificação da validade de um documento sem expor o conteúdo de outros registros, e que mesmo em caso de vazamento do repositório, os dados permaneçam inacessíveis (ilegíveis) para o atacante.

## 3. Princípios fundamentais de segurança
Para que a solução seja confiável, ela deve atender a quatro pilares da segurança da informação:
* **Confidencialidade:** garantir que o conteúdo do documento acadêmico seja legível apenas por pessoas autorizadas (o titular, o emissor e o verificador que possuir a autorização). No sistema, isso é garantido pelo armazenamento exclusivo de envelopes cifrados no repositório.
* **Integridade:** assegurar que o documento não foi modificado após sua emissão. Qualquer alteração em um único bit do documento deve invalidá-lo.
* **Autenticidade e comprovação de origem:** comprovar a origem do documento. A instituição não pode negar que emitiu um documento legítimo, e um atacante não pode forjar a identidade da instituição.
* **Disponibilidade:** garantir que titulares possam acessar seus documentos e que verificadores possam validá-los sempre que necessário, mantendo o serviço e as chaves de recuperação seguras e disponíveis mediante quórum.

## 4. Modelo de ameaças inicial e vulnerabilidades hipotéticas
O desenvolvimento da arquitetura inicia na identificação de ameaças aos fluxos de emissão, armazenamento e validação.

| Ameaça / Cenário | Vulnerabilidade Hipotética | Requisito de Segurança para Mitigação |
| :--- | :--- | :--- |
| Falsificação de histórico | Edição de um PDF baixado pelo aluno para inflar notas antes de enviá-lo a uma empresa. | Assinatura digital (Autenticidade/Integridade): assinar o hash do documento (SHA-256) com a chave privada institucional. A verificação falhará se o conteúdo for alterado. |
| Vazamento do repositório | Um invasor ou funcionário mal-intencionado copia todos os arquivos do banco de dados da instituição. | Cifragem e gestão de chaves (Confidencialidade): armazenar apenas envelopes cifrados (AES-256-GCM). Exigir chaves (DEK e KEK) armazenadas em um KMS separado para decifragem. |
| Emissão fraudulenta | Um funcionário da secretaria emite um diploma falso para um não-aluno usando credenciais. | Controle de acesso e auditoria: exigir autenticação multifator (MFA) para emissores. Manter logs de auditoria imutáveis (append-only com encadeamento). |
| Interceptação em trânsito | Captura de pacotes na rede durante o envio do documento ao titular. | Criptografia em trânsito: toda a comunicação deve ocorrer obrigatoriamente em canais seguros (TLS 1.3). |
| Cópia e reuso | Um documento original é impresso ou copiado por terceiros. | Validação criptográfica: o sistema exige um código de validação e a verificação do hash do documento apresentado contra a assinatura e o status da chave no sistema. |

## 5. Objetivos
A partir do contexto e das ameaças levantadas, o projeto estabelece os seguintes objetivos:

**Objetivo geral:**
Projetar e especificar a arquitetura e os mecanismos criptográficos do SecureDocTec, uma solução segura para emissão, armazenamento criptografado e validação de documentos acadêmicos, reduzindo riscos de fraude e vazamento de dados.

**Objetivos específicos:**
* Definir e justificar os algoritmos criptográficos (como AES-256-GCM, SHA-256 e Ed25519) que garantirão a confidencialidade, integridade e autenticidade dos arquivos.
* Estruturar um modelo de arquitetura baseado na segregação de funções, onde quem emite, gerencia chaves, audita e verifica possuem permissões e fluxos isolados.
* Estabelecer um ciclo de vida rigoroso para a gestão de chaves criptográficas (KMS), impedindo o comprometimento do sistema por vazamento de chaves ou reutilização de nonce.
* Projetar um mecanismo de validação externa que ateste a validade de um documento apresentado sem expor o repositório acadêmico a acessos não autorizados.
