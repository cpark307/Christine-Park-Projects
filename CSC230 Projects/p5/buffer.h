/**
* @file buffer.h
* @author Christine Park (cpark7)
*
* Header file for buffer.c
*/

#include <stdlib.h>
#include <stdio.h>
#include <string.h>

#ifndef _BUFFER_H_
#define _BUFFER_H_

/** initial capacity */
#define INIT_CAP 64

/** TWO */
#define TWO 2

/** Representation for the contents of an input file, copied to memory. */
typedef struct {
  /** Array of bytes from the file (not stored as a string - no NULL termination). */
  unsigned char *data;

  /** Number of currently used bytes in the data array. */
  unsigned int len;

  /** Capacity of the data array (it's typically over-allocated. */
  unsigned int cap;
} Buffer;

/**
* This function dynamically allocates a Buffer struct, initializes its fields
* (a typical representation for a resizable array).
* @return buffer
*/
Buffer *makeBuffer();

/**
* This function adds a single byte to the end of the given buffer, enlarging
* the data array if necessary.
* @param b buffer
* @param byte unsigned char to append to buffer
*/
void appendBuffer( Buffer *b, unsigned char byte );

/**
* This function frees all the memory for the given buffer.
* @param b buffer
*/
void freeBuffer( Buffer *b );

/**
* This function creates a new buffer, reads the contents of the file with the given name,
* stores it in the buffer and returns a pointer to the buffer. If the file can't be opened,
* it just returns NULL.
* @param filename name of file
* @return pointer to the buffer, null if file cannot be opened
*/
Buffer *readFile( const char *filename );

#endif
