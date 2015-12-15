package models;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.labs.repackaged.org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chazz on 15/12/15.
 */
public class Training {

    private final static String TITLE = "title";
    private final static String DESCRIPTION = "description";
    private final static String EXERCICES = "exercices";

    private final String title;
    private final String description;
    private List<Exercise> exercises;


    public Training(JSONObject json) throws JSONException{
        this.title = json.getString(Training.TITLE);
        this.description = json.getString(Training.DESCRIPTION);
        this.exercises = new ArrayList<>();
    }

    public Training(Entity entity) {
        if(!entity.getKind().equals(Training.class.getName()))
            throw new IllegalArgumentException();

        this.title = (String) entity.getProperty(Training.TITLE);
        this.description = (String) entity.getProperty(Training.DESCRIPTION);

        this.exercises = new ArrayList<>();
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

    public Entity toEntity(){
        Entity entity = new Entity(Training.class.getName());
        entity.setProperty(TITLE, this.title);
        entity.setProperty(DESCRIPTION, this.description);
        return entity;
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject object = new JSONObject();
        object.put(TITLE, this.title);
        object.put(DESCRIPTION, this.description);
        JSONArray array = new JSONArray();
        for(Exercise exercise: this.exercises )
            array.put(exercise.toJSON());
        object.put(EXERCICES, array);
        return object;
    }

    @Override
    public String toString() {
        return "Training[title=\""+title+"\", description=\""+description+"\"]";
    }
}
