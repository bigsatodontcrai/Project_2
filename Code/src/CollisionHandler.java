/**
* <h1> CollisionHandler </h1>
* <p>This is the collision handler file. When given char marker, it
*		checks the given Board Object at the position (x,y).
*		Then returns true if there is a collision, or
*		false when there is no collision.
*		There are no protections from OutOfBounds. Please
*		implement protections on your own code when using
*		CollisionHandler.java</p>
* <p> <b> Updates: </b> </p>
* <ul>
*	<li> Update:	20200908 </li>
*	<li> Update: 20200913 </li>
* </ul>
* <p> <b> Update log: </b> </p>
* <ul>
* <li> Update: 20200908 :Added out of bounds detection. </li>
*	<li> Update: 20200913 :Added Javadoc documentation. </li>
* </ul>
*	<p> <b> Requried Files </b> </p>
* <ul>
*	<li> Board.java. </li>
* </ul>
* <b> Thank you. </b>
*<p>
*	@author tsopeter
* @version 20200913
*	@since					09-05-2020
*	@since					09-08-2020
*	@since					09-13-2020
* </p>
* <p> <b> References: </b> </p>
* <ul>
* <li> https://www.tutorialspoint.com/java/java_documentation.html </li>
* <li> https://docs.oracle.com/javase/8/docs/technotes/tools/winodws/javadoc.html </li>
* </ul>
*/
public class CollisionHandler {

	/**
	* This static method handles the collision checking
	* @Pre Must have valid Board and marker
	* @Post Checks the board against the marker and returns when true when detected
	* @param g - Board
	* @param marker - char
	* @param x - int
	* @param y - int
	* @return Boolean
	* @throws IllegalArgumentException iae
	*/
	public static boolean check(Board g, char marker, int x, int y){
		if(x < 0 || y < 0 || x >= g.getXSize() || y >= g.getYSize()){
			throw new IllegalArgumentException("The proivded coordinates are out of bounds.");
		}
		if(g.getMarker(x, y) == marker){
			return true;
		}
		return false;
	}
}
