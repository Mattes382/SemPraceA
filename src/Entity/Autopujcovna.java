/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import AbstrDoubleList.AbstrDoubleList;
import static Entity.EnumPozice.AKTUALNI;
import static Entity.EnumPozice.POSLEDNI;
import java.io.Serializable;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Matej
 */
public class Autopujcovna implements IAutopujcovna, Serializable {

    private AbstrDoubleList<Pobocka> pobocky;
    private AbstrDoubleList<Auto> vypujcenaAuta;
    private int pocetPujcovenVSeznamu;
    private int pocetVypujcenychAut;

    public Autopujcovna(AbstrDoubleList<Pobocka> pobocky, AbstrDoubleList<Auto> vypujcenaAuta) {
        this.pobocky = pobocky;
        this.vypujcenaAuta = vypujcenaAuta;
        this.pocetPujcovenVSeznamu = pobocky.pocetPrvku();
        this.pocetVypujcenychAut = vypujcenaAuta.pocetPrvku();

    }

    public int getPocetVypujcenychAut() {
        return pocetVypujcenychAut;
    }

    public void setPocetVypujcenychAut(int pocetVypujcenychAut) {
        this.pocetVypujcenychAut = pocetVypujcenychAut;
    }

    public AbstrDoubleList<Pobocka> getPobocky() {
        return pobocky;
    }

    public void setPobocky(AbstrDoubleList<Pobocka> pobocky) {
        this.pobocky = pobocky;
    }

    public AbstrDoubleList<Auto> getVypujcenaAuta() {
        return vypujcenaAuta;
    }

    public void setVypujcenaAuta(AbstrDoubleList<Auto> vypujcenaAuta) {
        this.vypujcenaAuta = vypujcenaAuta;
    }

    public int getPocetPujcovenVSeznamu() {
        return pocetPujcovenVSeznamu;
    }

    public void setPocetPujcovenVSeznamu(int pocetPujcovenVSeznamu) {
        this.pocetPujcovenVSeznamu = pocetPujcovenVSeznamu;
    }

