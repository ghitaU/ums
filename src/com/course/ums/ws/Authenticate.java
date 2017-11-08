package com.course.ums.ws;

import com.course.ums.db.DBManager;
import org.json.JSONArray;
import org.json.JSONObject;
import spark.Request;
import spark.Response;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Authenticate extends MyRoute {
    @Override
    public Object myHandle(Request request, Response response) throws Exception {
        JSONObject json = new JSONObject(request.body());
        JSONObject result = new JSONObject();
        JSONArray results = new JSONArray();

        verifyCredentials(json, result, results);

        return results;
    }

    private void verifyCredentials(JSONObject json, JSONObject result, JSONArray results) throws SQLException {
        PreparedStatement preparedStatement = DBManager.getConnection().prepareStatement("SELECT id FROM users WHERE email = ? AND password = ?");
        preparedStatement.setString(1, json.getString("email"));
        preparedStatement.setString(2, json.getString("password"));
        preparedStatement.execute();
        result.put("result", preparedStatement.execute());

        if(result.equals("false")){
            result.put("message","user don't exists");
            results.put(result);
        }else {
            result = new JSONObject();
            String email = json.getString("email");
            String password = json.getString("password");
            //System.out.println("SELECT * FROM users WHERE email = '" + email + "' AND password = '" + password + "';");
            Statement statement = DBManager.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users WHERE email = '" + email + "' AND password = '" + password + "';");

            while (resultSet.next()) {
                result.put("id", resultSet.getInt("id"));
                results.put(result);
            }
            String table = "teachers";
            checkRole(table,result.getInt("id"),result,results);
            table = "students";
            checkRole(table,result.getInt("id"),result,results);
            table = "administrators";
            checkRole(table,result.getInt("id"),result,results);

        }
    }

    private void checkRole(String table, int id,JSONObject result,JSONArray results) throws SQLException {
        Statement statement = DBManager.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM "+table+" WHERE id = "+id+" ");
        //System.out.println("SELECT * FROM "+table+" WHERE id = "+id+" ");

        result = new JSONObject();

        if(resultSet.first()){
            result.put("role",table);
            results.put(result);
        }else {
            System.out.println("Try to check on another table!");
        }
    }

}
