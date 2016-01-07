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
public class Controller {
    private static final String MESSAGE = "message";


    private static Controller instance = new Controller();

    public static Controller getInstance() {
        return instance;
    }

    private final DatastoreService dataStore;
    private Cache cache;

    private Controller() {
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

    public void setUser(User user) {
        Entity entity = user.toEntity();
        dataStore.put(entity);
    }

    public void setWelcomeMessage(String message){
        Entity entity = new Entity(String.class.getName());
        entity.setProperty("message", message);
        entity.setProperty("createdAt", new Date());
        dataStore.put(entity);
    }

    public String getWelcomeMessage() {
        /* searches for the message in the cache */
        String message = (String ) cache.get(MESSAGE);

        /* if not cached */
        if(null == message) {
            Query query = new Query("Message");
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
     * stores an exercise as a child of  training corresponding to trainingID.
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

        for(Entity entity : preparedQuery.asIterable()) {
            Training training = new Training(entity);
            long trainingId = entity.getKey().getId();
            training.setExercises(getExercisesByTrainingId(trainingId));
            trainings.add(training);
        }

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
        for(Entity entity : preparedQuery.asIterable()) {
            Training training = new Training(entity);
            long trainingId = entity.getKey().getId();
            training.setExercises(getExercisesByTrainingId(trainingId));
            trainings.add(training);
        }
        return trainings;
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
}