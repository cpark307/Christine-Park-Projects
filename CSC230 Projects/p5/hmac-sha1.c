/**
* @file hmac-sha1.c
* @author Christine Park (cpark7)
*
* Defines a single utility hmacSHA1() for performing the HMAC operation for a given key and input
*/

#include "hmac-sha1.h"
#include "sha1.h"

/**
* Padding function for HMAC
* @param block[INIT CAP] block
* @param kstr key string
* @param c character to pad with
*/
static void makeHMACPad(unsigned char block[INIT_CAP], char* kstr, unsigned char c)
{
  memset(block, 0x00, INIT_CAP);
  memcpy(block, kstr, strlen(kstr));
  for (int i = 0; i < INIT_CAP; i++) {
    block[i] ^= c;
  }
  for (int i = 0; i < INIT_CAP; i++) {
    fprintf(stderr, "%c\n", block[i]);
  }
}

/**
* Run SHA1
* @param block[INIT CAP] block
* @param b buffer
* @param state state of SHA1State
*/
static int runSHA1(unsigned char block[INIT_CAP], Buffer *b, int start, SHA1State *state)
{
  int charsRead = start;
  for (int i = 0; i <= INIT_CAP - 1; i++) {
    block[i] = b->data[charsRead];
    charsRead++;
  }
  sha1Block(block, state);
  return charsRead;
}

/**
* This function performs the HMAC-SHA1. It takes a key as a string of characters,
* a pointer to a Buffer struct, and a pointer to an area of memory to store the
* digest (using sha1Encode()).
*/
void hmacSHA1(char* kstr, Buffer* b, unsigned char digest[SHA1_DIGEST]) {
  unsigned char innerPad[INIT_CAP];
  unsigned char outerPad[INIT_CAP];
  makeHMACPad(innerPad, kstr, IPAD_CHAR);
  makeHMACPad(outerPad, kstr, OPAD_CHAR);
  Buffer *iPadMessage = makeBuffer();
  for (int i = 0; i < INIT_CAP; i++) {
    appendBuffer(iPadMessage, innerPad[i]);
  }
  for (int i = 0; i < b->len; i++) {
    appendBuffer(iPadMessage, *(b->data + i));
  }

  padBuffer(iPadMessage);

  unsigned int* swapper = (unsigned int*)malloc(iPadMessage->len);
  memcpy(swapper, iPadMessage->data, iPadMessage->len);
  int numIntsBuffer = iPadMessage->len / FOUR;
  for (int i = 0; i < numIntsBuffer; i++) {
    swapBytes4(swapper + i);
  }
  memcpy(iPadMessage->data, swapper, iPadMessage->len);
  free(swapper);

  SHA1State state;
  initState(&state);


  int num64Blocks = iPadMessage->len / INIT_CAP;

  int charsRead = 0;
  for (int i = 0; i < num64Blocks; i++) {
    unsigned char block[INIT_CAP];
    charsRead = runSHA1(block, iPadMessage, charsRead, &state);
  }
  sha1Encode(digest, &state);

  freeBuffer(iPadMessage);

  initState(&state);

  Buffer *outerPadDigest = makeBuffer();
  for (int i = 0; i < INIT_CAP; i++) {
    appendBuffer(outerPadDigest, outerPad[i]);
  }
  for (int i = 0; i < SHA1_DIGEST; i++) {
    appendBuffer(outerPadDigest, digest[i]);
  }
  padBuffer(outerPadDigest);
  unsigned int* swapper2 = (unsigned int*)malloc(outerPadDigest->len);
  memcpy(swapper2, outerPadDigest->data, outerPadDigest->len);
  int numIntsBuffer2 = outerPadDigest->len / FOUR;
  for (int i = 0; i < numIntsBuffer2; i++) {
    swapBytes4(swapper2 + i);
  }
  memcpy(outerPadDigest->data, swapper2, outerPadDigest->len);
  free(swapper2);

  int num64Blocks2 = outerPadDigest->len / INIT_CAP;

  int charsRead2 = 0;
  for (int i = 0; i < num64Blocks2; i++) {
    unsigned char block[INIT_CAP];
    charsRead2 = runSHA1(block, outerPadDigest, charsRead2, &state);
  }
  sha1Encode(digest, &state);
  freeBuffer(outerPadDigest);
  for (int i = 0; i < SHA1_DIGEST; i++) {
    fprintf(stdout, "%02X", digest[i]);
  }
  fprintf(stdout, "\n");
}
