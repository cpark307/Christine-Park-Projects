package edu.ncsu.csc216.garage.model.service_garage;

/**
 * Custom BayCarMismatchException is thrown when an attempt to occupy an empty service bay with an incompatible vehicle type is made.
 * Regular bays can be occupied by both regular and hybrid/electric vehicles, but hybrid/electric bays can only accept hybrid/electric vehicles.
 * 
 * @author Will Parker
 * @author Christine Park
 *
 */
public class BayCarMismatchException extends Exception {

	/** ID used for serialization */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Calls the parameterized BayCarMismatchException constructor with the specified String message when an
	 * attempt to occupy a bay with an incompatible Vehicle type is made.
	 */
	public BayCarMismatchException() {
		super("BayCarMismatchException");
	}

	/**
	 * Generates a parameterized BayCarMismatchException with the specified String message when an
	 * attempt to occupy a bay with an incompatible Vehicle type is made.
	 * 
	 * @param message Message displayed when a BayCarMismatchException is thrown
	 */
	public BayCarMismatchException(String message) {
		super(message);
	}
}
