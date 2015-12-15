package controllers;

import com.google.appengine.api.datastore.*;
import models.Exercise;
import models.User;

import java.util.ArrayList;
import java.util.List;

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

    public void setExercice(Exercise exercise) {
        Entity entity = exercise.toEntity();
        dataStore.put(entity);
    }

    public Exercise getExerciseByKey(String string) {
        Key key = KeyFactory.createKey("Exercise", string);
        System.out.println(key);
        Filter filter = new FilterPredicate(Entity.KEY_RESERVED_PROPERTY, EQUAL, key);
        Query query = new Query("Exercise").setFilter(filter);
        PreparedQuery preparedQuery = dataStore.prepare(query);
        Entity entity = preparedQuery.asSingleEntity();
        String title = (String) entity.getProperty("title");
        String description = (String) entity.getProperty("description");
        int duration = (int) entity.getProperty("duration");
        return new Exercise(title, description, duration);
    }

    public List<Exercise> getExercises() {
        List<Exercise> exercises = new ArrayList<>();
        Query query = new Query("Exercise");
        PreparedQuery preparedQuery = dataStore.prepare(query);

        for(Entity entity : preparedQuery.asIterable()){
            String title = (String) entity.getProperty("title");
            String description = (String) entity.getProperty("description");
            int duration = (int) entity.getProperty("duration");
            exercises.add(new Exercise(title, description, duration));
        }

        return exercises;
    }

/*
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
*/


}
