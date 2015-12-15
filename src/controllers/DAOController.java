package controllers;

import com.google.appengine.api.datastore.*;
import models.Exercise;
import models.Training;
import models.User;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by Chazz on 02/12/15.
 */
public class DAOController {

    DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService();


    public void setUser(User user) {
        Entity entity = user.toEntity();
        dataStore.put(entity);
    }

    public User getUserByGoogleId(long googleId) throws  EntityNotFoundException, JSONException{

        /* Prepares query and execute it */
        Query query = new Query(User.class.getName());
        query.setFilter(new Query.FilterPredicate(User.GOOGLE_ID, Query.FilterOperator.EQUAL, googleId));
        PreparedQuery preparedQuery = dataStore.prepare(query);

        /* Parses result */
        Entity entity = preparedQuery.asSingleEntity();
        long id = entity.getKey().getId();
        User user = new User(entity);
        /* set the id of the user */

        user.setId(id);

        return user;
    }

    public User getUserById(long id) throws  EntityNotFoundException, JSONException{
        Key key = KeyFactory.createKey(User.class.getName(), id);
        Entity entity = dataStore.get(key);

        /* Creates model */
        User user  = new User(entity);

        return user;
    }


    /**
     * stores an exercice as child of the training corresponding to trainingID.
     * @param exercise
     */
    public void setExercise(Exercise exercise, long trainingId) {
        Entity entity = exercise.toEntity(trainingId);
        dataStore.put(entity);
    }

    public void setTrainingWithGoogleId(Training training, long googleId) throws EntityNotFoundException, JSONException {
        User user = getUserByGoogleId(googleId);
        long id = user.getId();

        /* set the training with the id of the user */
        Entity entity = training.toEntity(id);
        dataStore.put(entity);
    }


    public List<Training> getTrainings(){
        List<Training> trainings = new ArrayList<>();

        Query query = new Query(Training.class.getName());
        PreparedQuery preparedQuery = dataStore.prepare(query);

        for(Entity entity : preparedQuery.asIterable())
            trainings.add(new Training(entity));


        return trainings;
    }

    public List<Training> getTrainingsByGoogleId(long googleId) throws EntityNotFoundException, JSONException {
        User user = getUserByGoogleId(googleId);
        long userId = user.getId();

        return getTrainingsByUserId(userId);
    }

    private List<Training> getTrainingsByUserId(long userId) throws EntityNotFoundException, JSONException {
        List<Training> trainings = new ArrayList<>();
          /* Creates key with given id */
        Key key = KeyFactory.createKey(Training.class.getName(), userId);

        /* Prepares query and execute it */
        Query query = new Query(Training.class.getName());
        query.setAncestor(key);
        PreparedQuery preparedQuery = dataStore.prepare(query);

        /* Parses result */
        for(Entity entity : preparedQuery.asIterable())
            trainings.add(new Training(entity));

        return trainings;
    }

    private Training getTrainingById(long id) throws EntityNotFoundException {

        /* Creates key with given id */
        Key key = KeyFactory.createKey(Training.class.getName(), id);
        Entity entity = dataStore.get(key);

        /* Creates model */
        Training training = new Training(entity);

        /* Fetches children */
        training.setExercises(getExercisesByTrainingId(id));

        return training;
    }


    public List<Exercise> getExercisesByTrainingId(long id){
        List<Exercise> exercises = new ArrayList<>();

        /* Creates key with given id */
        Key key = KeyFactory.createKey(Training.class.getName(), id);

        /* Prepares query and execute it */
        Query query = new Query(Exercise.class.getName());
        query.setAncestor(key);
        PreparedQuery preparedQuery = dataStore.prepare(query);

        /* Parses result */
        for(Entity entity : preparedQuery.asIterable())
            exercises.add(new Exercise(entity));

        return exercises;
    }

    public List<Exercise> getExercises() {
        List<Exercise> exercises = new ArrayList<>();

        Query query = new Query(Exercise.class.getName());
        PreparedQuery preparedQuery = dataStore.prepare(query);

        for(Entity entity : preparedQuery.asIterable()) {
            exercises.add(new Exercise(entity));
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
