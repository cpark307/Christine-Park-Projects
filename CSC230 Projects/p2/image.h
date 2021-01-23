/** Maximum intensity value expected for images. */
#define STD_RANGE 255

/** Number of color components per pixel */
#define DEPTH 3

/** Exit code for a bad magic number at the top. */
#define BAD_HEADER 100

/** Exit code for bad or missing pixel data */
#define BAD_PIXEL 101

/** "Magic number" value for ppm files */
#define MAGIC_NUMBER "P3"

/** The number of rows in the ppm file */
extern int height;

/** The number of columns in the ppm file */
extern int width;

/** "Magic number" that is read from the header of a ppm file */
extern char magicNumber[];

/**
* Used to read the magic number at the start of an input image and check to make
* sure the value is correct and exits (with the correct exit status) if it is not.
*/
void checkType();

/**
* Reads the width and height in the image header
* @return width/height of image file listed in header
*/
int readDimension();

/**
* Reads the maximum intensity value at the end of the image header.
* Checks to make sure it is set to 255 and exits appropriately if not.
*/
void checkRange();

/**
* Reads all the pixel data for an image. Values are stored in the given array
*/
void readPixels( int height, int width, unsigned char pix[ height ][ width ][ DEPTH ] );

/**
* Writes the output image to the standard output.
*/
void writeImage( int height, int width, unsigned char pix[ height ][ width ][ DEPTH ] );
