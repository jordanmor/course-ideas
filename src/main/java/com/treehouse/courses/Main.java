package com.treehouse.courses;

import com.treehouse.courses.model.CourseIdea;
import com.treehouse.courses.model.CourseIdeaDAO;
import com.treehouse.courses.model.SimpleCourseIdeaDAO;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.treehouse.courses.RenderUtilities.render;
import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {
        staticFiles.location("/public"); // Static files

        CourseIdeaDAO dao = new SimpleCourseIdeaDAO();

        before((req, res) -> {
            if(req.cookie("username") !=  null) {
                req.attribute("username", req.cookie("username"));
            }
        });

        before("/ideas" ,(req, res) -> {
            if(req.attribute("username") == null) {
                res.redirect("/");
                halt();
            }
        });

        get("/", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("username", req.attribute("username"));
            return render(model, "index");
        });

        post("/sign-in", (req, res) -> {
            String username = req.queryParams("username");
            res.cookie("username", username);
            res.redirect("/");
            return null;
        });

        get("/ideas", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("ideas", dao.findAll());
            return render(model, "ideas");
        });

        post("/ideas", (req, res) -> {
            String title = req.queryParams("title");
            CourseIdea courseIdea = new CourseIdea(title, req.attribute("username"));
            dao.add(courseIdea);
            res.redirect("/ideas");
            return null;
        });

        get("/ideas/:slug", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("idea", dao.findBySlug(req.params("slug")));
            return render(model, "details");
        });

        post("/ideas/:slug/vote", (req, res) -> {
            CourseIdea idea = dao.findBySlug(req.params("slug"));
            idea.addVoter(req.attribute("username"));
            res.redirect("/ideas");
            return null;
        });

    }
}
