/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import Entity.Autopujcovna;
import Entity.Barva;
import Entity.EnumPozice;
import Entity.OsobniAuto;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 *
 * @author Matej
 */
public class OsobniAutoDialog {
// public OsobniAuto(double hmotnost, String spz, int stavKm, int pocetVypujceni, Barva barva)

    private static class Inputy {

        double hmotnost;
        String spz;
        int stavKm;
        int pocetVypujceni;
        Barva barva;

        public Inputy(double hmotnost, String spz, int stavKm, int pocetVypujceni, Barva barva) {
            this.hmotnost = hmotnost;
            this.spz = spz;
            this.stavKm = stavKm;
            this.pocetVypujceni = pocetVypujceni;
            this.barva = barva;
        }

    }

    public static void pridej(Autopujcovna ap) {
        Dialog<Inputy> dialog = new Dialog<>();
        dialog.setTitle("Přidat osobní automobil");
        

        ButtonType vlozit = new ButtonType("Vložit", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(vlozit, ButtonType.CANCEL);
        ObservableList<Barva> barvy
                = FXCollections.observableArrayList(Barva.values());

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField hmotnost = new TextField();
        hmotnost.setPromptText("Hmotnost");
        TextField spz = new TextField();
        spz.setPromptText("SPZ");
        TextField stavKm = new TextField();
        stavKm.setPromptText("Stav v kilometrech");
        TextField pocetVypujceni = new TextField();
        pocetVypujceni.setPromptText("Počet vypůjčení");
        ComboBox<Barva> barva = new ComboBox<>(barvy);
        barva.getSelectionModel().selectFirst();

        grid.add(new Label("Hmotnost:"), 0, 0);
        grid.add(hmotnost, 1, 0);
        grid.add(new Label("SPZ:"), 0, 1);
        grid.add(spz, 1, 1);
        grid.add(new Label("Stav v km:"), 0, 2);
        grid.add(stavKm, 1, 2);
        grid.add(new Label("Počet vypůjčení:"), 0, 3);
        grid.add(pocetVypujceni, 1, 3);
        grid.add(new Label("Barva:"), 0, 4);
        grid.add(barva, 1, 4);


        Node vlozitButton = dialog.getDialogPane().lookupButton(vlozit);
        vlozitButton.setDisable(true);


        hmotnost.textProperty().addListener((observable, oldValue, newValue) -> {
            vlozitButton.setDisable(newValue.trim().isEmpty());
            if (!newValue.matches("\\d*")) {
                hmotnost.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        spz.textProperty().addListener((observable, oldValue, newValue) -> {
            vlozitButton.setDisable(newValue.trim().isEmpty());

        });
        stavKm.textProperty().addListener((observable, oldValue, newValue) -> {
            vlozitButton.setDisable(newValue.trim().isEmpty());
            if (!newValue.matches("\\d*")) {
                stavKm.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        pocetVypujceni.textProperty().addListener((observable, oldValue, newValue) -> {
            vlozitButton.setDisable(newValue.trim().isEmpty());
            if (!newValue.matches("\\d*")) {
                pocetVypujceni.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        dialog.getDialogPane().setContent(grid);


        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == vlozit) {
                return new Inputy(Double.parseDouble(hmotnost.getText()), spz.getText(), Integer.parseInt(stavKm.getText()), Integer.parseInt(pocetVypujceni.getText()), barva.getValue());
            }
            return null;
        });

        Optional<Inputy> result = dialog.showAndWait();

        result.ifPresent(vysledek -> {
            
            ap.vlozAuto(new OsobniAuto(vysledek.hmotnost, vysledek.spz, vysledek.stavKm, vysledek.pocetVypujceni, vysledek.barva));
            
        });
       
    }
}
