package com.course.ums.ws.user;

import com.course.ums.auth.AuthManager;
import com.course.ums.db.DBManager;
import com.course.ums.ws.JSONRoute;
import org.json.JSONObject;
import spark.Request;
import spark.Response;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by vh on 11/9/17.
 */
public class StudentAdd extends JSONRoute {

    @Override
    public JSONObject handleJSONRequest(JSONObject request) throws Exception {
        String token = request.getString("token");
        if(!DBManager.validateToken(token, AuthManager.ROLE_ADMIN)) {
            throw new RuntimeException("Unauthorized!");
        }

        int id = DBManager.addUser(request);

        PreparedStatement ps = DBManager.getConnection().prepareStatement("INSERT INTO students(id, gender, birth_date) VALUES(?, ?, ?)");
        ps.setInt(1, id);
        ps.setString(2, request.getString("gender"));
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date birthDate = df.parse(request.getString("birth_date"));
        ps.setDate(3, new java.sql.Date(birthDate.getTime()));
        ps.execute();

        JSONObject result = new JSONObject();
        result.put("id", id);

        return result;
    }
}