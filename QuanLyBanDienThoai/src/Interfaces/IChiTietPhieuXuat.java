package Interfaces;

import Models.CTPhieuXuat;


public interface IChiTietPhieuXuat {
    public boolean Them(CTPhieuXuat ctpx);
    public boolean Sua(CTPhieuXuat ctpx);
    public boolean Xoa(int maPX);
}