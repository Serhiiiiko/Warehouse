package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
    private static final String URL = "jdbc:sqlite:warehouse.db";

    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public static void createNewDatabase() {
        try (Connection conn = connect()) {
            if (conn != null) {
                Statement stmt = conn.createStatement();
                stmt.execute("CREATE TABLE IF NOT EXISTS materials ("
                        + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + "type TEXT NOT NULL,"
                        + "quantity INTEGER NOT NULL,"
                        + "price REAL NOT NULL"
                        + ");");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
