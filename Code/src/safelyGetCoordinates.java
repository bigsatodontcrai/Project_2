import java.util.InputMismatchException;

/**
 * <h1> safelyGetCoordinates </h1> 
 * Implements gameLogicInterface and helps keep track of coordinates as well as converting them from char to int
 */
public class safelyGetCoordinates implements gameLogicInterface{
    private char[] coordinateLetters = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i' };
    private boolean invalidInput = true;
    private String input = "";
    private String output = "";
    private boolean colValid = false;
    private boolean rowValid = false;
    private char col = 'z';
    private char row = 'z';
    private int column;
    private int rowboat;
    private BrokenRadar broken1;
    private BrokenRadar broken2;

    /**
     * Class constructor, initalized column and row.
     * @param none
     */
    safelyGetCoordinates(){
        this.column = 0;
        this.row = 0;
    }

    /**
     * Returns the value of rowboat
     * @param none
     * @return int
     */
    public int getRow() {
        return rowboat;
    }

    /**
     * Returns the value of column
     * @param none
     * @return int
     */
    public int getCol() {
        return column;
    }

    /**
     * Converts a char to an int
     * @param col - char
     * @return int
     */
    private int letterToInt(char col) {
        col = Character.toLowerCase(col);
        for (int i = 0; i < coordinateLetters.length; i++) {
            if (col == coordinateLetters[i]) {
                return (i);
            }
        }
        throw new IllegalArgumentException(col + " is out of bounds.");
    }

    /**
     * Converts the coordinates from a char,int format to int,int
     * @param none
     * @throws throws an error if the coordinate give was not in range or if in the wrong format of char,int
     * @return boolean, returns true if conversion was successful 
     */
    private boolean convertCoordinates() {
        try {
            column = letterToInt(col);
            colValid = true;
        } catch (IllegalArgumentException iae) {
            Utility.errorMessage(iae, "Please input coordinates within the range of A1 - I9");
            colValid = false;
        }
        try {
            rowboat = Integer.parseInt("" + row); // This will throw a NumberFormatException if row is not a
                                               // number
            if (rowboat < 1 || rowboat > 9) {
                System.out.println("Please input coordinates within the range of A1 - I9");
                rowValid = false;
            } else {
                rowValid = true;
            }
        } catch (NumberFormatException nfe) {
            System.out.println(row + " is not an int.");
            System.out.println("Please input coordinates in the format: A1.");
            rowValid = false;
        }
        return true;
    }

    /**
     * Implementation of gameLogicInterface function.
     * Gets coordinates from user and returns them in as a string after making sure they were entered correctly
     * @param none
     * @return string
     */
    public String getCoordinates() {
        do {
            
            input = Utility.consoleInput.next();
            if (input.length() < 2||input.length() > 2) {
                System.out.println("Please input coordinates in the format: A1");
            } else {
                col = input.charAt(0);
                row = input.charAt(1);
                if (convertCoordinates()) {
                    output = "" + col + row;
                    invalidInput = false; // This line won't be reached until there are no errors
                }
            }

        } while (invalidInput);
        return (output);
    }

    /**
     * Implementation of gameLogicInterface function.
     * Takes user input when the game menu is printed and calls what is needed from the input
     * @param playerBoard - Board
     * @param other - Board
     * @param UI - getUserInput
     * @param player1Printer - BoardPrinterWrapper
     * @param player2Printer - BoardPrinterWrapper
     * @return boolean
     * @throws if an int is not inputted
     */
    public boolean Loop(Board playerBoard, Board other, getUserInput UI, BoardPrinterWrapper player1Printer, 
        BoardPrinterWrapper player2Printer) {
        Utility.printMenu();
        
        int choice = 0;
        do {
            try {
                choice = UI.askOption(Utility.consoleInput);
            } catch (InputMismatchException ime) {
                System.out.println("Please input an int.");
            }
        } while (choice < 0 || choice > 3);

        switch (choice) {
            case 1:
                switch (playerBoard.getName()) {
                    case "player1Board":
                        markBoard(other, player2Printer, player1Printer);
                        break;
                    case "player2Board":
                        markBoard(other, player1Printer, player2Printer);
                } 
                Utility.EnterToContinue();
                return true;
            case 2:
                switch (playerBoard.getName()) {
                    case "player1Board":
                        broken1.runRadar();
                        break;
                    case "player2Board":
                        broken2.runRadar();
                        break;
                    default: 
                        broken1.runRadar();
                } 
                Utility.EnterToContinue();
                return true;
            case 3:
                return false;
        } return true;

    }

    /**
     * Marks opponent's board where player has choosen to attack
     * @param opponent - Board
     * @param opboard - BoardPrinterWrapper
     * @param playerboard - BoardPrinterWrapper
     * @return void
     */
    public void markBoard(Board opponent, BoardPrinterWrapper opboard, BoardPrinterWrapper playerboard) {
        //Utility.clearTerminal();
        opboard.print(false);
        System.out.println("");
        playerboard.print(false);
        System.out.println("Choose where to attack your opponent's board: ");
        getCoordinates();
        if (opponent.getMarker(getRow() - 1, getCol()) == 's') {
            //Utility.clearTerminal();
            opponent.addMarker('x', getRow() - 1, getCol());
            opboard.print(false);
            playerboard.print(false);
            System.out.println("It's a hit!");
            opponent.hitShipBool(input);
            switch(opponent.getName()){
                case "player1Board":
                    broken2.removeByCoordinate(input);
                    break;
                case "player2Board":
                    broken1.removeByCoordinate(input);
                    break;
                default:
                    broken1.removeByCoordinate(input);
            }
            
        } else {
            //Utility.clearTerminal();
            opponent.addMarker('o', getRow() - 1, getCol());
            opboard.print(true);
            playerboard.print(true);
            System.out.println("It's a miss!");
        }

    }

    /**
     * Takes the amount and loops that many times to allow  the player to place all their ships onto their board
     * @param playerBoard - Board
     * @param playerWrapper - BoardPrinterWrapper
     * @param placeIt - PlaceShip
     * @return void
     */
    public void placeShipLoop(Board playerBoard, BoardPrinterWrapper playerWrapper, PlaceShip placeIt) {
        for (int i = 0; i < playerBoard.getNumberOfShips(); i++) {
            playerWrapper.print(false);
            System.out.println("Choose where to place your ship.");
            getCoordinates();
            System.out.println("Horizontal or vertical? Enter H or V.");
            String next = Utility.consoleInput.next();
            boolean hori = Utility.getHori(next);
            placeIt.place(getRow() - 1, getCol(), i + 1, hori);  

        }
        
    }

    /**
     * Sets the broken radar for each players board
     * @param p1board - Board
     * @param p2board - Board
     * @return void
     */
    public void setRadar(Board p1board, Board p2board){
        broken1 = new BrokenRadar(p2board);
        broken2 = new BrokenRadar(p1board);
        broken1.fillMap();
        broken2.fillMap();
    }

    /**
     * Set the radar for the opponent. Overloading if playing with AI
     * @param opponent - Board
     * @return void
     */
    public void setRadar(Board opponent){
        broken1 = new BrokenRadar(opponent);
        broken1.fillMap();
    }//overload if AI
}