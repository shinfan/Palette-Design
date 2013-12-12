public class Direction {
	public int vectors[][] = {
			{1, 0}, {0, 1}, {0, -1}, {-1, 0}
	};
	public double score;
	public int index;
	public Direction (double s, int d) {
		this.score = s;
		this.index = d;
	}	
	
	public Direction (int d) {
		this.score = 0;
		this.index = d;
	}	
	
	public int[] getVector() {
		return vectors[index];
	}
	
	public Direction reverse(){
		int d = 0;
		switch(index) {
		case(0):
			d = 3;
			break;
		case(3):
			d = 0;
			break;
		case(1):
			d = 2;
			break;
		case(2):
			d = 1;
			break;
		}
		
		return new Direction(d);
	}
	
	public void print() {
		System.out.println("index: " + index + " score:" + score);
	}
}