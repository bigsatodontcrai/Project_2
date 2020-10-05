import java.util.Random;
/**
 * <h1>AIMedium</h1>
 * The AIMedium class implements the easy mode for playing against AI by firing
 * randomly at the player, but once it finds a hit it uses the coordinate information to fire in
 * orthongonally adjacent spaces to find other hits until a ship is sunk
 *
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
     * @param orig - Board
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
     * @param row - int that holds number of rows
     * @param col - int that holds number of columns
     * @return void
     */
    private void markHit(int row, int col){
        BoardCopy.addMarker('x', row, col);
        
    }

    /**
     * Marks a ship coordinate with an 'x' to indicate a hit on the original player's board
     * @param row - int that holds number of rows
     * @param col - int that holds number of columns
     * @return void
     */
    private void markOrig(int row, int col){
        BoardOrig.addMarker('x', row, col);
        BoardOrig.hitShipBool(broken.convertCoor(row, col));
    }
    
    /**
     * Marks a coordinate that has already been shot at, but was not previously a ship
     * @param row - int that holds number of rows
     * @param col - int that holds number of columns
     * @return void
     */
    private void markMiss(int row, int col) {
        BoardOrig.addMarker('o', row, col);
    }

    /**
     * unmarks coordinates on the copy of the board
     * @param row - int that holds number of rows
     * @param col - int that holds number of columns
     * @return void
     */
    private void unMarkCopy(int row, int col) {
        BoardCopy.addMarker('v', row, col);
    }

    /**
     * Checks where the AI is on the board
     * @param row - int
     * @param col - int
     * @return boolean
     */
    private boolean checkLive(int row, int col){
        return row < 0 || col < 0 || row >= x || col >= y;
    } 
    
    /**
     * Marks a random coordinate on the board.
     * @param size - int that holds size
     * @return boolean - if the random coordinate matches with a ship coordinate, returns true.
     *  Else, returns false
     */
    private boolean markRandom(int size) {
        do {
            row = rand.nextInt(size);
            col = rand.nextInt(size);
        } while(origHit(row, col, 'o'));
        return isHit(row, col, 's');
    }

    /**
     * compares a coordinate on the original board to letter
     * @param row - int that holds number of rows
     * @param col - int that holds number of columns
     * @param letter - char that holds the letter that a coordinate gets compared to
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
     * @param row - int that holds number of rows
     * @param col - int that holds number of columns
     * @param letter - char that holds the letter that a coordinate gets compared to
     * @return boolean - if the coordinate and letter match, returns true. Else, return false
     */
    private boolean isHit(int row, int col, char letter) {
        if (checkLive(row, col)){
            return false;
        }
        if(BoardCopy.getMarker(row, col) == letter){
            BoardCopy.addMarker('x', row, col);
            System.out.println("lesgo " + row + " " + col);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Looks at the coordinate a row above the provided coordinate within the same column
     * @param row - int that holds number of rows
     * @param col - int that holds number of columns
     * @param letter - char that holds the letter that a coordinate gets compared to
     * @return boolean - if the "above" coordinate is out of bounds, returns false.
     *  if the coordinate and letter don't match, returns false. Else, returns true
     */
    private boolean checkUp(int row, int col, char letter){
        if(checkLive(row, col)){
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
     * @param row - int that holds number of rows
     * @param col - int that holds number of columns
     * @param letter - char that holds the letter that a coordinate gets compared to
     * @return boolean - if the "below" coordinate is out of bounds, returns false.
     *  if the coordinate and letter don't match, returns false. Else, returns true
     */
    private boolean checkDown(int row, int col, char letter){
        if(checkLive(row, col)){
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
     * @param row - int that holds number of rows
     * @param col - int that holds number of columns
     * @param letter - char that holds the letter that a coordinate gets compared to
     * @return boolean - if the "right" coordinate is out of bounds, returns false.
     *  if the coordinate and letter don't match, returns false. Else, returns true
     */
    private boolean checkRight(int row, int col, char letter){
        if(checkLive(row, col)){
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
     * @param row - int that holds number of rows
     * @param col - int that holds number of columns
     * @param letter - char that holds the letter that a coordinate gets compared to
     * @return boolean - if the "left" coordinate is out of bounds, returns false.
     *  if the coordinate and letter don't match, returns false. Else, returns true
     */
    private boolean checkLeft(int row, int col, char letter){
        if(checkLive(row, col)){
            return false;
        }
        else if (isHit(row, col - 1, letter)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Keeps track of what has been checked, up, down, left, and right
     * @param row - int
     * @param col - int
     * @return boolean
     */
    private boolean keepTrack(int row, int col) {
        boolean a = false;
        boolean b = false;
        boolean c = false;
        boolean d = false;
        if(checkDown(row, col, 's')) {
            a = ((BoardCopy.getMarker(row, col) == 'x' && BoardOrig.getMarker(row + 1, col) == 's'));
        } else {  a = false; } 
        if(checkRight(row, col, 's')) {
            b = ((BoardCopy.getMarker(row, col) == 'x' && BoardOrig.getMarker(row, col + 1) == 's'));
        } else { b = false; } 
        if(checkUp(row, col, 's')){
            c = ((BoardCopy.getMarker(row, col) == 'x' && BoardOrig.getMarker(row - 1, col) == 's'));
        } else  { c = false; }
        if(checkLeft(row, col, 's')) {
            d = ((BoardCopy.getMarker(row, col) == 'x' && BoardOrig.getMarker(row, col - 1) == 's'));
        } else { d = false;}
    
    return (a||b||c||d);
}

    /**
     * Once a ship is hit, this will check the area around the initial hit for the rest of the ship
     * @param row - int that holds number of rows
     * @param col - int that holds number of columns
     * @return boolean - once all the adjacent coordinates marked by an unsunk ship are marked hit
     *  returns true. Else, returns false.
     */
    private boolean solveBoard(int row, int col){
        
        if(checkUp(row, col, 's')){
            return solveBoard(row - 1, col) == keepTrack(row - 1, col) == true;
                
        } else if (checkDown(row, col, 's')){
            return solveBoard(row + 1, col) == keepTrack(row + 1, col) == true;
        } else if (checkRight(row, col, 's')){
            return solveBoard(row, col + 1) == keepTrack(row, col + 1) == true;
        } else if (checkLeft(row, col, 's')){
            return solveBoard(row, col - 1) == keepTrack(row, col - 1) == true;
        } 
        
        return false;
    }

    /**
     * Back Tracks to check if it missed a direction.
     * @param row - int 
     * @param col - int
     * @return boolean
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
     * Returns "nothing"
     * @param none
     * @return string 
     */
    public String getCoordinates() {
        return "nothing";
    }

    /**
     * Calls markBoard to loop through the steps of the game if either player hasn't lost all
     * their ships
     * @param playerBoard - board of player
     * @param other - board of opponent
     * @param UI - getUserInput
     * @param playerPrinter - BoardPrinterWrapper object of players board
     * @param player2Printer - Board PrinterWrapper object of AI board
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
     * @param opponent - Board object of AI board
     * @param opboard - BoardPrinterWrapper object of AI board
     * @param playerBoard - BoardPrinterWrapper object of player's board
     * @return void
     */
    public void markBoard(Board opponent, BoardPrinterWrapper opboard, BoardPrinterWrapper playerboard) {
        if(isHit(row, col, 'x')) {
            solveBoard(row, col);
        } 
        if(backTrack(row, col)){
                markOrig(row, col);
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
     * Returns row
     * @param none
     * @return int - row position
     */
    public int getRow() {
        return row;
    }

    /**
     * Returns col
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
        

    }
}