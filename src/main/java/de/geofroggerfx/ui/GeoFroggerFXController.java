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
package de.geofroggerfx.ui;

import de.geofroggerfx.application.ProgressEvent;
import de.geofroggerfx.application.SessionContext;
import de.geofroggerfx.gpx.GPXReader;
import de.geofroggerfx.model.Cache;
import de.geofroggerfx.model.Settings;
import de.geofroggerfx.plugins.Plugin;
import de.geofroggerfx.service.CacheService;
import de.geofroggerfx.service.PluginService;
import de.geofroggerfx.service.SettingsService;
import de.geofroggerfx.ui.details.DetailsController;
import de.geofroggerfx.ui.list.ListController;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static de.geofroggerfx.application.SessionContext.CACHE_ENTRY_LIST;

/**
 * Created by Andreas on 09.03.2015.
 */
@Component
public class GeoFroggerFXController extends FXMLController {

    private static final Logger LOGGER = Logger.getLogger(GeoFroggerFXController.class.getName());

    private static final String LICENSE = "/*\n" +
            " * Copyright (c) 2013-2015, Andreas Billmann <abi@geofroggerfx.de>\n" +
            " * All rights reserved.\n" +
            " *\n" +
            " * Redistribution and use in source and binary forms, with or without\n" +
            " * modification, are permitted provided that the following conditions are met:\n" +
            " *\n" +
            " * * Redistributions of source code must retain the above copyright notice, this\n" +
            " *   list of conditions and the following disclaimer.\n" +
            " * * Redistributions in binary form must reproduce the above copyright notice,\n" +
            " *   this list of conditions and the following disclaimer in the documentation\n" +
            " *   and/or other materials provided with the distribution.\n" +
            " *\n" +
            " * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS \"AS IS\"\n" +
            " * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE\n" +
            " * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE\n" +
            " * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE\n" +
            " * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR\n" +
            " * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF\n" +
            " * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS\n" +
            " * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN\n" +
            " * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)\n" +
            " * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE\n" +
            " * POSSIBILITY OF SUCH DAMAGE.\n" +
            " */";

    private static final String MASTHEAD_TEXT = "GeoFroggerFX by Andreas Billmann <abi@geofroggerfx.de>";
    private static final String ABOUT_TEXT = "Used libs:\n"
            + "\t- Groovy 2.0.4\n"
            + "\t- ControlsFX 8.20.8\n"
            + "\t- fontawesomefx 8.2\n"
            + "\t- H2 1.4.186\n"
            + "\t- GMapsFX 1.1.1\n"
            + "\t- Spring Framework 4.1.5\n"
            + "\t- Woodstox 4.4.1\n"
            + "\t- Sofia-Regular Font\n"
            + "\t- Elusive-Icons Font\n";


    @Autowired
    private CacheService cacheService;

    @Autowired
    private SettingsService settingsService;

    @Autowired
    private SessionContext sessionContext;

    @Autowired
    private ListController listController;

    @Autowired
    private DetailsController detailsController;

    @Autowired
    private PluginService pluginService;

    @Autowired
    private GPXReader reader;

    @FXML
    private HBox cacheListContent;

    @FXML
    private AnchorPane cacheDetailsContent;

    @FXML
    private Label leftStatus;

    @FXML
    private ProgressBar progress;

    @FXML
    private Menu pluginsMenu;

    private ResourceBundle resourceBundle;

    private final LoadCachesFromFileService loadService = new LoadCachesFromFileService();
    private final LoadCacheListsFromDatabaseService loadListsFromDBService = new LoadCacheListsFromDatabaseService();

    @Value("${fxml.geofrogger.view}")
    @Override
    public void setFxmlFilePath(String filePath) {
        this.fxmlFilePath = filePath;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resourceBundle = resources;

        LOGGER.info("load all sub controller");
        cacheListContent.getChildren().add(listController.getView());
        cacheDetailsContent.getChildren().add(detailsController.getView());

        LOGGER.info("add listeners");
        reader.addListener((ProgressEvent event) -> updateStatus(event.getMessage(), event.getProgress()));

        loadListsFromDBService.start();

        List<Plugin> plugins = pluginService.getAllPlugins();
        for (Plugin plugin: plugins) {
            MenuItem menuItem = new MenuItem(plugin.getName()+" ("+plugin.getVersion()+")");
            menuItem.setOnAction(actionEvent -> pluginService.executePlugin(plugin));
            pluginsMenu.getItems().add(menuItem);
        }

    }


