rapportdanalyse: uml.pdf rapportdanalyse.pdf

rapportdanalyse.pdf: rapportdanalyse.tex
	pdflatex rapportdanalyse.tex
	pdflatex rapportdanalyse.tex

uml.pdf: livrables/Uml.xmi
# seul l'export en svg semble marcher correctement mais inkscape est installé
# à l'n7 d'où la conversion .xmi ==(umbrello)==> .svg ==(inkscape)==> .pdf
	umbrello --export svg livrables/Uml.xmi
	inkscape -A uml.pdf livrables/class\ diagram.svg
	rm livrables/class\ diagram.svg

clean:
	rm -f rapportdanalyse.aux
	rm -f rapportdanalyse.log
	rm -f rapportdanalyse.out

cleanall: clean
	rm -f rapportdanalyse.pdf
	rm -f uml.pdf
