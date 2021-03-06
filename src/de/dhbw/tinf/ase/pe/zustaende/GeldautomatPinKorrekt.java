package de.dhbw.tinf.ase.pe.zustaende;

import de.dhbw.tinf.ase.pe.Geldautomat;
import de.dhbw.tinf.ase.pe.Karte;

public class GeldautomatPinKorrekt extends GeldautomatZustand{

	public  GeldautomatPinKorrekt(Geldautomat geldautomat) {
		super(geldautomat, new boolean[]{
				true,
				false,
				false,
				false,
				true,
				true,
				true,
				true
		});
	}
	
	@Override
	public void ausgeben() {
		geldautomat.karte = null;
		geldautomat.pinFalsch = 0;
		if(geldautomat.bargeld != 0)
		{
			geldautomat.setAktuellerZustand(new GeldautomatBereit(geldautomat));
		}
		else
		{
			geldautomat.setAktuellerZustand(new GeldautomatKeinGeld(geldautomat));
		}
	}

	@Override
	public int auszahlen(int betrag) {
		if ((betrag < 5) || (betrag > 500) || (betrag % 5 != 0)) 
		{
			throw new IllegalArgumentException("Betrag muss zwischen 5 und 500 Geld liegen und in Scheinen zahlbar sein!");
		}
		if (geldautomat.bargeld < betrag) 
		{
			int ausbezahlt = geldautomat.bargeld;
			geldautomat.bargeld = 0;
			ausgeben();
			return ausbezahlt;
		}
		else 
		{
			geldautomat.bargeld -= betrag;
			if(geldautomat.bargeld == 0) 
			{
				ausgeben();
			}
			return betrag;
		}
	}

	@Override
	public void bestuecken(int betrag) {
		throw new IllegalStateException("Bestuecken des Automaten waehrend Transaktion nicht moeglich!");
	}

	@Override
	public int eingeben(String pin) {
		throw new IllegalStateException("Pin wurde bereits eingegeben!");
	}

	@Override
	public void einschieben(Karte karte) {
		throw new IllegalStateException("Es befindet sich bereits eine Karte im Automat!");	
	}

	@Override
	public int fuellstand() {
		return geldautomat.bargeld;
	}

	@Override
	public String info() {
		if(geldautomat.bargeld > 500) 
		{
			return "Maximalbetrag kann abgehoben werden";
		}
		else 
		{
			return "Abhebung bis zu " + fuellstand() + " Geld ist moeglich.";
		}
	}

}
