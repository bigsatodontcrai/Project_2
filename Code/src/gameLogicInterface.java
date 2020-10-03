public interface gameLogicInterface {
    public String getCoordinates();

    public boolean Loop(Board playerBoard, Board other, getUserInput UI, BoardPrinterWrapper player1Printer,
            BoardPrinterWrapper player2Printer);

    public void markBoard(Board opponent, BoardPrinterWrapper opboard, BoardPrinterWrapper playerboard);

    public int getRow();
    
    public int getCol();

    public void placeShipLoop(Board playerBoard, BoardPrinterWrapper playerWrapper, PlaceShip placeIt);



}