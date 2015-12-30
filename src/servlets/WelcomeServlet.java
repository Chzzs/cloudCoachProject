package servlets;

import controllers.DAOController;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Chazz on 20/12/15.
 */
public class WelcomeServlet extends Servlet {

    @Override
    public void init() throws ServletException {
        super.init();
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
            JSONObject object = new JSONObject(getBody(request));
            this.controller.setWelcomeMessage(object.getString("message"));

            response.setStatus(HttpServletResponse.SC_OK);

        } catch(JSONException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

        } finally {
            out.flush();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject object = new JSONObject();
        PrintWriter out = response.getWriter();
        //TODO store and retrieve from MemoryCache
        try {
            object.put("message", this.controller.getWelcomeMessage());
            out.write(object.toString());
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (JSONException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } finally {
            out.flush();
            response.setContentType(CONTENT_TYPE);
        }
    }
}
