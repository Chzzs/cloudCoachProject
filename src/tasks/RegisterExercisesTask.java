package tasks;

import models.Exercise;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Chazz on 09/01/16.
 */
public class RegisterExercisesTask extends Task {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {

            long trainingId = Long.parseLong(request.getParameter("trainingId"));
            JSONObject json = new JSONObject(request.getParameter("exercise"));
            json.put(Exercise.TRAINING_ID, trainingId);
            Exercise exercise = new Exercise(json);
            this.controller.setExercise(exercise, trainingId);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
