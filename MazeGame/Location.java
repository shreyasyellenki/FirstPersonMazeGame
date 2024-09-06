public class Location {
    private int row;
    private int col;

    public Location(int r, int c){
        row = r;
        col = c;
    }

    public int getR(){
        return row;
    }

    public int getC(){
        return col;
    }

    public void incR(int x){
        row += x;
    }

    public void incC(int x){
        col += x;
    }

    public void set(int newR, int newC){
        row = newR;
        col = newC;
    }

    public boolean equals(Location other){
            if(this.row == other.getR() && this.col == other.getC())
                return true;
            else
                return false;
    }

    public boolean equals(int r, int c){
        if(row == r && col == c)
            return true;
        else
            return false;
    }

    public String toString(){
        return "(" + row + "," + col + ")";
    }
}
