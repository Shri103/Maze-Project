import java.awt.Color;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
public class MazeElement
{
	private Location loc;
	private int size;
	private BufferedImage img;

	public MazeElement(Location loc, int size,String imgString)
	{
		this.loc=loc;
		this.size=size;
		try {
			img = ImageIO.read(new File(imgString));
		} catch (IOException e) {
				System.out.println("Image ->["+imgString+"] not loaded");
		}
	}

	public BufferedImage getImg()
	{
		return img;
	}
	public Location getLoc()
	{
		return loc;
	}

	public int getSize()
	{
		return size;
	}

	public boolean intersects(MazeElement other){

		return this.loc.equals(other.loc);
	}

	public void move(int key, char[][] maze) {} // DOES NOTHING BY DEFAULT. COMPLETE FOR MOVING ELEMENTS


}