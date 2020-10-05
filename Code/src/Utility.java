import java.lang.IllegalArgumentException;
import java.util.Scanner;

/*
Note for others: the utility class contains
an interior class mainly because it was long and suited to be its own class
but it also uses some of the private methods here. just seemed like an appropriate
design decision. This way it'll be a bit more modular even internally.
*/

public class Utility {
    public static Scanner consoleInput = new Scanner(System.in);
    public static void clearTerminal() {
        for (int i = 0; i < 50; i++) {
            System.out.println("");
        }
    }

    /**
     *
     * Validates that the input is an int from 1 to 5, inclusive.
     *
     *
     * @param num The int to validate
     * @return True if the value of num valid, otherwise returns false and prints a
     *         message to the console
     *
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
     *
     * @pre: Calling clearTerminal() beforehand is recommended, but not required
     * @post: The menu will be displayed
     *
     */  
    public static void printMenu() {

      System.out.println("Menu:");
      System.out.println("1) Attack!!");
      System.out.println("2) Use the Radar... but it might be wrong!");
      System.out.println("3) Forfeit the match");
      System.out.println("CHOICE:");
    }

    public static void EnterToContinue(){
      System.out.println("Type any key to continue.");
      String nothing = consoleInput.next();
    }


    public static void errorMessage(IllegalArgumentException iae, String message) {
        System.out.println(iae.getMessage());
        System.out.println(message);
    }

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

    public static void printStart(){
      System.out.println("Let's play Battleship!");
      System.out.println("Choose between two player OR choose AI Difficulty:");
    }

    
}
