package Interfaces;

import Models.NhaCungCap;
import java.util.LinkedList;


public interface INhaCungCap {
    public boolean ThemNCC(NhaCungCap ncc);
    public boolean SuaNCC(NhaCungCap ncc);
    public boolean XoaNCC(int id);
    public LinkedList<Object[]> TimKiemNCC(String searchString);
}