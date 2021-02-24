package edu.ncsu.csc216.garage.model.service_garage;

import edu.ncsu.csc216.garage.model.vehicle.Vehicle;

/**
 * Concrete class representing a garage that consists of a collection of service bays.
 * A newly instantiated Garage will initialize the number of open bays to 8 by default. The Garage class will handle the management of the arrays of service bays it represents
 * by adding bays, releasing cars from bays, and managing the array's size.
 * 
 * @author Will Parker
 * @author Christine Park
 */
public class Garage {
	
	/** The maximum number of bays that can be open at one time */
	private static final int MAX_BAYS = 30;
	
	/** The number of bays initially opened when an instance of garage is created */
	private static final int DEFAULT_SIZE = 8;
	
	/** Counter that keeps track of the number of bays currently open in the garage */
	private int size;
	
	/** Array of service bays in the garage */
	private ServiceBay[] bay;
	
	
	/**
	 * Constructs a new garage with 8 empty service bays and a max service bay capacity of 30
	 */
	public Garage() {
		size = 0;
		bay = new ServiceBay[MAX_BAYS];
		ServiceBay.startBayNumberingAt101();
		initBays(DEFAULT_SIZE);
	}
	
	/**
	 * Helper method for the constructor that constructs the garage with the initial, default number of open service bays
	 * 
	 * @param size Number of bays to open in the garage
	 */
	private void initBays(int size) {
        for(int i = 0; i < size; i++) {
            addRepairBay();
        }
	}
	
	/**
	 * Opens a new repair bay in the garage in a fashion to maintain that 1/3 of the open bays are hybrid bays.
	 */
	public void addRepairBay() {
		 if(getSize() < MAX_BAYS) {
			 if(getSize() % 3 == 0) {
				 bay[getSize()] = new HybridElectricBay();
	             size++;
	         } else {
	             for (int j = getSize() - 1; j >= 0; j--) {
	            	 bay[j + 1] = bay[j];
	             }
	             bay[0] = new ServiceBay();
	             size++;
	            }
		  }
     }
	
	/**
	 * Returns the number of empty bays currently open in the garage
	 * 
	 * @return Number of empty service bays currently open in the garage
	 */
	public int numberOfEmptyBays() {
		int empty = 0;
		for (int i = 0; i < getSize(); i++) {
			if (!bay[i].isOccupied()) {
				empty++;
			}
		}
		return empty;
	}
	
	/**
	 * Returns the service bay at a specified index in the array of service bays (a.k.a. the garage)
	 * 
	 * @param index Index of the garage array that represents the service bay of interest
	 * @return Service bay of the garage at the specified index
	 */
	public ServiceBay getBayAt(int index) {
		return bay[index];

	}
	
	/**
	 * Returns the total number occupied and unoccupied service bays currently open in the garage
	 * 
	 * @return Total number of service bays currently open in the garage
	 */
	public int getSize() {
		return size;
	}
	
	/**
	 * Releases the vehicle from the service bay at the index of the garage array passed by the client
	 * 
	 * @param index Index of garage array representing the service bay from which the vehicle will be released
	 * @return Vehicle released from the service bay
	 */
	public Vehicle release(int index) {
        if(bay[index] == null || !bay[index].isOccupied()) {
            return null;
        }
        return bay[index].release();
	}
	
}
