all: raytracer

raytracer: raytracer/RayTracer.java imageio
	javac raytracer/RayTracer.java

raytracer/RayTracer.class: raytracer/*.java
	javac raytracer/RayTracer.java

raytracer/Matrix.class: raytracer/Matrix.java
	javac raytracer/Matrix.java

imageio: imageio/PPMWriter.class imageio/PPMImageWriterSpi.class

imageio/PPMWriter.class imageio/PPMImageWriterSpi.class: imageio/PPMWriter.java imageio/PPMImageWriterSpi.java
	javac $^

tests: imageio_tests maths_tests

imageio_tests: imageio tests/imageio/TestImageIO.class

maths_tests: raytracer/Matrix.class tests/maths/TestMatrix.class

tests/imageio/TestImageIO.class: tests/imageio/TestImageIO.java
	javac $^

tests/maths/TestMatrix.class: tests/maths/TestMatrix.java
	javac $^

doc: force
	javadoc -d ./doc -link http://docs.oracle.com/javase/7/docs/api/ imageio/*.java raytracer/*.java

clean: force
	rm -f raytracer/*.class
	rm -f imageio/*.class
	rm -f tests/imageio/*.class
	rm -f tests/maths/*.class

cleanall: clean force
	rm -rf doc/

force:

