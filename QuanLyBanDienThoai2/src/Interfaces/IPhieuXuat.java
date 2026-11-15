package Interfaces;

import Models.PhieuXuat;


public interface IPhieuXuat {
    public boolean ThemPhieuXuat(PhieuXuat px);
    public boolean SuaPhieuXuat(PhieuXuat px);
    public boolean XoaPhieuXuat(int maPX);
}