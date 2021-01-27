package de.dhbw.tinf.ase.pe.zustaende;

import de.dhbw.tinf.ase.pe.Geldautomat;
import de.dhbw.tinf.ase.pe.Karte;

public class GeldautomatBereit implements GeldautomatZustand{

	public  GeldautomatBereit(Geldautomat geldautomat) {
		this.geldautomat = geldautomat;
	}
	
	private Geldautomat geldautomat;
	
	@Override
	public void ausgeben() {
		throw new IllegalStateException("Es wurde noch keine Karte eingeschoben!");	
	}

	@Override
	public int auszahlen(int betrag) {
		return -1;
	}

	@Override
	public void bestuecken(int betrag) {
		geldautomat.bargeld += betrag;
	}

	@Override
	public int eingeben(String pin) {
		throw new IllegalStateException("Es wurde noch keine Karte eingeschoben!");	
	}

	@Override
	public void einschieben(Karte karte) {
		if(karte != null)
		{
			geldautomat.karte = karte;
			geldautomat.setAktuellerZustand(new GeldautomatKarteDrin(geldautomat));
		}
		else 
		{
			throw new IllegalStateException("Die eingeschobenen Karte darf nicht null sein!");
		}
	}

	@Override
	public int fuellstand() {
		return geldautomat.bargeld;
	}

	@Override
	public String info() {
		return "Abhebung bis zu " + fuellstand() + " Geld ist moeglich - bitte Karte eingeben";
	}
}
