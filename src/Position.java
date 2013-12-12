
public class Position {
	int x, y;
	int index_x, index_y;
	public Position (int x, int y) {
		this.x = x;
		this.y = y;
		this.index_x = x / Draw.size;
		this.index_y = y / Draw.size;
	}
	
	public Position move (Direction d) {
		int[] v = d.getVector();
		return new Position(x + v[0]*Draw.size, y + v[1]*Draw.size);
	}
	
	public void print() {
		System.out.println(index_x+" " +index_y);
	}
}
