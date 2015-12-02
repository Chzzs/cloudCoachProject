package servlets;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

/**
 * Created by Chazz on 24/11/15.
 */
public class SigninServlet extends Servlet {


    private Queue queue;

    @Override
    public void init() throws ServletException {
        super.init();
        this.queue = QueueFactory.getDefaultQueue();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        PrintWriter out = response.getWriter();
        JSONObject answer = new JSONObject();

        try {
            String payload = getBody(request);

            TaskOptions task = TaskOptions.Builder.withUrl("/tasks/register").payload(payload);
            queue.add(task);

            answer.put("success", true);
            answer.put("message", "User successfully registered");

            response.setStatus(200);

            out.println(answer.toString(0));

        } catch (JSONException e) {
            response.setStatus(400);
            out.println(e.getMessage());
        } finally {
            out.flush();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
