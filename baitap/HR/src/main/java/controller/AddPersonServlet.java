package controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import dao.PersonDAO;
import model.Person;

public class AddPersonServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException {

        Person p = new Person();
        p.setFirstName(req.getParameter("firstName"));
        p.setLastName(req.getParameter("lastName"));

        new PersonDAO().insert(p);

        res.sendRedirect("search.jsp");
    }
}