/**
 * 
 */
package edu.ncsu.csc216.garage.model.vehicle;

/**
 * Thrown when a user attempts to add/edit a vehicle with invalid information (tier, type, name, license)
 *
 * @author Christine Park
 * @author Will Parker
 */
public class BadVehicleInformationException extends Exception {

	/** ID used for serialization */
	private static final long serialVersionUID = 1L;
	
	/** 
	 * Default constructor for BadVehicleInformationException.
	 */
	public BadVehicleInformationException() {
		super("Invalid tier.");
	}
	
	/** 
	 * Passes along the specific message to the parent constructor
	 * @param message error message returned when exception thrown
	 */
	public BadVehicleInformationException(String message) {
		super(message);
	}	
	
}
