package tasks;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * Created by Chazz on 24/11/15.
 */
public class RegisterTask extends Task {

    private DatastoreService datastore;

    @Override
    public void init() throws ServletException {
        super.init();

        this.datastore =  DatastoreServiceFactory.getDatastoreService();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        logger.info("Registering user");
        String payload = getBody(request);

        try {

            JSONObject body = new JSONObject(payload);

            Entity user = new Entity("User");

            user.setProperty("username", body.get("username"));
            user.setProperty("password", body.get("password"));
            user.setProperty("email", body.get("email"));
            user.setProperty("signinDate", new Date());

            datastore.put(user);
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {

        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.warning("trying to GET /tasks/register, redirecting to /signin");
        response.sendRedirect("/signin");
    }
}
