import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.awt.image.*;
import javax.imageio.ImageIO;
public class MazeProjectStarter extends JPanel implements KeyListener, ActionListener
{
	private JFrame frame;
	private int size = 30, width = 1500, height = 1000;
	private boolean is3d;
	private char[][] maze;
	private Timer t;
    private Explorer explorer;
    private UDB udb;
	private MazeElement finishElement; 
	private MazeElement keyElement;
	
	public MazeProjectStarter(String mazeString){
		//Maze variables
		setBoard(mazeString);
		frame=new JFrame("A-Mazing Program");
		is3d = false;
		frame.setSize(width,height);
		frame.add(this);
		frame.addKeyListener(this);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		t = new Timer(500, this);  // will trigger actionPerformed every 500 ms
		t.start();
	}

	// All Graphics handled in this method.  Don't do calculations here
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2=(Graphics2D)g;
		g2.setColor(Color.BLACK);
		g2.fillRect(0,0,frame.getWidth(),frame.getHeight());

		if(is3d){
			int size3d = 600;
			int backWall = 350;
			int ULC = 100;
			int LRC = ULC+size3d;
			int shrink = (size3d - backWall)/5;
			int howFar = 5;
			boolean changed = false;
			int r = explorer.getLoc().getR();
            int c = explorer.getLoc().getC();
			 if(explorer.getDir() == 1){
				for(int i = 5; i > 0; i--)
					if((c+i) < maze[i].length && maze[r][c+i] == '#'){
						howFar = i-1;
						changed = true;
					}
			 }
			  if(explorer.getDir() == 2){
				for(int i = 5; i > 0; i--)
					if((r+i) < maze.length && maze[r+i][c] == '#'){
						howFar = i-1;
						changed = true;
					}
			 }
			if(explorer.getDir() == 0){
				for(int i = 5; i > 0; i--)
					if((r-i) >= 0 && maze[r-i][c] == '#'){
						howFar = i-1;
						changed = true;
					}
			 }
			  if(explorer.getDir() == 3){
				for(int i = 5; i > 0; i--)
					if((c-i) >= 0 &&maze[r][c-i] == '#'){
						howFar = i-1;
						changed = true;
					}
			 }
			
			for(int n = 0; n < howFar; n++){
				int[] xLocs ={ULC+shrink*n,ULC+shrink*(n+1),ULC+shrink*(n+1),ULC+shrink*n};
				int[] yLocs = {ULC+shrink*n,ULC+shrink*(n+1),LRC-shrink*(n+1),LRC-shrink*n};
				int[] xLocsR ={LRC-shrink*n,LRC-shrink*(n+1),LRC-shrink*(n+1),LRC-shrink*n};
				Polygon leftWall = new Polygon(xLocs, yLocs, xLocs.length);
				Polygon aboveWall = new Polygon(yLocs, xLocs, xLocs.length);
				Polygon rightWall = new Polygon(xLocsR, yLocs, xLocsR.length);
				Polygon bottomWall = new Polygon(yLocs, xLocsR, xLocsR.length);
				//DRAWING LEFT WALL
				if(explorer.getDir() == 0 && maze[r-n-1][c-1] == ' ')
					g2.setColor(Color.green);
				else if(explorer.getDir() == 1 && maze[r-1][c+n+1] == ' ')
					g2.setColor(Color.green);
				else if(explorer.getDir() == 2 && maze[r+n+1][c+1] == ' ')
					g2.setColor(Color.green);
				else if(explorer.getDir() == 3 && maze[r+1][c-n-1] == ' ')
					g2.setColor(Color.green);
				else
					g2.setColor(Color.white);
				g2.fill(leftWall);
				g2.setColor(Color.BLACK);
				g2.draw(leftWall);
				//DRAWING RIGHT WALL
				if(explorer.getDir() == 0 && maze[r-n-1][c+1] == ' ')
					g2.setColor(Color.green);
				else if(explorer.getDir() == 1 && maze[r+1][c+n+1] == ' ')
					g2.setColor(Color.green);
				else if(explorer.getDir() == 2 && maze[r+n+1][c-1] == ' ')
					g2.setColor(Color.green);
				else if(explorer.getDir() == 3 && maze[r-1][c-n-1] == ' ')
					g2.setColor(Color.green);
				else
					g2.setColor(Color.white);
				g2.fill(rightWall);
				g2.setColor(Color.BLACK);
				g2.draw(rightWall);
				//Ceiling and floor
				g2.setColor(Color.white);
				g2.fill(aboveWall);
				g2.fill(bottomWall);
				g2.setColor(Color.BLACK);
				g2.draw(aboveWall);
				g2.draw(bottomWall);
			}
			
			
		}
		else{
		for(int r=0;r<maze.length;r++){
			for(int c=0;c<maze[0].length;c++){

				g2.setColor(Color.GRAY);
				if (maze[r][c]=='#')
					g2.fillRect(c*size+size,r*size+size,size,size); //Wall
				else
					g2.drawRect(c*size+size,r*size+size,size,size);  //Open
                
                Location here = new Location(r, c);
                if(here.equals(explorer.getLoc()))
                    g2.drawImage(explorer.getImg(), c*size+size, r*size+size, size,size,null);
                if(here.equals(udb.getLoc()))
                    g2.drawImage(udb.getImg(), c*size+size, r*size+size, size,size,null);
				if(here.equals(finishElement.getLoc()))
					 g2.drawImage(finishElement.getImg(), c*size+size, r*size+size, size,size,null);
				if(here.equals(keyElement.getLoc()) && !explorer.getKey())
					 g2.drawImage(keyElement.getImg(), c*size+size, r*size+size, size,size,null);
                      

		}

		// Display at bottom of page
		int hor = size;
		int vert = maze.length*size+ 2*size;
		g2.setFont(new Font("Arial",Font.BOLD,20));
		g2.setColor(Color.PINK);
		g2.drawString("Steps Taken:  " + explorer.getSteps(),hor,vert);
		if(explorer.getKey())
			g2.drawString("KEY OBTAINED", hor, vert+100);
		if(explorer.intersects(finishElement) && explorer.getKey()){
			g2.drawString("FINISHED", hor, vert+200);
			setBoard("maze1.txt");
		}
		}
	}
}

	public void keyPressed(KeyEvent e){
		System.out.println(e.getKeyCode());
		// Call explorer method
		if(e.getKeyCode() == 32)
			is3d = !is3d;
		explorer.move(e.getKeyCode(), maze);
		System.out.println(e.getKeyCode());
		repaint();
	}

	/*** empty methods needed for interfaces **/
	public void keyReleased(KeyEvent e){}
	public void keyTyped(KeyEvent e){}
	public void actionPerformed(ActionEvent e) {
		if(this.udb != null)
       	 	udb.move(0, maze);
		
		if(explorer.intersects(keyElement))
			explorer.keyChange();
        repaint();
    }

	public void setBoard(String fileName){
	
        try{
			File file = new File(fileName);
			BufferedReader br = new BufferedReader(new FileReader(file));
            ArrayList<String> rowHolder = new ArrayList<String>();
			String line="";
            boolean equalCols = true;
			while((line=br.readLine())!= null) 
			{
				
				rowHolder.add(line);
			}
            for(int i = 1; i < rowHolder.size(); i++)
                if(rowHolder.get(0).length() != rowHolder.get(i).length())
                    equalCols = false;
            if(equalCols){
                char[][] temp = new char[rowHolder.size()][rowHolder.get(0).length()];
                for(int i = 0; i < rowHolder.size(); i++)
                    for(int j = 0; j < rowHolder.get(i).length(); j++){
                        temp[i][j] = rowHolder.get(i).charAt(j);
                        if(temp[i][j] == 'E')
                            explorer = new Explorer(new Location(i, j), 1);
                        if(temp[i][j] == 'U')
                            udb = new UDB(new Location(i, j), 1,"downwolf.png");
						if(temp[i][j] == 'F')
							finishElement = new MazeElement(new Location(i,j), 1, "pond.png");
						if(temp[i][j] == 'K')
							keyElement = new MazeElement(new Location(i,j), 1, "bread.png");
							
                    }
                
                maze = temp;
            }
            else
                System.out.println("Error in maze");

		}
			catch (IOException io){
				System.err.println("File Error: "+io);
		}
	}

	public static void main(String[] args){
		MazeProjectStarter app = new MazeProjectStarter("maze0.txt");
	}
}