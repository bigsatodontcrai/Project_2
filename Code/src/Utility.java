import java.lang.IllegalArgumentException;
import java.util.Scanner;

/*
Note for others: the utility class contains
an interior class mainly because it was long and suited to be its own class
but it also uses some of the private methods here. just seemed like an appropriate
design decision. This way it'll be a bit more modular even internally.
*/
/**
 * <h1>Utility</h1>
 * <p>Class of helper functions</p>
 */
public class Utility {
    public static Scanner consoleInput = new Scanner(System.in);
   
    /**
    * Prints 50 newlines to the terminal to move any present text off screen.
    * @param none
    * @return void
    */
    public static void clearTerminal() {
        for (int i = 0; i < 50; i++) {
            System.out.println("");
        }
    }

    /**
     * Validates that the input is an int from 1 to 5, inclusive.
     * @param num - The int to validate
     * @return boolean - True if the value of num valid, otherwise returns false and prints a
     *         message to the console
     */
    public static boolean validateShipNum(int num) {
      if (num > 5 || num < 1) {
        System.out.println("Please input an int from 1 to 5.");
        return (false);
      }
      return (true);
    }

    /**
     * Displays the menu in the console.
     * @pre Calling clearTerminal() beforehand is recommended, but not required
     * @post The menu will be displayed
     * @param none
     * @return void
     */  
    public static void printMenu() {

      System.out.println("Menu:");
      System.out.println("1) Attack!!");
      //System.out.println("2) See your board");//do we need this if the board is printing more often
      //System.out.println("2) Attack history");
      System.out.println("2) Use the Radar... but it might be wrong!");
      System.out.println("3) Forfeit the match");
      System.out.println("CHOICE:");
    }

    /**
     * Reads in any key the user enters to continute the game
     * @param none
     * @return void
     */
    public static void EnterToContinue(){
      System.out.println("Type any key to continue.");
      String nothing = consoleInput.next();
    }

    /**
     * Prints out error message
     * @param iae - the exeception caught
     * @param message - the message to be printed
     * @return void
     */
    public static void errorMessage(IllegalArgumentException iae, String message) {
        System.out.println(iae.getMessage());
        System.out.println(message);
    }

    /**
     * does this and chooseShipNum actually get used??
     */
    private void getInput(safelyGetCoordinates input) {
        //input.getCoordinates();
        chooseShipNum();
        input.getCoordinates();
    }

    private void chooseShipNum() {
      //boolean invalidInput = true;
      System.out.println("Welcome to the game of Battleship!");
      System.out.println("How many ships (per person) would you like to play with (1-5)?");
      int numberOfShips = 0;
      do {
        numberOfShips = getUserInput.getUserNumber(consoleInput);
      }while (validateShipNum(numberOfShips) == false);
    }
    
    /**
     * never gets used???????????
     */
    public void runUtility(safelyGetCoordinates input){
      getInput(input);

    }

    /**
     * Gets if the ship is supposed to be horizontal or vertical.
     * Returns true if horizontal, false otherwise
     * @param next - string indicating either H or V
     * @return boolean - true if next is H, false if V
     */
    public static boolean getHori(String next){
      
      boolean hori = false;
      boolean notValid = true;
      while(notValid){
        if (next.contains("H")) {
          hori = true;
          notValid = false;
        } else if (next.contains("V")) {
          hori = false;
          notValid = false;
        } 
      }
      return hori;
    }
    /**
     * Prints out the game welcome messages, starting the game
     * @param none
     * @return void
     */
    public static void printStart(){
      System.out.println("Let's play Battleship!");
      System.out.println("Choose between two player OR choose AI Difficulty:");
    }

    
}
