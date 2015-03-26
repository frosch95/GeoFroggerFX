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
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.*;
import de.geofroggerfx.application.SessionContext;
import de.geofroggerfx.application.SessionContextListener;
import de.geofroggerfx.model.Attribute;
import de.geofroggerfx.model.Cache;
import de.geofroggerfx.model.Log;
import de.geofroggerfx.ui.FXMLController;
import de.geofroggerfx.ui.GeocachingIcons;
import de.geofroggerfx.ui.glyphs.GeofroggerGlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

import static de.geofroggerfx.application.SessionContext.CURRENT_CACHE;
import static de.geofroggerfx.ui.GeocachingIcons.getIcon;
import static de.geofroggerfx.ui.GeocachingIcons.getStarsAsString;
import static de.jensd.fx.glyphs.GlyphsDude.createIcon;

/**
 * Created by Andreas on 09.03.2015.
 */
@Component
public class DetailsController extends FXMLController {

    @Autowired
    private SessionContext sessionContext;

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
        setCacheListener();
        initializeMap();
        setLogLinkButton();
    }

    private void setCacheListener() {
        sessionContext.addListener(CURRENT_CACHE, new SessionContextListener() {
            @Override
            public void sessionContextChanged() {
                fillContent();
            }
        });
    }

    private void initializeMap() {
        mapView.addMapInializedListener(new MapComponentInitializedListener() {
            @Override
            public void mapInitialized() {
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
            }
        });
    }

    private void setLogLinkButton() {
        Button logLink = GeofroggerGlyphsDude.createIconButton(FontAwesomeIcons.EXTERNAL_LINK);
        logLink.setStyle("-fx-background-color: transparent;");
        logButtonPane.getChildren().add(logLink);
        logLink.setOnAction(event -> {
            Cache cache = (Cache) sessionContext.getData(CURRENT_CACHE);
            if (cache != null) {
                StringBuilder sb = new StringBuilder();
                for (Log log: cache.getLogs()) {
                    sb.append(log.getText());
                    sb.append("\n--------------------------------------\n");
                }

                Alert logWindow = new Alert(Alert.AlertType.INFORMATION);
                logWindow.setContentText(sb.toString());
                logWindow.showAndWait();
            }
        });
    }

    private void fillContent() {
        Cache cache = (Cache) sessionContext.getData(CURRENT_CACHE);
        cacheName.setText(cache.getName());
        cacheDifficulty.setText(getStarsAsString(cache.getDifficulty()));
        cacheTerrain.setText(getStarsAsString(cache.getTerrain()));

        String shortDescription = cache.getShortDescription();
        if (!cache.getLongDescriptionHtml()) {
            shortDescription = shortDescription.replaceAll("\n","</br>");
        }

        String longDescription = cache.getLongDescription();
        if (!cache.getLongDescriptionHtml()) {
            longDescription = longDescription.replaceAll("\n","</br>");
        }

        String description = "<html><body><p>"+shortDescription+"</p><p>"+longDescription+"</p></body></html>";
        final WebEngine webEngine = this.description.getEngine();
        webEngine.loadContent(description);

        attributeList.getChildren().clear();
        for (Attribute attribute: cache.getAttributes()) {
            attributeList.getChildren().add(createIcon(getIcon(attribute), "1.6em"));
        }

        logList.getChildren().clear();
        int maxRow = Math.min(4, cache.getLogs().size());
        for (int row=0; row<maxRow; row++) {
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
    }

    private void setLogIcon(int row) {
        logList.add(GeofroggerGlyphsDude.createIcon(FontAwesomeIcons.CHECK), 0, row);
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
