package de.awattar;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public class StrompreisOptimierer {

	// Maximaler kWh-Preis in Cent (netto) im Tarif Hourly EUR / MWh 
	public static final BigDecimal MAXIMALER_PREIS_PRO_KILOWATTSTUNDE = new BigDecimal("400.00");

	public static final int intervallGroesseZumLadenInStunden = 3;

	//public static final Timestamp pruefZeitpunkt = Timestamp.valueOf("2021-11-25 20:00:00.0"); 
	public static final Timestamp pruefZeitpunkt = new Timestamp(System.currentTimeMillis());
	
	public static final String cmdSperreSpeicherentladung ="/home/pi/e3dcset -d 1";
	
	public static final String cmdErlaubeSpeicherentladung ="/home/pi/e3dcset -a";
	
	public static final int notstromreserve_Wh = 1000; // 10% = 1 kWh
	
	public static final int speicherKapazitaet_Wh = 10000; // 10 kWh

	
	public static void main(String[] args) {
		
		if (args.length != 1) {
			System.out.println("Parameter SOC fehlt");
			System.exit(1);
		}
		
		double soc = Double.valueOf(args[0]);
		System.out.println("SOC=" + soc);
		
		int verfuegbareLadungsmenge = (int)( speicherKapazitaet_Wh  * (soc / 100) - notstromreserve_Wh);
		
		System.out.println("verfuegbareLadungsmenge=" + verfuegbareLadungsmenge);
		
		List<PreisIntervall> preisIntervalle = new AwattarMarketdataClient().getPreisIntervalleViaDatei();
		
		Optimierer optimierer = new Optimierer(preisIntervalle);
		
		//optimierer.printPreisintervalle();
		
		PreisIntervall ladeZeitfenster = optimierer.getStartzeitpunktZumLaden(intervallGroesseZumLadenInStunden);
		
		Aktion aktion = optimierer.istSperrenDerSpeicherEntladungErforderlich(pruefZeitpunkt, ladeZeitfenster, verfuegbareLadungsmenge);
		
		System.out.println("Zum Zeitpunkt " + pruefZeitpunkt + " Aktion=" + aktion);
		
		E3DCSetCommandRunner runner = new E3DCSetCommandRunner();
		
		switch(aktion) {
		
		case SPERREN:
			runner.sperreSpeicherentladung();
			break;
		case ENTSPERREN:
			runner.erlaubeSpeicherentladung();
			break;
		default:
			// NOP
		}
		
	}

}