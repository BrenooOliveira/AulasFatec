#!/usr/bin/env python3
"""
pretext_builder.py
==================
Módulo complementar ao monografia_converter.py.
Gera todos os elementos pré-textuais de uma monografia ABNT NBR 14724.

Elementos gerados (em ordem ABNT):
    1.  Capa                         (obrigatório)
    2.  Folha de Rosto               (obrigatório)
    3.  Folha de Aprovação           (obrigatório)
    4.  Dedicatória                  (opcional — omitido se campo vazio no config)
    5.  Agradecimentos               (opcional)
    6.  Epígrafe                     (opcional)
    7.  Resumo em Português          (obrigatório)
    8.  Resumo em Inglês (Abstract)  (obrigatório)
    9.  Lista de Figuras             (opcional — gerada se gerar_lista_figuras=true)
    10. Lista de Tabelas             (opcional — gerada se gerar_lista_tabelas=true)
    11. Lista de Abreviaturas        (opcional)
    12. Sumário                      (obrigatório — campo TOC atualizado pelo Word)

Uso standalone:
    python pretext_builder.py monografia_config.json

Uso integrado (via monografia_full.py):
    from pretext_builder import PretextBuilder
    builder = PretextBuilder(config, doc)
    builder.build_all()
"""

import json
import sys
from pathlib import Path
from typing import Optional

from docx import Document
from docx.shared import Pt, Cm, RGBColor
from docx.enum.text import WD_ALIGN_PARAGRAPH, WD_LINE_SPACING
from docx.enum.section import WD_SECTION_START
from docx.oxml.ns import qn
from docx.oxml import OxmlElement
import lxml.etree as etree


# =========================================================================== #
# HELPERS XML / DOCX                                                          #
# =========================================================================== #

def _add_page_break(doc: Document):
    """Insere quebra de página."""
    para = doc.add_paragraph()
    run = para.add_run()
    run.add_break(__import__('docx.enum.text', fromlist=['WD_BREAK']).WD_BREAK.PAGE)
    para.paragraph_format.space_before = Pt(0)
    para.paragraph_format.space_after = Pt(0)


def _add_blank_line(doc: Document, count: int = 1):
    """Insere N parágrafos em branco com espaçamento zero."""
    for _ in range(count):
        p = doc.add_paragraph()
        p.paragraph_format.space_before = Pt(0)
        p.paragraph_format.space_after = Pt(0)


def _para(doc: Document,
          text: str = '',
          bold: bool = False,
          italic: bool = False,
          size_pt: int = 12,
          align: WD_ALIGN_PARAGRAPH = WD_ALIGN_PARAGRAPH.CENTER,
          font_name: str = 'Arial',
          space_before: float = 0,
          space_after: float = 0,
          first_indent_cm: float = 0,
          color: Optional[RGBColor] = None,
          underline: bool = False) -> 'Paragraph':
    """Atalho para criar parágrafo com estilo inline."""
    p = doc.add_paragraph()
    p.paragraph_format.alignment = align
    p.paragraph_format.space_before = Pt(space_before)
    p.paragraph_format.space_after = Pt(space_after)
    p.paragraph_format.first_line_indent = Cm(first_indent_cm)
    p.paragraph_format.line_spacing_rule = WD_LINE_SPACING.SINGLE

    if text:
        run = p.add_run(text)
        run.font.name = font_name
        run.font.size = Pt(size_pt)
        run.font.bold = bold
        run.font.italic = italic
        run.font.underline = underline
        if color:
            run.font.color.rgb = color
    return p


def _heading_abnt(doc: Document, text: str, font_name: str = 'Arial', size_pt: int = 12):
    """
    Heading pré-textual: caixa-alta, negrito, centralizado, sem número de seção.
    Esses headings aparecem no sumário mas não são numerados.
    """
    p = doc.add_paragraph()
    p.paragraph_format.alignment = WD_ALIGN_PARAGRAPH.CENTER
    p.paragraph_format.space_before = Pt(0)
    p.paragraph_format.space_after = Pt(24)
    p.paragraph_format.first_line_indent = Cm(0)

    run = p.add_run(text.upper())
    run.font.name = font_name
    run.font.size = Pt(size_pt)
    run.font.bold = True
    run.font.color.rgb = RGBColor(0, 0, 0)

    # Marcar como Heading 1 não-numerado para o sumário via estilo XML
    # Usamos 'Heading 1' do Word mas sem outline number
    try:
        p.style = doc.styles['Heading 1']
        # Re-aplicar formatação pois o estilo pode sobrescrever
        for r in p.runs:
            r.font.name = font_name
            r.font.size = Pt(size_pt)
            r.font.bold = True
            r.font.color.rgb = RGBColor(0, 0, 0)
        p.paragraph_format.alignment = WD_ALIGN_PARAGRAPH.CENTER
        p.paragraph_format.first_line_indent = Cm(0)
    except Exception:
        pass

    return p


