package login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // Konfigurasi database
    private static final String URL = "jdbc:mysql://localhost:3306/FoodOrderingDB"; // Ganti sesuai nama database
    private static final String USER = "root"; // Ganti sesuai username database
    private static final String PASSWORD = ""; // Ganti sesuai password database

    // Metode untuk mendapatkan koneksi
    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected to the database successfully!");
        } catch (SQLException e) {
            System.err.println("Failed to connect to the database!");
            e.printStackTrace(); // Cetak stack trace untuk debugging
        }
        return conn;
    }
}
