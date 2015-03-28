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

import de.geofroggerfx.application.SessionContext;
import de.geofroggerfx.model.Cache;
import de.geofroggerfx.model.CacheListEntry;
import de.geofroggerfx.service.CacheService;
import de.geofroggerfx.ui.FXMLController;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static de.geofroggerfx.application.SessionContext.CACHE_ENTRY_LIST;
import static de.geofroggerfx.application.SessionContext.CURRENT_CACHE;

/**
 * Created by Andreas on 09.03.2015.
 */
@Component
public class ListController extends FXMLController {

    @Autowired
    private SessionContext sessionContext;

    @Autowired
    private CacheService cacheService;

    @FXML
    ListView cacheListView;

    @Value("${fxml.geofrogger.list.view}")
    @Override
    public void setFxmlFilePath(String filePath) {
        this.fxmlFilePath = filePath;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setCellFactory();
        sessionContext.addListener(CACHE_ENTRY_LIST, () -> Platform.runLater(this::resetCacheList));
        cacheListView.getSelectionModel().selectedItemProperty().addListener(
                observable -> setSelectedCacheIntoSession()
        );
    }

    private void resetCacheList() {
        List<Cache> caches = (List<Cache>) sessionContext.getData(CACHE_ENTRY_LIST);
        cacheListView.getItems().setAll(caches);
    }

    @SuppressWarnings("unchecked")
    private void setCellFactory() {
        cacheListView.setCellFactory(param -> new CacheListCell());
    }

    private void setSelectedCacheIntoSession() {
        CacheListEntry entry = (CacheListEntry) cacheListView.getSelectionModel().getSelectedItem();
        Cache cache = null;
        if (entry != null) {
            cache = cacheService.getCacheForId(entry.getId());
        }
        sessionContext.setData(CURRENT_CACHE, cache);
    }
}
