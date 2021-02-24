import java.util.*;

/**
* Automated program that determines whether a student will be accepted into
* the Engineering, Jouralism, or Fine Arts College based on a multitude of 
* determined factors (e.g. Math/Reading/Essay SAT score, weighted/unweighted GPA,
* number of alumni relatives)
* @author Christine Park 
*/
public class Admissions{
    
    /** minimum SAT score*/
    public static final int MIN_SAT = 200;
    /** maximum SAT score*/
    public static final int MAX_SAT = 800;
    /** minimum essay score*/
    public static final int MIN_ESSAY = 2;
    /** maximum essay score*/
    public static final int MAX_ESSAY = 8;
    /** minimum eng GPA*/
    public static final double ENG_DENY_GPA = 3.8;
    /** minimum eng MATH SAT*/
    public static final int ENG_DENY_MATH = 500;
    /** minimum eng READING SAT*/
    public static final int ENG_DENY_READING = 400;
    /** eng admission cutoff for math */
    public static final int ENG_ADMIT_MATH = 700;
    /** eng admission cutoff for reading */
    public static final int ENG_ADMIT_READING = 600;
    /** minimum journ GPA*/
    public static final double JOURN_DENY_GPA = 3.0;
    /** journ admission cutoff for reading*/
    public static final int JOURN_DENY_READING = 400;
    /** journ admission cutoff for essay*/
    public static final int JOURN_DENY_ESSAY = 6;
    /** minimum fine arts GPA*/
    public static final double FINE_DENY_GPA = 2.8;
    /** num of alumni cutoff to be upgraded to admitted*/
    public static final int ALUM = 2;
    
