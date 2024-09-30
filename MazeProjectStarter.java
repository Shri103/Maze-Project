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
	public int level = 0;
	private int size = 30, width = 1500, height = 1000;
	private char[][] maze;
	private Timer t;
	private boolean is3d = false;
	private MazeElement finishLine;
	private IceCube freeze;
	private UDB udb;
	private Explorer explorer;
	private MazeElement finish;
	private boolean played = false;

	public MazeProjectStarter(){
		//Maze variables
		levelSelect(level);
		frame=new JFrame("A-Mazing Program");
		frame.setSize(width,height);
		frame.add(this);
		frame.addKeyListener(this);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		t = new Timer(500, this);  // will trigger actionPerformed every 500 ms
		t.start();
	}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2=(Graphics2D)g;
		g2.setColor(Color.BLACK);
		g2.fillRect(0,0,frame.getWidth(),frame.getHeight());

		if(is3d){
			int size3D = 600, backWall = 350;
			int ULC = 100, LRC = ULC + size3D;
			int shrink = (size3D - backWall)/5;

			//left wall
			for(int n = 0; n < 5; n++){
				int[] xLocsLeft = {ULC + shrink * n, ULC + shrink * (n + 1), ULC + shrink * (n + 1), ULC + shrink * n};
				int[] yLocsLeft = {ULC + shrink * n, ULC + shrink * (n + 1), LRC - shrink * (n + 1), LRC - shrink * n};
				int[] xLocsRight = {LRC - shrink * n, LRC - shrink * (n+1), LRC - shrink * (n+1), LRC - shrink * n};
 				int[] yLocsRight = {LRC - shrink * n, LRC - shrink * (n+1), ULC + shrink * (n+1), ULC + shrink * n};
				Polygon leftWall = new Polygon(xLocsLeft, yLocsLeft, xLocsLeft.length);
				Polygon ceiling = new Polygon(yLocsLeft, xLocsLeft, xLocsLeft.length);
				Polygon rightWall = new Polygon(xLocsRight, yLocsRight, xLocsRight.length);
				Polygon floor = new Polygon(yLocsRight, xLocsRight, xLocsRight.length);
				g2.setColor(Color.WHITE);
				g2.fill(leftWall);
				g2.fill(ceiling);
				g2.fill(rightWall);
				g2.fill(floor);
				g2.setColor(Color.BLACK);
				g2.draw(leftWall);
				g2.draw(ceiling);
				g2.draw(rightWall);
				g2.draw(floor);
				}
			}else{
				for(int r=0;r<maze.length;r++){
					for(int c=0;c<maze[0].length;c++){
						g2.setColor(Color.GREEN);
						if (maze[r][c]=='#'){
							g2.fillRect(c*size+size,r*size+size,size,size); //Wall
						}else{
							g2.drawRect(c*size+size,r*size+size,size,size);  //Open
						}
							Location location = new Location(r, c);
						if(location.equals(finishLine.getLoc())){
							g2.drawImage(finishLine.getImg(), c * size + size, r * size + size , size, size, null, this);
						}
						if(location.equals(explorer.getLoc())){
							g2.drawImage(explorer.getImg(), c * size + size, r * size + size , size, size, null, this);
						}
						if(location.equals(udb.getLoc())){
							g2.drawImage(udb.getImg(), c * size + size, r * size + size , size, size, null, this);
						}
						if(location.equals(freeze.getLoc())){
							g2.drawImage(freeze.getImg(), c * size + size, r * size + size , size, size, null, this);
						}
					}
				}
				//Display at bottom of page
				int hor = size;
				int vert = maze.length*size+ 2*size;
				g2.setFont(new Font("Arial",Font.BOLD,20));
				g2.setColor(Color.PINK);
				g2.drawString("Steps: " + explorer.getSteps(),hor,vert);
				g2.setColor(Color.YELLOW);
				g2.drawString("Lives: " + explorer.lives,hor,vert + 50);
				//Countdown timer to beat the level
				g2.setColor(Color.RED);
				g2.drawString("You have " + explorer.timeLeft + " seconds to beat the level!",hor,vert + 150);
				if(level == 0){
					g2.setColor(Color.BLUE);
					g2.drawString("Collect all the scooby snacks! Good luck!!!", hor, vert + 100);
				}else if(level == 1){
					g2.setColor(Color.BLUE);
					g2.drawString("Nice job on Level 1! Try this!",hor,vert + 100);
				}else{
					g2.setColor(Color.BLUE);
					g2.drawString("It's the final level. Make your lives count!",hor,vert + 100);
				}

			}
	}
	public void keyPressed(KeyEvent e){
		System.out.println(e.getKeyCode());
		explorer.move(e.getKeyCode(), maze);
		if(e.getKeyCode() == 32){
			is3d = !is3d;
		}
		if(explorer.intersects(finishLine)){
			System.out.println("Finished");
			level += 1;
			levelSelect(level);
		}
		repaint();
}

	/*** empty methods needed for interfaces **/
	public void keyReleased(KeyEvent e){}
	public void keyTyped(KeyEvent e){}
	public void actionPerformed(ActionEvent e) {
		if(explorer.intersects(udb)){
			explorer.lives--;
		}
		if(explorer.intersects(freeze)){
			udb.freezeUDB();
		}
		//resets the game when the user has lost all of their lives
		if(explorer.lives == 0){
			level = 0;
			levelSelect(0);
		}
		udb.move(0, maze);
		repaint();
		}

	public void setBoard(String fileName){
		ArrayList<String> lineReader = new ArrayList<String>();
		try{
			File file = new File(fileName);
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line="";
				while((line=br.readLine())!= null){
					lineReader.add(line);
				}
			}catch (IOException io){
				System.err.println("File Error: "+io);
			}
			char [][] temp = new char [lineReader.size()][lineReader.get(0).length()];
			for(int i = 0; i < lineReader.size(); i++){
				for(int j = 0; j < lineReader.get(i).length(); j++){
					temp[i][j] = lineReader.get(i).charAt(j);
					//detects the different spawn locations and spawns the respective items there
					if(temp[i][j] == '1'){
						freeze = new IceCube(new Location(i, j), size, "icecube.png");
						temp[i][j] = ' ';
						freeze.spots.add(new Location(i, j));
					}else if(temp[i][j] == 'F'){
						finishLine = new MazeElement(new Location(i, j), size, "scoobysnack.png");
						temp[i][j] = ' ';
					}else if(temp[i][j] == 'E'){
						System.out.println("In Explorer");
						explorer = new Explorer(new Location(i, j), size, 1);
						temp[i][j] = ' ';
					}else if(temp[i][j] == 'U'){
						udb = new UDB(new Location(i, j), size, "mazeMonster.png");
						temp[i][j] = ' ';
					}else if(temp[i][j] == 'P'){
						freeze.spots.add(new Location(i, j));
					}
				}
			}
		maze = temp;
	}
	void levelSelect(int l){
		if(l == 0){
			setBoard("maze0.txt");
		}else if(l == 1){
			setBoard("maze1.txt");
		}else if(l == 2){
			setBoard("maze2.txt");
		}
	}
	public static void main(String[] args){
		MazeProjectStarter app = new MazeProjectStarter();
	}
}
