
import AbstrDoubleList.AbstrDoubleList;
import Entity.Autopujcovna;
import Entity.Pobocka;
import generator.Generator;
import java.util.Iterator;
import org.junit.Test;
import perzistence.Perzistence;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Matej
 */
public class AbstrDoubleListTest {
   
            private Pobocka[] dejPole(AbstrDoubleList<Pobocka> instance) {
        int pocet = instance.pocetPrvku();
        Pobocka[] pole = new Pobocka[pocet];
        Iterator<Pobocka> iterator = instance.iterator();
        for (int i = 0; i < pocet; i++) {
            pole[i] = iterator.next();
        }

        return pole;
    }
            
   @Test
    public void testVlozZaAktualnim10() throws Exception{

        Autopujcovna instance = Generator.vytvorNahodnouAutoPujcovnu(10);
        Pobocka[] pole = dejPole(instance.getPobocky());
           for (int i = 0; i < pole.length; i++) {
               System.out.println(pole[i]);
           }
        System.out.println(instance.getPocetVypujcenychAut());
       Perzistence.uloz("kocourek.bin", instance);
       System.out.println("soubor ulozen");
       Autopujcovna instance2;
       
       instance2 = Perzistence.nacti("kocourek.bin");
       System.out.println("seznam nacten");
       Pobocka[] pole2 = dejPole(instance2.getPobocky());
           for (int i = 0; i < pole2.length; i++) {
               System.out.println(pole2[i]);
           }
       System.out.println(instance2.getVypujcenaAuta().pocetPrvku());    
    }

}
