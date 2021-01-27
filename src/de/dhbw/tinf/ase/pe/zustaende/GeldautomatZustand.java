package de.dhbw.tinf.ase.pe.zustaende;

import de.dhbw.tinf.ase.pe.Karte;

public interface GeldautomatZustand {

    public void ausgeben();

    public int auszahlen(int betrag);

    public void bestuecken(int betrag);

    public int eingeben(String pin);

    public void einschieben(Karte karte);

    public int fuellstand();

    public String info();

    public void wasWillstDuTun();
}