    @FXML
    public void importGpx(ActionEvent actionEvent) {
        final FileChooser fileChooser = new FileChooser();

        //Set extension filter
        final FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("GPX files (*.gpx)", "*.gpx");
        fileChooser.getExtensionFilters().add(extFilter);

        //Show open file dialog
        final File file = fileChooser.showOpenDialog(null);
        loadService.setFile(file);
        loadService.restart();
    }

    @FXML
    public void showAboutDialog(ActionEvent actionEvent) {
        Alert dialog = new Alert(Alert.AlertType.INFORMATION);
        dialog.setTitle(resourceBundle.getString("dialog.title.about"));
        dialog.setHeaderText(MASTHEAD_TEXT);
        dialog.setContentText(ABOUT_TEXT);
        dialog.getDialogPane().setExpandableContent(new TextArea(LICENSE));
        dialog.showAndWait();
    }

    @FXML
    public void showSettings(ActionEvent actionEvent) {
        Settings settings = settingsService.getSettings();
        TextInputDialog dialog = new TextInputDialog(settings.getMyUsername());
        dialog.setTitle("Settings");
        dialog.setHeaderText("Settings");
        dialog.setContentText("Please enter your name:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> {
            settings.setMyUsername(name);
            settingsService.storeSettings(settings);
        });

    }

    @FXML
    public void newList(ActionEvent actionEvent) {
//        final Optional<String> listNameOption = Dialogs.
//                create().
//                title(resourceBundle.getString("dialog.title.new_list")).
//                message(resourceBundle.getString("dialog.label.listname")).
//                showTextInput();
//        if (hasValue(listNameOption)) {
//            final String listName = listNameOption.get().trim();
//            if (cacheService.doesCacheListNameExist(listName)) {
//                Dialogs.
//                        create().
//                        message(resourceBundle.getString("dialog.msg.list.does.exist")).
//                        showError();
//            } else {
//                CacheList list = new CacheList();
//                list.setName(listName);
//                cacheService.storeCacheList(list);
//                setCacheListInContext();
//            }
//        }
    }

    @FXML
    public void deleteList(ActionEvent actionEvent) {
//        final Optional<CacheList> listOption = Dialogs.
//                create().
//                title("dialog.title.delete_list").
//                message("dialog.label.listname").
//                showChoices(cacheService.getAllCacheLists());
//
//        if (listOption.isPresent()) {
//            cacheService.deleteCacheList(listOption.get());
//            setCacheListInContext();
//        }
    }

    @FXML
    public void exit(ActionEvent actionEvent) {
        Platform.exit();
    }

    private void updateStatus(String text, double progressValue) {
        Platform.runLater(() -> {
            leftStatus.setText(text);
            progress.setProgress(progressValue);
        });
    }


    private class LoadCachesFromFileService extends Service {

        private final ObjectProperty<File> file = new SimpleObjectProperty();

        public final void setFile(File value) {
            file.set(value);
        }

        public final File getFile() {
            return file.get();
        }

        public final ObjectProperty<File> fileProperty() {
            return file;
        }

        @Override
        protected Task createTask() {
            return new Task() {
                @Override
                protected Void call() throws Exception {
                    try {
                        LOGGER.info("load from file "+file.get().getAbsolutePath());
                        final List<Cache> cacheList = reader.load(file.get());
                        LOGGER.info(cacheList.size()+" caches loaded");
                        if (!cacheList.isEmpty()) {
                            updateStatus(resourceBundle.getString("status.store.all.caches"), ProgressIndicator.INDETERMINATE_PROGRESS);
                            cacheService.storeCaches(cacheList);
                            updateStatus(resourceBundle.getString("status.all.caches.stored"), 0);
                            LOGGER.info("caches stored in database");

                            loadAllCacheEntries();
                        }
                    } catch (IOException ex) {
                        LOGGER.log(Level.SEVERE, ex.getMessage() ,ex);
                    }
                    return null;
                }
            };
        }

    }

    private class LoadCacheListsFromDatabaseService extends Service {

        @Override
        protected Task createTask() {
            return new Task() {
                @Override
                protected Void call() throws Exception {
                    loadAllCacheEntries();
                    return null;
                }
            };
        }

    }

    private void loadAllCacheEntries() {
        updateStatus(resourceBundle.getString("status.load.caches.from.db"), ProgressIndicator.INDETERMINATE_PROGRESS);
        sessionContext.setData(CACHE_ENTRY_LIST, cacheService.getAllCacheEntriesSortBy("name", "asc"));
        updateStatus(resourceBundle.getString("status.all.caches.loaded"), 0);
    }
}
