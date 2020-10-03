import java.util.InputMismatchException;
import java.util.Scanner;

public class getUserInput {
    private int num = 0;
    private int player = 0;
    private int choice = 0;

    public getUserInput(int number){
        this.player = number;
    }

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

    public int askOption(Scanner input){ 
        return getNumber(input);
    }

    private int getNumber(Scanner input){
        return input.nextInt();
    }

    public static int getUserNumber(Scanner input){
        return input.nextInt();
    }

    public int runInterface(Board ba, Scanner in){
        return askForShipNum(ba, in);
    }
}