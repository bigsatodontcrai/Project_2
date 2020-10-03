import java.util.Random;

public class PlaceAIShips {
    public static void placeAI(Board playerBoard, BoardPrinterWrapper playerWrapper,
    PlaceShip placeIt) {
        Random rand = new Random();
        int numShips = 1;
        int placementRow;
        int placementCol;
        do {
            numShips = rand.nextInt(6);
        } while (numShips == 0);
        //playerBoard = new Board(9, 9, '~', numShips, "player1Board");
        //playerWrapper = new BoardPrinterWrapper(playerBoard, 's', '~', true);
        placeIt = new PlaceShip(playerBoard);
        for (int i = 0; i < numShips; i++) {
            placementRow = rand.nextInt(playerBoard.getXSize() + 1);
            placementCol = rand.nextInt(playerBoard.getYSize() + 1);
            boolean direction = false;
            int dir = rand.nextInt(2);
            if (dir == 1) {
                direction = true;
            } else {
                direction = false;
            }
            if (placeIt.place(placementRow, placementCol, i, direction)) {
                System.out.println("Successful placement by AI!");
            } else {
                i--;
            }
        }
    }
}