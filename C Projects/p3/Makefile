CC = gcc
CFLAGS = -Wall -std=c99 -g

chop: chop.o edit.o text.o
chop.o: chop.c edit.h text.h

edit.o: edit.c edit.h

text.o: text.c text.h

clean:
	rm -f chop.o
	rm -f chop
	rm -f edit.o
	rm -f text.o