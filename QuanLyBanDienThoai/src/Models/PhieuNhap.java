package Models;

import java.time.LocalDate;

public class PhieuNhap {
    private int maPN;
    private LocalDate ngayNhap;
    private int maNCC;

    public int getMaPN() {
        return maPN;
    }
    public void setMaPN(int maPN) {
        this.maPN = maPN;
    }
    public LocalDate getNgayNhap() {
        return ngayNhap;
    }
    public void setNgayNhap(LocalDate ngayNhap) {
        this.ngayNhap = ngayNhap;
    }
    public int getMaNCC() {
        return maNCC;
    }
    public void setMaNCC(int maNCC) {
        this.maNCC = maNCC;
    }
    public PhieuNhap() {}
    public PhieuNhap(int maPN, LocalDate ngayNhap, int maNCC) {
        this.maPN = maPN;
        this.ngayNhap = ngayNhap;
        this.maNCC = maNCC;
    }
}