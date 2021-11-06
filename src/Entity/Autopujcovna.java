/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import struktury.AbstrDoubleList;
import static Entity.EnumPozice.AKTUALNI;
import static Entity.EnumPozice.POSLEDNI;
import java.io.Serializable;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import struktury.AbstrTable;
import struktury.eTypProhl;

/**
 *
 * @author Matej
 */
public class Autopujcovna implements IAutopujcovna, Serializable {

    private AbstrDoubleList<Pobocka> pobocky;
    private AbstrDoubleList<Auto> vypujcenaAuta;
    private Auto hledaneAuto = null;
    private int pocetPujcovenVSeznamu;
    private int pocetVypujcenychAut;
    private eTypProhl typProhledavni = eTypProhl.HLOUBKA;

    public Autopujcovna(AbstrDoubleList<Pobocka> pobocky, AbstrDoubleList<Auto> vypujcenaAuta) {
        this.pobocky = pobocky;
        this.vypujcenaAuta = vypujcenaAuta;
        this.pocetPujcovenVSeznamu = pobocky.pocetPrvku();
        this.pocetVypujcenychAut = vypujcenaAuta.pocetPrvku();

    }

    public Autopujcovna() {
        this.pobocky = new AbstrDoubleList<>();
        this.vypujcenaAuta = new AbstrDoubleList<>();
        this.pocetPujcovenVSeznamu = 0;
        this.pocetVypujcenychAut = 0;
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

    public void setTypProhledavni(eTypProhl typProhledavni) {
        this.typProhledavni = typProhledavni;
    }

    @Override
    public void vlozPobocku(IPobocka Pobocka, EnumPozice Pozice) {
        if (Pobocka != null) {

            Pobocka pobocka = (Pobocka) Pobocka;
            switch (Pozice) {
                case PRVNI:
                    pobocky.vlozPrvni(pobocka);
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
        pocetPujcovenVSeznamu--;
        return pobocka;
    }

    @Override
    public void vlozAuto(Auto auto) {
        if (auto != null) {
            pobocky.zpristupniAktualni().vlozAuto(auto);
        }

    }

    @Override
    public Auto hledejAutoVPobocce(String spz) {
        Auto tmp = pobocky.zpristupniAktualni().hledejAuto(spz);
        if (tmp != null) {
            this.hledaneAuto = tmp;
            return hledaneAuto;
        } else {
            return null;
        }
    }

    @Override
    public Auto hledejAuto(String spz) {
        Iterator<Pobocka> it = pobocky.iterator();
        Pobocka tmpPobocka = pobocky.zpristupniAktualni();
        pobocky.zpristupniPrvni();
        while (it.hasNext()) {
            Pobocka akt = it.next();
            AbstrTable<String, Auto> seznamAut = akt.getSeznamAut();
            Auto tmpAuto = seznamAut.najdi(spz);
            if (tmpAuto != null) {
                this.hledaneAuto = tmpAuto;
                return hledaneAuto;
            }
            pobocky.zpristupniNaslednika();
        }
        it = pobocky.iterator();
        pobocky.zpristupniPrvni();
        while (it.hasNext()) {
            if (tmpPobocka == it.next()) {
                break;
            } else {
                pobocky.zpristupniNaslednika();
            }
        }
        return null;
    }

    @Override
    public Auto odeberAutoPodleKlice(String spz) {
        Auto auto = hledejAuto(spz);
        if (auto != null) {
            odeberAuto();
            return auto;
        } else {
            return null;
        }
    }

    @Override
    public Auto zpristupnAuto() {
        return hledaneAuto;

    }

    @Override
    public Auto odeberAuto() { // odebere vybrane auto
        Auto auto = null;
        auto = pobocky.zpristupniAktualni().odeberAuto(hledaneAuto);
        return auto;
    }

    @Override
    public Auto vypujcAuto() {
        Auto auto = hledaneAuto;
        odeberAuto();
        vypujcenaAuta.vlozPrvni(auto);
        pocetVypujcenychAut++;
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
        auto.setPocetVypujceni(auto.getPocetVypujceni() + 1);
        auto.setStavKm(auto.getStavKm() + (int) (Math.random() * 1000));
        pocetVypujcenychAut--;
        pobocky.zpristupniAktualni().vlozAuto(auto);
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
                if (pobocky.zpristupniAktualni() != null) {
                    System.out.println(typProhledavni);
                    iterator = pobocky.zpristupniAktualni().iterator(typProhledavni);
                }
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
        if (pobocky.zpristupniAktualni() != null) {
            pobocky.zpristupniAktualni().zrus();
        }

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
