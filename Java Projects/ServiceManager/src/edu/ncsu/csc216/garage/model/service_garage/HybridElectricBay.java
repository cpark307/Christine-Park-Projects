package edu.ncsu.csc216.garage.model.service_garage;

import edu.ncsu.csc216.garage.model.vehicle.Vehicle;


/**
 * Concrete class representing a hybrid/electric service bay. HybridElectricBay is a child class of ServiceBay and will detail the 
 * logic specific to constructing and occupying hybrid/electric bays.
 * 
 * @author Will Parker
 * @author Christine Park
 */
public class HybridElectricBay extends ServiceBay {

	/**
	 * Constructs a new, empty hybrid/electric bay via a call to the parent class constructor with a prefix "E"
	 */
	public HybridElectricBay() {
		super("E");
	}

	/**
	 * Occupies a service bay with the vehicle passed by the client. Throws a BayCarMismatchException if an attempt to occupy 
	 * a bay with an incompatible vehicle type is made.
	 * 
	 * @param vehicle Vehicle to occupy service bay
	 * @throws BayOccupiedException if an attempt to occupy an already occupied bay is made
	 * @throws BayCarMismatchException if an attempt to occupy a bay with an incompatible vehicle type is made
	 */
	public void occupy(Vehicle vehicle) throws BayOccupiedException, BayCarMismatchException {
		if(super.isOccupied()){
            throw new BayOccupiedException();
        }
		if (vehicle.toString().charAt(0) == 'R' && getBayID().charAt(0) == 'E') {
			throw new BayCarMismatchException();
		}
        super.occupy(vehicle);
	}
}