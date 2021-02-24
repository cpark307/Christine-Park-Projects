import java.util.*;
import java.io.*;

/** TabConverter  will expand an input file by creating an output file with the same content,
*  except with all tab characters replaced with a given number of spaces. The program will also 
* unexpand an input file by creating an output file with the same content, but with 
* every sequence of a given number of spaces replaced with a tab character. 
* Unexpanding an expanded file should
* result in the original file, if the same number of spaces is used for both operations and if 
* the original file contains only single spaces and there are no spaces preceded by nor followed by 
* tab characters. Otherwise, the unexpanded version of an expanded file may not match the original 
* file. When converting sequences of spaces to tabs during the unexpansion operation, the sequences 
* of spaces should be converted to tabs from left to right.
* @author Christine Park
*/
public class TabConverter{
    
    /** Prompts user for inputs required to for TabConverter. Based on user's inputs,
    * executes "expand"/"unexpand" functionality.
    * @param args command line arguments (not used)
    */
    public static void main(String[] args){
        
        Scanner console = new Scanner(System.in);
    
        // Prints Header for Program 
        System.out.println("1) Type in input file or press 'q/Q' to exit program." +
            "\n2) Type in output file name. If output file exists, press 'y' to overwrite or 'n'" +
            " to start over \n3) Enter " +
            "'e/E' to expand all tabs with desired number of spaces or 'u/U' to collapse" +
            " desired number of spaces to a tab" +
            "4) Type in desired number of spaces for expand/unexpand function");
        System.out.println();
        
        boolean continuePrompt = false;
        Scanner fileScanner = null;
        PrintStream output = null;
        boolean expand = false;
        int nSpaces = 0;

        while (continuePrompt == false){
            do {
                System.out.print("Enter input filename or Q to quit: ");
                String inputFileName = console.next();
                fileScanner = getInputScanner(inputFileName);
            } while (fileScanner == null);
            continuePrompt = true;
            
            while (continuePrompt == true){
                System.out.print("Enter output filename: ");
                output = getOutputPrintStream(console);   
                if (output == null){
                    continuePrompt = false;
                } else {
                    continuePrompt = true;
                }
           
                while (continuePrompt == true){
                    System.out.print("Enter E-xpand or U-nexpand: ");
                    String eu = console.next();
                    if (eu.equalsIgnoreCase("e")){
                        expand = true;
                        continuePrompt = true;
                    } else if (eu.equalsIgnoreCase("u")){
                        expand = false;
                        continuePrompt = true;
                    } else {
                        continuePrompt = false;
                        System.out.println("Invalid option");
                        System.out.println();
                    }
              
                    while (continuePrompt == true){
                        System.out.print("Enter number of spaces: ");
                        if (!console.hasNextInt()){
                            System.out.println("Invalid value");
                            continuePrompt = true;
                        } else {
                            nSpaces = console.nextInt();
                            if (nSpaces < 2){
                                nSpaces = 2;
                            }
                            System.out.println();
                            processFile (expand, nSpaces, fileScanner, output);
                            continuePrompt = false;
                        }
                    }
                }       
            }
            
        }   
    }
            
    /** Returns Scanner for an input file. If the file does not exists, outputs
    * "File does not exist" and returns null
    * @param fileName String of input file name
    * @return input file Scanner
    */
    public static Scanner getInputScanner(String fileName){
        Scanner fileScanner = null;
        if (fileName.equalsIgnoreCase("q")){
            System.exit(1);
        }
        
        try {
            fileScanner = new Scanner(new File(fileName));
        } catch (FileNotFoundException e){
            System.out.println("File does not exist");
            System.out.println();
        }
        return fileScanner;
    }
    
    /**Prompts user to enter output filename and returns PrintStream for the file
    *If the file already exists, asks the user if it is OK to overwrite the file. 
    *If the user's answer begins with y or Y, creates and returns a PrintStream for the file
    *If a FileNotFoundException occurs, outputs "Cannot create output file" and returns null
    * Returns null if it is not OK to overwrite the file
    * @param console Scanner for console window
    * @return output File
    */
    public static PrintStream getOutputPrintStream(Scanner console){
        PrintStream output = null;
        String outputFileName = console.next();
        File file = new File(outputFileName);
        try {
            if (!file.exists()){
                output = new PrintStream(file);
            } else {
                System.out.print(outputFileName + " exists - OK to overwrite (y, n)?: ");
                String reply = console.next();
                if (reply.equalsIgnoreCase("y")){
                    output = new PrintStream(file);
                    return output;
                } else {
                    System.out.println();
                    return null;
                }
            }
        } catch (FileNotFoundException e){
            System.out.println("Cannot create output file");
            System.exit(1);
        }
        return output;
    }
    
