public class ColorConverter {

	public static double[] convert(String from, String to, double[] input) {
		if(from == to) return input;
		// CIELab --> XYZ --> RGB --> HSV
		else if(from=="CIELab") {
			if(to=="XYZ") return CIELab2XYZ(input);
			else if(to=="RGB") return XYZ2RGB(convert(from, "XYZ", input));
			else if(to=="HSV") 	return RGB2HSV(convert(from, "RGB", input));			
		}
		else if(from=="XYZ") {
			if(to=="CIELab") return XYZ2CIELab(input);
			else if(to=="RGB") return XYZ2RGB(input);
			else if(to=="HSV") 	return RGB2HSV(convert(from, "RGB", input));
		}
		else if(from=="RGB") {
			if(to=="CIELab") return XYZ2CIELab(convert(from, "XYZ", input));
			else if(to=="XYZ") return RGB2XYZ(input);
			else if(to=="HSV") 	return RGB2HSV(input);
		}
		else if(from=="HSV") {
			if(to=="CIELab") return XYZ2CIELab(convert(from, "XYZ", input));
			else if(to=="XYZ") return RGB2XYZ(convert(from, "RGB", input));
			else if(to=="RGB") 	return HSV2RGB(input);
		}
		assert(false);
		return null;
	}
	
	// RGB from 0 to 255
	// X from 0 to 95.047
	// Y from 0 to 100.000
	// Z from 0 to 108.883
	private static double[] RGB2XYZ(double[] RGB) {
		double R = RGB[0], G = RGB[1], B = RGB[2];
		double var_R = R / 255.0;        //R from 0 to 255
		double var_G = G / 255.0;        //G from 0 to 255
		double var_B = B / 255.0;        //B from 0 to 255

		if ( var_R > 0.04045 ) var_R = Math.pow((var_R + 0.055)/1.055, 2.4);
		else                   var_R /= 12.92;
		if ( var_G > 0.04045 ) var_G = Math.pow((var_G + 0.055)/1.055, 2.4);
		else                   var_G /= 12.92;
		if ( var_B > 0.04045 ) var_B = Math.pow((var_B + 0.055)/1.055, 2.4);
		else                   var_B /= 12.92;
		var_R *= 100.0;
		var_G *= 100.0;
		var_B *= 100.0;
		//Observer. = 2°, Illuminant = D65
		double X = var_R * 0.4124 + var_G * 0.3576 + var_B * 0.1805;
		double Y = var_R * 0.2126 + var_G * 0.7152 + var_B * 0.0722;
		double Z = var_R * 0.0193 + var_G * 0.1192 + var_B * 0.9505;
		double[] XYZ = {X, Y, Z};
		return XYZ;
	}
	
	
	static double truncateToInterval (double x) {
		if (x > 1) {
			return 1.0;
		} else if (x < 0) {
			return 0.0;
		} else {
			return x;
		}
	}
	
	private static double[] XYZ2RGB(double[] XYZ) {
		double X = XYZ[0], Y = XYZ[1], Z = XYZ[2];
		double var_X = X / 100.0;        //X from 0 to  95.047      (Observer = 2°, Illuminant = D65)
		double var_Y = Y / 100.0;        //Y from 0 to 100.000
		double var_Z = Z / 100.0;        //Z from 0 to 108.883

		double var_R = var_X *  3.2406 + var_Y * -1.5372 + var_Z * -0.4986;
		double var_G = var_X * -0.9689 + var_Y *  1.8758 + var_Z *  0.0415;
		double var_B = var_X *  0.0557 + var_Y * -0.2040 + var_Z *  1.0570;

		if ( var_R > 0.0031308 ) var_R = 1.055 * Math.pow(var_R, 1.0/2.4) - 0.055;
		else                     var_R = 12.92 * var_R;
		if ( var_G > 0.0031308 ) var_G = 1.055 * Math.pow(var_G, 1.0/2.4) - 0.055;
		else                     var_G = 12.92 * var_G;
		if ( var_B > 0.0031308 ) var_B = 1.055 * Math.pow(var_B, 1.0/2.4) - 0.055;
		else                     var_B = 12.92 * var_B;

		double R = truncateToInterval(var_R);
		double G = truncateToInterval(var_G);
		double B = truncateToInterval(var_B);
		
		double[] RGB = {R, G, B};
		return RGB;
		//System.out.println("FIXME");
		//return null;
	}
	
	// X from 0 to 95.047
	// Y from 0 to 100.000
	// Z from 0 to 108.883
	private static double[] XYZ2CIELab(double[] XYZ) {
		double X = XYZ[0], Y = XYZ[1], Z = XYZ[2];
		double ref_X = 95.047, ref_Y = 100.000, ref_Z = 108.83;
		double var_X = X / ref_X;          //ref_X =  95.047   Observer= 2°, Illuminant= D65
		double var_Y = Y / ref_Y;          //ref_Y = 100.000
		double var_Z = Z / ref_Z;          //ref_Z = 108.883

		// 16/116 to 1
		if ( var_X > 0.008856 ) var_X = Math.pow(var_X, 1.0/3.0); // 0.207 to 1
		else                    var_X = ( 7.787 * var_X ) + ( 16.0 / 116.0 ); // 16/116 to 0.207
		if ( var_Y > 0.008856 ) var_Y = Math.pow(var_Y, 1.0/3.0);
		else                    var_Y = ( 7.787 * var_Y ) + ( 16.0 / 116.0 );
		if ( var_Z > 0.008856 ) var_Z = Math.pow(var_Z, 1.0/3.0);
		else                    var_Z = ( 7.787 * var_Z ) + ( 16.0 / 116.0 );

		double CIE_L = ( 116.0 * var_Y ) - 16.0; // 0 to 100
		double CIE_a = 500.0 * ( var_X - var_Y ); // -431.03 to 431.03
		double CIE_b = 200.0 * ( var_Y - var_Z ); // -172.41 to 172.41
		double[] CIE_Lab = {CIE_L, CIE_a, CIE_b};
		return CIE_Lab;
	}
	
