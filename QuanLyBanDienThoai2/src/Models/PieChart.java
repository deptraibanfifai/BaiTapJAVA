package Models;


public class PieChart {
    private String tenSP;
    private int slBan;
    public String getTenSP() {
        return tenSP;
    }
    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }
    public int getSlBan() {
        return slBan;
    }
    public void setSlBan(int slBan) {
        this.slBan = slBan;
    }
    public PieChart() {}
    public PieChart(String tenSP, int slBan) {
        this.tenSP = tenSP;
        this.slBan = slBan;
    }
}