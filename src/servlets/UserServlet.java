package servlets;

import com.google.appengine.api.datastore.EntityNotFoundException;
import controllers.DAOController;
import models.Exercise;
import models.Training;
import models.User;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by Chazz on 15/12/15.
 */
public class UserServlet extends Servlet {

    private DAOController controller;

    @Override
    public void init() throws ServletException {
        super.init();
        //TODO maybe a singleton ?
        this.controller = new DAOController();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("POST UserServlet");
        PrintWriter out = response.getWriter();
        try{
            JSONObject json = new JSONObject(getBody(request));
            this.controller.setUser(new User(json));
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (JSONException e ){
            out.print(e.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } finally {
            out.flush();
        }
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("GET UserServlet");
        String id = request.getParameter("id");
        if(id != null) {
            try {
                User user = this.controller.getUserByGoogleId(Long.parseLong(id));
                logger.info(user.toString());
            } catch (EntityNotFoundException | JSONException e) {
                e.printStackTrace();
            }
        } else {
            // no id
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