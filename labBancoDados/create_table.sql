-- ============================================================
-- DDL – IoT Industrial Database
-- Plataforma: Supabase (PostgreSQL 15+)
-- Gerado para ingestão a partir de influx_modelado.xlsx
-- ============================================================


-- ============================================================
-- 1. segmento_cliente
-- ============================================================
CREATE TABLE IF NOT EXISTS segmento_cliente (
    seg_id          SERIAL          NOT NULL,
    seg_nome        VARCHAR(100)    NOT NULL,
    seg_descricao   VARCHAR(255),
    seg_ativo       CHAR(1)         NOT NULL DEFAULT 'S',
    seg_created_at  TIMESTAMPTZ     NOT NULL DEFAULT NOW(),

    -- Constraints
    CONSTRAINT PK_SEG PRIMARY KEY (seg_id),
    CONSTRAINT CHK_SEG_ATIVO CHECK (seg_ativo IN ('S', 'N'))
);

COMMENT ON TABLE  segmento_cliente              IS 'Segmentos de mercado atendidos pela empresa de automação industrial.';
COMMENT ON COLUMN segmento_cliente.seg_id       IS 'Chave primária artificial (Surrogate Key).';
COMMENT ON COLUMN segmento_cliente.seg_nome     IS 'Título ou nomenclatura oficial do segmento (ex: Supermercados, Frigoríficos).';
COMMENT ON COLUMN segmento_cliente.seg_descricao IS 'Texto explicativo com critérios ou características do segmento.';
COMMENT ON COLUMN segmento_cliente.seg_ativo    IS 'Flag de status: S = Ativo, N = Inativo.';
COMMENT ON COLUMN segmento_cliente.seg_created_at IS 'Timestamp de inserção do registro.';


-- ============================================================
-- 2. clientes
-- ============================================================
CREATE TABLE IF NOT EXISTS clientes (
    cli_id          SERIAL          NOT NULL,
    cli_nome        VARCHAR(150)    NOT NULL,
    cli_cnpj        VARCHAR(14)     NOT NULL,
    cli_seg_id      INTEGER         NOT NULL,
    cli_cidade      VARCHAR(100),
    cli_estado      CHAR(2),
    cli_ativo       CHAR(1)         NOT NULL DEFAULT 'S',
    cli_created_at  TIMESTAMPTZ     NOT NULL DEFAULT NOW(),

    -- Constraints
    CONSTRAINT PK_CLI       PRIMARY KEY (cli_id),
    CONSTRAINT UQ_CLI_CNPJ  UNIQUE      (cli_cnpj),
    CONSTRAINT FK_CLI_SEG   FOREIGN KEY (cli_seg_id)
                            REFERENCES  segmento_cliente (seg_id)
                            ON UPDATE CASCADE
                            ON DELETE RESTRICT,
    CONSTRAINT CHK_CLI_ATIVO  CHECK (cli_ativo  IN ('S', 'N')),
    CONSTRAINT CHK_CLI_ESTADO CHECK (cli_estado = UPPER(cli_estado))
);

COMMENT ON TABLE  clientes              IS 'Empresas clientes com equipamentos de refrigeração monitorados.';
COMMENT ON COLUMN clientes.cli_id       IS 'Chave primária artificial.';
COMMENT ON COLUMN clientes.cli_nome     IS 'Razão social ou nome oficial do cliente.';
COMMENT ON COLUMN clientes.cli_cnpj     IS 'CNPJ armazenado apenas com dígitos (14 chars). Único por cliente.';
COMMENT ON COLUMN clientes.cli_seg_id   IS 'FK → segmento_cliente.seg_id';
COMMENT ON COLUMN clientes.cli_cidade   IS 'Cidade sede do cliente.';
COMMENT ON COLUMN clientes.cli_estado   IS 'UF do cliente (ex: SP, RJ).';
COMMENT ON COLUMN clientes.cli_ativo    IS 'Flag de status: S = Ativo, N = Inativo/Suspenso.';
COMMENT ON COLUMN clientes.cli_created_at IS 'Timestamp de inserção do registro.';


