/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import struktury.AbstrDoubleList;
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
import java.util.Iterator;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import perzistence.Perzistence;
import struktury.AbstrTable;
import struktury.eTypProhl;

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
    @FXML
    private TextField vyhledavaciTextField;
    @FXML
    private ComboBox typProhledavani;

    private final ObservableList<IPobocka> pobockyObsList;
    private final ObservableList<Auto> autaObsList;
    private final ObservableList<Auto> vypAutaObsList;
    private TextInputDialog dialog = new TextInputDialog();
    private ChoiceDialog<String> choice = new ChoiceDialog<>("Osobní auto", "Osobní auto", "Užitkové auto");
    private ChoiceDialog<String> choicePozice = new ChoiceDialog<>("První", "První", "Předchozí", "Aktuální", "Další", "Poslední");
    private ChoiceDialog<String> choicePoziceVloz = new ChoiceDialog<>("První", "První", "Předchozí", "Další", "Poslední");
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
        typProhledavani.getItems().addAll("HLOUBKA", "ŠÍŘKA");
        typProhledavani.getSelectionModel().select("HLOUBKA");
    }

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
        Auto aktualni = autopujcovna.zpristupnAuto();
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
            Iterator<Auto> it = autopujcovna.iterator(eTyp.AUTA);

            while (it.hasNext()) {
                listAut.getItems().add(it.next());
            }