def _insert_toc_field(doc: Document, font_name: str = 'Arial'):
    """
    Insere campo TOC (Table of Contents) via XML OOXML.
    O Word/LibreOffice atualiza o sumário ao abrir o arquivo (F9).
    Inclui heading levels 1-3 com líderes de ponto.
    """
    # Parágrafo que vai conter o campo
    p = doc.add_paragraph()
    p.paragraph_format.alignment = WD_ALIGN_PARAGRAPH.LEFT
    p.paragraph_format.first_line_indent = Cm(0)

    # Instrução do campo TOC
    # \o "1-3" → níveis 1 a 3
    # \h        → links clicáveis
    # \z        → ocultar números de página no modo web
    # \u        → usar parágrafos não estilizados como Heading
    fld_char_begin = OxmlElement('w:fldChar')
    fld_char_begin.set(qn('w:fldCharType'), 'begin')

    instr_text = OxmlElement('w:instrText')
    instr_text.set(qn('xml:space'), 'preserve')
    instr_text.text = ' TOC \\o "1-3" \\h \\z \\u '

    fld_char_separate = OxmlElement('w:fldChar')
    fld_char_separate.set(qn('w:fldCharType'), 'separate')

    # Texto de placeholder (visível antes de atualizar)
    placeholder_run = OxmlElement('w:r')
    placeholder_t = OxmlElement('w:t')
    placeholder_t.text = '[Clique aqui e pressione F9 para atualizar o Sumário]'
    placeholder_t.set(qn('xml:space'), 'preserve')
    placeholder_run.append(placeholder_t)

    fld_char_end = OxmlElement('w:fldChar')
    fld_char_end.set(qn('w:fldCharType'), 'end')

    run = p.add_run()
    run._r.append(fld_char_begin)
    run._r.append(instr_text)
    run._r.append(fld_char_separate)
    run._r.append(placeholder_run)
    run._r.append(fld_char_end)

    run.font.name = font_name
    run.font.size = Pt(12)
    run.font.italic = True
    run.font.color.rgb = RGBColor(0x44, 0x44, 0x44)


# =========================================================================== #
# BUILDERS INDIVIDUAIS                                                         #
# =========================================================================== #

class CapaBuilder:
    """
    Capa ABNT NBR 14724 seção 4.1.1:
    - Nome da instituição (topo, centralizado)
    - Nome do autor
    - Título (negrito, caixa-alta)
    - Subtítulo (se houver)
    - Cidade e ano (rodapé da capa)
    """

    def __init__(self, doc: Document, cfg: dict, font: str):
        self.doc = doc
        self.cfg = cfg
        self.font = font

    def build(self):
        inst = self.cfg['instituicao']
        trabalho = self.cfg['trabalho']
        autores = self.cfg['autores']
        local = self.cfg['local']

        # Instituição
        _para(self.doc, inst['nome'].upper(),
              bold=True, size_pt=12, font_name=self.font,
              align=WD_ALIGN_PARAGRAPH.CENTER)

        if inst.get('unidade'):
            _para(self.doc, inst['unidade'].upper(),
                  size_pt=12, font_name=self.font,
                  align=WD_ALIGN_PARAGRAPH.CENTER)

        if inst.get('curso'):
            _para(self.doc, inst['curso'].upper(),
                  size_pt=12, font_name=self.font,
                  align=WD_ALIGN_PARAGRAPH.CENTER)

        # Espaço vertical (ABNT: autor centralizado verticalmente na capa)
        _add_blank_line(self.doc, 8)

        # Autor(es)
        for autor in autores:
            _para(self.doc, autor['nome_completo'].upper(),
                  bold=True, size_pt=12, font_name=self.font,
                  align=WD_ALIGN_PARAGRAPH.CENTER)

        _add_blank_line(self.doc, 8)

        # Título
        _para(self.doc, trabalho['titulo'],
              bold=True, size_pt=12, font_name=self.font,
              align=WD_ALIGN_PARAGRAPH.CENTER)

        # Subtítulo (separado por dois-pontos na mesma linha ou linha abaixo)
        if trabalho.get('subtitulo'):
            _para(self.doc, trabalho['subtitulo'],
                  size_pt=12, font_name=self.font,
                  align=WD_ALIGN_PARAGRAPH.CENTER)

        _add_blank_line(self.doc, 8)

        # Local e ano
        _para(self.doc, local['cidade'],
              size_pt=12, font_name=self.font,
              align=WD_ALIGN_PARAGRAPH.CENTER)

        _para(self.doc, local['ano'],
              size_pt=12, font_name=self.font,
              align=WD_ALIGN_PARAGRAPH.CENTER)

        _add_page_break(self.doc)


