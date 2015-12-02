package servlets;


import models.AbstractUser;
import models.Trainee;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * Created by Chazz on 02/12/15.
 */
public class TrainingServlet extends Servlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        logger.info("[POST] TrainingServlet");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("GET TrainingServlet");


        AbstractUser user = Trainee.create("john", "doe", "john.doe@mail.com", new Date());
        logger.info(user.toString());
    }
}
