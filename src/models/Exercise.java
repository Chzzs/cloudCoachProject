package models;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Chazz on 15/12/15.
 */
public class Exercise {

    public final static String TITLE = "title";
    public final static String DURATION = "duration";
    public final static String REPETITION = "repetition";
    public final static String DESCRIPTION = "description";
    public final static String TRAINING_ID = "trainingId";


    private final String title;
    private final String description;
    private final long duration;
    private final long repetition;
    private long trainingId;

    public Exercise(JSONObject json) throws JSONException{
        this.title = (String) json.get(TITLE);
        this.description = (String) json.get(DESCRIPTION);
        this.duration =  json.getLong(DURATION);
        this.repetition = json.getLong(REPETITION);
        this.trainingId = json.getLong(TRAINING_ID);
    }

    public boolean contains(String filter){
        if(this.title.toLowerCase().contains(filter.toLowerCase()))
            return true;
        return false;
    }

    public Exercise(Entity entity) {
        if(!entity.getKind().equals(Exercise.class.getName()))
            throw new IllegalArgumentException();

        this.title = (String) entity.getProperty(Exercise.TITLE);
        this.description = (String) entity.getProperty(Exercise.DESCRIPTION);
        this.duration = (long) entity.getProperty(Exercise.DURATION);
        this.repetition = (long) entity.getProperty(Exercise.REPETITION);
        this.trainingId  = (long) entity.getProperty(Exercise.TRAINING_ID);
    }

    public Entity toEntity(long trainingId) {
        //TODO workaround
        Key key = KeyFactory.createKey(Training.class.getName(), trainingId);
        Entity entity = new Entity(Exercise.class.getName(), key);


        entity.setProperty(TITLE, this.title);
        entity.setProperty(DURATION, this.duration);
        entity.setProperty(REPETITION, this.repetition);
        entity.setProperty(DESCRIPTION, this.description);
        entity.setProperty(TRAINING_ID, this.trainingId);
        return entity;
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject object = new JSONObject();
        object.put(TITLE, this.title);
        object.put(DESCRIPTION, this.description);
        object.put(DURATION, this.duration);
        object.put(REPETITION, this.repetition);
        return object;
    }

    public long getRepetition() {
        return this.repetition;
    }


    public long getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        return "Exercise[title=\""+title+"\", description=\""+description+"\", duration="+duration+"sec]";
    }
}
