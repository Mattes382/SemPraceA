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
public interface IAbstrFIFO<T> extends Iterable<T> {

    void zrus();

    boolean jePrazdny();

    void vloz(T data);

    T odeber();

    Iterator vytvorIterator();
}
