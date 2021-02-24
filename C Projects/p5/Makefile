CC = gcc
CFLAGS = -Wall -std=c99 -g -DTESTABLE

hash: hash.o buffer.o sha1.o hmac-sha1.o
hash.o: hash.c buffer.h sha1.h hmac-sha1.h

testdriver: testdriver.c buffer.o sha1.o

buffer.o: buffer.c buffer.h
sha1.o: sha1.c sha1.h
hmac-sha1.o: hmac-sha1.c hmac-sha1.h


clean:
	rm -f buffer.o
	rm -f hash
	rm -f hash.o 
	rm -f sha1.o
	rm -f hmac-sha1.o
	rm -f testdriver.o
	rm -f testdriver
      