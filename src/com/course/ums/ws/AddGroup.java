package com.course.ums.ws;

import com.course.ums.db.DBManager;
import org.json.JSONObject;
import spark.Request;
import spark.Response;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class AddGroup extends MyRoute {
    @Override
    public Object myHandle(Request request, Response response) throws Exception {
        JSONObject json = new JSONObject(request.body());
        JSONObject result = new JSONObject();
        JSONObject id = new JSONObject();

        int token = json.getInt("token");
        Statement statement = DBManager.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM administrators WHERE id = '"+token+"';");
        if(resultSet.first()) {
            PreparedStatement preparedStatement = DBManager.getConnection().prepareStatement("INSERT INTO groups(semester_id) VALUES(?)", PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, String.valueOf(json.getInt("semester_id")));
;
            preparedStatement.execute();
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                id.put("id", resultSet.getInt(1));
                result.put("result", id.getInt("id"));
            } else {
                result.put("result", "Unsuccessful insertion! Try again!");
            }
        }else {
            result.put("rezult","You enter a wrong token!!");
        }
        return result;
    }
}
