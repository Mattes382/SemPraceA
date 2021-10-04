/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.util.Iterator;

/**
 *
 * @author Matej
 */
public interface IPobocka{

    void vlozAuto(Auto auto, EnumPozice Pozice);// - vloží nové auto do seznamu na příslušnou pozici (první, poslední, předchůdce, následník)

    Auto zpristupnAuto(EnumPozice Pozice);// - zpřístupní auto z požadované pozice (první, poslední, předchůdce, následník, aktuální),

    Auto odeberAuto(EnumPozice Pozice);// - odebere auto z požadované pozice (první, poslední, předchůdce, následník, aktuální),

    Iterator iterator();//-vytvoří iterátor

    void zrus(); //– zruší všechny auta.
}
