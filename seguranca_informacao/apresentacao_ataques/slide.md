---
marp: true

class: invert
paginate: true
backgroundColor: #0d1117
color: #e6edf3
style: |
  section {
    font-family: 'Segoe UI', sans-serif;
    padding: 40px 60px;
  }
  h1 {
    color: #ff4d4d;
    font-size: 1.9em;
    border-bottom: 2px solid #ff4d4d;
    padding-bottom: 8px;
    margin-bottom: 16px;
  }
  h2 { color: #ff8c00; font-size: 1.4em; }
  h3 { color: #58a6ff; font-size: 1.1em; margin-bottom: 8px; }
  pre {
    background: #161b22;
    border-left: 4px solid #ff4d4d;
    padding: 14px;
    font-size: 0.8em;
  }
  blockquote {
    border-left: 4px solid #ff8c00;
    background: #1c2128;
    padding: 10px 18px;
    margin: 0;
    border-radius: 0 8px 8px 0;
    font-size: 0.95em;
  }
  table { width: 100%; border-collapse: collapse; font-size: 0.9em; }
  th { background: #ff4d4d22; color: #ff4d4d; }
  td, th { padding: 7px 12px; border: 1px solid #30363d; }
  ul { margin-top: 8px; }
  li { margin-bottom: 6px; font-size: 0.95em; }
  section.title {
    text-align: center;
    justify-content: center;
    align-items: center;
  }
  section.center { text-align: center; }
---

<!-- _class: title -->

# SIM Swapping
## Um Ataque Cibernético no Centro dos Crimes Financeiros

<br>

**Segurança da Informação · Fatec Mogi das Cruzes**
2026

---

# O que é SIM Swapping?

**SIM Swap** (*SIM Hijacking*) é o sequestro fraudulento do número de telefone da vítima junto à operadora — colocando o chip sob controle do atacante.

### Por que o número de telefone é tão crítico?
![bg right 80%](image.png)

---

# Anatomia do Ataque — Passo a Passo

![alt text](image-1.png)

---

# Diagrama de Fluxo do Ataque

![alt text](image-3.png)

---

# O Vetor Humano — Engenharia Social

| Técnica | Como é usada |
|---|---|
| **Vishing** | Ligação fingindo ser a vítima para a operadora |
| **OSINT** | Dados coletados em redes sociais e vazamentos |
| **Insider Threat** | Suborno direto de funcionários da operadora |
| **Pretexting** | Histórias falsas convincentes ("fui roubado") |
| **Dark Web** | Compra de dados pessoais de vazamentos massivos |

> ⚠️ No Brasil, **funcionários de operadoras corrompidos** são um vetor recorrente — tornando a prevenção pela vítima ainda mais limitada.

---

# Casos Reais

### 🌎 Internacional
- **Jack Dorsey (2019)** — número sequestrado; tweets ofensivos publicados via SMS
- **Michael Terpin (2018)** — US$ 24M em cripto perdidos; processo de US$ 224M contra AT&T
- **Baller Ape Club (2022)** — US$ 1M em NFTs roubados

### 🇧🇷 Brasil
- **WhatsApp hijacking** em massa após SIM Swap de usuários comuns
- Operações da **Polícia Federal (2020–2023)** desarticularam grupos especializados
- Prejuízos **bilionários** reportados ao Banco Central e FEBRABAN

---

# Mitigação

### Para o usuário
- **Não use SMS como 2FA** — prefira apps autenticadores (Google Auth, Authy)
- **Configure PIN/senha** diretamente na sua operadora
- Considere **chaves físicas** (YubiKey, Passkeys) para contas críticas

---

<!-- _class: center -->

# Conclusão

<br>

![alt text](image-4.png)

<br>

> **"O elo mais fraco não é a tecnologia — é o processo humano que a envolve."**

---

<!-- _class: title -->

# Obrigado

### Dúvidas & Discussão

> • FEBRABAN — Relatório de Crimes Cibernéticos
> • GSMA    — SIM Swap Fraud Report
> • NIST    — SP 800-63B Digital Identity Guidelines
> • IC3/FBI — Internet Crime Report
