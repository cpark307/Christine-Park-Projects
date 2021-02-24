CC = gcc
CFLAGS = -Wall -std=c99 -g

reading: reading.o input.o catalog.o

input.o: input.c input.h

catalog.o: catalog.c catalog.h

clean:
	rm -f reading.o
	rm -f reading
	rm -f input.o
	rm -f catalog.o
