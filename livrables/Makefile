all: imageio

imageio: imageio/PPMWriter.class imageio/PPMImageWriterSpi.class

imageio/PPMWriter.class imageio/PPMImageWriterSpi.class: imageio/PPMWriter.java imageio/PPMImageWriterSpi.java
	javac $^

tests: imageio_tests

imageio_tests: imageio tests/imageio/TestImageIO.class

tests/imageio/TestImageIO.class: tests/imageio/TestImageIO.java
	javac $^

doc: force
	javadoc -d ./doc -link http://docs.oracle.com/javase/7/docs/api/ imageio/PPMWriter.java imageio/PPMImageWriterSpi.java

clean: force
	rm -f imageio/*.class
	rm -f tests/imageio/*.class

cleanall: clean force
	rm -rf doc/


force:

