package client;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class CustomerManager extends JPanel {
    private JTable table;
    private DefaultTableModel model;

    public CustomerManager() {
        setLayout(new BorderLayout());

        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"ID", "Tên", "SĐT", "Email"});

        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        JButton btnAdd = new JButton("Thêm Khách hàng");
        btnAdd.addActionListener(e -> {
            addCustomer();
            loadCustomerData();
        });

        JPanel panelTop = new JPanel();
        panelTop.add(btnAdd);

        add(panelTop, BorderLayout.SOUTH);
        add(scrollPane, BorderLayout.CENTER);

        loadCustomerData();
    }

    private void loadCustomerData() {
        model.setRowCount(0);
        try (Connection conn = Database.getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM customers");
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("phone"),
                    rs.getString("email")
                });
            }
            LogClient.send(" Tải dữ liệu khách hàng");
        } catch (SQLException e) {
            LogClient.send(" Lỗi khi tải dữ liệu khách hàng: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void addCustomer() {
        String name = JOptionPane.showInputDialog(this, "Nhập tên khách hàng:");
        String email = JOptionPane.showInputDialog(this, "Nhập email:");
        String phone = JOptionPane.showInputDialog(this, "Nhập số điện thoại:");

        if (name == null || name.trim().isEmpty()) return;

        try (Connection conn = Database.getConnection()) {
            String sql = "INSERT INTO customers (name, phone, email) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, phone);
            ps.setString(3, email);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Thêm khách hàng thành công!");
            LogClient.send(" Thêm khách hàng: " + name);
        } catch (SQLException e) {
            LogClient.send(" Lỗi khi thêm khách hàng: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi thêm khách hàng!");
        }
    }
}
