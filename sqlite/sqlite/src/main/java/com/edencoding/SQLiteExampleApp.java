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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.List;

public class SQLiteExampleApp extends Application {

    private TableView<Student> tableView;
    private ObservableList<Student> studentList;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Datenbank
        if (!Database.isOK()) {
            Platform.exit();
            return;
        }

        primaryStage.setTitle("Studentenverwaltung");


        Button btnAddStudent = new Button("Neuen Studenten anlegen");


        // TableView
        tableView = new TableView<>();

        // Daten aus der DAO laden und in eine ObservableList packen
        List<Student> initialData = StudentDAO.loadStudentsFromDatabase();
        studentList = FXCollections.observableArrayList(initialData);
        tableView.setItems(studentList);

        // Spalten TableView

        TableColumn<Student, String> vorname = new TableColumn<>("Vorname");
        vorname.setCellValueFactory(new PropertyValueFactory<>("vorname"));
        vorname.setPrefWidth(150);

        TableColumn<Student, String> name = new TableColumn<>("Nachname");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        name.setPrefWidth(150);

        TableColumn<Student, String> gebDatum = new TableColumn<>("Geburtsdatum");
        gebDatum.setCellValueFactory(new PropertyValueFactory<>("Geburtsdatum"));
        gebDatum.setPrefWidth(150);

        TableColumn<Student, String> adresse = new TableColumn<>("Adresse");
        adresse.setCellValueFactory(new PropertyValueFactory<>("Adresse"));
        adresse.setPrefWidth(150);

        TableColumn<Student, String> studienort = new TableColumn<>("Studienort");
        studienort.setCellValueFactory(new PropertyValueFactory<>("Studienort"));
        studienort.setPrefWidth(150);

        // AktionsSpalte fürButtons (Bearbeiten & Löschen)
        TableColumn<Student, Void> actionColumn = new TableColumn<>("Aktionen");
        actionColumn.setPrefWidth(120);

        Callback<TableColumn<Student, Void>, TableCell<Student, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Student, Void> call(final TableColumn<Student, Void> param) {
                return new TableCell<>() {
                    private final Button btnEdit = new Button("✏");
                    private final Button btnDelete = new Button("❌");
                    private final HBox pane = new HBox(btnEdit, btnDelete);

                    {
                        pane.setSpacing(10);
                        pane.setAlignment(Pos.CENTER);





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

        // Spalten Tabelle hinzufügen
        tableView.getColumns().addAll( vorname, name,gebDatum,adresse,studienort, actionColumn);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        //Layout
        VBox rootLayout = new VBox(15);
        rootLayout.setPadding(new Insets(15));
        rootLayout.getChildren().addAll(btnAddStudent, tableView);

        // Szene
        primaryStage.setScene(new Scene(rootLayout, 600, 400));


        primaryStage.show();
    }
}
