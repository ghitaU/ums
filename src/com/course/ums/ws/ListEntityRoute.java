package com.course.ums.ws;

import com.course.ums.db.DBManager;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

/**
 * Created by vh on 11/16/17.
 */
public abstract class ListEntityRoute extends JSONRoute {

    public abstract String[] getAuthorizedRoles();

    public abstract String getTableName();

    public String getIDColumnName() {
        return "id";
    }

    @Override
    public JSONObject handleJSONRequest(JSONObject request) throws Exception {
        String token = request.getString("token");

        String[] authorizedRoles = getAuthorizedRoles();
        boolean authorized = authorizedRoles.length == 0;
        int i = 0;
        while (!authorized && i < authorizedRoles.length) {
            authorized = DBManager.validateToken(token, authorizedRoles[i]);
            i++;
        }

        if (!authorized) {
            throw new RuntimeException("Unauthorized!");
        }

        String condition;
        if (request.has("id")) {
            condition = " WHERE " + getIDColumnName() + "=" + request.getInt("id");
        } else {
            condition = "";
        }

        Statement statement = DBManager.getConnection().createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM " + getTableName() + condition);

        JSONArray items = new JSONArray();
        ResultSetMetaData rsmd = rs.getMetaData();
        while (rs.next()) {
            JSONObject item = new JSONObject();

            for (i = 0; i < rsmd.getColumnCount(); i++) {
                item.put(rsmd.getColumnName(i + 1), rs.getString(i + 1));
            }

            items.put(item);
        }

        JSONObject result = new JSONObject();
        result.put("items", items);

        return result;
    }
}