    @Override
    public void vlozPobocku(IPobocka Pobocka, EnumPozice Pozice) {
        if (Pobocka != null && !(Pobocka instanceof Pobocka)) {

            Pobocka pobocka = (Pobocka) Pobocka;
            switch (Pozice) {
                case PRVNI:
                    pobocky.vlozPrvni(pobocka); //mame vkladat interface????
                    break;
                case POSLEDNI:
                    pobocky.vlozPosledni(pobocka);
                    break;
                case PREDCHUDCE:
                    pobocky.vlozPredchudce(pobocka);
                    break;
                case NASLEDNIK:
                    pobocky.vlozNaslednika(pobocka);
                    break;
                case AKTUALNI: {
                    try {
                        throw new Exception("Pobočku nelze vložit na aktuální místo");
                    } catch (Exception ex) {
                        Logger.getLogger(Pobocka.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
            }
            pocetPujcovenVSeznamu++;
        }
    }

    @Override
    public IPobocka zpristupniPobocku(EnumPozice Pozice) {
        IPobocka pobocka = null;
        switch (Pozice) {
            case PRVNI:
                pobocka = pobocky.zpristupniPrvni();
                break;
            case POSLEDNI:
                pobocka = pobocky.zpristupniPosledni();
                break;
            case PREDCHUDCE:
                pobocka = pobocky.zpristupniPredchudce();
                break;
            case NASLEDNIK:
                pobocka = pobocky.zpristupniNaslednika();
                break;
            case AKTUALNI:
                pobocka = pobocky.zpristupniAktualni();
                break;
        }
        return pobocka;
    }

    @Override
    public IPobocka odeberPobocku(EnumPozice Pozice) {
        IPobocka pobocka = null;
        switch (Pozice) {
            case PRVNI:
                pobocka = pobocky.odeberPrvni();
                break;
            case POSLEDNI:
                pobocka = pobocky.odeberPosledni();
                break;
            case PREDCHUDCE:
                pobocka = pobocky.odeberPredchudce();
                break;
            case NASLEDNIK:
                pobocka = pobocky.odeberNaslednika();
                break;
            case AKTUALNI:
                pobocka = pobocky.odeberAktualni();
                break;
        }
        return pobocka;
    }

    @Override
    public void vlozAuto(Auto auto, EnumPozice Pozice) {
        if (auto != null) {
            if (Pozice == AKTUALNI) {
                try {
                    throw new Exception("Auto nelze vložit na aktuální místo");
                } catch (Exception ex) {
                    Logger.getLogger(Pobocka.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                pobocky.zpristupniAktualni().vlozAuto(auto, Pozice);
            }

        }

        pobocky.zpristupniAktualni().setPocetAutVSeznamu(pocetPujcovenVSeznamu++);
    }

    @Override
    public Auto zpristupnAuto(EnumPozice Pozice) {
        Auto auto = pobocky.zpristupniAktualni().zpristupnAuto(Pozice);

        return auto;
    }

    @Override
    public Auto odeberAuto(EnumPozice Pozice) {
        Auto auto = pobocky.zpristupniAktualni().odeberAuto(Pozice);
        pobocky.zpristupniAktualni().setPocetAutVSeznamu(pocetPujcovenVSeznamu--);
        return auto;
    }

    @Override
    public Auto vypujcAuto(EnumPozice Pozice) {
        Auto auto = pobocky.zpristupniAktualni().zpristupnAuto(Pozice);
        pobocky.zpristupniAktualni().odeberAuto(Pozice);
        vypujcenaAuta.vlozPrvni(auto);
        setPocetVypujcenychAut(pocetVypujcenychAut++);
        return auto;
    }

    @Override
    public Auto vratAuto(EnumPozice Pozice) {
        Auto auto = null;
        switch (Pozice) {
            case PRVNI:
                auto = vypujcenaAuta.odeberPrvni();
                break;
            case POSLEDNI:
                auto = vypujcenaAuta.odeberPosledni();
                break;
            case PREDCHUDCE:
                auto = vypujcenaAuta.odeberPredchudce();
                break;
            case NASLEDNIK:
                auto = vypujcenaAuta.odeberNaslednika();
                break;
            case AKTUALNI:
                auto = vypujcenaAuta.odeberAktualni();
                break;
        }
        int pocetVypujceni = auto.getPocetVypujceni();
        auto.setPocetVypujceni(pocetVypujceni++);
        setPocetVypujcenychAut(pocetVypujcenychAut--);
        pobocky.zpristupniAktualni().vlozAuto(auto, POSLEDNI);
        return auto;
    }

    @Override
    public Auto zpristupniVypujceneAuto(EnumPozice Pozice) {
        Auto auto = null;
        switch (Pozice) {
            case PRVNI:
                auto = vypujcenaAuta.zpristupniPrvni();
                break;
            case POSLEDNI:
                auto = vypujcenaAuta.zpristupniPosledni();
                break;
            case PREDCHUDCE:
                auto = vypujcenaAuta.zpristupniPredchudce();
                break;
            case NASLEDNIK:
                auto = vypujcenaAuta.zpristupniNaslednika();
                break;
            case AKTUALNI:
                auto = vypujcenaAuta.zpristupniAktualni();
                break;
        }
        return auto;
    }

    @Override
    public Iterator iterator(eTyp typ) {
        Iterator iterator = null;
        switch (typ) {
            case AUTA:
                iterator = pobocky.zpristupniAktualni().iterator();
                break;
            case POBOCKY:
                iterator = pobocky.iterator();
                break;
            case VYPUJCENE_AUTA:
                iterator = vypujcenaAuta.iterator();
                break;
        }

        return iterator;
    }

    @Override
    public void zrusPobocku() {
        pobocky.odeberAktualni();
    }

    @Override
    public void zrus() {
        pocetPujcovenVSeznamu = 0;
        pobocky.zrus();
        vypujcenaAuta.zrus();
    }

    @Override
    public String toString() {
        return "Autopujcovna";
    }






}
