package com.edencoding;

import com.edencoding.dao.Database;
import com.edencoding.dao.StudentDAO;
import com.edencoding.models.Student;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.List;
import java.util.Objects;

public class SQLiteExampleApp extends Application {

    private TableView<Student> tableView;
    private ObservableList<Student> studentList;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Datenbank-Check
        if (!Database.isOK()) {
            Platform.exit();
            return;
        }

        // App-Icon + Titel
        primaryStage.setTitle("Studentenverwaltung - THM");
        try {
            Image appIcon = new Image(Objects.requireNonNull(
                    getClass().getResourceAsStream("img/THM_Logo_184x75.png")
            ));
            primaryStage.getIcons().add(appIcon);
        } catch (Exception e) {
            System.out.println("App-Icon konnte nicht geladen werden.");
        }

        // Button hinzufuegen + Oeffnen von neuem Fenster
        Button btnAddStudent = new Button("+ Neuen Studenten anlegen");
        btnAddStudent.getStyleClass().add("btn-add-student");



        btnAddStudent.setOnAction(e -> {

            // Aufruf neues Fenster
            AddStudentWindow.display(primaryStage, (vorname, nachname, geburtsdatum, studienort) -> {

                // zur Ueberprufung
                System.out.println("Neuer Student empfangen:");
                System.out.println("Name: " + vorname + " " + nachname);
                System.out.println("Geburtsdatum: " + geburtsdatum);
                System.out.println("Studienort: " + studienort);

                // Hier kannst du den Studenten in deine Liste,
                // TableView oder Datenbank eintragen:
                // z.B. studentenListe.add(new Student(vorname, nachname, geburtsdatum, studienort));

            });
        });


        // Logo
        ImageView logoView = new ImageView();
        try {
            Image logoImg = new Image(Objects.requireNonNull(
                    getClass().getResourceAsStream("img/THM_Logo_184x75.png")
            ));
            logoView.setImage(logoImg);
            logoView.setFitHeight(45); // Skalieren von Logohoehe
            logoView.setPreserveRatio(true);
        } catch (Exception e) {
            System.out.println("Logo für die Kopfzeile konnte nicht geladen werden.");
        }

        // Kopfzeile (Logo links, Button rechts)
        HBox headerBox = new HBox();
        headerBox.setAlignment(Pos.CENTER_LEFT);
        headerBox.setPadding(new Insets(0, 0, 10, 0));

        // Region zum Herausschieben von Button nach rechts
        javafx.scene.layout.Region spacer = new javafx.scene.layout.Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        headerBox.getChildren().addAll(logoView, spacer, btnAddStudent);

        // TableView
        tableView = new TableView<>();

        // Daten aus der DAO laden und in eine ObservableList packen
        List<Student> initialData = StudentDAO.loadStudentsFromDatabase();
        studentList = FXCollections.observableArrayList(initialData);
        tableView.setItems(studentList);

        // Spalten TableView

        TableColumn<Student, String> vorname = new TableColumn<>("Vorname");
        vorname.setCellValueFactory(new PropertyValueFactory<>("vorname"));

        TableColumn<Student, String> name = new TableColumn<>("Nachname");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Student, String> gebDatum = new TableColumn<>("Geburtsdatum");
        gebDatum.setCellValueFactory(new PropertyValueFactory<>("Geburtsdatum"));

        TableColumn<Student, String> adresse = new TableColumn<>("Adresse");
        adresse.setCellValueFactory(new PropertyValueFactory<>("Adresse"));

        TableColumn<Student, String> studienort = new TableColumn<>("Studienort");
        studienort.setCellValueFactory(new PropertyValueFactory<>("Studienort"));
        // AktionsSpalte fürButtons (Bearbeiten & Loeschen)
        TableColumn<Student, Void> actionColumn = new TableColumn<>("Aktionen");
        actionColumn.setMinWidth(130);
        actionColumn.setMaxWidth(130);

        Callback<TableColumn<Student, Void>, TableCell<Student, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Student, Void> call(final TableColumn<Student, Void> param) {
                return new TableCell<>() {
                    private final Button btnEdit = new Button("✏ Bearbeiten");
                    private final Button btnDelete = new Button("❌");
                    private final HBox pane = new HBox(btnEdit, btnDelete);

                    {
                        pane.setSpacing(8);
                        pane.setAlignment(Pos.CENTER);

                        // Styling Buttons
                        btnEdit.getStyleClass().add("btn-edit");
                        btnDelete.getStyleClass().add("btn-delete");

                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(pane);
                        }
                    }
                };
            }
        };

        actionColumn.setCellFactory(cellFactory);

        // Spalten hinzufügen & proportional verteilen
        tableView.getColumns().addAll(vorname, name, gebDatum, adresse, studienort, actionColumn);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Hauptlayout
        VBox rootLayout = new VBox(10);
        rootLayout.setPadding(new Insets(20));


        rootLayout.getChildren().addAll(headerBox, tableView);
        VBox.setVgrow(tableView, Priority.ALWAYS); // Tabelle füllt den restlichen Raum aus



        // Szene
        Scene scene = new Scene(rootLayout, 850, 500);
        // Verbindung zu Stylesheet
        try {
            String cssPath = Objects.requireNonNull(getClass().getResource("/style.css")).toExternalForm();
            scene.getStylesheets().add(cssPath);
        } catch (Exception e) {
            System.out.println("Stylesheet 'style.css' konnte nicht geladen werden.");
        }
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
