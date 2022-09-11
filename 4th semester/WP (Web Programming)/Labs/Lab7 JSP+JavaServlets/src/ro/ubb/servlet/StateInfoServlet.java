package ro.ubb.servlet;

import ro.ubb.service.DatabaseManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class StateInfoServlet extends HttpServlet {
    public StateInfoServlet() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action != null) {
            if (action.equals("saveMove")) {
                DatabaseManager databaseManager = new DatabaseManager();

                int userId = Integer.parseInt(request.getParameter("userid"));
                int position = Integer.parseInt(request.getParameter("position"));
                int score = Integer.parseInt(request.getParameter("score"));

                databaseManager.saveMove(userId, position, score);
            } else if (action.equals("deleteMoves")) {
                DatabaseManager databaseManager = new DatabaseManager();

                int userId = Integer.parseInt(request.getParameter("userid"));

                databaseManager.deleteMoves(userId);
            }
        }
    }
}
