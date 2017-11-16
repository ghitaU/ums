package com.course.ums.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by vh on 11/2/17.
 */
public class DBManager {

    private static final String DB_URL = "jdbc:mysql://root:@localhost/ums?useLegacyDatetimeCode=false&serverTimezone=UTC";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }
}
