package models;

import com.google.appengine.api.datastore.Entity;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Chazz on 02/12/15.
 */
public class User {

    protected String firstname;
    protected String lastname;
    protected String email;
    protected String password;
    protected Date birthdate;
    private String role;

    public User(String firstname, String lastname, String email, String password, String role, Date birthdate) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.birthdate = birthdate;
        this.role = role;
    }

    public Entity toEntity() {
        Entity entity = new Entity("User");
        entity.setProperty("password", this.password);
        entity.setProperty("firstname", this.firstname);
        entity.setProperty("lastname", this.lastname);
        entity.setProperty("email", this.email);
        entity.setProperty("birthdate", this.birthdate);
        entity.setProperty("role", this.role);
        return entity;
    }


}
