package de.awattar;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Optimierer {

	private List<PreisIntervall> preisintervalle;
	
	public Optimierer(List<PreisIntervall> preisintervalle) {
		this.preisintervalle = preisintervalle;
	}
	
	public void printPreisintervalle() {
	
		System.out.println("Alle Preisintervalle aus der aWATTar API");
		
		for (PreisIntervall preisIntervall : preisintervalle) {
			System.out.println(preisIntervall);	
		}
		
	}
	
	public Timestamp getStartzeitpunktZumLaden(int intervallGroesseZumLadenInStunden) {

		int anzahlPreisintervalle = preisintervalle.size();
		
		if (intervallGroesseZumLadenInStunden > anzahlPreisintervalle) {
			System.out.println("Intervallgroesse begrenzt auf " + anzahlPreisintervalle);
			intervallGroesseZumLadenInStunden = anzahlPreisintervalle;
		}
		
		BigDecimal minimum = new BigDecimal("9999.00"); 
		Timestamp startzeitpunkt = null;
		
		for (int i = 0; i < preisintervalle.size()-intervallGroesseZumLadenInStunden+1; i++) {
			
			BigDecimal summe = new BigDecimal("0.00");
			
			for (int j = 0; j < intervallGroesseZumLadenInStunden; j++) {
				
				summe = summe.add(preisintervalle.get(i+j).getBruttoPreisProMegaWattStundeInEuroCap());
				
			}
			
			if (minimum.compareTo(summe) == 1) {
				minimum = summe;
				startzeitpunkt = preisintervalle.get(i).getStartAsTimestamp();
			}
		}
		
		System.out.println("StartzeitpunktZumLaden:" + startzeitpunkt);
		System.out.println("Preis im Ladezeitfenster:" + minimum.divide(new BigDecimal(intervallGroesseZumLadenInStunden), 2, RoundingMode.HALF_UP));
		
		return startzeitpunkt;
	}
	
	public boolean istSperrenDerSpeicherEntladungErforderlich(Timestamp jetzt, Timestamp naechsteLadung, int verfuegbareLadungsmenge) {
		
		// Erst mal pruefen ob wir mit der aktuell verfuegbaren Ladungsmenge hinkommen
		List<PreisIntervall> preisintervalleImZeitraum = getPreisintervalleImZeitraum(jetzt, naechsteLadung, this.preisintervalle);
		
		int erforderlicheladungsmenge = 0;
		
		for (PreisIntervall preisIntervall : preisintervalleImZeitraum) {
			erforderlicheladungsmenge = erforderlicheladungsmenge + preisIntervall.getEnergiemengeZurStunde();
		}
		System.out.println("\nPruefe istSperrenSpeicherEntladung erforderlich...\n");
		System.out.println("erforderlicheladungsmenge=" + erforderlicheladungsmenge);
		System.out.println("verfuegbareLadungsmenge=" + verfuegbareLadungsmenge);
		System.out.println("");
		
		if (erforderlicheladungsmenge < verfuegbareLadungsmenge) {
			System.out.println("Entladen des Speichers muss nicht gesperrt werden");
			return false;
		}
		
		// Die verfuegbare Energiemenge im Speicher reicht nicht bis zum naechsten Ladevorgang
		// Daher wird nun in den guenstigen Stunden das Entladen unterbunden
		// Zunaechst mussen die Preisintervalle im Zeitraum nach Preis sortiert werden
		PreisIntervallComparator comparator = new PreisIntervallComparator();
		Collections.sort(preisintervalleImZeitraum, comparator);
		
		System.out.println("Alle Preisintervalle im Zeitraum jetzt bis zum naechsten Laden (sortiert nach Preis):");
		System.out.println(preisintervalleImZeitraum);
			
		int einzusparendeLadungsmenge = erforderlicheladungsmenge - verfuegbareLadungsmenge;
		
		List<PreisIntervall> preisintervalleFuerEntladungssperre = new ArrayList<>();
		
		for (PreisIntervall preisIntervall : preisintervalleImZeitraum) {
			
			//System.out.println("einzusparendeLadungsmenge=" + einzusparendeLadungsmenge);
			
			einzusparendeLadungsmenge = einzusparendeLadungsmenge - preisIntervall.getEnergiemengeZurStunde();
			preisintervalleFuerEntladungssperre.add(preisIntervall);
						
			if (einzusparendeLadungsmenge <= 0) {
				break;
			}
		}
		
		System.out.println("\nPreisintervalleFuerEntladungssperre");
		System.out.println(preisintervalleFuerEntladungssperre);
		
		if (getPreisintervalleImZeitraum(jetzt, jetzt, preisintervalleFuerEntladungssperre).isEmpty()) {
			return false;
		}
		
		return true;
		
		
	}
	
	
	private List<PreisIntervall> getPreisintervalleImZeitraum(Timestamp beginn, Timestamp ende, List<PreisIntervall> preisintervalle){
		
		List<PreisIntervall> preisintervalleImIntervall = new ArrayList<>();
		
		for (PreisIntervall preisIntervall : preisintervalle) {
			if (preisIntervall.getStartAsTimestamp().compareTo(beginn) >= 0 && preisIntervall.getEndeAsTimestamp().compareTo(ende) <= 0) {
				
				preisintervalleImIntervall.add(preisIntervall);
			}
		}
		
		return preisintervalleImIntervall;
	}
	
}
