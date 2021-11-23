package de.awattar;

import java.util.List;

public class StrompreisOptimierer {

	public static void main(String[] args) {
		
		int intervallGroesseZumLadenInStunden = 3;
		
		List<PreisIntervall> preisIntervalle = new AwattarRestClient().getPreisIntervalle();
		
		Optimierer optimierer = new Optimierer(preisIntervalle);
		
		optimierer.printPreisintervalle();
		
		optimierer.printPreisintervalleCap();
		
		optimierer.getStartzeitpunktZumLaden(intervallGroesseZumLadenInStunden);
		
	}

}