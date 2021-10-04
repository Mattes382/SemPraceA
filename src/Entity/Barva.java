/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.awt.Color;

/**
 *
 * @author Matej
 */
public enum Barva {
        BILA(Color.WHITE, "bílá"),
    CERNA(Color.BLACK, "černá"),
    CERVENA(Color.RED, "červená"),
    ZELENA(Color.GREEN, "zelená"),
    MODRA(Color.BLUE, "modrá");

    private final Color color;
    private final String nazev;

    private Barva(Color color, String cesky) {
        this.color = color;
        this.nazev = cesky;
    }

    public Color getColor() {
        return color;
    }

    public String getNazev() {
        return nazev;
    }

    @Override
    public String toString() {
       return nazev;
    }
}
