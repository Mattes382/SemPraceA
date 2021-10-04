/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

/**
 *
 * @author Matej
 */
public class OsobniAuto extends Auto{

    private Barva barva;
    
    public OsobniAuto(double hmotnost, String spz, int stavKm, int pocetVypujceni, Barva barva) {
        super(hmotnost, spz, stavKm, pocetVypujceni);
        this.barva = barva;
    }

    public Barva getBarva() {
        return barva;
    }

    public void setBarva(Barva barva) {
        this.barva = barva;
    }

    @Override
    public String toString() {
        return "OsobniAuto{" + "barva=" + barva.getNazev() +", "+ super.toString();
    }
    
    
}
