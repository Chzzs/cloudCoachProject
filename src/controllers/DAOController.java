package controllers;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import models.AbstractUser;

/**
 * Created by Chazz on 02/12/15.
 */
public class DAOController {

    DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService();

    public void  setUser(AbstractUser user) {
        //dataStore.put();
    }


}
