/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import AbstrDoubleList.AbstrDoubleList;
import Entity.Auto;
import Entity.Autopujcovna;
import Entity.IAutopujcovna;
import Entity.IPobocka;
import Entity.Pobocka;
import Entity.eTyp;
import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

/**
 * FXML Controller class
 *
 * @author Matej
 * @param <T>
 */
public class FXMLController<T> implements Initializable {

    @FXML
    private TreeView<T> treeView;
    private AbstrDoubleList<Pobocka> pobocky = generator.Generator.generujPobocky(5);
    private AbstrDoubleList<Auto> vypujcenaAuta = generator.Generator.generujAuta(5);
    private Autopujcovna autopujcovna = new Autopujcovna(pobocky, vypujcenaAuta);
    TreeItem<T> root = new TreeItem<T>((T) autopujcovna);

    void nactiAutoPujcovnu() {
        TreeItem<T> vypujceneAuta = new TreeItem<T>((T) "Vypujcene auta");

        Iterator<Auto> iteratorVypujceneAuta = autopujcovna.iterator(eTyp.VYPUJCENE_AUTA);
        while (iteratorVypujceneAuta.hasNext()) {
            vypujceneAuta.getChildren().add(new TreeItem<T>((T) iteratorVypujceneAuta.next()));
        }
        root.getChildren().add(vypujceneAuta);

        Iterator<Pobocka> iterator = autopujcovna.iterator(eTyp.POBOCKY);
        while (iterator.hasNext()) {
            Pobocka rawPobocka = iterator.next();
            TreeItem<T> pobocka = new TreeItem<T>((T) rawPobocka);
            Iterator<Auto> iteratorAuta = rawPobocka.iterator();
            while (iteratorAuta.hasNext()) {
                TreeItem<T> auto = new TreeItem<T>((T) iteratorAuta.next());
                pobocka.getChildren().add(auto);
            }

            root.getChildren().add(pobocka);
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nactiAutoPujcovnu();

        treeView.setRoot(root);
    }

}
