package edu.ncsu.csc216.garage.model.vehicle;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests the Vehicle class.
 * 
 * @author Will Parker
 * @author Christine Park
 *
 */
public class VehicleTest {
	
	/** Valid license 1 */
	String license1 = "CA-2345B";
	
	/** Valid license 2 */
	String license2 = "BLUE";
	
	/** Valid name 1 */
	String name1 = "Park, Christine";
	
	/** Valid name 2 */
	String name2 = "Parker, William";
	
	/** Valid tier 1 */
	int tier1 = 0;
	
	/** Valid tier 2 */
	int tier2 = 3;
	
	/**
	 * Tests the Vehicle constructor with parameters representing the vehicle's license plate, owner's name, and service tier
	 * 
	 * @throws BadVehicleInformationException if invalid vehicle information given
	 */
	@Test
	public void testVehicle() throws BadVehicleInformationException {
		// empty license
        try {
            new RegularCar(" ", name2, tier2);
            fail();
        } catch (BadVehicleInformationException e) {
            assertEquals("License cannot be blank.", e.getLocalizedMessage());
        }
        
        // license with white space in the middle
        try {
        	new RegularCar("A BCD", name2, tier2);
        } catch (BadVehicleInformationException e) {
        	assertEquals("License cannot be blank.", e.getLocalizedMessage());
        }
        
    	// null license
        try {
            new RegularCar(null, name2, tier2);
            fail();
        } catch (BadVehicleInformationException e) {
            assertEquals("License cannot be blank.", e.getLocalizedMessage());
        }
        
        // license too long
        try {
            new RegularCar("123456789", name2, tier2);
            fail();
        } catch (BadVehicleInformationException e) {
            assertEquals("License cannot be more than 8 characters.", e.getLocalizedMessage());
        }
        
        // space name
        try {
            new HybridElectricCar(license2, " ", tier2);
            fail();
        } catch (BadVehicleInformationException e) {
            assertEquals("Owner name cannot be blank.", e.getLocalizedMessage());
        }
        
        // empty name
        try {
            new HybridElectricCar(license2, "", tier2);
            fail();
        } catch (BadVehicleInformationException e) {
            assertEquals("Owner name cannot be blank.", e.getLocalizedMessage());
        }
        
        // null name
        try {
            new RegularCar(license2, null, tier2);
            fail();
        } catch (BadVehicleInformationException e) {
            assertEquals("Owner name cannot be blank.", e.getLocalizedMessage());
        }
        
        // Invalid tier too high
        try {
            new RegularCar(license2, name2, 5);
            fail();
        } catch (BadVehicleInformationException e) {
            assertEquals("Invalid tier.", e.getLocalizedMessage());
        }
        
     // Invalid tier too low
        try {
            new RegularCar(license2, name2, -1);
            fail();
        } catch (BadVehicleInformationException e) {
            assertEquals("Invalid tier.", e.getLocalizedMessage());
        }
        
        RegularCar rc = new RegularCar(license2, name2, tier1);
        assertEquals("BLUE", rc.getLicense());
        assertEquals("Parker, William", rc.getName());
        assertEquals(0, rc.getTier());
  
	}

	/**
	 * Tests the meetsFilter method
	 */
	@Test
	public void testMeetsFilter() {
		Vehicle regular1 = null;
		Vehicle regular2 = null;
		Vehicle regular3 = null;
		Vehicle regular4 = null;
		Vehicle hev1 = null;
		Vehicle hev2 = null;
		try {
	        regular1 = new RegularCar(license2, name2, tier2);
	    } catch (BadVehicleInformationException e) {
	    	fail("Should not get an exception.");
	    }
		try {
			regular2 = new RegularCar(license1, name1, tier1);
		} catch (BadVehicleInformationException e) {
			fail("Should not get an exception.");
		}
		try {
			regular3 = new RegularCar("Tag2", "Parkour, Johnny", tier1);
		} catch (BadVehicleInformationException e) {
			fail("Should not get an exception.");
		}
		try {
			regular4 = new RegularCar("Tag3", "Palmour, Johnny", tier1);
		} catch (BadVehicleInformationException e) {
			fail("Should not get an exception.");
		}
		try {
			hev1 = new HybridElectricCar("Tag4", "JumpingJacks, Johnny", tier1);
		} catch (BadVehicleInformationException e) {
			fail("Should not get an exception.");
		}
		try {
			hev2 = new HybridElectricCar("Tag5", "Parkit, Johnny", tier1);
		} catch (BadVehicleInformationException e) {
			fail("Should not get an exception.");
		}

		assertTrue(regular1.meetsFilter("park"));
		assertTrue(regular2.meetsFilter("park"));
		assertTrue(regular3.meetsFilter("park"));
		assertFalse(regular4.meetsFilter("park"));
		assertTrue(hev2.meetsFilter("park"));  
		assertFalse(hev1.meetsFilter("park")); 
		assertTrue(regular1.meetsFilter(""));
		assertTrue(regular1.meetsFilter(null));
		assertTrue(regular1.meetsFilter("   "));
	}

