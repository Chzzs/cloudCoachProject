package servlets;


import com.google.appengine.api.datastore.EntityNotFoundException;
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
import java.util.List;

/**
 * Created by Chazz on 02/12/15.
 */
public class TrainingServlet extends Servlet {
    private DAOController controller;
    @Override
    public void init() throws ServletException {
        super.init();
        this.controller = new DAOController();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("[POST] TrainingServlet");
        PrintWriter out = response.getWriter();
        try{
            JSONObject json = new JSONObject(getBody(request));
            this.controller.setTraining(new Training(json));
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (JSONException e ){
            out.print(e.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

        } finally {
            out.flush();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.info("[GET] TrainingServlet");
        String id = request.getParameter("id");

        if(id != null) {
            doGetWithId(request,response, Long.parseLong(id));
        } else {
            doGetWithoutId(request,response);
        }
    }

    private void doGetWithId(HttpServletRequest request, HttpServletResponse response, long id)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
            Training training =  this.controller.getTrainingById(id);
            logger.info(training.toString());
            out.write(training.toJSON().toString(2));
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (JSONException | EntityNotFoundException e ){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            logger.warning(e.getMessage());
        } finally {
            out.flush();
        }
    }

    private void doGetWithoutId(HttpServletRequest request, HttpServletResponse response) {
        List<Training>  trainings = this.controller.getTrainings();
        for(Training training : trainings) {
            logger.info(training.toString());
        }
    }
}
