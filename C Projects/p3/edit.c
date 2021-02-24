/**
* @file edit.c
* @author Christine Park (cpark7)
* 
* Contains functions to edit the contents of the text representation,
* specifically to remove one or more lines and one or more columns.
*/

#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include "edit.h"
#include "text.h"

/**
* Modifies the global text representation to remove lines
* in the given range.
* @param start starting line number
* @param end ending line number
*/
void removeLines(int start, int end)
{
  if (end >= lineCount) {
    end = lineCount - 1;
  }
  int removeRowCount = end - start + 1;
  for (int i = end + 1; i <= lineCount; i++) {
    for (int j = 0; j < MAX_COLS; j++) {
      twoDArray[i - removeRowCount][j] = twoDArray[i][j];
    }
  }
  lineCount -= removeRowCount;
}

/**
* Removes characters in the start-end range on just one line (the one at index lno)
* @param lno line index
* @param starting line number
* @param end ending line number
*/
void editLine( int lno, int start, int end )
{
  int lnLength = strlen(*(twoDArray + lno));
  if (start >= lnLength) {
    return;
  }
  if (end >= lnLength) {
    end = lnLength - 2;
  }
  int colsToRemove = end - start + 1;
  for (int i = end + 1; i < MAX_COLS; i++) {
    twoDArray[lno][i - colsToRemove] = twoDArray[lno][i];
  }
}

/**
* Uses editLine() function to remove the given range of columns from all the lines in the text
* @param start starting line number
* @param end ending line number
*/
void removeCols(int start, int end)
{
  for (int i = 0; i <= lineCount; i++) {
    editLine(i, start, end);
  }
}
