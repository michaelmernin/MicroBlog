package com.company;

import com.sun.tools.internal.ws.processor.model.Model;
import spark.ModelAndView;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    public static User user;
    public static ArrayList<Messages> messages = new ArrayList();

    public static void main(String[] args) {
	Spark.init();

        Spark.get(
                "/",
                ((request, response) -> {
                    HashMap old = new HashMap();
                    if(user == null) {
                       return new ModelAndView(old, "index.html");
                    } else {
                        old.put("name", user.name);
                        old.put("newMessage", messages);
                        return new ModelAndView(old, "messages.html");
                    }
                }),
                new MustacheTemplateEngine()
        );

        Spark.post(
                "/login",
                ((request, response) -> {
                    String name = request.queryParams("loginName");
                     user = new User(name);
                     //messages = new
                    response.redirect("/");
                    return"";
                })
        );


        Spark.post(
                "/messages",
                ((request, response) -> {
                    String mess = request.queryParams("messages");
                    Messages newMess = new Messages(mess);
                    messages.add(newMess);

                    response.redirect("/");
                    return"";
                })
        );

    }
}
