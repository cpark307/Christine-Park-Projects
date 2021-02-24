/**
* @file hmac-sha1.h
* @author Christine Park, cpark7
* Header file for hmac-sha1.c
*/

#include "buffer.h"
#include "sha1.h"

/** Inner pad */
#define IPAD_CHAR 0x36
/** Outer pad */
#define OPAD_CHAR 0x5C

/**
* This function performs the HMAC-SHA1. It takes a key as a string of characters,
* a pointer to a Buffer struct, and a pointer to an area of memory to store the
* digest (using sha1Encode()).
*/
void hmacSHA1( char *kstr, Buffer *b, unsigned char digest[ SHA1_DIGEST ]);
