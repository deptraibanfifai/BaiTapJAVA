package Controllers;

import Data.DBConnect;
import Interfaces.IPhieuNhap;
import Models.PhieuNhap;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Date; // Add this import for java.sql.Date

public class PhieuNhapController extends DBConnect implements IPhieuNhap {
    private final java.sql.Connection conn;

    public PhieuNhapController() throws ClassNotFoundException, SQLException {
        this.conn = getConnect();
    }

    @Override
    public boolean ThemPhieuNhap(PhieuNhap pn) {
        try {
            String query = "INSERT INTO PhieuNhap(NgayNhap, MaNCC) VALUES (?, ?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setDate(1, Date.valueOf(pn.getNgayNhap())); // Convert LocalDate to java.sql.Date
            ps.setInt(2, pn.getMaNCC());

            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(PhieuNhapController.class.getName()).log(Level.SEVERE, "Failed to insert PhieuNhap", ex);
        }
        return false;
    }

    @Override
    public boolean XoaPhieuNhap(int maPN) {
        String query = "DELETE FROM PhieuNhap WHERE MaPN = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, maPN);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(PhieuNhapController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean XoaPnQuaMaNCC(int maNCC) {
        PreparedStatement ps = null;
        try {
            String query = "DELETE FROM phieunhap WHERE maNCC = ?";
            ps = conn.prepareStatement(query);
            ps.setInt(1, maNCC);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            Logger.getLogger(PhieuNhapController.class.getName()).log(Level.SEVERE, "Lỗi SQL: ", ex);
            return false;
        } finally {
            try {
                if (ps != null) ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(PhieuNhapController.class.getName()).log(Level.SEVERE, "Lỗi khi đóng PreparedStatement: ", ex);
            }
        }
    }

    // Phương thức lấy mã phiếu nhập
    public int getMaPN(PhieuNhap pn) throws SQLException {
        String query = "SELECT MaPN FROM PhieuNhap WHERE NgayNhap = ? AND MaNCC = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setDate(1, Date.valueOf(pn.getNgayNhap())); // Convert LocalDate to java.sql.Date
        ps.setInt(2, pn.getMaNCC());

        var rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt(1);
        }
        return 0;
    }

    @Override
    public LinkedList<Object[]> getData() {
        LinkedList<Object[]> lstPN = new LinkedList<>();
        String query = "SELECT pn.MaPN, sp.TenSP, ncc.TenNCC, ncc.DiaChi, ncc.sdt, sp.DvTinh, pn.NgayNhap, ctpn.SLNhap, ctpn.DGNhap "
                + "FROM PhieuNhap pn, NhaCungCap ncc, SanPham sp, CTPhieuNhap ctpn "
                + "WHERE pn.MaPN = ctpn.MaPN AND sp.MaSP = ctpn.MaSP AND pn.MaNCC = ncc.MaNCC";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            var rs = ps.executeQuery();
            while (rs.next()) {
                int maPN = rs.getInt(1);
                String tenSP = rs.getString(2);
                String tenNCC = rs.getString(3);
                String diaChi = rs.getString(4);
                String sdt = rs.getString(5);
                String dvTinh = rs.getString(6);
                String ngayNhap = rs.getDate(7).toString(); // Convert DATE to String for display
                int slNhap = rs.getInt(8);
                double getdgNhap = rs.getDouble(9);
                BigDecimal dgNhap = new BigDecimal(getdgNhap);

                Object[] data = new Object[] {maPN, tenSP, tenNCC, diaChi, sdt, dvTinh, ngayNhap, slNhap, dgNhap};
                lstPN.add(data);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PhieuNhapController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lstPN;
    }
}