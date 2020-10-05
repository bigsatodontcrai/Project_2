/**
 * Note for others: The AIHard class implements the easy mode for playing against AI by firing
 * at exactly the coordinates the player chooses to place their ships
 */

public class AIHard implements gameLogicInterface {
    private int row = 0;
    private int col = 0;
    private Board BoardOrig;

    AIHard(Board orig) {
        this.BoardOrig = orig;
    }

    private void boom(int row, int col) {
        BoardOrig.addMarker('x', row, col);
    }

    /**
     * @param none
     * @return string 
     */
    public String getCoordinates() {
        return "nothing";
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

        if (!playerBoard.fleetHasSunk()) {
            markBoard(other, player2Printer, player1Printer);
            return true;
        } else {
            return false;
        }
    }
    
    public void markBoard(Board opponent, BoardPrinterWrapper opboard, BoardPrinterWrapper playerboard) {
        for (int i = 0; i < opponent.getXSize(); i++) {
            for (int j = 0; j < opponent.getYSize(); j++) {
                if (BoardOrig.getMarker(i, j) == 's') {
                    boom(i, j);
                    i = opponent.getXSize();
                    j = opponent.getYSize();
                    break;
                }
            }
        }
        opboard.print(true);
        playerboard.print(true);
    }
    
    public int getRow() {
        return row;
    }

    
    public int getCol() {
        return col;
    }

    /**
     * places the ships of the AI player
     * @param playerBoard - Board object, playerWrapper - BoardPrinterWrapper object, PlaceIt - PlaceShip object
     * @return void
     */
    public void placeShipLoop(Board playerBoard, BoardPrinterWrapper playerWrapper, PlaceShip placeIt) {
        PlaceAIShips.placeAI(playerBoard, playerWrapper, placeIt);

    }

}