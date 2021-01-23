/**
* @file input.h
* @author Christine Park (cpark7)
*
* The header file for input.c. The input component will help
* with reading input from the book list files and from the user.
*/

#include <stdlib.h>
#include <stdio.h>
#include <string.h>

/** Buffer size for reading book entries */
#define BUFFER 1024

/** TWO */
#define TWO 2
/** THREE */
#define THREE 3
/** FIVE */
#define FIVE 5
/** SIX */
#define SIX 6
/** SEVEN */
#define SEVEN 7
/** TEN */
#define TEN 10

/**
* Reads a single  of input from the given file and returns it as a string inside
* a block of dynamically allocated memory.
* @param *fp pointer to input file
* @returns single line of input from given file as a string
* inside a block of dynamically allocated memory
*/
char *readLine( FILE *fp );