-- ============================================================
-- 3. maquinas
-- ============================================================
CREATE TABLE IF NOT EXISTS maquinas (
    maq_id           SERIAL          NOT NULL,
    maq_cli_id       INTEGER         NOT NULL,
    maq_nome         VARCHAR(120)    NOT NULL,
    maq_modelo       VARCHAR(120),
    maq_numero_serie VARCHAR(100),
    maq_localizacao  VARCHAR(150),
    maq_ativo        CHAR(1)         NOT NULL DEFAULT 'S',
    maq_created_at   TIMESTAMPTZ     NOT NULL DEFAULT NOW(),

    -- Constraints
    CONSTRAINT PK_MAQ            PRIMARY KEY (maq_id),
    CONSTRAINT UQ_MAQ_SERIE      UNIQUE      (maq_numero_serie),
    CONSTRAINT FK_MAQ_CLI        FOREIGN KEY (maq_cli_id)
                                 REFERENCES  clientes (cli_id)
                                 ON UPDATE CASCADE
                                 ON DELETE RESTRICT,
    CONSTRAINT CHK_MAQ_ATIVO     CHECK (maq_ativo IN ('S', 'N'))
);

COMMENT ON TABLE  maquinas                  IS 'Equipamentos de refrigeração industrial instalados nos clientes.';
COMMENT ON COLUMN maquinas.maq_id           IS 'Chave primária artificial.';
COMMENT ON COLUMN maquinas.maq_cli_id       IS 'FK → clientes.cli_id';
COMMENT ON COLUMN maquinas.maq_nome         IS 'Nome descritivo ou apelido da máquina.';
COMMENT ON COLUMN maquinas.maq_modelo       IS 'Modelo técnico fornecido pelo fabricante.';
COMMENT ON COLUMN maquinas.maq_numero_serie IS 'Número de série único do equipamento (chave natural).';
COMMENT ON COLUMN maquinas.maq_localizacao  IS 'Ponto físico ou setor de instalação na planta.';
COMMENT ON COLUMN maquinas.maq_ativo        IS 'Status: S = Em operação, N = Fora de serviço.';
COMMENT ON COLUMN maquinas.maq_created_at   IS 'Timestamp de cadastramento do equipamento.';


-- ============================================================
-- 4. tipos_sensores
-- ============================================================
CREATE TABLE IF NOT EXISTS tipos_sensores (
    tps_id          SERIAL          NOT NULL,
    tps_nome        VARCHAR(120)    NOT NULL,
    tps_categoria   VARCHAR(50),
    tps_unidade     VARCHAR(20),
    tps_descricao   VARCHAR(255),

    -- Constraints
    CONSTRAINT PK_TPS       PRIMARY KEY (tps_id),
    CONSTRAINT UQ_TPS_NOME  UNIQUE      (tps_nome)
);

COMMENT ON TABLE  tipos_sensores               IS 'Catálogo de tipologias de sensores utilizados no sistema IoT.';
COMMENT ON COLUMN tipos_sensores.tps_id        IS 'Chave primária artificial.';
COMMENT ON COLUMN tipos_sensores.tps_nome      IS 'Nome técnico ou comercial do tipo de sensor.';
COMMENT ON COLUMN tipos_sensores.tps_categoria IS 'Agrupamento funcional: temperatura, pressão, digital, bitmask, status, alarme.';
COMMENT ON COLUMN tipos_sensores.tps_unidade   IS 'Unidade de medida padrão das leituras (ex: °C, PSI, bits).';
COMMENT ON COLUMN tipos_sensores.tps_descricao IS 'Descrição técnica sobre finalidade e limitações do tipo de sensor.';


-- ============================================================
-- 5. sensores
-- ============================================================
CREATE TABLE IF NOT EXISTS sensores (
    sen_id          SERIAL          NOT NULL,
    sen_maq_id      INTEGER         NOT NULL,
    sen_tps_id      INTEGER         NOT NULL,
    sen_tag_fisica  VARCHAR(120),
    sen_descricao   VARCHAR(255),
    sen_ativo       CHAR(1)         NOT NULL DEFAULT 'S',
    sen_created_at  TIMESTAMPTZ     NOT NULL DEFAULT NOW(),

    -- Constraints
    CONSTRAINT PK_SEN       PRIMARY KEY (sen_id),
    CONSTRAINT FK_SEN_MAQ   FOREIGN KEY (sen_maq_id)
                            REFERENCES  maquinas (maq_id)
                            ON UPDATE CASCADE
                            ON DELETE RESTRICT,
    CONSTRAINT FK_SEN_TPS   FOREIGN KEY (sen_tps_id)
                            REFERENCES  tipos_sensores (tps_id)
                            ON UPDATE CASCADE
                            ON DELETE RESTRICT,
    CONSTRAINT CHK_SEN_ATIVO CHECK (sen_ativo IN ('S', 'N')),
    -- garante que a mesma tag não se repete na mesma máquina
    CONSTRAINT UQ_SEN_MAQ_TAG UNIQUE (sen_maq_id, sen_tag_fisica)
);

