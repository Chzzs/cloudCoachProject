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
import java.util.List;

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
            JSONObject json = new JSONObject(getBody(request));
            this.controller.setExercise(new Exercise(json));
            answer = "request successfully posted";
            status = 200;
        } catch (JSONException e) {
            logger.warning("exception");
            logger.warning(e.getMessage());
            answer = "bad request";
            status = 400;
        } finally {
            response.setStatus(status);
            out.print(answer);
            out.flush();
        }

    }

    /*
     * Practiful rest
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("GET ExerciseServlet");
        String key = request.getParameter("key");

        if(key != null) {
            //Exercise exercise = this.controller.getExerciseByKey(key);
            //logger.info(exercise.toString());
        } else {
            List<Exercise> exercises = this.controller.getExercises();
            for(Exercise exercise : exercises) {
                logger.info(exercise.toString());
            }
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
