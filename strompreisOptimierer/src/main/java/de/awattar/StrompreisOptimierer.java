package de.awattar;

import java.sql.Timestamp;
import java.util.List;

public class StrompreisOptimierer {
	
	public static final String WORK_DIR = "./";
	
	public static void main(String[] args) {
		
		Timestamp pruefZeitpunkt = PropertiesHelper.getPruefzeitpunkt();
		
		System.out.println("\n+++ Starte StrompreisOptimierung um " + pruefZeitpunkt + " +++");
		
		if (args.length != 1) {
			System.out.println("Parameter SOC fehlt");
			System.exit(1);
		}
		
		double soc = Double.valueOf(args[0]);
		System.out.println("SOC=" + soc);
		
		int speicherKapazitaet_Wh = PropertiesHelper.getAkkuKapazitaet();
		int notstromreserve_Wh = PropertiesHelper.getNotstromreserve();
		int verfuegbareLadungsmenge = (int)( speicherKapazitaet_Wh  * (soc / 100) - notstromreserve_Wh);
		
		System.out.println("verfuegbareLadungsmenge=" + verfuegbareLadungsmenge);
		
		List<PreisIntervall> preisIntervalle = new AwattarMarketdataClient().getPreisIntervalle();
		Optimierer optimierer = new Optimierer(preisIntervalle);
		
		//optimierer.printPreisintervalle();
		
		PreisIntervall ladeZeitfenster = optimierer.getStartzeitpunktZumLaden();
		
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