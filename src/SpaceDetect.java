
public class SpaceDetect {
	static int spaceBonus = 10;
	static int spacePenalty = 20;
	static int upperBound = 10;
	static int lowerBound = 5;

	public static int spaceLeft(Position p, Direction d, Board b) {
		int count = 0;
		p = p.move(d);
		while(!b.filled(p)) {
			p = p.move(d);
			count += 1;
			if (count > upperBound) return upperBound;
		}
		return count;
	}
	
	public static int[] getSpace(Position p, Board b) {
		int[] res = new int[4];
		for (int i = 0; i < 4; i++) {
			Direction d = new Direction(i);
			res[i] = spaceLeft(p, d, b);
		}
		return res;
	}
}
