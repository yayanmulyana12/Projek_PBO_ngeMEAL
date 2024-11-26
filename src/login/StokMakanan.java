package login;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class StokMakanan {

    // Metode untuk mengambil stok makanan dari database
    public Map<String, Integer> getStokMakanan() {
        Map<String, Integer> stokMakanan = new HashMap<>();
        String sql = "SELECT nama_makanan, stok FROM makanan";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                stokMakanan.put(rs.getString("nama_makanan"), rs.getInt("stok"));
            }
        } catch (SQLException e) {
            System.err.println("Gagal mengambil data stok makanan!");
            e.printStackTrace();
        }
        return stokMakanan;
    }

    // Metode untuk memperbarui stok makanan di database
    public void updateStokMakanan(String namaMakanan, int stokBaru) {
        String sql = "UPDATE makanan SET stok = ? WHERE nama_makanan = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, stokBaru);
            pstmt.setString(2, namaMakanan);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Gagal memperbarui stok makanan!");
            e.printStackTrace();
        }
    }

    // Metode untuk mengecek apakah stok cukup untuk makanan tertentu
    public boolean cekStokCukup(String namaMakanan, int jumlah) {
        Map<String, Integer> stokMakanan = getStokMakanan();
        int stokTersedia = stokMakanan.getOrDefault(namaMakanan, 0);
        return stokTersedia >= jumlah;
    }
}
