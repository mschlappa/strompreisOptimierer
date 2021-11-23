package de.awattar;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Optimierer {

	// Maximaler kWh-Preis in Cent (netto) im Tarif Hourly EUR / MWh 
	//private static final BigDecimal MAXIMALER_PREIS_PRO_KILOWATTSTUNDE = new BigDecimal("168.06723");
	private static final BigDecimal MAXIMALER_PREIS_PRO_KILOWATTSTUNDE = new BigDecimal("336.13445");

	private List<PreisIntervall> preisintervalle;
	
	private List<PreisIntervall> preisintervalleCap;	
	
	public Optimierer(List<PreisIntervall> preisintervalle) {
		this.preisintervalle = preisintervalle;
		ermittlePreisintervalleCap();
	}
	
	private void ermittlePreisintervalleCap() {
		
		List<PreisIntervall> cap = new ArrayList<PreisIntervall>();
		
		for (PreisIntervall preisIntervall : preisintervalle) {
			
			BigDecimal preisCap = preisIntervall.getPreis();
			
			if (preisIntervall.getPreis().compareTo(MAXIMALER_PREIS_PRO_KILOWATTSTUNDE) == 1) {
				preisCap = MAXIMALER_PREIS_PRO_KILOWATTSTUNDE;
			}
			
			cap.add(new PreisIntervall(preisIntervall.getStart(), preisIntervall.getEnde(), preisCap));
		}
		
		this.preisintervalleCap = cap;
	}
	
	
	public void printPreisintervalle() {
		System.out.println("Preisintervalle");		
		for (PreisIntervall preisIntervall : preisintervalle) {
			System.out.println(preisIntervall);	
		}
		
	}
	
	public void printPreisintervalleCap() {
		System.out.println("PreisintervalleCap");
		for (PreisIntervall preisIntervall : preisintervalleCap) {
			System.out.println(preisIntervall);	
		}
		
	}
	public Timestamp getStartzeitpunktZumLaden(int intervallGroesseZumLadenInStunden) {
		

		return null;
	}
	
}
