package com.course.ums.ws.semester;

import com.course.ums.auth.AuthManager;
import com.course.ums.db.DBManager;
import com.course.ums.ws.AddEntityRoute;
import org.json.JSONObject;

/**
 * Created by vh on 11/16/17.
 */
public class AddSemester extends AddEntityRoute {

    @Override
    public String[] getAuthorizedRoles() {
        return new String[]{AuthManager.ROLE_ADMIN};
    }

    @Override
    public int addEntity(JSONObject request) throws Exception {
        int id = DBManager.addSemester(request);

        return id;
    }
}
