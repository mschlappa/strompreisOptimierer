package de.awattar;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public class StrompreisOptimierer {

	// Maximaler kWh-Preis in Cent (netto) im Tarif Hourly EUR / MWh 
	public static final BigDecimal MAXIMALER_PREIS_PRO_KILOWATTSTUNDE = new BigDecimal("400.00");

	public static final int intervallGroesseZumLadenInStunden = 3;

	public static final Timestamp pruefZeitpunkt = Timestamp.valueOf("2021-11-24 19:00:00.0"); 
	//public static final Timestamp pruefZeitpunkt = new Timestamp(System.currentTimeMillis());
	
	public static final int verfuegbareLadungsmenge = 3000;
	
	public static final String cmdSperreSpeicherentladung ="echo e3dcset -d 1";
	
	public static final String cmdErlaubeSpeicherentladung ="echo e3dcset -a";
	
	
	public static void main(String[] args) {
		
		List<PreisIntervall> preisIntervalle = new AwattarMarketdataClient().getPreisIntervalleViaDatei();
		
		Optimierer optimierer = new Optimierer(preisIntervalle);
		
		//optimierer.printPreisintervalle();
		
		Timestamp startzeitpunktZumLaden = optimierer.getStartzeitpunktZumLaden(intervallGroesseZumLadenInStunden);
		
		boolean istSperrenDerSpeicherEntladungErforderlich = optimierer.istSperrenDerSpeicherEntladungErforderlich(pruefZeitpunkt, startzeitpunktZumLaden, verfuegbareLadungsmenge);
		
		System.out.println("Zum Zeitpunkt " + pruefZeitpunkt + " istSperrenDerSpeicherEntladungErforderlich=" + istSperrenDerSpeicherEntladungErforderlich);
		
		E3DCSetCommandRunner runner = new E3DCSetCommandRunner();
		
		if (istSperrenDerSpeicherEntladungErforderlich) {
			runner.sperreSpeicherentladung();
		}else {
			runner.erlaubeSpeicherentladung();
		}
		
		System.exit(0);
		
		
	}

}