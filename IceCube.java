import java.util.ArrayList;

public class IceCube extends MazeElement{

	private boolean stayAlive = true;
	private int index = 0;
	public boolean enabled = true;
	public ArrayList<Location> spots = new ArrayList<Location>();

	public IceCube(Location loc, int size, String imgString){
		super(loc, size, imgString);
		startTimer();
	}
	public void startTimer(){
		Thread timer = new Thread(new Runnable(){
		@Override
		public void run(){
			for(int i = 5; i >= 0; i--){
				System.out.println("Frozen for: " + i + "\n");
				try{
					Thread.sleep(1000);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			move();
		}
	});
		timer.start();
	}
	public void move(){
			if(index != 3){
				index++;
			}else{
				index = 0;
			}
			getLoc().set(spots.get(index).getR(), spots.get(index).getC());
			startTimer();
		}

	}

