package de.dhbw.tinf.ase.pe.zustaende;

import de.dhbw.tinf.ase.pe.Geldautomat;
import de.dhbw.tinf.ase.pe.Karte;

public class GeldautomatKeinGeld extends GeldautomatZustand {

    public GeldautomatKeinGeld(Geldautomat geldautomat) {
        super(geldautomat, new boolean[]{true,
                        true,
                        false,
                        false,
                        false,
                        false,
                        true,
                        true
                });
    }

    @Override
    public void bestuecken(int betrag) {
        if (betrag > 0) {
            geldautomat.bargeld += betrag;
            geldautomat.setAktuellerZustand(new GeldautomatBereit(geldautomat));

        } else {
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
}
