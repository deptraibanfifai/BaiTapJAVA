package controller;

import dao.MemberDAO;
import model.Member;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class RegisterServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        Member m = new Member();
        m.setUsername(req.getParameter("username"));
        m.setPassword(req.getParameter("password"));
        m.setEmail(req.getParameter("email"));

        new MemberDAO().register(m);

        res.sendRedirect("login.jsp");
    }
}