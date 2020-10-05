import java.util.Random;

/**
 * Note for others: The PlaceAIShips class is a helper class that places the ships of the AI player
 */
public class PlaceAIShips {
    public static int numShips = 1;
    private static Random rand = new Random();

    /**
     * picks AI ships
     * @param none
     * @return int - number of ships AI will use
     */
    public static int determineShips(){
        do {
            numShips = rand.nextInt(6);
        } while (numShips == 0);
        return numShips;
    }

    /**
     * places ships on board for AI player
     * @param playerBoard - Board object of AI, playerWrapper - BoardPrinterWrapper object of AI, 
     *  placeIt - PlaceShip object to place AI ships on its board
     */
    public static void placeAI(Board playerBoard, BoardPrinterWrapper playerWrapper,
    PlaceShip placeIt) {
        int placementRow;
        int placementCol;
        placeIt = new PlaceShip(playerBoard);
        for (int i = 0; i < numShips; i++) {
            placementRow = rand.nextInt(playerBoard.getXSize());
            placementCol = rand.nextInt(playerBoard.getYSize());
            boolean direction = false;
            int dir = rand.nextInt(2);
            if (dir == 1) {
                direction = true;
            } else {
                direction = false;
            }
            if (placeIt.place(placementRow, placementCol, i + 1, direction)) {
                System.out.println("Successful placement by AI!");
            } else {
                i--;
            }
        }
    }
}