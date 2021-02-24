import org.junit.Test;
import junit.framework.TestCase;

/**
 * Program to test Admissions methods
 * @author Suzanne Balik
 * @author Christine Park
 */
public class AdmissionsTest extends TestCase {
 
 /* getEngineeringAdmissionStatus Test Cases */
    @Test
    public void testGetEngineeringAdmissionStatus1() {
        
        assertEquals("Admissions.getEngineeringAdmissionStatus(750, 600, 4.0, 0)", "Admit",
                     Admissions.getEngineeringAdmissionStatus(750, 600, 4.0, 0));
        
    }
    
    @Test
    public void testGetEngineeringAdmissionStatus2() {
        
        assertEquals("Admissions.getEngineeringAdmissionStatus(600, 500, 4.0, 0)", "Defer",
                     Admissions.getEngineeringAdmissionStatus(600, 500, 4.0, 0));
    }

    @Test
    public void testGetEngineeringAdmissionStatus3() {
        
        assertEquals("Admissions.getEngineeringAdmissionStatus(600, 500, 4.0, 2)", "Admit",
                     Admissions.getEngineeringAdmissionStatus(600, 500, 4.0, 2));
    }
    
    @Test
    public void testGetEngineeringAdmissionStatus4() {
        
        assertEquals("Admissions.getEngineeringAdmissionStatus(300, 300, 4.0, 0)", "Deny",
                     Admissions.getEngineeringAdmissionStatus(300, 300, 4.0, 0));             
        
    }
    
    @Test
    public void testGetEngineeringAdmissionStatus5() {
        
        assertEquals("Admissions.getEngineeringAdmissionStatus(750, 600, 3.7, 0)", "Deny",
                     Admissions.getEngineeringAdmissionStatus(750, 600, 3.7, 0));               
    }
    
    @Test
    public void testGetEngineeringAdmissionStatus6() {
        
        assertEquals("Admissions.getEngineeringAdmissionStatus(700, 600, 3.8, 0)", "Admit",
                     Admissions.getEngineeringAdmissionStatus(700, 600, 3.8, 0));               
    }

    /* getJournalismAdmissionStatus Test Cases */
    @Test
    public void testGetJournalismAdmissionStatus1() {
        
        assertEquals("Admissions.getJournalismAdmissionStatus(750, 7, 4.0, 0)", "Admit",
                     Admissions.getJournalismAdmissionStatus(750, 7, 4.0, 0));
        
    }

    @Test
    public void testGetJournalismAdmissionStatus2() {
        
        assertEquals("Admissions.getJournalismAdmissionStatus(500, 2, 2.99, 0)", "Deny",
                     Admissions.getJournalismAdmissionStatus(500, 2, 2.99, 0));
    }

    @Test
    public void testGetJournalismAdmissionStatus3() {
        
        assertEquals("Admissions.getJournalismAdmissionStatus(400, 6, 4.0, 0)", "Defer",
                     Admissions.getJournalismAdmissionStatus(400, 6, 4.0, 0));
    }
    
    @Test
    public void testGetJournalismAdmissionStatus4() {
        
        assertEquals("Admissions.getJournalismAdmissionStatus(400, 6, 4.0, 2)", "Admit",
                     Admissions.getJournalismAdmissionStatus(400, 6, 4.0, 2));
    }
    
    @Test
    public void testGetJournalismAdmissionStatus5() {
        
        assertEquals("Admissions.getJournalismAdmissionStatus(600, 7, 3.0, 0)", "Admit",
                     Admissions.getJournalismAdmissionStatus(600, 7, 3.0, 0));
    }
    
    @Test
    public void testGetJournalismAdmissionStatus6() {
        
        assertEquals("Admissions.getJournalismAdmissionStatus(600, 7, 2.99, 0)", "Deny",
                     Admissions.getJournalismAdmissionStatus(600, 7, 2.99, 0));
    }
    
    /* getFineArtsAdmissionStatus Test Cases */
    @Test
    public void testGetFineArtsAdmissionStatus1() {
        
        assertEquals("Admissions.getFineArtsAdmissionStatus('E', 4.0, 0)", "Admit",
                     Admissions.getFineArtsAdmissionStatus('E', 4.0, 0));
        
    }

    @Test
    public void testGetFineArtsAdmissionStatus2() {
        
        assertEquals("Admissions.getFineArtsAdmissionStatus('P', 4.0, 0)", "Deny",
                     Admissions.getFineArtsAdmissionStatus('P', 4.0, 0));
        
    }
    
    @Test
    public void testGetFineArtsAdmissionStatus3() {
        
        assertEquals("Admissions.getFineArtsAdmissionStatus('G', 4.0, 0)", "Defer",
                     Admissions.getFineArtsAdmissionStatus('G', 4.0, 0));
        
    }
    
    @Test
    public void testGetFineArtsAdmissionStatus4() {
        
        assertEquals("Admissions.getFineArtsAdmissionStatus('G', 4.0, 2)", "Admit",
                     Admissions.getFineArtsAdmissionStatus('G', 4.0, 2));
        
    }
    
    @Test
    public void testGetFineArtsAdmissionStatus5() {
        
        assertEquals("Admissions.getFineArtsAdmissionStatus('G', 2.7, 2)", "Deny",
                     Admissions.getFineArtsAdmissionStatus('G', 2.7, 2));
        
    }
    
    @Test
    public void testGetFineArtsAdmissionStatus6() {
        
        assertEquals("Admissions.getFineArtsAdmissionStatus('F', 4.0, 2)", "Deny",
                     Admissions.getFineArtsAdmissionStatus('F', 4.0, 2));
        
    }

    /**
     * Test the Admissions methods with invalid values
     */ 
    @Test
    public void testInvalidMethods() {
        // Invalid test cases are provided for you below - You do NOT
        // need to add additional invalid tests. Just make sure these
        // pass!
        try {
            Admissions.getEngineeringAdmissionStatus(100, 600, 4.0, 0);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Invalid SAT score", e.getMessage());
        }

        try {
            Admissions.getEngineeringAdmissionStatus(200, 900, 4.0, 0);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Invalid SAT score", e.getMessage());
        }

        try {
            Admissions.getEngineeringAdmissionStatus(200, 600, -5, 0);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Invalid GPA", e.getMessage());
        }

        try {
            Admissions.getEngineeringAdmissionStatus(400, 600, 4.0, -5);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Invalid number of alumni", e.getMessage());
        }

        try {
            Admissions.getJournalismAdmissionStatus(0, 2, 4.0, 0);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Invalid SAT score", e.getMessage());
        }

        try {
            Admissions.getJournalismAdmissionStatus(900, 4, 4.0, 0);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Invalid SAT score", e.getMessage());
        }

        try {
            Admissions.getJournalismAdmissionStatus(700, 1, 4.0, 0);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Invalid SAT essay score", e.getMessage());
        }

        try {
            Admissions.getJournalismAdmissionStatus(200, 6, -5, 0);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Invalid GPA", e.getMessage());
        }

        try {
            Admissions.getJournalismAdmissionStatus(400, 5, 3.0, -5);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Invalid number of alumni", e.getMessage());
        }

        try {
            Admissions.getFineArtsAdmissionStatus('x', 4.0, 0);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Invalid portfolio rating", e.getMessage());
        }

        try {
            Admissions.getFineArtsAdmissionStatus('E', -3, 0);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Invalid GPA", e.getMessage());
        }

        try {
            Admissions.getFineArtsAdmissionStatus('G', 3.0, -2);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Invalid number of alumni", e.getMessage());
        }

    }
}