package controller;

import dao.ContentDAO;
import model.Content;
import model.Member;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class AddContentServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        Member user = (Member) req.getSession().getAttribute("user");

        Content c = new Content();
        c.setTitle(req.getParameter("title"));
        c.setBrief(req.getParameter("brief"));
        c.setContent(req.getParameter("content"));
        c.setAuthorId(user.getId());

        new ContentDAO().insert(c);

        res.sendRedirect("ListContentServlet");
    }
}