import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

import org.junit.Test;
import junit.framework.TestCase;

/**
 * Program to test TabConverter methods
 * 
 * @author Suzanne Balik
 * @author Christine Park
 */
public class TabConverterTest extends TestCase {

    /** One tab */
    public static final String ONE_TAB = "Apple\tBall Cat Dog";

    /** One tab expanded to 3 spaces */
    public static final String ONE_TAB_EXPANDED = "Apple   Ball Cat Dog";

    @Test
    public void testExpandLine1() {
        String description = "Expand Line 1";
        String actual = TabConverter.expandLine(ONE_TAB, 3);
        assertEquals(description, ONE_TAB_EXPANDED, actual);
    }
    
    @Test
    public void testUnexpandLine1() {
        String description = "Unexpand Line 1";
        String actual = TabConverter.unexpandLine(ONE_TAB_EXPANDED, 3);
        assertEquals(description, ONE_TAB, actual);
    }


    /** No tab */
    public static final String NO_TAB = "AppleBall Cat Dog";

    /** No tab expanded to 3 spaces */
    public static final String NO_TAB_EXPANDED = "AppleBall Cat Dog";
    
    @Test
    public void testExpandLine2() {
        String description = "Expand Line 2";
        String actual = TabConverter.expandLine(NO_TAB, 3);
        assertEquals(description, NO_TAB_EXPANDED, actual);
    }
    
    @Test
    public void testUnexpandLine2() {
        String description = "Unexpand Line 2";
        String actual = TabConverter.unexpandLine(NO_TAB_EXPANDED, 3);
        assertEquals(description, NO_TAB, actual);
    }
    
    /** Start with tab */
    public static final String START_TAB = "\tAppleBall Cat Dog";

    /** Start with tab expanded to 3 spaces */
    public static final String START_TAB_EXPANDED = "   AppleBall Cat Dog";
    
    @Test
    public void testExpandLine3() {
        String description = "Expand Line 3";
        String actual = TabConverter.expandLine(START_TAB, 3);
        assertEquals(description, START_TAB_EXPANDED, actual);
    }    
    
    @Test
    public void testUnexpandLine3() {
        String description = "Unexpand Line 3";
        String actual = TabConverter.unexpandLine(START_TAB_EXPANDED, 3);
        assertEquals(description, START_TAB, actual);
    }    
    
    /** End with tab */
    public static final String END_TAB = "AppleBall Cat Dog\t";

    /** End with tab expanded to 3 spaces */
    public static final String END_TAB_EXPANDED = "AppleBall Cat Dog   ";
    
    @Test
    public void testExpandLine4() {
        String description = "Expand Line 4";
        String actual = TabConverter.expandLine(END_TAB, 3);
        assertEquals(description, END_TAB_EXPANDED, actual);
    } 

    @Test
    public void testUnexpandLine4() {
        String description = "Unexpand Line 4";
        String actual = TabConverter.unexpandLine(END_TAB_EXPANDED, 3);
        assertEquals(description, END_TAB, actual);
    }    
    
    /** Newline with tab */
    public static final String NEWLINE_TAB = "Apple\tBall Cat Dog \n Apple\tBall Catt Dog";

    /** Newline with tab expanded to 3 spaces */
    public static final String NEWLINE_TAB_EXPAND = "Apple   Ball Cat Dog \n Apple   Ball Catt Dog";
    
    @Test
    public void testExpandLine5() {
        String description = "Expand Line 5";
        String actual = TabConverter.expandLine(NEWLINE_TAB, 3);
        assertEquals(description, NEWLINE_TAB_EXPAND, actual);
    } 
    
    @Test
    public void testUnexpandLine5() {
        String description = "Unexpand Line 5";
        String actual = TabConverter.unexpandLine(NEWLINE_TAB_EXPAND, 3);
        assertEquals(description, NEWLINE_TAB, actual);
    }    
    
    /** Multiple tabs One line*/
    public static final String MULT_TAB = "Apple\tBall\tCat\tDog ";

