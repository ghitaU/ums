package com.course.ums.ws.user;

import com.course.ums.auth.AuthManager;
import com.course.ums.db.DBManager;
import com.course.ums.ws.AddEntityRoute;
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
public class AddTeacher extends AddEntityRoute {

    @Override
    public String[] getAuthorizedRoles() {
        return new String[]{AuthManager.ROLE_ADMIN};
    }

    @Override
    public int addEntity(JSONObject request) throws Exception {
        int id = DBManager.addUser(request);

        PreparedStatement ps = DBManager.getConnection().prepareStatement("INSERT INTO teachers(id, level) VALUES(?, ?)");
        ps.setInt(1, id);
        ps.setInt(2, request.getInt("level"));
        ps.execute();

        return id;
    }

}