const STORAGE_KEY = 'secure-doctec-document';

function toBase64(buffer) {
  const bytes = new Uint8Array(buffer);
  let binary = '';
  bytes.forEach((byte) => {
    binary += String.fromCharCode(byte);
  });
  return btoa(binary);
}

function fromBase64(base64) {
  const binary = atob(base64);
  const bytes = new Uint8Array(binary.length);
  for (let i = 0; i < binary.length; i += 1) {
    bytes[i] = binary.charCodeAt(i);
  }
  return bytes;
}

function hexFromBytes(bytes) {
  return Array.from(new Uint8Array(bytes)).map((b) => b.toString(16).padStart(2, '0')).join('');
}

async function sha256Hex(input) {
  const bytes = new TextEncoder().encode(input);
  const digest = await crypto.subtle.digest('SHA-256', bytes);
  return hexFromBytes(digest);
}

async function generateEcdsaKeys() {
  return crypto.subtle.generateKey(
    { name: 'ECDSA', namedCurve: 'P-256' },
    true,
    ['sign', 'verify']
  );
}

async function signHash(hashHex, privateKey) {
  const hashBytes = new TextEncoder().encode(hashHex);
  const signature = await crypto.subtle.sign(
    { name: 'ECDSA', hash: 'SHA-256' },
    privateKey,
    hashBytes
  );
  return toBase64(signature);
}

async function verifySignature(hashHex, signatureBase64, publicKey) {
  const hashBytes = new TextEncoder().encode(hashHex);
  const signature = fromBase64(signatureBase64);
  return crypto.subtle.verify(
    { name: 'ECDSA', hash: 'SHA-256' },
    publicKey,
    signature,
    hashBytes
  );
}

async function encryptDocument(content) {
  const iv = crypto.getRandomValues(new Uint8Array(12));
  const key = await crypto.subtle.generateKey(
    { name: 'AES-GCM', length: 256 },
    true,
    ['encrypt', 'decrypt']
  );
  const encoded = new TextEncoder().encode(content);
  const encrypted = await crypto.subtle.encrypt({ name: 'AES-GCM', iv }, key, encoded);
  const rawKey = await crypto.subtle.exportKey('raw', key);

  return {
    iv: toBase64(iv),
    encrypted: toBase64(encrypted),
    key: toBase64(rawKey)
  };
}

async function decryptDocument(record) {
  const keyBytes = fromBase64(record.encryption.key);
  const iv = fromBase64(record.encryption.iv);
  const encrypted = fromBase64(record.encryption.encrypted);
  const key = await crypto.subtle.importKey('raw', keyBytes, 'AES-GCM', false, ['decrypt']);
  const decrypted = await crypto.subtle.decrypt({ name: 'AES-GCM', iv }, key, encrypted);
  return new TextDecoder().decode(decrypted);
}

function getStoredDocument() {
  const value = localStorage.getItem(STORAGE_KEY);
  return value ? JSON.parse(value) : null;
}

function saveDocument(documentRecord) {
  localStorage.setItem(STORAGE_KEY, JSON.stringify(documentRecord));
}

async function emitDocument(event) {
  event.preventDefault();

  const form = event.target;
  const payload = {
    studentName: form.studentName.value.trim(),
    course: form.course.value.trim(),
    documentType: form.documentType.value,
    issueDate: form.issueDate.value,
    issuer: form.issuer.value.trim(),
    content: form.content.value.trim()
  };

  if (!payload.studentName || !payload.course || !payload.content) {
    showMessage('É necessário preencher o aluno, o curso e o conteúdo do documento.', 'warning');
    return;
  }

  const hash = await sha256Hex(payload.content);
  const keyPair = await generateEcdsaKeys();
  const publicKey = await crypto.subtle.exportKey('spki', keyPair.publicKey);
  const privateKey = keyPair.privateKey;
  const signature = await signHash(hash, privateKey);
  const encryption = await encryptDocument(payload.content);

  const record = {
    id: `DOC-${Date.now()}`,
    studentName: payload.studentName,
    course: payload.course,
    documentType: payload.documentType,
    issueDate: payload.issueDate,
    issuer: payload.issuer,
    content: payload.content,
    hash,
    signature,
    publicKey: toBase64(publicKey),
    encryption
  };

  saveDocument(record);

  const output = document.getElementById('emission-output');
  if (output) {
    output.innerHTML = `
      <h3>Documento emitido com sucesso</h3>
      <p><strong>ID:</strong> ${record.id}</p>
      <p><strong>Aluno:</strong> ${record.studentName}</p>
      <p><strong>Curso:</strong> ${record.course}</p>
      <div class="code-block">Hash SHA-256: ${record.hash}

Assinatura digital: ${record.signature}

Chave pública institucional: ${record.publicKey.slice(0, 90)}...

Envelope cifrado (AES-256-GCM): ${record.encryption.encrypted.slice(0, 120)}...</div>
    `;
  }

  showMessage('Documento gerado com sucesso. O fluxo de validação pode ser testado agora.', 'success');
}

