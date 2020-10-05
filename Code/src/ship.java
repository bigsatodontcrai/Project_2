import java.lang.IllegalArgumentException;
import java.util.HashMap;


/**
 *<h1> ship </h1>
 * This class controls the ships within the game.
 */
public class ship {
    private int size;
    private boolean[] shipArray;
    private HashMap<String, Integer> pair = new HashMap<String, Integer>();
    private HashMap<Integer, String> reversePair = new HashMap<Integer, String>();
    private int counter;

    /**
     * Class constructor, sets the object variable size equal to the size passed plus one. Initalizes shipArray to the size passed in.
     * Sets counter equal to 0.
     * @param size -int, the size of the ship
     */
    public ship(int size) {
        this.size = size + 1;
        shipArray = new boolean[this.size];
        for(int i = 0; i < this.size; i++) {
            shipArray[i] = false;
        }
        this.counter = 0;
        
    }

    /**
     * Marks a ship spot as hit and increases counter by one.
     * @param coordinates - string
     * @return boolean, returns true which the board can use to print that the ship has been hit, returns false otherwise
     */
    public boolean shipHit(String coordinates) {
        if(pair.containsKey(coordinates)) {
            if(shipArray[pair.get(coordinates)] == false) {
                shipArray[pair.get(coordinates)] = true;
                counter++;
            }
            return true;//returns true which the board can use to print that the ship has been hit
        } else {
            return false;
        }
    }

    /**
     * Sinks a ship recursively
     * @param arr - boolean[]
     * @param theSize - int
     */
    private boolean shipSunkRecursive(boolean[] arr, int theSize){
        if(size == 1 && shipArray[0]) {
            return true;
        } else if(theSize == 1 && shipArray[(theSize/2)] == true) {
            return shipSunkRecursive(arr, 2 * (size - 1));
        } else if(theSize < size && theSize > size/2 + 1) {
            return shipSunkRecursive(arr, theSize - 1);
        } else if (theSize == size/2 + 1) {
            return arr[theSize - 1];
        } else if(arr[(theSize/2)]) {
            return shipSunkRecursive(arr, theSize/2);
        } 
        return false;
    }

    /**
     * Checks to see if a ship has sunk
     * @param none
     * @return boolean, returns true if counter and size are the same, meaning the ship has been hit at all spot, returns false otherwise.
     */
    private boolean shipSunk() {
        return counter == size;
    }

    /**
     * Returns outcome of shipSunk()
     * @param none
     * @return boolean
     */
    public boolean isSink() {
        return shipSunk();
    }
    
    /**
     * Returns the outcome of shipSunkRecursive()
     * @param none
     * @return boolean
     */
    public boolean isSinkRecursive() {
        return shipSunkRecursive(shipArray, size);
    }

    /**
     * Sets the ships coordinates
     * @param coordinates - string
     * @param location - int
     * @return void
     */
    public void setShipCors(String coordinates, int location) {
        
        pair.put(coordinates, location);
        reversePair.put(location, coordinates);
        
    }
    
    /**
     * Gets the ship's location
     * @param location - int
     * @return string
     */
    public String getShipLoc(int location){
        return reversePair.get(location);
    }
}