package tasks;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
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

/**
 * Created by Chazz on 09/01/16.
 */
public class RegisterTrainingsTask extends Task {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /* Retrieves the queue */
        Queue queue = QueueFactory.getDefaultQueue();

        try {
            JSONObject json = new JSONObject(request.getParameter("training"));
            String string = request.getParameter("googleId");
            logger.info(string);
            long googleId = Long.parseLong(string);

            Training training = new Training(json, googleId);
            long trainingId = this.controller.setTraining(training, googleId);

            JSONArray array = json.getJSONArray("exercises");

            for(int i = 0; i < array.length(); i++){
                TaskOptions task = TaskOptions.Builder
                        .withUrl("/tasks/register/exercises")
                        .param("exercise", array.get(i).toString())
                        .param("trainingId", ""+trainingId);

                queue.add(task);
                logger.info("id : "+trainingId);
                logger.info(array.get(i).toString());
            }

        } catch (JSONException | EntityNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}