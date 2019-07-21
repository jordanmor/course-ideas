package com.treehouse.courses;

import com.treehouse.courses.model.CourseIdeaDAO;
import com.treehouse.courses.model.SimpleCourseIdeaDAO;

import java.util.HashMap;
import java.util.Map;

import static com.treehouse.courses.RenderUtilities.render;
import static spark.Spark.get;
import static spark.Spark.post;

public class Main {
    public static void main(String[] args) {

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

    }
}
