import java.util.Random;

/**
 * Note for others: The AIMedium class implements the easy mode for playing against AI by firing
 * randomly at the player, but once it finds a hit it uses the coordinate information to fire in
 * orthongonally adjacent spaces to find other hits until a ship is sunk
 */

public class AIMedium implements gameLogicInterface{
    private int row = 0;
    private int col = 0;
    private int x;
    private int y;
    private Board BoardCopy;//this is going to keep track what's been visited
    private Board BoardOrig;//the actual board pointer
    Random rand = new Random();//RNG for placing ships and for firing initially
    private BrokenRadar broken; //= new BrokenRadar(BoardOrig);

    /**
     * AIMedium Constructor
     * @param board object
     * @return AIMedium object
     */
    AIMedium(Board orig){
        
        this.BoardOrig = orig;
        this.x = orig.getXSize();
        this.y = orig.getYSize();
        broken = new BrokenRadar(BoardOrig);
    }

    /**
     * Marks a ship coordinate with an 'x' to indicate a hit on the copy of the player's board
     * @param row - int that holds number of rows, col - int that holds number of columns
     * @return void
     */
    private void markHit(int row, int col){
        BoardCopy.addMarker('x', row, col);
        //BoardOrig.addMarker('x', row, col);
        
    }

    /**
     * Marks a ship coordinate with an 'x' to indicate a hit on the original player's board
     * @param row - int that holds number of rows, col - int that holds number of columns
     * @return void
     */
    private void markOrig(int row, int col){
        BoardOrig.addMarker('x', row, col);
        BoardOrig.hitShipBool(broken.convertCoor(row, col));
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
     * unmarks coordinates on the board
     * @param row - int that holds number of rows, col - int that holds number of columns
     * @return void
     */
    private void unMark(int row, int col){
        BoardCopy.addMarker('o', row, col);
    }

    /**
     * unmarks coordinates on the copy of the board
     * @param row - int that holds number of rows, col - int that holds number of columns
     * @return void
     */
    private void unMarkCopy(int row, int col) {
        BoardCopy.addMarker('v', row, col);
    }
    
    /**
     * Marks a random coordinate on the board.
     * @param size - int that holds size
     * @return boolean - if the random coordinate matches with a ship coordinate, returns true.
     *  Else, returns false
     */
    private boolean markRandom(int size) {
        //row = rand.nextInt(size);
        //col = rand.nextInt(size);
        do {
            row = rand.nextInt(size);
            col = rand.nextInt(size);
        } while(!origHit(row, col, 'o'));
        return isHit(row, col, 's');
    }

    /**
     * compares a coordinate on the original board to letter
     * @param row - int that holds number of rows, col - int that holds number of columns
     *  etter - char that holds the letter that a coordinate gets compared to
     * @return boolean - if the coordinate and letter match, returns true. Else, return false
     */
    private boolean origHit(int row, int col, char letter) {
        if(BoardOrig.getMarker(row, col) == letter) {
            return true;
        } else {
            return false;
        }
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
     * Looks at the coordinate a row above the provided coordinate within the same column
     * @param int that holds number of rows, col - int that holds number of columns,
     *  letter - char that holds the letter that a coordinate gets compared to
     * @return boolean - if the "above" coordinate is out of bounds, returns false.
     *  if the coordinate and letter don't match, returns false. Else, returns true
     */
    private boolean checkUp(int row, int col, char letter){
        if(row < 0 || col < 0 || row > x || col > y){
            return false;
        }
        else if(isHit(row - 1, col, letter)){
            return true; 
        } else {
            return false;
        }
    }

    /**
     * Looks at the coordinate a row below the provided coordinate within the same column
     * @param int that holds number of rows, col - int that holds number of columns,
     *  letter - char that holds the letter that a coordinate gets compared to
     * @return boolean - if the "below" coordinate is out of bounds, returns false.
     *  if the coordinate and letter don't match, returns false. Else, returns true
     */
    private boolean checkDown(int row, int col, char letter){
        if(row < 0 || col < 0 || row > x || col > y){
            return false;
        }
        else if (isHit(row + 1, col, letter)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Looks at the coordinate a column to the right of the provided coordinate within the same
     * row
     * @param int that holds number of rows, col - int that holds number of columns,
     *  letter - char that holds the letter that a coordinate gets compared to
     * @return boolean - if the "right" coordinate is out of bounds, returns false.
     *  if the coordinate and letter don't match, returns false. Else, returns true
     */
    private boolean checkRight(int row, int col, char letter){
        if(row < 0 || col < 0 || row > x || col > y){
            return false;
        }
        else if (isHit(row, col + 1, letter)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Looks at the coordinate a column to the left of the provided coordinate within the same
     * row
     * @param int that holds number of rows, col - int that holds number of columns,
     *  letter - char that holds the letter that a coordinate gets compared to
     * @return boolean - if the "left" coordinate is out of bounds, returns false.
     *  if the coordinate and letter don't match, returns false. Else, returns true
     */
    private boolean checkLeft(int row, int col, char letter){
        if(row < 0 || col < 0 || row > x || col > y){
            return false;
        }
        else if (isHit(row, col - 1, letter)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Once a ship is hit, this will check the area around the initial hit for the rest of the ship
     * @param row - int that holds number of rows, col - int that holds number of columns
     * @return boolean - once all the adjacent coordinates marked by an unsunk ship are marked hit
     *  returns true. Else, returns false.
     */
    private boolean solveBoard(int row, int col){
        
        if(checkUp(row, col, 's')){
            markHit(row - 1, col);
            return solveBoard(row - 1, col);
        } else if (checkDown(row, col, 's')){
            markHit(row + 1, col);
            return solveBoard(row + 1, col);
        } else if (checkRight(row, col, 's')){
            markHit(row, col - 1);
            return solveBoard(row, col - 1);
        } else if (checkLeft(row, col, 's')){
            markHit(row, col + 1);
            return solveBoard(row, col - 1);
        } 
        unMarkCopy(row, col);
        return false;
    }

    /**
     * If there were no
     */
    private boolean backTrack(int row, int col){
        if(checkUp(row, col, 'x')){
            this.row--;
            unMarkCopy(row - 1, col);
            return true;
        } else if (checkDown(row, col, 'x')){
            this.row++;
            unMarkCopy(row + 1, col);
            return true;
        } else if (checkLeft(row, col, 'x')){
            this.col--;
            unMarkCopy(row, col - 1);
            return true;
        } else if (checkRight(row, col, 'x')){
            this.col++;
            unMarkCopy(row, col + 1);
            return true;
        } return false;

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
    public boolean Loop(Board playerBoard, Board other, getUserInput UI, BoardPrinterWrapper player1Printer, BoardPrinterWrapper player2Printer) {
        int choice = 0;
        if(playerBoard.fleetHasSunk()){
            choice = 10000;
        } else {
            choice = rand.nextInt(10000);
        }
        if(choice < 9999){
            markBoard(other, player2Printer, player1Printer);
            return true;
        } else if(choice == 9999) {
            System.out.println("What?! AI has decided to surrender.");
            return false;
        }
        else {
            return false;
        }
    }

    /**
     * marks both player and AI board when there someone's ship is hit
     * @param opponent - Board object of AI board, opboard - BoardPrinterWrapper object of AI board,
     *  playerBoard - BoardPrinterWrapper object of player's board
     */
    public void markBoard(Board opponent, BoardPrinterWrapper opboard, BoardPrinterWrapper playerboard) {
        if(isHit(row, col, 'x')) {
            solveBoard(row, col);
            if(backTrack(row, col)){
                markOrig(row, col);
            } 
        } else {
            if(markRandom(opponent.getXSize())){
                markHit(row, col);
                markOrig(row, col);
            } else if(BoardOrig.getMarker(row, col) == '~'){
                markMiss(row, col);

            }
        }
        opboard.print(false);
        playerboard.print(false);
        
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
        this.BoardCopy = new Board(x, y, '~', BoardOrig.getNumberOfShips(), "AIMediumCopy");
        this.BoardCopy.setMapByCopy(BoardOrig);

    }
}