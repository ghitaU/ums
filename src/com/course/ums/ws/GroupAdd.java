package com.course.ums.ws;

import com.course.ums.auth.AuthManager;
import com.course.ums.db.DBManager;
import org.json.JSONObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import static com.course.ums.auth.AuthManager.result;

public class GroupAdd extends JSONRoute {
    @Override
    public JSONObject handleJSONRequest(JSONObject request) throws Exception {
        String token = request.getString("token");
        if (!DBManager.validateToken(token, AuthManager.ROLE_ADMIN)) {
            throw new RuntimeException("Unauthorized!");
        }

        Statement statement = DBManager.getConnection().createStatement();
        ResultSet rs ;

        PreparedStatement ps = DBManager.getConnection().prepareStatement("INSERT INTO groups(semester_id) VALUES(?)", PreparedStatement.RETURN_GENERATED_KEYS);
        ps.setString(1, String.valueOf(request.getInt("semester_id")));

        ps.execute();
        rs = ps.getGeneratedKeys();
        rs.next();
        int id = rs.getInt("id");
        result.put("id",id);

        return result;
    }
}
