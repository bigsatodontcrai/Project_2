//If classes come from files in the same folder, you don't have to import them :)
//You can compile this program in the terminal with "javac Battleship.java" and run it with "java Battleship"

/**
* <h1> Battleship </h1>
* <p>
* This is the program's main entry point. Creates a Run object and calls letsPlay().
* </p>
*/
public class Battleship {
    /**
        * This the the program's main function. Staring point of the program
        */
    public static void main(String[] args) {
        
        //Executive gameHandler = new Executive();
        //gameHandler.run();
        Run gameHandler = new Run();//creates Run object
        gameHandler.letsPlay(); //calls letsPlay() function
    }
}