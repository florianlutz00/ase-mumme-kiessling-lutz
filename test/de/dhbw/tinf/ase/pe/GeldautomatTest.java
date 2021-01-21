package de.dhbw.tinf.ase.pe;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Ignore;
import org.junit.Test;

import de.dhbw.tinf.ase.pe.Geldautomat;
import de.dhbw.tinf.ase.pe.Karte;

public class GeldautomatTest {

	@Test (expected = IllegalStateException.class)
	public void testKarteIstNull() {
		Geldautomat geldautomat = new Geldautomat();
		geldautomat.bestuecken(1000);
		geldautomat.einschieben(null);
		fail("Expected IllegalArgumentException!");
	}
	
	@Test (expected = IllegalStateException.class)
	public void testZweiteKarteEinschieben() {
		Geldautomat geldautomat = new Geldautomat();
		geldautomat.bestuecken(1000);
		geldautomat.einschieben(new Karte("1111"));
		geldautomat.einschieben(new Karte("2222"));
	}

	@Test
	public void testFalschePin() {
		Geldautomat geldautomat = new Geldautomat();
		geldautomat.bestuecken(1000);
		geldautomat.einschieben(new Karte("1111"));
		geldautomat.eingeben("2222");
		int summe = geldautomat.auszahlen(500);
		assertEquals("Bei falscher PIN muss -1 zurueckgegeben werden!", -1, summe);
	}
	
	@Test
	public void testAbhebungOhnePin() {
		Geldautomat geldautomat = new Geldautomat();
		geldautomat.bestuecken(1000);
		geldautomat.einschieben(new Karte("1111"));
		int summe = geldautomat.auszahlen(500);
		assertEquals("Bei falscher PIN muss -1 zurueckgegeben werden!", -1, summe);
	}

	@Test
	public void testBestueckung() {
		Geldautomat geldautomat = new Geldautomat();
		geldautomat.bestuecken(100);
		assertEquals("Bestand muss uebereinstimmen!", 100, geldautomat.fuellstand());
	}
	
	@Test
	public void testZuWenigGeldImAutomat() {
		Geldautomat geldautomat = new Geldautomat();
		geldautomat.bestuecken(100);
		geldautomat.einschieben(new Karte("1111"));
		int summe = geldautomat.auszahlen(300);
		assertEquals("Bei zu wenig Geld muss der Rest(100) zurueckgegeben werden!", 100, summe);
		assertEquals("Bargeld muss jetzt 0 sein!", 0, geldautomat.fuellstand());
	}
	
}
