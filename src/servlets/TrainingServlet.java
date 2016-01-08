package servlets;


import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.labs.repackaged.org.json.JSONArray;
import controllers.DAOController;
import models.Training;
import models.User;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

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
        PrintWriter out = response.getWriter();
        try{
            JSONObject json = new JSONObject(getBody(request));
            long googleId = json.getLong("googleId");
            System.out.println(googleId);
            Training training = new Training(json);

            this.controller.setTrainingWithGoogleId(training, googleId);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (EntityNotFoundException | JSONException e ){
            out.print(e.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } finally {
            out.flush();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.info("[GET] TrainingServlet");


        if(request.getParameter(GOOGLE_ID) != null) {
            doGetWithGoogleId(request, response);
        } else {
            doGetWithoutGoogleId(request, response);
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