class FolhaDeRostoBuilder:
    """
    Folha de Rosto ABNT NBR 14724 seção 4.1.2:
    - Autor
    - Título
    - Nota de natureza (recuada 8cm da margem esquerda, 10pt)
    - Orientador
    - Local e ano
    """

    def __init__(self, doc: Document, cfg: dict, font: str):
        self.doc = doc
        self.cfg = cfg
        self.font = font

    def build(self):
        trabalho = self.cfg['trabalho']
        autores = self.cfg['autores']
        orientacao = self.cfg['orientacao']
        local = self.cfg['local']

        # Autor
        for autor in autores:
            _para(self.doc, autor['nome_completo'].upper(),
                  bold=True, size_pt=12, font_name=self.font,
                  align=WD_ALIGN_PARAGRAPH.CENTER)

        _add_blank_line(self.doc, 8)

        # Título
        _para(self.doc, trabalho['titulo'],
              bold=True, size_pt=12, font_name=self.font,
              align=WD_ALIGN_PARAGRAPH.CENTER)

        if trabalho.get('subtitulo'):
            _para(self.doc, trabalho['subtitulo'],
                  size_pt=12, font_name=self.font,
                  align=WD_ALIGN_PARAGRAPH.CENTER)

        _add_blank_line(self.doc, 6)

        # Nota de natureza — recuada 8cm da margem esquerda (ABNT exato)
        nota_para = self.doc.add_paragraph()
        nota_para.paragraph_format.alignment = WD_ALIGN_PARAGRAPH.JUSTIFY
        nota_para.paragraph_format.left_indent = Cm(8)
        nota_para.paragraph_format.first_line_indent = Cm(0)
        nota_para.paragraph_format.space_before = Pt(0)
        nota_para.paragraph_format.space_after = Pt(0)
        nota_para.paragraph_format.line_spacing_rule = WD_LINE_SPACING.SINGLE

        run = nota_para.add_run(trabalho['natureza'])
        run.font.name = self.font
        run.font.size = Pt(10)

        if trabalho.get('area'):
            area_para = self.doc.add_paragraph()
            area_para.paragraph_format.alignment = WD_ALIGN_PARAGRAPH.JUSTIFY
            area_para.paragraph_format.left_indent = Cm(8)
            area_para.paragraph_format.first_line_indent = Cm(0)
            area_para.paragraph_format.space_before = Pt(6)
            area_para.paragraph_format.space_after = Pt(0)
            area_para.paragraph_format.line_spacing_rule = WD_LINE_SPACING.SINGLE
            r2 = area_para.add_run(trabalho['area'])
            r2.font.name = self.font
            r2.font.size = Pt(10)

        # Orientador (também recuado)
        orient_text = f"{orientacao['orientador_titulo']} {orientacao['orientador']}"
        orient_label_para = self.doc.add_paragraph()
        orient_label_para.paragraph_format.alignment = WD_ALIGN_PARAGRAPH.JUSTIFY
        orient_label_para.paragraph_format.left_indent = Cm(8)
        orient_label_para.paragraph_format.first_line_indent = Cm(0)
        orient_label_para.paragraph_format.space_before = Pt(12)
        orient_label_para.paragraph_format.space_after = Pt(0)
        orient_label_para.paragraph_format.line_spacing_rule = WD_LINE_SPACING.SINGLE
        r3 = orient_label_para.add_run(f'Orientador: {orient_text}')
        r3.font.name = self.font
        r3.font.size = Pt(10)

        if orientacao.get('coorientador'):
            co_para = self.doc.add_paragraph()
            co_para.paragraph_format.left_indent = Cm(8)
            co_para.paragraph_format.first_line_indent = Cm(0)
            co_para.paragraph_format.space_before = Pt(0)
            co_para.paragraph_format.space_after = Pt(0)
            co_para.paragraph_format.line_spacing_rule = WD_LINE_SPACING.SINGLE
            r4 = co_para.add_run(
                f"Coorientador: {orientacao['coorientador_titulo']} {orientacao['coorientador']}"
            )
            r4.font.name = self.font
            r4.font.size = Pt(10)

        _add_blank_line(self.doc, 8)

        # Local e ano
        _para(self.doc, local['cidade'],
              size_pt=12, font_name=self.font,
              align=WD_ALIGN_PARAGRAPH.CENTER)
        _para(self.doc, local['ano'],
              size_pt=12, font_name=self.font,
              align=WD_ALIGN_PARAGRAPH.CENTER)

        _add_page_break(self.doc)


