/**
* @file catalog.h
* @author Christine Park (cpark7)
*
* The header file for catalog.c.
* The catalog component contains code for implementing books and the catalog
*/

#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <stdbool.h>
#include "input.h"

/** Initial catalog capacity */
#define INIT_CAP 5
/** Max allowbale length for title */
#define MAX_TITLE 38
/** Max allowable length for author */
#define MAX_AUTHOR 20


/**
 * Book struct that contains the fields for id, title,
 * author, reading level, word count, and subjects
 */
struct Book {
  int id;
  char title[MAX_TITLE + 1];
  char author[MAX_AUTHOR + 1];
  double level;
  int words;
  char *subjects;
};

typedef struct Book Book;

/**
 * Catalog struct that contains the fields for an array
 * of pointers to Book structs, an integer representing the
 * number of books in the catalog, and the capacity of the catalog
 */
struct Catalog {
  Book **books;
  int count;
  int capacity;
};

typedef struct Catalog Catalog;

/**
 * Dynamically allocates storage for the catalog, initializes
 * its fields (to store a resizable array) and returns a pointer to it.
 * @return pointer to catalog
 */
Catalog *makeCatalog();

/**
 * This function frees the memory used to store the catalog, including
 * freeing space for all the books, freeing the resizable array of book
 * pointers and freeing space for the catalog struct itself.
 * @param *cat pointer to catalog
 */
void freeCatalog(Catalog *cat);

/**
 * This function reads all the books from a book list file with the given name.
 * It makes an instance of the Book struct for each one and stores a pointer to
 * that book in the resizable array
 *
 * @param cat Pointer to the catalog to read Books into
 * @param filename Filename to read Books from
 */
void readCatalog( Catalog *cat, char const *filename);

/**
 * This function lists all the books in the catalog, sorted by ID number.
 * @param cat pointer to catalog
 */
void listAll(Catalog * cat);

/**
 * This function lists all the books with a reading level between the
 * given min and max values (inclusive). In the output, books should
 * be sorted by reading level, and by ID if they have the same reading level.
 * @param cat pointer to catalog
 * @param min min value
 * @param max max value
 */
void listLevel(Catalog * cat, double min, double max);

/**
 * This function lists all the books in the catalog based on book ID and subsequently
 * calls listCatalog() to print all the books in the catalog that have subjects
 * fileds that contain the substring passed in the subject
 * @param cat Catalog of books to print
 * @param subject Substring to search for in subjects field
 */
void listSubject(Catalog *cat, char const *subject);

/**
 * This is a static function in the catalog component. It is used by the
 * listAll(), listLevel() and litSubject() functions to actually report
 * the list of books in the right format.
 * @param *cat pointer to catalog
 * @param test pointer to function
 * @param *data arbitrary block of data
 */
void listCatalog(Catalog *cat, bool (*test) (Book const *book, void const *data), void const *data);
