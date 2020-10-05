/**
 * <h1>gameLogicInterface</h1>
 * Interface for the game logic, how we run the game throughout the classes.
 */

public interface gameLogicInterface {
    /**
     * @param none
     * @return string 
     */
	public String getCoordinates();

    /**
     * Implementation of gameLogicInterface function.
     * Takes user input when the game menu is printed and calls what is needed from the input
     * @param playerBoard - Board
     * @param other - Board
     * @param UI - getUserInput
     * @param player1Printer - BoardPrinterWrapper
     * @param player2Printer - BoardPrinterWrapper
     * @return boolean
     */
    public boolean Loop(Board playerBoard, Board other, getUserInput UI, BoardPrinterWrapper player1Printer,
            BoardPrinterWrapper player2Printer);

    /**
     * Marks opponent's board where player has chosen to attack
     * @param opponent - Board
     * @param opboard - BoardPrinterWrapper
     * @param playerboard - BoardPrinterWrapper
     * @return void
     */
    public void markBoard(Board opponent, BoardPrinterWrapper opboard, BoardPrinterWrapper playerboard);

    /**
     * Returns row
     * @param none
     * @return int - row position
     */
    public int getRow();
    
    /**
     * Returns col
     * @param none
     * @return int - column position
     */
    public int getCol();

    /**
     * places the ships of the AI player
     * @param playerBoard - Board object
     * @param playerWrapper - BoardPrinterWrapper object
     * @param PlaceIt - PlaceShip object
     * @return void
     */
    public void placeShipLoop(Board playerBoard, BoardPrinterWrapper playerWrapper, PlaceShip placeIt);



}