/**
* @file buffer.c
* @author Christine Park (cpark7)
*
* This component is responsible for reading and storing the input file.
* It uses a struct named Buffer to store the entire file contents in memory.
* This makes it easy to compute with and to add padding at the start of the
* SHA1 computation. The starter includes a partial implementation of this component,
* including a definition of the Buffer struct.
*
*/

#include "buffer.h"


/**
* This function dynamically allocates a Buffer struct, initializes its fields
* (a typical representation for a resizable array).
* @return buffer
*/
Buffer* makeBuffer()
{
  Buffer* b = (Buffer*)malloc(sizeof(Buffer));
  b->cap = INIT_CAP;
  b->len = 0;
  b->data = (unsigned char*)malloc(INIT_CAP * sizeof(unsigned char));
  return b;
}

/**
* This function adds a single byte to the end of the given buffer, enlarging the
* data array if necessary.
* @param b buffer
* @param byte unsigned char to append to buffer
*/
void appendBuffer(Buffer* b, unsigned char byte)
{
  if (b->len >= b->cap) {
    b->cap *= TWO;
    b->data = (unsigned char*)realloc(b->data, b->cap * sizeof(unsigned char));
  }
  b->data[b->len] = byte;
  b->len += 1;
}

/**
* This function frees all the memory for the given buffer.
* @param b buffer
*/
void freeBuffer(Buffer* b)
{
  free(b->data);
  free(b);
}

/**
* This function creates a new buffer, reads the contents of the file with the given name,
* stores it in the buffer and returns a pointer to the buffer. If the file can't be opened,
* it just returns NULL.
* @param filename name of file
* @return pointer to the buffer, null if file cannot be opened
*/
Buffer* readFile(const char* filename)
{
  FILE* f;

  f = fopen(filename, "rb");
  if (!f) {
    return NULL;
  }

  Buffer* b = makeBuffer();

  int ch = fgetc(f);

  while (ch != EOF) {
    appendBuffer(b, ch);
    ch = fgetc(f);
  }

  fclose(f);
  return b;
}