    /**
    * Provides instructions for the user and gathers the inputs from the user
    * to determine admission status.
    * @param args array of command line arguments (not used)
    */
    public static void main(String[] args){
    
        System.out.println("           Welcome to the College Admissions Program!");
        System.out.print("When prompted, please enter the applicant's name, and the school\n" +
            "to which he/she is applying  -  E (Engineering), J (Journalism),\n" +
            "or F (Fine Arts). Depending on the school, you will be prompted\n" +
            "to enter SAT scores (Math, Reading/Writing, Essay),  high school\n" + 
            "GPA (weighted/unweighted), or a Portfolio rating - E (Excellent),\n" +
            "G (Good), F (Fair), P (Poor). You will also be prompted to enter\n" + 
            "the number of alumni family members. The applicant's admission\n" + 
            "status of Admit, Defer, or Deny will then be displayed.");
        System.out.println();
    
        Scanner console = new Scanner(System.in);
        System.out.print("Applicant Name: ");
        String applicantName = console.nextLine();
        System.out.print("E (Engineering), J (Journalism), or F (Fine Arts): ");
        String school = console.next();
        int mathSAT = 0;
        int readingSAT = 0;
        int essaySAT = 0;
        double hsGPA = 0;
        int numAlum = 0;
           
        if (school.equalsIgnoreCase("e")){
            
            System.out.print("Math SAT score (200-800): ");
            mathSAT = console.nextInt();
            if (mathSAT >= MIN_SAT && mathSAT <= MAX_SAT){
                System.out.print("Reading/Writing SAT score (200-800): ");
                readingSAT = console.nextInt();
                if (readingSAT >= MIN_SAT && readingSAT <= MAX_SAT){
                    System.out.print("Weighted high school GPA: ");
                    hsGPA = console.nextDouble();
                    if (hsGPA >= 0){
                        System.out.print("Number of alumni family members: ");
                        numAlum = console.nextInt();
                        if (numAlum >= 0){
                            String engAdmissionStatus = 
                                getEngineeringAdmissionStatus(mathSAT, readingSAT, hsGPA, numAlum);
                            System.out.println();
                            System.out.print("Admission Status: " + engAdmissionStatus);
                            System.out.println();
                        } else {
                            System.out.print("Invalid number of alumni");
                            System.exit(1);
                        }
                    } else {
                        System.out.print("Invalid GPA");
                        System.exit(1);
                    }
                } else {
                    System.out.print("Invalid SAT score");
                    System.exit(1);
                }
            } else {
                System.out.print("Invalid SAT score");
                System.exit(1);
            }
        } else if (school.equalsIgnoreCase("j")){
            System.out.print("Reading/Writing SAT score (200-800): ");
            readingSAT = console.nextInt();
            if (readingSAT >= MIN_SAT && readingSAT <= MAX_SAT){
                System.out.print("SAT Essay Writing score (2-8): ");
                essaySAT = console.nextInt();
                if (essaySAT >= MIN_ESSAY && essaySAT <= MAX_ESSAY){
                    System.out.print("Unweighted high school GPA: ");
                    hsGPA = console.nextDouble();
                    if (hsGPA >= 0){
                        System.out.print("Number of alumni family members: ");
                        numAlum = console.nextInt();
                        if (numAlum >= 0){
                            String journAdmissionStatus =
                                getJournalismAdmissionStatus(readingSAT, essaySAT, hsGPA, numAlum);
                            System.out.println();
                            System.out.print("Admission Status: " + journAdmissionStatus);
                            System.out.println();
                        } else {
                            System.out.print("Invalid number of alumni");
                            System.exit(1);
                        }
                    } else {
                        System.out.print("Invalid GPA");
                        System.exit(1);
                    }
                } else {
                    System.out.print("Invalid SAT essay score");
                    System.exit(1);
                }
            } else {
                System.out.print("Invalid SAT score");
                System.exit(1);
            }
        } else if (school.equalsIgnoreCase("f")){
            System.out.print("Portfolio rating (E (Excellent), G (Good), F (Fair)," + 
                " or P (Poor)): ");
            String portfolioString = console.next();
            if (portfolioString.equalsIgnoreCase("e") || portfolioString.equalsIgnoreCase("g") ||
                portfolioString.equalsIgnoreCase("f") || portfolioString.equalsIgnoreCase("p")){
                System.out.print("Unweighted high school GPA: ");
                char portfolio = portfolioString.charAt(0);
                hsGPA = console.nextDouble();
                if (hsGPA >= 0){
                    System.out.print("Number of alumni family members: ");
                    numAlum = console.nextInt();
                    if (numAlum >= 0){
                        String fineArtsAdmissionStatus = 
                            getFineArtsAdmissionStatus(portfolio, hsGPA, numAlum);
                        System.out.println();
                        System.out.print("Admission Status: " + fineArtsAdmissionStatus);
                        System.out.println();
                    } else {
                        System.out.print("Invalid number of alumni");
                        System.exit(1);
                    }
                } else {
                    System.out.print("Invalid GPA");
                    System.exit(1);
                }
            }        
        } else {
            System.out.print("Invalid school");
            System.exit(1);
        }
    }
    
    /** Determines admission status into engineering school
    * @param mathSAT math SAT score
    * @param readingWritingSAT reading SAT score
    * @param gpa weighted high school gpa
    * @param numberOfAlumni # of alumni in family
    * @return status of admission
    * @throws IllegalArgumentException if SAT score, GPA, or number of alumni is invalid
    */
    public static String getEngineeringAdmissionStatus(int mathSAT, int readingWritingSAT, 
        double gpa, int numberOfAlumni){
        String result = "";
        
        if (mathSAT < MIN_SAT || readingWritingSAT < MIN_SAT || mathSAT > MAX_SAT ||
            readingWritingSAT > MAX_SAT){
            throw new IllegalArgumentException("Invalid SAT score");
        }
        if (gpa < 0){
            throw new IllegalArgumentException("Invalid GPA");
        }       
        if (numberOfAlumni < 0){
            throw new IllegalArgumentException("Invalid number of alumni");
        }  
        
        if (gpa < ENG_DENY_GPA || mathSAT < ENG_DENY_MATH || readingWritingSAT < ENG_DENY_READING){
            result = "Deny";
            return result;
        } else if (mathSAT >= ENG_ADMIT_MATH && readingWritingSAT >= ENG_ADMIT_READING){
            result = "Admit";
            return result;
        } else if ((mathSAT >= ENG_DENY_MATH && mathSAT < ENG_ADMIT_MATH) &&
            (readingWritingSAT >= ENG_DENY_READING && readingWritingSAT < ENG_ADMIT_READING)){
            if (numberOfAlumni < ALUM){
                result = "Defer";
                return result;
            } else {
                result = "Admit";
                return result;
            }
        }
        return result;
    } 
    
