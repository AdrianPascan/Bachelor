package ro.ubb.servlet;

import netscape.javascript.JSObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import ro.ubb.model.Asset;
import ro.ubb.service.DatabaseManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AssetsServlet extends HttpServlet {
    public AssetsServlet() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action != null) {
            if (action.equals("get")) {
                DatabaseManager databaseManager = new DatabaseManager();
                databaseManager.connect();

                List<Asset> assets =
                        databaseManager.getUserAssets(Integer.parseInt(request.getParameter("userid")));

                databaseManager.disconnect();

                JSONArray jsonArray = new JSONArray();
                for (Asset asset: assets) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("id", asset.getId());
                    jsonObject.put("userid", asset.getUserId());
                    jsonObject.put("name", asset.getName());
                    jsonObject.put("description", asset.getDescription());
                    jsonObject.put("value", asset.getValue());

                    jsonArray.add(jsonObject);
                }

                response.setContentType("application/json");
                response.getWriter().write(jsonArray.toString());
            }
            else if (action.equals("add")) {
                int userId = Integer.parseInt(request.getParameter("userid"));

                List<Asset> assets = new ArrayList<>();

                int index = 0;
                while (request.getParameter(String.format("assets[%d][name]", index)) != null) {
                    String name =
                            request.getParameter(String.format("assets[%d][name]", index));
                    String description =
                            request.getParameter(String.format("assets[%d][description]", index));
                    int value =
                            Integer.parseInt( request.getParameter(String.format("assets[%d][value]", index)) );

                    Asset asset = new Asset(-1, userId, name, description, value);
                    assets.add(asset);

                    index ++;
                }

                DatabaseManager databaseManager = new DatabaseManager();
                databaseManager.connect();

                boolean success = databaseManager.addUserAssets(assets);

                databaseManager.disconnect();

                response.setContentType("application/json");
                response.getWriter().write(JSONValue.toJSONString(success));
            }
        }
    }
}
