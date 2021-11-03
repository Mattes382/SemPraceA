/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package struktury;

import java.util.Iterator;

/**
 *
 * @author Matej
 */
public interface IAbstrTable<K extends Comparable<K>, V> {

    void zrus();

    boolean jePrazdny();

    V najdi(K key);

    void vloz(K key, V value) throws Exception;

    V odeber(K key) throws NullPointerException;

    Iterator<V> vytvorIterator(eTypProhl typ);
}
