package views;

import model.Database;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class HotelManagementApp {

    private static JFrame frame;

    public static void main(String[] args) {
        Connection connection = Database.connect();

        frame = new JFrame("Quản lý khách sạn");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 800);
        frame.setLayout(new BorderLayout());

        JTree tree = createMenuTree();
        tree.setSelectionRow(0);
        JScrollPane treeScroll = new JScrollPane(tree);
        frame.add(treeScroll, BorderLayout.WEST);

        JPanel mainPanel = new JPanel(new CardLayout());

        mainPanel.add(createRoomManagementPanel(connection), "Quản lý phòng");
        mainPanel.add(createCustomerManagementPanel(connection), "Quản lý khách hàng");
        mainPanel.add(createStatsPanel(connection), "Thống kê");

        frame.add(mainPanel, BorderLayout.CENTER);

        tree.addTreeSelectionListener(e -> {
            TreePath selectedPath = e.getNewLeadSelectionPath();
            if (selectedPath != null) {
                String selectedNode = selectedPath.getLastPathComponent().toString();
                CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
                cardLayout.show(mainPanel, selectedNode);
            }
        });

        frame.setVisible(true);

        Runtime.getRuntime().addShutdownHook(new Thread(Database::disconnect));
    }
    private static JTree createMenuTree() {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Quản lý khách sạn");

        root.add(new DefaultMutableTreeNode("Quản lý phòng"));
        root.add(new DefaultMutableTreeNode("Quản lý khách hàng"));
        root.add(new DefaultMutableTreeNode("Thống kê"));

        JTree tree = new JTree(root);
        tree.setRootVisible(false); // Ẩn node gốc
        return tree;
    }
    private static JPanel createCustomerManagementPanel(Connection connection) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.YELLOW);

        JLabel label = new JLabel("Danh sách khách hàng", SwingConstants.CENTER);
        panel.add(label, BorderLayout.NORTH);

        String[] columns = {"Mã KH", "Tên", "SĐT", "Địa chỉ", "Email", "Phòng đã đặt"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        loadCustomersFromDatabase(connection, model);

        // Buttons
        JPanel btnPanel = new JPanel();
        JTextField searchField = new JTextField(10);
        JButton searchBtn = new JButton("Tìm");
        JButton addBtn = new JButton("Thêm");
        JButton editBtn = new JButton("Sửa");
        JButton deleteBtn = new JButton("Xóa");
        JButton refreshBtn = new JButton("Làm mới");

        btnPanel.add(searchField);
        btnPanel.add(searchBtn);
        btnPanel.add(addBtn);
        btnPanel.add(editBtn);
        btnPanel.add(deleteBtn);
        btnPanel.add(refreshBtn);

        panel.add(btnPanel, BorderLayout.SOUTH);

        // Event: Tìm kiếm
        searchBtn.addActionListener(e -> {
            String keyword = searchField.getText().trim();
            searchCustomers(connection, model, keyword);
        });

        // Event: Làm mới
        refreshBtn.addActionListener(e -> loadCustomersFromDatabase(connection, model));

        // Event: Thêm
        addBtn.addActionListener(e -> {
            JTextField id = new JTextField(), name = new JTextField(), phone = new JTextField();
            JTextField addr = new JTextField(), email = new JTextField(), room = new JTextField();
            JPanel input = new JPanel(new GridLayout(6, 2));
            input.add(new JLabel("Mã KH:")); input.add(id);
            input.add(new JLabel("Tên:")); input.add(name);
            input.add(new JLabel("SĐT:")); input.add(phone);
            input.add(new JLabel("Địa chỉ:")); input.add(addr);
            input.add(new JLabel("Email:")); input.add(email);
            input.add(new JLabel("Phòng:")); input.add(room);
            int result = JOptionPane.showConfirmDialog(null, input, "Thêm khách hàng", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                try {
                    PreparedStatement stmt = connection.prepareStatement(
                            "INSERT INTO customers VALUES (?, ?, ?, ?, ?, ?)");
                    stmt.setString(1, id.getText());
                    stmt.setString(2, name.getText());
                    stmt.setString(3, phone.getText());
                    stmt.setString(4, addr.getText());
                    stmt.setString(5, email.getText());
                    stmt.setString(6, room.getText());
                    stmt.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Thêm thành công!");
                    loadCustomersFromDatabase(connection, model);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Lỗi: " + ex.getMessage());
                }
            }
        });

        // Event: Sửa
        editBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Chọn dòng cần sửa.");
                return;
            }
            String id = model.getValueAt(row, 0).toString();
            JTextField name = new JTextField(model.getValueAt(row, 1).toString());
            JTextField phone = new JTextField(model.getValueAt(row, 2).toString());
            JTextField addr = new JTextField(model.getValueAt(row, 3).toString());
            JTextField email = new JTextField(model.getValueAt(row, 4).toString());
            JTextField room = new JTextField(model.getValueAt(row, 5).toString());

            JPanel input = new JPanel(new GridLayout(5, 2));
            input.add(new JLabel("Tên:")); input.add(name);
            input.add(new JLabel("SĐT:")); input.add(phone);
            input.add(new JLabel("Địa chỉ:")); input.add(addr);
            input.add(new JLabel("Email:")); input.add(email);
            input.add(new JLabel("Phòng:")); input.add(room);

            int result = JOptionPane.showConfirmDialog(null, input, "Sửa khách hàng", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                try {
                    PreparedStatement stmt = connection.prepareStatement(
                            "UPDATE customers SET customername=?, phone=?, address=?, email=?, bookedRoom=? WHERE customerid=?");
                    stmt.setString(1, name.getText());
                    stmt.setString(2, phone.getText());
                    stmt.setString(3, addr.getText());
                    stmt.setString(4, email.getText());
                    stmt.setString(5, room.getText());
                    stmt.setString(6, id);
                    stmt.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Sửa thành công!");
                    loadCustomersFromDatabase(connection, model);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Lỗi: " + ex.getMessage());
                }
            }
        });

        // Event: Xóa
        deleteBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Chọn dòng cần xóa.");
                return;
            }
            String id = model.getValueAt(row, 0).toString();
            int confirm = JOptionPane.showConfirmDialog(null, "Xác nhận xóa?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    PreparedStatement stmt = connection.prepareStatement("DELETE FROM customers WHERE customerid=?");
                    stmt.setString(1, id);
                    stmt.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Xóa thành công!");
                    loadCustomersFromDatabase(connection, model);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Lỗi: " + ex.getMessage());
                }
            }
        });

        return panel;
    }
    private static JPanel createRoomManagementPanel(Connection connection) {
        JPanel panel = new JPanel(new BorderLayout());

        JLabel label = new JLabel("Danh sách phòng", SwingConstants.CENTER);
        panel.add(label, BorderLayout.NORTH);

        String[] columns = {"Mã phòng", "Loại phòng", "Giá", "Trạng thái"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        loadRoomsFromDatabase(connection, model);

        JPanel btnPanel = new JPanel();
        JTextField searchField = new JTextField(10);
        JButton searchBtn = new JButton("Tìm");
        JButton addBtn = new JButton("Thêm");
        JButton editBtn = new JButton("Sửa");
        JButton deleteBtn = new JButton("Xóa");
        JButton refreshBtn = new JButton("Làm mới");

        btnPanel.add(searchField);
        btnPanel.add(searchBtn);
        btnPanel.add(addBtn);
        btnPanel.add(editBtn);
        btnPanel.add(deleteBtn);
        btnPanel.add(refreshBtn);
        panel.add(btnPanel, BorderLayout.SOUTH);

        searchBtn.addActionListener(e -> searchRooms(connection, model, searchField.getText().trim()));
        refreshBtn.addActionListener(e -> loadRoomsFromDatabase(connection, model));

        addBtn.addActionListener(e -> {
            JTextField id = new JTextField(), type = new JTextField(), price = new JTextField(), status = new JTextField();
            JPanel input = new JPanel(new GridLayout(4, 2));
            input.add(new JLabel("Mã phòng:")); input.add(id);
            input.add(new JLabel("Loại phòng:")); input.add(type);
            input.add(new JLabel("Giá:")); input.add(price);
            input.add(new JLabel("Trạng thái:")); input.add(status);
            int result = JOptionPane.showConfirmDialog(null, input, "Thêm phòng", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                try {
                    PreparedStatement stmt = connection.prepareStatement("INSERT INTO rooms VALUES (?, ?, ?, ?)");
                    stmt.setString(1, id.getText());
                    stmt.setString(2, type.getText());
                    stmt.setString(3, price.getText());
                    stmt.setString(4, status.getText());
                    stmt.executeUpdate();
                    loadRoomsFromDatabase(connection, model);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Lỗi: " + ex.getMessage());
                }
            }
        });

        editBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Chọn phòng để sửa.");
                return;
            }
            String id = model.getValueAt(row, 0).toString();
            JTextField type = new JTextField(model.getValueAt(row, 1).toString());
            JTextField price = new JTextField(model.getValueAt(row, 2).toString());
            JTextField status = new JTextField(model.getValueAt(row, 3).toString());

            JPanel input = new JPanel(new GridLayout(3, 2));
            input.add(new JLabel("Loại phòng:")); input.add(type);
            input.add(new JLabel("Giá:")); input.add(price);
            input.add(new JLabel("Trạng thái:")); input.add(status);

            int result = JOptionPane.showConfirmDialog(null, input, "Sửa phòng", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                try {
                    PreparedStatement stmt = connection.prepareStatement(
                        "UPDATE rooms SET roomtype=?, price=?, status=? WHERE roomid=?");
                    stmt.setString(1, type.getText());
                    stmt.setString(2, price.getText());
                    stmt.setString(3, status.getText());
                    stmt.setString(4, id);
                    stmt.executeUpdate();
                    loadRoomsFromDatabase(connection, model);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Lỗi: " + ex.getMessage());
                }
            }
        });

        deleteBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Chọn phòng để xóa.");
                return;
            }
            String id = model.getValueAt(row, 0).toString();
            int confirm = JOptionPane.showConfirmDialog(null, "Xác nhận xóa phòng?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    PreparedStatement stmt = connection.prepareStatement("DELETE FROM rooms WHERE roomid=?");
                    stmt.setString(1, id);
                    stmt.executeUpdate();
                    loadRoomsFromDatabase(connection, model);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Lỗi: " + ex.getMessage());
                }
            }
        });

        return panel;
    }
    private static JPanel createStatsPanel(Connection connection) {
        JPanel panel = new JPanel(new BorderLayout());

        JLabel label = new JLabel("Thống kê khách hàng", SwingConstants.CENTER);
        panel.add(label, BorderLayout.NORTH);

        JButton button = new JButton("Hiển thị thống kê");
        button.addActionListener(e -> showCustomerStats(connection));
        panel.add(button, BorderLayout.CENTER);

        return panel;
    }

    private static void showCustomerStats(Connection connection) {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT COUNT(*) AS total FROM customers");
            ResultSet rs = stmt.executeQuery();
            rs.next();
            int total = rs.getInt("total");

            String customerQuery = """
                SELECT customername, address, bookedRoom 
                FROM customers 
                WHERE bookedRoom IS NOT NULL
                ORDER BY address, bookedRoom
                """;
            stmt = connection.prepareStatement(customerQuery);
            rs = stmt.executeQuery();

            DefaultTableModel customerModel = new DefaultTableModel(new String[]{"Tên", "Địa chỉ", "Phòng"}, 0);
            while (rs.next()) {
                customerModel.addRow(new Object[]{
                    rs.getString("customername"),
                    rs.getString("address"),
                    rs.getString("bookedRoom")
                });
            }

            String roomQuery = """
                SELECT roomid, roomtype 
                FROM rooms 
                WHERE roomid NOT IN (SELECT bookedRoom FROM customers WHERE bookedRoom IS NOT NULL)
                ORDER BY roomid
                """;
            stmt = connection.prepareStatement(roomQuery);
            rs = stmt.executeQuery();

            DefaultTableModel roomModel = new DefaultTableModel(new String[]{"Mã phòng", "Loại phòng"}, 0);
            while (rs.next()) {
                roomModel.addRow(new Object[]{rs.getString("roomid"), rs.getString("roomtype")});
            }

            JLabel summary = new JLabel("Tổng số khách hàng: " + total, SwingConstants.CENTER);

            JTable customerTable = new JTable(customerModel);
            JTable roomTable = new JTable(roomModel);

            JPanel customerPanel = new JPanel(new BorderLayout());
            customerPanel.add(new JLabel("Khách hàng đã đặt:"), BorderLayout.NORTH);
            customerPanel.add(new JScrollPane(customerTable), BorderLayout.CENTER);

            JPanel roomPanel = new JPanel(new BorderLayout());
            roomPanel.add(new JLabel("Phòng còn trống:"), BorderLayout.NORTH);
            roomPanel.add(new JScrollPane(roomTable), BorderLayout.CENTER);

            JPanel statsPanel = new JPanel(new BorderLayout());
            statsPanel.add(summary, BorderLayout.NORTH);
            statsPanel.add(new JSplitPane(JSplitPane.VERTICAL_SPLIT, customerPanel, roomPanel), BorderLayout.CENTER);

            JOptionPane.showMessageDialog(null, statsPanel, "Thống kê", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Lỗi: " + ex.getMessage());
        }
    }
    private static void loadCustomersFromDatabase(Connection connection, DefaultTableModel model) {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM customers");
            ResultSet rs = stmt.executeQuery();
            model.setRowCount(0);
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("customerid"),
                    rs.getString("customername"),
                    rs.getString("phone"),
                    rs.getString("address"),
                    rs.getString("email"),
                    rs.getString("bookedRoom")
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Lỗi: " + ex.getMessage());
        }
    }

    private static void searchCustomers(Connection connection, DefaultTableModel model, String keyword) {
        try {
            PreparedStatement stmt = connection.prepareStatement(
                "SELECT * FROM customers WHERE customername LIKE ?");
            stmt.setString(1, "%" + keyword + "%");
            ResultSet rs = stmt.executeQuery();
            model.setRowCount(0);
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("customerid"),
                    rs.getString("customername"),
                    rs.getString("phone"),
                    rs.getString("address"),
                    rs.getString("email"),
                    rs.getString("bookedRoom")
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Lỗi: " + ex.getMessage());
        }
    }

    private static void loadRoomsFromDatabase(Connection connection, DefaultTableModel model) {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM rooms");
            ResultSet rs = stmt.executeQuery();
            model.setRowCount(0);
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("roomid"),
                    rs.getString("roomtype"),
                    rs.getString("price"),
                    rs.getString("status")
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Lỗi: " + ex.getMessage());
        }
    }

    private static void searchRooms(Connection connection, DefaultTableModel model, String keyword) {
        try {
            PreparedStatement stmt = connection.prepareStatement(
                "SELECT * FROM rooms WHERE roomid LIKE ? OR roomtype LIKE ? OR status LIKE ?");
            stmt.setString(1, "%" + keyword + "%");
            stmt.setString(2, "%" + keyword + "%");
            stmt.setString(3, "%" + keyword + "%");
            ResultSet rs = stmt.executeQuery();
            model.setRowCount(0);
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("roomid"),
                    rs.getString("roomtype"),
                    rs.getString("price"),
                    rs.getString("status")
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Lỗi: " + ex.getMessage());
        }
    }
}

