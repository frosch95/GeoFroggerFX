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

import de.geofroggerfx.model.Attribute;
import de.geofroggerfx.model.Type;
import de.jensd.fx.glyphs.GlyphIcon;
import de.jensd.fx.glyphs.GlyphIcons;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons;
import javafx.scene.text.Text;

import java.util.HashMap;
import java.util.Map;

import static de.geofroggerfx.model.Attribute.*;
import static de.geofroggerfx.model.Type.*;
import static de.geofroggerfx.ui.glyphs.GeofroggerGlyphsDude.createIcon;
import static de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons.*;
import static de.jensd.fx.glyphs.weathericons.WeatherIcons.*;

/**
 * @author Andreas
 */
public class GeocachingIcons {


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // cache attribute
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private static final Map<Attribute, GlyphIcons> attributeMap;

    static {
        attributeMap = new HashMap<>();
        attributeMap.put(DOGS_TRUE, PAW);
        attributeMap.put(DOGS_FALSE, PAW);
        // attributeMap.put(ACCESS_OR_PARKING_FEE_TRUE,);
        // attributeMap.put(ACCESS_OR_PARKING_FEE_FALSE, );
        // attributeMap.put(CLIMBING_GEAR_TRUE,);
        // attributeMap.put(CLIMBING_GEAR_FALSE,);
        attributeMap.put(BOAT_TRUE, SHIP);
        attributeMap.put(BOAT_FALSE, SHIP);
        // attributeMap.put(SCUBA_GEAR_TRUE(5);
        // attributeMap.put(SCUBA_GEAR_FALSE(-5);
        attributeMap.put(RECOMMENDED_FOR_KIDS_TRUE, CHILD);
        attributeMap.put(RECOMMENDED_FOR_KIDS_FALSE, CHILD);
        attributeMap.put(TAKES_LESS_THAN_AN_HOUR_TRUE, TIME_1);
        attributeMap.put(TAKES_LESS_THAN_AN_HOUR_FALSE, TIME_1);
        attributeMap.put(SCENIC_VIEW_TRUE, EYE);
        attributeMap.put(SCENIC_VIEW_FALSE, EYE);
        // attributeMap.put(SIGNIFICANT_HIKE_TRUE(9);
        // attributeMap.put(SIGNIFICANT_HIKE_FALSE(-9);
        // attributeMap.put(DIFFICULT_CLIMBING_TRUE(10);
        // attributeMap.put(DIFFICULT_CLIMBING_FALSE(-10);
        // attributeMap.put(MAY_REQUIRE_WADING_TRUE(11);
        // attributeMap.put(MAY_REQUIRE_WADING_FALSE(-11);
        // attributeMap.put(MAY_REQUIRE_SWIMMING_TRUE(12);
        // attributeMap.put(MAY_REQUIRE_SWIMMING_FALSE(-12);
        attributeMap.put(AVAILABLE_AT_ALL_TIMES_TRUE, TIME_12);
        attributeMap.put(AVAILABLE_AT_ALL_TIMES_FALSE, TIME_12);
        attributeMap.put(RECOMMENDED_AT_NIGHT_TRUE, MOON_ALT);
        attributeMap.put(RECOMMENDED_AT_NIGHT_FALSE, MOON_ALT);
        attributeMap.put(AVAILABLE_DURING_WINTER_TRUE, SNOW);
        attributeMap.put(AVAILABLE_DURING_WINTER_FALSE, SNOW);
        // attributeMap.put(POISON_PLANTS_TRUE(17);
        // attributeMap.put(POISON_PLANTS_FALSE(-17);
        // attributeMap.put(DANGEROUS_ANIMALS_TRUE(18);
        // attributeMap.put(DANGEROUS_ANIMALS_FALSE(-18);
        // attributeMap.put(TICKS_TRUE(19);
        // attributeMap.put(TICKS_FALSE(-19);
        // attributeMap.put(ABANDONED_MINES_TRUE(20);
        // attributeMap.put(ABANDONED_MINES_FALSE(-20);
        attributeMap.put(CLIFF_FALLING_ROCKS_TRUE, METEOR);
        attributeMap.put(CLIFF_FALLING_ROCKS_FALSE, METEOR);
        // attributeMap.put(HUNTING_TRUE(22);
        // attributeMap.put(HUNTING_FALSE(-22);
        // attributeMap.put(DANGEROUS_AREA_TRUE(23);
        // attributeMap.put(DANGEROUS_AREA_FALSE(-23);
        attributeMap.put(WHEELCHAIR_ACCESSIBLE_TRUE, WHEELCHAIR);
        attributeMap.put(WHEELCHAIR_ACCESSIBLE_FALSE, WHEELCHAIR);
        // attributeMap.put(PARKING_AVAILABLE_TRUE(25);
        // attributeMap.put(PARKING_AVAILABLE_FALSE(-25);
        attributeMap.put(PUBLIC_TRANSPORTATION_TRUE, BUS);
        attributeMap.put(PUBLIC_TRANSPORTATION_FALSE, BUS);
        // attributeMap.put(DRINKING_WATER_NEARBY_TRUE(27);
        // attributeMap.put(DRINKING_WATER_NEARBY_FALSE(-27);
        // attributeMap.put(PUBLIC_RESTROOMS_NEARBY_TRUE(28);
        // attributeMap.put(PUBLIC_RESTROOMS_NEARBY_FALSE(-28);
        attributeMap.put(TELEPHONE_NEARBY_TRUE, PHONE);
        attributeMap.put(TELEPHONE_NEARBY_FALSE, PHONE);
        // attributeMap.put(PICNIC_TABLES_NEARBY_TRUE(30);
        // attributeMap.put(PICNIC_TABLES_NEARBY_FALSE(-30);
        // attributeMap.put(CAMPING_AVAILABLE_TRUE(31);
        // attributeMap.put(CAMPING_AVAILABLE_FALSE(-31);
        attributeMap.put(BICYCLES_TRUE, BICYCLE);
        attributeMap.put(BICYCLES_FALSE, BICYCLE);
        attributeMap.put(MOTORCYCLES_TRUE, MOTORCYCLE);
        attributeMap.put(MOTORCYCLES_FALSE, MOTORCYCLE);
        // attributeMap.put(QUADS_TRUE(34);
        // attributeMap.put(QUADS_FALSE(-34);
        // attributeMap.put(OFF_ROAD_VEHICLES_TRUE(35);
        // attributeMap.put(OFF_ROAD_VEHICLES_FALSE(-35);
        // attributeMap.put(SNOWMOBILES_TRUE(36);
        // attributeMap.put(SNOWMOBILES_FALSE(-36);
        // attributeMap.put(HORSES_TRUE(37);
        // attributeMap.put(HORSES_FALSE(-37);
        // attributeMap.put(CAMPFIRES_TRUE(38);
        // attributeMap.put(CAMPFIRES_FALSE(-38);
        // attributeMap.put(THORNS_TRUE(39);
        // attributeMap.put(THORNS_FALSE(-39);
        // attributeMap.put(STEALTH_REQUIRED_TRUE(40);
        // attributeMap.put(STEALTH_REQUIRED_FALSE(-40);
        // attributeMap.put(STROLLER_ACCESSIBLE_TRUE(41);
        // attributeMap.put(STROLLER_ACCESSIBLE_FALSE(-41);
        // attributeMap.put(WATCH_FOR_LIVESTOCK_TRUE(43);
        // attributeMap.put(WATCH_FOR_LIVESTOCK_FALSE(-43);
        // attributeMap.put(NEEDS_MAINTENANCE_TRUE(42);
        // attributeMap.put(NEEDS_MAINTENANCE_FALSE(-42);
        // attributeMap.put(FLASHLIGHT_REQUIRED_TRUE(44);
        // attributeMap.put(FLASHLIGHT_REQUIRED_FALSE(-44);
        // attributeMap.put(LOST_AND_FOUND_TOUR_TRUE(45);
        // attributeMap.put(LOST_AND_FOUND_TOUR_FALSE(-45);
        // attributeMap.put(TRUCK_DRIVER_RV_TRUE(46);
        // attributeMap.put(TRUCK_DRIVER_RV_FALSE(-46);
        // attributeMap.put(FIELD_PUZZLE_TRUE(47);
        // attributeMap.put(FIELD_PUZZLE_FALSE(-47);
        // attributeMap.put(UV_LIGHT_REQUIRED_TRUE(48);
        // attributeMap.put(UV_LIGHT_REQUIRED_FALSE(-48);
        // attributeMap.put(SNOWSHOES_TRUE(49);
        // attributeMap.put(SNOWSHOES_FALSE(-49);
        // attributeMap.put(CROSS_COUNTRY_SKIS_TRUE(50);
        // attributeMap.put(CROSS_COUNTRY_SKIS_FALSE(-50);
        // attributeMap.put(SPECIAL_TOOL_REQUIRED_TRUE(51);
        // attributeMap.put(SPECIAL_TOOL_REQUIRED_FALSE(-51);
        attributeMap.put(NIGHT_CACHE_TRUE, MOON_WANING_CRESCENT_2);
        attributeMap.put(NIGHT_CACHE_FALSE, MOON_WANING_CRESCENT_2);
        // attributeMap.put(PARK_AND_GRAB_TRUE(53);
        // attributeMap.put(PARK_AND_GRAB_FALSE(-53);
        // attributeMap.put(ABANDONED_STRUCTURE_TRUE(54);
        // attributeMap.put(ABANDONED_STRUCTURE_FALSE(-54);
        // attributeMap.put(SHORT_HIKE_LESS_THAN_1KM_TRUE(55);
        // attributeMap.put(SHORT_HIKE_LESS_THAN_1KM_FALSE(-55);
        // attributeMap.put(MEDIUM_HIKE_1KM_10KM_TRUE(56);
        // attributeMap.put(MEDIUM_HIKE_1KM_10KM_FALSE(-56);
        // attributeMap.put(LONG_HIKE_10KM_TRUE(57);
        // attributeMap.put(LONG_HIKE_10KM_FALSE(-57);
        // attributeMap.put(FUEL_NEARBY_TRUE(58);
        // attributeMap.put(FUEL_NEARBY_FALSE(-58);
        // attributeMap.put(FOOD_NEARBY_TRUE(59);
        // attributeMap.put(FOOD_NEARBY_FALSE(-59);
        // attributeMap.put(WIRELESS_BEACON_TRUE(60);
        // attributeMap.put(WIRELESS_BEACON_FALSE(-60);
        // attributeMap.put(PARTNERSHIP_CACHE_TRUE(61);
        // attributeMap.put(PARTNERSHIP_CACHE_FALSE(-61);
        attributeMap.put(SEASONAL_ACCESS_TRUE, THERMOMETER);
        attributeMap.put(SEASONAL_ACCESS_FALSE, THERMOMETER);
        // attributeMap.put(TOURIST_FRIENDLY_TRUE(63);
        // attributeMap.put(TOURIST_FRIENDLY_FALSE(-63);
        // attributeMap.put(TREE_CLIMBING_TRUE(64);
        // attributeMap.put(TREE_CLIMBING_FALSE(-64);
        // attributeMap.put(FRONT_YARD_PRIVATE_RESIDENCE_TRUE(65);
        // attributeMap.put(FRONT_YARD_PRIVATE_RESIDENCE_FALSE(-65);
        // attributeMap.put(TEAMWORK_REQUIRED_TRUE(66);
        // attributeMap.put(TEAMWORK_REQUIRED_FALSE(-66);
        // attributeMap.put(GEOTOUR_TRUE(67);
        // attributeMap.put(GEOTOUR_FALSE(-67);
    }

