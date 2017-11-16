package com.course.ums.db;

import org.json.JSONObject;

import java.sql.*;

/**
 * Created by vh on 11/2/17.
 */
public class DBManager {

    private static final String DB_URL = "jdbc:mysql://trainer:trainer@localhost/ums?useLegacyDatetimeCode=false";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    public static boolean validateToken(String token, String role) {
        String table = role + "s";

        PreparedStatement ps = null;
        try {
            ps = DBManager.getConnection().prepareStatement("SELECT id FROM " + table + " WHERE id=?");
            ps.setInt(1, Integer.parseInt(token));
            ResultSet rs = ps.executeQuery();

            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static int addUser(JSONObject request) throws SQLException {
        PreparedStatement ps = DBManager.getConnection().prepareStatement("INSERT INTO users(first_name, last_name, email, password) VALUES(?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, request.getString("first_name"));
        ps.setString(2, request.getString("last_name"));
        ps.setString(3, request.getString("email"));
        ps.setString(4, request.getString("password"));
        ps.execute();
        ResultSet rs = ps.getGeneratedKeys();
        rs.next();

        return rs.getInt(1);
    }
}
