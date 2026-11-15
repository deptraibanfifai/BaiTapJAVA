package Data;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {

    // Kết nối 1 - MySQL (XAMPP)
    protected Connection getConnect() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:4306/quanlydienthoai";
        String user = "root";
        String pass = ""; // mặc định XAMPP user root không có mật khẩu
        return DriverManager.getConnection(url, user, pass);
    }
    // Có thể viết thêm getConnect2(), getConnect3() tương tự nếu muốn

    protected void closeConnect(Connection conn) throws IOException, SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }
}