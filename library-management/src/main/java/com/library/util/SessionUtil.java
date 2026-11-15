package com.library.util;

import com.library.model.User;
import javax.servlet.http.HttpSession;

public class SessionUtil {

    public static User getLoggedInUser(HttpSession session) {
        return (User) session.getAttribute("user");
    }

    public static boolean isLoggedIn(HttpSession session) {
        return session.getAttribute("user") != null;
    }

    public static boolean isAdmin(HttpSession session) {
        User user = getLoggedInUser(session);
        return user != null && "ADMIN".equals(user.getRole());
    }

    public static void setUser(HttpSession session, User user) {
        session.setAttribute("user", user);
    }

    public static void logout(HttpSession session) {
        session.invalidate();
    }
}