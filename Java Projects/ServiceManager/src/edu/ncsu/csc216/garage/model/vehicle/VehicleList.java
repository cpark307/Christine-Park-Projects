package edu.ncsu.csc216.garage.model.vehicle;

import java.util.NoSuchElementException;
import java.util.Scanner;

import edu.ncsu.csc216.garage.model.util.SimpleIterator;

/**
 * Concrete class representing the list of vehicles at the service garage in the form of a linked list.
 * 
 * @author Will Parker
 * @author Christine Park
 */
public class VehicleList {
	
	/** Node pointing to the first vehicle in the linked list */
	private Node head;
	
	/**
	 * Constructs a new VehicleList with the information passed from a scanner of a pre-saved list of vehicles 
	 * 
	 * @param s Scanner used to populate constructed VehicleList
	 */
	public VehicleList(Scanner s) {
		if (s == null) {
			return;
		}
		while (s.hasNextLine()) {
			String line = s.nextLine();
			@SuppressWarnings("resource")
			Scanner lineScanner = new Scanner(line);
			String type = "";
			String license = "";
			String name = "";
			int tier = 0;
			while (lineScanner.hasNext()) {
				try {
					type = lineScanner.next();
					tier = lineScanner.nextInt();
					license = lineScanner.next();
					name = lineScanner.nextLine();
				} catch (Exception e) {
					continue;
				}
			} try {
				if (type.equalsIgnoreCase("e")) {
					add(new HybridElectricCar(license, name, tier));
				} else if (type.equalsIgnoreCase("r")) {
					add(new RegularCar(license, name, tier));
				}
			} catch (BadVehicleInformationException e) {
				continue;
			}
		}
	}
	
	/**
	 * Constructs a new, empty linked list of vehicles
	 */
	public VehicleList() {
		head = null;
	}
	
	/**
	 * Returns a simple iterator initialized to point at the first element in the linked list of vehicles
	 * 
	 * @return Iterator pointing to an element of the linked list of vehicles
	 */
	public SimpleIterator<Vehicle> iterator(){
		return new Cursor();
	}
	
	/**
	 * Removes the vehicle in the position of vehicles that meet the filter passed by the client
	 * 
	 * @param filter Filter applied to vehicle list
	 * @param index Index of vehicle in list of vehicles that meet filter
	 * @return Vehicle removed from vehicle list
	 */
	public Vehicle remove(String filter, int index) {
		if (head == null || index < 0) {
			return null;
		}
		
		Vehicle v = get(filter, index);
		if (v == null) {
			return null;
		}
		Node previous = null;
		Node current = head;
		while (!v.equals(current.v) && current.v != null) {
			previous = current;
			current = current.next;
		}
		// Removes the first item in the list
		if (current == head) {
			head = current.next;
			current.next = null;
			return v;
		}
		previous.next = current.next;
		current.next = null;
		return v;
	}
	
	/**
	 * Gets the vehicle in the position of vehicles that meet the filter passed by the client
	 * 
	 * @param filter Filter applied to vehicle list
	 * @param index Index of vehicle in list of vehicles that meet filter
	 * @return Vehicle at index of vehicles in list that meet the filter
	 */
	public Vehicle get(String filter, int index) {
		Node current = findTrailingNode(filter, index);
		if (current != null) {
			return current.v;
		} else {
			return null;
		}
	}
	
	/**
	 * Adds a vehicle to the vehicle list
	 * 
	 * @param v Vehicle to add to vehicle list 
	 */
	public void add(Vehicle v) {
		if (head == null) {
			head = new Node(v, null);
		} else if (v.getTier() > head.v.getTier()) {
			head = new Node(v, head);
		} else {
			Node previous = head;
			Node current = head.next;
			while (current != null && v.getTier() <= current.v.getTier()) {
				previous = current;
				current = current.next;
			}
			previous.next = new Node(v, current);
		}
	}
	
	/**
	 * Finds the trailing node at a specified index of vehicles in the vehicle list that meet the filter
	 * 
	 * @param filter Filter applied to vehicle list
	 * @param index Index to of node to find
	 * @return Node of vehicle at specified index of filtered vehicles
	 */
	private Node findTrailingNode(String filter, int index) {
		if (head == null || index < 0) {
			return null;
		}
		int indexOfFilterCounter = 0;
		Node current = head;
		while (current != null) {
			if (current.v.meetsFilter(filter)) {
				if (indexOfFilterCounter == index) {
					return current;
				} else {
					indexOfFilterCounter++;
					if (current.next != null) {
						current = current.next;
					} else {
						return null;
					}
				}
			} else {
				while (!current.v.meetsFilter(filter)) {
					if (current.next == null) {
						return null;
					} else {
						current = current.next;
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * Returns a string representation of all vehicles that meet the filter applied by the client
	 * 
	 * @param filter Filter applied to vehicle list
	 * @return String representation of vehicles in the vehicle list that meet the filter 
	 */
	public String filteredList(String filter) {
		if (head == null) {
			return "";
		}
		SimpleIterator<Vehicle> iterator = iterator();
		String filteredList = "";
		while (iterator.hasNext()) {
			Vehicle v = (Vehicle) iterator.next();
			if (v.meetsFilter(filter)) {
				filteredList += v.toString() + "\n";
			}
		}
		return filteredList;
	}
	
	/**
	 * Inner class that provides for node construction used to traverse/modify the linked list of vehicles
	 * 
	 * @author Will Parker
	 * @author Christine Park
	 */
	private class Node {
		
		/** Link for a constructed node that points to the next node in the linked list */
		public Node next;
		
		/** Vehicle information for each node */
		public Vehicle v;
		
		/**
		 * Constructs a new node with the vehicle and link to the next node as passed by the client
		 * 
		 * @param v Vehicle to assign to constructed node's state
		 * @param next Next node in linked list for constructed node to point to
		 */
		public Node (Vehicle v, Node next) {
			this.v = v;
			this.next = next;
		}
		
	}
	
	/**
	 * Inner class that creates an iterator used to traverse the linked list of vehicles
	 * 
	 * @author Will Parker
	 * @author Christine Park
	 */
	private class Cursor implements SimpleIterator<Vehicle> {

		/** Node that will be used as a cursor/iterator to traverse the linked list */
		private Node cursor;
		
		/**
		 * Constructs a cursor to point at the front of the linked list of vehicles
		 */
		private Cursor() {
			cursor = head;
		}
		
		/**
		 * Returns true if the linked list of vehicles has another vehicle after the one the cursor is pointing to
		 * 
		 * @return True if the linked list of vehicles has another item after the one the cursor is pointing to
		 */
		@Override
		public boolean hasNext() {
			if (cursor != null) {
				return true;
			}
			return false;
		}

		/**
		 * Advances the cursor to the next vehicle in the linked list and returns the vehicle at that index
		 * 
		 * @return Vehicle cursor is pointing to
		 * @throws NoSuchElementException if cursor is pointing to an empty Node
		 */
		@Override
		public Vehicle next() {
			if (cursor == null) {
				throw new NoSuchElementException();
			}
			Vehicle v = cursor.v;
			cursor = cursor.next;
			return v;
		}	
	}
}
