import java.lang.IllegalArgumentException;
import java.util.HashMap;



public class ship {
    private int size;
    private boolean[] shipArray;
    private HashMap<String, Integer> pair = new HashMap<String, Integer>();
    
    private int counter;

    public ship(int size) {
        this.size = size;
        shipArray = new boolean[5];
        for(int i = 0; i < size; i++) {
            shipArray[i] = false;
        }
        this.counter = 0;
        
    }

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

    private boolean shipSunk() {
        return counter == size;
    }

    public boolean isSink() {
        return shipSunk();
    }

    public boolean isSinkRecursive() {
        return shipSunkRecursive(shipArray, size);
    }

    public void setShipCors(String coordinates) {
        for(int i = 0; i < size; i++) {
            pair.put(coordinates, i);
        }
    }
}