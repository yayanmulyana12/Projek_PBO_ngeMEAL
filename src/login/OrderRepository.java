package login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderRepository {

    // Menyimpan detail pesanan ke database
    public void saveOrderDetails(List<Object[]> orderDetails) {
        String insertQuery = "INSERT INTO orders (menu_name, quantity, price, order_date, payment_method) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            // Loop untuk memproses setiap pesanan yang ada di dalam orderDetails
            for (Object[] order : orderDetails) { // Mengambil informasi dari setiap order
                String menuName = (String) order[0];
                int quantity = (int) order[1];
                double price = getPriceForMenu(menuName) * quantity;
                String orderDate = java.time.LocalDateTime.now().toString();
                String paymentMethod = "Cash";

                preparedStatement.setString(1, menuName);
                preparedStatement.setInt(2, quantity);
                preparedStatement.setDouble(3, price);
                preparedStatement.setString(4, orderDate);
                preparedStatement.setString(5, paymentMethod);
                preparedStatement.addBatch();
            }
            // Mengeksekusi batch query untuk menyimpan data pesanan ke database
            preparedStatement.executeBatch();
            System.out.println("Order details saved successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to save order details!");
        }
    }

    // Mendapatkan harga menu berdasarkan nama
    private double getPriceForMenu(String menuName) {
        switch (menuName) {
            case "Nasi Goreng": return 10000;
            case "Bakso": return 10000;
            case "Nasi Uduk": return 15000;
            case "Batagor": return 5000;
            case "Cireng": return 5000;
            case "Ayam Geprek": return 10000;
            case "Bubur Ayam": return 8000;
            case "Seblak": return 15000;
            case "Mie Goreng": return 10000;
            case "Mie Rebus": return 10000;
            case "Ayam Katsu": return 10000;
            case "Nasi Padang": return 10000;
            case "Kare": return 10000;
            case "Sushi": return 10000;
            default: return 0;
        }
    }

    // Mendapatkan semua detail pesanan
    public List<Object[]> getOrderDetails() {
        List<Object[]> orders = new ArrayList<>();
        String selectQuery = "SELECT menu_name, quantity, price, order_date, payment_method FROM orders";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                orders.add(new Object[]{
                    resultSet.getString("menu_name"),
                    resultSet.getInt("quantity"),
                    resultSet.getDouble("price"),
                    resultSet.getString("order_date"),
                    resultSet.getString("payment_method")
                });
            }
            System.out.println("Order details retrieved successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to retrieve order details!");
        }

        return orders;
    }

    // Mendapatkan detail pesanan terbaru
    public List<Object[]> getLatestOrderDetails() {
        List<Object[]> orders = new ArrayList<>(); // List untuk menyimpan detail pesanan yang diambil dari database
        //buat mengambil detail pesanan terbaru berdasarkan order_date terbesar (terakhir)
        String selectQuery = "SELECT menu_name, quantity, price, order_date, payment_method " +
                             "FROM orders " +
                             "WHERE order_date = (SELECT MAX(order_date) FROM orders)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                orders.add(new Object[]{
                    resultSet.getString("menu_name"),
                    resultSet.getInt("quantity"),
                    resultSet.getDouble("price"),
                    resultSet.getString("order_date"),
                    resultSet.getString("payment_method")
                });
            }
            System.out.println("Latest order details retrieved successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to retrieve latest order details!");
        }

        return orders;
    }
}
