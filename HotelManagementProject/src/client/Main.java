package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame {
    private JPanel contentPanel;

    public Main() {
        setTitle("Hệ Thống Quản Lý Khách Sạn");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel navPanel = new JPanel();
        navPanel.setLayout(new BoxLayout(navPanel, BoxLayout.Y_AXIS));
        navPanel.setPreferredSize(new Dimension(200, 0));

        JButton btnCustomers = new JButton("Quản lý Khách hàng");
        JButton btnRooms = new JButton("Quản lý Phòng");

        navPanel.add(btnCustomers);
        navPanel.add(btnRooms);

        contentPanel = new JPanel(new BorderLayout());

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, navPanel, contentPanel);
        splitPane.setDividerLocation(200);
        add(splitPane, BorderLayout.CENTER);

        // Đồng hồ ở góc dưới bên phải
        JLabel clockLabel = new JLabel("Đồng hồ đang tải...");
        clockLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        clockLabel.setFont(new Font("Arial", Font.PLAIN, 13));

        JPanel clockPanel = new JPanel(new BorderLayout());
        clockPanel.add(clockLabel, BorderLayout.EAST);
        add(clockPanel, BorderLayout.SOUTH);

        ClockThread clockThread = new ClockThread(clockLabel);
        clockThread.start();

        LogClient.send(" Ứng dụng đã khởi động lúc " + new java.util.Date());

        btnCustomers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LogClient.send(" Chọn quản lý khách hàng");
                showCustomerManager();
            }
        });

        btnRooms.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LogClient.send(" Chọn quản lý phòng");
                showRoomManager();
            }
        });
    }

    private void showCustomerManager() {
        contentPanel.removeAll();
        contentPanel.add(new CustomerManager());
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void showRoomManager() {
        contentPanel.removeAll();
        contentPanel.add(new RoomManager());
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Login().setVisible(true));
    }
}
