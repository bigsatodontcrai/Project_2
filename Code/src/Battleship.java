//If classes come from files in the same folder, you don't have to import them :)
//You can compile this program in the terminal with "javac Battleship.java" and run it with "java Battleship"

/**
* <h1> Battleship </h1>
* <p>
* This is the program's main entry point.
* </p>
* <b>
* Author: Sean Cunningham
* </b>
* <p>
* KUID: 2935773
* </p>
* <p>
* Email: s096c429@ku.edu
* </p>
* @author Sean Cunningham
* @KUID: 2935773
* @Email: s096c429@ku.edu
* </p>
*/
public class Battleship {
    /**
        * This the the program's main
        */
    public static void main(String[] args) {
        
        //Executive gameHandler = new Executive();
        //gameHandler.run();
        Run gameHandler = new Run();
        gameHandler.letsPlay();
    }
}