package com.edencoding;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.util.Objects;

public class AddStudentWindow {


    // Interface für Datenübergabe an Hauptfenster
    public interface StudentSaveListener {
        void onSave(String vorname, String nachname, LocalDate geburtsdatum, String studienort);
    }

    public static void display(Stage ownerStage, StudentSaveListener listener) {
        Stage window = new Stage();

        // Blockiert Interaktion mit Hauptfenster, solange offen
        window.initModality(Modality.WINDOW_MODAL);
        window.initOwner(ownerStage);
        window.setTitle("Neuen Studenten hinzufügen");
        window.setMinWidth(350);
        window.setMinHeight(300);

        // App Icon
        try {
            window.getIcons().add(new Image(Objects.requireNonNull(
                    AddStudentWindow.class.getResourceAsStream("img/THM_Logo_184x75.png"))));
        } catch (Exception e) {
            System.out.println("Icon konnte nicht geladen werden: " + e.getMessage());
        }

        // Layout Formular
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setVgap(15);
        grid.setHgap(10);
        grid.setAlignment(Pos.CENTER);


        grid.setStyle("-fx-background-color: white;");

        //Inline CSS-Styles
        String labelStyle = "-fx-text-fill: #4a5c66; -fx-font-size: 14px; -fx-font-weight: bold;";
        String inputStyle = "-fx-background-color: white; -fx-text-fill: #4a5c66; -fx-background-radius: 5; -fx-padding: 5;";
        String btnSaveStyle = "-fx-background-color: #80ba24; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5; -fx-padding: 8 15;";
        String btnCancelStyle = "-fx-background-color: #9C132E; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5; -fx-padding: 8 15;";

        // Formular-Felder

        // Vorname
        Label lblVorname = new Label("Vorname:");
        lblVorname.setStyle(labelStyle);
        TextField txtVorname = new TextField();
        txtVorname.setPromptText("Max");
        txtVorname.setStyle(inputStyle);
        GridPane.setConstraints(lblVorname, 0, 0);
        GridPane.setConstraints(txtVorname, 1, 0);

        // Nachname
        Label lblNachname = new Label("Nachname:");
        lblNachname.setStyle(labelStyle);
        TextField txtNachname = new TextField();
        txtNachname.setPromptText("Mustermann");
        txtNachname.setStyle(inputStyle);
        GridPane.setConstraints(lblNachname, 0, 1);
        GridPane.setConstraints(txtNachname, 1, 1);

        // Geburtsdatum
        Label lblGeburtsdatum = new Label("Geburtsdatum:");
        lblGeburtsdatum.setStyle(labelStyle);
        DatePicker datePicker = new DatePicker();
        datePicker.setPromptText("TT.MM.JJJJ");
        // Style für das DatePicker-Textfeld anpassen
        datePicker.getEditor().setStyle(inputStyle);
        GridPane.setConstraints(lblGeburtsdatum, 0, 2);
        GridPane.setConstraints(datePicker, 1, 2);

        // Studienort
        Label lblStudienort = new Label("Studienort:");
        lblStudienort.setStyle(labelStyle);
        TextField txtStudienort = new TextField();
        txtStudienort.setPromptText("Frankenberg");
        txtStudienort.setStyle(inputStyle);
        GridPane.setConstraints(lblStudienort, 0, 3);
        GridPane.setConstraints(txtStudienort, 1, 3);

        // Buttons
        Button btnCancel = new Button("Abbrechen");
        btnCancel.setStyle(btnCancelStyle);
        btnCancel.setOnAction(e -> window.close());

        Button btnSave = new Button("Student anlegen");
        btnSave.setStyle(btnSaveStyle);
        btnSave.setOnAction(e -> {
            // Validierung: Prüfen,alle Felder ausgefüllt
            if (txtVorname.getText().isEmpty() || txtNachname.getText().isEmpty() ||
                    datePicker.getValue() == null || txtStudienort.getText().isEmpty()) {

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Fehler");
                alert.setHeaderText(null);
                alert.setContentText("Bitte füllen Sie alle Felder aus!");
                alert.showAndWait();
            } else {
                // Daten an Listener übergeben
                if (listener != null) {
                    listener.onSave(
                            txtVorname.getText(),
                            txtNachname.getText(),
                            datePicker.getValue(),
                            txtStudienort.getText()
                    );
                }
                window.close();
            }
        });

        // Buttonanordnung
        HBox buttonBox = new HBox(15);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);
        buttonBox.getChildren().addAll(btnCancel, btnSave);
        GridPane.setConstraints(buttonBox, 1, 4);


        grid.getChildren().addAll(lblVorname, txtVorname, lblNachname, txtNachname, lblGeburtsdatum, datePicker, lblStudienort, txtStudienort, buttonBox);

        Scene scene = new Scene(grid);
        window.setScene(scene);
        window.showAndWait(); // Wartet, bis Fenster geschlossen
    }
}
