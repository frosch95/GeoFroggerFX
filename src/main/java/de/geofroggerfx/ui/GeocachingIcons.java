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
import de.jensd.fx.glyphs.materialdesignicons.utils.MaterialDesignIconFactory;
import javafx.scene.text.Text;

import java.util.HashMap;
import java.util.Map;

import static de.geofroggerfx.model.Attribute.*;
import static de.geofroggerfx.model.Type.*;

import static de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon.*;

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
        attributeMap.put(DOGS_FALSE, PAW_OFF);
        attributeMap.put(ACCESS_OR_PARKING_FEE_TRUE, CASH_USD);
        attributeMap.put(ACCESS_OR_PARKING_FEE_FALSE, CASH_USD);
        // attributeMap.put(CLIMBING_GEAR_TRUE,);
        // attributeMap.put(CLIMBING_GEAR_FALSE,);
        attributeMap.put(BOAT_TRUE, FERRY);
        attributeMap.put(BOAT_FALSE, FERRY);
        attributeMap.put(SCUBA_GEAR_TRUE, LINUX);
        attributeMap.put(SCUBA_GEAR_FALSE, LINUX);
        attributeMap.put(RECOMMENDED_FOR_KIDS_TRUE, ODNOKLASSNIKI);
        attributeMap.put(RECOMMENDED_FOR_KIDS_FALSE, ODNOKLASSNIKI);
        //attributeMap.put(TAKES_LESS_THAN_AN_HOUR_TRUE, TIME_1);
        //attributeMap.put(TAKES_LESS_THAN_AN_HOUR_FALSE, TIME_1);
        attributeMap.put(SCENIC_VIEW_TRUE, EYE);
        attributeMap.put(SCENIC_VIEW_FALSE, EYE);
        attributeMap.put(SIGNIFICANT_HIKE_TRUE, TERRAIN);
        attributeMap.put(SIGNIFICANT_HIKE_FALSE, TERRAIN);
        attributeMap.put(DIFFICULT_CLIMBING_TRUE, HUMAN_HANDSUP);
        attributeMap.put(DIFFICULT_CLIMBING_FALSE, HUMAN_HANDSUP);
        attributeMap.put(MAY_REQUIRE_WADING_TRUE, POOL);
        attributeMap.put(MAY_REQUIRE_WADING_FALSE, POOL);
        attributeMap.put(MAY_REQUIRE_SWIMMING_TRUE, SWIM);
        attributeMap.put(MAY_REQUIRE_SWIMMING_FALSE, SWIM);
        attributeMap.put(AVAILABLE_AT_ALL_TIMES_TRUE, THEME_LIGHT_DARK);
        attributeMap.put(AVAILABLE_AT_ALL_TIMES_FALSE, THEME_LIGHT_DARK);
        attributeMap.put(RECOMMENDED_AT_NIGHT_TRUE, WEATHER_NIGHT);
        attributeMap.put(RECOMMENDED_AT_NIGHT_FALSE, WEATHER_NIGHT);
        attributeMap.put(AVAILABLE_DURING_WINTER_TRUE, WEATHER_SNOWY);
        attributeMap.put(AVAILABLE_DURING_WINTER_FALSE, WEATHER_SNOWY);
        attributeMap.put(POISON_PLANTS_TRUE, FLASK);
        attributeMap.put(POISON_PLANTS_FALSE, FLASK);
        attributeMap.put(DANGEROUS_ANIMALS_TRUE, GHOST);
        attributeMap.put(DANGEROUS_ANIMALS_FALSE, GHOST);
        attributeMap.put(TICKS_TRUE, BUG);
        attributeMap.put(TICKS_FALSE, BUG);
        //attributeMap.put(ABANDONED_MINES_TRUE, TOWER_FIRE);
        //attributeMap.put(ABANDONED_MINES_FALSE, TOWER_FIRE);
        attributeMap.put(CLIFF_FALLING_ROCKS_TRUE, METEOR);
        attributeMap.put(CLIFF_FALLING_ROCKS_FALSE, METEOR);
        attributeMap.put(HUNTING_TRUE, TARGET);
        attributeMap.put(HUNTING_FALSE, TARGET);
        attributeMap.put(DANGEROUS_AREA_TRUE, ALERT);
        attributeMap.put(DANGEROUS_AREA_FALSE, ALERT);
        attributeMap.put(WHEELCHAIR_ACCESSIBLE_TRUE, WHEELCHAIR_ACCESSIBILITY);
        attributeMap.put(WHEELCHAIR_ACCESSIBLE_FALSE, WHEELCHAIR_ACCESSIBILITY);
        attributeMap.put(PARKING_AVAILABLE_TRUE, PARKING);
        attributeMap.put(PARKING_AVAILABLE_FALSE, PARKING);
        attributeMap.put(PUBLIC_TRANSPORTATION_TRUE, BUS);
        attributeMap.put(PUBLIC_TRANSPORTATION_FALSE, BUS);
        attributeMap.put(DRINKING_WATER_NEARBY_TRUE, BEER);
        attributeMap.put(DRINKING_WATER_NEARBY_FALSE, BEER);
        attributeMap.put(PUBLIC_RESTROOMS_NEARBY_TRUE, HUMAN_MALE_FEMALE);
        attributeMap.put(PUBLIC_RESTROOMS_NEARBY_FALSE, HUMAN_MALE_FEMALE);
        attributeMap.put(TELEPHONE_NEARBY_TRUE, PHONE);
        attributeMap.put(TELEPHONE_NEARBY_FALSE, PHONE);
        attributeMap.put(PICNIC_TABLES_NEARBY_TRUE, FORMAT_STRIKETHROUGH);
        attributeMap.put(PICNIC_TABLES_NEARBY_FALSE, FORMAT_STRIKETHROUGH);
        attributeMap.put(CAMPING_AVAILABLE_TRUE, TENT);
        attributeMap.put(CAMPING_AVAILABLE_FALSE, TENT);
        attributeMap.put(BICYCLES_TRUE, BIKE);
        attributeMap.put(BICYCLES_FALSE, BIKE);
        attributeMap.put(MOTORCYCLES_TRUE, MOTORBIKE);
        attributeMap.put(MOTORCYCLES_FALSE, MOTORBIKE);
        // attributeMap.put(QUADS_TRUE(34);
        // attributeMap.put(QUADS_FALSE(-34);
        attributeMap.put(OFF_ROAD_VEHICLES_TRUE, JEEPNEY);
        attributeMap.put(OFF_ROAD_VEHICLES_FALSE, JEEPNEY);
        // attributeMap.put(SNOWMOBILES_TRUE(36);
        // attributeMap.put(SNOWMOBILES_FALSE(-36);
        // attributeMap.put(HORSES_TRUE(37);
        // attributeMap.put(HORSES_FALSE(-37);
        attributeMap.put(CAMPFIRES_TRUE, FIRE);
        attributeMap.put(CAMPFIRES_FALSE, FIRE);
        attributeMap.put(THORNS_TRUE, BARLEY);
        attributeMap.put(THORNS_FALSE, BARLEY);
        attributeMap.put(STEALTH_REQUIRED_TRUE, INCOGNITO);
        attributeMap.put(STEALTH_REQUIRED_FALSE, INCOGNITO);
        attributeMap.put(STROLLER_ACCESSIBLE_TRUE, BABY);
        attributeMap.put(STROLLER_ACCESSIBLE_FALSE, BABY);
        attributeMap.put(WATCH_FOR_LIVESTOCK_TRUE, COW);
        attributeMap.put(WATCH_FOR_LIVESTOCK_FALSE, COW);
        attributeMap.put(NEEDS_MAINTENANCE_TRUE, WRENCH);
        attributeMap.put(NEEDS_MAINTENANCE_FALSE, WRENCH);
        attributeMap.put(FLASHLIGHT_REQUIRED_TRUE, FLASHLIGHT);
        attributeMap.put(FLASHLIGHT_REQUIRED_FALSE, FLASHLIGHT);
        // attributeMap.put(LOST_AND_FOUND_TOUR_TRUE(45);
        // attributeMap.put(LOST_AND_FOUND_TOUR_FALSE(-45);
        attributeMap.put(TRUCK_DRIVER_RV_TRUE, TRUCK);
        attributeMap.put(TRUCK_DRIVER_RV_FALSE, TRUCK);
        attributeMap.put(FIELD_PUZZLE_TRUE, PUZZLE);
        attributeMap.put(FIELD_PUZZLE_FALSE, PUZZLE);
        attributeMap.put(UV_LIGHT_REQUIRED_TRUE, SPOTLIGHT_BEAM);
        attributeMap.put(UV_LIGHT_REQUIRED_FALSE, SPOTLIGHT_BEAM);
        // attributeMap.put(SNOWSHOES_TRUE(49);
        // attributeMap.put(SNOWSHOES_FALSE(-49);
        // attributeMap.put(CROSS_COUNTRY_SKIS_TRUE(50);
        // attributeMap.put(CROSS_COUNTRY_SKIS_FALSE(-50);
        attributeMap.put(SPECIAL_TOOL_REQUIRED_TRUE, SCREWDRIVER);
        attributeMap.put(SPECIAL_TOOL_REQUIRED_FALSE, SCREWDRIVER);
        attributeMap.put(NIGHT_CACHE_TRUE, BRIGHTNESS_3);
        attributeMap.put(NIGHT_CACHE_FALSE, BRIGHTNESS_3);
        attributeMap.put(PARK_AND_GRAB_TRUE, TIMER);
        attributeMap.put(PARK_AND_GRAB_FALSE, TIMER);
        attributeMap.put(ABANDONED_STRUCTURE_TRUE, FACTORY);
        attributeMap.put(ABANDONED_STRUCTURE_FALSE, FACTORY);
        attributeMap.put(SHORT_HIKE_LESS_THAN_1KM_TRUE, NUMERIC_1_BOX);
        attributeMap.put(SHORT_HIKE_LESS_THAN_1KM_FALSE, NUMERIC_1_BOX_OUTLINE);
        attributeMap.put(MEDIUM_HIKE_1KM_10KM_TRUE, NUMERIC_5_BOX);
        attributeMap.put(MEDIUM_HIKE_1KM_10KM_FALSE, NUMERIC_5_BOX_OUTLINE);
        attributeMap.put(LONG_HIKE_10KM_TRUE, NUMERIC_9_PLUS_BOX);
        attributeMap.put(LONG_HIKE_10KM_FALSE, NUMERIC_9_PLUS_BOX_OUTLINE);
        attributeMap.put(FUEL_NEARBY_TRUE, GAS_STATION);
        attributeMap.put(FUEL_NEARBY_FALSE, GAS_STATION);
        attributeMap.put(FOOD_NEARBY_TRUE, FOOD);
        attributeMap.put(FOOD_NEARBY_FALSE, FOOD);
        attributeMap.put(WIRELESS_BEACON_TRUE, ROUTER_WIRELESS);
        attributeMap.put(WIRELESS_BEACON_FALSE, ROUTER_WIRELESS);
        attributeMap.put(PARTNERSHIP_CACHE_TRUE, THUMB_UP);
        attributeMap.put(PARTNERSHIP_CACHE_FALSE, THUMB_UP);
        attributeMap.put(SEASONAL_ACCESS_TRUE, THERMOMETER);
        attributeMap.put(SEASONAL_ACCESS_FALSE, THERMOMETER);
        attributeMap.put(TOURIST_FRIENDLY_TRUE, WALLET_TRAVEL);
        attributeMap.put(TOURIST_FRIENDLY_FALSE, WALLET_TRAVEL);
        attributeMap.put(TREE_CLIMBING_TRUE, TREE);
        attributeMap.put(TREE_CLIMBING_FALSE, TREE);
        // attributeMap.put(FRONT_YARD_PRIVATE_RESIDENCE_TRUE(65);
        // attributeMap.put(FRONT_YARD_PRIVATE_RESIDENCE_FALSE(-65);
        attributeMap.put(TEAMWORK_REQUIRED_TRUE, ACCOUNT_MULTIPLE);
        attributeMap.put(TEAMWORK_REQUIRED_FALSE, ACCOUNT_MULTIPLE);
        attributeMap.put(GEOTOUR_TRUE, ROUTES);
        attributeMap.put(GEOTOUR_FALSE, ROUTES);
    }

    public static GlyphIcons getIcon(Attribute attribute) {
        GlyphIcons iconName = attributeMap.get(attribute);
        if (iconName == null) {
            iconName = SQUARE_INC;
        }
        return iconName;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // cache type
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private static final Map<Type, GlyphIcons> typeMap;

    static {
        typeMap = new HashMap<>();
        typeMap.put(MULTI_CACHE, TAG_MULTIPLE);
        typeMap.put(TRADITIONAL_CACHE, TAG);
        typeMap.put(UNKNOWN_CACHE, COMMENT_QUESTION_OUTLINE);
        typeMap.put(EARTH_CACHE, EARTH);
        typeMap.put(LETTERBOX, EMAIL);
        typeMap.put(EVENT, CALENDAR);
        typeMap.put(CITO_EVENT, CALENDAR);
        typeMap.put(MEGA_EVENT, CALENDAR);
        typeMap.put(WHERIGO, COMPASS);
        typeMap.put(WEBCAM_CACHE, CAMERA);
        typeMap.put(VIRTUAL_CACHE, LAPTOP);
    }

    public static Text getIconAsText(Type type, String iconSize) {
        return MaterialDesignIconFactory.get().createIcon(getIcon(type), iconSize);
    }


    public static String getIconAsString(Type type) {
        return getIcon(type).characterToString();
    }

    public static GlyphIcons getIcon(Type type) {
        GlyphIcons iconName = typeMap.get(type);
        if (iconName == null) {
            iconName = SQUARE_INC;
        }
        return iconName;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // star icon
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static Text getStars(String value) {
        return getStarsAsText(value, GlyphIcon.DEFAULT_FONT_SIZE);
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
                stringBuffer.append(' ');
                System.out.println(value);
        }
        return stringBuffer.toString();
    }

    public static Text getStarsAsText(String value, String iconSize) {
        Text text = MaterialDesignIconFactory.get().createIcon(STAR, iconSize);
        text.setText(value);
        return text;
    }

}
