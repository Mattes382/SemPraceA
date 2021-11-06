/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package struktury;

import java.io.Serializable;
import java.util.Iterator;

/**
 *
 * @author Matej
 */
public class AbstrTable<K extends Comparable<K>, V> implements IAbstrTable<K, V>, Serializable {

    private Prvek root;

    private class Prvek implements Serializable {

        Prvek levy;
        Prvek pravy;
        Prvek rodic;
        V data;
        K klic;

        public Prvek(Prvek levy, Prvek pravy, Prvek rodic, V data, K klic) {
            this.levy = levy;
            this.pravy = pravy;
            this.rodic = rodic;
            this.data = data;
            this.klic = klic;
        }
    }

    @Override
    public void zrus() {
        root = null;
    }

    @Override
    public boolean jePrazdny() {
        return root == null;
    }

    @Override
    public void vloz(K key, V value) {
        //duplikat - najdi
        V val = najdi(key);
        if (val == null) {
            if (root == null) {
                Prvek r = new Prvek(null, null, null, value, key);
                root = r;
            } else {
                Prvek tmp = root;
                while (true) {
                    //vkladany Prvek je vetsi
                    if (tmp.klic.compareTo(key) < 0) {
                        if (tmp.pravy == null) {
                            Prvek p = new Prvek(null, null, tmp, value, key);
                            tmp.pravy = p;
                            return;
                        }
                        tmp = tmp.pravy;
                        //vkladany Prvek je mensi
                    } else if (tmp.klic.compareTo(key) > 0) {
                        if (tmp.levy == null) {
                            Prvek l = new Prvek(null, null, tmp, value, key);
                            tmp.levy = l;
                            return;
                        }
                        tmp = tmp.levy;
                    }
                }
            }
        }
    }

    @Override
    public V najdi(K key) {

        Prvek tmp = root;
        while (tmp != null) {
            if (tmp.klic.compareTo(key) == 0) {
                return tmp.data;
            } else if (tmp.klic.compareTo(key) > 0) {
                tmp = tmp.levy;
            } else if (tmp.klic.compareTo(key) < 0) {
                tmp = tmp.pravy;
            }

        }
        return null;
    }

