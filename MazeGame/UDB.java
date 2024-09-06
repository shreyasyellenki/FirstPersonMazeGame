public class UDB extends MazeElement {

    private boolean goUp;
    public UDB(Location loc, int size, String imgString){
        super(loc, size, imgString);
        goUp = true;
    }
    @Override
    public void move(int key,char[][] maze){
        int r = getLoc().getR();
        int c = getLoc().getC();
        if(goUp){
            if(r > 0 && maze[r-1][c] == ' '){
                maze[r][c] = ' ';
                maze[r-1][c] = 'U';
                super.getLoc().incR(-1);
               
            }
            else
                goUp = false;
         }
         else{
            if(r < maze.length-1 && maze[r+1][c] == ' '){
                 maze[r+1][c] = 'U';
                 maze[r][c] = ' ';
                super.getLoc().incR(1);
               
            }
            else    
                goUp = true;

         }
    }
}
