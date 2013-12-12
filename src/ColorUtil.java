import java.awt.Color;


public class ColorUtil {
	public static double colorDiff(Color c1, Color c2) {
		double[] rgb1 = {c1.getRed(), c1.getGreen(), c1.getBlue()};
		double[] rgb2 = {c2.getRed(), c2.getGreen(), c2.getBlue()};
		double[] cp1 = ColorConverter.convert("RGB", "CIELab", rgb1);
		double[] cp2 = ColorConverter.convert("RGB", "CIELab", rgb2);

		return Math.sqrt((cp1[0] - cp2[0]) * (cp1[0]-cp2[0]) + 
				(cp1[1] - cp2[1]) * (cp1[1]-cp2[1])+ 
				(cp1[2] - cp2[2]) * (cp1[2]-cp2[2]));
	}
	
	public static double getHue(Color c) {
		double[] rgb = {c.getRed(), c.getGreen(), c.getBlue()};
		double[] cp = ColorConverter.convert("RGB", "HSV", rgb);
		return cp[0];
	}
	
	public static double getSaturation(Color c) {
		double[] rgb = {c.getRed(), c.getGreen(), c.getBlue()};
		double[] cp = ColorConverter.convert("RGB", "HSV", rgb);
		return cp[1];
	}
	
	public static boolean isSimilarHue(Color c1, Color c2, double range) {		
		return hueDiff(c1, c2) <= range;
	}
	
	
	public static double getBrightness(Vertex v) {
		int r, g, b;
		Color c = v.color;
		r = c.getRed();
		g = c.getGreen();
		b = c.getBlue();
		return r*r + g*g + b*b;
	}
	
	public static Color[] getGradient(Color c1, Color c2, int n) {
    	Color[] res = new Color[n];
    	res[0] = c2;
    	res[n-1] = c1;
    	double[] rgb1 = {c1.getRed(), c1.getGreen(), c1.getBlue()};
		double[] rgb2 = {c2.getRed(), c2.getGreen(), c2.getBlue()};
		double[] cp1 = ColorConverter.convert("RGB", "CIELab", rgb1);
		double[] cp2 = ColorConverter.convert("RGB", "CIELab", rgb2);

		double[] length = {(cp1[0]-cp2[0])/n,(cp1[1]-cp2[1])/n,(cp1[2]-cp2[2])/n};
		
		for (int i = 1; i < n-1; i++) {
			double[] cp = {cp2[0]+ i * length[0],cp2[1]+ i * length[1],cp2[2]+ i * length[2]};
			double[] rgb = ColorConverter.convert("CIELab", "RGB", cp);
			Color c = new Color((float)rgb[0], (float)rgb[1], (float)rgb[2]);
			res[i] = c;
		}
		return res;
	}
	
	public static double hueDiff(Color c1, Color c2) {
		double h1 = getHue(c1);
		double h2 = getHue(c2);
		double d1 = Math.abs(h1-h2);
		double large = Math.max(h1, h2);
		double small = Math.min(h1, h2);
		double d2 = (1 - large) + small;
		return Math.min(d1, d2);
	}
	
}
