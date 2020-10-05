/**
 * Note for others: The AIHard class implements the easy mode for playing against AI by firing
 * at exactly the coordinates the player chooses to place their ships
 */

public class AIHard implements gameLogicInterface {
    private int row;
    private int col;

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
                return true;
    }
    /**
     * marks both player and AI board when there someone's ship is hit
     * @param opponent - Board object of AI board, opboard - BoardPrinterWrapper object of AI board,
     *  playerBoard - BoardPrinterWrapper object of player's board
     */
    public void markBoard(Board opponent, BoardPrinterWrapper opboard, BoardPrinterWrapper playerboard) {

    }
    /**
     * @param none
     * @return int - row position
     */
    public int getRow() {
        return row;
    }

    /**
     * @param none
     * @return int - column position
     */
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