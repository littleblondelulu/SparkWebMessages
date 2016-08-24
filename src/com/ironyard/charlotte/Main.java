package com.ironyard.charlotte;

import spark.ModelAndView;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    static User user;
    static ArrayList<String> userMessages = new ArrayList<>();
    static  HashMap m = new HashMap();

    public static void main(String[] args) {
        Spark.init();

        Spark.get(
                "/",
                ((request, response) -> {
                    if (user == null) {
                        return new ModelAndView(m, "login.html");
                    } else {
                        m.put("name", user.name);
                        m.put("password", user.password);
                        return new ModelAndView(m, "messages.html");
                    }
                }),
                new MustacheTemplateEngine()
        );

        Spark.post(
                "/login",
                ((request, response) -> {
                    String name = request.queryParams("loginName");
                    user = new User(name);
                    response.redirect("/");
                    return "";
                })
        );

        Spark.post(
                "/create-messages",
                ((request, response) -> {
                    userMessages.add(request.queryParams("messageLog"));
                    m.put("messageList", userMessages);
                    //FOR LOOP FOR THE userMessages ArrayList - does this work?
//                    for (String s : userMessages) {
//                        int i = userMessages.indexOf(s);
//                        System.out.println(i + ". " + s);
//                    }
                    response.redirect("/");
                    return "";
                })
        );

    }
}