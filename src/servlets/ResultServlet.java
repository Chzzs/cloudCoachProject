package servlets;

import com.google.gson.Gson;
import models.Result;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by Chazz on 10/01/16.
 */
public class ResultServlet extends Servlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {

            JSONObject json = new JSONObject(getBody(request));
            long googleId = json.getLong(Result.GOOGLE_ID);
            long exerciseId = json.getLong(Result.EXERCISE_ID);
            this.controller.setResult(googleId, exerciseId);
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            System.out.println("ok");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long googleId = Long.parseLong(request.getParameter(Result.GOOGLE_ID));
        PrintWriter out = response.getWriter();

        if(googleId != null) {
           List<Result> results =  this.controller.getResultsByGoogleId(googleId);
            logger.info(""+results.size());
            //Exercises were parsed as string, so moved to google.Gson.
            Gson gson = new Gson();

            out.write(gson.toJson(results));
        }
            out.flush();


    }
}
