/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.util.Iterator;
import struktury.eTypProhl;

/**
 *
 * @author Matej
 */
public interface IPobocka{

    void vlozAuto(Auto auto);// - vloží nové auto do seznamu na příslušnou pozici (první, poslední, předchůdce, následník)

    //Auto zpristupnAuto();// - zpřístupní auto z požadované pozice (první, poslední, předchůdce, následník, aktuální),

    Auto odeberAuto(Auto auto);// - odebere auto z požadované pozice (první, poslední, předchůdce, následník, aktuální),

    Iterator iterator(eTypProhl typProhledavani);//-vytvoří iterátor s moznosti prohledavat do hloubky nebo sirky
    
    Auto hledejAuto(String spz); // vyhleda auto v pobocce

    void zrus(); //– zruší všechny auta.
}
