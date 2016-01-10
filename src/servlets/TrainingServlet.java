package servlets;


import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.labs.repackaged.org.json.JSONArray;
import controllers.DAOController;
import models.Exercise;
import models.Training;
import models.User;
import org.json.JSONException;
import org.json.JSONObject;
import tasks.Task;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by Chazz on 02/12/15.
 */
public class TrainingServlet extends Servlet {

    private final static String GOOGLE_ID="googleId";

    @Override
    public void init() throws ServletException {
        super.init();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("[POST] TrainingServlet");

        /* Retrieves the queue */
        Queue queue = QueueFactory.getDefaultQueue();


        PrintWriter out = response.getWriter();
        try{
            String body = getBody(request);
            logger.info(body);
            JSONObject json = new JSONObject(body);
            TaskOptions task = TaskOptions.Builder
                    .withUrl("/tasks/register/trainings")
                    .param("training", json.getJSONObject("training").toString())
                    .param("googleId", (String)json.get("googleId"));

            queue.add(task);

            response.setStatus(HttpServletResponse.SC_OK);
        } catch (  JSONException e ){
            out.print(e.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } finally {
           out.flush();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.info("[GET] TrainingServlet");

        if(request.getParameter(Training.ID)!= null) {
            doGetWithId(request, response);

        } else if(request.getParameter(GOOGLE_ID) != null) {
            doGetWithGoogleId(request, response);
        } else {
            doGetWithoutGoogleId(request, response);
        }

    }

     private void doGetWithId(HttpServletRequest request, HttpServletResponse response) throws IOException {
         PrintWriter out = response.getWriter();
         long id = Long.parseLong(request.getParameter(Training.ID));
         try {
             Training training = this.controller.getTrainingById(id);
             List<Exercise> exercises = this.controller.getExercisesByTrainingId(id);
             training.setExercises(exercises);
             JSONObject json = training.toJSON();
             out.write(json.toString());
         } catch (JSONException e ){
             response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
             logger.warning(e.getMessage());
         } finally {
             out.flush();
         }
     }


    private void doGetWithoutGoogleId(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();

        try {
            List<Training> trainings =  this.controller.getTrainings();
            JSONArray array = new JSONArray();
            for(Training training: trainings)
                array.put(training.toJSON());
            out.write(array.toString());
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (JSONException e ){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            logger.warning(e.getMessage());
        } finally {
            out.flush();
        }
    }

    private void doGetWithGoogleId(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        long googleId = Long.parseLong(request.getParameter(GOOGLE_ID));

        try {
            List<Training> trainings =  this.controller.getTrainingsByGoogleId(googleId);
            JSONArray array = new JSONArray();
            for(Training training: trainings)
                array.put(training.toJSON());

            out.write(array.toString());
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (JSONException | EntityNotFoundException e ){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            logger.warning(e.getMessage());
        } finally {
            out.flush();
        }
    }
}
