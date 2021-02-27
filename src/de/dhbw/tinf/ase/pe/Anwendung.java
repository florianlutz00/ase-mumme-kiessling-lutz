package de.dhbw.tinf.ase.pe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Anwendung {

    private static BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws Exception {
        Geldautomat geldautomat = new Geldautomat();

        System.out.println("Willkommen beim DHBW Geldautomat!");

        System.out.println(geldautomat.info());

        while (true) {
            try {

                wasWillstDuTun(geldautomat.verfuegbareOptionen());

                String input = cin.readLine();

                int aktion = 0;

                try {
                    aktion = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    System.out.println("Unzulaessige Eingabe!");
                    continue;
                }

                aktion = aktionZuAktionsnummer(aktion, geldautomat.verfuegbareOptionen());

                if (aktion == 1) {
                    System.out.println(geldautomat.info());
                } else if (aktion == 2) {
                    geldautomatBestuecken(geldautomat);
                } else if (aktion == 3) {
                    karteEinschieben(geldautomat);
                } else if (aktion == 4) {
                    pinEingeben(geldautomat);
                } else if (aktion == 5) {
                    geldAuszahlen(geldautomat);
                } else if (aktion == 6) {
                    geldautomat.ausgeben();
                    System.out.println("Deine Karte wurde wieder ausgeworfen");
                } else if (aktion == 7) {
                    System.out.println("Der Automat enthaelt " + geldautomat.fuellstand() + " Taler");
                } else if (aktion == 8) {
                    break;
                } else {
                    System.out.println("Ungueltige Aktion!");
                }
            } catch (IllegalStateException e) {
                System.out.println(e.getMessage());
            }
        }

        System.out.println("Danke dass du den DHBW Geldautomat benutzt hast :-)");
        cin.close();
    }

    private static void geldautomatBestuecken(Geldautomat geldautomat) {
        System.out.print("Bitte gib die Summe ein: ");
        try {
            String input = cin.readLine();
            int summe = Integer.parseInt(input);
            geldautomat.bestuecken(summe);
        } catch (NumberFormatException | IOException e) {
            geldautomatBestuecken(geldautomat);
        }
    }

    private static void karteEinschieben(Geldautomat geldautomat) {
        String pin = erzeugePin();
        Karte karte = new Karte(pin);
        geldautomat.einschieben(karte);
        System.out.println("Die Pin fuer deine Karte ist " + pin);
        System.out.println("Die Karte ist jetzt im Automat");
    }

    private static String erzeugePin() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            double zufall = Math.random();
            double ziffer = zufall;
            if (ziffer < 0.95) {
                ziffer *= 10;
            }
            ziffer = Math.round(ziffer);
            sb.append((int) ziffer);
        }
        return sb.toString();
    }

    private static void pinEingeben(Geldautomat geldautomat) {
        System.out.print("Bitte gib jetzt deine PIN ein: ");
        try {
            String pin = cin.readLine();
            switch (geldautomat.eingeben(pin)) {
                case 0:
                    System.out.println("PIN korrekt!");
                    break;
                case 1:
                    System.out.println("Die eingegebene PIN ist falsch, Sie haben 2 Versuche uebrig.");
                    break;
                case 2:
                    System.out.println("Die eingegebene PIN ist falsch, Sie haben 1 Versuch uebrig.");
                    break;
                case 3:
                    System.out.println("Sie haben Ihre PIN mehrmals falsch eingegeben. Ihre Karte wird nun ausgeworfen.");
                    break;
                default:
                    System.out.println("Etwas ist schief gelaufen.");
            }
        } catch (IOException e) {
            System.out.println("Etwas ist schief gelaufen - bitte noch einmal versuchen! (Fehler: " + e.getMessage() + ")");
            pinEingeben(geldautomat);
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void geldAuszahlen(Geldautomat geldautomat) {
        System.out.print("Bitte gib den gewuenschten Betrag ein: ");
        try {
            String input = cin.readLine();
            int abheben = Integer.parseInt(input);
            int summe = geldautomat.auszahlen(abheben);

            if (summe == -1) {
                System.out.println("Keine Karte oder falsche PIN - bitte noch einmal versuchen!");
            }else if (summe == abheben) {
                System.out.println(input + " Taler ausgegeben - viel Spass damit.");
            } else {
                System.out.println(summe + " Taler ausgegeben - viel Spass damit. Leider ist der Automat leer. Ihre Karte wird nun ausgeworfen.");
            }
        } catch (IOException | NumberFormatException e) {
            geldAuszahlen(geldautomat);
        }
    }


    private static int aktionZuAktionsnummer(int aktion, boolean[] verfuegbareOptionen) {
        int anzahlOptionen = 1;
        if(verfuegbareOptionen.length != 8){
            throw new IllegalArgumentException("Es muss ein Array mit 8 boolean Werten übergeben werden");
        }
        for (int i = 0; i < verfuegbareOptionen.length; i++) {
            if (verfuegbareOptionen[i]) {
                if (aktion == anzahlOptionen) {
                    return i + 1;
                }
                anzahlOptionen++;
            }
        }
        return -1;
    }

    private static void wasWillstDuTun(boolean[] verfuegbareOptionen) {
        System.out.println("Was willst du tun?");
        if(verfuegbareOptionen.length != 8){
            throw new IllegalArgumentException("Es muss ein Array mit 8 boolean Werten übergeben werden");
        }
        int anzahlOptionen = 1;
        if (verfuegbareOptionen[0]) {
            System.out.println("[" + anzahlOptionen + "] - Info ausgeben");
            anzahlOptionen++;
        }
        if (verfuegbareOptionen[1]) {
            System.out.println("[" + anzahlOptionen + "] - Geldautomat bestuecken");
            anzahlOptionen++;
        }
        if (verfuegbareOptionen[2]) {
            System.out.println("[" + anzahlOptionen + "] - Karte einschieben");
            anzahlOptionen++;
        }
        if (verfuegbareOptionen[3]) {
            System.out.println("[" + anzahlOptionen + "] - PIN eingeben");
            anzahlOptionen++;
        }
        if (verfuegbareOptionen[4]) {
            System.out.println("[" + anzahlOptionen + "] - Geld auszahlen");
            anzahlOptionen++;
        }
        if (verfuegbareOptionen[5]) {
            System.out.println("[" + anzahlOptionen + "] - Karte entnehmen");
            anzahlOptionen++;
        }
        if (verfuegbareOptionen[6]) {
            System.out.println("[" + anzahlOptionen + "] - Fuellstand anzeigen");
            anzahlOptionen++;
        }
        if (verfuegbareOptionen[7]) {
            System.out.println("[" + anzahlOptionen + "] - Programm beenden");
        }
    }

}
