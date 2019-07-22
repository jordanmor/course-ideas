package com.treehouse.courses;

import com.treehouse.courses.model.CourseIdea;
import com.treehouse.courses.model.CourseIdeaDAO;
import com.treehouse.courses.model.SimpleCourseIdeaDAO;

import java.util.HashMap;
import java.util.Map;

import static com.treehouse.courses.RenderUtilities.render;
import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {
        staticFiles.location("/public"); // Static files

        CourseIdeaDAO dao = new SimpleCourseIdeaDAO();

        get("/", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("username", req.cookie("username"));
            return render(model, "index");
        });

        post("/", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            String username = req.queryParams("username");
            res.cookie("username", username);
            model.put("username", username);
            return render(model, "index");
        });

        get("/ideas", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("ideas", dao.findAll());
            return render(model, "ideas");
        });

        post("/ideas", (req, res) -> {
            String title = req.queryParams("title");
            // TODO:csd - This username is tied to the cookie implementation
            CourseIdea courseIdea = new CourseIdea(title, req.cookie("username"));
            dao.add(courseIdea);
            res.redirect("/ideas");
            return null;
        });

    }
}
