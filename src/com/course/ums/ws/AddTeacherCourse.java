package com.course.ums.ws;

import com.course.ums.db.DBManager;
import org.json.JSONObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import static com.course.ums.auth.AuthManager.result;

public class AddTeacherCourse extends JSONRoute {
    @Override
    public JSONObject handleJSONRequest(JSONObject request) throws Exception {

        Statement statement = DBManager.getConnection().createStatement();
        ResultSet rs;

        PreparedStatement ps = DBManager.getConnection().prepareStatement("INSERT INTO teachers_courses(teachers_id,courses_id) VALUES(?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
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
