package de.geofroggerfx.model;

import javafx.beans.property.*;

import java.util.Date;

/**
 * Created by Andreas on 25.03.2015.
 */
public class Log {

    private LongProperty id = new SimpleLongProperty();
    private ObjectProperty<Date> date = new SimpleObjectProperty<>();
    private ObjectProperty<LogType> type = new SimpleObjectProperty<>();
    private ObjectProperty<User> finder = new SimpleObjectProperty<>();
    private StringProperty text = new SimpleStringProperty();

    public long getId() {
        return id.get();
    }

    public LongProperty idProperty() {
        return id;
    }

    public void setId(long id) {
        this.id.set(id);
    }

    public LogType getType() {
        return type.get();
    }

    public ObjectProperty<LogType> typeProperty() {
        return type;
    }

    public void setType(LogType type) {
        this.type.set(type);
    }

    public User getFinder() {
        return finder.get();
    }

    public ObjectProperty<User> finderProperty() {
        return finder;
    }

    public void setFinder(User finder) {
        this.finder.set(finder);
    }

    public String getText() {
        return text.get();
    }

    public StringProperty textProperty() {
        return text;
    }

    public void setText(String text) {
        this.text.set(text);
    }

    public Date getDate() {
        return date.get();
    }

    public ObjectProperty<Date> dateProperty() {
        return date;
    }

    public void setDate(Date date) {
        this.date.set(date);
    }
}
