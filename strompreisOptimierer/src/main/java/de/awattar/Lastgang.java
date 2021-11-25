package de.awattar;

public class Lastgang {

	private Lastgang() {	
	}
	
	/**
	 * Anahme der Energiemenge die in der Stunde im Haus verbraucht wird (in Wh)
	 */
	private static final int[] LASTGANG = {
			500, // 0
			500, // 1
			500, // 2
			500, // 3
			500, // 4
			500, // 5
			646, // 6
			540, // 7
			426, // 8
			246, // 9
			107, // 10
			21, // 11
			185, // 12
			286, // 13
			246, // 14
			132, // 15
			173, // 16
			329, // 17
			401, // 18
			431, // 19
			360, // 20
			468, // 21
			468, // 22
			499, // 23			
			};
	
	public static int getEnergiemengeZurStunde(int stunde) {
		return LASTGANG[stunde];
	}
	
}
