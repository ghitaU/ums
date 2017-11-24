package com.course.ums.ws.user;

import com.course.ums.auth.AuthManager;
import com.course.ums.ws.ListEntityRoute;

/**
 * Created by vh on 11/16/17.
 */
public class ListTeachers extends ListEntityRoute {

    @Override
    public String[] getAuthorizedRoles() {
        return new String[] {AuthManager.ROLE_ADMIN, AuthManager.ROLE_STUDENT, AuthManager.ROLE_TEACHER};
    }

    @Override
    public String getTableName() {
        return "teachers INNER JOIN users ON teachers.id = users.id";
    }

    @Override
    public String getIDColumnName() {
        return "users.id";
    }
}
