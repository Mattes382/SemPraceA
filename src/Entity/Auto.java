/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;

/**
 *
 * @author Matej
 */
public abstract class Auto implements Serializable{

    private double hmotnost;
    private String spz;
    private int stavKm = 0;
    private int pocetVypujceni = 0;

    public Auto(double hmotnost, String spz, int stavKm, int pocetVypujceni) {
        this.hmotnost = hmotnost;
        this.spz = spz;
        this.stavKm = stavKm;
        this.pocetVypujceni = pocetVypujceni;
    }

    public Auto(double hmotnost, String spz) {
        this.hmotnost = hmotnost;
        this.spz = spz;
    }
    

    public double getHmotnost() {
        return hmotnost;
    }

    public void setHmotnost(double hmotnost) {
        this.hmotnost = hmotnost;
    }

    public String getSpz() {
        return spz;
    }

    public void setSpz(String spz) {
        this.spz = spz;
    }

    public int getStavKm() {
        return stavKm;
    }

    public void setStavKm(int stavKm) {
        this.stavKm = stavKm;
    }

    public int getPocetVypujceni() {
        return pocetVypujceni;
    }

    public void setPocetVypujceni(int pocetVypujceni) {
        this.pocetVypujceni = pocetVypujceni;
    }

    @Override
    public String toString() {
        return "hmotnost=" + hmotnost + ", spz=" + spz + ", stavKm=" + stavKm + ", pocetVypujceni=" + pocetVypujceni + '}';
    }
    
    
}
