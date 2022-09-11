package ro.ubb.servlet;

import ro.ubb.model.User;
import ro.ubb.service.DatabaseManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.System;

public class LogoutServlet extends HttpServlet {
    public LogoutServlet() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {

        DatabaseManager databaseManager = new DatabaseManager();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        databaseManager.saveTime(user.getId(),System.currentTimeMillis() - session.getCreationTime());
        session.invalidate();

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/index.html");
        requestDispatcher.forward(request, response);
    }
}
