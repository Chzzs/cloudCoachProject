package servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;




import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Chazz on 02/12/15.
 */
public class SignServlet extends Servlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	logger.info("POST SignServlet");
    	//Format the answer
    	
    	//check user
    	
    	//add or not user
    	
    	//send response
    	
    	
		response.setContentType("application/json");
		JSONObject jsonToSend;
		jsonToSend=new JSONObject();
		
		jsonToSend.put("statustx", "kek");
		
		//Send the Json object to the web browser
		response.setStatus(HttpServletResponse.SC_OK);
		PrintWriter out= response.getWriter();
		out.write(jsonToSend.toString());
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
