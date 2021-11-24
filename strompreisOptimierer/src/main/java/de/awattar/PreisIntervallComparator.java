package de.awattar;

import java.util.Comparator;

public class PreisIntervallComparator implements Comparator<PreisIntervall> {

	/**
	 * Vergleicht die Preisintervalle nach Preis (Cap)
	 */
	@Override
	public int compare(PreisIntervall o1, PreisIntervall o2) {
		return o1.getBruttoPreisProMegaWattStundeInEuroCap().compareTo(o2.getBruttoPreisProMegaWattStundeInEuroCap());
	}

}
