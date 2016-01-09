package controllers;

import com.google.appengine.api.datastore.*;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.jsr107cache.GCacheFactory;
import models.Exercise;
import models.Training;
import models.User;
import org.json.JSONException;

import javax.cache.Cache;
import javax.cache.CacheException;
import javax.cache.CacheFactory;
import javax.cache.CacheManager;
import java.util.*;

/**
 * Created by Chazz on 30/12/15.
 */
public class DAOController {

    private static final String MESSAGE = "message";

    /* singleton */
    private static DAOController instance = new DAOController();
    public static DAOController getInstance() {
        return instance;
    }

    /* attributes */
    private final DatastoreService dataStore;
    private Cache cache;

    /**
     * Initialise the singleton DAO Controller.
     */
    private DAOController() {

        this.dataStore = DatastoreServiceFactory.getDatastoreService();
        this.cache = null;

        /* init cache */

        Map properties = new HashMap<>();
        properties.put(GCacheFactory.EXPIRATION_DELTA, 3600);
        properties.put(MemcacheService.SetPolicy.ADD_ONLY_IF_NOT_PRESENT, true);

        try {
            CacheFactory factory = CacheManager.getInstance().getCacheFactory();
            this.cache = factory.createCache(properties);
        } catch (CacheException e ){
            /* boom */
            e.printStackTrace();
        }
    }

    /*
     * List of all actions
     */

    /**
     * Stores an user
     * @param user
     */
    public void setUser(User user) {
        Entity entity = user.toEntity();
        dataStore.put(entity);
    }

    /**
     * Stores a new welcome message.
     * @param message
     */

    public void setWelcomeMessage(String message){
        Entity entity = new Entity(String.class.getName());
        entity.setProperty("message", message);
        entity.setProperty("createdAt", new Date());
        dataStore.put(entity);
    }

    /**
     * Fetches the last welcome message added, first try to find it in the cache.
     * If not present, puts it in.
     * @return the current welcome message
     */
    public String getWelcomeMessage() {
        /* searches for the message in the cache */
        String message = (String) cache.get(MESSAGE);

        /* if not cached */
        if(null == message) {
            Query query = new Query(String.class.getName());
            query.addSort("createdAt", Query.SortDirection.DESCENDING);
            PreparedQuery preparedQuery = dataStore.prepare(query);
            for(Entity entity : preparedQuery.asIterable()){
                message = (String) entity.getProperty("message");
                break;
            }
            cache.put(MESSAGE, message);
        }
        return message;
    }

    /**
     * Fetches an user by its googleId.
     * @param googleId
     * @return the user's profile
     * @throws EntityNotFoundException
     * @throws JSONException
     */
    public User getUserByGoogleId(long googleId) throws  EntityNotFoundException, JSONException {

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


    /**
     * Stores an exercise as a child of  training corresponding to trainingID.
     * @param exercise
     */
    public void setExercise(Exercise exercise, long trainingId) {
        Entity entity = exercise.toEntity(trainingId);
        dataStore.put(entity);
    }

    /**
     * Stores a training with the registered googleId.
     * @param training
     * @param googleId
     * @throws EntityNotFoundException
     * @throws JSONException
     */
    public long setTrainingWithGoogleId(Training training, long googleId) throws EntityNotFoundException, JSONException {
        User user = getUserByGoogleId(googleId);
        long id = user.getId();

        /* set the training with the id of the user */
        Entity entity = training.toEntity(id);
        dataStore.put(entity);

        /* return id */
        return entity.getKey().getId();
    }

    /**
     * Fetches the list of all trainings.
     * @return the list of all trainings.
     */
    public List<Training> getTrainings(){
        List<Training> trainings = new ArrayList<>();

        Query query = new Query(Training.class.getName());
        PreparedQuery preparedQuery = dataStore.prepare(query);

        for(Entity entity : preparedQuery.asIterable()) {
            Training training = new Training(entity);
            long trainingId = entity.getKey().getId();
            training.setExercises(getExercisesByTrainingId(trainingId));
            trainings.add(training);
        }

        return trainings;
    }

    /**
     * Fetches a list of trainings according to the given googleId.
     * First retrieves info about the user and calls DAOController#getTrainingsByUserId
     * @param googleId
     * @return the list of trainings corresponding to the googleId
     * @throws EntityNotFoundException
     * @throws JSONException
     */
    public List<Training> getTrainingsByGoogleId(long googleId) throws EntityNotFoundException, JSONException {
        User user = getUserByGoogleId(googleId);
        long userId = user.getId();

        return getTrainingsByUserId(userId);
    }

    /**
     * Gets the trainings of an user.
     * @param userId
     * @return the training list of an user.
     * @throws EntityNotFoundException
     * @throws JSONException
     */
    private List<Training> getTrainingsByUserId(long userId) throws EntityNotFoundException, JSONException {
        List<Training> trainings = new ArrayList<>();
          /* Creates key with given id */
        Key key = KeyFactory.createKey(Training.class.getName(), userId);

        /* Prepares query and execute it */
        Query query = new Query(Training.class.getName());
        query.setAncestor(key);
        PreparedQuery preparedQuery = dataStore.prepare(query);

        /* Parses result */
        for(Entity entity : preparedQuery.asIterable()) {
            Training training = new Training(entity);
            long trainingId = entity.getKey().getId();
            training.setExercises(getExercisesByTrainingId(trainingId));
            trainings.add(training);
        }
        return trainings;
    }


    /**
     * Fetches a list of exercises according to the given trainingId.
     * @param id the training id
     * @return the list of Exercises of this training.
     */
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

    /**
     * Lists all the exercises presents.
     * @return the list of all the exercises.
     */
    public List<Exercise> getExercises() {
        List<Exercise> exercises = new ArrayList<>();

        Query query = new Query(Exercise.class.getName());
        PreparedQuery preparedQuery = dataStore.prepare(query);

        for(Entity entity : preparedQuery.asIterable()) {
            exercises.add(new Exercise(entity));
        }
        return exercises;
    }

    public List<Exercise> getExercisesWithFilter(String filter) {
        List<Exercise> exercises = new ArrayList<>();

        Query query = new Query(Exercise.class.getName());
        PreparedQuery preparedQuery = dataStore.prepare(query);

        for(Entity entity : preparedQuery.asIterable()) {
            Exercise exercise = new Exercise(entity);
            if(exercise.contains(filter))
                exercises.add(exercise);
        }
        return exercises;
    }

    public List<Training> getTrainingsWithFilter(String filter) {
        List<Training> trainings = new ArrayList<>();

        Query query = new Query(Training.class.getName());
        PreparedQuery preparedQuery = dataStore.prepare(query);

        for(Entity entity : preparedQuery.asIterable()) {
            Training training = new Training(entity);
            if(training.contains(filter)){
                long trainingId = entity.getKey().getId();
                training.setExercises(getExercisesByTrainingId(trainingId));
                trainings.add(training);

            }
        }
        return trainings;
    }
}
