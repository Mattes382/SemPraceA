/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package generator;

import AbstrDoubleList.AbstrDoubleList;
import Entity.Auto;
import Entity.Autopujcovna;
import Entity.Barva;
import Entity.OsobniAuto;
import Entity.Pobocka;
import Entity.UzitkoveAuto;
import java.util.Random;

/**
 *
 * @author Matej
 */
public class Generator {

    static String[] nazvyPobocek = {"Pardubice", "Hradec Králové", "Praha",
        "České Budějovice", "Zlín", "Karlovy Vary", "Ústí nad Labem", "Brno",
        "Ostrava", "Olomouc", "Kolín", "Plzeň", "Jihlava", "Liberec"
    };

    public static int nahodneCislo(int min, int max) {

        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

    public static boolean getRandomBoolean() {
        Random random = new Random();
        return random.nextBoolean();
    }

    public static String nahodnaSpz() {
        StringBuilder s = new StringBuilder();

        char cislo1 = (char) (Math.random() * 10 + '0');
        s.append(cislo1);
        char pismeno = (char) (Math.random() * 26 + 'A');
        s.append(pismeno);
        char cislo2 = (char) (Math.random() * 10 + '0');
        s.append(cislo2);

        for (int i = 0; i < 4; i++) {
            char cislo = (char) (Math.random() * 10 + '0');
            s.append(cislo);
        }
        return s.toString();
    }

    public static Auto vytvorNahodneAuto() {
        Auto vysledek = null;
        int nahodnaHmotnost = nahodneCislo(100, 5000);
        int typDopravnihoProstredku = nahodneCislo(1, 2);
        int nahodnyPocetVypujceni = nahodneCislo(0, 500);
        int nahodnyStavKm = nahodnyPocetVypujceni * (1 / nahodneCislo(2, 8));

        switch (typDopravnihoProstredku) {
            case 1:
                int nahodnaNosnost = nahodnaHmotnost * nahodneCislo(50, 1000);

                vysledek = new UzitkoveAuto(nahodnaNosnost, nahodnaHmotnost, nahodnaSpz(), nahodnyStavKm, nahodnyPocetVypujceni);
                break;
            case 2:
                int nahodnaBarva = nahodneCislo(1, 5);
                Barva barva = null;
                switch (nahodnaBarva) {
                    case 1:
                        barva = Barva.BILA;
                        break;
                    case 2:
                        barva = Barva.CERNA;
                        break;
                    case 3:
                        barva = Barva.CERVENA;
                        break;
                    case 4:
                        barva = Barva.MODRA;
                        break;
                    case 5:
                        barva = Barva.ZELENA;
                        break;
                }
                vysledek = new OsobniAuto(nahodnaHmotnost, nahodnaSpz(), nahodnyStavKm, nahodnyPocetVypujceni, barva);
                break;
        }
        return vysledek;
    }

    public static Pobocka vytvorNahodnouPobocku(int pocetAut) {
        String nahodneJmenoPobocky = nazvyPobocek[nahodneCislo(0, nazvyPobocek.length - 1)];
        AbstrDoubleList<Auto> auta = generujAuta(pocetAut);
        Pobocka pobocka = new Pobocka(nahodneJmenoPobocky, auta);
        return pobocka;
    }
    
    public static Autopujcovna vytvorNahodnouAutoPujcovnu(int pocetPobocek){
        AbstrDoubleList<Pobocka> pobocky = generujPobocky(pocetPobocek);
        int nahodnyPocetAut = nahodneCislo(1, 25);
        AbstrDoubleList<Auto> vypujcenaAuta = generujAuta(nahodnyPocetAut);
        Autopujcovna autopujcovna = new Autopujcovna(pobocky, vypujcenaAuta);
        return autopujcovna;
    }

    public static AbstrDoubleList<Pobocka> generujPobocky(int pocetPobocek) {
        AbstrDoubleList<Pobocka> p = new AbstrDoubleList<>();

        for (int i = 0; i < pocetPobocek; i++) {
            int nahodnyPocetAut = nahodneCislo(1, 100);
            p.vlozPosledni(vytvorNahodnouPobocku(nahodnyPocetAut));
        }
        return p;
    }
    
        public static AbstrDoubleList<Autopujcovna> generujAutoPujcovny(int pocetAutoPujcoven) {
        AbstrDoubleList<Autopujcovna> ap = new AbstrDoubleList<>();

        for (int i = 0; i < pocetAutoPujcoven; i++) {
            int nahodnyPocetPobocek = nahodneCislo(1, 14);
            ap.vlozPosledni(vytvorNahodnouAutoPujcovnu(nahodnyPocetPobocek));
        }
        return ap;
    }

    public static AbstrDoubleList<Auto> generujAuta(int pocetAut) {
        AbstrDoubleList<Auto> a = new AbstrDoubleList<>();
        for (int i = 0; i < pocetAut; i++) {
            a.vlozPosledni(vytvorNahodneAuto());
        }
        return a;
    }

}
