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
package de.geofroggerfx.ui.details;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.javascript.object.*;
import de.geofroggerfx.application.SessionContext;
import de.geofroggerfx.model.Attribute;
import de.geofroggerfx.model.Cache;
import de.geofroggerfx.model.Log;
import de.geofroggerfx.ui.FXMLController;
import de.geofroggerfx.ui.GeocachingIcons;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import de.jensd.fx.glyphs.materialdesignicons.utils.MaterialDesignIconFactory;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Window;
import org.controlsfx.control.PopOver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

import static de.geofroggerfx.application.SessionContext.CURRENT_CACHE;
import static de.geofroggerfx.ui.GeocachingIcons.getStarsAsString;
import static de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon.CHECK;

/**
 * Created by Andreas on 09.03.2015.
 */
@Component
public class DetailsController extends FXMLController {

    @Autowired
    private SessionContext sessionContext;

    @FXML
    private BorderPane detailsPane;

    @FXML
    private Label cacheName;

    @FXML
    private Text cacheDifficulty;

    @FXML
    private Text cacheTerrain;

    @FXML
    private VBox mainContent;

    @FXML
    private WebView description;

    @FXML
    private GoogleMapView mapView;

    @FXML
    private FlowPane attributeList;

    @FXML
    private GridPane logList;

    @FXML
    private FlowPane logButtonPane;

    private GoogleMap map;
    private Marker marker;


    @Value("${fxml.geofrogger.details.view}")
    @Override
    public void setFxmlFilePath(String filePath) {
        this.fxmlFilePath = filePath;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        detailsPane.setVisible(false);
        setCacheListener();
        initializeMap();
        setLogLinkButton();
    }

    private void setCacheListener() {
        sessionContext.addListener(CURRENT_CACHE, () -> fillContent());
    }

    private void initializeMap() {
        mapView.addMapInializedListener(() -> {
            //Set the initial properties of the map.
            MapOptions mapOptions = new MapOptions();
            mapOptions.center(new LatLong(47.6097, -122.3331))
                    .mapType(MapTypeIdEnum.ROADMAP)
                    .overviewMapControl(false)
                    .panControl(false)
                    .rotateControl(false)
                    .scaleControl(false)
                    .streetViewControl(false)
                    .zoomControl(false)
                    .zoom(12);

            map = mapView.createMap(mapOptions);

            LatLong latLong = new LatLong(49.045458, 9.189835);
            MarkerOptions options = new MarkerOptions();
            options.position(latLong);
            marker = new Marker(options);
            map.addMarker(marker);
            map.setCenter(latLong);
        });
    }

    private void setLogLinkButton() {
        Button logLink = MaterialDesignIconFactory.get().createIconButton(MaterialDesignIcon.LINK_VARIANT);
        logLink.setStyle("-fx-background-color: transparent;");
        logButtonPane.getChildren().add(logLink);


        logLink.setOnAction(event -> {
            Cache cache = (Cache) sessionContext.getData(CURRENT_CACHE);
            if (cache != null) {
                showLogPopOver(logLink, cache);
            }
        });
    }

