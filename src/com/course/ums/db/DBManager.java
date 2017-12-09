package com.course.ums.db;

import com.sun.org.apache.regexp.internal.RE;
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

    public static int addCourse(JSONObject request) throws SQLException {
        PreparedStatement ps = DBManager.getConnection().prepareStatement("INSERT INTO courses(name) VALUES(?)", Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, request.getString("name"));
        ps.execute();
        ResultSet rs = ps.getGeneratedKeys();
        rs.next();

        return rs.getInt(1);
    }

    public static int addGroup(JSONObject request) throws SQLException {
        PreparedStatement ps = DBManager.getConnection().prepareStatement("INSERT INTO groups(semesters_id, name) VALUES(?, ?)", Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, request.getInt("semesters_id"));
        ps.setString(2, request.getString("name"));
        ps.execute();
        ResultSet rs = ps.getGeneratedKeys();
        rs.next();

        return rs.getInt(1);
    }

    public static int addSemester(JSONObject request) throws SQLException {
        PreparedStatement ps = DBManager.getConnection().prepareStatement("INSERT INTO semesters(year, `index`) VALUES(?, ?)", Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, request.getInt("year"));
        ps.setString(2, request.getString("index"));
        ps.execute();
        ResultSet rs = ps.getGeneratedKeys();
        rs.next();

        return rs.getInt(1);
    }

    public static int addExam(JSONObject request)throws SQLException{
        PreparedStatement ps = DBManager.getConnection().prepareStatement("INSERT INTO exams(date,semesters_id,courses_id) VALUES(?,?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
        ps.setString(1, String.valueOf(request.getString("date")));
        ps.setString(2, String.valueOf(request.getInt("semester_id")));
        ps.setString(3, String.valueOf(request.getInt("teachers_courses_id")));
        ps.setString(4, String.valueOf(request.getInt("groups_id")));
        ps.execute();
        ResultSet rs = ps.getGeneratedKeys();
        rs.next();

        return rs.getInt(1);
    }

    public static int addGroupStudent(JSONObject request)throws SQLException{
        PreparedStatement ps = DBManager.getConnection().prepareStatement("INSERT INTO group_students(groups_id,students_id,semesters_id) VALUES(?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
        ps.setString(1, String.valueOf(request.getInt("groups_id")));
        ps.setString(2, String.valueOf(request.getInt("students_id")));
        ps.setString(2, String.valueOf(request.getInt("semesters_id")));
        ps.execute();
        ResultSet rs = ps.getGeneratedKeys();
        rs.next();

        return rs.getInt(1);
    }

    public static int removeGroupStudent(JSONObject request)throws SQLException{
        PreparedStatement ps = DBManager.getConnection().prepareStatement("DELETE FROM group_students where groups_id = (?) end students_id = (?) and semesters_id = (?) VALUES(?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
        ps.setString(1, String.valueOf(request.getInt("groups_id")));
        ps.setString(2, String.valueOf(request.getInt("students_id")));
        ps.setString(2, String.valueOf(request.getInt("semesters_id")));
        ps.execute();
        ResultSet rs = ps.getGeneratedKeys();
        rs.next();

        return rs.getInt(1);
    }

    public static int addGroupTeacher(JSONObject request) throws SQLException{
        PreparedStatement ps = DBManager.getConnection().prepareStatement("INSERT INTO group_teacher_courses(groups_id,teachers_courses_id) VALUES(?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
        ps.setString(1, String.valueOf(request.getInt("groups_id")));
        ps.setString(2, String.valueOf(request.getInt("teachers_courses_id")));
        ps.execute();
        ResultSet rs = ps.getGeneratedKeys();

        rs.next();
        return rs.getInt(1);

    }

    public static int removeGroupTeacher(JSONObject request) throws SQLException{
        PreparedStatement ps = DBManager.getConnection().prepareStatement("DELETE FROM group_teacher_courses WHERE groups_id = (?) and teachers_courses_id = (?) ", PreparedStatement.RETURN_GENERATED_KEYS);
        ps.setString(1, String.valueOf(request.getInt("groups_id")));
        ps.setString(2, String.valueOf(request.getInt("teachers_courses_id")));
        ps.execute();
        ResultSet rs = ps.getGeneratedKeys();

        rs.next();
        return rs.getInt(1);

    }

    public static int addTeacherCourse(JSONObject request)throws SQLException{
        PreparedStatement ps = DBManager.getConnection().prepareStatement("INSERT INTO teachers_courses(teachers_id,courses_id) VALUES(?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
        ps.setString(1, String.valueOf(request.getInt("teachers_id")));
        ps.setString(2, String.valueOf(request.getInt("courses_id")));
        ps.execute();
        ResultSet rs = ps.getGeneratedKeys();
        rs.next();
        return rs.getInt(1);
    }

    public static int removeTeacherCourse(JSONObject request)throws SQLException{
        PreparedStatement ps = DBManager.getConnection().prepareStatement("DELETE FROM teachers_courses where teachers_id = (?) and courses_id = (?)", PreparedStatement.RETURN_GENERATED_KEYS);
        ps.setString(1, String.valueOf(request.getInt("teachers_id")));
        ps.setString(2, String.valueOf(request.getInt("courses_id")));

        ps.execute();
        ResultSet rs = ps.getGeneratedKeys();
        rs.next();
        return rs.getInt(1);

    }


}