package de.awattar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

class PropertiesHelperTest {

	@Test
	void test() {

		assertEquals(PropertiesHelper.getAkkuKapazitaet(), 10000);
		assertEquals(PropertiesHelper.getNotstromreserve(), 1000);
		assertEquals(PropertiesHelper.getIntervallgroesseZumLadenInStunden(), 3);
		assertEquals(PropertiesHelper.getMaxPreisProKilowattStunde(), new BigDecimal("400.00"));
		assertEquals(PropertiesHelper.getPreisIntervalleViaDateiEinlesen(), true);
		assertEquals(PropertiesHelper.getAwattarApiURL(), "https://api.awattar.de/v1/marketdata");
		assertNotNull(PropertiesHelper.getPruefzeitpunkt());
	}

}