	/**
	 * Tests the toString method
	 */
	@Test
	public void testToString() {
		RegularCar rc = null;
		HybridElectricCar hec = null;
		try {
			rc = new RegularCar(license2, name2, tier1);
        } catch (BadVehicleInformationException e) {
            fail("Should not get an exception.");
        }
		try {
			hec = new HybridElectricCar(license1, name1, tier2);
        } catch (BadVehicleInformationException e) {
            fail("Should not get an exception.");
        }
		assertEquals("R None      BLUE      Parker, William", rc.toString());
		assertEquals("E Platinum  CA-2345B  Park, Christine", hec.toString());
	}

	/**
	 * Tests the compareToTier method
	 */
	@Test
	public void testCompareToTier() {
		Vehicle regular1 = null;
		Vehicle regular2 = null;
		Vehicle regular3 = null;
		Vehicle regular5 = null;
		Vehicle regular6 = null;
		try {
	        regular1 = new RegularCar("Tag1", "Park, Christine", 0);
	    } catch (BadVehicleInformationException e) {
	    	fail("Should not get an exception.");
	    }
		try {
			regular2 = new RegularCar("Tag2", "Parker, William", 1);
		} catch (BadVehicleInformationException e) {
			fail("Should not get an exception.");
		}
		try {
			regular3 = new RegularCar("Tag3", "Parkour, Johnny", 2);
		} catch (BadVehicleInformationException e) {
			fail("Should not get an exception.");
		}
		try {
			regular6 = new RegularCar("Tag6", "Jones, Junie", 0);
		} catch (BadVehicleInformationException e) {
			fail("Should not get an exception.");
		}		
		
		assertEquals(1, regular1.compareToTier(regular5));
		assertEquals(0, regular1.compareToTier(regular6));
		assertEquals(1, regular2.compareToTier(regular1));
		assertEquals(-1, regular2.compareToTier(regular3));
	}
	
	/**
	 * Tests the equals method
	 */
	@Test
	public void testEquals() {
		Vehicle regular1 = null;
		Vehicle regular2 = null;
		Vehicle regular3 = null;
		Vehicle regular4 = null;
		Vehicle regular5 = null;
		Vehicle hev1 = null;
		// Baseline for testing
		try {
	        regular1 = new RegularCar("Tag1", "Name1", 0);
	    } catch (BadVehicleInformationException e) {
	    	fail("Should not get an exception.");
	    }
		// Equal car to regular1
		try {
			regular2 = new RegularCar("Tag1", "Name1", 0);
		} catch (BadVehicleInformationException e) {
			fail("Should not get an exception.");
		}
		// Different tag than regular1
		try {
			regular3 = new RegularCar("Tag2", "Name1", 0);
		} catch (BadVehicleInformationException e) {
			fail("Should not get an exception.");
		}
		// Different name than regular1
		try {
			regular4 = new RegularCar("Tag1", "Name2", 0);
		} catch (BadVehicleInformationException e) {
			fail("Should not get an exception.");
		}
		// Different tier than regular1
		try {
			regular5 = new RegularCar("Tag1", "Name1", 1);
		} catch (BadVehicleInformationException e) {
			fail("Should not get an exception.");
		}
		// Different type than regular1
		try {
			hev1 = new HybridElectricCar("Tag1", "Name1", 0);
		} catch (BadVehicleInformationException e) {
			fail("Should not get an exception.");
		}
		
		assertTrue(regular1.equals(regular1));
		assertTrue(regular1.equals(regular2));
		assertFalse(regular1.equals(regular3));
		assertFalse(regular1.equals(regular4));
		assertFalse(regular1.equals(regular5));
		assertFalse(regular1.equals(hev1));
	}
	
	/**
	 * Tests the hashcode method
	 */
	@Test
	public void testHashCode() {
		Vehicle regular1 = null;
		Vehicle regular2 = null;
		Vehicle regular3 = null;
		// Baseline for testing
		try {
	        regular1 = new RegularCar("Tag1", "Name1", 0);
	    } catch (BadVehicleInformationException e) {
	    	fail("Should not get an exception.");
	    }
		// Equal car to regular1
		try {
			regular2 = new RegularCar("Tag1", "Name1", 0);
		} catch (BadVehicleInformationException e) {
			fail("Should not get an exception.");
		}
		// Different tag than regular1
		try {
			regular3 = new RegularCar("Tag2", "Name1", 0);
		} catch (BadVehicleInformationException e) {
			fail("Should not get an exception.");
		}
		
		assertTrue(regular1.hashCode() == regular1.hashCode());
		assertTrue(regular1.hashCode() == regular2.hashCode());
		assertFalse(regular1.hashCode() == regular3.hashCode());
	}
}
