package com.course.ums.ws;


import com.course.ums.db.DBManager;
import com.course.ums.ws.JSONRoute;
import org.json.JSONObject;

import java.util.Set;

/**
 * Created by vh on 11/16/17.
 */
public abstract class AddEntityRoute extends JSONRoute {

    public abstract String[] getAuthorizedRoles();

    public abstract int addEntity(JSONObject request) throws Exception ;

    @Override
    public JSONObject handleJSONRequest(JSONObject request) throws Exception {
        String token = request.getString("token");

        String[] authorizedRoles = getAuthorizedRoles();
        boolean authorized = authorizedRoles.length == 0;
        int i = 0;
        while (!authorized && i < authorizedRoles.length) {
            authorized = DBManager.validateToken(token, authorizedRoles[i]);
            i++;
        }

        if (!authorized) {
            throw new RuntimeException("Unauthorized!");
        }

        int id = addEntity(request);

        JSONObject result = new JSONObject();
        result.put("id", id);

        return result;
    }
}