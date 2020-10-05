import java.lang.IllegalArgumentException;

public class PlaceShip {
    private Board board;
    private String shipNumber;
    private boolean isHori;
    private boolean directionSucceeded;

    public PlaceShip(Board theBoard){
        this.board = theBoard;
        this.isHori = false;
        this.directionSucceeded = false;
        
        
    }
   
        
    private boolean setShipDirection(boolean ds) {
        directionSucceeded = ds;
        return directionSucceeded;
    }

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
    
    private boolean placeIt(int row, int col, int size){
        return placeIterative(row, col, size);
    }

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

    public String getShipName() {
        return shipNumber;
    }

}