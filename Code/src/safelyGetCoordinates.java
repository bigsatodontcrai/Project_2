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
        return true;
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
                        markBoard(playerBoard, player2Printer, player1Printer);
                        break;
                    case "player2Board":
                        markBoard(playerBoard, player1Printer, player2Printer);
                } return true;
            case 2:
                return false;
        } return true;

    }

    public void markBoard(Board opponent, BoardPrinterWrapper opboard, BoardPrinterWrapper playerboard) {
        Utility.clearTerminal();
        opboard.print(true);
        System.out.println("");
        playerboard.print(false);
        System.out.println("Choose where to attack your opponent's board: ");
        getCoordinates();
        if (opponent.getMarker(getRow() - 1, getCol()) == 's') {
            Utility.clearTerminal();
            opponent.addMarker('x', getRow() - 1, getCol());
            opboard.print(true);
            playerboard.print(true);
            System.out.println("It's a hit!");
        } else {
            Utility.clearTerminal();
            opponent.addMarker('o', getRow() - 1, getCol());
            opboard.print(true);
            playerboard.print(true);
            System.out.println("It's a miss!");
        }

    }

    public void placeShipLoop(Board playerBoard, BoardPrinterWrapper playerWrapper, PlaceShip placeIt) {
        for (int i = 0; i < playerBoard.getNumberOfShips(); i++) {
            playerWrapper.print(false);
            System.out.println("Choose where to place your ship.");
            getCoordinates();
            System.out.println("Horizontal or vertical? Enter H or V.");
            String next = Utility.consoleInput.next();
            boolean hori = Utility.getHori(next);
            playerBoard.setShipCoordinates(i, getRow() - 1, getCol());
            placeIt.place(getRow() - 1, getCol(), i + 1, hori);

        }
    }
}