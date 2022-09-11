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

public class LoginServlet extends HttpServlet {
    public LoginServlet() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        RequestDispatcher requestDispatcher = null;

        DatabaseManager databaseManager = new DatabaseManager();
        databaseManager.connect();

        User user = databaseManager.authenticateUser(username, password);

        databaseManager.disconnect();

        if (user != null) {
            requestDispatcher = request.getRequestDispatcher("/loginSuccess.jsp");

            HttpSession session = request.getSession();
            session.setAttribute("user", user);
        } else {
            requestDispatcher = request.getRequestDispatcher("/loginFail.jsp");
        }
        requestDispatcher.forward(request, response);
    }
}
