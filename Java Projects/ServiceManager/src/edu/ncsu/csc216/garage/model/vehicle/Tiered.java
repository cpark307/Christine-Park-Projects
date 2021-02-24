package edu.ncsu.csc216.garage.model.vehicle;

/**
 * Interface that describes behaviors of objects with tier status. Interface abstracts methods for comparing tier statuses and returning the tier of a Tiered object.
 *  
 * @author Christine Park
 * @author Will Parker
 */
public interface Tiered {
	
	/**
	 * Compare the tier status of this object with another. Returns 0 if the two match, 
	 * a negative number if the tier status of this object is less than the other’s, a 
	 * positive number if the tier status of this object is greater. Required for ordering.
	 * 
	 * @param t tier
	 * @return 0 if match, negative number if tier of this object is less than the other's, a positive number if the tier status of this object is greater
	 */
	 int compareToTier(Tiered t);
	 
	 /**
	  * Returns the tier of the object
	  * 
	  * @return tier of the object
	  */
	 int getTier();
}
