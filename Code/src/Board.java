/**
 * <h1>Board</h1>
 * <p>
 * <b> tsopeter@ku.edu </b>
 * </p>
 * <p>
 * The Board.java file handles the char[][] map object. It allows to fill in the
 * board with char board_marker and allows to add to board, get from board, and
 * get the dimensions of the board.
 *
 * It has two copy functions, It has getCopyBoard(), this passes an instance of
 * a Board Object that has the same value as this object It has getCopyMap(),
 * this passes an instance of a char[][] Objec that has the same value as
 * this.map Object.
 * </p>
 * <p>
 * <b> Note: </b>
 * </p>
 * <ul>
 * <li>There are no protections from OutOfBounds. Please implement protections
 * on your own code when using Board.java.</li>
 * </ul>
 * <p>
 * <b> Thank you. </b>
 * </p>
 * <p>
 *
 * @author tsopeter
 * @version 20200913
 * @since 09-05-2020
 * @since 09-08-2020
 * @since 09-13-2020
 *        </p>
 *        <p>
 *        <b> References: </b>
 *        </p>
 *        <ul>
 *        <li>https://www.tutorialspoint.com/java/java_documentation.html</li>
 *        <li>
 *        https://docs.oracle.com/javase/8/docs/technotes/tools/winodws/javadoc.html
 *        </li>
 *        </ul>
 */
public class Board {

	char[][] map;
	int xSize;
	int ySize;

	char board_marker;
	ship[] theShips;
	int numberOfShips;
	boolean[] hasSunk;
	int sunkCounter; 
	String name;
	int lastShipHit = 0;
	private char[] coordinateLetters = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I' };
	

	/**
	 * Must have valid dimensions and board marker
	 * <p>
	 * <b> Preconditions: </b>
	 * </p>
	 * <p>
	 * Must have strictly positive x and y
	 * </p>
	 * <p>
	 * <b> Postconditions: </b>
	 * </p>
	 * <p>
	 * Makes a map with x and y dimensions
	 * </p>
	 *
	 * @param x              Int
	 * @param y              Int
	 * @param t_board_marker Char
	 * @param numberOfShips Int
	 */
	public Board(int x, int y, char t_board_marker, int numberOfShips, String theName) {
		this.xSize = 0;
		this.ySize = 0;
		this.board_marker = '\0';

		this.xSize = x;
		this.ySize = y;

		this.board_marker = t_board_marker;

		this.map = new char[this.ySize][this.xSize];
		for (int i = 0; i < this.ySize; i++) {
			for (int k = 0; k < this.xSize; k++) {
				this.map[i][k] = this.board_marker;
			}
		}

		this.numberOfShips = numberOfShips;
		this.theShips = new ship[numberOfShips];
		for(int i = 0; i < numberOfShips; i++) {
			theShips[i] = new ship(i);
		}

		this.hasSunk = new boolean[numberOfShips];
		for(int i = 0; i < numberOfShips; i++) {
			this.hasSunk[i] = false;
		}
		this.sunkCounter = 0;

		this.name = theName;
	}

	/**
	 * This returns this map object
	 * <p>
	 * <b> Preconditions: </b>
	 * </p>
	 * <p>
	 * Must have been constructed
	 * </p>
	 * <p>
	 * <b> Postconditions: </b>
	 * </p>
	 * <p>
	 * Returns this.map Object
	 * </p>
	 *
	 * @return char[][]
	 */
	public char[][] getBoard() {
		return this.map;
	}

	/**
	 * This returns a copy of the Map
	 * <p>
	 * <b> Preconditions: </b>
	 * </p>
	 * <p>
	 * Must have been constructed
	 * </p>
	 * <p>
	 * <b> Postconditions: </b>
	 * </p>
	 * <p>
	 * Returns a copy of this.map with char[][] copy
	 * </p>
	 *
	 * @return char[][]
	 */
	public char[][] getCopyMap() {
		char[][] copy = new char[this.ySize][this.xSize];
		for (int i = 0; i < this.ySize; i++) {
			for (int k = 0; k < this.xSize; k++) {
				copy[i][k] = this.map[i][k];
			}
		}
		return copy;
	}

