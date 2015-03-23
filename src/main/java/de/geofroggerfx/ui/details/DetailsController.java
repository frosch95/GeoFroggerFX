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
import de.geofroggerfx.model.Cache;
import de.geofroggerfx.ui.FXMLController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
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
import static de.geofroggerfx.ui.GeocachingIcons.getStarsAsString;

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

    private GoogleMap map;
    private Marker marker;


    @Value("${fxml.geofrogger.details.view}")
    @Override
    public void setFxmlFilePath(String filePath) {
        this.fxmlFilePath = filePath;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sessionContext.addListener(CURRENT_CACHE, new SessionContextListener() {
            @Override
            public void sessionContextChanged() {
                fillContent();
            }
        });

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
}
