import java.util.Random;

public class AIEasy implements gameLogicInterface {
    private int row;
    private int col;
    private Board BoardOrig;//the actual board pointer
    Random rand = new Random();//RNG for placing ships and for firing initially

    AIEasy(Board orig){
        this.BoardOrig = orig;
    }

    public String getCoordinates() {
        return "nothing";
    }

    public boolean Loop(Board playerBoard, Board other, getUserInput UI, BoardPrinterWrapper player1Printer,
            BoardPrinterWrapper player2Printer){
              if(!playerBoard.fleetHasSunk())
              {
                markBoard(other, player2Printer, player1Printer);
                return true;
              }else
              {
                return false;
              }
    }

    private void markOrig(int row, int col){
        BoardOrig.addMarker('x', row, col);
    }

    private void markMiss(int row, int col) {
        BoardOrig.addMarker('o', row, col);
    }

    private boolean markRandom(int size) {
      do {
          row = rand.nextInt(size);
          col = rand.nextInt(size);
      }while(isHit(row, col, 'o') && isHit(row, col, 'x'));
      return isHit(row, col, 's');
    }

    private boolean isHit(int row, int col, char letter) {
        if(BoardOrig.getMarker(row, col) == letter){
            return true;
        } else {
            return false;
        }
    }

    public void markBoard(Board opponent, BoardPrinterWrapper opboard, BoardPrinterWrapper playerboard){
      if(markRandom(opponent.getXSize()))
        {
          if(!isHit(row, col, 'x') && !isHit(row, col, 'o'))
          {
            markOrig(row, col);
          }
        }else
        {
            markMiss(row, col);
        }
      opboard.print(true);
      playerboard.print(true);
    }

    public int getRow(){
        return row;
    }

    public int getCol(){
        return col;
    }

    public void placeShipLoop(Board playerBoard, BoardPrinterWrapper playerWrapper, PlaceShip placeIt){
        PlaceAIShips.placeAI(playerBoard, playerWrapper, placeIt);
    }
}