class FolhaDeAprovacaoBuilder:
    """
    Folha de Aprovação ABNT NBR 14724 seção 4.1.3:
    - Autor, Título
    - Texto padrão de aprovação
    - Linhas de assinatura dos membros da banca
    - Data de aprovação
    """

    def __init__(self, doc: Document, cfg: dict, font: str):
        self.doc = doc
        self.cfg = cfg
        self.font = font

    def build(self):
        trabalho = self.cfg['trabalho']
        autores = self.cfg['autores']
        banca = self.cfg['banca']
        local = self.cfg['local']
        orientacao = self.cfg['orientacao']

        for autor in autores:
            _para(self.doc, autor['nome_completo'].upper(),
                  bold=True, size_pt=12, font_name=self.font,
                  align=WD_ALIGN_PARAGRAPH.CENTER)

        _add_blank_line(self.doc, 2)

        _para(self.doc, trabalho['titulo'],
              bold=True, size_pt=12, font_name=self.font,
              align=WD_ALIGN_PARAGRAPH.CENTER)

        _add_blank_line(self.doc, 2)

        # Nota de aprovação
        aprovacao_text = (
            f"{trabalho['tipo']} apresentado ao {self.cfg['instituicao']['curso']} "
            f"da {self.cfg['instituicao']['nome']}, como requisito parcial "
            f"para a obtenção do título de Bacharel em Ciência da Computação, "
            f"aprovado em {banca['data_aprovacao']}."
        )
        nota_para = self.doc.add_paragraph()
        nota_para.paragraph_format.alignment = WD_ALIGN_PARAGRAPH.JUSTIFY
        nota_para.paragraph_format.left_indent = Cm(8)
        nota_para.paragraph_format.first_line_indent = Cm(0)
        nota_para.paragraph_format.space_before = Pt(0)
        nota_para.paragraph_format.space_after = Pt(0)
        nota_para.paragraph_format.line_spacing_rule = WD_LINE_SPACING.SINGLE
        run = nota_para.add_run(aprovacao_text)
        run.font.name = self.font
        run.font.size = Pt(10)

        _add_blank_line(self.doc, 4)

        # Banca examinadora
        _para(self.doc, 'BANCA EXAMINADORA',
              bold=True, size_pt=12, font_name=self.font,
              align=WD_ALIGN_PARAGRAPH.CENTER, space_after=12)

        # Orientador como presidente da banca
        self._assinatura(
            f"{orientacao['orientador_titulo']} {orientacao['orientador']}",
            'Orientador(a) — Presidente',
            self.cfg['instituicao']['nome']
        )

        # Membros
        for membro in banca['membros']:
            self._assinatura(
                f"{membro['titulo']} {membro['nome']}",
                'Membro',
                membro['instituicao']
            )

        _add_page_break(self.doc)

    def _assinatura(self, nome: str, papel: str, inst: str):
        """Bloco de assinatura: linha + nome + papel + instituição."""
        _add_blank_line(self.doc, 2)

        # Linha de assinatura
        linha_para = self.doc.add_paragraph()
        linha_para.paragraph_format.alignment = WD_ALIGN_PARAGRAPH.CENTER
        linha_para.paragraph_format.space_before = Pt(0)
        linha_para.paragraph_format.space_after = Pt(2)
        run = linha_para.add_run('_' * 50)
        run.font.name = self.font
        run.font.size = Pt(12)

        _para(self.doc, nome, bold=True, size_pt=12,
              font_name=self.font, align=WD_ALIGN_PARAGRAPH.CENTER)
        _para(self.doc, papel, size_pt=10,
              font_name=self.font, align=WD_ALIGN_PARAGRAPH.CENTER)
        _para(self.doc, inst, size_pt=10,
              font_name=self.font, align=WD_ALIGN_PARAGRAPH.CENTER)


