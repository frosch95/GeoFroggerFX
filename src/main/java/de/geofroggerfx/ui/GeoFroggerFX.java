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

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;
import javafx.stage.Stage;
//import org.scenicview.ScenicView;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@ComponentScan("de.geofroggerfx")
@PropertySource(value = "classpath:/de/geofroggerfx/ui/application.properties")
public class GeoFroggerFX extends Application {

    private AnnotationConfigApplicationContext appContext;

    @Override
    public void start(Stage primaryStage) throws Exception {

        loadCustomFonts();

        appContext = new AnnotationConfigApplicationContext(GeoFroggerFX.class);
        String name = appContext.getEnvironment().getProperty("application.name");
        String version = appContext.getEnvironment().getProperty("application.version");
        GeoFroggerFXController geoFroggerFXController = appContext.getBean(GeoFroggerFXController.class);

        Scene scene = new Scene((Parent) geoFroggerFXController.getView());

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {
                if (isScenicViewShortcutPressed(event)) {
//                    ScenicView.show(scene);
                }
            }
        });
        primaryStage.setScene(scene);
        primaryStage.setTitle(String.format("%s %s", name, version));
        primaryStage.show();
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Override
    public void stop() throws Exception {
        if (appContext != null) {
            appContext.close();
        }

        super.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private static boolean isScenicViewShortcutPressed(final KeyEvent keyEvent) {
        return keyEvent.isAltDown() && keyEvent.isControlDown() && keyEvent.getCode().equals(KeyCode.V);
    }

    private void loadCustomFonts() {
        Font.loadFont(GeoFroggerFX.class.getResource("/fonts/sofia/Sofia-Regular.otf").toExternalForm(), 24);
    }
}
