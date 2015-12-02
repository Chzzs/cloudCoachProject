package servlets;


import controllers.DAOController;
import models.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * Created by Chazz on 02/12/15.
 */
public class TrainingServlet extends Servlet {
    private DAOController controller;
    @Override
    public void init() throws ServletException {
        super.init();
        this.controller = new DAOController();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("[POST] TrainingServlet");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("[GET] TrainingServlet");
        User trainee = new User("john", "doe", "john.doe@mail.com", "pwd", "trainee", new Date());
        this.controller.setUser(trainee);
        trainee = this.controller.getUserByName("john", "doe", "pwd");
        logger.info(trainee.toString());
    }
}
