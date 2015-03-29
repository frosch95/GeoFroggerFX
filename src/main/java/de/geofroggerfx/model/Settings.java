package de.geofroggerfx.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by Andreas on 29.03.2015.
 */
public class Settings {

    private StringProperty myUsername = new SimpleStringProperty();

    public String getMyUsername() {
        return myUsername.get();
    }

    public StringProperty myUsernameProperty() {
        return myUsername;
    }

    public void setMyUsername(String myUsername) {
        this.myUsername.set(myUsername);
    }
}
