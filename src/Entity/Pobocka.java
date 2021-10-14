/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import AbstrDoubleList.AbstrDoubleList;
import java.io.Serializable;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Matej
 */
public class Pobocka implements IPobocka, Serializable{

    private String jmenoPobocky;
    private AbstrDoubleList<Auto> seznamAut;
    private int pocetAutVSeznamu;

    public Pobocka(String jmenoPobocky, AbstrDoubleList<Auto> seznamAut) {
        this.jmenoPobocky = jmenoPobocky;
        this.seznamAut = seznamAut;
        this.pocetAutVSeznamu = seznamAut.pocetPrvku();
    }

    public String getJmenoPobocky() {
        return jmenoPobocky;
    }

    public void setJmenoPobocky(String jmenoPobocky) {
        this.jmenoPobocky = jmenoPobocky;
    }

    public AbstrDoubleList<Auto> getSeznamAut() {
        return seznamAut;
    }

    public void setSeznamAut(AbstrDoubleList<Auto> seznamAut) {
        this.seznamAut = seznamAut;
        this.pocetAutVSeznamu = seznamAut.pocetPrvku();
    }

    public int getPocetAutVSeznamu() {
        return pocetAutVSeznamu;
    }

    public void setPocetAutVSeznamu(int pocetAutVSeznamu) {
        this.pocetAutVSeznamu = pocetAutVSeznamu;
    }

    @Override
    public void vlozAuto(Auto auto, EnumPozice Pozice) {
        if (auto != null) {
            switch (Pozice) {
                case PRVNI:
                    seznamAut.vlozPrvni(auto);
                    break;
                case POSLEDNI:
                    seznamAut.vlozPosledni(auto);
                    break;
                case PREDCHUDCE:
                    seznamAut.vlozPredchudce(auto);
                    break;
                case NASLEDNIK:
                    seznamAut.vlozNaslednika(auto);
                    break;
                case AKTUALNI: {
                    try {
                        throw new Exception("Auto nelze vložit na aktuální místo");
                    } catch (Exception ex) {
                        Logger.getLogger(Pobocka.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
            }
            pocetAutVSeznamu++;
        }
    }

    @Override
    public Auto zpristupnAuto(EnumPozice Pozice) {
        Auto auto = null;
        switch (Pozice) {
            case PRVNI:
                auto = seznamAut.zpristupniPrvni();
                break;
            case POSLEDNI:
                auto = seznamAut.zpristupniPosledni();
                break;
            case PREDCHUDCE:
                auto = seznamAut.zpristupniPredchudce();
                break;
            case NASLEDNIK:
                auto = seznamAut.zpristupniNaslednika();
                break;
            case AKTUALNI:
                auto = seznamAut.zpristupniAktualni();
                break;
        }
        return auto;
    }

    @Override
    public Auto odeberAuto(EnumPozice Pozice) {
        Auto auto = null;
        switch (Pozice) {
            case PRVNI:
                auto = seznamAut.odeberPrvni();
                break;
            case POSLEDNI:
                auto = seznamAut.odeberPosledni();
                break;
            case PREDCHUDCE:
                auto = seznamAut.odeberPredchudce();
                break;
            case NASLEDNIK:
                auto = seznamAut.odeberNaslednika();
                break;
            case AKTUALNI:
                auto = seznamAut.odeberAktualni();
                break;
        }
        pocetAutVSeznamu--;
        return auto;
    }

    @Override
    public Iterator iterator() {
        return seznamAut.iterator();
    }

    @Override
    public void zrus() {
        pocetAutVSeznamu = 0;
        seznamAut.zrus();
    }

    @Override
    public String toString() {
        return "Pobocka{" + "jmenoPobocky=" + jmenoPobocky + ", pocetAutVSeznamu=" + pocetAutVSeznamu + '}';
    }

    
}
