<<<<<<< HEAD
import java.util.Random;
import Board.java;

private class BrokenRadar gameLogicInterface {
  private Board BoardCopy; //don't need to manipulate board, just report coordinates
  private int row = 0;
  private int col = 0;
  Random rand = new Random(); //for the random coordinate

  //board copy


  //look at copy of board and report one coordinate where opponent exists

  private boolean isHit(int row, int col, char ship) {
    if(copyBoard.)
  }

  private boolean isShip(int row, int col) {
    //probably not needed
  }
  


  public void peekOpponent(Board opponent) {
    int row = opponent.getXSize;
    int col = opponent.getYSize;


    //is this too nested?
    for (int i = 0; i < row; i++) {
      for (int j = 0; i < col; i++) {

        if (!isHit(row, col) && isShip(row, col)) {
          //is not already hit and is a ship
          //row = conditionRow
          //col = condiationCol
        }
      }
    }
    
  }

  }
=======
public class BrokenRadar {
    private char[] coordinateLetters = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i' };
    private Board opponentBoard;
    public BrokenRadar(Board opponent){
        this.opponentBoard = opponent;//one way to do it
    } 
    
}
>>>>>>> 21a358749b8c1cb09c678094daa54e13218d6e50
