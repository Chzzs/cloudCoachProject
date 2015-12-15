package models;

import com.google.appengine.api.datastore.Entity;

/**
 * Created by Chazz on 15/12/15.
 */
public class Exercise {

    private final static String TITLE = "title";
    private final static String DURATION = "duration";
    private final static String DESCRIPTION = "description";


    private final String title;
    private final String description;
    private final int duration;

    public Exercise(String title, String description, int duration) {
        this.title = title;
        this.description = description;
        this.duration = duration;
    }

    public Entity toEntity() {
        Entity entity = new Entity(Exercise.class.getName());
        entity.setProperty(TITLE, this.title);
        entity.setProperty(DURATION, this.duration);
        entity.setProperty(DESCRIPTION, this.description);
        return entity;
    }

    public int getDuration() {
        return duration;
    }


    @Override
    public String toString() {
        return "Exercise[title="+title+", description="+description+", duration="+duration+"sec]";
    }
}
