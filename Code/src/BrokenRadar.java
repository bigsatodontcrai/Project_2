import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;
import java.util.Collections;

/**
 * <h1> BrokenRadar</h1>
 * The Broken Radar is a custome feature that allows the user to see some
 * coordinates of the opponent's board marked by ships. However, since it's broken not all
 * provided coordinates are true. One coordinate is random.
 */
public class BrokenRadar {
    private char[] coordinateLetters = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I' };
    private ship[] shipArray;
    private int size;
    private int numberOfShips;
    private Random rand = new Random();
    private int numRows;
    private int numCols;
    private LinkedList<String> Coordinates = new LinkedList<String>();
    private HashMap<Integer, String> used = new HashMap<Integer, String>();
    private HashMap<Integer, String> trueCombo = new HashMap<Integer, String>();
    private HashMap<String, Integer> trueComboReverse = new HashMap<String, Integer>();

    /**
     * BrokenRadar constructor
     * @param board object
     * @return BrokenRadar object
     */
    public BrokenRadar(Board opponent){
        int temp = opponent.getNumberOfShips();
        System.out.println(temp);
        this.numRows = opponent.getXSize();
        this.numCols = opponent.getYSize();
        this.numberOfShips = temp;
        this.shipArray = opponent.getShipArray();
        this.size = temp * (temp + 1) / 2;//1 + 2 + 3... is the number of locations with ships
    } 

    /**
     * coverts index of the array the coordinates are stored in to a letter that makes sense
     * to a Battleship board
     * @param row - int that holds number of rows
     * @param col - int that holds number of columns
     * @return string - letter that correlates to the index in the array where integer
     * coordinate is stored
     */
    public String convertCoor(int row, int col){
        return coordinateLetters[col] + Integer.toString(row);
    }

    /**
     * Locates coordinates of opponents ships
     * @param none
     * @return void
     */
    public void fillMap(){
        String[] set;
        for(int i = 0; i < numberOfShips; i++){
            set = new String[i + 1];
            for(int j = 0; j < i + 1; j++){
                set[j] = shipArray[i].getShipLoc(j);
                //System.out.println("in original array" + set[j]);
            }
            
            int s = 0;
            if(i != 0){
                s = i * (i + 1)/2;
            }
            
            for (int j = s; j < s + i + 1; j++) {
                //System.out.println(j);
                trueCombo.put(j, set[j - s]);
                trueComboReverse.put(set[j - s], j);
                //System.out.println("yung money " + trueComboReverse.get(set[j - s]));
            }
            
        }
    }//N^2 complexity but better than a brute force search of the board, technically from n(n+1)/2 complexity which is 

    /**
     * removes true coordinates from list
     * @param int - location
     * @return void
     */
    private void removeFromList(int location){
        String coor = trueCombo.get(location);
        trueCombo.remove(location, coor);
        used.put(location, coor);
    }

    /**
     * calls removeFromList to remove coordiante from list
     * @param string - coordinate with a letter
     * @return void
     */
    public void removeByCoordinate(String coordinate){
        int location = trueComboReverse.get(coordinate);
        trueComboReverse.remove(coordinate, location);
        removeFromList(location);
    }

    /**
     * provides true coordiantes of opponents ships
     * @param none
     * @return void
     */
    private void getRealCoordinate(){
        int randomLoc = rand.nextInt(size);
        boolean check = true;
        do {
            if (trueCombo.containsKey(randomLoc)){
                Coordinates.push(trueCombo.get(randomLoc));
                check = false;
            } else {
                randomLoc = rand.nextInt(size);
                check = true;
            }
        } while(check);
    }

    /**
     * provides random coordinate that might throw off the player
     * @param none
     * @return void
     */
    private void getRandomCoordinate(){
        boolean check = true;
        int randomRow = 0;
        int randomCol = 0;
        String place = "";
        do {
            randomRow = rand.nextInt(numRows);
            randomCol = rand.nextInt(numCols);
            place = convertCoor(randomRow, randomCol);
            if(trueCombo.containsValue(place) || used.containsValue(place)){
                check = true;
            } else {
                Coordinates.push(place);
                check = false;
            }
        } while(check);
        

    }

    /**
     * collects coordinates to be displayed to player
     * @param none
     * @return boolean - returns true after calling other methods
     */
    private boolean findCoordinates(){
        
        getRealCoordinate();
        getRealCoordinate();
        getRandomCoordinate();
        return true;
        
        
    }

    /**
     * shuffles the array that stores the coordinates to be provided to no patern can
     * be detected after multiple uses
     * @param none
     * @return void
     */
    private void shuffleList(){
        Collections.shuffle(Coordinates);
    }

    /**
     * clears list of coordinates
     * @param none
     * @return void
     */
    private void emptyList(){
        Coordinates.clear();
    }

    /**
     * implements broken aspect of the radar
     * @param none
     * @return void
     */
    private void setBroken(){
        findCoordinates();
        shuffleList();
    }
    
    /**
     * displays coordinates to player
     * @param none
     * @return void
     */
    private void printList(){
        
        System.out.println("Their locations: " + Coordinates.get(0) + " " + Coordinates.get(1) + " " + Coordinates.get(2));
        
    }

    /**
     * activates radar
     * @param none
     * @return void
     */
    public void runRadar(){
        setBroken();
        printList();
        emptyList();
    }
  


  /*public void peekOpponent(Board opponent) {
    int row = opponent.getXSize;
    int col = opponent.getYSize;


    //is this too nested?
    for (int i = 0; i < row; i++) {
      for (int j = 0; i < col; i++) {

        if (!isHit(row, col) && isShip(row, col)) {
          //is not already hit and is a ship
          //row = conditionRow
          //col = condiationCol
        }
      }
    }
    
  }

  }*/
}