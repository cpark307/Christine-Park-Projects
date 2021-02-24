/**
* @file reading.c
* @author Christine Park (cpark7)
* The reading component contains main, code to read in user
* commands and the implementation for the reading list.
*/

#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include "catalog.h"
#include "input.h"

/**
 * Adds book to reading list
 *
 * @param cat cat
 * @param list reading list
 * @param id book id to add
 */
static void addToReadingList(Catalog *cat, Catalog *list, int id)
{
  if (list->count >= list->capacity) {
    list->capacity *= TWO;
    list->books = (Book **) realloc(list->books, list->capacity * sizeof(Book*));
    int startHere = list->count;
    for (int i = startHere; i < list->capacity; i++) {
      list->books[i] = (Book *) malloc (sizeof(Book));
    }
  }

  for (int i = 0; i < list->count; i++) {
    if (list->books[i]->id == id) {
      fprintf(stdout, "Book %d is already on the reading list\n", id);
      return;
    }
  }

  bool notInCatalog = true;
  int index = 0;
  while (notInCatalog) {
    for (int i = 0; i < cat->count; i++) {
      if (cat->books[i]->id == id) {
        notInCatalog = false;
        index = i;
      }
    }
    if (notInCatalog) {
      fprintf(stdout, "Book ID is not in the cat\n");
      return;
    }
  }
  
  list->books[list->count]->id = cat->books[index]->id;
  strncpy(list->books[list->count]->title,
    cat->books[index]->title, MAX_TITLE + 1);
  strncpy(list->books[list->count]->author,
    cat->books[index]->author, MAX_AUTHOR + 1);
  list->books[list->count]->level = cat->books[index]->level;
  list->books[list->count]->words = cat->books[index]->words;
  list->books[list->count]->subjects = cat->books[index]->subjects;
  list->count += 1;
}

/**
 * Removes book from reading list
 *
 * @param list Pointer to the reading list
 * @param id Book ID to remove to the reading list
 */
static void removeFromReadingList(Catalog *list, int id)
{
  bool isNotInList = true;
  int index = 0;
  while (isNotInList) {
    for (int i = 0; i < list->count; i++) {
      if (list->books[i]->id == id) {
        isNotInList = false;
        index = i;
        break;
      }
    }
    if (isNotInList) {
      fprintf(stdout, "Book %d is not on the reading list\n", id);
      return;
    }
  }
  
  free(list->books[index]);
  list->books[index] = (Book *) malloc (sizeof(Book));
  for (int i = index; i < list->capacity - 1; i++) {
    memmove(list->books[i], list->books[i + 1], sizeof(Book));
  }
  list->count--;
}

/**
 * Prints the books in the user's reading list
 * along with the average reading level of all the books
 * and the sum of the word counts of all the books.
 *
 * @param list pointer to the reading list
 */
static void printReadingList(Catalog *list)
{

  if (list->count == 0) {
    fprintf(stdout, "List is empty\n");
    return;
  }
  
  char idHdr[THREE] = "ID";
  char titleHdr[SIX] = "Title";
  char authorHdr[SEVEN] = "Author";
  char levelHdr[SIX] = "Level";
  char wordsHdr[SIX] = "Words";
  
  fprintf(stdout, "%5s %38s %20s %5s %7s\n",
    idHdr, titleHdr, authorHdr, levelHdr, wordsHdr);

  for (int i = 0; i < list->count; i++) {
    Book book = *(list->books[i]);
    fprintf(stdout, "%5d %38s %20s %5.1lf %7d\n",
      book.id, book.title, book.author, book.level, book.words);
  }

  int bookCnt = list->count;
  double sumLevels = 0.0;
  int sumWords = 0;
  for (int i = 0; i < bookCnt; i++) {
    sumLevels += list->books[i]->level;
    sumWords += list->books[i]->words;
  }
  double avgLevel = sumLevels / bookCnt;
  fprintf(stdout, "%71.1lf %7d\n", avgLevel, sumWords);
}

