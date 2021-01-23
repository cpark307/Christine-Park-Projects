/**
* @file catalog.c
* @author Christine Park (cpark7)
*
* The catalog component contains code for implementing books and the catalog.
*/
#include "catalog.h"

/**
 * Contains min and max range for level command
 */
typedef struct LevelData {
  double low;
  double high;
} LevelData;

/**
* This function dynamically allocates storage for the catalog, initializes
* its fields (to store a resizable array) and returns a pointer to it.
* @return pointer to new catalog
*/
Catalog *makeCatalog()
{
  Catalog * cat = (Catalog *) malloc(sizeof(Catalog));
  cat->books = (Book **) malloc(INIT_CAP * sizeof(Book*));
  for (int i = 0; i < INIT_CAP; i++) {
    cat->books[i] = (Book *) malloc (sizeof(Book));
  }
  cat->count = 0;
  cat->capacity = INIT_CAP;
  return cat;
}

/**
* This function frees the memory used to store the catalog, including
* freeing space for all the books, freeing the resizable array of book
* pointers and freeing space for the catalog struct itself.
* @param pointer to catalog
*/
void freeCatalog(Catalog *cat)
{
  for (int i = cat->capacity- 1; i >= 0; i--) {
    if (i <= cat->count - 1) {
      free(cat->books[i]->subjects);
    }
    free(cat->books[i]);
  }
  free(cat->books);
  free(cat);
}

/**
* This function reads all the books from a book list file with the given name.
* It makes an instance of the Book struct for each one and stores a pointer to
* that book in the resizable array
* @param cat pointer to catalog
* @param filename pointer to given file
*/
void readCatalog( Catalog *cat, char const *filename)
{
  FILE *fp = fopen(filename, "r");
  if (!fp) {
    fprintf(stderr, "Can't open file: %s\n", filename);
    freeCatalog(cat);
    exit(EXIT_FAILURE);
  }
  
  char *line;
  while ((line = readLine(fp)) != NULL) {
    int id;
    char *title = (char *) malloc(BUFFER * sizeof(char));
    char *author = (char *) malloc (BUFFER * sizeof(char));
    double level;
    int words;
    char *subjects = (char *) malloc (BUFFER * sizeof(char));
    free(title);
    free(author);
    free(subjects);
    
    int match = sscanf(line, "%d\t%m[^\t]\t%m[^\t]\t%lf\t%d\t%m[^\n]\n",
      &id, &title, &author, &level, &words, &subjects);
    free(line);
    
    for (int i = 0; i < cat->count; i++) {
      if (cat->books[i]->id == id) {
        fprintf(stderr, "Duplicate book id: %d\n", id);
        free(title);
        free(author);
        free(subjects);
        freeCatalog(cat);
        fclose(fp);
        exit(EXIT_FAILURE);
      }
    }
    if (match < FIVE) {
      fprintf(stderr, "Invalid book list: %s\n", filename);
      free(title);
      free(author);
      freeCatalog(cat);
      fclose(fp);
      exit(EXIT_FAILURE);
    }
    
    if (cat->count >= cat->capacity) {
      cat->capacity *= TWO;
      cat->books = (Book **) realloc(cat->books, cat->capacity * sizeof(Book* ));
      for (int i = (cat->count); i < cat->capacity; i++) {
        cat->books[i] = (Book *) malloc (sizeof(Book));
      }
    }
    
    cat->books[cat->count]->id = id;
    cat->books[cat->count]->level = level;
    cat->books[cat->count]->words = words;
    cat->books[cat->count]->subjects = subjects;
    
    if (strlen(author) > MAX_AUTHOR) {
      strncpy(cat->books[cat->count]->author, author, MAX_AUTHOR - TWO);
      cat->books[cat->count]->author[MAX_AUTHOR - TWO] = '.';
      cat->books[cat->count]->author[MAX_AUTHOR - 1] = '.';
      cat->books[cat->count]->author[MAX_AUTHOR] = '\0';
    } else {
      strncpy(cat->books[cat->count]->author, author, MAX_AUTHOR);
      cat->books[cat->count]->author[MAX_AUTHOR] = '\0';
    }
    free(author);
    if (strlen(title) > MAX_TITLE) {
      strncpy(cat->books[cat->count]->title, title, MAX_TITLE - TWO);
      cat->books[cat->count]->title[MAX_TITLE - TWO] = '.';
      cat->books[cat->count]->title[MAX_TITLE - 1] = '.';
      cat->books[cat->count]->title[MAX_TITLE] = '\0';
    } else {
      strncpy(cat->books[cat->count]->title, title, MAX_TITLE);
      cat->books[cat->count]->title[MAX_TITLE] = '\0';
    }
    free(title);
    cat->count++;
  }
  fclose(fp);
}

