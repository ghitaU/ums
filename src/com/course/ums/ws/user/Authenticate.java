package com.course.ums.ws.user;

import com.course.ums.auth.AuthManager;
import com.course.ums.db.DBManager;
import com.course.ums.ws.JSONRoute;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Authenticate extends JSONRoute {
    @Override
    public JSONObject handleJSONRequest(JSONObject request) throws Exception {
        String email = request.getString("email");
        String password = request.getString("password");

        PreparedStatement preparedStatement = DBManager.getConnection().prepareStatement("SELECT id FROM users WHERE email=? AND password=?");
        preparedStatement.setString(1, email);
        preparedStatement.setString(2, password);

        ResultSet rs = preparedStatement.executeQuery();
        rs.next();
        int id = rs.getInt("id");

        JSONArray roles = new JSONArray();
        preparedStatement = DBManager.getConnection().prepareStatement("SELECT id FROM administrators WHERE id=?");
        preparedStatement.setInt(1, id);
        rs = preparedStatement.executeQuery();

        if (rs.next()) {
            roles.put(AuthManager.ROLE_ADMIN);
        }

        preparedStatement = DBManager.getConnection().prepareStatement("SELECT id FROM students WHERE id=?");
        preparedStatement.setInt(1, id);
        rs = preparedStatement.executeQuery();

        if (rs.next()) {
            roles.put(AuthManager.ROLE_STUDENT);
        }

        preparedStatement = DBManager.getConnection().prepareStatement("SELECT id FROM teachers WHERE id=?");
        preparedStatement.setInt(1, id);
        rs = preparedStatement.executeQuery();

        if (rs.next()) {
            roles.put(AuthManager.ROLE_TEACHER);
        }

        if (roles.length() == 0) {
            throw new RuntimeException("No roles for user " + id);
        }

        JSONObject result = new JSONObject();
        result.put("token", id);
        result.put("roles", roles);

        return result;
    }

}