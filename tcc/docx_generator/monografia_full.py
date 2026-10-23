#!/usr/bin/env python3
"""
monografia_full.py
==================
Orquestrador completo: gera o documento ABNT final com TODOS os elementos
na ordem correta (pré-textuais + corpo + pós-textuais).

Ordem ABNT NBR 14724 implementada:
    ── PRÉ-TEXTUAIS ─────────────────────────────────────────────────
    1.  Capa
    2.  Folha de Rosto
    3.  Folha de Aprovação
    4.  Dedicatória             (opcional)
    5.  Agradecimentos          (opcional)
    6.  Epígrafe                (opcional)
    7.  Resumo em Português
    8.  Abstract (inglês)
    9.  Lista de Figuras        (opcional)
    10. Lista de Tabelas        (opcional)
    11. Lista de Abreviaturas   (opcional)
    12. Sumário                 (campo TOC — atualizar com F9 no Word)
    ── TEXTUAIS ─────────────────────────────────────────────────────
    13. Corpo do trabalho       (a partir do .md)
    ── PÓS-TEXTUAIS ─────────────────────────────────────────────────
    14. Referências             (extraídas do .md)
    15. Apêndices               (placeholder)

Uso:
    python monografia_full.py monografia_config.json Monografia
    python monografia_full.py monografia_config.json Monografia --output tcc_breno.docx --font Arial
    python monografia_full.py monografia_config.json Monografia --skip-pretext   # apenas corpo

Dependências:
    pip install python-docx
    (monografia_converter.py e pretext_builder.py no mesmo diretório)
"""

import argparse
import sys
from copy import deepcopy
from pathlib import Path

from docx import Document
from docx.shared import Cm

# Módulos do projeto
from monografia_converter import (
    CONFIG,
    MarkdownParser,
    AcademicValidator,
    DocxGenerator,
    ABNTStyleManager,
    ReportGenerator,
    ImageBlock,
)
from pretext_builder import PretextBuilder


# =========================================================================== #
# ORQUESTRADOR PRINCIPAL                                                       #
# =========================================================================== #

class MonografiaFull:
    """
    Combina PretextBuilder (pré-textuais) + DocxGenerator (corpo)
    em um único documento ABNT completo.
    """

    def __init__(self, config_path: str, md_path: str,
                 output_path: str, font: str = 'Arial',
                 skip_pretext: bool = False):
        self.config_path = config_path
        self.md_path = md_path
        self.output_path = output_path
        self.font = font
        self.skip_pretext = skip_pretext

        # Carregar config JSON
        self.cfg = PretextBuilder.load_config(config_path)

        # Configuração de estilos (cópia do CONFIG global com fonte escolhida)
        self.doc_config = deepcopy(CONFIG)
        self.doc_config['font_name'] = font

    # ------------------------------------------------------------------ #
    def build(self):
        print(f'\n{"="*60}')
        print('  MONOGRAFIA FULL — ABNT NBR 14724 COMPLETO')
        print(f'{"="*60}')
        print(f'  Config: {self.config_path}')
        print(f'  Fonte:  {self.md_path}')
        print(f'  Saída:  {self.output_path}')
        print(f'{"="*60}\n')

        # ── 1. Parse do Markdown ──────────────────────────────────────
        print('[1/5] Parseando markdown...')
        parser = MarkdownParser(self.md_path)
        blocks = parser.parse()
        print(f'      {len(blocks)} blocos extraídos.')

        # ── 2. Validação ABNT ─────────────────────────────────────────
        print('[2/5] Validando regras ABNT...')
        validator = AcademicValidator()
        blocks = validator.validate_all(blocks)
        print(f'      {len(validator.pendencias)} pendências encontradas.')

        # Extrair ImageBlocks para a Lista de Figuras
        image_blocks = [b for b in blocks if isinstance(b, ImageBlock)]

        # ── 3. Criar documento base ───────────────────────────────────
        print('[3/5] Criando documento...')
        doc = Document()

        # Margens ABNT em todas as seções
        for section in doc.sections:
            section.top_margin    = Cm(self.doc_config['margin_top_cm'])
            section.left_margin   = Cm(self.doc_config['margin_left_cm'])
            section.bottom_margin = Cm(self.doc_config['margin_bottom_cm'])
            section.right_margin  = Cm(self.doc_config['margin_right_cm'])

        # ── 4. Pré-textuais ───────────────────────────────────────────
        if not self.skip_pretext:
            print('[4/5] Gerando pré-textuais...')
            pretext = PretextBuilder(self.cfg, doc, self.font)
            pretext.build_all(image_blocks=image_blocks)
        else:
            print('[4/5] Pré-textuais ignorados (--skip-pretext).')

        # ── 5. Corpo do trabalho ──────────────────────────────────────
        print('[5/5] Inserindo corpo do trabalho...')

        # Registrar estilos ABNT no documento já criado
        style_mgr = ABNTStyleManager(doc, self.doc_config)

        # Gerar corpo reutilizando DocxGenerator sobre o doc existente
        body_gen = DocxGenerator(self.doc_config)
        body_gen.doc = doc  # redirecionar para o doc já com pré-textuais
        body_gen.style_mgr = style_mgr
        body_gen.build(blocks)

        # ── Salvar ────────────────────────────────────────────────────
        doc.save(self.output_path)
        print(f'\n✅ Documento salvo: {self.output_path}')

        # ── Relatório de pendências ───────────────────────────────────
        report_path = Path(self.output_path).stem + '_pendencias.md'
        reporter = ReportGenerator(
            pendencias=validator.pendencias,
            source_file=self.md_path,
            output_file=self.output_path,
        )
        reporter.write_report(str(report_path))
        reporter.print_summary(blocks)

        return self.output_path, str(report_path)


# =========================================================================== #
# CLI                                                                          #
# =========================================================================== #

def main():
    parser = argparse.ArgumentParser(
        description='Gera monografia ABNT completa (pré-textuais + corpo) a partir de config.json + .md'
    )
    parser.add_argument('config', help='Arquivo monografia_config.json')
    parser.add_argument('input',  help='Arquivo .md com o corpo da monografia')
    parser.add_argument('--output', '-o', default=None,
                        help='Arquivo .docx de saída (padrão: monografia_final.docx)')
    parser.add_argument('--font', choices=['Arial', 'Times New Roman'],
                        default='Arial', help='Fonte principal (padrão: Arial)')
    parser.add_argument('--skip-pretext', action='store_true',
                        help='Gerar apenas o corpo (sem pré-textuais)')
    args = parser.parse_args()

    output = args.output or 'monografia_final.docx'

    full = MonografiaFull(
        config_path=args.config,
        md_path=args.input,
        output_path=output,
        font=args.font,
        skip_pretext=args.skip_pretext,
    )
    full.build()


if __name__ == '__main__':
    main()