class DedicatoriaBuilder:
    """
    Dedicatória ABNT: texto no canto inferior direito da página.
    Elemento opcional — omitido se campo vazio no config.
    """

    def __init__(self, doc: Document, cfg: dict, font: str):
        self.doc = doc
        self.cfg = cfg
        self.font = font

    def build(self):
        texto = self.cfg.get('dedicatoria', '').strip()
        if not texto:
            return  # Elemento opcional — não gerar se vazio

        # Espaço para empurrar texto para o final da página
        _add_blank_line(self.doc, 16)

        ded_para = self.doc.add_paragraph()
        ded_para.paragraph_format.alignment = WD_ALIGN_PARAGRAPH.RIGHT
        ded_para.paragraph_format.left_indent = Cm(8)
        ded_para.paragraph_format.first_line_indent = Cm(0)
        ded_para.paragraph_format.space_before = Pt(0)
        ded_para.paragraph_format.space_after = Pt(0)
        ded_para.paragraph_format.line_spacing_rule = WD_LINE_SPACING.SINGLE
        run = ded_para.add_run(texto)
        run.font.name = self.font
        run.font.size = Pt(12)
        run.font.italic = True

        _add_page_break(self.doc)


class AgradecimentosBuilder:
    """
    Agradecimentos ABNT: heading centralizado + texto justificado.
    Elemento opcional.
    """

    def __init__(self, doc: Document, cfg: dict, font: str):
        self.doc = doc
        self.cfg = cfg
        self.font = font

    def build(self):
        texto = self.cfg.get('agradecimentos', '').strip()
        if not texto:
            return

        _heading_abnt(self.doc, 'AGRADECIMENTOS', self.font)

        p = self.doc.add_paragraph()
        p.paragraph_format.alignment = WD_ALIGN_PARAGRAPH.JUSTIFY
        p.paragraph_format.first_line_indent = Cm(1.25)
        p.paragraph_format.space_before = Pt(0)
        p.paragraph_format.space_after = Pt(0)
        p.paragraph_format.line_spacing_rule = WD_LINE_SPACING.MULTIPLE
        p.paragraph_format.line_spacing = 1.5
        run = p.add_run(texto)
        run.font.name = self.font
        run.font.size = Pt(12)

        _add_page_break(self.doc)


class EpigrafeBuilder:
    """
    Epígrafe ABNT: texto no canto inferior direito, sem heading.
    Elemento opcional.
    """

    def __init__(self, doc: Document, cfg: dict, font: str):
        self.doc = doc
        self.cfg = cfg
        self.font = font

    def build(self):
        epigrafe = self.cfg.get('epigrafe', {})
        texto = epigrafe.get('texto', '').strip()
        autor = epigrafe.get('autor', '').strip()
        if not texto:
            return

        _add_blank_line(self.doc, 16)

        ep_para = self.doc.add_paragraph()
        ep_para.paragraph_format.alignment = WD_ALIGN_PARAGRAPH.RIGHT
        ep_para.paragraph_format.left_indent = Cm(8)
        ep_para.paragraph_format.first_line_indent = Cm(0)
        ep_para.paragraph_format.space_before = Pt(0)
        ep_para.paragraph_format.space_after = Pt(6)
        ep_para.paragraph_format.line_spacing_rule = WD_LINE_SPACING.SINGLE
        run = ep_para.add_run(f'"{texto}"')
        run.font.name = self.font
        run.font.size = Pt(12)
        run.font.italic = True

        if autor:
            autor_para = self.doc.add_paragraph()
            autor_para.paragraph_format.alignment = WD_ALIGN_PARAGRAPH.RIGHT
            autor_para.paragraph_format.left_indent = Cm(8)
            autor_para.paragraph_format.first_line_indent = Cm(0)
            autor_para.paragraph_format.space_before = Pt(0)
            autor_para.paragraph_format.space_after = Pt(0)
            r2 = autor_para.add_run(f'— {autor}')
            r2.font.name = self.font
            r2.font.size = Pt(12)

        _add_page_break(self.doc)


