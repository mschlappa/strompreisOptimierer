package de.awattar;

import java.sql.Timestamp;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StrompreisOptimierer {
	
	private static final Logger logger = LoggerFactory.getLogger(StrompreisOptimierer.class);
	
	public static final String WORK_DIR = "./";
	
	public static void main(String[] args) {
		
		Timestamp pruefZeitpunkt = PropertiesHelper.getPruefzeitpunkt();
		
		logger.info("+++ Starte StrompreisOptimierung um " + pruefZeitpunkt + " +++");
		
		if (args.length != 1) {
			logger.error("Parameter SOC fehlt");
			System.exit(1);
		}
		
		double soc = Double.valueOf(args[0]);
		logger.debug("SOC=" + soc);
		
		int speicherKapazitaet_Wh = PropertiesHelper.getAkkuKapazitaet();
		int notstromreserve_Wh = PropertiesHelper.getNotstromreserve();
		int verfuegbareLadungsmenge = (int)( speicherKapazitaet_Wh  * (soc / 100) - notstromreserve_Wh);
		
		logger.debug("verfuegbareLadungsmenge=" + verfuegbareLadungsmenge);
		
		List<PreisIntervall> preisIntervalle = new AwattarMarketdataClient().getPreisIntervalle();
		Optimierer optimierer = new Optimierer(preisIntervalle);
		
		//optimierer.printPreisintervalle();
		
		PreisIntervall ladeZeitfenster = optimierer.getStartzeitpunktZumLaden();
		
		Aktion aktion = optimierer.istSperrenDerSpeicherEntladungErforderlich(pruefZeitpunkt, ladeZeitfenster, verfuegbareLadungsmenge);
		
		logger.info("Zum Zeitpunkt " + pruefZeitpunkt + " Aktion=" + aktion);
		
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
