package servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import com.google.appengine.repackaged.com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.appengine.repackaged.com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.appengine.repackaged.com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.appengine.repackaged.com.google.api.client.http.HttpTransport;
import com.google.appengine.repackaged.com.google.api.client.http.javanet.NetHttpTransport;
import com.google.appengine.repackaged.com.google.api.client.json.jackson.JacksonFactory;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.GeneralSecurityException;
import java.util.Arrays;


/**
 * Created by Chazz on 02/12/15.
 */


public class SignServlet extends Servlet {

	private final static String GOOGLE_CLIENT_ID = "313469799156-sfjdt8eqj20tj116ur441lk7atkou9lo.apps.googleusercontent.com";

	
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	logger.info("POST SignServlet");
       	response.setContentType("application/json");

    	HttpTransport transport = new NetHttpTransport();
    	JacksonFactory jsonFactory = new JacksonFactory();
    	
    	GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
        .setAudience(Arrays.asList(GOOGLE_CLIENT_ID))
        .build();

    
    	String idTokenString=request.getParameter("token");
    	GoogleIdToken idToken = null;

			try {
				idToken = verifier.verify(idTokenString);
			} catch (GeneralSecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
 
	    	if (idToken != null) {
	      	  Payload payload = idToken.getPayload();
	      	    System.out.println("User ID: " + payload.getSubject());
	      	    System.out.println("User email: " + payload.getEmail());
	        	PrintWriter out = response.getWriter();
	        	
	      	  JSONObject jsonToSend;
	  		jsonToSend=new JSONObject();
	  		try {
				jsonToSend.put("email", payload.getEmail());
				jsonToSend.put("tokenId", payload.getSubject());

				out.write(jsonToSend.toString());
				out.flush();
			} catch (JSONException e) {
				e.printStackTrace();
			}
	      	} else {
	      	  System.out.println("Invalid ID token.");
	      	}

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("GET SignServlet");
        

    	
    	
		
		
    }
    
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
    	super.doPut(req, resp);

    }
    
}
