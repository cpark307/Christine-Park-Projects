/**
 * @file blur.c
 * @author Christine Park
 * Applies a 3x3 Gaussian blur to all pixels of the input image
 */
#include <stdlib.h>
#include <stdio.h>
#include "image.h"

/** Gaussian blur matrix dimension*/
#define GAUSS_MAT 3
/** Weight 4 */
#define WEIGHT_4 4

/**
 */
int main()
{
  checkType();
  int width = readDimension();
  int height = readDimension();
  checkRange();
  unsigned char pix[height][width][DEPTH];
  readPixels(height, width, pix);
  unsigned char pixBlur[height][width][DEPTH];
  for (int i = 0; i < height; i++) {
    for (int j = 0; j < width; j++) {
      for (int k = 0; k < DEPTH; k++) {

        int pixelValue = pix[i][j][k];
        pixBlur[i][j][k] = (unsigned char) pixelValue;
      }
    }
  }
  int weight[GAUSS_MAT][GAUSS_MAT] = {
    [0] = { 1, 2, 1 },
    [1] = { 2, WEIGHT_4, 2 },
    [2] = { 1, 2, 1 }
  };
  for (int i = 0; i < height; i++) {
    for (int j = 0; j < width; j++) {
      for (int k = 0; k < DEPTH; k++) {
        int pix5 = pix[i][j][k];
        if (height == 1 && width == 1) {
          pixBlur[i][j][k] = (unsigned char) pix5;
        }
        else if (height == 1 && width > 1) {
          if (j == 0) {
            int pix6 = pix[i][j + 1][k];
            int sum = weight[1][1] * pix5 + weight[1][2] * pix6;
            int weightSum = weight[1][1] + weight[1][2];
            int avg = sum / weightSum;
            pixBlur[i][j][k] = (unsigned char) avg;
          }
          else if (j > 0 && j < width - 1) {
            int pix6 = pix[i][j + 1][k];
            int pix4 = pix[i][j - 1][k];
            int sum = weight[1][1] * pix5 +
              weight[1][0] * pix4 + weight[1][2] * pix6;
            int weightSum = weight[1][1] + weight[1][0] + weight[1][2];
            int avg = sum / weightSum;
            pixBlur[i][j][k] = (unsigned char) avg;
          }

          else if (j == width - 1) {
            int pix4 = pix[i][j - 1][k];
            int sum = weight[1][1] * pix5 + weight[1][0] * pix4;
            int weightSum = weight[1][1] + weight[1][0];
            int avg = sum / weightSum;
            pixBlur[i][j][k] = (unsigned char) avg;
          }
        }
        else if (height > 1 && width == 1) {
          if (i == 0) {
            int pix8 = pix[i + 1][j][k];
            int sum = weight[1][1] * pix5 +
              weight[2][1] * pix8;
            int weightSum = weight[1][1] + weight[2][1];
            int avg = sum / weightSum;
            pixBlur[i][j][k] = (unsigned char) avg;
          }
          else if (i > 0 && i < height - 1) {
            int pix2 = pix[i - 1][j][k];
            int pix8 = pix[i + 1][j][k];
            int sum = weight[1][1] * pix5 +
              weight[0][1] * pix2 + weight[2][1] * pix8;
            int weightSum = weight[1][1] + weight[0][1] + weight[2][1];
            int avg = sum / weightSum;
            pixBlur[i][j][k] = (unsigned char) avg;
          }
          else if (i == height - 1) {
            int pix2 = pix[i - 1][j][k];
            int sum = weight[1][1] * pix5 + weight[0][1] * pix2;
            int weightSum = weight[1][1] + weight[0][1];
            int avg = sum / weightSum;
            pixBlur[i][j][k] = (unsigned char) avg;
          }
        }
        else if (i == 0 && j == 0) {
          int pix6 = pix[i][j + 1][k];
          int pix8 = pix[i + 1][j][k];
          int pix9 = pix[i + 1][j + 1][k];
          int sum = weight[1][1] * pix5 + weight[1][2] *
            pix6 + weight[2][1] * pix8 +
            weight[2][2] * pix9;
          int weightSum = weight[1][1] + weight[1][2] +
            weight[2][1] + weight[2][2];
          int avg = sum / weightSum;
          pixBlur[i][j][k] = (unsigned char) avg;
        }
        else if (i == 0 && j > 0 && j < width - 1) {
          int pix6 = pix[i][j + 1][k];
          int pix4 = pix[i][j - 1][k];
          int pix8 = pix[i + 1][j][k];
          int pix7 = pix[i + 1][j - 1][k];
          int pix9 = pix[i + 1][j + 1][k];
          int sum = weight[1][1] * pix5 +
            weight[1][0] * pix4 + weight[1][2] * pix6 +
            weight[2][0] * pix7 + weight[2][2] * pix9 +
            weight[2][1] * pix8;
          int weightSum = weight[1][1] + weight[1][0] + weight[1][2] +
            weight[2][0] + weight[2][2] + weight[2][1];
          int avg = sum / weightSum;
          pixBlur[i][j][k] = (unsigned char) avg;
        }
        else if (i == 0 && j == width - 1) {
          int pix4 = pix[i][j - 1][k];
          int pix8 = pix[i + 1][j][k];
          int pix7 = pix[i + 1][j - 1][k];
          int sum = weight[1][1] * pix5 + weight[1][0] *
            pix4 + weight[2][1] * pix8 + weight[2][0] * pix7;
          int weightSum = weight[1][1] + weight[1][0] +
            weight[2][1] + weight[2][0];
          int avg = sum / weightSum;
          pixBlur[i][j][k] = (unsigned char) avg;
        }
        else if (i == height - 1 && j == 0) {
          int pix6 = pix[i][j + 1][k];
          int pix2 = pix[i - 1][j][k];
          int pix3 = pix[i - 1][j + 1][k];
          int sum = weight[1][1] * pix5 + weight[1][2] *
          pix6 + weight[0][1] * pix2 +
          weight[0][2] * pix3;
          int weightSum = weight[1][1] + weight[1][2] +
          weight[0][1] + weight[0][2];
          int avg = sum / weightSum;
          pixBlur[i][j][k] = (unsigned char) avg;
        }
        else if (i == height - 1 && j == width - 1) {
          int pix4 = pix[i][j - 1][k];
          int pix2 = pix[i - 1][j][k];
          int pix1 = pix[i - 1][j - 1][k];
          int sum = weight[1][1] * pix5 + weight[1][0] *
            pix4 + weight[0][1] * pix2 +
            weight[0][0] * pix1;
          int weightSum = weight[1][1] + weight[1][0] +
            weight[0][1] + weight[0][0];
          int avg = sum / weightSum;
          pixBlur[i][j][k] = (unsigned char) avg;
        }
        else if (j == 0 && i > 0 && i < height - 1) {
          int pix2 = pix[i - 1][j][k];
          int pix6 = pix[i][j + 1][k];
          int pix8 = pix[i + 1][j][k];
          int pix9 = pix[i + 1][j + 1][k];
          int pix3 = pix[i - 1][j + 1][k];
          int sum = weight[1][1] * pix5 +
            weight[0][1] * pix2 + weight[1][2] * pix6 +
            weight[2][1] * pix8 + weight[2][2] * pix9 +
            weight[0][2] * pix3;
          int weightSum = weight[1][1] + weight[1][2] + weight[0][1] +
            weight[0][2] + weight[2][1] + weight[2][2];
          int avg = sum / weightSum;
          pixBlur[i][j][k] = (unsigned char) avg;
        }
        else if (j == width - 1 && i > 0 && i < height - 1) {
          int pix2 = pix[i - 1][j][k];
          int pix4 = pix[i][j - 1][k];
          int pix8 = pix[i + 1][j][k];
          int pix7 = pix[i + 1][j - 1][k];
          int pix1 = pix[i - 1][j - 1][k];
          int sum = weight[1][1] * pix5 +
            weight[0][1] * pix2 + weight[1][0] * pix4 +
            weight[0][0] * pix1 + weight[2][0] * pix7 +
            weight[2][1] * pix8;
          int weightSum = weight[1][1] + weight[1][2] + weight[0][1] +
            weight[0][2] + weight[2][1] + weight[2][2];
          int avg = sum / weightSum;
          pixBlur[i][j][k] = (unsigned char) avg;
        }
        else if (i == height - 1 && j > 0 && j < width - 1) {
          int pix6 = pix[i][j + 1][k];
          int pix4 = pix[i][j - 1][k];
          int pix2 = pix[i - 1][j][k];
          int pix1 = pix[i - 1][j - 1][k];
          int pix3 = pix[i - 1][j + 1][k];
          int sum = weight[1][1] * pix5 +
            weight[1][0] * pix4 + weight[1][2] * pix6 +
            weight[0][0] * pix1 + weight[0][2] * pix3 +
            weight[0][1] * pix2;
          int weightSum = weight[1][1] + weight[1][0] + weight[1][2] +
            weight[0][0] + weight[0][2] + weight[0][1];
          int avg = sum / weightSum;
          pixBlur[i][j][k] = (unsigned char) avg;
        }
        else {
          int pix6 = pix[i][j + 1][k];
          int pix4 = pix[i][j - 1][k];
          int pix2 = pix[i - 1][j][k];
          int pix1 = pix[i - 1][j - 1][k];
          int pix3 = pix[i - 1][j + 1][k];
          int pix8 = pix[i + 1][j][k];
          int pix7 = pix[i + 1][j - 1][k];
          int pix9 = pix[i + 1][j + 1][k];
          int sum = weight[1][1] * pix5 +
            weight[1][0] * pix4 + weight[1][2] * pix6 +
            weight[0][0] * pix1 + weight[0][2] * pix3 +
            weight[0][1] * pix2 + weight[2][1] * pix8 +
            weight[2][0] * pix7 + weight[2][2] * pix9;
          int weightSum = weight[1][1] + weight[1][0] + weight[1][2] +
            weight[0][0] + weight[0][2] + weight[0][1] +
            weight[2][0] + weight[2][1] + weight[2][2];
          int avg = sum / weightSum;
          pixBlur[i][j][k] = (unsigned char) avg;
        }
      }
    }
  }

  writeImage(height, width, pixBlur);
  return EXIT_SUCCESS;
}
