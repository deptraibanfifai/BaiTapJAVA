package Interfaces;

import Models.SanPham;


public interface ISanPham {
    public boolean ThemSanPham(SanPham sp);
    public boolean SuaSanPham(SanPham sp);
    public boolean XoaSanPham(int maSP);
}