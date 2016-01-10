package models;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.labs.repackaged.org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chazz on 15/12/15.
 */
public class Training {

    public final static String ID = "id";
    public final static String TITLE = "title";
    public final static String DESCRIPTION = "description";
    public final static String EXERCISES = "exercises";
    public final static String GOOGLE_ID = "googleId";

    private final String title;
    private final String description;
    private long googleId ;


    private List<Exercise> exercises;

    private long id = 0;


    public Training(JSONObject json, long googleId) throws JSONException{
        this.title = json.getString(Training.TITLE);
        this.description = json.getString(Training.DESCRIPTION);
        this.googleId = googleId;
        this.exercises = new ArrayList<>();
    }

    public Training(Entity entity) {
        if(!entity.getKind().equals(Training.class.getName()))
            throw new IllegalArgumentException();

        this.title = (String) entity.getProperty(Training.TITLE);
        this.description = (String) entity.getProperty(Training.DESCRIPTION);

        this.exercises = new ArrayList<>();

        this.googleId = (long) entity.getProperty(Training.GOOGLE_ID);

    }

    public int getDuration(){
        int duration = 0;
        for(Exercise exercise : exercises)
            duration += exercise.getDuration();
        return duration;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }



    public Entity toEntity(long googleId){
    //    Key key = KeyFactory.createKey(User.class.getName(), userId);
        Entity entity = new Entity(Training.class.getName());
        entity.setProperty(TITLE, this.title);
        entity.setProperty(DESCRIPTION, this.description);
        entity.setProperty(GOOGLE_ID, googleId);
        return entity;
    }

    public boolean contains(String filter){
        if (this.title.toLowerCase().contains(filter.toLowerCase()))
            return true;
        return false;
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject object = new JSONObject();
        object.put(TITLE, this.title);
        object.put(DESCRIPTION, this.description);
        object.put(GOOGLE_ID, this.googleId);
        JSONArray array = new JSONArray();
        for(Exercise exercise: this.exercises )
            array.put(exercise.toJSON());
        object.put(EXERCISES, array);
        if(id != 0) {
            object.put(ID, this.id);
        }
        return object;
    }
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Training[title=\""+title+"\", description=\""+description+"\"]";
    }
}
