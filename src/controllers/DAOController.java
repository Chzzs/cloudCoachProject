package controllers;

import com.google.appengine.api.datastore.*;
import models.User;

import java.util.Date;

import static com.google.appengine.api.datastore.Query.*;
import static com.google.appengine.api.datastore.Query.FilterOperator.*;
/**
 * Created by Chazz on 02/12/15.
 */
public class DAOController {

    DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService();

    public void setUser(User user) {
        Entity entity = user.toEntity();
        dataStore.put(entity);
    }

    public boolean checkUserByEmail(String email, String password) {
        Filter emailFilter = new FilterPredicate("email", EQUAL, email);
        return false;
    }




    public User getUserByName(String firstname, String lastname, String password) {

        Filter firstnameFilter = new FilterPredicate("firstname", EQUAL, firstname);
        Filter lastnameFilter = new FilterPredicate("lastname", EQUAL, lastname);
        Filter passwordFilter = new FilterPredicate("password", EQUAL, password);
        Filter nameFilter =  CompositeFilterOperator.and( firstnameFilter, lastnameFilter, passwordFilter);

        Query query = new Query("User").setFilter(nameFilter);
        PreparedQuery preparedQuery = dataStore.prepare(query);

        Entity entity = preparedQuery.asSingleEntity();

        String email = (String) entity.getProperty("email");
        String role = (String) entity.getProperty("role");
        Date birthdate =(Date) entity.getProperty("birthdate");

        return new User(firstname,lastname,email,password,role,birthdate);
    }



}
