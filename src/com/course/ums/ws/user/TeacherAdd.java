package com.course.ums.ws.user;

import com.course.ums.auth.AuthManager;
import com.course.ums.db.DBManager;
import com.course.ums.ws.JSONRoute;
import org.json.JSONObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by vh on 11/9/17.
 */
public class TeacherAdd extends JSONRoute {
    @Override
    public JSONObject handleJSONRequest(JSONObject request) throws Exception {
        String token = request.getString("token");
        if(!DBManager.validateToken(token, AuthManager.ROLE_ADMIN)) {
            throw new RuntimeException("Unauthorized!");
        }

        int id = DBManager.addUser(request);

        PreparedStatement ps = DBManager.getConnection().prepareStatement("INSERT INTO teachers(id, level) VALUES(?, ?)");
        ps.setInt(1, id);
        ps.setInt(2, request.getInt("level"));
        ps.execute();

        JSONObject result = new JSONObject();
        result.put("id", id);

        return result;    }
}
