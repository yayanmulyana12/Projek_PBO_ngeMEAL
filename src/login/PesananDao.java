
package login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PesananDao {
    
    public void simpanPesanan(String customerName, double totalPrice, String paymentMethod, String paymentDate) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "INSERT INTO daftar_pesanan (customer_name, total_price, payment_method, payment_date, status) VALUES (?, ?, ?, ?, ?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, customerName);
            preparedStatement.setDouble(2, totalPrice);
            preparedStatement.setString(3, paymentMethod);
            preparedStatement.setString(4, paymentDate);
            preparedStatement.setString(5, "Menunggu Pembayaran"); // Status default

            preparedStatement.executeUpdate();
            System.out.println("Data berhasil disimpan ke database!");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Gagal menyimpan data!");
        }
    }
}

