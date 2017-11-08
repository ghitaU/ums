package com.course.ums.ws;

import spark.Request;
import spark.Response;
import spark.Route;

/**
 * Created by vh on 11/2/17.
 */
public abstract class MyRoute implements Route {

    @Override
    public Object handle(Request request, Response response) {
        Object result;

        try {
            result = myHandle(request, response);
        } catch (Exception e) {
            result = e;
        }

        return result;
    }

    public abstract Object myHandle(Request request, Response response) throws Exception;

}