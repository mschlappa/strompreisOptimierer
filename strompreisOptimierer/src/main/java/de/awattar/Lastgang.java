package de.awattar;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Lastgang {

	Properties properties = new Properties();

	private static Lastgang instanz = new Lastgang();

	private Lastgang() {
		loadProperties();
	}

	private void loadProperties() {

		String dateiPfad = StrompreisOptimierer.WORK_DIR + "lastgang.properties";

		try {
			properties.load(new FileInputStream(dateiPfad));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static int getEnergiemengeZurStunde(int stunde) {
		return Integer.valueOf(instanz.properties.getProperty(Integer.toString(stunde))).intValue();
	}

}