    /**If expand is true, copies the input file to the output file replacing tabs by the
    * given number of spaces, as described above
    * If expand is false, copies the input file to the output file replacing the given
    * number of spaces with tabs, as described above
    * Throws an IllegalArgumentException with the message "Invalid number of spaces"
    * if numberOfSpaces is less than 2
    * @param expand true = expand; false = unexpand
    * @param numberOfSpaces number of Spaces for expand/unexpand
    * @param input Scanner for input file
    * @param output output File
    * @throw
    */
    public static void processFile (boolean expand, int numberOfSpaces, Scanner input, 
                                PrintStream output){
        if (numberOfSpaces < 2){
            throw new IllegalArgumentException("Invalid number of spaces");
        } else {
            if (expand == true){
                while (input.hasNextLine()){
                    String origLine = input.nextLine();
                
                    String expandedLine = expandLine(origLine, numberOfSpaces);
                    output.println(expandedLine);
                }
            } else if (expand == false){
                while (input.hasNextLine()){
                    String origLine = input.nextLine();
                    String unexpandedLine = unexpandLine(origLine, numberOfSpaces);
                    output.println(unexpandedLine);
                }
            }                    
        }
    }
    
    /**Returns string containing expanded line
    * Throws an IllegalArgumentException with the message "Invalid number of spaces"
    * if numberOfSpaces is less than 2
    * @param line line from input
    * @param numberOfSpaces number of spaces
    * @return expanded line
    * @throws IllegalArgumentException when numberOfSpaces < 2
    */
    public static String expandLine(String line, int numberOfSpaces) 
        throws IllegalArgumentException{
        if (numberOfSpaces < 2){
            throw new IllegalArgumentException("Invalid number of spaces");
        } 
        
        String lineExpanded = "";
        int length = line.length();
        for (int i = 0; i < length; i++){
            if (line.charAt(i) != '\t'){
                lineExpanded += line.charAt(i);
            } else {
                for (int j = 1; j <= numberOfSpaces; j++){
                    lineExpanded += " ";
                }
            }       
        }
        return lineExpanded;
    }
    
    
    /**Returns string containing unexpanded line
    * Throws an IllegalArgumentException with the message "Invalid number of spaces"
    * if numberOfSpaces is less than 2
    * @param line line from input
    * @param numberOfSpaces number of spaces
    * @return expanded line
    * @throws IllegalArgumentException when numberOfSpaces < 2
    */
    public static String unexpandLine(String line, int numberOfSpaces){
        if (numberOfSpaces < 2){
            throw new IllegalArgumentException("Invalid number of spaces");
        } 

        String lineUnexpanded = "";
        String space = "";
        int spaceCount = 0;
        int length = line.length();
        int spaceInd = 0;

        for (int i = 0; i < length; i++){
            if (line.charAt(i) != ' '){
                lineUnexpanded += line.charAt(i);
                spaceCount = 0;
            } else if (line.charAt(i) == ' '){
                spaceInd = i;
                boolean endLine = false;
                while (line.charAt(spaceInd) == ' ' && !endLine){
                    spaceCount++;
                    if (spaceInd < length - 1){
                        spaceInd++;
                    } else if (spaceInd == length - 1) {
                        endLine = true;
                    }
                }

                int nTab = spaceCount / numberOfSpaces;
                int nSpaceAfterTab = spaceCount % numberOfSpaces;

                for (int k = 0; k < nTab; k++){
                    lineUnexpanded += "\t";
                }
                for (int k = 0; k < nSpaceAfterTab; k++){
                    lineUnexpanded += " ";
                }           
                i += spaceCount;
                i--;           
            }
        }
        return lineUnexpanded;
    }
    
}

