package com.course.ums.ws;


import com.course.ums.db.DBManager;
import org.json.JSONArray;
import org.json.JSONObject;
import spark.Request;
import spark.Response;

import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by vh on 11/2/17.
 */
public class ListUsers extends JSONRoute {

    @Override
    public JSONObject handleJSONRequest(JSONObject request) throws Exception {
        Statement statement = DBManager.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM users");

        JSONObject result = new JSONObject();
        JSONArray users = new JSONArray();
        result.put("users", users);
        while (resultSet.next()) {
            JSONObject user = new JSONObject();

            user.put("id", resultSet.getInt("id"));
            user.put("first_name", resultSet.getString("first_name"));
            user.put("last_name", resultSet.getString("last_name"));
            user.put("email", resultSet.getString("email"));
            user.put("password", resultSet.getString("password"));

            users.put(user);
        }

        return result;
    }
}
