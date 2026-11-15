package Models;

import java.time.LocalDate;

public class PhieuXuat {
    private int maPX;
    private LocalDate ngayXuat;
    private String tenKH;
    
    public int getMaPX() {
        return maPX;
    }
    public void setMaPX(int maPX) {
        this.maPX = maPX;
    }
    public LocalDate getNgayXuat() {
        return ngayXuat;
    }
    public void setNgayXuat(LocalDate localDate) {
        this.ngayXuat = localDate;
    }
    public String getTenKH() {
        return tenKH;
    }
    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }
    public PhieuXuat() {}
    public PhieuXuat(int maPX, LocalDate ngayXuat, String tenKH) {
        this.maPX = maPX;
        this.ngayXuat = ngayXuat;
        this.tenKH = tenKH;
    }
}
