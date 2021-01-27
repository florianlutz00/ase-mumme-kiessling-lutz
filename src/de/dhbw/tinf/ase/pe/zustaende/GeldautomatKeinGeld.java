package de.dhbw.tinf.ase.pe.zustaende;

import de.dhbw.tinf.ase.pe.Geldautomat;
import de.dhbw.tinf.ase.pe.Karte;

public class GeldautomatKeinGeld implements GeldautomatZustand {

	public  GeldautomatKeinGeld(Geldautomat geldautomat) {
		this.geldautomat = geldautomat;
	}
	
	private Geldautomat geldautomat;
	
	
	
	@Override
	public void bestuecken(int betrag) {
		if(betrag > 0) 
		{
			geldautomat.bargeld += betrag;
			geldautomat.setAktuellerZustand(new GeldautomatBereit(geldautomat));
		}
		else 
		{
			throw new IllegalArgumentException("Dem Automaten darf kein Geld entnommen werden und Nachzaehlen ist auch nicht erlaubt!");
		}
		
	}

	@Override
	public void ausgeben() {
		throw new IllegalStateException("Es wurde noch keine Karte eingeschoben!");	
	}

	@Override
	public int auszahlen(int betrag) {
		return -1;
	}

	@Override
	public int eingeben(String pin) {
		throw new IllegalStateException("Es wurde noch keine Karte eingeschoben!");	
	}

	@Override
	public void einschieben(Karte karte) {
		throw new IllegalStateException("Der Automat beinhaltet kein Bargeld, bitte versuchen Sie es ein anderes Mal!");	
		
	}

	@Override
	public int fuellstand() {
		return geldautomat.bargeld;
	}

	@Override
	public String info() {
		return "Der Automat enthaelt " + fuellstand() + " Taler.";
	}


	@Override
	public  void wasWillstDuTun() {
		System.out.println("Was willst du tun?");
		System.out.println("[1] - Info ausgeben");
		System.out.println("[2] - Geldautomat bestuecken");
		System.out.println("[3] - Karte einschieben");
		System.out.println("[7] - Fuellstand anzeigen");
		System.out.println("[8] - Programm beenden");
	}
}
