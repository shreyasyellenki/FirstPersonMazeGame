import java.awt.Color;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
public class Explorer extends MazeElement
{
	
	private int dir, steps;
	private BufferedImage img;
    private boolean keyObtained;
    private BufferedImage[] images;
    private static final String[] FILE_NAMES = {"upduck.png","rightduck.png","downduck.png","leftduck.png"};
	public Explorer(Location loc, int size)
	{
		super(loc,size,"");
        //this.loc=loc;
		//this.size=size;
        images = new BufferedImage[4];
        dir = 1; //0-up,1-right,2-down,3-left
        steps = 0;
        keyObtained = false;
		try {
			//img = ImageIO.read(new File(imgString));
            // Loop through FILE_NAMES and load image for each
            for(int i = 0; i < images.length;i++)
                images[i] = ImageIO.read(new File(FILE_NAMES[i]));
		} catch (IOException e) {
				System.out.println("Image not loaded");
		}
	}

	public BufferedImage getImg()
	{
		return images[dir];
	}

	public void move(int key, char[][] maze) {

        if(key == 38){
            
            int r = getLoc().getR();
            int c = getLoc().getC();
            if(dir == 1){
                if( c+1 < maze[r].length&& (maze[r][c+1] == ' ' || (maze[r][c+1] == 'F' && this.keyObtained) || maze[r][c+1] == 'F')){
                maze[r][c+1] = 'E';
                maze[r][c] = ' ';
                getLoc().incC(1);
                 steps++;
                }
                   
            }
            if(dir == 2){
                if( r+1 < maze.length&& (maze[r+1][c] == ' '|| (maze[r+1][c] == 'F' && this.keyObtained)|| maze[r+1][c] == 'K')){
                    maze[r+1][c] = 'E';
                    maze[r][c] = ' ';
                    getLoc().incR(1);
                     steps++;
                }
            }
            if(dir == 3){
                if( c-1 < maze[r].length&& (maze[r][c-1] == ' '|| (maze[r][c-1] == 'F' && this.keyObtained) || maze[r][c-1] == 'K')){
                    maze[r][c-1] = 'E';
                    maze[r][c] = ' ';
                    getLoc().incC(-1);
                     steps++;
                }
                     
                   
            }
            if(dir == 0){
                if( r-1 < maze.length&& (maze[r-1][c] == ' '|| (maze[r-1][c] == 'F' && this.keyObtained)|| maze[r-1][c] == 'K')){
                 maze[r-1][c] = 'E';
                 maze[r][c] = ' ';
                getLoc().incR(-1);
                 steps++;
                }
                     
            }
           
        }
        else if(key == 37)
            if(dir > 0)
                dir -= 1;
            else
                dir = 3;
        else if(key == 39)
            if(dir < 3)
                dir += 1;
            else
                dir = 0;
        
           
    }
    public int getSteps(){return steps;}
    public int getDir(){return dir;} 
    public void keyChange(){
        keyObtained = true;
    }
    public boolean getKey(){return keyObtained;}
    // DOES NOTHING BY DEFAULT. COMPLETE FOR MOVING ELEMENTS
    

}
