public class AIHard implements gameLogicInterface {
    private int row = 0;
    private int col = 0;
    private Board BoardOrig;

    AIHard(Board orig) {
        this.BoardOrig = orig;
    }

    private void boom(int row, int col) {
        BoardOrig.addMarker('x', row, col);
    }

    public String getCoordinates() {
        return "nothing";
    }

    public boolean Loop(Board playerBoard, Board other, getUserInput UI, BoardPrinterWrapper player1Printer,
            BoardPrinterWrapper player2Printer) {

        if (!playerBoard.fleetHasSunk()) {
            markBoard(other, player2Printer, player1Printer);
            return true;
        } else {
            return false;
        }
    }

    public void markBoard(Board opponent, BoardPrinterWrapper opboard, BoardPrinterWrapper playerboard) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (BoardOrig.getMarker(i, j) == 's') {
                    boom(i, j);
                    i = j = 9;
                    break;
                }
            }
        }
        opboard.print(true);
        playerboard.print(true);
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