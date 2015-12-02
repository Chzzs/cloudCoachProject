package models;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Chazz on 02/12/15.
 */
public class Trainee extends AbstractUser {

    private Trainee(String uuid, String firstname, String lastname, String email, Date birthdate) {
        super(uuid, firstname, lastname, email, birthdate);
    }

    public static Trainee create(String firstname, String lastname, String email, Date birthdate) {
        String uuid = UUID.randomUUID().toString();
        return new Trainee(uuid,firstname, lastname, email, birthdate);
    }

}
