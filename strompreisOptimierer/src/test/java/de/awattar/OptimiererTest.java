package de.awattar;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class OptimiererTest {

	@Test
	void testLiegtTimestampImIntervall_genauAufGrenzeVon2aufeinanderfolgendenIntervallen() {
		
		List<PreisIntervall> list = new ArrayList<>();
		
		PreisIntervall pi1 = new PreisIntervall(Timestamp.valueOf("2021-12-01 00:00:00.0"), Timestamp.valueOf("2021-12-01 01:00:00.0"), null);
		PreisIntervall pi2 = new PreisIntervall(Timestamp.valueOf("2021-12-01 01:00:00.0"), Timestamp.valueOf("2021-12-01 02:00:00.0"), null);
		
		list.add(pi1);
		list.add(pi2);
		
		Optimierer optimierer = new Optimierer(list);
		
		boolean liegtImIntervall = optimierer.liegtTimestampImIntervall(Timestamp.valueOf("2021-12-01 01:00:00.0"), list);
		
		assertTrue(liegtImIntervall == true);
	}

	@Test
	void testLiegtTimestampImIntervall_zwischen2Intervallen() {
		
		List<PreisIntervall> list = new ArrayList<>();
		
		PreisIntervall pi1 = new PreisIntervall(Timestamp.valueOf("2021-12-01 00:00:00.0"), Timestamp.valueOf("2021-12-01 01:00:00.0"), null);
		PreisIntervall pi2 = new PreisIntervall(Timestamp.valueOf("2021-12-01 02:00:00.0"), Timestamp.valueOf("2021-12-01 03:00:00.0"), null);
		
		list.add(pi1);
		list.add(pi2);
		
		Optimierer optimierer = new Optimierer(list);
		
		boolean liegtImIntervall = optimierer.liegtTimestampImIntervall(Timestamp.valueOf("2021-12-01 01:30:00.0"), list);
		
		assertTrue(liegtImIntervall == false);
	}
	
	@Test
	void testLiegtTimestampImIntervall_obereGrenze() {
		
		List<PreisIntervall> list = new ArrayList<>();
		
		PreisIntervall pi1 = new PreisIntervall(Timestamp.valueOf("2021-12-01 00:00:00.0"), Timestamp.valueOf("2021-12-01 01:00:00.0"), null);
		
		list.add(pi1);
		
		Optimierer optimierer = new Optimierer(list);
		
		boolean liegtImIntervall = optimierer.liegtTimestampImIntervall(Timestamp.valueOf("2021-12-01 01:00:00.0"), list);
		
		assertTrue(liegtImIntervall == false);
	}

//	@Test
//	void testGetPreisintervalleImZeitraum() {
//		
//		Optimierer optimierer = new Optimierer(list);
//
//		optimierer.getPreisintervalleImZeitraum(null, null, null);
//		
//	}
}
