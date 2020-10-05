import java.lang.IllegalArgumentException;

/**
 * Note for others: The PlaceShip class allows for the user, or AI, to place their ships on the 
 * board
 */
public class PlaceShip {
    private Board board;
    private String shipNumber;
    private boolean isHori;
    private boolean directionSucceeded;

    /**
     * PlaceShip Constructor
     * @param theBoard - board object
     * @return PlaceShip object
     */ 
    public PlaceShip(Board theBoard){
        this.board = theBoard;
        this.isHori = false;
        this.directionSucceeded = false;
        
        
    }
   
    /**
     * sets the direction that the ship will extend to after placing the ship's head
     * @param ds - boolean 
     * @return boolean - ds  
     */  
    private boolean setShipDirection(boolean ds) {
        directionSucceeded = ds;
        return directionSucceeded;
    }

    /**
     * actual placing of the ship and making sure the ship in in bounds of the board
     * @param row - int that holds number of rows, col - int that holds number of columns,
     *  size - int that holds the size of the row/col
     * @return boolean - returns false if any ship coordinates fall out of bounds
     */
    private boolean placeIterative(int row, int col, int size) {
        boolean[] check = new boolean[size];
        if(isHori) {
            for (int i = 0; i < size; i++) {
                if (!CollisionHandler.check(board, 's', row + i, col)) {
                    check[i] = true;
                } else {
                    System.out.println("Try a different coordinate");
                    return false;
                }
            }
            for (int i = 0; i < size; i++){
                if (check[i]){
                    board.addMarker('s', row + i, col);
                    board.setShipCoordinates(size - 1, row + 1 + i, col, i);
                }
            }
            return true;
        } else {
            for (int i = 0; i < size; i++) {
                if (!CollisionHandler.check(board, 's', row, col + i)) {
                    check[i] = true;
                } else {
                    System.out.println("Try a different coordinate");
                    return false;
                }
            }
            for (int i = 0; i < size; i++){
                if (check[i]){
                    board.addMarker('s', row, col + i);
                    board.setShipCoordinates(size - 1, row + 1, col + i, i);
                }
            }
         return true;
       }
        
    }
    
    /**
     * calls placeIterative method to place a ship
     * @param row - int that holds number of rows, col - int that holds number of columns,
     *  size - int that holds the size of the row/col
     * @return boolean - returns the results of the bool type placeIterative
     */
    private boolean placeIt(int row, int col, int size){
        return placeIterative(row, col, size);
    }

    /**
     * places ship on board and checks if ship will overlap other ships
     * @param row - int that holds number of rows, col - int that holds number of columns,
     *  size - int that holds the size of the row/col, dir - boolean
     * @return boolean - returns false if newly placed ship overlaps with another ship
     */
    public boolean place(int row, int col, int size, boolean dir) {
        try {
            isHori = dir;
            if(placeIt(row, col, size)){
                return setShipDirection(true);
            } else {
                throw new IllegalArgumentException("Error: collision with ship.");
            }
        } catch(IllegalArgumentException iae) {
            Utility.errorMessage(iae, "Could not place ship. Try again.");
            return setShipDirection(false);
        }
    }

    /**
     * grabs ship label
     * @param none
     * @return string - the ships label
     */
    public String getShipName() {
        return shipNumber;
    }

}