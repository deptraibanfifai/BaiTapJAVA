package com.library.service;

import com.library.dao.UserDAO;
import com.library.model.User;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;

    // Đăng nhập
    public User login(String username, String password) {
        User user = userDAO.findByUsername(username);
        if (user == null) return null;
        if (!BCrypt.checkpw(password, user.getPassword())) return null;
        if ("LOCKED".equals(user.getStatus())) return null;
        return user;
    }

    // Đăng ký
    public String register(User user) {
        if (userDAO.findByUsername(user.getUsername()) != null)
            return "Username đã tồn tại!";
        if (userDAO.findByEmail(user.getEmail()) != null)
            return "Email đã được sử dụng!";

        // Mã hóa password
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        user.setRole("USER");
        user.setStatus("ACTIVE");
        userDAO.save(user);
        return "OK";
    }

    // Đổi mật khẩu
    public String changePassword(int userId, String oldPass, String newPass) {
        User user = userDAO.findById(userId);
        if (!BCrypt.checkpw(oldPass, user.getPassword()))
            return "Mật khẩu cũ không đúng!";
        if (newPass.length() < 6)
            return "Mật khẩu mới phải ít nhất 6 ký tự!";
        userDAO.updatePassword(userId, BCrypt.hashpw(newPass, BCrypt.gensalt()));
        return "OK";
    }

    public User findById(int id)           { return userDAO.findById(id); }
    public List<User> findAll()            { return userDAO.findAll(); }
    public int update(User user)           { return userDAO.update(user); }
    public int delete(int id)              { return userDAO.delete(id); }
    public int countAll()                  { return userDAO.countAll(); }

    public void lockUser(int id)   { userDAO.updateStatus(id, "LOCKED"); }
    public void unlockUser(int id) { userDAO.updateStatus(id, "ACTIVE"); }
}