    /** Determines admission status into journalism school
    * @param readingWritingSAT essay SAT score
    * @param essayWritingSAT math SAT score
    * @param gpa unweighted high school gpa
    * @param numberOfAlumni # of alumni in family
    * @return status of admission
    * @throws IllegalArgumentException if SAT score, GPA, or number of alumni is invalid
    */
    public static String getJournalismAdmissionStatus(int readingWritingSAT, int essayWritingSAT, 
        double gpa, int numberOfAlumni){
        String result = "";
        if (readingWritingSAT < MIN_SAT || readingWritingSAT > MAX_SAT){
            throw new IllegalArgumentException("Invalid SAT score");
        }
        if (essayWritingSAT < MIN_ESSAY || essayWritingSAT > MAX_ESSAY){
            throw new IllegalArgumentException("Invalid SAT essay score");
        }
        if (gpa < 0){
            throw new IllegalArgumentException("Invalid GPA");
        }       
        if (numberOfAlumni < 0){
            throw new IllegalArgumentException("Invalid number of alumni");
        }
      
        if (gpa < JOURN_DENY_GPA || readingWritingSAT < JOURN_DENY_READING ||
            essayWritingSAT < JOURN_DENY_ESSAY){
            result = "Deny";
            return result;
        } else if (readingWritingSAT >= JOURN_DENY_READING && essayWritingSAT > JOURN_DENY_ESSAY ){
            result = "Admit";
            return result;
        } else {
            if (numberOfAlumni < ALUM){
                result = "Defer";
                return result;
            } else {
                result = "Admit";
                return result;
            }
             
        }
        
    } 
    
    /** Determines admission status into Fine Arts school
    * @param portfolioRating rating of portfolio
    * @param gpa unweighted high school gpa
    * @param numberOfAlumni # of alumni in family
    * @return status of admission
    * @throws IllegalArgumentException if portfolio rating, GPA, or number of alumni is invalid
    */
    public static String getFineArtsAdmissionStatus(char portfolioRating, 
        double gpa, int numberOfAlumni){
        String result = "";

        
        if (portfolioRating != 'e' && portfolioRating != 'E' && portfolioRating != 'g' 
            && portfolioRating != 'G' && portfolioRating != 'f' && portfolioRating != 'F' 
            && portfolioRating != 'p' && portfolioRating != 'P'){
            throw new IllegalArgumentException("Invalid portfolio rating");
        }
        if (gpa < 0){
            throw new IllegalArgumentException("Invalid GPA");
        }       
        if (numberOfAlumni < 0){
            throw new IllegalArgumentException("Invalid number of alumni");
        }        
    
        if (gpa < FINE_DENY_GPA || (Character.toLowerCase(portfolioRating) == 'p' 
            || Character.toLowerCase(portfolioRating) == 'f')){
            result = "Deny";
            return result;
        } else if (portfolioRating == 'e' || portfolioRating == 'E'){
            result = "Admit";
            return result;
        } else if (portfolioRating == 'g' || portfolioRating == 'G'){
            if (numberOfAlumni < ALUM){
                result = "Defer";
                return result;
            } else {
                result = "Admit";
                return result;
            }
      
        } 
        return result;
    }
}
