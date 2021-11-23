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

	/**
	 * NettoPreis pro kWh in EUR/MWh
	 */
	public BigDecimal getPreis() {
		return preis;
	}

	/**
	 * BruttoPreis pro kWh in Cent/kWh
	 */
	public BigDecimal getBruttoPreisProKilowattstundeInCent() {
		BigDecimal preisbrutto = getPreis().multiply(new BigDecimal("0.119"));
		return preisbrutto.setScale(2, RoundingMode.HALF_UP);
	}

	public void setPreis(BigDecimal preis) {
		this.preis = preis;
	}
	
	
	public String toString() {
		
		StringBuffer buf = new StringBuffer();
		
		buf.append(getStartAsTimestamp());
		buf.append(" - ");
		buf.append(getEndeAsTimestamp());

		buf.append(" : ");
		buf.append(getBruttoPreisProKilowattstundeInCent());

		return buf.toString();
	}
	
}
