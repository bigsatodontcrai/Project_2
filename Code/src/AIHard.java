public class AIHard implements gameLogicInterface {
    private int row;
    private int col;

    public String getCoordinates() {
        return "nothing";
    }

    public boolean Loop(Board playerBoard, Board other, getUserInput UI, BoardPrinterWrapper player1Printer,
            BoardPrinterWrapper player2Printer) {
                return true;
    }

    public void markBoard(Board opponent, BoardPrinterWrapper opboard, BoardPrinterWrapper playerboard) {

    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void placeShipLoop(Board playerBoard, BoardPrinterWrapper playerWrapper, PlaceShip placeIt) {
        PlaceAIShips.placeAI(playerBoard, playerWrapper, placeIt);
    }
    
}