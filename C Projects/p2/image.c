/**
* @file image.c
* @author Christine Park (cpark7)
* Provides functions for reading and writing PPM images
* and making sure they are in the right format
*/
#include "image.h"
#include <stdlib.h>
#include <stdio.h>
#include <string.h>

#define VALID 3

/**
* Used to read the magic number at the start of an input image and check to make
* sure the value is correct and exits (with the correct exit status) if it is not.
*/
void checkType()
{
  char magicNumber[VALID];
  scanf("%s", magicNumber);
  if (strcmp(magicNumber, MAGIC_NUMBER) != 0){
    exit (BAD_HEADER);
  }
}

/**
* Reads the width and height in the image header
* @return width/height of image file listed in header
*/
int readDimension()
{
  int dim = -1;
  if (scanf("%d", &dim) != 1){
    exit(BAD_HEADER);
  }
  if (dim < 0){
    exit(BAD_HEADER);
  }
  return dim;
}

/**
* Reads the maximum intensity value at the end of the image header
* Checks to make sure it is set to 255 and exits appropriately if not
*/
void checkRange()
{
  int range = -1;
  if (scanf("%d", &range) != 1){
   exit(BAD_HEADER);
  }
  if (range != STD_RANGE){
    exit(BAD_HEADER);
  }
}

/**
* Reads all the pixel data for an image. Values are stored in the given array
* @param height number of rows in ppm file
* @param width number of columns in ppm file
* @param pix 3D array containing pixel data that has the pixel parameters of the input file
*/
void readPixels( int height, int width, unsigned char pix[ height ][ width ][ DEPTH ] )
{
  for (int i = 0; i < height; i++){
    for (int j = 0; j < width; j++){
      for (int k = 0; k < DEPTH; k++){
        int val = 0;
        if (scanf("%d", &val) != 1){
           exit(BAD_PIXEL);
        }
        if (val < 0 || val > STD_RANGE){
          exit(BAD_PIXEL);
        }
        pix[i][j][k] = (unsigned char) val;
      }
    }
  }
}

/**
* Writes the output image to the standard output.
* @param height height of ppm file to be written
* @param width width of ppm file to be written
* @param pix 3D array containing pixel data that will be written to the file
*/
void writeImage( int height, int width, unsigned char pix[ height ][ width ][ DEPTH ] )
{
  printf("%s\n", MAGIC_NUMBER);
  printf("%d %d\n", width, height);
  printf("%d\n", STD_RANGE);
  for (int i = 0; i < height; i++) {
    for (int j = 0; j < width; j++) {
      for (int k = 0; k < DEPTH; k++) {
        if (j < width - 1) {
          printf ( "%u ", pix [i][j][k] );
        } else if (k < DEPTH - 1) {
          printf ( "%u ", pix [i][j][k] );
        } else {
          printf ( "%u", pix [i][j][k] );
        }
      }
    }
    putchar('\n');
  }
}
