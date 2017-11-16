package com.course.ums.ws;


import com.course.ums.db.DBManager;
import org.json.JSONArray;
import org.json.JSONObject;
import spark.Request;
import spark.Response;
import spark.Route;

import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by vh on 11/2/17.
 */
public class ListUsers extends MyRoute {

    @Override
    public Object myHandle(Request request, Response response) throws Exception {
        Statement statement = DBManager.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM users");

        JSONArray users = new JSONArray();
        while (resultSet.next()) {
            JSONObject user = new JSONObject();

            user.put("id", resultSet.getInt("id"));
            user.put("first_name", resultSet.getString("first_name"));
            user.put("last_name", resultSet.getString("last_name"));
            user.put("email", resultSet.getString("email"));
            user.put("password", resultSet.getString("password"));

            users.put(user);
        }

        return users;
    }
}
