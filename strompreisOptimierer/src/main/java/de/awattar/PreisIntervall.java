package de.awattar;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;

import com.google.gson.annotations.SerializedName;

public class PreisIntervall {

	
	public PreisIntervall(String start, String ende, BigDecimal preis) {
		this.start = start;
		this.ende = ende;
		this.preis = preis;
	}
	
	public PreisIntervall(Timestamp start, Timestamp ende, BigDecimal preis) {
		this.start = String.valueOf(start.getTime());
		this.ende = String.valueOf(ende.getTime());
		this.preis = preis;
	}

	@SerializedName("start_timestamp")
	private String start;
	
	@SerializedName("end_timestamp")
	private String ende; 
	
	@SerializedName("marketprice")
	private BigDecimal preis;

	public String getStart() {
		return start;
	}

	public Timestamp getStartAsTimestamp() {
		return new Timestamp(Long.valueOf(getStart()));
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnde() {
		return ende;
	}

	public Timestamp getEndeAsTimestamp() {
		return new Timestamp(Long.valueOf(getEnde()));
	}

	public void setEnde(String ende) {
		this.ende = ende;
	}

	public BigDecimal getNettoPreisProMegaWattStundeInEuro() {
		return preis.setScale(2, RoundingMode.HALF_UP);
	}

	public BigDecimal getBruttoPreisProMegaWattStundeInEuro() {
		BigDecimal preis = getNettoPreisProMegaWattStundeInEuro().multiply(new BigDecimal("1.19"));
		return preis.setScale(2, RoundingMode.HALF_UP);
	}

	public BigDecimal getBruttoPreisProMegaWattStundeInEuroCap() {
		
		if (getBruttoPreisProMegaWattStundeInEuro().compareTo(StrompreisOptimierer.MAXIMALER_PREIS_PRO_KILOWATTSTUNDE) == 1) {
			return StrompreisOptimierer.MAXIMALER_PREIS_PRO_KILOWATTSTUNDE;
		}else {
			return getBruttoPreisProMegaWattStundeInEuro();
		}
		
	}

	public void setPreis(BigDecimal preis) {
		this.preis = preis;
	}
	
	public int getEnergiemengeZurStunde() {
		return Lastgang.getEnergiemengeZurStunde(getStartAsTimestamp().getHours());
	}
	
	public String toString() {
		
		StringBuffer buf = new StringBuffer();
		
		buf.append(getStartAsTimestamp());
		buf.append("-");
		buf.append(getEndeAsTimestamp());

		buf.append(" Preis:");
		buf.append(getBruttoPreisProMegaWattStundeInEuroCap());

		buf.append(" Energiemenge:");
		buf.append(getEnergiemengeZurStunde());
		buf.append(" Wh\n");

		return buf.toString();
	}
	
}
