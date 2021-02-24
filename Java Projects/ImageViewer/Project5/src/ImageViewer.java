
import java.awt.*;
import java.util.*;
import java.io.*;

/** Allows user to choose an image from a list, which is displayed normally. The user 
* is then given the option to see the same image displayed in grey scale, high 
* contrast, or as a negative.
* @author Christine Park
*/
public class ImageViewer{
    
    /** +10 pixel border width for images */
    public static final int BORDER = 10;
    /** count of R, G, and B*/
    public static final int AVERAGE3 = 3;
    /** maximum RGB value*/
    public static final int MAX_RGB = 255;
    /** high contrast boundary value*/
    public static final int HC = 128;
    
    /** 
    * Initiates the Image Viewer program.
    * @param args input file name
    */
    public static void main(String[] args){
        if (args.length != 1){
            System.out.println("Usage: java -cp bin ImageViewer filename");
            System.exit(1);
        }
        
        try {
            ImageInfo[] imageInfo = getImageList(args[0]);
            
            Scanner console = new Scanner(System.in);
            int option = -1;
            do 
            {  
 
                option = getImageNumber(console, imageInfo);
                if (option > 0 && option <= imageInfo.length){
                    displayImage(console, imageInfo[option - 1]);
                }
                option = -1;
            } while (option == -1) ;
        } catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
            System.exit(1);
        }
    } 
        
    /** 
    * Creates a Scanner for the given file and read through the file 
    * once counting the number of lines (images).
    * Creates an array of ImageInfo objects of the correct size
    * Creates another Scanner for the file and read through the file again
    * reading the information about each image and storing it in the ImageInfo array
    * Returns the ImageInfo array
    * If a FileNotFoundException occurs, throws an IllegalArgumentException
    * with the message, "Unable to access input file: filename"
    * @param filename name of input file
    * @return array of ImageInfo objects
    */
    public static ImageInfo[] getImageList(String filename){
        int lineCount = 0;
        try {
            Scanner input = new Scanner(new File(filename));
            String line;
            while (input.hasNextLine()){
                line = input.nextLine();
                lineCount++;
            }
            input.close();
        } catch (FileNotFoundException e){
            throw new IllegalArgumentException("Unable to access input file: " + filename);
        }
 


        ImageInfo[] images = new ImageInfo[lineCount];
        
     
        try {
            Scanner input = new Scanner(new File(filename));
            String line;

            for (int i = 0; i < lineCount; i++){
                line = input.nextLine();
                Scanner lineScanner = new Scanner(line);
                String imageName = lineScanner.next();
                int width = lineScanner.nextInt();
                int height = lineScanner.nextInt();
                String title = lineScanner.nextLine().trim();
                images[i] = new ImageInfo(imageName, width, height, title);
            }
            input.close();
        } catch (FileNotFoundException e){
            throw new IllegalArgumentException("Unable to access input file: " + filename);
        }         
        return images;  
    }
    
    /** Displays the image menu and prompts the user for the Image.
    * If the user enters a valid image number (number of images + 1),
    * returns the number.
    * If the user enters an invalid image number or something other than
    * an integer, outputs an error message as shown above and return -1
    * @param console Scanner for console window
    * @param images array of ImageInfo objects
    * @return user input option number
    */
    public static int getImageNumber(Scanner console, ImageInfo[] images){
        
        
        
        System.out.println();
        
        System.out.println("Image Viewer - Please choose an image to view (or " +
            (images.length + 1) + " to quit)"); 
            
        System.out.println();
                            
        for (int i = 0; i < images.length; i++){
            System.out.println( i + 1 + " -" + images[i].getTitle());
        }
        System.out.println(images.length + 1 + " - Quit the program");
    
        System.out.print("\nImage: ");
        
        int option = 0;
        boolean isDisplayValid = false;
        if (!console.hasNextInt()){
            console.nextInt();
            System.out.println("Invalid image number");
            return -1;
        } else {
            option = console.nextInt();
            if (option < 1 || option > (images.length + 1)){
                System.out.println("Invalid image number");
                return -1;
            } else if (option == images.length + 1){
                System.out.println("Goodbye!");
                System.exit(1);
            } else if (!new File(images[option - 1].getFilename()).exists()  ){
                System.out.println(images[option - 1].getTitle() +
                    " missing image file: " + images[option - 1].getFilename());
                return -1;
            }

            return option;
        }
    }

    /** Displays the image on a DrawingPanel.
    * Displays the option menu and prompts the user for the option.
    * Depending on the option, uses one of the conversion methods to convert 
    * the pixels associated with the image to a different format
    * Redisplays the converted image on the same DrawingPanel
    * If the user enters an invalid option, outputs an error message and returns 
    * If the image file does not exist, outputs an error message.
    * @param console Scanner for console window
    * @param image instance of ImageInfo class
    */
    public static void displayImage(Scanner console, ImageInfo image){

        if (!new File(image.getFilename()).exists()  ){
            System.out.println(image.getTitle() +
                " missing image file: " + image.getFilename());
            return;
        }
       
        DrawingPanel panel = new DrawingPanel(image.getWidth() + BORDER * 2, 
            image.getHeight() + BORDER * 2);
        
        Graphics g = panel.getGraphics();
        panel.setBackground(Color.WHITE);
        String imageFilename = image.getFilename();
 
        Image img = panel.loadImage(imageFilename);

        
        g.drawImage(img, BORDER, BORDER, panel);
   
        Color[][] pixels = panel.getPixels();
        for (int i = 0; i < pixels.length; i++) {
            for (int j = 0; j < pixels[0].length; j++) {
                int red = pixels[i][j].getRed();
                int green = pixels[i][j].getGreen();
                int blue = pixels[i][j].getBlue();
                pixels[i][j] = new Color(red, green, blue);
            }
        }
 
        panel.setPixels(pixels);
        System.out.println("Please enter an option below.\n");
        System.out.println("G-rey scale");
        System.out.println("H-igh contrast");
        System.out.println("N-egative");
        System.out.println();
        System.out.print("Option: ");
        
        String option = console.next();

        if (option.equalsIgnoreCase("g")) {
            convertToGreyScale(pixels);
            panel.setPixels(pixels);
        } else if (option.equalsIgnoreCase("h")) {
            convertToHighContrast(pixels);
            panel.setPixels(pixels);
        } else if (option.equalsIgnoreCase("n")) {
            convertToNegative(pixels);
            panel.setPixels(pixels);
        } else {
            System.out.println("Invalid option.");
            return;
        }
    }
         
    /** The Color pixels in the array are converted to high contrastS
    * @param pixels 2D array of pixels
    */
    public static void convertToHighContrast(Color[][] pixels){
        int r, g, b;
        for(int i = 0; i < pixels.length; i++) {
            for(int j = 0; j < pixels[0].length; j++) {
                r = pixels[i][j].getRed();
                g = pixels[i][j].getGreen();
                b = pixels[i][j].getBlue();
                if(r < HC) {
                    r = 0;
                } else {
                    r = MAX_RGB;
                }
                if (g < HC) {
                    g = 0;
                } else {
                    g = MAX_RGB;
                }
                if (b < HC) {
                    b = 0;
                } else {
                    b = MAX_RGB;
                }
        
                pixels[i][j] = new Color(r, g, b);
            }
        }
    }

   /** The Color pixels in the array are negated
    * @param pixels 2D array of pixels   
    */
    public static void convertToNegative(Color[][] pixels) {
        int r, g, b;
        for(int i = 0; i < pixels.length; i++) {
            for(int j = 0; j < pixels[0].length; j++) {
                r = pixels[i][j].getRed();
                g = pixels[i][j].getGreen();
                b = pixels[i][j].getBlue();
                r = MAX_RGB - r;
                g = MAX_RGB - g;
                b = MAX_RGB - b;
                pixels[i][j] = new Color(r, g, b);
            }
        }
    }

   /** The Color pixels in the array are converted to grey scale
    * @param pixels 2D array of pixels
    */
    public static void convertToGreyScale(Color[][] pixels) {
        int r, g, b;
        for(int i = 0; i < pixels.length; i++) {
            for(int j = 0; j < pixels[0].length; j++) {
                r = pixels[i][j].getRed();
                g = pixels[i][j].getGreen();
                b = pixels[i][j].getBlue();
                int a = (r + g + b) / AVERAGE3;
                pixels[i][j] = new Color(a, a, a);
            }
        }
    }
}
