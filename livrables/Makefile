all: imageio

imageio: imageio/PPMWriter.class imageio/PPMImageWriterSpi.class

imageio/%.class: imageio/%.java
	javac $<

tests: imageio_tests

imageio_tests: imageio tests/imageio/TestImageIO.class

tests/imageio/%.class: tests/imageio/%.java
	javac $<

doc: force
	javadoc -d ./doc -link http://docs.oracle.com/javase/7/docs/api/ imageio

clean: force
	rm -f imageio/*.class
	rm -f tests/imageio/*.class

force:

