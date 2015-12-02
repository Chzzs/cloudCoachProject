package servlets;

import com.google.appengine.api.datastore.*;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.jsr107cache.GCacheFactory;
import net.sf.jsr107cache.Cache;
import net.sf.jsr107cache.CacheException;
import net.sf.jsr107cache.CacheFactory;
import net.sf.jsr107cache.CacheManager;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Chazz on 24/11/15.
 */
public class WelcomeServlet extends Servlet {


    private final static String MESSAGE_KEY = "message";
    private final static String MESSAGE_TAG = "message";

    private final static String MESSAGE_ENTITY = "Message";
    private Cache cache = null;

    @Override
    public void init() throws ServletException {
        super.init();

        try {
            Map properties = new HashMap();
            properties.put(GCacheFactory.EXPIRATION_DELTA, 3600);
            properties.put(MemcacheService.SetPolicy.ADD_ONLY_IF_NOT_PRESENT, true);
            CacheFactory factory = CacheManager.getInstance().getCacheFactory();
            this.cache = factory.createCache(properties);
        } catch (CacheException e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        PrintWriter out = response.getWriter();
        JSONObject answer = new JSONObject();

        try {
            JSONObject body = new JSONObject(getBody(request));

            Entity message = new Entity(MESSAGE_ENTITY);
            message.setProperty("content", body.get("content"));
            message.setProperty("created", new Date());


            datastore.put(message);

            answer.put("success", true);
            answer.put("message", "Message successfully posted");


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
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        PrintWriter out = response.getWriter();
        JSONObject answer = new JSONObject();

        try {
            String message = (String) this.cache.get(MESSAGE_KEY);
            if(message!= null) {
                answer.put(MESSAGE_TAG, message);
            } else {
                Query query = new Query("Message");
                query.addSort("created", Query.SortDirection.DESCENDING);
                PreparedQuery preparedQuery = datastore.prepare(query);
                for(Entity result: preparedQuery.asIterable()) {
                    message = (String) result.getProperty("content");
                    answer.put(MESSAGE_TAG, message);

                    System.out.println(message);

                    //TODO tidy this
                    break;
                }
                cache.put(MESSAGE_KEY, message);
            }
            out.println(answer.toString(0));
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            out.flush();
        }

    }
}
