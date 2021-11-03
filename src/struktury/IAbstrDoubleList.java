package struktury;

import java.util.Iterator;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Matej
 * @param <T>
 */
public interface IAbstrDoubleList<T> extends Iterable<T>{

//-zrušení celého seznamu,
    void zrus();

//-test naplněnosti seznamu,
    boolean jePrazdny();

//-vložení prvku do seznamu na první místo
    void vlozPrvni(T data);

//-vložení prvku do seznamu na poslední místo,
    void vlozPosledni(T data);

//-vložení prvku do seznamu jakožto následníka aktuálního prvku,
    void vlozNaslednika(T data);

//-vložení prvku do seznamu jakožto předchůdce aktuálního prvku,
    void vlozPredchudce(T data);

//-zpřístupnění aktuálního prvku seznamu,
    T zpristupniAktualni();

//-zpřístupnění prvního prvku seznamu,
    T zpristupniPrvni();

//-zpřístupnění posledního prvku seznamu,
    T zpristupniPosledni();

//-zpřístupnění následníka aktuálního prvku, 
    T zpristupniNaslednika();

//-zpřístupnění předchůdce aktuálního prvku,
    T zpristupniPredchudce();
//Pozn. Operace typu zpřístupni, přenastavují pozici aktuálního prvku

//-odebrání (vyjmutí) aktuálního prvku ze seznamu poté je aktuální prvek nastaven na první prvek
    T odeberAktualni();

//-odebrání prvního prvku ze seznamu,
    T odeberPrvni();

//-odebrání posledního prvku ze seznamu, 
    T odeberPosledni();

//-odebrání následníka aktuálního prvku ze seznamu,
    T odeberNaslednika();

//-odebrání předchůdce aktuálního prvku ze seznamu,
    T odeberPredchudce();

// -vytvoří iterátor (dle rozhraní Iterable)
    @Override
    Iterator<T> iterator();

    // vrátí počet prvků v seznamu
    int pocetPrvku();
}
