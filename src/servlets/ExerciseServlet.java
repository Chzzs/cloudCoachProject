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

    @Override
    public void init() throws ServletException {
        super.init();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("POST ExerciseServlet");
        PrintWriter out = response.getWriter();
        int status = 400;
        String answer = "";
        try {
            JSONObject json = new JSONObject(getBody(request));
            this.controller.setExercise(new Exercise(json), json.getLong("trainingId"));
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
        Long id = Long.parseLong(request.getParameter("id"));
        PrintWriter out = response.getWriter();
        try {
            if (id != null) {
                Exercise exercise = this.controller.getExerciseById(id);
                JSONObject json = exercise.toJSON();
                out.write(json.toString());
            } else {
                List<Exercise> exercises = this.controller.getExercises();
                for (Exercise exercise : exercises) {
                    logger.info(exercise.toString());
                }
            }
        } catch (JSONException e ) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        } finally {
            out.flush();
        }

    }


}
