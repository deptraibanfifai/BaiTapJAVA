package controller;

import dao.MemberDAO;
import model.Member;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        Member m = new MemberDAO().login(
                req.getParameter("username"),
                req.getParameter("password")
        );

        if (m != null) {
            req.getSession().setAttribute("user", m);
            res.sendRedirect("index.jsp");
        } else {
            res.sendRedirect("login.jsp");
        }
    }
}