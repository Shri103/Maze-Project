 import java.awt.Color;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.awt.image.*;
import javax.imageio.ImageIO;

public class Explorer extends MazeElement{

	private int dir;
	private int steps = 0;
	public int lives = 3;
	String [] explorerImgFiles;
	BufferedImage[] images;
	public int timeLeft = 0;
	//private static final String [] FILE_NAMES = {"C:\\Users\\shrih\\OneDrive\\Documents\\shaggynscooby.png", "C:\\Users\\shrih\\OneDrive\\Documents\\shaggynscooby.png", "C:\\Users\\shrih\\OneDrive\\Documents\\shaggynscooby.png", "C:\\Users\\shrih\\OneDrive\\Documents\\shaggynscooby.png"};
private static final String [] FILE_NAMES = {"shaggynscoobyup.png", "shaggynscoobyright.png", "shaggynscoobydown.png", "shaggynscoobyleft.png"};
	public Explorer(Location loc, int size, int dir){
		super(loc, size, "");
		this.dir = dir; //0-up, 1-right, 2-down, 3-left
		images = new BufferedImage[4];
		startCountdown();
		try{
			for(int i = 0; i < images.length; i++){
				images[i] = ImageIO.read(new File(FILE_NAMES[i]));
			}
		}catch(IOException e){
			System.out.println("Image not loaded");
		}
		}
		public BufferedImage getImg(){
			return images[dir];
		}
		public int getSteps(){
			return steps;
		}
		@Override
		public void move(int key, char[][]maze){
			if(key == 39){
				//right
				dir = 1;
				int r = getLoc().getR();
				int c = getLoc().getC();
				if(dir == 1){
					Location loc = getLoc();
				if(c + 1 < maze[r].length && maze[r][c + 1] != '#'){
					getLoc().incC(1);
					steps++;
					}

				}
			}else if(key == 37){
				//left
				dir = 3;
				int r = getLoc().getR();
				int c = getLoc().getC();
					Location loc = getLoc();
				if(c > 0 && maze[r][c - 1] != '#'){
					getLoc().incC(-1);
					steps++;
				}

				}
			else if(key == 38){
				//up
				dir = 0;
				int r = getLoc().getR();
				int c = getLoc().getC();
				if(dir == 0){
					Location loc = getLoc();
				if(r > 0 && maze[r - 1][c] != '#'){
					getLoc().incR(-1);
					steps++;
				}

				}
			}else if(key == 40){
				//down
				dir = 2;
				int r = getLoc().getR();
				int c = getLoc().getC();
				if(dir == 2){
					Location loc = getLoc();
				if(r > 0 && maze[r + 1][c] != '#'){
					getLoc().incR(1);
					steps++;
				}
				}
			}
		}
		public void startCountdown(){
				Thread tL = new Thread(new Runnable(){
				@Override
				public void run(){
					for(int i = 60; i >= 0; i--){
						timeLeft = i;
						try{
							Thread.sleep(1000);
						}catch(Exception e){
							e.printStackTrace();
						}
					}
					lives = 0;
				}
			});
				tL.start();
	}
	}
