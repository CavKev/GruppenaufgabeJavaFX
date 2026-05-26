package com.edencoding.dao;

import com.edencoding.models.Student;
import com.edencoding.models.Studiengruppe;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StudentDAO {


    public static List<Student> loadStudentsFromDatabase() {

        List<Student> students = new ArrayList<>();

        String query = "SELECT * FROM  Student";

        try (Connection connection = Database.connect()) {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date date = formatter.parse(rs.getString("Geburtsdatum"));

                students.add(new Student(
                        rs.getLong("DB_ID"),
                        rs.getString("Name"),
                        rs.getString("Vorname"),
                        date,
                        rs.getString("Adresse"),
                        StudiengruppeDAO.loadGruppeByID(rs.getLong("Gruppe_DB_ID"))));
            }
        } catch (SQLException | ParseException e) {
            Logger.getAnonymousLogger().log(
                    Level.SEVERE,
                    LocalDateTime.now() + ": Could not load Persons from database ");

        }
        return students;
    }

}