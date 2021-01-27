package de.dhbw.tinf.ase.pe;

import de.dhbw.tinf.ase.pe.zustaende.GeldautomatKeinGeld;
import de.dhbw.tinf.ase.pe.zustaende.GeldautomatZustand;

public class Geldautomat {
	
	public int bargeld = 0;
	public Karte karte;
	public int pinFalsch = 0;
	public GeldautomatZustand aktuellerZustand;
	
	public Geldautomat(){
		setAktuellerZustand(new GeldautomatKeinGeld(this));
	}
	
	public void setAktuellerZustand(GeldautomatZustand aktuellerZustand) {
		this.aktuellerZustand = aktuellerZustand;
	}

	public void bestuecken(int bargeld) {
		aktuellerZustand.bestuecken(bargeld);
	}
	
	public void einschieben(Karte karte) {
		aktuellerZustand.einschieben(karte);
	}

	public void ausgeben() {
		aktuellerZustand.ausgeben();
	}

	public int eingeben(String pin) {
		return aktuellerZustand.eingeben(pin);
	}

	public int auszahlen(int summe) {
		return aktuellerZustand.auszahlen(summe);
	}

	public String info() {
		return aktuellerZustand.info();
	}
	
	public int fuellstand() {
		return aktuellerZustand.fuellstand();
	}
	
}
