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
public class AbstrTable<K extends Comparable<K>, V> implements IAbstrTable<K, V> {

    private Prvek root;

    private class Prvek {

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
    public void vloz(K key, V value){
        //duplikat - najdi
        V val = najdi(key);
        if (val == null) {
            if (root == null) {
                Prvek r = new Prvek(null, null, null, value, key);
                root = r;
            } else {
                Prvek pom = root;
                while (true) {
                    //vkladany Prvek je vetsi
                    if (pom.klic.compareTo(key) < 0) {
                        if (pom.pravy == null) {
                            Prvek p = new Prvek(null, null, pom, value, key);
                            pom.pravy = p;
                            return;
                        }
                        pom = pom.pravy;
                        //vkladany Prvek je mensi
                    } else if (pom.klic.compareTo(key) > 0) {
                        if (pom.levy == null) {
                            Prvek l = new Prvek(null, null, pom, value, key);
                            pom.levy = l;
                            return;
                        }
                        pom = pom.levy;
                    }
                }
            }
        } else {
            System.out.println(val);
            //prvek je stejny
//           throw new Exception("Nemuzes vlozit stejny prvek");
        }
    }

    @Override
    public V najdi(K key) {

        Prvek pom = root;
        while (pom != null) {
            if (pom.klic.compareTo(key) == 0) {
                return pom.data;
                //hledany prvek je mensi nez pom
            } else if (pom.klic.compareTo(key) > 0) {
                pom = pom.levy;
            } else if (pom.klic.compareTo(key) < 0) {
                pom = pom.pravy;
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
        //hledam prvek
        while (deletePrvek.klic.compareTo(key) != 0) {
            if (deletePrvek.klic.compareTo(key) > 0) {
                deletePrvek = deletePrvek.levy;
            } else if (deletePrvek.klic.compareTo(key) < 0) {
                deletePrvek = deletePrvek.pravy;
            }
        }
        V data = deletePrvek.data;
        //Prvek jsem nasel
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
                    //pokud nema rodice nastv root
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
                Prvek pom = root;

                @Override
                public boolean hasNext() {
                    //test na prazdnost a 
                    return !zasobnik.jePrazdny() || pom != null;
                }

                @Override
                public V next() {
                    while (pom != null) {
                        zasobnik.vloz(pom);
                        pom = pom.levy;
                    }
                    Prvek odPrvek = zasobnik.odeber();
                    //Jdu do prave vetve
                    pom = odPrvek.pravy;
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

                    Prvek pom = fronta.odeber();
                    V retdata = pom.data;

                    if (pom.levy != null) {
                        fronta.vloz(pom.levy);
                    }
                    if (pom.pravy != null) {
                        fronta.vloz(pom.pravy);
                    }
                    return retdata;
                }
            };
        }
        return null;
    }

}
