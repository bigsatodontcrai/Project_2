import java.util.Random;

/* 
Note for others: The AIEasy class implements the easy mode for playing against AI by firing
randomly at the players board.
*/

public class AIEasy implements gameLogicInterface {
    private int row;
    private int col;
    private Board BoardCopy;//this is going to keep track what's been visited
    private Board BoardOrig;//the actual board pointer
    Random rand = new Random();//RNG for placing ships and for firing initially
    
    /**
     * AIEasy Constructor
     * @param board oject
     * @return AIEasy object
     */
    AIEasy(Board orig){
        this.BoardCopy = orig.getCopyBoard(orig);
        this.BoardOrig = orig;
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
            BoardPrinterWrapper player2Printer){
              if(!playerBoard.fleetHasSunk())
              {
                markBoard(other, player2Printer, player1Printer);
                return true
              }else
              {
                return false;
              }
    }

    /**
     * Marks a ship coordinate with an 'x' to indicate a hit on the copy of the player's board
     * @param row - int that holds number of rows, col - int that holds number of columns
     * @return void
     */
    private void markHit(int row, int col){
        BoardCopy.addMarker('x', row, col);
    }

    /**
     * Marks a ship coordinate with an 'x' to indicate a hit on the original player's board
     * @param row - int that holds number of rows, col - int that holds number of columns
     * @return void
     */
    private void markOrig(int row, int col){
        BoardOrig.addMarker('x', row, col);
    }

    /**
     * Marks a coordinate that has already been shot at, but was not previously a ship
     * @param row - int that holds number of rows, col - int that holds number of columns
     * @return void
     */
    private void markMiss(int row, int col) {
        BoardOrig.addMarker('o', row, col);
    }

    /**
     * Marks a random coordinate on the board.
     * @param size - int that holds size
     * @return boolean - if the random coordinate matches with a ship coordinate, returns true.
     *  Else, returns false
     */
    private boolean markRandom(int size) {
        row = rand.nextInt(size);
        col = rand.nextInt(size);
        return isHit(row, col, 's');
    }

    /**
     * checks if a ship has been hit
     * @param row - int that holds number of rows, col - int that holds number of columns,
     *  letter - char that holds the letter that a coordinate gets compared to
     * @return boolean - if the coordinate and letter match, returns true. Else, return false
     */
    private boolean isHit(int row, int col, char letter) {
        if(BoardCopy.getMarker(row, col) == letter){
            return true;
        } else {
            return false;
        }
    }

    /**
     * marks both player and AI board when there someone's ship is hit
     * @param opponent - Board object of AI board, opboard - BoardPrinterWrapper object of AI board,
     *  playerBoard - BoardPrinterWrapper object of player's board
     */
    public void markBoard(Board opponent, BoardPrinterWrapper opboard, BoardPrinterWrapper playerboard){
      if(!isHit(row, col, 'x'))
      {
        if(markRandom(opponent.getXSize()))
        {
            markHit(row, col);
            markOrig(row, col);
        }else
        {
            markMiss(row, col);
        }
      }
      opbaord.print(true);
      playerboard.print(true);
    }

    /**
     * @param none
     * @return int - row position
     */
    public int getRow(){
        return row;
    }

    /**
     * @param none
     * @return int - column position
     */
    public int getCol(){
        return col;
    }

    /**
     * places the ships of the AI player
     * @param playerBoard - Board object, playerWrapper - BoardPrinterWrapper object, PlaceIt - PlaceShip object
     * @return void
     */
    public void placeShipLoop(Board playerBoard, BoardPrinterWrapper playerWrapper, PlaceShip placeIt){
        PlaceAIShips.placeAI(playerBoard, playerWrapper, placeIt);
    }
}
