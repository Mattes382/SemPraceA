/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package perzistence;

import AbstrDoubleList.AbstrDoubleList;
import Entity.Auto;
import Entity.Autopujcovna;
import Entity.EnumPozice;
import Entity.IAutopujcovna;
import Entity.IPobocka;
import Entity.Pobocka;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import java.util.Objects;

/**
 *
 * @author Matej
 */
public class Perzistence {

    public static void uloz(String fileName, Autopujcovna autopujcovna) throws IOException {
        try {
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(fileName));

            os.writeInt(autopujcovna.getPocetPujcovenVSeznamu());

            for (Pobocka pob : autopujcovna.getPobocky()) {
                os.writeObject(pob);
            }

            os.writeInt(autopujcovna.getPocetVypujcenychAut());
            
            for (Auto auto : autopujcovna.getVypujcenaAuta()) {
                os.writeObject(auto);
            }

            os.close();
        } catch (IOException ex) {
            System.out.println("Chyba behem ukladani");
            System.out.println(ex);
        }

    }

    public static Autopujcovna nacti(String fileName) throws FileNotFoundException, ClassNotFoundException {
        Autopujcovna nactenaAutopujcovna = null;
        try {
            ObjectInputStream is = new ObjectInputStream(new FileInputStream(fileName));

            nactenaAutopujcovna = new Autopujcovna();
            int pocetPobocek = is.readInt();

            for (int i = 0; i < pocetPobocek; i++) {
                Pobocka pob = (Pobocka) is.readObject();
                Pobocka pobocka = new Pobocka(pob.getJmenoPobocky(), pob.getSeznamAut());
                pobocka.setSeznamAut(pob.getSeznamAut());
                nactenaAutopujcovna.vlozPobocku(pobocka, EnumPozice.POSLEDNI);
            }

            int pocetAut = is.readInt();
            nactenaAutopujcovna.setPocetVypujcenychAut(pocetAut);
            for (int i = 0; i < pocetAut; i++) {
                Auto auto = (Auto) is.readObject();
                
                nactenaAutopujcovna.getVypujcenaAuta().vlozPosledni(auto);
            }

            is.close();
        } catch (IOException ex) {
            System.out.println("Chyba behem načítání. IO Exception");
            System.out.println(ex);
        }

        return nactenaAutopujcovna;
    }
}
