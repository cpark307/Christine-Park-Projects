/**
* @file edit.h
* @author Christine Park (cpark7)
*/

/**
* Modifies the global text representation to remove lines
* in the given range.
* @param start starting line number
* @param end ending line number
*/
void removeLines(int start, int end);

/**
* Removes characters in the start-end range on just one line (the one at index lno)
* @param lno line index
* @param starting line number
* @param end ending line number
*/
void editLine(int lno, int start, int end);

/**
* Uses editLine() function to remove the given range of columns from all the lines in the text
* @param start starting line number
* @param end ending line number
*/
void removeCols(int start, int end);
