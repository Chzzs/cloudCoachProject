package models;

import com.google.appengine.api.datastore.Entity;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Chazz on 02/12/15.
 */
public class User {

    private final static String FIRST_NAME = "firstName";
    private final static String LAST_NAME = "lastName";
    private final static String EMAIL = "email";
    private final static String GOOGLE_ID = "googleId";

    private String firstName;
    private String lastName;
    private String email;
    private long googleId;

    public User(String firstName, String lastName, String email, long googleId) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.googleId = googleId;
    }

    public User(JSONObject object) throws JSONException {
        this.email = (String) object.get(EMAIL);
        this.firstName = (String) object.get(FIRST_NAME);
        this.lastName = (String) object.get(LAST_NAME);
        this.googleId = (long) object.get(GOOGLE_ID);
    }

    public Entity toEntity() {
        Entity entity = new Entity(User.class.getName());
        entity.setProperty(FIRST_NAME, this.firstName);
        entity.setProperty(LAST_NAME, this.lastName);
        entity.setProperty(EMAIL, this.email);
        entity.setProperty(GOOGLE_ID, this.googleId);
        return entity;
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject object = new JSONObject();
        object.put(EMAIL, this.email);
        object.put(FIRST_NAME, this.firstName);
        object.put(LAST_NAME, this.lastName);
        object.put(GOOGLE_ID, this.googleId);
        return object;
    }


}
