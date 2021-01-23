package edu.ncsu.csc216.garage.model.vehicle;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.Test;

/**
 * Tests the VehicleList class
 * 
 * @author Will Parker
 * @author Christine Park
 *
 */
public class VehicleListTest {
	
	/** Valid test file for generating a vehicle list with a scanner */
	private final String validTestFile = "test-files/cars.txt";
	
	/** Invalid test file for generating a vehicle list with a scanner */
	private final String invalidTestFile2 = "test-files/not_a_file.txt";
	
	/** Instance of VehicleList */
	private VehicleList list;
	
	/** Second instance of VehicleList */
	private VehicleList list2;

	/**
	 * Tests the VehicleList constructor with a scanner parameter and the get method
	 */
	@Test
	public void testVehicleListScanner() {
		Scanner s = null;
		Vehicle v = null;
		Vehicle v2 = null;
		Vehicle v3 = null;
		try {
			s = new Scanner(new File(validTestFile));
		} catch (FileNotFoundException e) {
			fail("File does not exist.");
		}
		list = new VehicleList(s);
		try {
			v = new RegularCar("HI-01345", "Rhyne, Lauren", 3);
			v2 = new RegularCar("VA-121A", "Henderson, William", 1);
			v3 = new HybridElectricCar("CN09822", "Hughes, Jack", 0);
		} catch (BadVehicleInformationException e) {
			fail("Should not throw an exception");
		}
		assertEquals(v, list.get(null, 0));
		assertEquals(v3, list.get("H", 1));
		assertEquals(v2, list.get("H", 0));
		Scanner t = null;
		try {
			t = new Scanner(new File(invalidTestFile2));
			fail();
		} catch (FileNotFoundException e) {
			// Not given a real file
		}
		list2 = new VehicleList(t);
		assertNull(list2.get(null, 0));
		assertNull(list2.get(null, -1));
	}

	/**
	 * Tests the null VehicleList constructor
	 */
	@Test
	public void testVehicleList() {
		list = new VehicleList();
		assertNull(list.get(null, 0));
	}


	/**
	 * Tests the remove method
	 */
	@Test
	public void testRemove() {
		Vehicle v = null;
		Vehicle v2 = null;
		Vehicle v3 = null;
		Vehicle v4 = null;
		list = new VehicleList();
		try {
			v = new RegularCar("HI-01345", "Rhyne, Lauren", 3);
			v2 = new RegularCar("VA-121A", "Henderson, William", 1);
			v3 = new HybridElectricCar("CN09822", "Hughes, Jack", 0);
			v4 = new RegularCar("NC-DUR1", "Nicholson, Henry", 3);
		} catch (BadVehicleInformationException e) {
			fail("Should not throw an exception");
		}
		list.add(v);
		list.add(v2);
		list.add(v3);
		list.add(v4);
		assertEquals(v, list.remove(null, 0));
		assertEquals(v3, list.remove("H", 1));
		assertEquals(v2, list.remove("H", 0));
		assertEquals(v4, list.get(null, 0));
	}

	/**
	 * Tests the filteredList method
	 */
	@Test
	public void testFilteredList() {
		Scanner s = null;
		Vehicle v = null;
		Vehicle v2 = null;
		Vehicle v3 = null;
		try {
			s = new Scanner(new File(validTestFile));
		} catch (FileNotFoundException e) {
			fail("File does not exist.");
		}
		list = new VehicleList(s);
		try {
			v = new RegularCar("HI-01345", "Rhyne, Lauren", 3);
			v2 = new RegularCar("VA-121A", "Henderson, William", 1);
			v3 = new HybridElectricCar("CN09822", "Hughes, Jack", 0);
		} catch (BadVehicleInformationException e) {
			fail("Should not throw an exception");
		}
		String filteredList1 = list.filteredList(null);
		Scanner filteredScanner1 = new Scanner(filteredList1);
		assertEquals(filteredScanner1.nextLine(), v.toString());
		String filteredList2 = list.filteredList("H");
		Scanner filteredScanner2 = new Scanner(filteredList2);
		assertEquals(filteredScanner2.nextLine(), v2.toString());
		assertEquals(filteredScanner2.nextLine(), v3.toString());
		filteredScanner1.close();
		filteredScanner2.close();
		list2 = new VehicleList();
		assertEquals("", list2.filteredList(null));
	}
}
