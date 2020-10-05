import java.util.InputMismatchException;

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

    safelyGetCoordinates(){
        this.column = 0;
        this.row = 0;
    }

    public int getRow() {
        return rowboat;
    }

    public int getCol() {
        return column;
    }

    private int letterToInt(char col) {
        col = Character.toLowerCase(col);
        for (int i = 0; i < coordinateLetters.length; i++) {
            if (col == coordinateLetters[i]) {
                return (i);
            }
        }
        throw new IllegalArgumentException(col + " is out of bounds.");
    }

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

    public void markBoard(Board opponent, BoardPrinterWrapper opboard, BoardPrinterWrapper playerboard) {
        //Utility.clearTerminal();
        do{
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
            
            } else if (opponent.getMarker(getRow() - 1, getCol()) == 'o'){
                System.out.println("There is already a miss here. Try again.");
            } else {
            //Utility.clearTerminal();
                opponent.addMarker('o', getRow() - 1, getCol());
                opboard.print(true);
                playerboard.print(true);
                System.out.println("It's a miss!");
            }
        } while(opponent.getMarker(getRow() - 1, getCol()) == 'o');
       

    }

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

    public void setRadar(Board p1board, Board p2board){
        broken1 = new BrokenRadar(p2board);
        broken2 = new BrokenRadar(p1board);
        broken1.fillMap();
        broken2.fillMap();
    }

    public void setRadar(Board opponent){
        broken1 = new BrokenRadar(opponent);
        broken1.fillMap();
    }//overload if AI
}