	public void setMapByCopy(Board copy) {
		this.map = copy.getCopyMap();
	}

	/**
	 * This returns a copy of the Board
	 * <p>
	 * <b> Preconditions: </b>
	 * </p>
	 * <p>
	 * Must have been constructed
	 * </p>
	 * <p>
	 * <b> Postconditions: </b>
	 * </p>
	 * <p>
	 * Returns a copy of this object with Board
	 * </p>
	 *
	 * @return Board
	 */
	public Board getCopyBoard(Board copy) {
		copy = new Board(this.xSize, this.ySize, this.board_marker, this.numberOfShips, this.name);
		for (int i = 0; i < this.ySize; i++) {
			for (int k = 0; k < this.xSize; k++) {
				copy.addMarker(this.map[i][k], i, k);
			}
		}
		return copy;
	}


	//copy constructor
	Board(Board c) {
		getCopyBoard(c);
	}


	/**
	 * This returns the X size of the array
	 * <p>
	 * <b> Preconditions: </b>
	 * </p>
	 * <p>
	 * Must have been constructed
	 * </p>
	 * <p>
	 * <b> Postconditions: </b>
	 * </p>
	 * <p>
	 * Returns the x dimension
	 * </p>
	 *
	 * @return int
	 */
	public int getXSize() {
		return this.xSize;
	}

	/**
	 * This returns the Y size of the array
	 * <p>
	 * <b> Preconditions: </b>
	 * </p>
	 * <p>
	 * Must have been constructed
	 * </p>
	 * <p>
	 * <b> Postconditions: </b>
	 * </p>
	 * <p>
	 * Returns the y dimension
	 * </p>
	 *
	 * @return int
	 */
	public int getYSize() {
		return this.ySize;
	}

	/**
	 * This method adds to the Map
	 * <p>
	 * <b> Preconditions: </b>
	 * </p>
	 * <p>
	 * Must have been constructed Must have valid address
	 * </p>
	 * <p>
	 * <b> Postconditions: </b>
	 * </p>
	 * <p>
	 * Adds a character to the map Object
	 * </p>
	 *
	 * @param marker Char
	 * @param x      Int
	 * @param y      Int
	 */
	public void addMarker(char marker, int x, int y) {
		this.map[x][y] = marker;
	}

	/**
	 * This method gets a character from Map
	 * <p>
	 * <b> Preconditions: </b>
	 * </p>
	 * <p>
	 * Must have been constructed Must have valid address
	 * </p>
	 * <p>
	 * <b> Postconditions: </b>
	 * </p>
	 * <p>
	 * Returns a character from a (x,y) position of map
	 * </p>
	 *
	 * @param x Int
	 * @param y Int
	 * @return char
	 */
	public char getMarker(int x, int y) {
		return this.map[x][y];
	}

	public void setShipCoordinates(int shipNum, int row, int col, int location) {
		String Pair = "";
		//String[] PairArray = new String[shipNum];
		Pair = coordinateLetters[col] + Integer.toString(row);
		theShips[shipNum].setShipCors(Pair, location);

		
	}

	public int getNumberOfShips() {
		return numberOfShips;
	}

	private boolean fleetSunk(ship[] sp, int num){
		if(num > 0){
			if(sp[num - 1].isSink() == true){
				return fleetSunk(sp, num - 1);
			} else if (sp[num - 1].isSink() == false && num > 0){
			return false;
			} else {
				return true;
			}
		} else {
			return true;
		}
	}

	public boolean fleetHasSunk(){
		return fleetSunk(theShips, numberOfShips);
	}

	public boolean isEq(Board playerBoard) {
		return this.name == playerBoard.name;
	}

	public String getName(){
		return name;
	}

	public boolean hitShipBool(String coordinates){
		//String coordinates = Integer.toString(row) + Integer.toString(col);
		for(int i = 0; i < numberOfShips; i++){
			if(theShips[i].shipHit(coordinates)){
				lastShipHit = i + 1;
				return true;
			} 
		}
		return false;
	}

	public void setlastShipHit(int num){
		lastShipHit = num;
	}

	public int getlastShipHit(){
		return lastShipHit;
	}

	public ship[] getShipArray(){
		return theShips;
	}
}
