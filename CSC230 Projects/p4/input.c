/**
* @file input.c
* @author Christine Park (cpark7)
*
* The input component will help with reading input from the book
* list files and from the user.
*/

#include "input.h"


/**
* Reads a single  of input from the given file and returns it as a string inside
* a block of dynamically allocated memory.
* @param *fp pointer to input file
* @returns single line of input from given file as a string
* inside a block of dynamically allocated memory
*/
char *readLine( FILE *fp )
{
  char *line = malloc(sizeof(char) * BUFFER);

  if (fgets(line, BUFFER, fp) == NULL) {
    free(line);
    return NULL;
  } else {
    return line;
  }
}
