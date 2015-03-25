/*
 * Copyright (c) Andreas Billmann <abi@geofroggerfx.de>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package de.geofroggerfx.model;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Cache {
    private LongProperty id = new SimpleLongProperty();
    private BooleanProperty available = new SimpleBooleanProperty();
    private BooleanProperty archived = new SimpleBooleanProperty();
    private BooleanProperty found = new SimpleBooleanProperty();
    private StringProperty name = new SimpleStringProperty();
    private StringProperty placedBy = new SimpleStringProperty();
    private ObjectProperty<User> owner = new SimpleObjectProperty<>();
    private ObjectProperty<Type> type = new SimpleObjectProperty<>();
    private ObjectProperty<Container> container = new SimpleObjectProperty<>();
    private ObjectProperty<ObservableList<Attribute>> attributes = new SimpleObjectProperty<>(FXCollections.observableArrayList());
    private StringProperty difficulty = new SimpleStringProperty();
    private StringProperty terrain = new SimpleStringProperty();
    private StringProperty country = new SimpleStringProperty();
    private StringProperty state = new SimpleStringProperty();
    private StringProperty shortDescription = new SimpleStringProperty();
    private BooleanProperty shortDescriptionHtml = new SimpleBooleanProperty();
    private StringProperty longDescription = new SimpleStringProperty();
    private BooleanProperty longDescriptionHtml = new SimpleBooleanProperty();
    private StringProperty encodedHints = new SimpleStringProperty();
    private ObjectProperty<Waypoint> mainWayPoint = new SimpleObjectProperty<>();
    private ObjectProperty<ObservableList<Log>> logs = new SimpleObjectProperty<>(FXCollections.observableArrayList());

    public long getId() {
        return id.get();
    }

    public LongProperty idProperty() {
        return id;
    }

    public void setId(long id) {
        this.id.set(id);
    }

    public boolean getAvailable() {
        return available.get();
    }

    public BooleanProperty availableProperty() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available.set(available);
    }

    public boolean getArchived() {
        return archived.get();
    }

    public BooleanProperty archivedProperty() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived.set(archived);
    }

    public boolean getFound() {
        return found.get();
    }

    public BooleanProperty foundProperty() {
        return found;
    }

    public void setFound(boolean found) {
        this.found.set(found);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getPlacedBy() {
        return placedBy.get();
    }

    public StringProperty placedByProperty() {
        return placedBy;
    }

    public void setPlacedBy(String placedBy) {
        this.placedBy.set(placedBy);
    }

    public User getOwner() {
        return owner.get();
    }

    public ObjectProperty<User> ownerProperty() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner.set(owner);
    }

    public Type getType() {
        return type.get();
    }

    public ObjectProperty<Type> typeProperty() {
        return type;
    }

    public void setType(Type type) {
        this.type.set(type);
    }

    public Container getContainer() {
        return container.get();
    }

    public ObjectProperty<Container> containerProperty() {
        return container;
    }

    public void setContainer(Container container) {
        this.container.set(container);
    }

    public ObservableList<Attribute> getAttributes() {
        return attributes.get();
    }

    public ObjectProperty<ObservableList<Attribute>> attributesProperty() {
        return attributes;
    }

    public void setAttributes(ObservableList<Attribute> attributes) {
        this.attributes.set(attributes);
    }

    public String getDifficulty() {
        return difficulty.get();
    }

    public StringProperty difficultyProperty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty.set(difficulty);
    }

    public String getTerrain() {
        return terrain.get();
    }

    public StringProperty terrainProperty() {
        return terrain;
    }

    public void setTerrain(String terrain) {
        this.terrain.set(terrain);
    }

    public String getCountry() {
        return country.get();
    }

    public StringProperty countryProperty() {
        return country;
    }

    public void setCountry(String country) {
        this.country.set(country);
    }

    public String getState() {
        return state.get();
    }

    public StringProperty stateProperty() {
        return state;
    }

    public void setState(String state) {
        this.state.set(state);
    }

    public String getShortDescription() {
        return shortDescription.get();
    }

    public StringProperty shortDescriptionProperty() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription.set(shortDescription);
    }

    public boolean getShortDescriptionHtml() {
        return shortDescriptionHtml.get();
    }

    public BooleanProperty shortDescriptionHtmlProperty() {
        return shortDescriptionHtml;
    }

    public void setShortDescriptionHtml(boolean shortDescriptionHtml) {
        this.shortDescriptionHtml.set(shortDescriptionHtml);
    }

    public String getLongDescription() {
        return longDescription.get();
    }

    public StringProperty longDescriptionProperty() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription.set(longDescription);
    }

    public boolean getLongDescriptionHtml() {
        return longDescriptionHtml.get();
    }

    public BooleanProperty longDescriptionHtmlProperty() {
        return longDescriptionHtml;
    }

    public void setLongDescriptionHtml(boolean longDescriptionHtml) {
        this.longDescriptionHtml.set(longDescriptionHtml);
    }

    public String getEncodedHints() {
        return encodedHints.get();
    }

    public StringProperty encodedHintsProperty() {
        return encodedHints;
    }

    public void setEncodedHints(String encodedHints) {
        this.encodedHints.set(encodedHints);
    }

    public Waypoint getMainWayPoint() {
        return mainWayPoint.get();
    }

    public ObjectProperty<Waypoint> mainWayPointProperty() {
        return mainWayPoint;
    }

    public void setMainWayPoint(Waypoint mainWayPoint) {
        this.mainWayPoint.set(mainWayPoint);
    }

    public ObservableList<Log> getLogs() {
        return logs.get();
    }

    public ObjectProperty<ObservableList<Log>> logsProperty() {
        return logs;
    }

    public void setLogs(ObservableList<Log> logs) {
        this.logs.set(logs);
    }

    @Override
    public String toString() {
        return "Cache{" +
                "name=" + name +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cache cache = (Cache) o;

        if (id != null ? !id.equals(cache.id) : cache.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
