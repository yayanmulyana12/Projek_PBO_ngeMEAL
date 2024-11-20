package login;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderRepository {

    // Metode untuk menyimpan pesanan (sudah ada di kode Anda)
    public void saveOrder(String menuName, int quantity, double price, String paymentMethod) {
        String insertQuery = "INSERT INTO Orders (menu_name, quantity, price, payment_method) VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            preparedStatement.setString(1, menuName);
            preparedStatement.setInt(2, quantity);
            preparedStatement.setDouble(3, price);
            preparedStatement.setString(4, paymentMethod);

            preparedStatement.executeUpdate();
            System.out.println("Order saved successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to save order!");
        }
    }

    // Metode baru untuk mengambil data pesanan dari database
    public List<Object[]> getOrders() {
        String selectQuery = "SELECT menu_name, quantity, price, payment_method FROM Orders";
        List<Object[]> orders = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String menuName = resultSet.getString("menu_name");
                int quantity = resultSet.getInt("quantity");
                double price = resultSet.getDouble("price");
                String paymentMethod = resultSet.getString("payment_method");

                // Tambahkan data ke list
                orders.add(new Object[]{menuName, quantity, price, paymentMethod});
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to fetch orders!");
        }

        return orders;
        
    }
    public void deleteOrder(int orderId) {
        String deleteQuery = "DELETE FROM Orders WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {

            preparedStatement.setInt(1, orderId);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Order deleted successfully!");
            } else {
                System.out.println("Order not found!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to delete order!");
        }
    }
    public void updateOrder(int orderId, String menuName, int quantity, double price, String paymentMethod) {
        String updateQuery = "UPDATE Orders SET menu_name = ?, quantity = ?, price = ?, payment_method = ? WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {

            preparedStatement.setString(1, menuName);
            preparedStatement.setInt(2, quantity);
            preparedStatement.setDouble(3, price);
            preparedStatement.setString(4, paymentMethod);
            preparedStatement.setInt(5, orderId);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Order updated successfully!");
            } else {
                System.out.println("Order not found!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to update order!");
        }
    }
}
