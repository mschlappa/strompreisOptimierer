package de.awattar;

public class Lastgang {

	private Lastgang() {	
	}
	
	/**
	 * Anahme der Energiemenge die in der Stunde im Haus verbraucht wird (in Wh)
	 */
	private static final int[] LASTGANG = {
			500, // 0
			501, // 1
			502, // 2
			503, // 3
			504, // 4
			505, // 5
			506, // 6
			507, // 7
			508, // 8
			509, // 9
			510, // 10
			511, // 11
			512, // 12
			513, // 13
			514, // 14
			515, // 15
			516, // 16
			517, // 17
			518, // 18
			519, // 19
			520, // 20
			521, // 21
			522, // 22
			523, // 23			
			};
	
	public static int getEnergiemengeZurStunde(int stunde) {
		return LASTGANG[stunde];
	}
	
}
