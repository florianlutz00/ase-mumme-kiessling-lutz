package de.dhbw.tinf.ase.pe.zustaende;

import de.dhbw.tinf.ase.pe.Geldautomat;
import de.dhbw.tinf.ase.pe.Karte;

public class GeldautomatKarteDrin implements GeldautomatZustand{
	
	public  GeldautomatKarteDrin(Geldautomat geldautomat) {
		this.geldautomat = geldautomat;
	}
	
	private Geldautomat geldautomat;

	@Override
	public void ausgeben() {
		geldautomat.karte = null;
		geldautomat.pinFalsch = 0;
		geldautomat.setAktuellerZustand(new GeldautomatBereit(geldautomat));
	}

	@Override
	public int auszahlen(int betrag) {
		return -1;
	}

	@Override
	public void bestuecken(int betrag) {
		throw new IllegalStateException("Bestuecken des Automaten waehrend Transaktion nicht moeglich!");
	}

	@Override
	public int eingeben(String pin) {
		try 
		{
			Integer.parseInt(pin);
		} 
		catch (NumberFormatException e) 
		{
			throw new IllegalArgumentException("PIN muss aus Zahlen bestehen!", e);
		}

		if (pin.length() != 4) 
		{
			throw new IllegalArgumentException("PIN muss vierstellig sein1");
		}
			if (geldautomat.karte.istKorrekt(pin)) 
			{
				geldautomat.setAktuellerZustand(new GeldautomatPinKorrekt(geldautomat));
				return 0;
			} 
			else 
			{
				geldautomat.pinFalsch++;
				if (geldautomat.pinFalsch > 2) 
				{
					ausgeben();
					geldautomat.pinFalsch = 0;
					return 3;
				}
				return geldautomat.pinFalsch;
			}
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
		return "Falsche PIN oder PIN nicht eingegeben - Abhebung nicht moeglich!";
	}


	@Override
	public  void wasWillstDuTun() {
		System.out.println("Was willst du tun?");
		System.out.println("[1] - Info ausgeben");
		System.out.println("[4] - PIN eingeben");
		System.out.println("[6] - Karte entnehmen");
		System.out.println("[7] - Fuellstand anzeigen");
		System.out.println("[8] - Programm beenden");
	}
}
