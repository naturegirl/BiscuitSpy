NAME=paper

TARGET=$(NAME)-cos597g
BIBTEX := bibtex
TGIF   := tgif
XFIG   := xfig
GNUPLOT:= gnuplot

SOURCES=$(NAME)-cos597g.tex \
	abstract.tex \
	intro.tex \
	model.tex \
	design.tex \
	eval.tex \
	context.tex \
	related.tex \
	future.tex \
	conclusion.tex \
	local.bib

# FIGS=	data/one.pdf \
#	data/two.pdf

all: $(TARGET).ps
pdf: all

$(TARGET).pdf: Makefile  $(SOURCES) $(FIGS)
	pdflatex  $(TARGET).tex
	-bibtex --min-crossrefs=100    $(TARGET)
	pdflatex  $(TARGET).tex

color: $(TARGET).pdf
	pdflatex $(TARGET).tex
	pdftops $(TARGET).pdf

$(TARGET).ps: $(TARGET).pdf
	pdflatex "\newcommand{\wantBW}{} \input{$(TARGET).tex}"
	pdftops $(TARGET).pdf

%.pdf : %.fig #Makefile
	fig2dev -L pdf -b 1 $< $@

%.eps : %.dia #Makefile
	dia --nosplash -e $@ $<

%.eps : %.obj
	TMPDIR=/tmp $(TGIF) -print -eps $<

%.pdf : %.eps #Makefile
	epstopdf $<

clean:
	rm -f *.aux *.log *.out *.bbl *.blg *~ *.bak $(FIGS) $(TARGET).ps $(TARGET).pdf
