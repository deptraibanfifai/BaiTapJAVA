package Interfaces;

import Models.PhieuNhap;
import java.util.LinkedList;


public interface IPhieuNhap {
    public LinkedList<Object[]> getData();
    public boolean ThemPhieuNhap(PhieuNhap pn);
    public boolean XoaPhieuNhap(int id);
}