    private void showLogPopOver(Button logLink, Cache cache) {
        PopOver popOver = new PopOver();

        Window window = logLink.getScene().getWindow();

        VBox vbox = new VBox();
        vbox.setFillWidth(true);
        vbox.setMaxWidth(window.getWidth() - 420);
        vbox.setSpacing(60.0);
        vbox.setPadding(new Insets(30,20,30,20));
        vbox.setStyle("-fx-background-color: white");

        for (Log log: cache.getLogs()) {

            BorderPane borderPane = new BorderPane();

            Text icon = new MaterialDesignIconView(CHECK);
            Label date = new Label(log.getDate().toString());
            date.setStyle("-fx-font-weight: bold; -fx-font-size: 1.2em;");
            Label name = new Label(log.getFinder().getName());
            name.setStyle("-fx-font-weight: bold; -fx-font-size: 1.2em;");

            HBox.setHgrow(icon, Priority.NEVER);
            HBox.setHgrow(date, Priority.NEVER);
            HBox.setHgrow(name, Priority.ALWAYS);

            HBox header = new HBox();
            header.setSpacing(10.0);
            header.getChildren().addAll(icon, date, name);
            header.setPadding(new Insets(10, 10, 10, 10));
            header.setStyle("-fx-border-color: transparent transparent gray transparent");

            borderPane.setTop(header);

            Label logText = new Label(log.getText());
            logText.setWrapText(true);
            logText.setPadding(new Insets(10,10,10,10));
            BorderPane.setAlignment(logText, Pos.TOP_LEFT);

            borderPane.setCenter(logText);

            vbox.getChildren().add(borderPane);
        }

        ScrollPane scrollPane = new ScrollPane(vbox);
        scrollPane.setFitToWidth(true);
        AnchorPane.setBottomAnchor(scrollPane, 0.0);
        AnchorPane.setLeftAnchor(scrollPane, 0.0);
        AnchorPane.setTopAnchor(scrollPane, 0.0);
        AnchorPane.setRightAnchor(scrollPane, 0.0);

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefWidth(window.getWidth()-400);
        anchorPane.setMaxWidth(window.getWidth() - 400);
        anchorPane.setStyle("-fx-background-color: white");

        anchorPane.setMaxHeight(window.getHeight() - 400);
        anchorPane.setPadding(new Insets(20, 20, 20, 20));
        anchorPane.getChildren().add(scrollPane);
        anchorPane.setStyle("-fx-background-color: transparent");

        popOver.setContentNode(anchorPane);
        popOver.setDetached(true);
        //popOver.setDetachedTitle("Log Entries");
        popOver.show(logLink.getScene().getWindow());
    }

    private void fillContent() {
        Cache cache = (Cache) sessionContext.getData(CURRENT_CACHE);

        if (cache != null) {
            detailsPane.setVisible(true);
            cacheName.setText(cache.getName());
            cacheDifficulty.setText(getStarsAsString(cache.getDifficulty()));
            cacheTerrain.setText(getStarsAsString(cache.getTerrain()));

            String shortDescription = cache.getShortDescription();
            if (!cache.getLongDescriptionHtml()) {
                shortDescription = shortDescription.replaceAll("\n", "</br>");
            }

            String longDescription = cache.getLongDescription();
            if (!cache.getLongDescriptionHtml()) {
                longDescription = longDescription.replaceAll("\n", "</br>");
            }

            String description = "<html><body><p>" + shortDescription + "</p><p>" + longDescription + "</p></body></html>";
            final WebEngine webEngine = this.description.getEngine();
            webEngine.loadContent(description);

            attributeList.getChildren().clear();
            for (Attribute attribute : cache.getAttributes()) {
                attributeList.getChildren().add(MaterialDesignIconFactory.get().createIcon(GeocachingIcons.getIcon(attribute)));
            }

            logList.getChildren().clear();
            int maxRow = Math.min(4, cache.getLogs().size());
            for (int row = 0; row < maxRow; row++) {
                setLogIcon(row);
                setLogText(row, cache.getLogs().get(row));
            }

            LatLong latLong = new LatLong(cache.getMainWayPoint().getLatitude(), cache.getMainWayPoint().getLongitude());
            MarkerOptions options = new MarkerOptions();
            options.position(latLong);
            options.title(cache.getMainWayPoint().getName());

            map.removeMarker(marker);

            marker.setTitle(cache.getMainWayPoint().getName());
            marker = new Marker(options);
            map.addMarker(marker);
            map.setCenter(latLong);
            mapView.requestLayout();
        } else {
            detailsPane.setVisible(false);
        }
    }

    private void setLogIcon(int row) {
        logList.add(MaterialDesignIconFactory.get().createIcon(CHECK), 0, row);
    }

    private void setLogText(int row, Log log) {
        Label logLabel = new Label(log.getText());
        logLabel.setWrapText(false);
        logLabel.setTextOverrun(OverrunStyle.ELLIPSIS);
        logLabel.setAlignment(Pos.TOP_LEFT);
        logLabel.setMaxHeight(Region.USE_PREF_SIZE);
        logLabel.setPrefHeight(18.0);
        logLabel.setMinHeight(Region.USE_PREF_SIZE);
        logLabel.setTooltip(new Tooltip(log.getText()));
        logList.add(logLabel, 1, row);
    }
}
