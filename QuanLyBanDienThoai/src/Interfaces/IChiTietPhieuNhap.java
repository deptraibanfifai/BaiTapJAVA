package Interfaces;

import Models.CTPhieuNhap;


public interface IChiTietPhieuNhap {
    public boolean ThemCTPhieuNhap(CTPhieuNhap ctpn);
    public boolean SuaCTPhieuNhap(CTPhieuNhap ctpn);
    public boolean XoaCTPhieuNhap(int maPN, int maSP);
}