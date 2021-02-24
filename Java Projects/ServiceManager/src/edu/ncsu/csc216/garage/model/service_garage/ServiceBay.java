package edu.ncsu.csc216.garage.model.service_garage;

import edu.ncsu.csc216.garage.model.vehicle.Vehicle;

/**
 * Concrete class representing a regular service bay that can handle both regular and hybrid/electric vehicles.
 * Each service bay will have state detailing its ID, whether or not it is empty, and if it is not empty the vehicle occupying it.
 * An instance of ServiceBay has the ability to release the vehicle currently in it or occupy itself with vehicle.
 * 
 * @author Will Parker
 * @author Christine Park
 */
public class ServiceBay {
	
	/** Variable is true if the bay is occupied with a vehicle */
	private boolean occupied;
	
	/** The bay's ID */
	private String bayID;
	
	/** The number that will be assigned to the next bay that is opened */
	private static int nextNumber;
	
	/** Instance of Vehicle representing the vehicle in the service bay */
	private Vehicle myVehicle;
	
	/**
	 * Begins numbering service bays starting with the number 101
	 */
	public static void startBayNumberingAt101() {
		nextNumber = 1;
	}
	
	/**
	 * Constructs a new service bay with a prefix for the bayID
	 * 
	 * @param prefix Prefix to use in the newly constructed service bay's bayID
	 */
	public ServiceBay(String prefix) {
		if (prefix == null || prefix.trim().length() <= 0) {
			prefix = "1";
		}
		prefix = "" + prefix.trim().charAt(0);
		if (nextNumber < 10) {
        	this.bayID = prefix + "0" + nextNumber;
        } else {
        	this.bayID = prefix + nextNumber;
        }
        nextNumber++;
        occupied = false;
	}
	
	/**
	 * Creates a new service bay and passes the prefix "1" to the parameterized constructor
	 */
	public ServiceBay() {
		this("1");
	}
	
	/**
	 * Returns the bayID of the service bay
	 * 
	 * @return bayID of the service bay
	 */
	public String getBayID() {
		return bayID;
	}
	
	/**
	 * Returns true if the service bay is occupied by a vehicle
	 * 
	 * @return True if bay is occupied by a vehicle
	 */
	public boolean isOccupied() {
		return occupied;
	}
	
	/**
	 * Releases the vehicle currently housed by the service bay once service is complete. Returns the vehicle that was released.
	 * 
	 * @return The vehicle that was released from the service bay
	 */
	public Vehicle release() {
		Vehicle vehicle = myVehicle;
		if(!occupied) {
			return null;
		}
		occupied = false;
		myVehicle = null;
		return vehicle;
	}
	
	/**
	 * Occupies the service bay with the vehicle passed by the client.
	 * Throws a BayOccupiedException if an attempt is made to occupy a service bay that is already occupied.
	 * 
	 * @param vehicle Vehicle that will occupy the service bay
	 * @throws BayOccupiedException if an attempt is made to occupy an already occupied service bay
	 * @throws BayCarMismatchException if an attempt to occupy a bay with an incompatible car type is made
	 */
	public void occupy(Vehicle vehicle) throws BayOccupiedException, BayCarMismatchException {
		if (occupied) {
			throw new BayOccupiedException();
        }
		if (vehicle.toString().charAt(0) == 'R' && getBayID().charAt(0) == 'E') {
			throw new BayCarMismatchException();
		}
        myVehicle = vehicle;
        occupied = true;
	}
	
	/**
	 * Returns the string representation of the service bay.
	 * 
	 * @return String representation of the service bay
	 */
	public String toString() {
		if(!occupied) {
            return getBayID()  + ": EMPTY";
        }
        return getBayID() + ": " + String.format("%-9s", myVehicle.getLicense()) + 
        		myVehicle.getName();
    }
      
}
