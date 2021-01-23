/**
 * @file sha1.h
 * @author Christine Parker (cpark7)
 *
 * Header file for sha1.c
 */

#ifndef _SHA1_H_
#define _SHA1_H_

#include "buffer.h"

 /* Mechanism to conditionally expose static functions to other components.  For
    production, we can make make them static, but for testing we can disable
    the static keyword and expose functions to the test driver. */
#ifdef TESTABLE
#define test_static
#else
#define test_static static
#endif

/** Number of bytes in a block used in the SHA1 calculation. */
#define SHA1_BLOCK 64

/** Number of bytes in an SHA1 digest */
#define SHA1_DIGEST 20

/** Size of the w array of words*/
#define W_SIZE 80

/** Number of bits in a 4-byte integer */
#define NUM_BITS_INT 32

/** Number of integers to process in a single iteration of the SHA1 algorithm */
#define INTS_TO_HASH 16

/** Fourteen*/
#define FOURTEEN 14


/** Degree of which to rotate bits to the left */
#define ROTATE_5 5

/** THIRTY*/
#define THIRTY 30

/** Three*/
#define THREE 3

/** TWO */
#define TWO 2

/** FOUR*/
#define FOUR 4

/** Eigth Bytes */
#define EIGHT_BYTES 8

/** 0x80 added to end of buffer before padding*/
#define END_OF_BUFF 0x80

/** Maskd the 8-byte length block at the end of the buffer's padding */
#define MASK 0xFF

/** Value of h0 */
#define H0 0x67452301

/** Value of h1 */
#define H1 0xEFCDAB89

/** Value of h2 */
#define H2 0x98BADCFE

/** Value of h3 */
#define H3 0x10325476

/** Value of h4 */
#define H4 0xC3D2E1F0

/** Representation for the state of the SHA1 computation.  It's just 5
    unsigned 32-bit integers. Client code can create an instance
    (statically, on the stack or on the heap), but initState() needs
    to initialize it before it can be used. */
typedef struct {
  /** Fields h0 through h4 of the SHA1 state.  */
  unsigned int h0;
  unsigned int h1;
  unsigned int h2;
  unsigned int h3;
  unsigned int h4;
} SHA1State;

#endif

/**
* Swaps the first and last bytes and then the middle two bytes of an unsigned integer
* @param pointer to integer
*/
void swapBytes4(unsigned int* val);

/**
* Given the address of a SHA1State, this function initializes its fields,
* filling them in with the five constant values given in the SHA1 algorithm.
* @param state state of SHA1
*/
void initState(SHA1State* state);

/**
* This function pads the given buffer, bringing its length up to a multiple of 64 bytes,
* adding byte values as described in the SHA1 algorithm.
* @param b buffer
*/
void padBuffer(Buffer* b);

/**
* This function is used to create the final hash value (also known as a "digest").
* It transfers the 20 bytes in the h0, h1, h2, h3, and h4 state variables into a 20 byte
* unsigned char array, in big-endian order. i.e., digest[0] through digest[3] contains the
* value of h0 in big-endian order, digest[4] through digest[7] contains h1
* in big-endian order, etc.
* @param digest[SHA_DIGEST] char array for digest
* @param state state of SHA1State
*/
void sha1Block(unsigned char data[SHA1_BLOCK], SHA1State* state);

/**
 * Encodes the SHA1 hash digest using the SHA1 state values following computation of the values
 * using the SHA1 hashing algorithm. The digest is comprised of the 5 constants concatenated
 * into a 20-byte-long string.
 *
 * @param data The 20-byte array of characters that will be the final SHA1 digest
 * @param state The state of the SHA1 execution from which to derive the digest
 */
void sha1Encode(unsigned char data[SHA1_DIGEST], SHA1State* state);