class ResumoBuilder:
    """
    Resumo (PT) e Abstract (EN) ABNT NBR 14724 seção 4.1.9 e 4.1.10:
    - Heading centralizado em caixa-alta
    - Texto em parágrafo único (sem recuo de primeira linha), espaçamento simples
    - Palavras-chave: separadas por ponto-e-vírgula
    """

    def __init__(self, doc: Document, cfg: dict, font: str):
        self.doc = doc
        self.cfg = cfg
        self.font = font

    def build(self):
        resumo_cfg = self.cfg.get('resumo', {})

        # --- Resumo em português ---
        pt = resumo_cfg.get('pt', {})
        if pt.get('texto'):
            _heading_abnt(self.doc, 'RESUMO', self.font)
            self._texto_resumo(pt['texto'])
            if pt.get('palavras_chave'):
                self._palavras_chave(pt['palavras_chave'], 'Palavras-chave')
            _add_page_break(self.doc)

        # --- Abstract (inglês) ---
        en = resumo_cfg.get('en', {})
        if en.get('texto'):
            _heading_abnt(self.doc, 'ABSTRACT', self.font)
            # Título em inglês (opcional, mas recomendado pela ABNT)
            if en.get('titulo'):
                _para(self.doc, en['titulo'], bold=True, size_pt=12,
                      font_name=self.font, align=WD_ALIGN_PARAGRAPH.CENTER,
                      space_after=12)
            self._texto_resumo(en['texto'])
            if en.get('keywords'):
                self._palavras_chave(en['keywords'], 'Keywords')
            _add_page_break(self.doc)

    def _texto_resumo(self, texto: str):
        """Parágrafo único sem recuo, espaçamento simples."""
        p = self.doc.add_paragraph()
        p.paragraph_format.alignment = WD_ALIGN_PARAGRAPH.JUSTIFY
        p.paragraph_format.first_line_indent = Cm(0)
        p.paragraph_format.space_before = Pt(0)
        p.paragraph_format.space_after = Pt(12)
        p.paragraph_format.line_spacing_rule = WD_LINE_SPACING.SINGLE
        run = p.add_run(texto)
        run.font.name = self.font
        run.font.size = Pt(12)

    def _palavras_chave(self, palavras: list[str], label: str):
        """Palavras-chave: Label em negrito + termos separados por ponto-e-vírgula."""
        p = self.doc.add_paragraph()
        p.paragraph_format.alignment = WD_ALIGN_PARAGRAPH.LEFT
        p.paragraph_format.first_line_indent = Cm(0)
        p.paragraph_format.space_before = Pt(0)
        p.paragraph_format.space_after = Pt(0)
        p.paragraph_format.line_spacing_rule = WD_LINE_SPACING.SINGLE

        label_run = p.add_run(f'{label}: ')
        label_run.font.name = self.font
        label_run.font.size = Pt(12)
        label_run.font.bold = True

        termos_run = p.add_run('; '.join(p.capitalize() for p in palavras) + '.')
        termos_run.font.name = self.font
        termos_run.font.size = Pt(12)


