package edu.ncsu.csc216.garage.model.util;

/**
 * Provides a simple iterator interface that enables the traversal of a linked list
 * 
 * @author Will Parker
 * @author Christine Park
 *
 * @param <E> Generic object parameter for the iterator
 */
public interface SimpleIterator<E> {

	/**
	 * Returns true if the next item in the linked list is not null
	 * 
	 * @return True if the linked list has another item after the current one
	 */
	boolean hasNext();
	
	/**
	 * Advances the iterator to the next item in the linked list
	 * 
	 * @return Generic element iterator is pointing to
	 */
	E next();
}
