/**
*/

#include "sha1.h"
#include <stdio.h>
#include <stdint.h>
#include <string.h>

/**
* Swaps the first and last bytes and then the middle two bytes of an unsigned integer
*/
void swapBytes4(unsigned int* val)
{
  char* p = (char*)val;

  char t = p[0];
  p[0] = p[THREE];
  p[THREE] = t;

  t = p[1];
  p[1] = p[TWO];
  p[TWO] = t;
}

/** Constants, to mix in some random-looking bits during the SHA1
* calculation.  80 constants for 80 iterations.  The constants for one
* round (iterations 0-19, 20-39, 40-59, 60-79) all have the same value.
*/
static unsigned int k[W_SIZE] =
{
0x5A827999, 0x5A827999, 0x5A827999, 0x5A827999, 0x5A827999, 0x5A827999, 0x5A827999, 0x5A827999,
 0x5A827999, 0x5A827999, 0x5A827999, 0x5A827999,
 0x5A827999, 0x5A827999, 0x5A827999, 0x5A827999, 0x5A827999, 0x5A827999, 0x5A827999, 0x5A827999,
0x6ED9EBA1, 0x6ED9EBA1, 0x6ED9EBA1, 0x6ED9EBA1, 0x6ED9EBA1, 0x6ED9EBA1, 0x6ED9EBA1,
 0x6ED9EBA1, 0x6ED9EBA1,
 0x6ED9EBA1, 0x6ED9EBA1, 0x6ED9EBA1, 0x6ED9EBA1, 0x6ED9EBA1, 0x6ED9EBA1, 0x6ED9EBA1, 0x6ED9EBA1,
  0x6ED9EBA1, 0x6ED9EBA1, 0x6ED9EBA1,
0x8F1BBCDC, 0x8F1BBCDC, 0x8F1BBCDC, 0x8F1BBCDC, 0x8F1BBCDC, 0x8F1BBCDC, 0x8F1BBCDC, 0x8F1BBCDC,
 0x8F1BBCDC, 0x8F1BBCDC, 0x8F1BBCDC,
 0x8F1BBCDC, 0x8F1BBCDC, 0x8F1BBCDC, 0x8F1BBCDC, 0x8F1BBCDC, 0x8F1BBCDC, 0x8F1BBCDC,
  0x8F1BBCDC, 0x8F1BBCDC,
0xCA62C1D6, 0xCA62C1D6, 0xCA62C1D6, 0xCA62C1D6, 0xCA62C1D6, 0xCA62C1D6, 0xCA62C1D6,
 0xCA62C1D6, 0xCA62C1D6, 0xCA62C1D6, 0xCA62C1D6, 0xCA62C1D6, 0xCA62C1D6, 0xCA62C1D6, 0xCA62C1D6,
 0xCA62C1D6, 0xCA62C1D6, 0xCA62C1D6, 0xCA62C1D6, 0xCA62C1D6,
};

/**
* Given the address of a SHA1State, this function initializes its fields,
* filling them in with the five constant values given in the SHA1 algorithm.
* @param state state of SHA1
*/
void initState(SHA1State* state)
{
  state->h0 = H0;
  state->h1 = H1;
  state->h2 = H2;
  state->h3 = H3;
  state->h4 = H4;
}

/**
* This function pads the given buffer, bringing its length up to a multiple of 64 bytes,
* adding byte values as described in the SHA1 algorithm.
* @param b buffer
*/
void padBuffer(Buffer* b)
{
  unsigned long ilen = (unsigned long)(EIGHT_BYTES * b->len);

  appendBuffer(b, END_OF_BUFF);
  while (b->len % SHA1_BLOCK != (SHA1_BLOCK - EIGHT_BYTES)) {
    appendBuffer(b, 0x00);
  }
  for (int i = EIGHT_BYTES - 1; i >= 0; i--) {
    appendBuffer(b, (ilen >> (EIGHT_BYTES * i)) & MASK);
  }
}


/**
* This function is used to create the final hash value (also known as a "digest").
* It transfers the 20 bytes in the h0, h1, h2, h3, and h4 state variables into a 20 byte
* unsigned char array, in big-endian order. i.e., digest[0] through digest[3] contains the
* value of h0 in big-endian order, digest[4] through digest[7] contains h1
* in big-endian order, etc.
*/
void sha1Encode(unsigned char digest[SHA1_DIGEST], SHA1State* state)
{
  unsigned int a = state->h0;
  unsigned int* ap = (unsigned int*)malloc(FOUR);
  free(ap);
  ap = &a;
  swapBytes4(ap);
  unsigned char* h0p = (unsigned char*)malloc(FOUR);
  memcpy(h0p, ap, FOUR);

  unsigned int b = state->h1;
  unsigned int* bp = (unsigned int*)malloc(FOUR);
  free(bp);
  bp = &b;
  swapBytes4(bp);
  unsigned char* h1p = (unsigned char*)malloc(FOUR);
  memcpy(h1p, bp, FOUR);

  unsigned int c = state->h2;
  unsigned int* cp = (unsigned int*)malloc(FOUR);
  free(cp);
  cp = &c;
  swapBytes4(cp);
  unsigned char* h2p = (unsigned char*)malloc(FOUR);
  memcpy(h2p, cp, FOUR);

  unsigned int d = state->h3;
  unsigned int* dp = (unsigned int*)malloc(FOUR);
  free(dp);
  dp = &d;
  swapBytes4(dp);
  unsigned char* h3p = (unsigned char*)malloc(FOUR);
  memcpy(h3p, dp, FOUR);

  unsigned int e = state->h4;
  unsigned int* ep = (unsigned int*)malloc(FOUR);
  free(ep);
  ep = &e;
  swapBytes4(ep);
  unsigned char* h4p = (unsigned char*)malloc(FOUR);
  memcpy(h4p, ep, FOUR);

  for (int i = 0; i < FOUR; i++) {
    digest[i] = *(h0p + i);
  }
  for (int i = FOUR; i < EIGHT_BYTES; i++) {
    digest[i] = *(h1p + (i - FOUR));
  }
  for (int i = EIGHT_BYTES; i < FOUR * THREE; i++) {
    digest[i] = *(h2p + (i - EIGHT_BYTES));
  }
  for (int i = FOUR * THREE; i < FOUR * FOUR; i++) {
    digest[i] = *(h3p + (i - FOUR * THREE));
  }
  for (int i = FOUR * FOUR; i < FOUR * ROTATE_5; i++) {
    digest[i] = *(h4p + (i - FOUR * FOUR));
  }

  free(h0p);
  free(h1p);
  free(h2p);
  free(h3p);
  free(h4p);
}

