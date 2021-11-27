package de.awattar;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Lastgang {

	private static Properties properties = new Properties();

	private static Lastgang instanz = new Lastgang();

	private static String workDir = StrompreisOptimierer.WORK_DIR;
	
	private Lastgang() {
	}

	public static void init() {

		String dateiPfad = getWorkDir() + "lastgang.properties";

		try {
			properties.load(new FileInputStream(dateiPfad));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static int getEnergiemengeZurStunde(int stunde) {
		return Integer.valueOf(instanz.properties.getProperty(Integer.toString(stunde))).intValue();
	}

	public static String getWorkDir() {
		return workDir;
	}

	public static void setWorkDir(String workDir) {
		Lastgang.workDir = workDir;
	}

}
