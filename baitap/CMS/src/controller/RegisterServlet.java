package controller;

import dao.MemberDAO;
import model.Member;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class RegisterServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Member m = new Member();
        m.setUsername(request.getParameter("username"));
        m.setPassword(request.getParameter("password"));
        m.setEmail(request.getParameter("email"));

        new MemberDAO().register(m);
        response.sendRedirect("login.jsp");
    }
}
