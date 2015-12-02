package servlets;

import com.google.appengine.api.datastore.*;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.google.appengine.api.datastore.Query.FilterOperator.EQUAL;
/**
 * Created by Chazz on 24/11/15.
 */
public class LoginServlet extends Servlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            JSONObject body = new JSONObject(getBody(request));
            DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
            Query.Filter filter = new Query.FilterPredicate("username", EQUAL, body.get("username"));

            Query query = new Query("User");
            query.setFilter(filter);


            PreparedQuery preparedQuery = datastore.prepare(query);

            for(Entity result: preparedQuery.asIterable()) {
                System.out.println((String) result.getProperty("username"));
                System.out.println((String) result.getProperty("password"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }



    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }


}
