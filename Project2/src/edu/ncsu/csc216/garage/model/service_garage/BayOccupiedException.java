package edu.ncsu.csc216.garage.model.service_garage;

/**
 * Custom BayOccupiedException is thrown when an attempt to occupy an already occupied service bay is made
 * 
 * @author Will Parker
 * @author Christine Park
 *
 */
public class BayOccupiedException extends Exception {
	
	/** ID used for serialization */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Calls the parameterized BayOccupiedException constructor with the specified String message when an
	 * attempt to occupy an already occupied bay is made
	 */
	public BayOccupiedException() {
		super("BayOccupiedException");
	}

	/**
	 * Generates a parameterized BayOccupiedException with the specified String message when an
	 * attempt to occupy an already occupied bay is made
	 * 
	 * @param message Message displayed when a BayOccupiedException is thrown
	 */
	public BayOccupiedException(String message) {
		super(message);
	}

}
