package controller;

import dao.PersonDAO;
import model.Person;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class SearchServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String name = req.getParameter("name");

        List<Person> list = new PersonDAO().search(name);

        req.setAttribute("list", list);
        req.getRequestDispatcher("search.jsp").forward(req, res);
    }
}