/**
 * Starting point for the program.
 *
 * @param argc number of command line arguments entered by the user
 * @param argv array of command line arguments entered by the user
 * @return exit status of program
 */
int main(int argc, char *argv[])
{
  if (argc <= 1) {
    fprintf( stderr, "usage: reading <book-list>*\n");
    exit(EXIT_FAILURE);
  }
  
  Catalog *cat = makeCatalog();
  
  for (int i = 1; i < argc; i++) {
    int fileCount = strlen(argv[i]);
    char filename[fileCount + 1];
    strcpy(filename, argv[i]);
    
    readCatalog(cat, filename);
  }
  
  Catalog *readingList = makeCatalog();
  
  fprintf(stdout, "cmd> ");
  char *ans = readLine(stdin);
  char *str = (char *) malloc (TEN * sizeof(char));
  double lowLevel;
  double highLevel;
  int addOrRemove;
  char *subjectOpt = (char *) malloc (BUFFER * sizeof(char));

  while (!feof(stdin) && strcmp(ans, "quit\n") != 0) {
    //LEVEL cmd
    if ((sscanf(ans, "%s %lf %lf\n", str, &lowLevel, &highLevel)) == THREE) {
      if (strcmp(str, "level") == 0 && lowLevel <= highLevel) {
        fprintf(stdout, "%s", ans);
        listLevel(cat, lowLevel, highLevel);
        fprintf(stdout, "\n");
        free(ans);
      } else {
        fprintf(stdout, "%s", ans);
        fprintf(stdout, "Invalid command\n");
        fprintf(stdout, "\n");
        free(ans);
      }
    }
    //REMOVE/ADD cmd
    else if ((sscanf(ans, "%s %d\n", str, &addOrRemove)) == TWO) {
      if (strcmp(str, "add") == 0) {
        fprintf(stdout, "%s", ans);
        addToReadingList(cat, readingList, addOrRemove);
        fprintf(stdout, "\n");
        free(ans);
      } else if (strcmp(str, "remove") == 0) {
        fprintf(stdout, "%s", ans);
        removeFromReadingList(readingList, addOrRemove);
        fprintf(stdout, "\n");
        free(ans);
      }
      else {
        fprintf(stdout, "%s", ans);
        fprintf(stdout, "Invalid command\n");
        fprintf(stdout, "\n");
        free(ans);
      }
    }
    //SUBJECT cmd
    else if ((sscanf(ans, "%s %s\n", str, subjectOpt)) == TWO) {
      if (strcmp(str, "subject") == 0) {
        fprintf(stdout, "%s", ans);
        listSubject(cat, subjectOpt);
        fprintf(stdout, "\n");
        free(ans);
      }
      else {
        fprintf(stdout, "%s", ans);
        fprintf(stdout, "Invalid command\n");
        fprintf(stdout, "\n");
        free(ans);
      }
    }
    //CAT cmd
    else if (strcmp(ans, "catalog\n") == 0) {
      fprintf(stdout, "%s", ans);
      listAll(cat);
      fprintf(stdout, "\n");
        free(ans);
    }
    //LIST cmd
    else if (strcmp(ans, "list\n") == 0) {
      fprintf(stdout, "%s", ans);
      printReadingList(readingList);
      fprintf(stdout, "\n");
        free(ans);
    }
    //INVALID cmd
    else {
      fprintf(stdout, "%s", ans);
      fprintf(stdout, "Invalid command\n");
      fprintf(stdout, "\n");
      free(ans);
    }
    fprintf(stdout, "cmd> ");
    ans = readLine(stdin);
  }
  
  if (ans != NULL) {
    if (strcmp(ans, "quit\n") == 0) {
    fprintf(stdout, "%s", ans);
    }
  }

  free(str);
  free(subjectOpt);
  free(ans);
  freeCatalog(cat);
  for (int i = readingList->capacity - 1; i >= 0; i--){
    free(readingList->books[i]);
  }
  free(readingList->books);
  free(readingList);
  return EXIT_SUCCESS;
}