//            for (Auto auto : aktualniPob.getSeznamAut()) {
//                listAut.getItems().add(auto);
//            }
            Auto aktAuto = autopujcovna.zpristupnAuto(); //musim dodelat aktualniPob.getSeznamAut().zpristupniAktualni();
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
        if (autopujcovna.zpristupniPobocku(EnumPozice.AKTUALNI) != null) {
            Auto auto = Generator.vytvorNahodneAuto();
            autopujcovna.vlozAuto(auto);
            obnovitListy();
        }

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
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Chyba");
        if (autopujcovna.getPocetPujcovenVSeznamu() != 0) {
            if (autopujcovna.getPobocky().zpristupniAktualni().getPocetAutVSeznamu() != 0) {
                if (autopujcovna.zpristupnAuto() != null) {
                    autopujcovna.vypujcAuto();
                    obnovitListy();
                } else {
                    alert.setContentText("Pobočka nemá vybraný prvek!");
                    alert.showAndWait();
                }
            } else {
                alert.setContentText("Pobočka nemá žádné auta!");
                alert.showAndWait();
            }
        } else {
            alert.setContentText("V autopůjčovně není žádná pobočka!");
            alert.showAndWait();
        }

    }

    @FXML
    private void vratAuto(ActionEvent event) {
        if (autopujcovna.getPocetVypujcenychAut() != 0) {
            choicePozice.setTitle("Výběr pozice");
            choicePozice.setContentText("Vyberte pozici vráceného auta:");

            Optional<String> pozice = choicePozice.showAndWait();
            pozice.ifPresent((t) -> {
                switch (t) {

                    case "První":
                        autopujcovna.vratAuto(EnumPozice.PRVNI);
                        break;
                    case "Předchozí":
                        autopujcovna.vratAuto(EnumPozice.PREDCHUDCE);
                        break;
                    case "Aktuální":
                        autopujcovna.vratAuto(EnumPozice.AKTUALNI);
                        break;
                    case "Další":
                        autopujcovna.vratAuto(EnumPozice.NASLEDNIK);
                        break;
                    case "Poslední":
                        autopujcovna.vratAuto(EnumPozice.POSLEDNI);
                        break;

                }

                obnovitListy();
            });
        }

    }

    @FXML
    private void odeberAuto(ActionEvent event) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Chyba");
        if (autopujcovna.getPocetPujcovenVSeznamu() != 0) {
            if (autopujcovna.getPobocky().zpristupniAktualni().getPocetAutVSeznamu() != 0) {
                if (autopujcovna.zpristupnAuto() != null) {
                    autopujcovna.odeberAuto();
                    obnovitListy();
                } else {
                    alert.setContentText("Pobočka nemá vybrané auto!");
                    alert.showAndWait();
                }

            } else {

                alert.setContentText("Pobočka nemá žádné auta!");
                alert.showAndWait();
            }
        } else {
            alert.setContentText("V autopůjčovně není žádná pobočka!");
            alert.showAndWait();
        }
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
        choicePoziceVloz.setTitle("Výběr pozice");
        choicePoziceVloz.setContentText("Vyberte pozici vložení:");

        Optional<String> pozice = choicePoziceVloz.showAndWait();

        pozice.ifPresent(pozicePolozky -> {
            dialog.setHeaderText("Zadejte jméno pobočky");
            result = dialog.showAndWait();
            result.ifPresent(jmenoPobocky -> {
                if (!jmenoPobocky.isEmpty()) {
                    Pobocka pobocka = new Pobocka(jmenoPobocky, new AbstrTable<String, Auto>());
                    switch (pozicePolozky) {
                        case "První":
                            autopujcovna.vlozPobocku(pobocka, EnumPozice.PRVNI);
                            break;
                        case "Předchozí":
                            autopujcovna.vlozPobocku(pobocka, EnumPozice.PREDCHUDCE);
                            break;
                        case "Další":
                            autopujcovna.vlozPobocku(pobocka, EnumPozice.NASLEDNIK);
                            break;
                        case "Poslední":
                            autopujcovna.vlozPobocku(pobocka, EnumPozice.POSLEDNI);
                            break;
                    }

                    obnovitListy();
                }

            });
        });

    }

    @FXML
    private void odeberPobocku(ActionEvent event) {
        choicePozice.setTitle("Výběr pozice");
        choicePozice.setContentText("Vyberte pozici odebrání:");

        Optional<String> pozice = choicePozice.showAndWait();

        pozice.ifPresent(pozicePolozky -> {
            if (autopujcovna.getPocetPujcovenVSeznamu() != 0) {
                switch (pozicePolozky) {
                    case "První":
                        autopujcovna.odeberPobocku(EnumPozice.PRVNI);
                        break;
                    case "Předchozí":
                        autopujcovna.odeberPobocku(EnumPozice.PREDCHUDCE);
                        break;
                    case "Aktuální":
                        autopujcovna.odeberPobocku(EnumPozice.AKTUALNI);
                        break;
                    case "Další":
                        autopujcovna.odeberPobocku(EnumPozice.NASLEDNIK);
                        break;
                    case "Poslední":
                        autopujcovna.odeberPobocku(EnumPozice.POSLEDNI);
                        break;

                }

                obnovitListy();
            } else {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Chyba");
                alert.setContentText("Autopůjčovna nemá žádně pobočky!");

                alert.showAndWait();
            }
        });
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

    @FXML
    private void hledat(ActionEvent event) {
        Auto hledaneAuto = autopujcovna.hledejAuto(vyhledavaciTextField.getText());

        if (hledaneAuto == null) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Hledání auta v autopůjčovně");
            alert.setContentText("Auto nebylo nalezeno...");

            alert.showAndWait();
        }

        setSelected();
        setSelectedAuta();

    }

    @FXML
    private void hledatVPobocce(ActionEvent event) {
        if (autopujcovna.zpristupniPobocku(EnumPozice.AKTUALNI) != null) {
            Auto hledaneAuto = autopujcovna.hledejAutoVPobocce(vyhledavaciTextField.getText());

            if (hledaneAuto == null) {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Hledání auta v pobočce");
                alert.setContentText("Auto nebylo nalezeno...");

                alert.showAndWait();
            }

            setSelectedAuta();
        }
    }

    @FXML
    private void odeberAutoKlic(ActionEvent event) {
        Auto hledaneAuto = autopujcovna.odeberAutoPodleKlice(vyhledavaciTextField.getText());

        if (hledaneAuto == null) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Hledání auta v autopůjčovně");
            alert.setContentText("Auto nebylo nalezeno...");

            alert.showAndWait();
        }

        obnovitListy();
    }

    @FXML
    private void selectTypProhledavani(ActionEvent event) {
        if (typProhledavani.getValue().toString().equals("HLOUBKA")) {
            autopujcovna.setTypProhledavni(eTypProhl.HLOUBKA);
        } else {
            autopujcovna.setTypProhledavni(eTypProhl.SIRKA);
        }
    }

    @FXML
    private void selectItem(MouseEvent event) {
        vyhledavaciTextField.setText(listAut.getSelectionModel().getSelectedItem().getSpz());
    }
}
