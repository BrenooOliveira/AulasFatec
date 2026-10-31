# SecureDocTec — protótipo visual

Este diretório reúne a prova de conceito visual para a proposta de segurança de documentos acadêmicos da Fatec.

## Páginas incluídas

- index.html — visão geral e introdução
- emissao.html — simulação da geração do documento e da assinatura
- validacao.html — verificação de um documento conforme
- adulteracao.html — cenário de documento alterado
- resumo.html — benefícios, limitações e conclusão

## Como executar

A forma mais simples é abrir os arquivos diretamente no navegador, ou iniciar um servidor local:

```bash
cd /home/breno-oliveira/Documentos/gitRepositories/AulasFatec/seguranca_informacao/securedoctec/proto
python3 -m http.server 8000
```

Depois, abra `http://localhost:8000` no navegador.

## Como usar

1. Abra a página de emissão e gere um documento.
2. Na página de validação, use o conteúdo original para confirmar a autenticidade.
3. Na página de adulteração, altere o conteúdo para demostrar a falha de integridade.

## Observação

Esta é uma demonstração didática, com uso de Web Crypto API para simular hash SHA-256, AES-256-GCM e assinatura digital em um contexto acadêmico.
