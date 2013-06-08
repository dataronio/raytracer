all: analyse livrables rapport

livrables: force
	cd livrables; make

tests:
	cd livrables; make tests

analyse: gui.png uml.pdf livrables/analyse.pdf

rapport: gui.png uml.pdf livrables/rapport.pdf

livrables/analyse.pdf: analyse.tex
	pdflatex -interaction nonstopmode -output-directory livrables analyse.tex
	pdflatex -interaction nonstopmode -output-directory livrables analyse.tex

livrables/rapport.pdf: rapport.tex
	pdflatex -interaction nonstopmode -output-directory livrables rapport.tex
	pdflatex -interaction nonstopmode -output-directory livrables rapport.tex
	
uml.pdf: livrables/Uml.xmi
	# l'export depuis umbrello est foireux, seul l'export en svg marche,
	# et pas avec la version installée à l'n7 (la version 2.4.5 marche)
	# d'où la conversion .xmi ==(umbrello)==> .svg ==(inkscape)==> .pdf
	# de plus pour utiliser umbrello en ligne de commande il faut être
	# connecté au serveur X
	umbrello --export svg livrables/Uml.xmi
	inkscape -A uml.pdf livrables/class\ diagram.svg
	rm livrables/class\ diagram.svg

gui.png: gui.ui
	$(warning "gui.ui modifié, impossible de générer gui.png automatiquement !")

doc: force
	cd livrables; make doc

clean: force
	rm -f livrables/analyse.aux
	rm -f livrables/analyse.log
	rm -f livrables/analyse.out
	rm -f livrables/rapport.aux
	rm -f livrables/rapport.log
	rm -f livrables/rapport.out
	cd livrables; make clean

cleanall: clean
	rm -f livrables/analyse.pdf
	rm -f livrables/rapport.pdf
	rm -f uml.pdf
	rm -rf livrables/doc

force:

