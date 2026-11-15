package controller;

import dao.PersonDAO;
import model.Person;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class EditServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        int id = Integer.parseInt(req.getParameter("id"));

        Person p = new PersonDAO().getById(id);

        req.setAttribute("p", p);
        req.getRequestDispatcher("edit.jsp").forward(req, res);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        Person p = new Person();
        p.setId(Integer.parseInt(req.getParameter("id")));
        p.setFirstName(req.getParameter("firstName"));
        p.setLastName(req.getParameter("lastName"));

        new PersonDAO().update(p);

        res.sendRedirect("SearchServlet");
    }
}