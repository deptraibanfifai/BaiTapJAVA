package Models;

import org.mindrot.jbcrypt.BCrypt;

public class TaiKhoan {
    private int id;
    private String tenDN;
    private String matKhau;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenDN() {
        return tenDN;
    }

    public void setTenDN(String tenDN) {
        if (tenDN == null || tenDN.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên đăng nhập không được rỗng");
        }
        this.tenDN = tenDN;
    }

    public String getMatKhau() {
        return matKhau;
    }

 // Ví dụ sử dụng bcrypt (cần thư viện như BCrypt)
    public void setMatKhau(String matKhau) {
        if (matKhau == null || matKhau.trim().isEmpty()) {
            throw new IllegalArgumentException("Mật khẩu không được rỗng");
        }
        this.matKhau = BCrypt.hashpw(matKhau, BCrypt.gensalt());
    }
    
    public TaiKhoan() {}

    public TaiKhoan(int id, String tenDN, String matKhau) {
        this.id = id;
        this.tenDN = tenDN;
        this.matKhau = matKhau;
    }
}