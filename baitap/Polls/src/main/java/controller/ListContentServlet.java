package controller;

import dao.ContentDAO;
import model.Member;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class ListContentServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        Member user = (Member) req.getSession().getAttribute("user");

        req.setAttribute("list", new ContentDAO().getAll(user.getId()));

        req.getRequestDispatcher("listContent.jsp").forward(req, res);
    }
}