package com.course.ums.ws.groupTeacher;

import com.course.ums.auth.AuthManager;
import com.course.ums.db.DBManager;
import com.course.ums.ws.AddEntityRoute;
import com.course.ums.ws.JSONRoute;
import org.json.JSONObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import static com.course.ums.auth.AuthManager.result;

public class AddGroupTeacher extends AddEntityRoute {
    @Override
    public String[] getAuthorizedRoles() {
        return new String[]{AuthManager.ROLE_ADMIN};
    }


    @Override
    public int addEntity(JSONObject request) throws Exception {
        int id = DBManager.addGroupTeacher(request);

        return id;
    }
}