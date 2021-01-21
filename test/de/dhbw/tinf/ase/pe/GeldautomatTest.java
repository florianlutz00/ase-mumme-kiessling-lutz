package de.dhbw.tinf.ase.pe;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Ignore;
import org.junit.Test;

import de.dhbw.tinf.ase.pe.Geldautomat;
import de.dhbw.tinf.ase.pe.Karte;

public class GeldautomatTest {
	
	@Test
	public void testStandardZustand() {
		Geldautomat geldautomat = new Geldautomat();
		fail();
	}

	@Test
	public void testWechsleInZustandBereitDurchBestücken() {
		Geldautomat geldautomat = new Geldautomat();
		geldautomat.bestuecken(200);
		fail();
	}
	
	@Test
	public void testWechsleInZustandBereitDurchKarteAuswerfen() {
		Geldautomat geldautomat = new Geldautomat();
		geldautomat.bestuecken(200);
		geldautomat.einschieben(new Karte("0000"));
		geldautomat.eingeben("0000");
		geldautomat.ausgeben();
		fail();
	}
	
	@Test
	public void testWechsleInZustandKarteDrin() {
		Geldautomat geldautomat = new Geldautomat();
		geldautomat.bestuecken(200);
		geldautomat.einschieben(new Karte("0000"));
		fail();
	}
	
	@Test
	public void testBleibeInZustandKarteDrin() {
		Geldautomat geldautomat = new Geldautomat();
		geldautomat.bestuecken(200);
		geldautomat.einschieben(new Karte("0000"));
		geldautomat.eingeben("1111");
		fail();
	}
	
	@Test
	public void testWechsleInZustandPinKorrekt() {
		Geldautomat geldautomat = new Geldautomat();
		geldautomat.bestuecken(200);
		geldautomat.einschieben(new Karte("0000"));
		geldautomat.eingeben("0000");
		fail();
	}
	
	@Test
	public void testBleibeInZustandPinKorrekt() {
		Geldautomat geldautomat = new Geldautomat();
		geldautomat.bestuecken(200);
		geldautomat.einschieben(new Karte("0000"));
		geldautomat.eingeben("0000");
		geldautomat.auszahlen(20);
		fail();
	}
	
	@Test
	public void testWechsleInZustandKeinGeld() {
		Geldautomat geldautomat = new Geldautomat();
		geldautomat.bestuecken(200);
		geldautomat.einschieben(new Karte("0000"));
		geldautomat.eingeben("0000");
		geldautomat.auszahlen(200);
		fail();
	}
	
	@Test
	public void testBestueckung() {
		Geldautomat geldautomat = new Geldautomat();
		geldautomat.bestuecken(100);
		assertEquals("Bestand muss uebereinstimmen!", 100, geldautomat.fuellstand());
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testFalscheBestueckung() {
		Geldautomat geldautomat = new Geldautomat();
		geldautomat.bestuecken(-100);
	}
	
	@Test (expected = IllegalStateException.class)
	public void testKarteInLeerenAutomat() {
		Geldautomat geldautomat = new Geldautomat();
		geldautomat.einschieben(new Karte("1111"));
	}
	
	@Test (expected = IllegalStateException.class)
	public void testKarteIstNull() {
		Geldautomat geldautomat = new Geldautomat();
		geldautomat.bestuecken(1000);
		geldautomat.einschieben(null);
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
	public void testGeldAbheben() {
		Geldautomat geldautomat = new Geldautomat();
		geldautomat.bestuecken(100);
		geldautomat.einschieben(new Karte("1111"));
		geldautomat.eingeben("1111");
		int summe = geldautomat.auszahlen(50);
		assertEquals("Der Geldautomat muss 50 ausgeben!", 50, summe);
	}

	@Test
	public void testGeldMehrmalsAbheben() {
		Geldautomat geldautomat = new Geldautomat();
		geldautomat.bestuecken(100);
		geldautomat.einschieben(new Karte("1111"));
		geldautomat.eingeben("1111");
		int summe = geldautomat.auszahlen(50);
	    summe += geldautomat.auszahlen(20);
		assertEquals("Der Geldautomat muss 70 ausgeben!", 70, summe);
	}
	
	@Test
	public void testZuWenigGeldImAutomat() {
		Geldautomat geldautomat = new Geldautomat();
		geldautomat.bestuecken(100);
		geldautomat.einschieben(new Karte("1111"));
		geldautomat.eingeben("1111");
		int summe = geldautomat.auszahlen(300);
		assertEquals("Bei zu wenig Geld muss der Rest(100) zurueckgegeben werden!", 100, summe);
		assertEquals("Bargeld muss jetzt 0 sein!", 0, geldautomat.fuellstand());
	}
	
}