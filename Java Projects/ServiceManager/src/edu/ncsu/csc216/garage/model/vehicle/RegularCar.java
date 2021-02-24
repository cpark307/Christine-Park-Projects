package edu.ncsu.csc216.garage.model.vehicle;

import edu.ncsu.csc216.garage.model.service_garage.Garage;
import edu.ncsu.csc216.garage.model.service_garage.NoAvailableBayException;

/**
 * Concrete class representing a standard vehicle that cannot be serviced in HybridElectricBays.
 * The RegularCar class will detail the logic specific to regular cars when constructing new vehicles and choosing service bays.
 * 
 * @author Christine Park
 * @author Will Parker
 */
public class RegularCar extends Vehicle {

	/**
	 * Constructs a new Regular car with the values passed by the client for license plate, owner's name, and vehicle's tier index.
	 * 
	 * @param license license string
     * @param name owner's name
     * @param tierIndex represents the vehicle's preferred service tier status
     * @throws BadVehicleInformationException if the license of owner are not valid
	 */
	public RegularCar(String license, String name, int tierIndex) throws BadVehicleInformationException {
		super(license, name, tierIndex);
	}

    /** 
     * The vehicle picks a service bay
     * 
     * @param garage Garage containing service bay car will occupy
     * @throws NoAvailableBayException when there are no available service bays for the car to enter
     */
    public void pickServiceBay(Garage garage) throws NoAvailableBayException {
    	boolean placed = false;
    	for (int i = 0; i < garage.getSize(); i++) {
    		if (!garage.getBayAt(i).isOccupied() && !placed && garage.getBayAt(i).getBayID().charAt(0) != 'E') {
    			try {
    				garage.getBayAt(i).occupy(this);
    				placed = true;
    				} catch (Exception e) {
    					throw new NoAvailableBayException();
    				}
    			}
    		}
    	if (!placed) {
    		throw new NoAvailableBayException();
    	}
    }
    
    /**
     * Returns a String representation of the RegularCar
     * 
     * @return String representation of the RegularCar
     */
    public String toString(){
		return "R " + super.toString();
    }
}
