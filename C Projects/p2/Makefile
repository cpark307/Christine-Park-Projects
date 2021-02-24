CC = gcc
CFLAGS = -Wall -std=c99 -g

brighten: brighten.o image.o
brigthen.o: brighten.c image.h

border: border.o image.o
border.o: border.c image.h

blur: blur.o image.o
blur.o: blur.c image.h

image.o: image.c image.h

clean:
	rm -f output.ppm
	rm -f *.java
	rm -f *.class
	rm -f blur
	rm -f border
	rm -f brighten
	rm -f brighten.o
	rm -f border.o
	rm -f blur.o