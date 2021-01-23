/**
* @file brighten.c
* @author Christine Park (cpark7)
* Adds 32 to the intensity values (RGB) for every pixel to "brighten"
* the imange. Maximum intensity value is 255.
*/
#include <stdlib.h>
#include <stdio.h>
#include "image.h"

/** How much to add to the intensity values of a pixel. */
#define DELTA 32

/**
* Starts the program
* @return exit status of program
*/
int main()
{
  // Perform preliminary checks to verify input image file is valid
  checkType();
  int width = readDimension();
  int height = readDimension();
  checkRange();
  
  unsigned char pix[height][width][DEPTH];
  readPixels(height, width, pix);
  
  // Perform brighten operation on valid image file
  for (int i = 0; i < height; i++){
    for (int j = 0; j < width; j++){
      for (int k = 0; k < DEPTH; k++){
        int val = pix[i][j][k];
        pix[i][j][k] = val + DELTA > STD_RANGE ? STD_RANGE : (unsigned char) val + DELTA;
        }
      }
    }
  writeImage(height, width, pix);
  return EXIT_SUCCESS;
}
