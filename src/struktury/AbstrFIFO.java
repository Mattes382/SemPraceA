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
public class AbstrFIFO<T> implements IAbstrFIFO<T> {

    private final IAbstrDoubleList seznam;

    public AbstrFIFO() {
        this.seznam = new AbstrDoubleList();
    }

    @Override
    public void zrus() {
        seznam.zrus();
    }

    @Override
    public boolean jePrazdny() {
        return seznam.jePrazdny();
    }

    @Override
    public void vloz(T data) {
        seznam.vlozPosledni(data);
    }

    @Override
    public T odeber() {
        return (T) seznam.odeberPrvni();
    }

    @Override
    public Iterator vytvorIterator() {
        return iterator();
    }

    @Override
    public Iterator<T> iterator() {
        return seznam.iterator();
    }
}
