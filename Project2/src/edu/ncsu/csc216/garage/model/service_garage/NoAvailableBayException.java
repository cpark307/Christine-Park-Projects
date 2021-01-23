package edu.ncsu.csc216.garage.model.service_garage;


/**
 * Custom NoAvailableBayException is thrown when a vehicle attempts to choose a service bay but all appropriate service bays are occupied
 * 
 * @author Will Parker
 * @author Christine Park
 *
 */
public class NoAvailableBayException extends Exception {

	/** ID used for serialization */
	private static final long serialVersionUID = 1L;

	/**
	 * Calls the parameterized NoAvailableBayException constructor with the specified String message when a
	 * vehicle attempts to choose a service bay but all appropriate service bays are occupied.
	 */
	public NoAvailableBayException() {
		super("No Available Bay");
	}

	/**
	 * Generates a parameterized NoAvailableBayException with the specified String message when a
	 * vehicle attempts to choose a service bay but all appropriate service bays are occupied.
	 * 
	 * @param message Message displayed when a BayOccupiedException is thrown
	 */
	public NoAvailableBayException(String message) {
		super(message);
	}


}