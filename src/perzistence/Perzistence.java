/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package perzistence;

import AbstrDoubleList.AbstrDoubleList;
import java.io.FileInputStream;
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
    
    public static <T> void uloz(String jmenoSouboru, AbstrDoubleList seznam) throws IOException {
        try {
            Objects.requireNonNull(seznam);

            ObjectOutputStream vystup = new ObjectOutputStream(new FileOutputStream(jmenoSouboru));

            vystup.writeInt(seznam.pocetPrvku());

            Iterator<T> it = seznam.iterator();
            while (it.hasNext()) {
                vystup.writeObject(it.next());
            }
            vystup.close();

        } catch (IOException e) {
            throw new IOException(e);
        }

    }

    public static <T> AbstrDoubleList<T> nacti(String jmenoSouboru, AbstrDoubleList seznam) throws IOException {
        try {
            Objects.requireNonNull(seznam);

            ObjectInputStream vstup = new ObjectInputStream(new FileInputStream(jmenoSouboru));
            seznam.zrus();

            int pocet = vstup.readInt();

            for (int i = 0; i < pocet; i++) {
                seznam.vlozPosledni((T) vstup.readObject());
            }
            vstup.close();

        } catch (IOException | ClassNotFoundException e) {
            throw new IOException(e);
        } finally {

        }
        return seznam;
    }
}
