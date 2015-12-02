package servlets;

<<<<<<< HEAD
=======
import models.AbstractUser;
import models.Trainee;

>>>>>>> Initial Servlets added
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
<<<<<<< HEAD
=======
import java.util.Date;
>>>>>>> Initial Servlets added

/**
 * Created by Chazz on 02/12/15.
 */
public class TrainingServlet extends Servlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
<<<<<<< HEAD

=======
        logger.info("[POST] TrainingServlet");
>>>>>>> Initial Servlets added
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("GET TrainingServlet");
<<<<<<< HEAD

=======
        AbstractUser user = Trainee.create("john", "doe", "john.doe@mail.com", new Date());
        logger.info(user.toString());
>>>>>>> Initial Servlets added
    }
}
