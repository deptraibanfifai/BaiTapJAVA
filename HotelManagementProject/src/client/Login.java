package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.security.MessageDigest;
import java.sql.*;

public class Login extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;

    private JTextField txtUserLogin, txtUserRegister;
    private JPasswordField txtPassLogin, txtPassRegister;
    private JCheckBox showPassLogin, showPassRegister;

    public Login() {
        setTitle("Đăng nhập hệ thống");
        setSize(450, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(createLoginPanel(), "login");
        mainPanel.add(createRegisterPanel(), "register");

        add(mainPanel);
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(255, 192, 203));

        JLabel title = new JLabel(" Đăng nhập hệ thống", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(title, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(5, 1, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50));
        formPanel.setBackground(new Color(255, 192, 203));

        txtUserLogin = new JTextField();
        txtPassLogin = new JPasswordField();
        showPassLogin = new JCheckBox("Hiện mật khẩu");
        showPassLogin.setBackground(new Color(255, 192, 203));
        showPassLogin.addActionListener(e -> txtPassLogin.setEchoChar(showPassLogin.isSelected() ? (char) 0 : '•'));

        formPanel.add(new JLabel(" Tên đăng nhập:"));
        formPanel.add(txtUserLogin);
        formPanel.add(new JLabel(" Mật khẩu:"));
        formPanel.add(txtPassLogin);
        formPanel.add(showPassLogin);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(255, 192, 203));
        JButton btnLogin = new JButton("Đăng nhập");
        JButton btnSwitch = new JButton("Đăng ký tài khoản");

        btnLogin.addActionListener(e -> login());
        btnSwitch.addActionListener(e -> cardLayout.show(mainPanel, "register"));

        buttonPanel.add(btnLogin);
        buttonPanel.add(btnSwitch);

        panel.add(formPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createRegisterPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(255, 192, 203));

        JLabel title = new JLabel(" Đăng ký tài khoản", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(title, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(5, 1, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50));
        formPanel.setBackground(new Color(144, 238, 144));

        txtUserRegister = new JTextField();
        txtPassRegister = new JPasswordField();
        showPassRegister = new JCheckBox("Hiện mật khẩu");
        showPassRegister.setBackground(new Color(144, 238, 144));
        showPassRegister.addActionListener(e -> txtPassRegister.setEchoChar(showPassRegister.isSelected() ? (char) 0 : '•'));

        formPanel.add(new JLabel(" Tên người dùng mới:"));
        formPanel.add(txtUserRegister);
        formPanel.add(new JLabel(" Mật khẩu mới:"));
        formPanel.add(txtPassRegister);
        formPanel.add(showPassRegister);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(144, 238, 144));
        JButton btnRegister = new JButton("Đăng ký");
        JButton btnSwitch = new JButton("Quay lại đăng nhập");

        btnRegister.addActionListener(e -> register());
        btnSwitch.addActionListener(e -> cardLayout.show(mainPanel, "login"));

        buttonPanel.add(btnRegister);
        buttonPanel.add(btnSwitch);

        panel.add(formPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void login() {
        String username = txtUserLogin.getText();
        String password = new String(txtPassLogin.getPassword());

        try (Connection conn = Database.getConnection()) {
            String sql = "SELECT * FROM users WHERE username=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String storedHash = rs.getString("password");
                if (storedHash.equals(hashPassword(password))) {
                    JOptionPane.showMessageDialog(this, " Đăng nhập thành công!");
                    LogClient.send("Đăng nhập thành công cho user: " + username);
                    new Main().setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, " Sai mật khẩu!");
                    LogClient.send("Đăng nhập thất bại do sai mật khẩu: " + username);
                }
            } else {
                JOptionPane.showMessageDialog(this, "⚠ Tài khoản không tồn tại!");
                LogClient.send("Đăng nhập thất bại: tài khoản không tồn tại - " + username);
            }
        } catch (Exception e) {
            LogClient.send("Lỗi đăng nhập: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void register() {
        String username = txtUserRegister.getText();
        String password = new String(txtPassRegister.getPassword());

        try (Connection conn = Database.getConnection()) {
            String checkSql = "SELECT * FROM users WHERE username=?";
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setString(1, username);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Tên người dùng đã tồn tại!");
                return;
            }

            String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, hashPassword(password));
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, " Đăng ký thành công!");
            LogClient.send("Người dùng mới đăng ký: " + username);
            cardLayout.show(mainPanel, "login");

        } catch (Exception e) {
            LogClient.send("Lỗi đăng ký: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private String hashPassword(String password) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(password.getBytes());
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Login().setVisible(true));
    }
}
