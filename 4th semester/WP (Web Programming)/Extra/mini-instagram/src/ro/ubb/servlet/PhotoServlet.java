package ro.ubb.servlet;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import ro.ubb.model.Photo;
import ro.ubb.model.User;
import ro.ubb.model.Vote;
import ro.ubb.service.DatabaseManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PhotoServlet extends HttpServlet {
    public PhotoServlet() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action != null) {
            if (action.equals("getPhotos")) {
                int top = Integer.parseInt(request.getParameter("top"));

                DatabaseManager databaseManager = new DatabaseManager();
                databaseManager.connect();

                List<Photo> photos =
                        databaseManager.getPhotos(top);

                databaseManager.disconnect();

                JSONObject jsonObject = new JSONObject();
                for (Photo photo: photos) {
                    User user = photo.getUser();

                    JSONObject userObject = new JSONObject();
                    userObject.put("id", user.getId());
                    userObject.put("name", user.getName());

                    JSONObject photoObject = new JSONObject();
                    photoObject.put("id", photo.getId());
                    photoObject.put("user", userObject);
                    photoObject.put("source", photo.getSource());
                    photoObject.put("votes", photo.getVotes());

                    jsonObject.put(photo.getId(), photoObject);
                }

                response.setContentType("application/json");
                response.getWriter().write(jsonObject.toString());
            }
            else if (action.equals("addPhoto")) {
                int userId = Integer.parseInt(request.getParameter("userid"));
                String source = request.getParameter("source");

                User user = new User(userId, null, null);
                Photo photo = new Photo(
                        -1,
                        user,
                        source,
                        0
                );

                DatabaseManager databaseManager = new DatabaseManager();
                databaseManager.connect();

                boolean success = databaseManager.addPhoto(photo);

                databaseManager.disconnect();

                response.setContentType("application/json");
                response.getWriter().write(JSONValue.toJSONString(success));
            }
            else if (action.equals("votePhoto")) {
                int userId = Integer.parseInt(request.getParameter("userid"));
                int photoId = Integer.parseInt(request.getParameter("photoid"));
                int rank = Integer.parseInt(request.getParameter("rank"));

                System.out.println("userid="+ userId);
                System.out.println("photoid="+ photoId);
                System.out.println("rank="+ rank);

                User user = new User();
                user.setId(userId);

                Photo photo = new Photo();
                photo.setId(photoId);

                Vote vote = new Vote(user, photo, rank);

                DatabaseManager databaseManager = new DatabaseManager();
                databaseManager.connect();

                boolean success = databaseManager.votePhoto(vote);

                databaseManager.disconnect();

                response.setContentType("application/json");
                response.getWriter().write(JSONValue.toJSONString(success));
            }
        }
    }
}
