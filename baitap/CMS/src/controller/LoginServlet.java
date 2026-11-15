package controller;

import dao.MemberDAO;
import model.Member;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String user = request.getParameter("username");
        String pass = request.getParameter("password");

        MemberDAO dao = new MemberDAO();
        Member m = dao.login(user, pass);

        if (m != null) {
            request.getSession().setAttribute("user", m);
            response.sendRedirect("index.jsp");
        } else {
            response.sendRedirect("login.jsp?error=1");
        }
    }
}
