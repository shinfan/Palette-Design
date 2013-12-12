
public class Setting {
	public int hueWeight = 5;
	public int disWeight = 1;
	public int step = 99;
	public int trendBonus = 20;
	public boolean spaceCheck = false;
	public boolean shortBranch = false;
	public Setting (int d, int h, int s, int t, boolean sc, boolean sb) {
		hueWeight = h;
		disWeight = d;
		step = s;
		trendBonus = t;
		spaceCheck = sc;
		shortBranch = sb;
	}
}
