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
package de.geofroggerfx.ui.list;

import de.geofroggerfx.model.CacheListEntry;
import de.geofroggerfx.ui.GeocachingIcons;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.utils.MaterialDesignIconFactory;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;

import static de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon.CHECKBOX_BLANK_OUTLINE;

/**
 * Multi-Column-Row list cell to shows the most important data in a list.
 *
 * @author Andreas
 */
public class CacheListCell extends ListCell<CacheListEntry> {
    private static final String YELLOW = ";-fx-fill: linear-gradient(#e8e474 0%, #edcf59 70%, #bfba26 85%);";
    private static final String GRAY = ";-fx-fill: linear-gradient(#cccccc 0%, #999999 70%, #888888 85%);";
    private final GridPane grid = new GridPane();
    private final Text icon = MaterialDesignIconFactory.get().createIcon(CHECKBOX_BLANK_OUTLINE, "20.0");
    private final Text foundIcon = MaterialDesignIconFactory.get().createIcon(CHECKBOX_BLANK_OUTLINE, "10.0");
    private final Label name = new Label();
    private final Text difficultyStars = MaterialDesignIconFactory.get().createIcon(CHECKBOX_BLANK_OUTLINE, "8.0");
    private final Text terrainStars = MaterialDesignIconFactory.get().createIcon(CHECKBOX_BLANK_OUTLINE, "8.0");

    public CacheListCell() {
        this.getStyleClass().add("cache-list-cell");
        configureGrid();
        configureIcon();
        configureName();
        configureDifficulty();
        configureTerrain();
        addControlsToGrid();
    }

    @Override
    public void updateItem(CacheListEntry cache, boolean empty) {
        super.updateItem(cache, empty);
        if (empty) {
            clearContent();
        } else {
            addContent(cache);
            updateCellState(cache);
        }
    }

    private void updateCellState(CacheListEntry cache) {

        if (cache.getArchived()) {
            this.getStyleClass().add("archived");
        } else {
            this.getStyleClass().remove("archived");
        }

        if (cache.getAvailable()) {
            this.getStyleClass().remove("not-available");
        } else {
            this.getStyleClass().add("not-available");
        }
    }

    private void configureGrid() {
        grid.setHgap(10);
        grid.setVgap(4);
        grid.setPadding(new Insets(0, 10, 0, 10));

        ColumnConstraints column1 = new ColumnConstraints(32);
        ColumnConstraints column2 = new ColumnConstraints(USE_COMPUTED_SIZE , USE_COMPUTED_SIZE, Double.MAX_VALUE);
        column2.setHgrow(Priority.NEVER);
        ColumnConstraints column3 = new ColumnConstraints(30 , 50 , Double.MAX_VALUE);
        column3.setHgrow(Priority.ALWAYS);
        column3.setFillWidth(true);
        ColumnConstraints column4 = new ColumnConstraints(USE_COMPUTED_SIZE , USE_COMPUTED_SIZE , Double.MAX_VALUE);
        column4.setHgrow(Priority.NEVER);
        ColumnConstraints column5 = new ColumnConstraints(30 , 50 , Double.MAX_VALUE);
        column5.setHgrow(Priority.ALWAYS);
        column5.setFillWidth(true);
        ColumnConstraints column6 = new ColumnConstraints(10, 12, 16);
        column6.setHgrow(Priority.NEVER);
        column6.setFillWidth(false);
        grid.getColumnConstraints().addAll(column1, column2, column3, column4, column5, column6);
    }

    private void configureIcon() {
        icon.setStyle(icon.getStyle()+ GRAY);
    }

    private void configureName() {
        name.setStyle("-fx-font-weight: bold;");
    }

    private void configureDifficulty() {
        difficultyStars.setStyle(difficultyStars.getStyle() + YELLOW);
    }

    private void configureTerrain() {
        terrainStars.setStyle(difficultyStars.getStyle() + YELLOW);
    }


    private void addControlsToGrid() {
        grid.add(icon, 0, 0, 1, 2);
        grid.add(name, 1, 0, 4, 1);
        grid.add(foundIcon, 5, 0);
        grid.add(new Label("Difficulty:"), 1, 1);
        grid.add(difficultyStars, 2, 1);
        grid.add(new Label("Terrain:"), 3, 1);
        grid.add(terrainStars, 4, 1);
    }

    private void clearContent() {
        setText(null);
        setGraphic(null);
    }

    private void addContent(CacheListEntry cache) {
        setIcon(cache);
        setFoundIcon(cache);
        setCacheName(cache);
        setDifficulty(cache);
        setTerrain(cache);
        setGraphic(grid);
    }

    private void setCacheName(CacheListEntry cache) {
        name.setText(cache.getName());
    }

    private void setIcon(CacheListEntry cache) {
        icon.setText(GeocachingIcons.getIconAsString(cache.getType()));
    }

    private void setFoundIcon(CacheListEntry cache) {
        if (cache.getFound()) {
            foundIcon.setText(MaterialDesignIcon.CHECK.characterToString());
        } else {
            foundIcon.setText("");
        }
    }

    private void setDifficulty(CacheListEntry cache) {
        difficultyStars.setText(GeocachingIcons.getStarsAsString(cache.getDifficulty()));
    }

    private void setTerrain(CacheListEntry cache) {
        terrainStars.setText(GeocachingIcons.getStarsAsString(cache.getTerrain()));
    }

}
