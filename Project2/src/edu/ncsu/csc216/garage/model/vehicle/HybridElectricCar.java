package edu.ncsu.csc216.garage.model.vehicle;

import edu.ncsu.csc216.garage.model.service_garage.Garage;
import edu.ncsu.csc216.garage.model.service_garage.NoAvailableBayException;

/**
 * Concrete class representing a hybrid/electric vehicle that can be serviced in HybridElectricBays.
 * The HybridElectricCar class details logic specific to hybrid/electric cars when constructing new vehicles and picking service bays.
 * 
 * @author Christine Park
 * @author Will Parker
 */
public class HybridElectricCar extends Vehicle {
	
	/**
	 * Constructs a new HybridElectric car with the values passed by the client for license plate, owner's name, and vehicle's tier index.
	 * 
     * @param license license string
     * @param name owner's name
     * @param tierIndex represents the vehicle's preferred service tier status
     * @throws BadVehicleInformationException if the license of owner are not valid
	 */
	public HybridElectricCar(String license, String name, int tierIndex) throws BadVehicleInformationException  {
		super(license, name, tierIndex);
	}

	/** 
     * The vehicle picks a service bay
     * 
     * @param garage is the garage where the car will occupy
     * @throws NoAvailableBayException when there are no available service bays for the car to enter
     */
	@Override
	public void pickServiceBay(Garage garage) throws NoAvailableBayException {
    	boolean placed = false;
    	for (int i = garage.getSize() - 1; i >= 0; i--) {
    		if (!garage.getBayAt(i).isOccupied() && !placed) {
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
     * Returns a String representation of the HybridElectricCar (prefixed by "E")
     * 
     * @return String representation of the HybridElectricCar
     */
    public String toString(){
		return "E " + super.toString();
    }
}