	private static double[] CIELab2XYZ(double[] LAB) {
		double CIE_L= LAB[0], CIE_a = LAB[1], CIE_b = LAB[2];
		double var_Y = ( CIE_L + 16 ) / 116.0;
		double var_X = CIE_a / 500.0 + var_Y;
		double var_Z = var_Y - CIE_b / 200.0;

		if ( Math.pow(var_Y,3.0) > 0.008856 ) var_Y = Math.pow(var_Y,3.0);
		else var_Y = ( var_Y - 16.0 / 116.0 ) / 7.787;
		if ( Math.pow(var_X,3.0) > 0.008856 ) var_X = Math.pow(var_X,3.0);
		else var_X = ( var_X - 16.0 / 116.0 ) / 7.787;
		if ( Math.pow(var_Z,3.0) > 0.008856 ) var_Z = Math.pow(var_Z,3.0);
		else var_Z = ( var_Z - 16.0 / 116.0 ) / 7.787;

		double ref_X = 95.047, ref_Y = 100.000, ref_Z = 108.883;
		double X = ref_X * var_X;     //ref_X =  95.047     Observer= 2°, Illuminant= D65
		double Y = ref_Y * var_Y;     //ref_Y = 100.000
		double Z = ref_Z * var_Z;     //ref_Z = 108.883
		double[] XYZ = {X, Y, Z};
		return XYZ;
	}
	
	private static double[] RGB2HSV(double[] RGB) {
		double R = RGB[0], G = RGB[1], B = RGB[2];
		double var_R = ( R / 255.0 );                    //RGB from 0 to 255
		double var_G = ( G / 255.0 );
		double var_B = ( B / 255.0 );
		double var_Min = Math.min(Math.min(var_R, var_G), var_B);    //Min. value of RGB
		double var_Max = Math.max(Math.max(var_R, var_G), var_B);    //Max. value of RGB
		double del_Max = var_Max - var_Min;             //Delta RGB value 
		double H = 0, S = 0, V = var_Max;
		if ( del_Max == 0 ) {                     //This is a gray, no chroma...
			H = 0; S = 0;                                //HSV results from 0 to 1
		}
		else {                                   //Chromatic data...
		   S = del_Max / var_Max;
		   double del_R = ( ( ( var_Max - var_R ) / 6.0 ) + ( del_Max / 2.0 ) ) / del_Max;
		   double del_G = ( ( ( var_Max - var_G ) / 6.0 ) + ( del_Max / 2.0 ) ) / del_Max;
		   double del_B = ( ( ( var_Max - var_B ) / 6.0 ) + ( del_Max / 2.0 ) ) / del_Max;
		   if 	   ( var_R == var_Max ) H = del_B - del_G;
		   else if ( var_G == var_Max ) H = ( 1.0 / 3.0 ) + del_R - del_B;
		   else if ( var_B == var_Max ) H = ( 2.0 / 3.0 ) + del_G - del_R;
		   if(R==255 && G==12) System.out.println(del_Max);
		   if ( H < 0 ) H += 1.0;
		   if ( H > 1 ) H -= 1.0;
		}
		double[] HSV = {H,S,V};
		return HSV;
	}
	
	private static double[] HSV2RGB(double[] HSV) {
		double H = HSV[0], S = HSV[1], V = HSV[2];
		double R = 0, G = 0, B = 0;
		if ( S == 0 ) {                      //HSV from 0 to 1
		   R = V * 255.0; G = V * 255.0; B = V * 255.0;
		}
		else {
		   double var_h = H * 6.0;
		   if ( var_h == 6 ) var_h = 0;      //H must be < 1
		   double var_i = Math.floor( var_h );             //Or ... var_i = floor( var_h )
		   double var_1 = V * ( 1.0 - S );
		   double var_2 = V * ( 1.0 - S * ( var_h - var_i ) );
		   double var_3 = V * ( 1.0 - S * ( 1.0 - ( var_h - var_i ) ) );
		   double var_r = 0, var_g = 0, var_b = 0;
		   if      ( var_i == 0 ) { var_r = V     ; var_g = var_3 ; var_b = var_1; }
		   else if ( var_i == 1 ) { var_r = var_2 ; var_g = V     ; var_b = var_1; }
		   else if ( var_i == 2 ) { var_r = var_1 ; var_g = V     ; var_b = var_3; }
		   else if ( var_i == 3 ) { var_r = var_1 ; var_g = var_2 ; var_b = V;     }
		   else if ( var_i == 4 ) { var_r = var_3 ; var_g = var_1 ; var_b = V;     }
		   else                   { var_r = V     ; var_g = var_1 ; var_b = var_2; }
		   R = var_r * 255.0;                  //RGB results from 0 to 255
		   G = var_g * 255.0;
		   B = var_b * 255.0;
		}
		double[] RGB = {R,G,B};
		return RGB;
	}
}
