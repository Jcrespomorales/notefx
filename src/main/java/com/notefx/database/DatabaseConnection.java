package com.notefx.database;


import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:sqlite:note.db";

    public static Connection connect() {
        try {
            File dbFile = new File("note.db");
            System.out.println("SQLite path: " + dbFile.getAbsolutePath());
            return DriverManager.getConnection(URL);
        } catch (SQLException e) {
            System.out.println("Error de conexión: " + e.getMessage());
            return null;
        }
    }
}