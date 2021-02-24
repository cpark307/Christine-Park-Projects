/**
* @file text.c
* @author Christine Park (cpark7)
* Defines the array of strings used to represent lines of text from the 
* input file. Alos has functions to read this representation from a file
* and wrtie the resulting text out to a file.
*/
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include "text.h"

/** Number of lines in 2D array of characters */
int lineCount = 0;
/** 2D array of characters */
char twoDArray[MAX_ROWS][MAX_COLS];

/**
* Reads text from the given file and stores it in the global
* text representation. If the program reading from the standard input, then
* the given file pointer will be stdin
* @param *fp input file
*/
void readFile( FILE *fp )
{
  int charCount = 0;
  int next = fgetc(fp);
  if (next != EOF) {
    lineCount++;
    charCount++;
  }

  while(next != EOF) {
    if (charCount >= MAX_COLS && next != '\0') {
      fprintf(stderr, "Line too long\n");
      exit(1);
    }
    if (lineCount >= MAX_ROWS) {
      fprintf(stderr, "Too many lines\n");
      exit(1);
    }
    twoDArray[lineCount - 1][charCount - 1] = (char) next;
    if (next == '\n') {
      lineCount++;
      charCount = 0;
    }
    next = fgetc(fp);
    charCount++;
  }
  int fclose(FILE *fp);
}

/**
 * Writes text from the potentially edited 2D character array
 * to the output steam specified by the client.
 * @param fp Output stream to which to write edited text
 */
void writeFile( FILE *fp )
{
  for (int i = 0; i < lineCount; i++) {
    int len = strlen(*(twoDArray + i));
    for (int j = 0; j < len; j++) {
      fprintf(fp, "%c", twoDArray[i][j]);
    }
  }
}