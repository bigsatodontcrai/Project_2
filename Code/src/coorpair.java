public class coorpair {
    private int row;
    private int col;

    public coorpair(int theRows, int theCols){
        this.row = theRows;
        this.col = theCols;
    }

    public boolean comparisonEquals(coorpair cor) {
        return (cor.row == this.row && cor.col == this.col);
    }
    
}