class ListaFigurasBuilder:
    """
    Lista de Figuras ABNT: heading + entradas geradas a partir das ImageBlocks.
    Usa campo LISTOF do Word para manutenção automática (mesma lógica do TOC).

    Na ausência de numeração automática do Word, gera lista textual
    com os alt-texts e páginas como placeholder.
    """

    def __init__(self, doc: Document, cfg: dict, font: str, image_blocks: list = None):
        self.doc = doc
        self.cfg = cfg
        self.font = font
        self.image_blocks = image_blocks or []

    def build(self):
        if not self.cfg.get('listas', {}).get('gerar_lista_figuras', False):
            return
        if not self.image_blocks:
            return

        _heading_abnt(self.doc, 'LISTA DE FIGURAS', self.font)

        for i, img in enumerate(self.image_blocks, 1):
            p = self.doc.add_paragraph()
            p.paragraph_format.alignment = WD_ALIGN_PARAGRAPH.LEFT
            p.paragraph_format.first_line_indent = Cm(0)
            p.paragraph_format.space_before = Pt(0)
            p.paragraph_format.space_after = Pt(3)
            p.paragraph_format.line_spacing_rule = WD_LINE_SPACING.SINGLE

            run = p.add_run(f'Figura {i} — {img.alt or "[sem legenda]"}')
            run.font.name = self.font
            run.font.size = Pt(12)

            # Líder de ponto e número de página (placeholder)
            from docx.oxml import OxmlElement as _OxmlElement
            tab_run = p.add_run('\t[p. XX]')
            tab_run.font.name = self.font
            tab_run.font.size = Pt(12)
            tab_run.font.italic = True
            tab_run.font.color.rgb = RGBColor(0x80, 0x80, 0x80)

        _add_page_break(self.doc)


class ListaTabelasBuilder:
    """
    Lista de Tabelas ABNT (placeholder — tabelas geradas no corpo do texto).
    """

    def __init__(self, doc: Document, cfg: dict, font: str):
        self.doc = doc
        self.cfg = cfg
        self.font = font

    def build(self):
        if not self.cfg.get('listas', {}).get('gerar_lista_tabelas', False):
            return

        _heading_abnt(self.doc, 'LISTA DE TABELAS', self.font)

        p = self.doc.add_paragraph()
        p.paragraph_format.alignment = WD_ALIGN_PARAGRAPH.CENTER
        p.paragraph_format.space_before = Pt(0)
        p.paragraph_format.space_after = Pt(0)
        run = p.add_run('[Atualizar manualmente após inserir as tabelas numeradas no corpo do texto]')
        run.font.name = self.font
        run.font.size = Pt(10)
        run.font.italic = True
        run.font.color.rgb = RGBColor(0x80, 0x80, 0x80)

        _add_page_break(self.doc)


class ListaAbreviaturasBuilder:
    """
    Lista de Abreviaturas e Siglas ABNT:
    heading + tabela de duas colunas (sigla / significado).
    """

    def __init__(self, doc: Document, cfg: dict, font: str):
        self.doc = doc
        self.cfg = cfg
        self.font = font

    def build(self):
        listas_cfg = self.cfg.get('listas', {})
        if not listas_cfg.get('gerar_lista_abreviaturas', False):
            return

        abreviaturas = listas_cfg.get('abreviaturas', {})
        if not abreviaturas:
            return

        _heading_abnt(self.doc, 'LISTA DE ABREVIATURAS E SIGLAS', self.font)

        # Ordenar alfabeticamente
        for sigla in sorted(abreviaturas.keys()):
            significado = abreviaturas[sigla]
            p = self.doc.add_paragraph()
            p.paragraph_format.alignment = WD_ALIGN_PARAGRAPH.LEFT
            p.paragraph_format.first_line_indent = Cm(0)
            p.paragraph_format.space_before = Pt(0)
            p.paragraph_format.space_after = Pt(3)
            p.paragraph_format.line_spacing_rule = WD_LINE_SPACING.SINGLE

            run_sigla = p.add_run(f'{sigla}')
            run_sigla.font.name = self.font
            run_sigla.font.size = Pt(12)
            run_sigla.font.bold = True

            run_sep = p.add_run(f' — {significado}')
            run_sep.font.name = self.font
            run_sep.font.size = Pt(12)

        _add_page_break(self.doc)


class SumarioBuilder:
    """
    Sumário ABNT: heading + campo TOC (Table of Contents).
    O Word/LibreOffice atualiza automaticamente ao abrir (F9).
    Levels 1-3 incluídos; líderes de ponto automáticos.
    """

    def __init__(self, doc: Document, cfg: dict, font: str):
        self.doc = doc
        self.cfg = cfg
        self.font = font

    def build(self):
        _heading_abnt(self.doc, 'SUMÁRIO', self.font)
        _insert_toc_field(self.doc, self.font)
        _add_page_break(self.doc)


