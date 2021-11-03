/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import struktury.AbstrDoubleList;
import java.io.Serializable;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import struktury.AbstrTable;
import struktury.IAbstrTable;
import struktury.eTypProhl;

/**
 *
 * @author Matej
 */
public class Pobocka implements IPobocka, Serializable{

    private String jmenoPobocky;
    private AbstrTable<String, Auto> seznamAut = new AbstrTable<>();
    private int pocetAutVSeznamu;

    public Pobocka(String jmenoPobocky, AbstrTable<String, Auto> seznamAut) {
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

    public AbstrTable<String, Auto> getSeznamAut() {
        return seznamAut;
    }

    public void setSeznamAut(AbstrTable<String, Auto> seznamAut) {
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
    public void vlozAuto(Auto auto) {   
        seznamAut.vloz(auto.getSpz(), auto);
        pocetAutVSeznamu++;
    }
    

    @Override
    public Auto zpristupnAuto() {
        Auto auto = null;
        //auto = seznamAut.
        return auto;
    }

    @Override
    public Auto odeberAuto() {
        Auto auto = null;
        //
        pocetAutVSeznamu--;
        return auto;
    }

    @Override
    public Iterator iterator() {
        return seznamAut.vytvorIterator(eTypProhl.HLOUBKA);
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
