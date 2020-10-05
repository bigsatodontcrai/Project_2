import java.util.InputMismatchException;
import java.util.Scanner;
/**
 * <h1>getUserInput</h1>
 * This class handels reading in user input
 */
public class getUserInput {
    private int num = 0;
    private int player = 0;
    private int choice = 0;

    /**
     * Class constructor, sets player variable equal to the int passed in
     * @param number - int
     */
    public getUserInput(int number){
        this.player = number;
    }
    /**
     * Asks users for the number of ships they would like to have. Handels incorrect inputs as well.
     * @param playerboard - Board
     * @param input - Scanner
     * @return int
     */
    private int askForShipNum(Board playerboard, Scanner input) {
        do {
            System.out.println("Select the number of ships you will use.");
            try{
                num = getNumber(input);
            }
            catch (InputMismatchException ime) {
            System.out.println("Please input an int.");
        }
        } while (!Utility.validateShipNum(num));
        return num;

    }

    /**
     * Calls getNumber and returns the int getNumber returns.
     * @param input - Scanner
     * @return int
     */
    public int askOption(Scanner input){ 
        return getNumber(input);
    }
    
    /**
     * Gets the number the user input.
     * @param input
     * @return int
     */
    private int getNumber(Scanner input){
        return input.nextInt();
    }
    
    /**
     * Gets the number the user input.
     * @param input
     * @return int
     */
    public static int getUserNumber(Scanner input){
        return input.nextInt();
    }

    /**
     * Calls askForShipNum and returns whatever askForShipNum returns
     * @param ba - Board
     * @param in - Scanner
     * @return int
     */
    public int runInterface(Board ba, Scanner in){
        return askForShipNum(ba, in);
    }
}