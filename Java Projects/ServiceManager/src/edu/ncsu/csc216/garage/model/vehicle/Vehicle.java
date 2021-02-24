package edu.ncsu.csc216.garage.model.vehicle;

import edu.ncsu.csc216.garage.model.service_garage.Garage;
import edu.ncsu.csc216.garage.model.service_garage.NoAvailableBayException;

/** 
 * Abstract class representing a vehicle that is serviceable in the garage. Vehicle will implement the Tiered interface that enables it to maintain state
 * detailing the service tier priority. Vehicle serves as a parent class for HybridElectricCar and RegularCar and will maintain state detailing a vehicle's
 * license, owner's name, and tierIndex. 
 * 
 * @author Christine Park
 * @author Will Parker
 */
public abstract class Vehicle implements Tiered {
	
	/** Vehicle license in string representation */
	private String license;
	
	/** Owner's name */
    private String name;
    
    /** Represents the vehicle's preferred service tier status */
    private int tierIndex;
    
    /** Names for number tiers */ 
    public static final String[] CUSTOMER_TIER = {"None", "Silver", "Gold", "Platinum"};

    /**
     * Constructor that creates a vehicle out of a license, owner name, and tier status.
     * 
     * @param license license string
     * @param name owner's name
     * @param tierIndex represents the vehicle's preferred service tier status
     * @throws BadVehicleInformationException if the license of owner are not valid
     */
    public Vehicle(String license, String name, int tierIndex) throws BadVehicleInformationException {
    	setLicense(license);
    	setName(name);
    	setTier(tierIndex);
    }
   

    /**
     * The vehicle picks a service bay.
     * 
     * @throws NoAvailableBayException if there is no bay open
     * @param garage with service bays for selection
     */
    public abstract void pickServiceBay(Garage garage) throws NoAvailableBayException;
    

    /**
     * True if filter is a prefix to the owner’s last name. The check is case insensitive.
     * A filter of null or the null string (e.g., “”) would return true. 
     * 
     * @param filter value to match the prefix 
     * @return true if filter matches prefix 
     */
    public boolean meetsFilter(String filter) {
		if (filter == null || filter.equals("")) {
			return true;
		}
		filter = filter.toLowerCase().trim();
		if (filter.equals("")) {
			return true;
		}
		if (getName().toLowerCase().startsWith(filter)) {
			return true;
		}
		return false;
    }
    
    /**
     * Returns a String representation of the Vehicle
     * 
     * @return String representation of the Vehicle
     */
    public String toString(){
    	return String.format("%-10s", CUSTOMER_TIER[getTier()]) + String.format("%-10s", getLicense()) + getName();
    }
    
    /**
     * Returns name of owner
     * 
     * @return owner name
     */
    public String getName() {
    	return name;
    }
    
	 /**
	  * Returns the tier of the object
	  * 
	  * @return tier of the object
	  */
	 public int getTier() {
		 return tierIndex;
	 }
    
    /**
     * Returns license of Vehicle
     * 
     * @return license
     */
    public String getLicense() {
    	return license;
    }
    
	/**
	 * Compare the tier status of this object with another. Returns 0 if the two match, 
	 * a negative number if the tier status of this object is less than the other’s, a 
	 * positive number if the tier status of this object is greater. Required for ordering.
	 * 
	 * @param t tier
	 * @return 0 if match, negative number if tier of this object is less than the other's, a positive number if the tier status of this object is greater
	 */
	 public int compareToTier(Tiered t) {
		 if (t == null) {
			 return 1;
		 } else if (tierIndex == t.getTier()) {
			 return 0;
		 } else if (tierIndex < t.getTier()) {
			 return -1;
		 } else {
			 return 1;
		 }
	 }
	 
	 /**
	  * Sets the tier of the object. Throws BadVehicleInformationException if attempt to set tier to invalid value is made.
	  * 
	  * @param tier new tier
	  * @throws BadVehicleInformationException if attempt to set tier to invalid value is made
	  */
	 public void setTier(int tier) throws BadVehicleInformationException {
		 if (tier < 0 || tier > 3) {
			 throw new BadVehicleInformationException();
		 } else {
			 this.tierIndex = tier;
		 }
	 }
	 
	 /**
	  * Sets owner's name. Throws a BadVehicleInformation exception if the name to set is invalid.
	  * 
	  * @param name owner name
	  * @throws BadVehicleInformationException if name to set is blank
	  */
	 private void setName(String name) throws BadVehicleInformationException {
		 if (name == null || name.equals("")) {
			 throw new BadVehicleInformationException("Owner name cannot be blank.");
		 }
		 name = name.trim();
		 if (name.equals("")) {
			 throw new BadVehicleInformationException("Owner name cannot be blank.");
		 }
		 this.name = name;
	 }
	 
	 /**
	  * Sets license plate value. Throws a BadVehicleInformation exception if the license to set is invalid.
	  * 
	  * @param license new license
	  * @throws BadVehicleInformationException if license to set is empty, has blank space within license string, or is longer than 8 characters
	  */
	 private void setLicense(String license) throws BadVehicleInformationException {
		 if (license == null) {
			 throw new BadVehicleInformationException("License cannot be blank.");
		 }
		 license = license.trim();
		 if (license.equals("")) {
			 throw new BadVehicleInformationException("License cannot be blank.");
		 }
		 for (int i = 0; i < license.length(); i++) {
			 if (license.charAt(i) == ' ') {
				 throw new BadVehicleInformationException("License cannot be blank.");
			 }
		 }
		 if (license.length() > 8) {
			 throw new BadVehicleInformationException("License cannot be more than 8 characters.");
		 }
		 this.license = license;
	 }


	 /**
	  * Generates hash codes for Vehicle objects using the license, name, and tier index
	  * 
	  * @return hash code for Vehicle object
	  */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + license.hashCode();
		result = prime * result + name.hashCode();
		result = prime * result + tierIndex;
		return result;
	}


	/**
	 * Checks for Vehicle object equality using license, name, and tier index fields.
	 * 
	 * @param obj Object to use for equality comparison
	 * @return True if obj and this Vehicle are equal
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		Vehicle other = (Vehicle) obj;
		if (!license.equals(other.license))
			return false;
		if (!name.equals(other.name))
			return false;
		if (tierIndex != other.tierIndex)
			return false;
		return true;
	} 
}
