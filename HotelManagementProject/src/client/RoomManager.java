package client;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class RoomManager extends JPanel {
    private JTable table;
    private DefaultTableModel model;

    public RoomManager() {
        setLayout(new BorderLayout());

        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"ID", "Tên phòng", "Loại", "Giá", "Trạng thái"});

        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        JButton btnLoad = new JButton("Tải phòng");
        btnLoad.addActionListener(e -> loadRoomData());

        JPanel panelTop = new JPanel();
        panelTop.add(btnLoad);

        add(panelTop, BorderLayout.SOUTH);
        add(scrollPane, BorderLayout.CENTER);

        loadRoomData();
    }

    private void loadRoomData() {
        model.setRowCount(0);
        try (Connection conn = Database.getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM rooms");
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("type"),
                    rs.getDouble("price"),
                    rs.getBoolean("available") ? "Còn trống" : "Đã thuê"
                });
            }
            LogClient.send(" Tải dữ liệu phòng");
        } catch (SQLException e) {
            LogClient.send(" Lỗi khi tải dữ liệu phòng: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
