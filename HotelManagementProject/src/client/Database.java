package client;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static final String URL = "jdbc:mysql://localhost:4306/hotel_db";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static Connection connection;

   
    public static Connection getConnection() {
        if (connection == null || isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println(" Kết nối thành công đến cơ sở dữ liệu!");
            } catch (ClassNotFoundException | SQLException e) {
                System.out.println(" Kết nối thất bại!");
                e.printStackTrace();
            }
        }
        return connection;
    }

    private static boolean isClosed() {
        try {
            return connection == null || connection.isClosed();
        } catch (SQLException e) {
            return true;
        }
    }

    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println(" Đã ngắt kết nối cơ sở dữ liệu.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
