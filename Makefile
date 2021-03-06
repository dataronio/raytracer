all: analyse livrables rapport

livrables: force
	cd livrables; make

tests:
	cd livrables; make tests

raytracer:
	cd livrables; make raytracer

analyse: gui.png uml.pdf livrables/analyse.pdf

rapport: livrables/rapport.pdf

presentation: livrables/presentation.pdf

livrables/analyse.pdf: analyse.tex
	pdflatex -interaction nonstopmode -output-directory livrables analyse.tex
	pdflatex -interaction nonstopmode -output-directory livrables analyse.tex

livrables/rapport.pdf: rapport.tex screen2.png uml.pdf guiuml.pdf
	$(warning Les images de test du rapport ne sont pas générées pour des raisons de perf, faites make images_rapport pour les générer)
	pdflatex -interaction nonstopmode -output-directory livrables rapport.tex
	pdflatex -interaction nonstopmode -output-directory livrables rapport.tex

livrables/presentation.pdf: presentation.tex uml_hor.pdf
	pdflatex -interaction nonstopmode -output-directory livrables presentation.tex
	pdflatex -interaction nonstopmode -output-directory livrables presentation.tex

images_rapport: rapport.tex livrables/tests/raytracer/fichier_simple.png livrables/tests/raytracer/complet.png
livrables/tests/raytracer/%.png: livrables/tests/raytracer/%
	# échoue si raytracer n'est pas compilé, mais volontairement ignoré
	cd livrables; java raytracer.RayTracer ../$< ../$<; true

uml.pdf: livrables/Uml.xmi
	# l'export depuis umbrello est foireux, seul l'export en svg marche,
	# et pas avec la version installée à l'n7 (la version 2.4.5 marche)
	# d'où la conversion .xmi ==(umbrello)==> .svg ==(inkscape)==> .pdf
	# de plus pour utiliser umbrello en ligne de commande il faut être
	# connecté au serveur X
	umbrello --export svg livrables/Uml.xmi
	inkscape -A uml.pdf livrables/class\ diagram.svg
	rm livrables/class\ diagram.svg

uml_hor.pdf: Uml_hor.xmi
	umbrello --export svg Uml_hor.xmi
	inkscape -A uml_hor.pdf class\ diagram.svg
	rm class\ diagram.svg

guiuml.pdf: livrables/GuiUml.xmi
	umbrello --export svg livrables/GuiUml.xmi
	inkscape -A guiuml.pdf livrables/class\ diagram.svg
	rm livrables/class\ diagram.svg

gui.png: gui.ui
	$(warning "gui.ui modifié, impossible de générer gui.png automatiquement !")

doc: force
	cd livrables; make doc

clean: force
	rm -f livrables/*.aux
	rm -f livrables/*.log
	rm -f livrables/*.out
	rm -f livrables/*.out
	rm -f livrables/*.toc
	rm -f livrables/*.lof
	rm -f livrables/*.nav
	rm -f livrables/*.vrb
	rm -f livrables/*.snm
	cd livrables; make clean

cleanall: clean
	rm -f livrables/analyse.pdf
	rm -f livrables/rapport.pdf
	rm -f livrables/presentation.pdf
	rm -f uml.pdf
	rm -f guiuml.pdf
	rm -rf livrables/doc

force:

