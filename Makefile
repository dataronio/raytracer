rapportdanalyse: uml.pdf livrables/rapportdanalyse.pdf

livrables/rapportdanalyse.pdf: rapportdanalyse.tex
	pdflatex -output-directory livrables rapportdanalyse.tex
	pdflatex -output-directory livrables rapportdanalyse.tex

uml.pdf: livrables/Uml.xmi
	# l'export depuis umbrello est foireux, seul l'export en svg marche,
	# et pas avec la version installée à l'n7 (la version 2.4.5 marche)
	# d'où la conversion .xmi ==(umbrello)==> .svg ==(inkscape)==> .pdf
	# de plus pour utiliser umbrello en ligne de commande il faut être
	# connecté au serveur X
	umbrello --export svg livrables/Uml.xmi
	inkscape -A uml.pdf livrables/class\ diagram.svg
	rm livrables/class\ diagram.svg

clean:
	rm -f livrables/rapportdanalyse.aux
	rm -f livrables/rapportdanalyse.log
	rm -f livrables/rapportdanalyse.out

cleanall: clean
	rm -f livrables/rapportdanalyse.pdf
	rm -f uml.pdf
