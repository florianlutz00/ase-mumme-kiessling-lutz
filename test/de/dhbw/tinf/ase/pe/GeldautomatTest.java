package de.dhbw.tinf.ase.pe;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import de.dhbw.tinf.ase.pe.zustaende.GeldautomatBereit;
import de.dhbw.tinf.ase.pe.zustaende.GeldautomatKarteDrin;
import de.dhbw.tinf.ase.pe.zustaende.GeldautomatKeinGeld;
import de.dhbw.tinf.ase.pe.zustaende.GeldautomatPinKorrekt;
import org.junit.Test;

public class GeldautomatTest {
	
	@Test
	public void testStandardZustand() {
		Geldautomat geldautomat = new Geldautomat();
		assertTrue("Geldautomat muss im Zustand Kein Geld sein", geldautomat.aktuellerZustand instanceof GeldautomatKeinGeld);
	}

	@Test
	public void testWechsleInZustandBereitDurchBestuecken() {
		Geldautomat geldautomat = new Geldautomat();
		geldautomat.bestuecken(200);
		assertTrue("Geldautomat muss im Zustand Bereit sein", geldautomat.aktuellerZustand instanceof GeldautomatBereit);
	}
	
	@Test
	public void testWechsleInZustandBereitDurchKarteAuswerfen() {
		Geldautomat geldautomat = new Geldautomat();
		geldautomat.bestuecken(200);
		geldautomat.einschieben(new Karte("0000"));
		geldautomat.eingeben("0000");
		geldautomat.ausgeben();
		assertTrue("Geldautomat muss im Zustand Bereit sein", geldautomat.aktuellerZustand instanceof GeldautomatBereit);
	}
	
	@Test
	public void testWechsleInZustandKarteDrin() {
		Geldautomat geldautomat = new Geldautomat();
		geldautomat.bestuecken(200);
		geldautomat.einschieben(new Karte("0000"));
		assertTrue("Geldautomat muss im Zustand KarteDrin sein", geldautomat.aktuellerZustand instanceof GeldautomatKarteDrin);
	}
	
	@Test
	public void testBleibeInZustandKarteDrin() {
		Geldautomat geldautomat = new Geldautomat();
		geldautomat.bestuecken(200);
		geldautomat.einschieben(new Karte("0000"));
		geldautomat.eingeben("1111");
		assertTrue("Geldautomat muss im Zustand KarteDrin sein", geldautomat.aktuellerZustand instanceof GeldautomatKarteDrin);
	}
	
	@Test
	public void testWechsleInZustandPinKorrekt() {
		Geldautomat geldautomat = new Geldautomat();
		geldautomat.bestuecken(200);
		geldautomat.einschieben(new Karte("0000"));
		geldautomat.eingeben("0000");
		assertTrue("Geldautomat muss im Zustand PinKorrekt sein", geldautomat.aktuellerZustand instanceof GeldautomatPinKorrekt);
	}
	
	@Test
	public void testBleibeInZustandPinKorrekt() {
		Geldautomat geldautomat = new Geldautomat();
		geldautomat.bestuecken(200);
		geldautomat.einschieben(new Karte("0000"));
		geldautomat.eingeben("0000");
		geldautomat.auszahlen(20);
		assertTrue("Geldautomat muss im Zustand PinKorrekt sein", geldautomat.aktuellerZustand instanceof GeldautomatPinKorrekt);
	}
	
	@Test
	public void testWechsleInZustandKeinGeldPassend() {
		Geldautomat geldautomat = new Geldautomat();
		geldautomat.bestuecken(200);
		geldautomat.einschieben(new Karte("0000"));
		geldautomat.eingeben("0000");
		geldautomat.auszahlen(200);
		assertTrue("Geldautomat muss im Zustand Kein Geld sein", geldautomat.aktuellerZustand instanceof GeldautomatKeinGeld);
	}
	
	@Test
	public void testWechsleInZustandKeinGeldUnpassend() {
		Geldautomat geldautomat = new Geldautomat();
		geldautomat.bestuecken(200);
		geldautomat.einschieben(new Karte("0000"));
		geldautomat.eingeben("0000");
		geldautomat.auszahlen(250);
		assertTrue("Geldautomat muss im Zustand Kein Geld sein", geldautomat.aktuellerZustand instanceof GeldautomatKeinGeld);
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
	
	@Test
	public void testDreiFalschePins() {
		Geldautomat geldautomat = new Geldautomat();
		geldautomat.bestuecken(100);
		geldautomat.einschieben(new Karte("1111"));
		geldautomat.eingeben("1110");
		geldautomat.eingeben("1110");
		geldautomat.eingeben("1110");
		assertTrue("Geldautomat muss im Zustand Bereit sein", geldautomat.aktuellerZustand instanceof GeldautomatBereit);
	}
	
}