/**
* Book id comparator
* @param aptr pointer a
* @param bptr pointer b
* @return 1 if a > b, 0 if a=b, -1 if a < b
*/
static int idComp( const void *aptr, const void *bptr )
{
  const Book *a = *(const Book **)aptr;
  const Book *b = *(const Book **)bptr;
  if ( a->id < b->id ) {
    return -1;
  }
  if ( a->id > b->id ) {
    return 1;
  }
  return 0;
}

/**
* Book level comparator
* @param aptr pointer a
* @param bptr pointer b
* @return 1 if a > b, 0 if a=b, -1 if a < b
*/
static int levelComp( const void *aptr, const void *bptr )
{
  const Book *a = *(const Book **)aptr;
  const Book *b = *(const Book **)bptr;
  if ( a->level < b->level ) {
    return -1;
  }
  if ( a->level > b->level ) {
    return 1;
  }
  return idComp(aptr, bptr);
}

/**
 * Test function used when printing books all books
 *
 * @param book
 * @param data data used to determine whether to return true/false (not used)
 * @return true
 */
static bool listAllTest(Book const *book, void const *data)
{
  return true;
}

/**
 *  Test function used when printing books all books
 *
 * @param book
 * @param data data used to determine whether to return true/false (not used)
 * @return True if book's reading level is between the low and high fields in data
 */
static bool listLevelTest(Book const *book, void const *data)
{
  LevelData *levelData = (LevelData *) data;
  if (book->level >= levelData->low && book->level <= levelData->high) {
    return true;
  }
  return false;
}

/**
 * Test function used when printing books of certain subjects
 * @param book book
 * @param data struct containing min and max range (inclusive)
 * @return True if book's subjects field contains the substring in data
 */
static bool listSubjectTest(Book const *book, void const *data)
{
  char *substr = (char *) data;
  if (strstr(book->subjects, substr) != NULL) {
    return true;
  }
  return false;
}

/**
 * This function lists all the books in the catalog, sorted by ID number.
 * @param cat pointer to catalog
 */
void listAll(Catalog *cat)
{
  qsort(cat->books, cat->count, sizeof(Book *), idComp);
  listCatalog(cat, listAllTest, NULL);
}

/**
 * This function lists all the books with a reading level between the
 * given min and max values (inclusive). In the output, books should
 * be sorted by reading level, and by ID if they have the same reading level.
 * @param cat pointer to catalog
 * @param min min value
 * @param max max value
 */
void listLevel(Catalog *cat, double min, double max)
{
  qsort(cat->books, cat->count, sizeof(Book *), levelComp);

  LevelData *levelData = (LevelData *) malloc(sizeof(LevelData));
  levelData->low = min;
  levelData->high = max;
  
  listCatalog(cat, listLevelTest, levelData);
  free(levelData);
}

/**
 * This function lists all the books in the catalog based on book ID and subsequently
 * calls listCatalog() to print all the books in the catalog that have subjects
 * fileds that contain the substring passed in the subject.
 * @param cat Catalog of books to print
 * @param subject Substring to search for in subjects field
 */
void listSubject(Catalog *cat, char const *subject)
{
  qsort(cat->books, cat->count, sizeof(Book *), idComp);
  listCatalog(cat, listSubjectTest, subject);
}

/**
 * Prints the books in the catalog that meet the criteria of the test determined by the user.
 * @param cat Catalog of books to print
 * @param test Function to use when determining whether to print book
 *(takes parameters for book and data)
 * @param data Data used to decide whether to print book
 */
void listCatalog(Catalog *cat, bool (*test) (Book const *book, void const *data), void const *data)
{
  char idHdr[THREE] = "ID";
  char titleHdr[SIX] = "Title";
  char authorHdr[SEVEN] = "Author";
  char levelHdr[SIX] = "Level";
  char wordsHdr[SIX] = "Words";
  bool isValid = false;
  
  for (int i = 0; i < cat->count; i++) {
    if (test(cat->books[i], data)) {
      isValid = true;
      break;
    }
  }
  if (isValid) {
    fprintf(stdout, "%5s %38s %20s %5s %7s\n", idHdr, titleHdr, authorHdr, levelHdr, wordsHdr);
    for (int i = 0; i < cat->count; i++) {
      if (test(cat->books[i], data)) {
        Book book = *(cat->books[i]);
        fprintf(stdout, "%5d %38s %20s %5.1lf %7d\n",
        book.id, book.title, book.author, book.level, book.words);
      }
    }
  } else {
    fprintf(stdout, "No matching books\n");
  }
}
