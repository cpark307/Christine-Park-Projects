/**
* @file chop.c
* @author Christine Park (cpark7)
*
* The main component of the program. Parses the command line arguments, opens the input and
* output files and uses the other components to read the input, removes the selected
* lines and columns, then writes out the resulting text
*/

#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include "edit.h"
#include "text.h"

/** number of command line arguments*/
#define NUM_ARG 3

/**
* Starts the chop program
* @param argc number of command line arguments
* @ argv[] array of command line arguments
*/
int main(int argc, char* argv[]) 
{
  int inLen = strlen(argv[argc - 2]);
  int outLen = strlen(argv[argc - 1]);
  char inFile[inLen + 1];
  strcpy(inFile, argv[argc - 2]);
  char outFile[outLen + 1];
  strcpy(outFile, argv[argc - 1]);
  FILE *fpin;

  if (strcmp(inFile, "-") == 0) {
    fpin = stdin;
  }

  else if ( ( fpin = fopen( inFile, "r" ) ) == NULL ) {
    fprintf( stderr, "Can't open file: %s\n", inFile );
    exit( EXIT_FAILURE );
  }

  readFile(fpin);
  

  for (int i = 1; i <= argc - NUM_ARG; i++) {
    if (strcmp(argv[i], "line") == 0) {
      int start = atoi(argv[i + 1]);
      if (start <= 0) {
        fprintf(stderr, "invalid arguments\nusage: chop command* (infile|-) (outfile|-)\n");
        return EXIT_FAILURE;
      }
      int end = start;
      removeLines(start - 1, end - 1);
      i++;
    } else if (strcmp(argv[i], "lines") == 0) {
      int start = atoi(argv[i + 1]);
      int end = atoi(argv[i + 2]);
      if (end < start) {
        fprintf(stderr, "invalid arguments\nusage: chop command* (infile|-) (outfile|-)\n");
        return EXIT_FAILURE;
      }
      if (start <= 0 || end <= 0) {
        fprintf(stderr, "invalid arguments\nusage: chop command* (infile|-) (outfile|-)\n");
        return EXIT_FAILURE;
      }
      removeLines(start - 1, end - 1);
      i += 2;
    } else if (strcmp(argv[i], "col") == 0) {
      int start = atoi(argv[i + 1]);
      if (start <= 0) {
        fprintf(stderr, "invalid arguments\nusage: chop command* (infile|-) (outfile|-)\n");
        return EXIT_FAILURE;
      }
      int end = start;
      removeCols(start - 1, end - 1);
      i++;
    } else if (strcmp(argv[i], "cols") == 0) {
      int start = atoi(argv[i + 1]);
      int end = atoi(argv[i + 2]);
      if (end < start) {
        fprintf(stderr, "invalid arguments\nusage: chop command* (infile|-) (outfile|-)\n");
        return EXIT_FAILURE;
      }
      if (start <= 0 || end <= 0) {
        fprintf(stderr, "invalid arguments\nusage: chop command* (infile|-) (outfile|-)\n");
        return EXIT_FAILURE;
      }
      removeCols(start - 1, end - 1);
      i += 2;
    } else {
      fprintf(stderr, "invalid arguments\nusage: chop command* (infile|-) (outfile|-)\n");
      return EXIT_FAILURE;
    }
  }
  
  FILE *fpout;
  if (strcmp(outFile, "-") == 0) {
    fpout = stdout;
  } else if ( ( fpout = fopen( outFile, "w" ) ) == NULL ) {
    fprintf( stderr, "Can't open file: %s\n", outFile );
    exit( EXIT_FAILURE );
  }
  
  writeFile(fpout);

  return EXIT_SUCCESS;
}
