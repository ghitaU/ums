package com.course.ums.ws;

import com.course.ums.auth.AuthManager;
import com.course.ums.db.DBManager;
import org.json.JSONObject;
import spark.Request;
import spark.Response;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import static com.course.ums.auth.AuthManager.result;

public class RemoveTeacherCourse extends JSONRoute {
    @Override
    public JSONObject handleJSONRequest(JSONObject request) throws Exception {
        String token = request.getString("token");
        if (!DBManager.validateToken(token, AuthManager.ROLE_ADMIN)) {
            throw new RuntimeException("Unauthorized!");
        }

        Statement statement = DBManager.getConnection().createStatement();
        ResultSet rs;

        PreparedStatement ps = DBManager.getConnection().prepareStatement("DELETE FROM teachers_courses where teachers_id = (?) and courses_id = (?)", PreparedStatement.RETURN_GENERATED_KEYS);
        ps.setString(1, String.valueOf(request.getInt("teachers_id")));
        ps.setString(2, String.valueOf(request.getInt("courses_id")));

        ps.execute();
        rs = ps.getGeneratedKeys();
        rs.next();
        int id = rs.getInt("id");
        result.put("id", id);

        return result;
    }
}
