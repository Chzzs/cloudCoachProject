package servlets;

import models.Exercise;
import models.Training;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by Chazz on 02/12/15.
 */
public class SearchServlet extends Servlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("GET SearchServlet");
        String query = request.getParameter("query");
        logger.info(query);

        PrintWriter out = response.getWriter();
        List<Exercise> exercises = this.controller.getExercisesWithFilter(query);
        List<Training> trainings = this.controller.getTrainingsWithFilter(query);
        try {
            JSONObject object = new JSONObject();
            JSONArray array = new JSONArray();
            for (Exercise exercise : exercises) {
                array.put(exercise.toJSON());
                logger.info(exercise.toString());
            }
            object.put("exercises", array);
            array= new JSONArray();
            for (Training training : trainings) {
                array.put(training.toJSON());
                logger.info(training.toString());
            }
            object.put("trainings", array);
            response.setContentType("application/json");

            out.write(object.toString());
        } catch ( JSONException e) {
            e.printStackTrace();
        } finally {
            out.flush();
        }
    }
}
