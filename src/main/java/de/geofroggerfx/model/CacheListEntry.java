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

/**
 * Created by Andreas on 10.03.2015.
 */
public class CacheListEntry {

    private LongProperty id = new SimpleLongProperty();
    private StringProperty name = new SimpleStringProperty();
    private StringProperty difficulty = new SimpleStringProperty();
    private StringProperty terrain = new SimpleStringProperty();
    private StringProperty code = new SimpleStringProperty();
    private ObjectProperty<Type> type = new SimpleObjectProperty<>();

    public CacheListEntry(long id,
                          String name,
                          String code,
                          String difficulty,
                          String terrain,
                          Type type) {
        this.id.setValue(id);
        this.name.setValue(name);
        this.code.setValue(code);
        this.difficulty.setValue(difficulty);
        this.terrain.setValue(terrain);
        this.type.setValue(type);
    }

    public String getName() {
        return name.get();
    }

    public ReadOnlyStringProperty nameProperty() {
        return name;
    }

    public String getCode() {
        return code.get();
    }

    public ReadOnlyStringProperty codeProperty() {
        return code;
    }

    public long getId() {
        return id.get();
    }

    public ReadOnlyLongProperty idProperty() {
        return id;
    }

    public String getDifficulty() {
        return difficulty.get();
    }

    public ReadOnlyStringProperty difficultyProperty() {
        return difficulty;
    }

    public String getTerrain() {
        return terrain.get();
    }

    public ReadOnlyStringProperty terrainProperty() {
        return terrain;
    }

    public Type getType() {
        return type.get();
    }

    public ReadOnlyObjectProperty<Type> typeProperty() {
        return type;
    }

    @Override
    public String toString() {
        return "CacheListEntry{" +
                "name=" + name +
                '}';
    }
}
