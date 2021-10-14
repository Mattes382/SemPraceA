package AbstrDoubleList;

import java.io.Serializable;
import java.util.Iterator;


public class AbstrDoubleList<T> implements IAbstrDoubleList<T>, Serializable {

    private int pocetPrvku = 0;
    private Prvek prvni;
    private Prvek aktualni;
    private Prvek posledni;

    public class Prvek implements Serializable { //vnitrni trida

        public T data;
        public Prvek predchozi;
        public Prvek nasledujici;

        Prvek(T data) {
            this.predchozi = null;
            this.data = data;
            this.nasledujici = null;
        }
    }

    @Override
    public void zrus() {
        prvni = null;
        aktualni = null;
        posledni = null;
        pocetPrvku = 0;
    }

    @Override
    public boolean jePrazdny() {
        return pocetPrvku <= 0;
    }

    @Override
    public void vlozPrvni(T data) {
        Prvek novy = new Prvek(data);
        if (prvni == null) {
            prvni = novy;
            aktualni = novy;
            posledni = novy;
        } else {
            prvni.predchozi = novy;
            novy.nasledujici = prvni;
            prvni = novy;
        }

        zpristupniPrvni();
        pocetPrvku++;
    }

    @Override
    public void vlozPosledni(T data) {
        if (jePrazdny()) {
            vlozPrvni(data);
        } else {

            Prvek novy = new Prvek(data);

            novy.predchozi = posledni;
            posledni.nasledujici = novy;
            posledni = novy;

            zpristupniPosledni();
            pocetPrvku++;
        }
    }

    @Override
    public void vlozNaslednika(T data) {
        if (prvni == null) {
            vlozPrvni(data);
        } else {
            Prvek novy = new Prvek(data);
            Prvek tmp = aktualni.nasledujici;

            if (prvni.nasledujici == null) {
                prvni.nasledujici = novy;
                aktualni.predchozi = prvni;
            }

            aktualni.nasledujici = novy;
            novy.predchozi = aktualni;
            aktualni = novy;

            if (tmp != null) {
                tmp.predchozi = novy;
                novy.nasledujici = tmp;
            } else {
                aktualni.nasledujici = null;
                Prvek tmp2 = posledni;
                posledni = novy;
                posledni.predchozi = tmp2;
            }

            pocetPrvku++;
        }
    }

    @Override
    public void vlozPredchudce(T data) {
        if (prvni == null) {
            vlozPrvni(data);
        } else {
            Prvek novy = new Prvek(data);
            Prvek tmp = aktualni.predchozi;

            if (tmp == null) {
                vlozPrvni(data);
                return;
            }

            novy.predchozi = tmp;
            novy.nasledujici = aktualni;

            tmp.nasledujici = novy;
            aktualni.predchozi = novy;

            aktualni = novy;

            pocetPrvku++;
        }
    }

    @Override
    public T zpristupniAktualni() {
        if (!jePrazdny()) {
            return aktualni.data;
        } else {
            return null;
        }
    }

    @Override
    public T zpristupniPrvni() {
        if (jePrazdny()) {
            return null;
        }

        aktualni = prvni;
        return prvni.data;
    }

    @Override
    public T zpristupniPosledni() {
        if (jePrazdny()) {
            return null;
        }

        aktualni = posledni;
        return posledni.data;
    }

    @Override
    public T zpristupniNaslednika() {
        if (jePrazdny()) {
            return null;
        }

        if (aktualni.nasledujici != null) {
            aktualni = aktualni.nasledujici;
            return aktualni.data;
        } else {
            return aktualni.data;
        }
    }

    @Override
    public T zpristupniPredchudce() {
        if (jePrazdny()) {
            return null;
        }

        if (aktualni.predchozi != null) {
            aktualni = aktualni.predchozi;
            return aktualni.data;
        } else {
            return aktualni.data;
        }
    }

    @Override
    public T odeberAktualni() {
        if (!jePrazdny()) {
            T tmp;
            if (aktualni.equals(prvni)) {
                tmp = odeberPrvni();
            } else if (aktualni.equals(posledni)) {
                tmp = odeberPosledni();
            } else {
                tmp = aktualni.data;

                aktualni.predchozi.nasledujici = aktualni.nasledujici;
                aktualni.nasledujici.predchozi = aktualni.predchozi;

                zpristupniPrvni();
                pocetPrvku--;
            }
            return tmp;
        } else {
            return null;
        }
    }

    @Override
    public T odeberPrvni() {
        if (!jePrazdny()) {
            T tmp;
            if (prvni.nasledujici == null) {
                tmp = prvni.data;
                zrus();
            } else {
                tmp = prvni.data;
                if (prvni.equals(aktualni)) {
                    aktualni = prvni.nasledujici;
                }
                prvni = prvni.nasledujici;
                prvni.predchozi = null;
                pocetPrvku--;
            }
            return tmp;
        } else {
            return null;
        }
    }

    @Override
    public T odeberPosledni() {
        if (!jePrazdny()) {
            T tmp;
            if (posledni.equals(prvni)) {
                tmp = odeberPrvni();
                zrus();
            } else if (posledni.equals(aktualni)) {
                tmp = aktualni.data;

                posledni = aktualni.predchozi;
                posledni.nasledujici = null;
                aktualni = aktualni.predchozi;
                aktualni.nasledujici = null;

                pocetPrvku--;
            } else {
                tmp = posledni.data;
                posledni = posledni.predchozi;
                posledni.nasledujici.predchozi = null;
                posledni.nasledujici = null;
                pocetPrvku--;
            }
            return tmp;
        } else {
            return null;
        }
    }

    @Override
    public T odeberNaslednika() {
        if (!jePrazdny()) {
            T tmpData;
            if (aktualni.equals(posledni)) {
                return null;
            } else {
                tmpData = aktualni.nasledujici.data;

                if (aktualni.nasledujici.nasledujici != null) {
                    aktualni.nasledujici = aktualni.nasledujici.nasledujici;
                    aktualni.nasledujici.predchozi = aktualni;
                } else {
                    return odeberPosledni();
                }

                pocetPrvku--;
            }
            return tmpData;
        } else {
            return null;
        }
    }

    @Override
    public T odeberPredchudce() {
        if (!jePrazdny()) {
            T tmpData;

            if (aktualni.predchozi.equals(prvni)) {
                tmpData = odeberPrvni();
            } else {
                tmpData = aktualni.predchozi.data;

                aktualni.predchozi.predchozi.nasledujici = aktualni;
                aktualni.predchozi = aktualni.predchozi.predchozi;

                pocetPrvku--;
            }

            return tmpData;
        } else {
            return null;
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator() {
            Prvek akt = prvni;

            @Override
            public boolean hasNext() {
                return akt != null;
            }

            @Override
            public T next() {
                
                T data = akt.data;
                akt = akt.nasledujici;

                return data;
            }
        };
    }

    @Override
    public int pocetPrvku() {
        return pocetPrvku;
    }

}
