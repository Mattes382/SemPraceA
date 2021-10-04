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
public class UzitkoveAuto extends Auto{
    private int nosnost;

    public UzitkoveAuto(int nosnost, double hmotnost, String spz, int stavKm, int pocetVypujceni) {
        super(hmotnost, spz, stavKm, pocetVypujceni);
        this.nosnost = nosnost;
    }

    public int getNosnost() {
        return nosnost;
    }

    public void setNosnost(int nosnost) {
        this.nosnost = nosnost;
    }

    @Override
    public String toString() {
        return "UzitkoveAuto{" + "nosnost=" + nosnost + ", "+ super.toString();
    }


    
    
}
