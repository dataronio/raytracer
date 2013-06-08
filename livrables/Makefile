all: raytracer

raytracer: raytracer/RayTracer.java imageio gui
	javac raytracer/RayTracer.java

raytracer/RayTracer.class: $(shell find raytracer -type f)
	javac raytracer/RayTracer.java

raytracer/Matrix.class: raytracer/Matrix.java
	javac raytracer/Matrix.java

raytracer/Tuple3d.class: raytracer/Tuple3d.java
	javac raytracer/Matrix.java

raytracer/Vector3d.class: raytracer/Vector3d.java
	javac raytracer/Matrix.java

raytracer/Point3d.class: raytracer/Point3d.java
	javac raytracer/Matrix.java

imageio: imageio/PPMWriter.class imageio/PPMImageWriterSpi.class

imageio/PPMWriter.class imageio/PPMImageWriterSpi.class: imageio/PPMWriter.java imageio/PPMImageWriterSpi.java
	javac $^

gui: gui/Gui.class

gui/Gui.class: $(shell find gui -type f)
	javac gui/*.java

tests: imageio_tests maths_tests vecteurs_tests

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
	javadoc -private -d ./doc -link http://docs.oracle.com/javase/7/docs/api/ imageio/*.java raytracer/*.java gui/*.java

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

