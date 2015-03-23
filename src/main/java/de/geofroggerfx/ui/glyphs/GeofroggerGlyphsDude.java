package de.geofroggerfx.ui.glyphs;

import de.jensd.fx.glyphs.GlyphsDude;
import javafx.scene.text.Font;

/**
 * Created by Andreas on 23.03.2015.
 */
public class GeofroggerGlyphsDude extends GlyphsDude {

    static {
        Font.loadFont(GeofroggerGlyphsDude.class.getResource(ElusiveIcon.TTF_PATH).toExternalForm(), 10.0);
    }
}