    /** Multiple tabs One line Space expanded to 3 spaces */
    public static final String MULT_TAB_EXPANDED = "Apple   Ball   Cat   Dog ";
    
    @Test
    public void testExpandLine6() {
        String description = "Expand Line 6";
        String actual = TabConverter.expandLine(MULT_TAB, 3);
        assertEquals(description, MULT_TAB_EXPANDED, actual);
    } 
    
    @Test
    public void testUnexpandLine6() {
        String description = "Unexpand Line 6";
        String actual = TabConverter.unexpandLine(MULT_TAB_EXPANDED, 3);
        assertEquals(description, MULT_TAB, actual);
    }        
    
    /** Consecutive spaces and tabs*/
    public static final String NULL_STRING = "";

    /** Consecutive spaces and tabs expanded to 3 spaces*/
    public static final String NULLSTRING_EXPANDED = "";

    @Test
    public void testExpandLine7() {
        String description = "Expand Line 7";
        String actual = TabConverter.expandLine(NULL_STRING , 2);
        assertEquals(description, NULLSTRING_EXPANDED, actual);
    } 
    
    @Test
    public void testUnexpandLine7() {
        String description = "Unexpand Line 7";
        String actual = TabConverter.unexpandLine(NULLSTRING_EXPANDED, 2);
        assertEquals(description, NULL_STRING , actual);
    }   
    
    /**
     * Test the TabConverter methods with invalid values
     */
    @Test
    public void testInvalidMethods() {
        // Invalid test cases are provided for you below - You do NOT
        // need to add additional invalid tests. Just make sure these
        // pass!
        try {
            TabConverter.processFile(true, 1, null, null);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Invalid number of spaces", e.getMessage());
        }

        try {
            TabConverter.expandLine("hello there", 0);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Invalid number of spaces", e.getMessage());
        }

        try {
            TabConverter.unexpandLine("see you later", -3);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Invalid number of spaces", e.getMessage());
        }
    }

    /**
     * Tests getInputScanner
     */
    @Test
    public void testGetInputScanner() {
        // You do NOT need to add additional getInputScanner tests. Just make sure these
        // pass!
        Scanner input = TabConverter.getInputScanner("test-files/test1.txt");
        assertNotNull(input);
        assertEquals("abc", input.next());
        assertTrue(input.hasNext());
        input.close();
    }

    /**
     * Tests getOutputPrintStream
     */
    @Test
    public void testGetOutputPrintStream() throws FileNotFoundException {
        // You do NOT need to add additional getOutputPrintStream tests. Just make sure
        // these pass!
        File file = new File("test-files/testGetOutputPrintStream.txt");
        if (file.exists()) {
            file.delete();
        }
        Scanner inputScanner = new Scanner("test-files/testGetOutputPrintStream.txt");
        PrintStream output = TabConverter.getOutputPrintStream(inputScanner);
        assertNotNull(output);
        output.println("Hello output!!!");
        output.close();
        Scanner testFile = new Scanner(new File("test-files/testGetOutputPrintStream.txt"));
        assertNotNull(testFile);
        assertEquals("Hello output!!!", testFile.nextLine());
        assertFalse(testFile.hasNextLine());
        testFile.close();
    }

    /**
     * Tests processFile
     */
    @Test
    public void testProcessFile() throws FileNotFoundException {
        // You do NOT need to add additional processFile tests. Just make sure
        // these pass!
        Scanner input = new Scanner(new File("test-files/test1.txt"));
        PrintStream output = new PrintStream(new File("test-files/test1-expand.txt"));

        TabConverter.processFile(true, 2, input, output);

        Scanner expected = new Scanner(new File("test-files/exp_test1Expand.txt"));
        Scanner actual = new Scanner(new File("test-files/test1-expand.txt"));
        while (expected.hasNextLine()) {
            if (actual.hasNextLine()) {
                assertTrue("Matching lines", expected.nextLine().equals(actual.nextLine()));
            } else {
                fail();
            }
        }
        expected.close();
        actual.close();
    }
}
