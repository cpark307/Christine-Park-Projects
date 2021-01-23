package edu.ncsu.csc216.garage.model.dealer;


import java.util.Scanner;


import edu.ncsu.csc216.garage.model.service_garage.Garage;
import edu.ncsu.csc216.garage.model.service_garage.NoAvailableBayException;
import edu.ncsu.csc216.garage.model.util.SimpleIterator;
import edu.ncsu.csc216.garage.model.vehicle.BadVehicleInformationException;
import edu.ncsu.csc216.garage.model.vehicle.HybridElectricCar;
import edu.ncsu.csc216.garage.model.vehicle.RegularCar;
import edu.ncsu.csc216.garage.model.vehicle.Tiered;
import edu.ncsu.csc216.garage.model.vehicle.Vehicle;
import edu.ncsu.csc216.garage.model.vehicle.VehicleList;

/**
 * Class that handles the high-level implementation of the service manager's responsibilities that include opening new service bays, adding and 
 * removing vehicles to the vehicle waiting list, occupying service bays, and releasing vehicles from service bays.
 * 
 * ServiceManager implements the Manageable interface and will contain instances of Garage and VehicleList.
 * 
 * @author Will Parker
 * @author Christine Park
 */	
public class ServiceManager implements Manageable {

	/** Instance of the Garage class representing the service garage */
	private Garage myGarage;

	/** Instance of VehicleList that represents the queue of cars awaiting service */
	private VehicleList waitingCars;

	/**
	 * Constructs a new service manager with an empty vehicle waiting list
	 */
	public ServiceManager() {
		myGarage = new Garage();
		waitingCars = new VehicleList();
	}

	/**
	 * Constructs a new service manager with a vehicle list populated with the results of scanning a text file that contains a pre-saved vehicle waiting list
	 * 
	 * @param s Scanner that reads a text file detailing a pre-saved waiting list of vehicles
	 */
	public ServiceManager(Scanner s) {
		myGarage = new Garage();
		waitingCars = new VehicleList(s);
	}

	/**
	 * Adds a vehicle to the vehicle wait list when passed pieces of information about the vehicle
	 * 
	 * @param type Parameter detailing whether vehicle is regular or hybrid/electric
	 * @param license Parameter detailing the license plate of the vehicle
	 * @param name Parameter detailing the vehicle's owner's name
	 * @param tierInd Parameter detailing the Tiered priority status
	 */
	@Override
	public void putOnWaitingList(String type, String license, String name, int tierInd) {
		try {
			if (type.equalsIgnoreCase("e")) {
				waitingCars.add(new HybridElectricCar(license, name, tierInd));
			} else if (type.equalsIgnoreCase("r")) {
				waitingCars.add(new RegularCar(license, name, tierInd));
			}
		} catch (BadVehicleInformationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Adds a vehicle to the vehicle wait list when passed a Tiered object parameter
	 * 
	 * @param tier Vehicle to add to vehicle wait list
	 */
	@Override
	public void putOnWaitingList(Tiered tier) {
		waitingCars.add((Vehicle) tier);
	}

	/**
	 * Returns the wait-listed vehicle that appears in the specified position under the given filter
	 * 
	 * @param filter Filter applied to wait-listed vehicles
	 * @param position Position of wait-listed vehicle in list of results of applied filter
	 * @return Vehicle at specified position under the given filter
	 */
	@Override
	public Tiered getWaitingItem(String filter, int position) {
		return waitingCars.get(filter, position);
	}

	/**
	 * Removes the vehicle that appears in the specified position under the given filter
	 * 
	 * @param filter Filter applied to vehicle waiting list
	 * @param index Position of vehicle in filtered list to remove from waiting list
	 * @return Vehicle removed from the vehicle waiting list
	 */
	@Override
	public Tiered remove(String filter, int index) {
		return waitingCars.remove(filter, index);
	}

	/**
	 * Fills service bays with vehicles
	 */
	@Override
	public void fillServiceBays() {
		int index = 0;
		if (waitingCars != null) {
			SimpleIterator<Vehicle> cursor = waitingCars.iterator();
			Vehicle current;
			while (cursor.hasNext() && myGarage.numberOfEmptyBays() > 0) {
				try {
					current = (Vehicle) cursor.next();
					current.pickServiceBay(myGarage);
					waitingCars.remove("", index);
				} catch (NoAvailableBayException e) {
					index++;
				}
			}
		}
		
	}

	/**
	 * Releases the vehicle the in the service bay at the index of the array of service bays that represent the service garage
	 * 
	 * @param bay Index of service bay array to release vehicle from
	 * @return Vehicle released from specified service bay
	 */
	@Override
	public Tiered releaseFromService(int bay) {
		return myGarage.release(bay);
	}

	/**
	 * Adds a new service bay to the array of service bays representing the garage.
	 */
	@Override
	public void addNewBay() {
	     myGarage.addRepairBay();
	}

	/**
	 * Returns a string representation of the vehicles in the vehicle waiting list that meet the applied filter
	 * 
	 * @param filter Filter applied to vehicle waiting list
	 * @return String representation of the vehicles in the vehicle waiting list that meet the applied filter
	 */
	@Override
	public String printWaitList(String filter) {
		return waitingCars.filteredList(filter);
	}

	/**
	 * Returns a string representation of the service bays that are currently open
	 * 
	 * @return String representation of the currently open service bays
	 */
	@Override
	public String printServiceBays() {
		 String bay = "";
	        for(int i = 0; i < myGarage.getSize(); i++) {
	            bay += myGarage.getBayAt(i).toString() + "\n";
	        }
	        return bay;
	}


}
