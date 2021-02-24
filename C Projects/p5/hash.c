/**
* @file hash.c
* @author Christine Park, cpark7
* This is the main component. It contains the main function.
* It's responsible for parsing the command-line arguments and
* using the other components to read the input file, to perform
* the SHA1 computation and to compute the HMAC if requested.
*/

#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include "buffer.h"
#include "sha1.h"
#include "hmac-sha1.h"

/**
* Starts the program.
 * @param argc number of command line arguments entered by the user
 * @param argv array of command line arguments entered by the user
 * @return exit status of program
*/
int main(int argc, char* argv[])
{

  if (argc != TWO && argc != FOUR) {
    fprintf(stderr, "usage: hash [-hmac <key>] <filename>\n");
    exit(EXIT_FAILURE);
  }

  if (argc == TWO) {
    int fileLength = strlen(argv[1]);
    char fileName[fileLength + 1];
    strcpy(fileName, argv[1]);

    Buffer* b = readFile(fileName);
    if (!b) {
      fprintf(stderr, "Can't open file: %s\n", fileName);
      exit(EXIT_FAILURE);
    }
    padBuffer(b);
    unsigned int* swapper = (unsigned int*)malloc(b->len);
    memcpy(swapper, b->data, b->len);
    int numIntsBuffer = b->len / FOUR;
    for (int i = 0; i < numIntsBuffer; i++) {
      swapBytes4(swapper + i);
    }
    memcpy(b->data, swapper, b->len);
    free(swapper);

    SHA1State state;
    initState(&state);
    unsigned char* digest = (unsigned char*)malloc(SHA1_DIGEST);
    int num64Blocks = b->len / SHA1_BLOCK;
    int charsRead = 0;
    for (int i = 0; i < num64Blocks; i++) {
      unsigned char block[SHA1_BLOCK];
      for (int j = 0; j < SHA1_BLOCK; j++) {
        block[j] = b->data[charsRead];
        charsRead++;
      }
      sha1Block(block, &state);
    }
    sha1Encode(digest, &state);
    for (int i = 0; i < SHA1_DIGEST; i++) {
      fprintf(stdout, "%02X", digest[i]);
    }
    fprintf(stdout, "\n");
    freeBuffer(b);
    free(digest);
  }
  else {
    char fileName[strlen(argv[THREE]) + 1];
    strcpy(fileName, argv[THREE]);

    char key[strlen(argv[TWO]) + 1];
    strcpy(key, argv[TWO]);
    Buffer* b = readFile(fileName);
    if (!b) {
      fprintf(stderr, "Can't open file: %s\n", fileName);
      exit(EXIT_FAILURE);
    }
    unsigned char* digest = (unsigned char*)malloc(SHA1_DIGEST);
    hmacSHA1(key, b, digest);
    free(digest);
    freeBuffer(b);
  }
  return EXIT_SUCCESS;
}
