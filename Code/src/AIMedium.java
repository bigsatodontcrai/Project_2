import java.util.Random;

public class AIMedium implements gameLogicInterface{
    private int row = 0;
    private int col = 0;
    private Board BoardCopy;//this is going to keep track what's been visited
    private Board BoardOrig;//the actual board pointer
    Random rand = new Random();//RNG for placing ships and for firing initially

    AIMedium(Board orig){
        this.BoardCopy = orig.getCopyBoard(orig);
        this.BoardOrig = orig;
    }

    private void markHit(int row, int col){
        BoardCopy.addMarker('x', row, col);
        //BoardOrig.addMarker('x', row, col);
        
    }

    private void markOrig(int row, int col){
        BoardOrig.addMarker('x', row, col);
    }
    private void markMiss(int row, int col) {
        BoardOrig.addMarker('o', row, col);
    }
    private void unMark(int row, int col){
        BoardCopy.addMarker('o', row, col);
    }

    private void unMarkCopy(int row, int col) {
        BoardCopy.addMarker('v', row, col);
    }
    
    private boolean markRandom(int size) {
        row = rand.nextInt(size);
        col = rand.nextInt(size);
        return isHit(row, col, 's');
    }

    private boolean isHit(int row, int col, char letter) {
        if(BoardCopy.getMarker(row, col) == letter){
            return true;
        } else {
            return false;
        }
    }

    private boolean checkUp(int row, int col, char letter){
        if(isHit(row - 1, col, letter)){
            return true; 
        } else {
            return false;
        }
    }

    private boolean checkDown(int row, int col, char letter){
        if (isHit(row + 1, col, letter)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean checkRight(int row, int col, char letter){
        if (isHit(row, col + 1, letter)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean checkLeft(int row, int col, char letter){
        if (isHit(row, col - 1, letter)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean solveBoard(int row, int col){
        
        if(checkUp(row, col, 's')){
            markHit(row - 1, col);
            return backTrack(row - 1, col);
        } else if (checkDown(row, col, 's')){
            markHit(row + 1, col);
            return backTrack(row + 1, col);
        } else if (checkRight(row, col, 's')){
            markHit(row, col - 1);
            return backTrack(row, col - 1);
        } else if (checkLeft(row, col, 's')){
            markHit(row, col + 1);
            return backTrack(row, col - 1);
        } 
        unMark(row, col);
        return false;
    }

    private boolean backTrack(int row, int col){
        if(checkUp(row, col, 'x')){
            this.row--;
            unMarkCopy(row - 1, col);
            return true;
        } else if (checkDown(row, col, 'x')){
            this.row++;
            unMarkCopy(row + 1, col);
            return true;
        } else if (checkLeft(row, col, 'x')){
            this.col--;
            unMarkCopy(row, col - 1);
            return true;
        } else if (checkRight(row, col, 'x')){
            this.col++;
            unMarkCopy(row, col + 1);
            return true;
        } return false;

    }

    public String getCoordinates() {
        return "nothing";
    }

    public boolean Loop(Board playerBoard, Board other, getUserInput UI, BoardPrinterWrapper player1Printer, BoardPrinterWrapper player2Printer) {
        int choice = 0;
        if(playerBoard.fleetHasSunk()){
            choice = 10000;
        }
        else {
            choice = rand.nextInt(10000);
        }
        if(choice < 9999){
            markBoard(other, player2Printer, player1Printer);
            return true;
        } else if(choice == 9999) {
            System.out.println("What?! AI has decided to surrender.");
            return false;
        }
        else {
            return false;
        }
    }

    public void markBoard(Board opponent, BoardPrinterWrapper opboard, BoardPrinterWrapper playerboard) {
        if(isHit(row, col, 'x')) {
            solveBoard(row, col);
            if(backTrack(row, col)){
                markOrig(row, col);
            } else {
                markMiss(row, col);
            }
        } else {
            if(markRandom(opponent.getXSize())){
                markHit(row, col);
                markOrig(row, col);
            } else {
                markMiss(row, col);

            }
        }
        opboard.print(true);
        playerboard.print(true);
        
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void placeShipLoop(Board playerBoard, BoardPrinterWrapper playerWrapper, PlaceShip placeIt) {
        PlaceAIShips.placeAI(playerBoard, playerWrapper, placeIt);

    }
}