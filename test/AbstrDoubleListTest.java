
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
   
            private Autopujcovna[] dejPole(AbstrDoubleList<Autopujcovna> instance) {
        int pocet = instance.pocetPrvku();
        Autopujcovna[] pole = new Autopujcovna[pocet];
        Iterator<Autopujcovna> iterator = instance.iterator();
        for (int i = 0; i < pocet; i++) {
            pole[i] = iterator.next();
        }

        return pole;
    }
            
   @Test
    public void testVlozZaAktualnim10() throws Exception{

        AbstrDoubleList<Autopujcovna> instance = Generator.generujAutoPujcovny(10);
        Autopujcovna[] pole = dejPole(instance);
           for (int i = 0; i < pole.length; i++) {
               System.out.println(pole[i].getPocetPujcovenVSeznamu());
           }
       Perzistence.uloz("kocourek", instance);
       System.out.println("soubor ulozen");
       AbstrDoubleList<Autopujcovna> instance2 = Generator.generujAutoPujcovny(1);
       
       Perzistence.nacti("kocourek", instance2);
       System.out.println("seznam nacten");
               Autopujcovna[] pole2 = dejPole(instance);
           for (int i = 0; i < pole2.length; i++) {
               System.out.println(pole2[i].getPocetPujcovenVSeznamu());
           }

    }

}
