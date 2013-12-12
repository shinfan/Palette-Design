
public class Board {
	public Vertex[][] board;
	static final public int BOARD_SIZE = 150;
	
	
	public Board() {
		board =  new Vertex[BOARD_SIZE][BOARD_SIZE];
	}
	
	public boolean isValidPos (int i, int j) {
		return inBound(i, j) && board[i][j] == null;
	}
	
	public boolean isValidPos (Position p) {
		return inBound(p.index_x, p.index_y) && !filled(p);
	}
	
	public boolean filled (Position p) {
		return inBound(p.index_x, p.index_y) && board[p.index_x][p.index_y] != null;
	}
	
	static public boolean inBound(int i, int j) {
		return i > 0 && j > 0 && i < BOARD_SIZE && j < BOARD_SIZE;
	}
	
	public void set(Vertex v, int x, int y) {
		board[x/Draw.size][y/Draw.size] = v;
	}
	
	public void set(Vertex v, Position p) {
		board[p.index_x][p.index_y] = v;
	}
	
	
	public Vertex get(int x, int y) {
		return board[x/Draw.size][y/Draw.size];
	}
	
	public Vertex get(Position p) {
		return board[p.index_x][p.index_y];
	}
	
	public void remove(Position p) {
		board[p.index_x][p.index_y] = null;
	}
	public void reset() {
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				board[i][j] = null;
			}	
		}
	}
	
	public Vertex[] getNeighbours(Position p) {
		Vertex[] res = new Vertex[4];
		for (int i = 0; i < 4; i++) {
			Direction d = new Direction(i);
			res[i] = get(p.move(d));
		}
		return res;
	}	
	public int countNeighbours(Position p) {
		Vertex[] nbrs = this.getNeighbours(p);
		int count = 0;
		for (int i = 0; i < 4; i++) {
			if (nbrs[i] != null) count += 1;
		}
		return count;
	}
}
