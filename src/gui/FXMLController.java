/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import AbstrDoubleList.AbstrDoubleList;
import Entity.Auto;
import Entity.Autopujcovna;
import Entity.EnumPozice;
import Entity.IPobocka;
import Entity.Pobocka;
import Entity.eTyp;
import generator.Generator;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Dialog;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;
import javafx.util.Pair;
import perzistence.Perzistence;

/**
 * FXML Controller class
 *
 * @author Matej
 */
public class FXMLController implements Initializable {

    @FXML
    private ListView<IPobocka> listPobocek;

    @FXML
    private ListView<Auto> listAut;
    @FXML
    private ListView<Auto> listVypujcenychAut;

    private final ObservableList<IPobocka> pobockyObsList;
    private final ObservableList<Auto> autaObsList;
    private final ObservableList<Auto> vypAutaObsList;
    private TextInputDialog dialog = new TextInputDialog();
    private ChoiceDialog<String> choice = new ChoiceDialog<>("Osobní auto", "Osobní auto", "Užitkové auto");
    private Optional<String> result;

    Autopujcovna autopujcovna = new Autopujcovna();
    String jmenoSouboru = "zaloha.bin";

    public FXMLController() {
        this.autaObsList = FXCollections.observableArrayList();
        this.pobockyObsList = FXCollections.observableArrayList();
        this.vypAutaObsList = FXCollections.observableArrayList();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    /*
            listView.getItems().clear();
        Iterator<DopravniProstredek> iterator = sp.iterator();
        while (iterator.hasNext()) {
            listProstredku.add(iterator.next());
        }
        listView.setItems(listProstredku);
    
    
    
     */
    private void obnovitListy() {
        listPobocek.getItems().clear();
        pobockyObsList.clear();
        listAut.getItems().clear();
        autaObsList.clear();
        listVypujcenychAut.getItems().clear();
        vypAutaObsList.clear();

        Iterator<Pobocka> iteratorPobocky = autopujcovna.iterator(eTyp.POBOCKY);
        Iterator<Auto> iteratorAuta = autopujcovna.iterator(eTyp.AUTA);
        Iterator<Auto> iteratorVypAuta = autopujcovna.iterator(eTyp.VYPUJCENE_AUTA);

        while (iteratorPobocky.hasNext()) {
            while (iteratorAuta.hasNext()) {
                autaObsList.add(iteratorAuta.next());
            }
            pobockyObsList.add(iteratorPobocky.next());
        }
        while (iteratorVypAuta.hasNext()) {
            vypAutaObsList.add(iteratorVypAuta.next());
        }

//        for (Pobocka pob : autopujcovna.getPobocky()) {
//
//            for (Auto auto : pob.getSeznamAut()) {
//                autaObsList.add(auto);
//            }
//            pobockyObsList.add(pob);
//
//        }
//
//        for (Auto auto : autopujcovna.getVypujcenaAuta()) {
//            vypAutaObsList.add(auto);
//        }

        listAut.setItems(autaObsList);
        listPobocek.setItems(pobockyObsList);
        listVypujcenychAut.setItems(vypAutaObsList);
        setSelected();
        setSelectedVypAuta();
    }

    private void setSelected() {
        IPobocka aktualni = autopujcovna.zpristupniPobocku(EnumPozice.AKTUALNI);
        if (aktualni != null) {
            listPobocek.getSelectionModel().select(aktualni);
            zobrazit();
        }
    }

    private void setSelectedAuta() {
        Auto aktualni = autopujcovna.zpristupnAuto(EnumPozice.AKTUALNI);
        if (aktualni != null) {
            listAut.getSelectionModel().select(aktualni);
        }
    }

    private void setSelectedVypAuta() {
        Auto aktualni = autopujcovna.zpristupniVypujceneAuto(EnumPozice.AKTUALNI);
        if (aktualni != null) {
            listVypujcenychAut.getSelectionModel().select(aktualni);
        }
    }

    private void zobrazit() {
        if (!autopujcovna.getPobocky().jePrazdny()) {
            listAut.getItems().clear();
            Pobocka aktualniPob = autopujcovna.getPobocky().zpristupniAktualni();

            for (Auto auto : aktualniPob.getSeznamAut()) {
                listAut.getItems().add(auto);
            }

            Auto aktAuto = aktualniPob.getSeznamAut().zpristupniAktualni();
            if (aktAuto != null) {
                listAut.getSelectionModel().select(aktAuto);
            }
        }
    }

    @FXML
    private void generuj(ActionEvent event) {
        IPobocka p = Generator.vytvorNahodnouPobocku(Generator.nahodneCislo(1, 10));
        autopujcovna.vlozPobocku(p, EnumPozice.POSLEDNI);
        obnovitListy();
    }

    @FXML
    private void generujAuta(ActionEvent event) {
        Auto auto = Generator.vytvorNahodneAuto();
        autopujcovna.vlozAuto(auto, EnumPozice.POSLEDNI);
        obnovitListy();
    }

    @FXML
    private void zrus(ActionEvent event) {
        autopujcovna.zrus();
        obnovitListy();

    }

    @FXML
    private void zrusAuta(ActionEvent event) {
        autopujcovna.zrusPobocku();
        obnovitListy();

    }

    @FXML
    private void nacti(ActionEvent event) throws FileNotFoundException {
        try {
            autopujcovna = Perzistence.nacti(jmenoSouboru);
            obnovitListy();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void uloz(ActionEvent event) {

        try {
            Perzistence.uloz(jmenoSouboru, autopujcovna);
        } catch (IOException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void vypujcAuto(ActionEvent event) {
        if (autopujcovna.getPobocky().zpristupniAktualni().getPocetAutVSeznamu() != 0) {
            autopujcovna.vypujcAuto(EnumPozice.AKTUALNI);
            obnovitListy();
        }
    }

    @FXML
    private void vratAuto(ActionEvent event) {

        if (autopujcovna.getPocetVypujcenychAut() != 0) {
            autopujcovna.vratAuto(EnumPozice.AKTUALNI);
            obnovitListy();
        }

    }

    @FXML
    private void odeberAuto(ActionEvent event) {
        autopujcovna.zpristupniPobocku(EnumPozice.AKTUALNI).odeberAuto(EnumPozice.AKTUALNI);
        obnovitListy();
    }

    @FXML
    private void vlozAuto(ActionEvent event) {
        if (!(autopujcovna.zpristupniPobocku(EnumPozice.AKTUALNI) == null)) {
            choice.setTitle("Výběr auta");
            choice.setContentText("Vyberte si typ auta:");

            Optional<String> result = choice.showAndWait();
            result.ifPresent(auto -> {
                if (auto.startsWith("O")) {
                    OsobniAutoDialog.pridej(autopujcovna);

                } else {
                    UzitkoveAutoDialog.pridej(autopujcovna);
                }
                obnovitListy();
            });
        } else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Chyba");
            alert.setContentText("Auto potřebuje pobočku!");

            alert.showAndWait();
        }

    }

    @FXML
    private void vlozPobocku(ActionEvent event) {
        dialog.setHeaderText("Zadejte jméno pobočky");
        result = dialog.showAndWait();
        result.ifPresent(jmenoPobocky -> {
            if (!jmenoPobocky.isEmpty()) {
                Pobocka pobocka = new Pobocka(jmenoPobocky, new AbstrDoubleList<Auto>());
                autopujcovna.vlozPobocku(pobocka, EnumPozice.POSLEDNI);
                obnovitListy();
            }

        });
    }

    @FXML
    private void odeberPobocku(ActionEvent event) {
        autopujcovna.odeberPobocku(EnumPozice.AKTUALNI);
        obnovitListy();
    }

    @FXML
    private void prvniPobocky(ActionEvent event) {
        autopujcovna.zpristupniPobocku(EnumPozice.PRVNI);
        setSelected();
    }

    @FXML
    private void predchoziPobocky(ActionEvent event) {
        autopujcovna.zpristupniPobocku(EnumPozice.PREDCHUDCE);
        setSelected();
    }

    @FXML
    private void dalsiPobocky(ActionEvent event) {
        autopujcovna.zpristupniPobocku(EnumPozice.NASLEDNIK);
        setSelected();
    }

    @FXML
    private void posledniPobocky(ActionEvent event) {
        autopujcovna.zpristupniPobocku(EnumPozice.POSLEDNI);
        setSelected();
    }

    @FXML
    private void prvniAuta(ActionEvent event) {
        autopujcovna.zpristupnAuto(EnumPozice.PRVNI);
        setSelectedAuta();
    }

    @FXML
    private void predchoziAuta(ActionEvent event) {
        autopujcovna.zpristupnAuto(EnumPozice.PREDCHUDCE);
        setSelectedAuta();
    }

    @FXML
    private void dalsiAuta(ActionEvent event) {
        autopujcovna.zpristupnAuto(EnumPozice.NASLEDNIK);
        setSelectedAuta();
    }

    @FXML
    private void posledniAuta(ActionEvent event) {
        autopujcovna.zpristupnAuto(EnumPozice.POSLEDNI);
        setSelectedAuta();
    }

    @FXML
    private void prvniVypujcenaAuta(ActionEvent event) {
        autopujcovna.zpristupniVypujceneAuto(EnumPozice.PRVNI);
        setSelectedVypAuta();
    }

    @FXML
    private void predchoziVypujcenaAuta(ActionEvent event) {
        autopujcovna.zpristupniVypujceneAuto(EnumPozice.PREDCHUDCE);
        setSelectedVypAuta();

    }

    @FXML
    private void dalsiVypujcenaAuta(ActionEvent event) {
        autopujcovna.zpristupniVypujceneAuto(EnumPozice.NASLEDNIK);
        setSelectedVypAuta();

    }

    @FXML
    private void posledniVypujcenaAuta(ActionEvent event) {
        autopujcovna.zpristupniVypujceneAuto(EnumPozice.POSLEDNI);
        setSelectedVypAuta();

    }

}
