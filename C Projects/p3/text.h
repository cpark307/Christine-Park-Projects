/**
* @file text.h
* @author Christine Park (cpark7)
* Header file for text.c
*/

/** Maximum nmber of rows allowed in 2D text array */
#define MAX_ROWS 10000
/** Maximum nmber of columns allowed in 2D text array */
#define MAX_COLS 101

/** Number of lines in 2D array of characters */
extern int lineCount;
/** 2D array of characters */
extern char twoDArray[MAX_ROWS][MAX_COLS];


/**
* Reads text from the given file and stores it in the global
* text representation. If the program reading from the standard input, then
* the given file pointer will be stdin
* @param *fp input file
*/
void readFile(FILE *fp);

/**
* Writes out the text from the global text representation to the given output file.
* If the program is writing to standard output, then the given file pointer
* should be stdout.
* 
*/
void writeFile(FILE *fp);
