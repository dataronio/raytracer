all: raytracer_ gui_
	jar cfm sources.jar MANIFEST.MF raytracer/*.class gui/*.class imageio/*.class

raytracer_: $(shell find raytracer -type f -name "*.java") imageio_
	javac raytracer/*.java

raytracer/%.class: raytracer/%.java
	javac $<

imageio_: imageio/PPMWriter.class imageio/PPMImageWriterSpi.class

imageio/%.class: imageio/%.java
	javac $^

gui_: $(shell find gui -type f -name "*.java") raytracer_
	javac gui/*.java

gui/%.class: gui/%.java
	javac $<

tests_: imageio_tests maths_tests vecteurs_tests

imageio_tests: imageio tests/imageio/TestImageIO.class

maths_tests: raytracer/Matrix.class tests/maths/TestMatrix.class

vecteurs_tests: raytracer/Tuple3d.class raytracer/Vector3d.class raytracer/Point3d.class tests/vecteurs/TestVecteurs.class

tests/imageio/TestImageIO.class: tests/imageio/TestImageIO.java
	javac $^

tests/maths/TestMatrix.class: tests/maths/TestMatrix.java
	javac $^

tests/vecteurs/TestVecteurs.class: tests/vecteurs/TestVecteurs.java
	javac $^

doc: force
	javadoc -private -d ./doc -link http://docs.oracle.com/javase/7/docs/api/ imageio/*.java raytracer/*.java gui/*.java tests/imageio/*.java tests/maths/*.java tests/vecteurs/*.java

clean: force
	rm -f raytracer/*.class
	rm -f imageio/*.class
	rm -f gui/*.class
	rm -f tests/imageio/*.class
	rm -f tests/maths/*.class
	rm -f tests/vecteurs/*.class

cleanall: clean force
	rm -rf doc/

force:

