/**
* @file border.c
* @author Christine Park (cpark7)
* Adds a 4-pixel border around the entire input image
*/
#include <stdlib.h>
#include <stdio.h>
#include "image.h"

/** The size of the border to put around the image. */
#define PADDING 4

/** 2 for right/left and top/bottom borders. */
#define DOUBLE 2

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
  

  
  unsigned char pixBorder[height + DOUBLE * PADDING][width + DOUBLE * PADDING][DEPTH];
  // Create Upper Border
  for (int i = 0; i < PADDING; i++){
    for (int j = 0; j < width + DOUBLE * PADDING; j++){
      for (int k = 0; k < DEPTH; k++){
        pixBorder[i][j][k] = 0;
      }
    }
  }
  
  // Create Left Border + Image + Right Border
  for (int i = 0; i < height; i++){
    for (int j = 0; j < PADDING; j++){
      for (int k = 0; k < DEPTH; k++){
        pixBorder[i + PADDING][j][k] = 0;
      }
    }
    for (int j = 0; j < width; j++){
      for (int k = 0; k < DEPTH; k++){
        int val = pix[i][j][k];
        pixBorder[i + PADDING][j + PADDING][k] = val;
      }
    }
    for (int j = 0; j < PADDING; j++){
      for (int k = 0; k < DEPTH; k++){
        pixBorder[i + PADDING][j + width + PADDING][k] = 0;
      }
    }
  }
  // Create Lower Border
  for (int i = 0; i < PADDING; i++){
    for (int j = 0; j < width + DOUBLE * PADDING; j++){
      for (int k = 0; k < DEPTH; k++){
        pixBorder[i + height + PADDING][j][k] = 0;
      }
    }
  }
  writeImage(height + DOUBLE * PADDING, width + DOUBLE * PADDING, pixBorder);
  return EXIT_SUCCESS;
}
