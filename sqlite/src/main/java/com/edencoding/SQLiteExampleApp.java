package com.edencoding;

import com.edencoding.dao.Database;
import com.edencoding.dao.StudentDAO;
import com.edencoding.models.Student;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.List;

public class SQLiteExampleApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        if (Database.isOK()) {

            List<Student> students = StudentDAO.loadStudentsFromDatabase();


            Parent root = FXMLLoader.load(getClass().getResource("fxml/sqLiteView.fxml"));
            primaryStage.setTitle("Connecting SQLite to JavaFX");
            primaryStage.setScene(new Scene(root));
            primaryStage.initStyle(StageStyle.UNDECORATED);
            primaryStage.show();
        } else {
            Platform.exit();
        }
    }
}
