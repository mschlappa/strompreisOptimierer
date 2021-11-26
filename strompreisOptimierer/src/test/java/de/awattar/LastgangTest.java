package de.awattar;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class LastgangTest {

	@Test
	void test() {
		
		assertEquals(107, Lastgang.getEnergiemengeZurStunde(10));
	}

}
