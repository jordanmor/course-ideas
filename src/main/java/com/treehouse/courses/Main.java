package com.treehouse.courses;

import spark.ModelAndView;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static com.treehouse.courses.RenderUtilities.render;
import static spark.Spark.get;
import static spark.Spark.post;

public class Main {
    public static void main(String[] args) {

        get("/", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            return render(model, "index");
        });

        post("/sign-in", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("username", req.queryParams("username"));
            return render(model, "sign-in");
        });
    }
}