    public static GlyphIcons getIcon(Attribute attribute) {
        GlyphIcons iconName = attributeMap.get(attribute);
        if (iconName == null) {
            iconName = FontAwesomeIcons.SQUARE_ALT;
        }
        return iconName;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // cache type
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private static final Map<Type, GlyphIcons> typeMap;

    static {
        typeMap = new HashMap<>();
        typeMap.put(MULTI_CACHE, TAGS);
        typeMap.put(TRADITIONAL_CACHE, TAG);
        typeMap.put(UNKNOWN_CACHE, QUESTION);
        typeMap.put(EARTH_CACHE, GLOBE);
        typeMap.put(LETTERBOX, ENVELOPE);
        typeMap.put(EVENT, CALENDAR);
        typeMap.put(CITO_EVENT, CALENDAR);
        typeMap.put(MEGA_EVENT, CALENDAR);
        typeMap.put(WHERIGO, COMPASS);
        typeMap.put(WEBCAM_CACHE, CAMERA);
        typeMap.put(VIRTUAL_CACHE, LAPTOP);
    }

    public static Text getIconAsText(Type type, String iconSize) {
        return createIcon(getIcon(type), iconSize);
    }

    public static Text getIconAsText(Type type) {
        return getIconAsText(type, GlyphIcon.DEFAULT_ICON_SIZE);
    }

    public static String getIconAsString(Type type) {
        return getIcon(type).characterToString();
    }

    public static GlyphIcons getIcon(Type type) {
        GlyphIcons iconName = typeMap.get(type);
        if (iconName == null) {
            iconName = FontAwesomeIcons.BLANK;
        }
        return iconName;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // star icon
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static Text getStars(String value) {
        return getStarsAsText(value, GlyphIcon.DEFAULT_ICON_SIZE);
    }

    public static String getStarsAsString(String value) {
        StringBuffer stringBuffer = new StringBuffer();
        switch (value) {
            case "1":
                stringBuffer.append(STAR.characterToString());
                break;

            case "1.5":
                stringBuffer.append(STAR.characterToString())
                        .append(STAR_HALF.characterToString());
                break;

            case "2":
                stringBuffer.append(STAR.characterToString())
                        .append(STAR.characterToString());
                break;

            case "2.5":
                stringBuffer.append(STAR.characterToString())
                        .append(STAR.characterToString())
                        .append(STAR_HALF.characterToString());
                break;

            case "3":
                stringBuffer.append(STAR.characterToString())
                        .append(STAR.characterToString())
                        .append(STAR.characterToString());
                break;

            case "3.5":
                stringBuffer.append(STAR.characterToString())
                        .append(STAR.characterToString())
                        .append(STAR.characterToString())
                        .append(STAR_HALF.characterToString());
                break;

            case "4":
                stringBuffer.append(STAR.characterToString())
                        .append(STAR.characterToString())
                        .append(STAR.characterToString())
                        .append(STAR.characterToString());
                break;

            case "4.5":
                stringBuffer.append(STAR.characterToString())
                        .append(STAR.characterToString())
                        .append(STAR.characterToString())
                        .append(STAR.characterToString())
                        .append(STAR_HALF.characterToString());
                break;

            case "5":
                stringBuffer.append(STAR.characterToString())
                        .append(STAR.characterToString())
                        .append(STAR.characterToString())
                        .append(STAR.characterToString())
                        .append(STAR.characterToString());
                break;

            default:
                stringBuffer.append(FontAwesomeIcons.BLANK.characterToString());
                System.out.println(value);
        }
        return stringBuffer.toString();
    }

    public static Text getStarsAsText(String value, String iconSize) {
        Text text = createIcon(FontAwesomeIcons.BLANK, iconSize);
        text.setText(value);
        return text;
    }

}
