package de.awattar;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Properties;


public class PropertiesHelper {

	Properties properties = new Properties();
	
	private static PropertiesHelper instanz = new PropertiesHelper();
	
	private PropertiesHelper() {
		loadProperties();
	}

	private void loadProperties() {
		
		String dateiPfad = StrompreisOptimierer.WORK_DIR + "strompreisOptimierer.properties";
		
		try {
			properties.load(new FileInputStream(dateiPfad));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static BigDecimal getMaxPreisProKilowattStunde() {
		return new BigDecimal(instanz.properties.getProperty("MAXIMALER_PREIS_PRO_KILOWATTSTUNDE"));		
	}
	
	public static int getIntervallgroesseZumLadenInStunden() {
		return Integer.valueOf(instanz.properties.getProperty("INTERVALLGROESSE_ZUM_LADEN_IN_STUNDEN")).intValue();
	}
	
	public static String getBefehlEntladungSperren() {
		return instanz.properties.getProperty("BEFEHL_ENTLADUNG_SPERREN");
	}
	
	public static String getBefehlEntladungEntsperren() {
		return instanz.properties.getProperty("BEFEHL_ENTLADUNG_ENTSPERREN");
	}
	
	public static int getAkkuKapazitaet() {
		return Integer.valueOf(instanz.properties.getProperty("AKKU_KAPAZITAET")).intValue();
	}
	
	public static int getNotstromreserve() {
		return Integer.valueOf(instanz.properties.getProperty("NOTSTROMRESERVE")).intValue();
	}
	
	public static boolean getPreisIntervalleViaDateiEinlesen() {
		return Boolean.valueOf(instanz.properties.getProperty("PREISINTERVALLE_VIA_DATEI_EINLESEN")).booleanValue();
	}

	public static String getAwattarApiURL() {
		return instanz.properties.getProperty("AWATTAR_API_URL");
	}
	
	public static Timestamp getPruefzeitpunkt() {
		
		String pruefzeitpunkt = instanz.properties.getProperty("VORGABE_PRUEF_ZEITPUNKT");
		
		if (pruefzeitpunkt == null) {
			return new Timestamp(System.currentTimeMillis());
		}
		return Timestamp.valueOf(pruefzeitpunkt);
		
	}
	
}

