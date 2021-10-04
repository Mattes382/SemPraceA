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
public interface IAutopujcovna{

    /* - vloží 
        novou pobočku do seznamu na příslušnou pozici (první, poslední, předchůdce, následník)
     */
    void vlozPobocku(IPobocka Pobocka, EnumPozice Pozice);

    IPobocka zpristupniPobocku(EnumPozice Pozice);

    /* - zpřístupní 
        pobočku z požadované pozice (první, poslední, předchůdce, následník, aktuální),*/
    IPobocka odeberPobocku(EnumPozice Pozice);

    /* - odebere pobočku
    z požadované pozice (první, poslední, předchůdce, následník, aktuální),*/
    void vlozAuto(Auto auto, EnumPozice Pozice);

    /*  - vloží nové auto do 
    seznamu aktuální pobočky na příslušnou pozici (první, poslední, předchůdce, následník)*/
    Auto zpristupnAuto(EnumPozice Pozice); // - zpřístupní auto z požadované 

    /*pozice aktuální pobočky (první, poslední, předchůdce, následník, aktuální),*/
    Auto odeberAuto(EnumPozice Pozice);//- odebere auto z požadované pozice (první, poslední, předchůdce, následník, aktuální),

    Auto vypujcAuto(EnumPozice Pozice);/* - odebere auto z požadované pozice aktuální pobočky a vloží ho do seznamu výpůjček (první, poslední, předchůdce, 
    následník, aktuální),*/

    Auto vratAuto(EnumPozice Pozice);/* - odebere auto z požadované pozice výpůjček a vloží ho do seznamu aktuální pobočky (první, poslední, předchůdce, následník, 
    aktuální),*/

    Auto zpristupniVypujceneAuto(EnumPozice Pozice);/* - zpřístupní 
    auto z požadované pozice ze seznamu vypůjčených aut (první, poslední, předchůdce, 
    následník, aktuální),*/
    Iterator iterator(eTyp typ);/*- vrací požadovaný iterátor
    Poboček/Automobilů/Vypůjčených automobilů*/
    void zrusPobocku();//– zruší všechny auta v aktuální pobočce

    void zrus();//– zruší všechny pobočky
}