COMMENT ON TABLE  sensores                IS 'Sensores físicos instalados e vinculados a cada máquina monitorada.';
COMMENT ON COLUMN sensores.sen_id         IS 'Chave primária artificial.';
COMMENT ON COLUMN sensores.sen_maq_id     IS 'FK → maquinas.maq_id';
COMMENT ON COLUMN sensores.sen_tps_id     IS 'FK → tipos_sensores.tps_id';
COMMENT ON COLUMN sensores.sen_tag_fisica IS 'Tag/endereço do sensor no PLC, Modbus ou SCADA (ex: Cooler_Temperature_Z4).';
COMMENT ON COLUMN sensores.sen_descricao  IS 'Descrição da posição ou função do sensor no processo.';
COMMENT ON COLUMN sensores.sen_ativo      IS 'Status de transmissão: S = Ativo/transmitindo, N = Desconectado.';
COMMENT ON COLUMN sensores.sen_created_at IS 'Timestamp de vinculação do sensor ao sistema.';


-- ============================================================
-- 6. leitura_sensores
-- ============================================================
-- Tabela de séries temporais com alto volume de inserção.
-- Usa BIGSERIAL na PK e índice composto (sen_id + timestamp)
-- para otimizar queries de janela temporal por sensor.
CREATE TABLE IF NOT EXISTS leitura_sensores (
    lse_id              BIGSERIAL       NOT NULL,
    lse_sen_id          INTEGER         NOT NULL,
    lse_valor_float     NUMERIC(10, 4),
    lse_valor_boolean   BOOLEAN,
    lse_valor_texto     TEXT,
    lse_timestamp       TIMESTAMPTZ     NOT NULL,

    -- Constraints
    CONSTRAINT PK_LSE       PRIMARY KEY (lse_id),
    CONSTRAINT FK_LSE_SEN   FOREIGN KEY (lse_sen_id)
                            REFERENCES  sensores (sen_id)
                            ON UPDATE CASCADE
                            ON DELETE RESTRICT,
    -- Exatamente um dos três campos de valor deve ser preenchido por leitura
    CONSTRAINT CHK_LSE_VALOR CHECK (
        (lse_valor_float   IS NOT NULL)::INT +
        (lse_valor_boolean IS NOT NULL)::INT +
        (lse_valor_texto   IS NOT NULL)::INT = 1
    )
);

COMMENT ON TABLE  leitura_sensores                  IS 'Série temporal de leituras coletadas pelo gateway IoT (rasp1 / InfluxDB → Supabase).';
COMMENT ON COLUMN leitura_sensores.lse_id           IS 'Chave primária BIGSERIAL — suporta alto volume de inserções.';
COMMENT ON COLUMN leitura_sensores.lse_sen_id       IS 'FK → sensores.sen_id';
COMMENT ON COLUMN leitura_sensores.lse_valor_float  IS 'Leitura numérica com precisão de 4 casas decimais (temperatura, pressão, etc.).';
COMMENT ON COLUMN leitura_sensores.lse_valor_boolean IS 'Leitura digital/booleana (habilita/desabilita, ligar/desligar).';
COMMENT ON COLUMN leitura_sensores.lse_valor_texto  IS 'Leitura textual ou bitmask (ex: estado de válvulas, alarmes GEA).';
COMMENT ON COLUMN leitura_sensores.lse_timestamp    IS 'Timestamp com fuso horário do momento exato da leitura no gateway.';


-- ============================================================
-- 7. Índices de performance
-- ============================================================

-- Consultas de série temporal por sensor (query mais comum em IoT)
CREATE INDEX IF NOT EXISTS idx_lse_sen_ts
    ON leitura_sensores (lse_sen_id, lse_timestamp DESC);

-- Filtro por janela de tempo global
CREATE INDEX IF NOT EXISTS idx_lse_timestamp
    ON leitura_sensores (lse_timestamp DESC);

-- Busca de sensor por tag (join com leitura_sensores)
CREATE INDEX IF NOT EXISTS idx_sen_tag
    ON sensores (sen_tag_fisica);

-- Filtro de máquinas por cliente
CREATE INDEX IF NOT EXISTS idx_maq_cli
    ON maquinas (maq_cli_id);

-- Filtro de clientes por segmento
CREATE INDEX IF NOT EXISTS idx_cli_seg
    ON clientes (cli_seg_id);
