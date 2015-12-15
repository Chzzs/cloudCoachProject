package servlets;

import controllers.DAOController;
import models.Exercise;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Chazz on 02/12/15.
 */
public class ExerciseServlet extends Servlet {

    private DAOController controller;

    @Override
    public void init() throws ServletException {
        super.init();
        //TODO maybe a singleton ?
        this.controller = new DAOController();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("POST ExerciseServlet");
        PrintWriter out = response.getWriter();
        int status = 400;
        String answer = "";
        try {
            JSONObject body = new JSONObject(getBody(request));
            Exercise exercise = createExercise(body);
            this.controller.setExercice(exercise);
            answer = "request successfully posted";
            status = 200;
        } catch (JSONException e) {
            logger.warning("exception");
            answer = "bad request";
            status = 400;
        } finally {
            response.setStatus(status);
            out.print(answer);
            out.flush();
        }

    }

    private Exercise createExercise(JSONObject json) throws JSONException {
        String title = (String) json.get("title");
        String description = (String) json.get("description");
        String domain = (String) json.get("domain");
        int duration = (int) json.get("duration");

        return new Exercise(title, description, duration);
    }


    /*
     * Practiful rest
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("GET ExerciseServlet");
        String key = request.getParameter("key");

        if(key != null) {
            Exercise exercise = this.controller.getExerciseByKey(key);
            logger.info(exercise.toString());
        } else {
            this.controller.getExercises();
        }

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("PUT ExerciseServlet");
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("DELETE ExerciseServlet");
    }
}
