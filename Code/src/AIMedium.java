import java.util.Random;

public class AIMedium implements gameLogicInterface{
    private int row = 0;
    private int col = 0;
    private int x;
    private int y;
    private Board BoardCopy;//this is going to keep track what's been visited
    private Board BoardOrig;//the actual board pointer
    Random rand = new Random();//RNG for placing ships and for firing initially
    private BrokenRadar broken; //= new BrokenRadar(BoardOrig);
    

    AIMedium(Board orig){
        
        this.BoardOrig = orig;
        this.x = orig.getXSize();
        this.y = orig.getYSize();
        broken = new BrokenRadar(BoardOrig);
    }

    private void markHit(int row, int col){
        BoardCopy.addMarker('x', row, col);
        
    }

    private void markOrig(int row, int col){
        BoardOrig.addMarker('x', row, col);
        BoardOrig.hitShipBool(broken.convertCoor(row, col));
    }
    private void markMiss(int row, int col) {
        BoardOrig.addMarker('o', row, col);
    }

    private void unMarkCopy(int row, int col) {
        BoardCopy.addMarker('v', row, col);
    }

    private boolean checkLive(int row, int col){
        return row < 0 || col < 0 || row >= x || col >= y;
    } 
    
    private boolean markRandom(int size) {
        do {
            row = rand.nextInt(size);
            col = rand.nextInt(size);
        } while(origHit(row, col, 'o'));
        return isHit(row, col, 's');
    }

    private boolean origHit(int row, int col, char letter) {
        if(BoardOrig.getMarker(row, col) == letter) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isHit(int row, int col, char letter) {
        if (checkLive(row, col)){
            return false;
        }
        if(BoardCopy.getMarker(row, col) == letter){
            BoardCopy.addMarker('x', row, col);
            System.out.println("lesgo " + row + " " + col);
            return true;
        } else {
            return false;
        }
    }

    private boolean checkUp(int row, int col, char letter){
        if(checkLive(row, col)){
            return false;
        }
        else if(isHit(row - 1, col, letter)){
            return true; 
        } else {
            return false;
        }
    }

    private boolean checkDown(int row, int col, char letter){
        if(checkLive(row, col)){
            return false;
        }
        else if (isHit(row + 1, col, letter)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean checkRight(int row, int col, char letter){
        if(checkLive(row, col)){
            return false;
        }
        else if (isHit(row, col + 1, letter)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean checkLeft(int row, int col, char letter){
        if(checkLive(row, col)){
            return false;
        }
        else if (isHit(row, col - 1, letter)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean keepTrack(int row, int col) {
        boolean a = false;
        boolean b = false;
        boolean c = false;
        boolean d = false;
        if(checkDown(row, col, 's')) {
            a = ((BoardCopy.getMarker(row, col) == 'x' && BoardOrig.getMarker(row + 1, col) == 's'));
        } else {  a = false; } 
        if(checkRight(row, col, 's')) {
            b = ((BoardCopy.getMarker(row, col) == 'x' && BoardOrig.getMarker(row, col + 1) == 's'));
        } else { b = false; } 
        if(checkUp(row, col, 's')){
            c = ((BoardCopy.getMarker(row, col) == 'x' && BoardOrig.getMarker(row - 1, col) == 's'));
        } else  { c = false; }
        if(checkLeft(row, col, 's')) {
            d = ((BoardCopy.getMarker(row, col) == 'x' && BoardOrig.getMarker(row, col - 1) == 's'));
        } else { d = false;}
    
    return (a||b||c||d);
}

    private boolean solveBoard(int row, int col){
        
        if(checkUp(row, col, 's')){
            return solveBoard(row - 1, col) == keepTrack(row - 1, col) == true;
                
        } else if (checkDown(row, col, 's')){
            return solveBoard(row + 1, col) == keepTrack(row + 1, col) == true;
        } else if (checkRight(row, col, 's')){
            return solveBoard(row, col + 1) == keepTrack(row, col + 1) == true;
        } else if (checkLeft(row, col, 's')){
            return solveBoard(row, col - 1) == keepTrack(row, col - 1) == true;
        } 
        
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
        } else {
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
        } 
        if(backTrack(row, col)){
                markOrig(row, col);
            } else {
            if(markRandom(opponent.getXSize())){
                markHit(row, col);
                markOrig(row, col);
            } else if(BoardOrig.getMarker(row, col) == '~'){
                markMiss(row, col);

            }
        }
        opboard.print(false);
        playerboard.print(false);
        
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void placeShipLoop(Board playerBoard, BoardPrinterWrapper playerWrapper, PlaceShip placeIt) {
        PlaceAIShips.placeAI(playerBoard, playerWrapper, placeIt);
        this.BoardCopy = new Board(x, y, '~', BoardOrig.getNumberOfShips(), "AIMediumCopy");
        

    }
}