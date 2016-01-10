package models;

import com.google.appengine.api.datastore.Entity;

import java.util.Date;

/**
 * Created by Chazz on 10/01/16.
 */
public class Result {

    public final static String GOOGLE_ID = "googleId";
    public final static String EXERCISE_ID = "exerciseId";
    public final static String DATE = "date";


    long googleId;
    long exerciseId;
    Date date;

    public Result(long googleId, long exerciseId) {
        this.googleId = googleId;
        this.exerciseId = exerciseId;
        this.date = new Date();
    }

    public Result(Entity entity) {
        if(!entity.getKind().equals(Result.class.getName()))
            throw new IllegalArgumentException();

        this.googleId = (long) entity.getProperty(Result.GOOGLE_ID);
        this.exerciseId  = (long) entity.getProperty(Result.EXERCISE_ID);
        this.date = (Date) entity.getProperty(Result.DATE);
    }

    public Entity toEntity() {
        //TODO workaround
        //  Key key = KeyFactory.createKey(Training.class.getName(), trainingId);
        // Entity entity = new Entity(Exercise.class.getName(), key);
        Entity entity = new Entity(Result.class.getName());

        entity.setProperty(GOOGLE_ID, this.googleId);
        entity.setProperty(EXERCISE_ID, this.exerciseId);
        entity.setProperty(DATE, this.date);

        return entity;
    }

    @Override
    public String toString() {
        return "Result[googleId"+googleId+", exerciseId="+exerciseId+", date="+date+"]";
    }
}