    @Override
    public V odeber(K key) {
        if (root == null) {
            throw new NullPointerException("Strom je prazdny");
        } else if (key.equals("")) {
            throw new NullPointerException("prazdny klic");
        }
        V val = najdi(key);
        if (val == null) {
            throw new NullPointerException("Odebirany prvek neni ve stromu");
        }
        Prvek deletePrvek = root;

        while (deletePrvek.klic.compareTo(key) != 0) {
            if (deletePrvek.klic.compareTo(key) > 0) {
                deletePrvek = deletePrvek.levy;
            } else if (deletePrvek.klic.compareTo(key) < 0) {
                deletePrvek = deletePrvek.pravy;
            }
        }
        V data = deletePrvek.data;

        //Pokud nema zadne potomky  
        if (deletePrvek.levy == null && deletePrvek.pravy == null) {
            //pokud pom ma rodice
            if (deletePrvek.rodic != null) {
                //pokud je pom levy
                if (deletePrvek.rodic.levy == deletePrvek) {
                    deletePrvek.rodic.levy = null;
                } else {
                    //pokud je pom pravy
                    deletePrvek.rodic.pravy = null;
                }

            } else {
                zrus();
            }
            //pokud ma praveho potomka
        } else if (deletePrvek.levy == null && deletePrvek.pravy != null) {
            //zjistin jestli je odebirany levy nebo pravy potomek
            if (deletePrvek.rodic != null) {
                if (deletePrvek.rodic.levy == deletePrvek) {
                    deletePrvek.rodic.levy = deletePrvek.pravy;
                } else {
                    deletePrvek.rodic.pravy = deletePrvek.pravy;
                }
                deletePrvek.pravy.rodic = deletePrvek.rodic;
            } else {
                root = deletePrvek.pravy;
                root.rodic = null;
            }
            //pokud ma leveho potomka
        } else if (deletePrvek.pravy == null && deletePrvek.levy != null) {
            //pokud deletePrvek je u rodice jako levy u rodice
            if (deletePrvek.rodic != null) {
                if (deletePrvek.rodic.levy == deletePrvek) {
                    deletePrvek.rodic.levy = deletePrvek.levy;
                } else {
                    deletePrvek.rodic.pravy = deletePrvek.levy;
                }

                //potomek pomu ma vazbu na rodice pomu
                deletePrvek.levy.rodic = deletePrvek.rodic;
            } else {
                root = deletePrvek.levy;
                root.rodic = null;
            }
        } else {
            //pokud jsou 2 potomci
            //hledany prvek je ten, ktery je co nejbliz nad prvkem, ktery chceme odstranit
            //potomek napravo a pak co nejdál doleva
            Prvek newPrvek = deletePrvek.pravy;
            while (newPrvek.levy != null) {
                newPrvek = newPrvek.levy;
            }
            //nasli jsme novy prvek
            //pokud nahrazeny prvek nema potomky
            if (newPrvek.pravy == null) {
                //pokud odebirany ma rodice
                if (newPrvek.rodic.levy == newPrvek) {
                    newPrvek.rodic.levy = null;
                } else {
                    newPrvek.rodic.pravy = null;
                }
                if (deletePrvek.rodic != null) {
                    //Pro zjisteni jestli je pravy nebo levy potomek
                    if (deletePrvek.rodic.pravy == deletePrvek) {
                        deletePrvek.rodic.pravy = newPrvek;
                    } else {
                        deletePrvek.rodic.levy = newPrvek;
                    }
                    newPrvek.rodic = deletePrvek.rodic;
                    //pokud nema rodice nastv root
                } else {
                    //pokud ma rodice
                    root = newPrvek;
                    newPrvek.rodic = null;
                }
                //Vymazu hledany Prvek         
                //potomci
                //hledany               
                newPrvek.levy = deletePrvek.levy;
                //Jestlize pravy prvek byl jenom jeden, tudiz uz tam neni zadny

                newPrvek.pravy = deletePrvek.pravy;
                if (newPrvek.pravy != null) {
                    deletePrvek.pravy.rodic = newPrvek;
                }
                deletePrvek.levy.rodic = newPrvek;
                //pokud ma praveho potomka 
            } else {
                //odebrani hledaneho
                //rodic ->potomek
                if (newPrvek.rodic.pravy == newPrvek) {
                    newPrvek.rodic.pravy = newPrvek.pravy;
                } else {
                    newPrvek.rodic.levy = newPrvek.pravy;
                }
                //potomek -> rodic  PROBLEM ukazuje na odebirany prvek
                if (deletePrvek == newPrvek.rodic) {
                } else {
                    newPrvek.pravy.rodic = newPrvek.rodic;
                }
                //pravy potmek               
                newPrvek.pravy = deletePrvek.pravy;
                //levy potomek
                newPrvek.levy = deletePrvek.levy;
                newPrvek.levy.rodic = newPrvek;
                //pokud ma rodice -> neni root
                if (deletePrvek.rodic != null) {
                    //Pro zjisteni jestli je 
                    if (deletePrvek.rodic.pravy == deletePrvek) {
                        deletePrvek.rodic.pravy = newPrvek;
                    } else {
                        deletePrvek.rodic.levy = newPrvek;
                    }
                    newPrvek.rodic = deletePrvek.rodic;
                    //pokud nema rodice nastveny root
                } else {
                    //pokud ma rodice
                    root = newPrvek;
                    newPrvek.rodic = null;
                }
            }
        }
        return data;

    }

    public int pocetPrvku() {
        int pocetPrvku = 0;
        Iterator<V> iterator = vytvorIterator(eTypProhl.SIRKA);

        while (iterator.hasNext()) {
            iterator.next();
            pocetPrvku++;
        }
        return pocetPrvku;
    }

    @Override
    public Iterator<V> vytvorIterator(eTypProhl typ) {
        //inorder
        if (typ == eTypProhl.HLOUBKA) {
            return new Iterator<V>() {
                IAbstrLIFO<Prvek> zasobnik = new AbstrLIFO();
                Prvek tmp = root;

                @Override
                public boolean hasNext() {
                    return !zasobnik.jePrazdny() || tmp != null;
                }

                @Override
                public V next() {
                    while (tmp != null) {
                        zasobnik.vloz(tmp);
                        tmp = tmp.levy;
                    }
                    Prvek odPrvek = zasobnik.odeber();
                    //Jdu do prave vetve
                    tmp = odPrvek.pravy;
                    return odPrvek.data;
                }
            };
        } else if (typ == eTypProhl.SIRKA) {
            return new Iterator<V>() {
                IAbstrFIFO<Prvek> fronta = new AbstrFIFO<>();
                int i = 0;

                @Override
                public boolean hasNext() {
                    if (i == 0 && root != null) {
                        fronta.vloz(root);
                        i++;
                    }
                    return !fronta.jePrazdny();
                }

                @Override
                public V next() {

                    Prvek tmp = fronta.odeber();
                    V retdata = tmp.data;

                    if (tmp.levy != null) {
                        fronta.vloz(tmp.levy);
                    }
                    if (tmp.pravy != null) {
                        fronta.vloz(tmp.pravy);
                    }
                    return retdata;
                }
            };
        }
        return null;
    }

}
