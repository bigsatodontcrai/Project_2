/**
* <h1> BoardPrinter </h1>
* <p>This is the board printer file. It handles printing to the screen</p>
*	<p> <b> Required Files: </b> </p>
* <ul>
*		<li> Board.java </li>
* </ul>
*/
public class BoardPrinter{
	/**
	* This static method prints the board normally
	* @Pre Must have valid Board object
	* @Post Outputs the board to the console
	* @param g - Board
	*/
	public static void printBoard(Board g){
		for(int i = 0; i < g.getYSize(); i++){
			for(int k = 0; k < g.getXSize(); k++){
				System.out.print(g.getMarker(k, i));
				System.out.print(' ');
			}
			System.out.print('\n');
		}
	}

	/**
	*	This static method prints the board without a given character
	* @Pre Must have valid Board Object and remove_character
	* @Post Outputs the board to the console
	* @param g - Board
	* @param remove_marker - char
	*/
	public static void printAndRemove(Board g, char remove_marker){
		for(int i = 0; i < g.getYSize(); i++){
			for(int k = 0; k < g.getXSize(); k++){
				if(g.getMarker(k, i) == remove_marker){
					System.out.print(g.board_marker);
				}
				else{
					System.out.print(g.getMarker(k, i));
				}
				System.out.print(' ');
			}
			System.out.print('\n');
		}
	}

}
