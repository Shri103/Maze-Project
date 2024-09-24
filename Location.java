public class Location{
	int row, col;
	public Location(int x, int y){
		row = x;
		col = y;
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
	return (this.row == other.row && this.col == other.col);
}
}
