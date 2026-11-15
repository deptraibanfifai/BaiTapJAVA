package client;

import java.sql.*;
import java.util.ArrayList;

public class RoomDAO {
    private Connection conn;

    public RoomDAO() {
    	conn = Database.getConnection(); 
    }

    public boolean addRoom(Room room) {
        String sql = "INSERT INTO rooms (name, type, price, available) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, room.getName());
            ps.setString(2, room.getType());
            ps.setDouble(3, room.getPrice());
            ps.setBoolean(4, room.isAvailable());
            boolean result = ps.executeUpdate() > 0;
            if (result) {
                LogClient.send(" Thêm phòng thành công: " + room.getName());
            }
            return result;
        } catch (SQLException e) {
            LogClient.send(" Lỗi khi thêm phòng: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<Room> getAllRooms() {
        ArrayList<Room> list = new ArrayList<>();
        String sql = "SELECT * FROM rooms";
        try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Room r = new Room(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("type"),
                        rs.getDouble("price"),
                        rs.getBoolean("available")
                );
                list.add(r);
            }
            LogClient.send(" Đã load danh sách phòng từ cơ sở dữ liệu");
        } catch (SQLException e) {
            LogClient.send(" Lỗi khi load danh sách phòng: " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    public boolean deleteRoom(int id) {
        String sql = "DELETE FROM rooms WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            boolean result = ps.executeUpdate() > 0;
            if (result) {
                LogClient.send(" Đã xóa phòng với ID: " + id);
            }
            return result;
        } catch (SQLException e) {
            LogClient.send(" Lỗi khi xóa phòng: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateRoom(Room room) {
        String sql = "UPDATE rooms SET name=?, type=?, price=?, available=? WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, room.getName());
            ps.setString(2, room.getType());
            ps.setDouble(3, room.getPrice());
            ps.setBoolean(4, room.isAvailable());
            ps.setInt(5, room.getId());
            boolean result = ps.executeUpdate() > 0;
            if (result) {
                LogClient.send(" Đã cập nhật phòng: ID=" + room.getId());
            }
            return result;
        } catch (SQLException e) {
            LogClient.send(" Lỗi khi cập nhật phòng: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}