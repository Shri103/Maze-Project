import java.awt.*;

public class UDB extends MazeElement{

	public boolean goUp, frozen;

	public UDB(Location loc, int size, String imgString){
		super(loc, size, imgString);
		goUp = true;
	}

	@Override
	public void move(int key, char[][]maze){
		int r = getLoc().getR();
		int c = getLoc().getC();
		if(!frozen){
			if(goUp && r > 0 && maze[r - 1][c] == ' '){
				getLoc().incR(-1);
			}else if(!goUp && r > 0 && maze[r + 1][c] == ' '){
				getLoc().incR(1);
			}
			if(maze[r + 1][c] == '#'){
				goUp = true;
			}
			if(maze[r - 1][c] == '#'){
				goUp = false;
			}
		}
	}
	public void freezeUDB(){
		Thread freezeTimer = new Thread(new Runnable(){
		@Override
		public void run(){
			for(int i = 5; i >= 0; i--){
				System.out.println(i + " ");
				frozen = true;
				try{
					Thread.sleep(1000);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			frozen = false;
				}
			});
		freezeTimer.start();
	}
}
