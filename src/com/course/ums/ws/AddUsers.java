package com.course.ums.ws;

import com.course.ums.db.DBManager;
import org.json.JSONObject;
import spark.Request;
import spark.Response;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AddUsers extends MyRoute{

    @Override
    public Object myHandle(Request request, Response response) throws Exception {
        JSONObject json = new JSONObject(request.body());
        JSONObject result = new JSONObject();
        JSONObject id = new JSONObject();

        PreparedStatement preparedStatement = DBManager.getConnection().prepareStatement("INSERT INTO users(first_name, last_name, email, password) VALUES(?, ?, ?, ?)",PreparedStatement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, json.getString("first_name"));
        preparedStatement.setString(2, json.getString("last_name"));
        preparedStatement.setString(3, json.getString("email"));
        preparedStatement.setString(4, json.getString("password"));
        preparedStatement.execute();

        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        resultSet.next();
        id.put("id", resultSet.getInt(1));

        if(json.getInt("token")==1) {
            //this is a administrator
            preparedStatement = DBManager.getConnection().prepareStatement("INSERT INTO administrators(active, id) VALUES(?, ?)");
            preparedStatement.setString(1, String.valueOf(json.getInt("active")));
            preparedStatement.setString(2, String.valueOf(id.getInt("id")));
            preparedStatement.execute();

            result.put("administrator id", id);
        }else
            if(json.getInt("token")==2) {
            //this is a teachers
            preparedStatement = DBManager.getConnection().prepareStatement("INSERT INTO teachers(level, id) VALUES(?, ?)");
            preparedStatement.setString(1, json.getString("level"));
            preparedStatement.setString(2, String.valueOf(id.getInt("id")));
            preparedStatement.execute();

            result.put("teacher id", id);
        }else
            if(json.getInt("token")==3) {
            //this is a student
                preparedStatement = DBManager.getConnection().prepareStatement("INSERT INTO students(gender, birth_date, id) VALUES(?, ?)");
                preparedStatement.setString(1, json.getString("gender"));
                preparedStatement.setString(1, json.getString("birth_date"));
                preparedStatement.setString(2, String.valueOf(id.getInt("id")));
                preparedStatement.execute();

                result.put("student id", id);
        }else
            result.put("rezult","Wrong fields! Please try again!");
        return result;
    }
}
