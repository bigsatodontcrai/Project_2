/**
 * <h1>Board</h1>
 * <p>
 * The Board.java file handles the char[][] map object. It allows to fill in the
 * board with char board_marker and allows to add to board, get from board, and
 * get the dimensions of the board.
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
	 * @Preconditions Must have strictly positive x and y
	 * @Postconditions Makes a map with x and y dimensions
	 * @param x - Int
	 * @param y - Int
	 * @param t_board_marker - Char
	 * @param numberOfShips - Int
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
	 * @Pre Must have been constructed
	 * @Post Returns this.map Object
	 * @param none
	 * @return char[][]
	 */
	public char[][] getBoard() {
		return this.map;
	}

	/**
	 * This returns a copy of the Map
	 * @Pre Must have been constructed
	 * @Post Returns a copy of this.map with char[][] copy
	 * @param none
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

	/**
	 * Sets map equal to a copy
	 * @param copy - Board
	 * @return void
	 */
	public void setMapByCopy(Board copy) {
		this.map = copy.getCopyMap();
	}

	/**
	 * This returns a copy of the Board
	 * @Pre Must have been constructed
	 * @Post Returns a copy of this object with Board
	 * @param copy - Board
	 * @return Board object
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
	/**
	 * Board copy constructor
	 * @param c - Board
	 */
	Board(Board c) {
		getCopyBoard(c);
	}


	/**
	 * This returns the X size of the array
	 * @Pre Must have been constructed
	 * @Post Returns the x dimension
	 * @param none
	 * @return int
	 */
	public int getXSize() {
		return this.xSize;
	}

	/**
	 * This returns the Y size of the array
	 * @Pre: Must have been constructed
	 * @Post Returns the y dimension
	 * @param none
	 * @return int
	 */
	public int getYSize() {
		return this.ySize;
	}

	/**
	 * This method adds to the Map
	 * @Pre Must have been constructed Must have valid address
	 * @Post Adds a character to the map Object
	 * @param marker - Char
	 * @param x - Int
	 * @param y - Int
	 */
	public void addMarker(char marker, int x, int y) {
		this.map[x][y] = marker;
	}

	/**
	 * This method gets a character from Map
	 * @Pre Must have been constructed Must have valid address
	 * @Post Returns a character from a (x,y) position of map
	 * @param x - Int
	 * @param y - Int
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
	/**
	 * Gets the number of ships that the user chose in the beginning of the game
	 * @param none
	 * @return int
	 */
	public int getNumberOfShips() {
		return numberOfShips;
	}

	/**
	 * Checks if the fleet of ships has sunk.
	 * @param sp - ship[]
	 * @param num - int
	 * @return boolean 
	 * returns true if all ships have hit, false otherwise
	 */
	private boolean fleetSunk(ship[] sp, int num){
		for(int i = 0; i < num; i++){
			if(sp[i].isSink() == false){
				return false;
			}
		}
		System.out.println("The fleet has sunk! Good game."); 
		return true;
	}

	/**
	 * Returns whether or not the fleet of ships has sunk. Based off of what is returned from the function fleetSunk.
	 * @param none
	 * @return boolean
	 */
	public boolean fleetHasSunk(){
		
		return fleetSunk(theShips, numberOfShips);
	}

	/**
	 * Returns if current object is equal to playerBoard object
	 * @param playerBoard - Board
	 * @return boolean
	 */
	public boolean isEq(Board playerBoard) {
		return this.name == playerBoard.name;
	}

	/**
	 * Returns object name
	 * @param none
	 * @return string
	 */
	public String getName(){
		return name;
	}

	/**
	 * Checks if a ship has been hit at given coordinates
	 * @param coordinates
	 * @return boolean
	 * <p>Returns true if has been hit, false otherwise</p>
	 */
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

	/**
	 * Sets the variable lastShipHit equal to the int passed in
	 * @param num - int
	 * @return void
	 */
	public void setlastShipHit(int num){
		lastShipHit = num;
	}

	/**
	 * Gets the variable lastShipHit and returns its' value
	 * @param none
	 * @return void
	 */
	public int getlastShipHit(){
		return lastShipHit;
	}
	/**
	 * Gets the ship array theShips and returns the array
	 * @param none
	 * @return ship[]
	 */
	public ship[] getShipArray(){
		return theShips;
	}
}
