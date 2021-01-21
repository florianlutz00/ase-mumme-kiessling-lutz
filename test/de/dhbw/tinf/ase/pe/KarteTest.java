package de.dhbw.tinf.ase.pe;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Ignore;
import org.junit.Test;

import de.dhbw.tinf.ase.pe.Geldautomat;
import de.dhbw.tinf.ase.pe.Karte;


public class KarteTest {
	
	@Test (expected = IllegalArgumentException.class)
	public void testPinMitBuchstaben() {
		Karte karte = new Karte("0a00");
	}

	
	@Test (expected = IllegalArgumentException.class)
	public void testPinMitSonderzeichen() {
		Karte karte = new Karte("0a0-");
	}

	@Test
	public void testFalschePin() {
		Karte karte = new Karte("0001");
		boolean result = karte.istKorrekt("0002");
		assertEquals("Bei falscher PIN muss false zurueckkommen!", false, result);
	}
	
	@Test
	public void testRichtigePin() {
		Karte karte = new Karte("0001");
		boolean result = karte.istKorrekt("0001");
		assertEquals("Bei falscher PIN muss false zurueckkommen!", true, result);
	}

}