/**
* Version0 of function f that SHA1 alg uses.
* @param b integer
* @param c integer
* @param d integer
*/
test_static unsigned int fVersion0(unsigned int b, unsigned int c, unsigned int d)
{
  return (b & c) | (~b & d);
}

/**
* Version1 of function f that SHA1 alg uses.
* @param b integer
* @param c integer
* @param d integer
*/
test_static unsigned int fVersion1(unsigned int b, unsigned int c, unsigned int d)
{
  return (b ^ c ^ d);
}

/**
* Version2 of function f that SHA1 alg uses.
* @param b integer
* @param c integer
* @param d integer
*/
test_static unsigned int fVersion2(unsigned int b, unsigned int c, unsigned int d)
{
  return (b & c) | (b & d) | (c & d);
}

/**
* Version3 of function f that SHA1 alg uses.
* @param b integer
* @param c integer
* @param d integer
*/
test_static unsigned int fVersion3(unsigned int b, unsigned int c, unsigned int d)
{
  return (b ^ c ^ d);
}

/**
* This function implements the rotate left operation from the SHA1 algorithm,
* shifting the given value to the left by s bits, with wraparound. It returns
* the resulting value.
* @param value given value
* @param s number of bits to shift by
* @return resulting value
*/
test_static unsigned int rotateLeft(unsigned int value, int s)
{
  return ((value << s) | (value >> (NUM_BITS_INT - s)));
}

/**
* This function implements an iteration of the SHA1 algorithm on
* a 64-byte block (interpreted as 16 unsigned integers).
* @param data[16] data block
* @param a values from SHA1 alg description
* @param b values from SHA1 alg description
* @param c values from SHA1 alg description
* @param d values from SHA1 alg description
* @param e values from SHA1 alg description
* @param i iteration number, value between 0 and 79
*/
test_static void sha1Iteration(unsigned int data[INTS_TO_HASH], unsigned int* a, unsigned int* b,
  unsigned int* c, unsigned int* d, unsigned int* e, int i)
{
  unsigned int w[W_SIZE];
  for (int i = 0; i < INTS_TO_HASH; i++) {
    w[i] = data[i];
  }
  for (int i = INTS_TO_HASH; i < W_SIZE; i++) {
    w[i] = rotateLeft((w[i - THREE] ^ w[i - EIGHT_BYTES] ^ w[i - FOURTEEN] ^
      w[i - INTS_TO_HASH]), 1);
  }

  unsigned int temp;
  if (i < SHA1_DIGEST) {
    temp = *e + rotateLeft(*a, ROTATE_5) + w[i] + k[i] + fVersion0(*b, *c, *d);
  }
  if (i >= SHA1_DIGEST && i < SHA1_DIGEST * TWO) {
    temp = *e + rotateLeft(*a, ROTATE_5) + w[i] + k[i] + fVersion1(*b, *c, *d);
  }
  if (i >= SHA1_DIGEST * TWO && i < SHA1_DIGEST * THREE) {
    temp = *e + rotateLeft(*a, ROTATE_5) + w[i] + k[i] + fVersion2(*b, *c, *d);
  }
  if (i >= SHA1_DIGEST * THREE && i < W_SIZE) {
    temp = *e + rotateLeft(*a, ROTATE_5) + w[i] + k[i] + fVersion3(*b, *c, *d);
  }
  *e = *d;
  *d = *c;
  *c = rotateLeft(*b, THIRTY);
  *b = *a;
  *a = temp;
}

/**
* This function performs 80 SHA1 iterations on the given block of bytes,
* updating the given state.
* @param data[SHA1_BLOCK} datt block
* @param state state of SHA1
*/
void sha1Block(unsigned char data[SHA1_BLOCK], SHA1State* state)
{
  unsigned int a = state->h0;
  unsigned int b = state->h1;
  unsigned int c = state->h2;
  unsigned int d = state->h3;
  unsigned int e = state->h4;

  unsigned char* dataPtr = (unsigned char*)malloc(SHA1_BLOCK);
  free(dataPtr);
  dataPtr = data;
  unsigned int* block = (unsigned int*)malloc(SHA1_BLOCK);
  memcpy(block, dataPtr, SHA1_BLOCK);

  for (int i = 0; i < W_SIZE; i++) {
    sha1Iteration(block, &a, &b, &c, &d, &e, i);
  }

  state->h0 += a;
  state->h1 += b;
  state->h2 += c;
  state->h3 += d;
  state->h4 += e;
  free(block);

}
