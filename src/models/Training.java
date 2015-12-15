package models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chazz on 15/12/15.
 */
public class Training {

    private final static String TITLE = "TITLE";
    private final static String DESCRIPTION = "DESCRIPTION";

    private final String title;
    private final String description;
    private final List<Exercise> exercises;

    public Training(String title, String description) {
        this.title = title;
        this.description = description;
        this.exercises = new ArrayList<>();
    }

    public int getDuration(){
        int duration = 0;
        for(Exercise exercise : exercises)
            duration += exercise.getDuration();

        return duration;
    }

    public String getTitle(){
        return title;
    }

    public String getDescription() {
        return description;
    }

    public void toEntity() {

    }
}