# =========================================================================== #
# PRETEXT BUILDER — ORQUESTRADOR                                              #
# =========================================================================== #

class PretextBuilder:
    """
    Orquestrador de todos os elementos pré-textuais.

    Uso:
        builder = PretextBuilder(config_dict, doc, font_name='Arial')
        builder.build_all(image_blocks=[...])

    Os elementos são inseridos na ordem ABNT:
        Capa → Folha de Rosto → Folha de Aprovação → Dedicatória →
        Agradecimentos → Epígrafe → Resumo PT → Abstract EN →
        Lista de Figuras → Lista de Tabelas → Lista de Abreviaturas → Sumário
    """

    def __init__(self, cfg: dict, doc: Document, font: str = 'Arial'):
        self.cfg = cfg
        self.doc = doc
        self.font = font

    # ------------------------------------------------------------------ #
    def build_all(self, image_blocks: list = None):
        """Gera todos os elementos pré-textuais em ordem ABNT."""
        steps = [
            ('Capa',                   lambda: CapaBuilder(self.doc, self.cfg, self.font).build()),
            ('Folha de Rosto',         lambda: FolhaDeRostoBuilder(self.doc, self.cfg, self.font).build()),
            ('Folha de Aprovação',     lambda: FolhaDeAprovacaoBuilder(self.doc, self.cfg, self.font).build()),
            ('Dedicatória',            lambda: DedicatoriaBuilder(self.doc, self.cfg, self.font).build()),
            ('Agradecimentos',         lambda: AgradecimentosBuilder(self.doc, self.cfg, self.font).build()),
            ('Epígrafe',               lambda: EpigrafeBuilder(self.doc, self.cfg, self.font).build()),
            ('Resumo (PT)',            lambda: ResumoBuilder(self.doc, self.cfg, self.font).build()),
            ('Lista de Figuras',       lambda: ListaFigurasBuilder(self.doc, self.cfg, self.font, image_blocks).build()),
            ('Lista de Tabelas',       lambda: ListaTabelasBuilder(self.doc, self.cfg, self.font).build()),
            ('Lista de Abreviaturas',  lambda: ListaAbreviaturasBuilder(self.doc, self.cfg, self.font).build()),
            ('Sumário',                lambda: SumarioBuilder(self.doc, self.cfg, self.font).build()),
        ]

        print('  Gerando elementos pré-textuais:')
        for nome, fn in steps:
            try:
                fn()
                print(f'    ✅ {nome}')
            except Exception as e:
                print(f'    ❌ {nome}: {e}')

    # ------------------------------------------------------------------ #
    @staticmethod
    def load_config(json_path: str) -> dict:
        """Carrega e valida o arquivo de configuração JSON."""
        path = Path(json_path)
        if not path.exists():
            raise FileNotFoundError(f'Config não encontrado: {json_path}')
        with open(path, encoding='utf-8') as f:
            cfg = json.load(f)

        # Validação mínima
        required = ['instituicao', 'trabalho', 'autores', 'local']
        missing = [k for k in required if k not in cfg]
        if missing:
            raise ValueError(f'Campos obrigatórios faltando no config: {missing}')

        return cfg


# =========================================================================== #
# STANDALONE — gera apenas os pré-textuais para inspeção                     #
# =========================================================================== #

def main():
    if len(sys.argv) < 2:
        print('Uso: python pretext_builder.py monografia_config.json [--font Arial]')
        sys.exit(1)

    config_path = sys.argv[1]
    font = 'Arial'
    if '--font' in sys.argv:
        idx = sys.argv.index('--font')
        font = sys.argv[idx + 1]

    print(f'🔄 Gerando pré-textuais a partir de: {config_path}')

    cfg = PretextBuilder.load_config(config_path)
    doc = Document()

    # Margens ABNT
    for section in doc.sections:
        section.top_margin    = Cm(3.0)
        section.left_margin   = Cm(3.0)
        section.bottom_margin = Cm(2.0)
        section.right_margin  = Cm(2.0)

    builder = PretextBuilder(cfg, doc, font)
    builder.build_all()

    output = Path(config_path).stem + '_pretextuais.docx'
    doc.save(output)
    print(f'✅ Salvo: {output}')


if __name__ == '__main__':
    main()
