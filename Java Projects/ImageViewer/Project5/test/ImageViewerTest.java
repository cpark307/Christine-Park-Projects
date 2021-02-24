import java.awt.Color;
import java.util.Arrays;
import java.util.Scanner;

import org.junit.Test;

import junit.framework.TestCase;

/** Test class for ImageViewer class
* @author Christine Park
*/
public class ImageViewerTest extends TestCase {

    @Test
    public void testGetImageList() {
        // Create images array with room for 4 ImageInfo objects
        ImageInfo[] expected = new ImageInfo[4];

        // Create and add 4 ImageInfo objects to the images array
        expected[0] = new ImageInfo("images/rainbow.png", 150, 75, "Somewhere over the rainbow...");
        expected[1] = new ImageInfo("images/smiley.jpeg", 212, 238, "Have a great day!");
        expected[2] = new ImageInfo("images/soup.jpg", 220, 331, "Is it time for lunch?");
        expected[3] = new ImageInfo("images/puppy.jpeg", 259, 194, "Too cute for words...");

        String description = "ImageViewer.getImageList(\"test-files/misc.txt\")";
        ImageInfo[] actual = ImageViewer.getImageList("test-files/misc.txt");
        assertTrue(description, Arrays.equals(expected, actual));
 
    }
    
    @Test
    public void testGetImageList1() {
        // Create images array with room for 3 ImageInfo objects
        ImageInfo[] expected1 = new ImageInfo[4];

        // Create and add 3 ImageInfo objects to the images array
        expected1[0] = new ImageInfo("images/wolf.gif", 200, 269, "Mr. Wuf at NC State");
        expected1[1] = new ImageInfo("images/belltower.jpg", 133, 200, 
            "NC State Memorial Belltower");
        expected1[2] = new ImageInfo("images/daniels.jpeg", 266, 190, 
            "Daniels Hall - Home of CSC 116!");

        String description = "misc.txt not equal to ncstate.txt";
        ImageInfo[] actual = ImageViewer.getImageList("test-files/misc.txt");
        assertFalse(description, Arrays.equals(expected1, actual));
 
    }


    @Test
    public void testGetImageListInvalid() {
        try {
            ImageViewer.getImageList("notexist.txt");
            fail("Invalid file didn't throw exception");
        } catch (IllegalArgumentException e) {
            assertEquals("Invalid file threw IllegalArgumentException. Checking message.",
                    "Unable to access input file: notexist.txt", e.getMessage());
        }
    }

    @Test
    public void testConvertToGreyScale() {
        // Create 2D Color array
        Color[][] pixels = { { new Color(0, 0, 200), new Color(0, 0, 200) },
            { new Color(0, 0, 200), new Color(0, 0, 200) } };

        Color[][] expected = { { new Color(66, 66, 66), new Color(66, 66, 66) },
            { new Color(66, 66, 66), new Color(66, 66, 66) } };

        String description = "convertToGreyScale with 2x2 Array of Color (0, 0, 200)";
        ImageViewer.convertToGreyScale(pixels);
        assertTrue(description, Arrays.deepEquals(expected, pixels));
    }

    @Test
    public void testConvertToGreyScale1() {
        // Create 2D Color array
        Color[][] pixels = { { new Color(200, 200, 200), new Color(200, 200, 200) },
            { new Color(200, 200, 200), new Color(200, 200, 200) } };

        Color[][] expected = { { new Color(200, 200, 200), new Color(200, 200, 200) },
            { new Color(200, 200, 200), new Color(200, 200, 200) } };

        String description = "convertToGreyScale with 2x2 Array of Color (200, 200, 200)";
        ImageViewer.convertToGreyScale(pixels);
        assertTrue(description, Arrays.deepEquals(expected, pixels));
    }

    @Test
    public void testConvertToGreyScale2() {
        // Create 2D Color array
        Color[][] pixels = { { new Color(100, 20, 60), new Color(50, 30, 70) },
            { new Color(60, 60, 60), new Color(50, 50, 50) } };

        Color[][] expected = { { new Color(60, 60, 60), new Color(50, 50, 50) },
            { new Color(60, 60, 60), new Color(50, 50, 50) } };

        String description = "convertToGreyScale with 2x2 Array of Color (60, 60, 60)" 
            + "x (50, 50, 50)";
        ImageViewer.convertToGreyScale(pixels);
        assertTrue(description, Arrays.deepEquals(expected, pixels));
    }        

    
    @Test
    public void testConvertToHighContrast() {

        // Create 2D Color array
        Color[][] pixels = { { new Color(127, 128, 200), new Color(127, 128, 200) },
            { new Color(127, 128, 200), new Color(127, 128, 200) } };

        Color[][] expected = { { new Color(0, 255, 255), new Color(0, 255, 255) },
            { new Color(0, 255, 255), new Color(0, 255, 255) } };

        String description = "convertToHighContrast with 2x2 Array of Color (127, 128, 200)";
        ImageViewer.convertToHighContrast(pixels);
        assertTrue(description, Arrays.deepEquals(expected, pixels));
    }

