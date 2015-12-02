package models;

import java.util.Date;

/**
 * Created by Chazz on 02/12/15.
 */
public abstract class AbstractUser {

    protected final String uuid;
    protected String firstname;
    protected String lastname;
    protected String email;
    protected Date birthdate;

    AbstractUser(String uuid, String firstname, String lastname, String email, Date birthdate) {
        this.uuid = uuid;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.birthdate = birthdate;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("uuid: "+this.uuid)
                .append(", "+this.firstname)
                .append(" "+this.lastname);

        return builder.toString();
    }
}
