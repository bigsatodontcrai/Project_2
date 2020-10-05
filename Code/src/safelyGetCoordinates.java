import java.util.InputMismatchException;

/**
 * Note for others: The safelyGetCoordinates class will get coordinates on the 
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
    private String[] current = new String[3];

    /**
     * safelyGetCoordinates constructor
     * @param none
     * @return safelyGetCoordinates object
     */
    safelyGetCoordinates(){
        this.column = 0;
        this.row = 0;
        for(int i = 0; i < 3; i++){
            current[i] = "N/A";
        }
    }

    /**
     * @param none
     * @return int - row position
     */
    public int getRow() {
        return rowboat;
    }

     /**
     * @param none
     * @return int - column position
     */
    public int getCol() {
        return column;
    }

    /**
     * converts letter to an int
     * @param col - char that holds the letter to the Battleship game coordinates
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
     * converts letter coordinate that is use provided to integer coordinates for background use
     * @param none
     * @return boolean - if the provided coordinate is invalid, returns false. Else, returns true
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
        if (colValid && rowValid){
            return true;
        } else {
            return false;
        }
    }

    /**
     * obtains coordinates from user
     * @param none
     * @return string - letter from A1 format coordinates
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
     * Calls markBoard to loop through the steps of the game if either player hasn't lost all
     * their ships
     * @param playerBoard - board of player, other - board of opponent, UI - getUserInput, 
     *  playerPrinter - BoardPrinterWrapper object of players board,
     *  player2Printer - Board PrinterWrapper object of AI board
     * @return boolean - returns true if either player still has ships, returns false when someone loses.
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
                if(playerBoard.fleetHasSunk()){
                    return false;
                }
                return true;
            case 2:
                switch (playerBoard.getName()) {
                    case "player1Board":
                        current = broken1.runRadar();
                        break;
                    case "player2Board":
                        current = broken2.runRadar();
                        break;
                    default: 
                        current = broken1.runRadar();
                } 
                Utility.EnterToContinue();
                return true;
            case 3:
                return false;
        } return true;

    }

    /**
     * marks both player and AI board when there someone's ship is hit
     * @param opponent - Board object of AI board, opboard - BoardPrinterWrapper object of AI board,
     *  playerBoard - BoardPrinterWrapper object of player's board
     */
    public void markBoard(Board opponent, BoardPrinterWrapper opboard, BoardPrinterWrapper playerboard) {
        Utility.clearTerminal();
        boolean valid = true;
        do{
            opboard.print(true);
            System.out.println("");
            playerboard.print(false);
            System.out.println("Radar coordinates from last radar usage: " + current[0] + " " + current[1] + " " + current[2]);
            System.out.println("Choose where to attack your opponent's board: ");
            getCoordinates();
            if (opponent.getMarker(getRow() - 1, getCol()) == 's') {
                valid = true;
                Utility.clearTerminal();
                opponent.addMarker('x', getRow() - 1, getCol());
                opboard.print(true);
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
            
            } else if (opponent.getMarker(getRow() - 1, getCol()) == 'o'){
                System.out.println("There is already a miss here. Try again.");
                valid = false;
            } else {
                valid = true;
                Utility.clearTerminal();
                opponent.addMarker('o', getRow() - 1, getCol());
                opboard.print(true);
                playerboard.print(true);
                System.out.println("It's a miss!");
            }
        } while(!valid);
       

    }

     /**
     * allows player to choose where to place ships on board
     * @param playerBoard - Board object, playerWrapper - BoardPrinterWrapper object, PlaceIt - PlaceShip object
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
            if(!placeIt.place(getRow() - 1, getCol(), i + 1, hori)){
                i--;
            }  

        }
        
    }

    /**
     * radar feature with humans
     * @param p1board - board object for player 1, p2board - board object for player 2
     * @return void
     */
    public void setRadar(Board p1board, Board p2board){
        broken1 = new BrokenRadar(p2board);
        broken2 = new BrokenRadar(p1board);
        broken1.fillMap();
        broken2.fillMap();
    }

    /**
     * radar feature with AI
     * @param opponent - board object 
     * @return void
     */
    public void setRadar(Board opponent){
        broken1 = new BrokenRadar(opponent);
        broken1.fillMap();
    }//overload if AI
}