package de.dhbw.tinf.ase.pe.zustaende;

import de.dhbw.tinf.ase.pe.Geldautomat;
import de.dhbw.tinf.ase.pe.Karte;

public abstract class GeldautomatZustand {

    protected boolean[] verfuegbareOptionen;
    protected Geldautomat geldautomat;

    public GeldautomatZustand(Geldautomat geldautomat, boolean[] verfuegbareOptionen) {
        this.geldautomat = geldautomat;
        this.verfuegbareOptionen = verfuegbareOptionen;
    }


    public abstract void ausgeben();

    public abstract int auszahlen(int betrag);

    public abstract void bestuecken(int betrag);

    public abstract int eingeben(String pin);

    public abstract void einschieben(Karte karte);

    public abstract int fuellstand();

    public abstract String info();

    public boolean[] getVerfuegbareOptionen() {
        return verfuegbareOptionen;
    }
}
