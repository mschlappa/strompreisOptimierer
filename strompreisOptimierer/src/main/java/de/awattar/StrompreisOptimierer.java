package de.awattar;

import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beust.jcommander.JCommander;

public class StrompreisOptimierer {
	
	public static String WORK_DIR;
	
	public static void main(String[] args) {
		
		KommandozeilenArgumente argumente = new KommandozeilenArgumente();
		
		JCommander.newBuilder()
		  .addObject(argumente)
		  .build()
		  .parse(args);
		
		StrompreisOptimierer.WORK_DIR = argumente.getWorkdir();
		
		if (argumente.isDebug()) {
			System.setProperty(org.slf4j.impl.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "DEBUG");
		}
		
		final Logger logger = LoggerFactory.getLogger(StrompreisOptimierer.class);
		
		PropertiesHelper.init();
		Lastgang.init();		
		
		Timestamp pruefZeitpunkt = PropertiesHelper.getPruefzeitpunkt();
		
		logger.info("+++ Starte StrompreisOptimierung um " + pruefZeitpunkt + " +++");
				
		int speicherKapazitaet_Wh = PropertiesHelper.getAkkuKapazitaet();
		int notstromreserve_Wh = PropertiesHelper.getNotstromreserve();
		int soc = argumente.getSoc();
		int verfuegbareLadungsmenge = (int)( speicherKapazitaet_Wh  * (soc / 100d) - notstromreserve_Wh);
		
		logger.debug("SOC=" + argumente.getSoc());
		logger.debug("verfuegbareLadungsmenge=" + verfuegbareLadungsmenge);
		
		List<PreisIntervall> preisIntervalle = new AwattarMarketdataClient().getPreisIntervalle();
		Optimierer optimierer = new Optimierer(preisIntervalle);
		
		optimierer.printPreisintervalle();
		
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
