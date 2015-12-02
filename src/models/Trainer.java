package models;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Chazz on 02/12/15.
 */
public class Trainer extends AbstractUser {

    private Trainer(String uuid, String firstname, String lastname, String email, Date birthdate) {
        super(uuid, firstname, lastname, email, birthdate);
    }

    public static Trainer create(String firstname, String lastname, String email, Date birthdate) {
        String uuid = UUID.randomUUID().toString();
        return new Trainer(uuid,firstname, lastname, email, birthdate);
    }

}
