package com.edencoding.dao;

import com.edencoding.models.Student;
import com.edencoding.models.Studiengruppe;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StudiengruppeDAO {


    public static Studiengruppe loadGruppeByID(long db_id) {

        String query = "SELECT * FROM  Studiengruppe where DB_ID = " + db_id;

        try (Connection connection = Database.connect()) {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {

                Studiengruppe gruppe = new Studiengruppe(rs.getLong("DB_ID"),
                        rs.getString("Name"),
                        rs.getString("Studienort"));
                return gruppe;

            }
        } catch (SQLException e) {
            Logger.getAnonymousLogger().log(
                    Level.SEVERE,
                    LocalDateTime.now() + ": Could not load Persons from database ");
            return null;

        }
        return null;
    }

}