async function validateDocument(event) {
  event.preventDefault();
  const form = event.target;
  const presentedText = form.presentedText.value.trim();
  const record = getStoredDocument();

  if (!record) {
    showMessage('Primeiro gere um documento na página de emissão.', 'warning');
    return;
  }

  let content = presentedText;
  const hash = await sha256Hex(content);
  const verification = await verifySignature(hash, record.signature, await crypto.subtle.importKey('spki', fromBase64(record.publicKey), { name: 'ECDSA', namedCurve: 'P-256' }, false, ['verify']));

  const container = document.getElementById('validation-output');
  if (!container) return;

  const isValid = hash === record.hash && verification;

  container.innerHTML = `
    <h3>Resultado da validação</h3>
    <div class="status-box ${isValid ? 'status-valid' : 'status-invalid'}">
      ${isValid ? 'VÁLIDO — documento íntegro e autenticado.' : 'INVÁLIDO — documento adulterado ou assinatura inválida.'}
    </div>
    <div class="code-block">Hash calculado: ${hash}

Hash original: ${record.hash}

Assinatura válida: ${verification ? 'Sim' : 'Não'}</div>
  `;

  showMessage(isValid ? 'A validação confirmada indica integridade e autenticidade.' : 'A validação falhou. O conteúdo foi alterado ou a assinatura não corresponde.', isValid ? 'success' : 'danger');
}

async function simulateAdulteration(event) {
  event.preventDefault();
  const record = getStoredDocument();
  if (!record) {
    showMessage('Primeiro gere um documento na página de emissão.', 'warning');
    return;
  }

  const form = event.target;
  const modifiedText = form.modifiedText.value.trim();
  const modifiedHash = await sha256Hex(modifiedText);
  const publicKey = await crypto.subtle.importKey('spki', fromBase64(record.publicKey), { name: 'ECDSA', namedCurve: 'P-256' }, false, ['verify']);
  const verification = await verifySignature(modifiedHash, record.signature, publicKey);

  const container = document.getElementById('adulteration-output');
  if (!container) return;

  const isValid = modifiedHash === record.hash && verification;

  container.innerHTML = `
    <h3>Resultado do cenário adulterado</h3>
    <div class="status-box ${isValid ? 'status-valid' : 'status-invalid'}">
      ${isValid ? 'Documento ainda válido.' : 'Documento adulterado — a assinatura e o hash não conferem.'}
    </div>
    <div class="code-block">Conteúdo alterado:
${modifiedText}

Hash recalculado: ${modifiedHash}

Hash oficial: ${record.hash}

Autenticidade confirmada: ${verification ? 'Sim' : 'Não'}</div>
  `;

  showMessage(isValid ? 'Mesmo com a alteração, a assinatura continua válida.' : 'A alteração foi detectada e a validação foi rejeitada.', isValid ? 'success' : 'danger');
}

function showMessage(message, type) {
  const messageBox = document.getElementById('toast');
  if (!messageBox) return;

  messageBox.textContent = message;
  messageBox.className = `toast ${type}`;
  messageBox.style.opacity = '1';

  clearTimeout(showMessage.timeoutId);
  showMessage.timeoutId = setTimeout(() => {
    messageBox.style.opacity = '0';
  }, 3500);
}

function initEmitter() {
  const form = document.getElementById('document-form');
  if (form) form.addEventListener('submit', emitDocument);
}

function initValidator() {
  const form = document.getElementById('validation-form');
  if (form) form.addEventListener('submit', validateDocument);

  const doc = getStoredDocument();
  const sample = document.getElementById('presentedText');
  if (doc && sample) {
    sample.value = doc.content;
  }
}

function initAdulteration() {
  const form = document.getElementById('adulteration-form');
  if (form) form.addEventListener('submit', simulateAdulteration);

  const doc = getStoredDocument();
  const sample = document.getElementById('modifiedText');
  if (doc && sample) {
    sample.value = `${doc.content}\n\n[ALTERAÇÃO SIMULADA: a matrícula foi modificada e a nota foi aumentada.]`;
  }
}

document.addEventListener('DOMContentLoaded', () => {
  initEmitter();
  initValidator();
  initAdulteration();
});