    @Test
    public void testConvertToHighContrast1() {

        // Create 2D Color array
        Color[][] pixels = { { new Color(120, 129, 255), new Color(120, 129, 255) },
            { new Color(0, 200, 128), new Color(0, 200, 128) } };

        Color[][] expected = { { new Color(0, 255, 255), new Color(0, 255, 255) },
            { new Color(0, 255, 255), new Color(0, 255, 255) } };

        String description = "convertToHighContrast with 2x2 Array of Color";
        ImageViewer.convertToHighContrast(pixels);
        assertTrue(description, Arrays.deepEquals(expected, pixels));
    }

    @Test
    public void testConvertToHighContrast2() {

        // Create 2D Color array
        Color[][] pixels = { { new Color(20, 234, 180), new Color(32, 6, 232) },
            { new Color(32, 234, 232), new Color(20, 234, 180) } };

        Color[][] expected = { { new Color(0, 255, 255), new Color(0, 0, 255) },
            { new Color(0, 255, 255), new Color(0, 255, 255) } };

        String description = "convertToHighContrast with 2x2 Array of Color";
        ImageViewer.convertToHighContrast(pixels);
        assertTrue(description, Arrays.deepEquals(expected, pixels));
    }
    

    @Test
    public void testConvertToNegative() {

        // Create 2D Color array
        Color[][] pixels = { { new Color(0, 100, 255), new Color(0, 100, 255) },
            { new Color(0, 100, 255), new Color(0, 100, 255) } };

        Color[][] expected = { { new Color(255, 155, 0), new Color(255, 155, 0) },
            { new Color(255, 155, 0), new Color(255, 155, 0) } };

        String description = "convertToNegative with 2x2 Array of Color (0, 100, 255)";
        ImageViewer.convertToNegative(pixels);
        assertTrue(description, Arrays.deepEquals(expected, pixels));
    }
    
    @Test
    public void testConvertToNegative1() {

        // Create 2D Color array
        Color[][] pixels = { { new Color(50, 50, 50), new Color(50, 50, 50) },
            { new Color(50, 50, 50), new Color(50, 50, 50) } };

        Color[][] expected = { { new Color(205, 205, 205), new Color(205, 205, 205) },
            {new Color(205, 205, 205), new Color(205, 205, 205) } };

        String description = "convertToNegative with 2x2 Array of Color";
        ImageViewer.convertToNegative(pixels);
        assertTrue(description, Arrays.deepEquals(expected, pixels));
    }
    
    @Test
    public void testConvertToNegative2() {

        // Create 2D Color array
        Color[][] pixels = { { new Color(200, 1, 119), new Color(20, 49, 100) },
            { new Color(111, 222, 21), new Color(30, 40, 50) } };

        Color[][] expected = { { new Color(55, 254, 136), new Color(235, 206, 155) },
            { new Color(144, 33, 234), new Color(225, 215, 205) } };

        String description = "convertToNegative with 2x2 Array of Color";
        ImageViewer.convertToNegative(pixels);
        assertTrue(description, Arrays.deepEquals(expected, pixels));
    }
    

    @Test
    public void testGetImageNumber() {
        // NOTE: You do not need to add any additional tests for this method

        // Create images array with room for 4 ImageInfo objects
        ImageInfo[] imageArray = new ImageInfo[4];

        // Create and add 4 ImageInfo objects to the images array
        imageArray[0] = new ImageInfo("images/rainbow.png", 150, 75, 
                "Somewhere over the rainbow...");
        imageArray[1] = new ImageInfo("images/smiley.jpeg", 212, 238, "Have a great day!");
        imageArray[2] = new ImageInfo("images/soup.jpg", 220, 331, "Is it time for lunch?");
        imageArray[3] = new ImageInfo("images/puppy.jpeg", 259, 194, "Too cute for words...");

        Scanner inputScanner = new Scanner("1");

        assertEquals(1, ImageViewer.getImageNumber(inputScanner, imageArray));
    }

    @Test
    public void testDisplayImage() {
        // NOTE: You do not need to add any additional tests for this method. This tests
        // that you have the displayImage method.

        ImageInfo imageDoesNotExist = new ImageInfo("imageDoesNotExist/rainbow.png", 150, 75,
                "Somewhere over the rainbow...");
        ImageViewer.displayImage(new Scanner("Somewhere over the rainbow..."), imageDoesNotExist);
        assertNotNull(imageDoesNotExist);
    }
}
