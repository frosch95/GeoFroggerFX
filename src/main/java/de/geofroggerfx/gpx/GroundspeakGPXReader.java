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
package de.geofroggerfx.gpx;

import de.geofroggerfx.application.ProgressEvent;
import de.geofroggerfx.application.ProgressListener;
import de.geofroggerfx.model.*;
import org.springframework.stereotype.Service;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @author Andreas Billmann
 */
@Service
public class GroundspeakGPXReader implements GPXReader {

    private static final Logger LOGGER = Logger.getLogger(GroundspeakGPXReader.class.getName());

    public static final String LAT = "lat";
    public static final String LON = "lon";
    public static final String TIME = "time";
    public static final String NAME = "name";
    public static final String DESC = "desc";
    public static final String WPT = "wpt";
    public static final String URL = "url";
    public static final String URLNAME = "urlname";
    public static final String SYM = "sym";
    public static final String TYPE = "type";
    public static final String CACHE = "cache";
    public static final String GROUNDSPEAK = "groundspeak";
    public static final String ID = "id";
    public static final String AVAILABLE = "available";
    public static final String ARCHIVED = "archived";
    public static final String PLACED_BY = "placed_by";
    public static final String OWNER = "owner";
    public static final String CONTAINER = "container";
    public static final String ATTRIBUTES = "attributes";
    public static final String ATTRIBUTE = "attribute";
    public static final String INC = "inc";
    public static final String DIFFICULTY = "difficulty";
    public static final String TERRAIN = "terrain";
    public static final String COUNTRY = "country";
    public static final String STATE = "state";
    public static final String SHORT_DESCRIPTION = "short_description";
    public static final String HTML = "html";
    public static final String LONG_DESCRIPTION = "long_description";
    public static final String ENCODED_HINTS = "encoded_hints";
    public static final String LOGS = "logs";
    public static final String LOG = "log";
    public static final String DATE = "date";
    public static final String FINDER = "finder";
    public static final String ENCODED = "encoded";
    public static final String TRAVELBUGS = "travelbugs";
    public static final String TRAVELBUG = "travelbug";
    public static final String TEXT = "text";
    public static final String REF = "ref";

    // 2011-12-03T10:15:30+01:00
    private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

    private final List<ProgressListener> listeners = new ArrayList<>();

    private final Map<Long, User> userCache = new HashMap<>();


    @Override
    public void addListener(ProgressListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    @Override
    public List<Cache> load(File file) throws IOException {
        fireEvent(new ProgressEvent("GPX Reader",
                ProgressEvent.State.STARTED,
                "Load File " + file.getName() + " started."));
        List<Cache> modelList = new ArrayList<>();

        long start = System.currentTimeMillis();
        XMLInputFactory factory = XMLInputFactory.newInstance();
        try (FileInputStream fis = new FileInputStream(file)) {
            XMLStreamReader reader = factory.createXMLStreamReader(fis);

            Waypoint waypoint = null;
            Cache cache = null;
            String currentLevel = null;
            String startTag;
            StringBuffer tagContent = new StringBuffer();

            while(reader.hasNext()) {
                int event = reader.next();

                switch (event) {
                    case XMLStreamConstants.START_ELEMENT:

                        tagContent = new StringBuffer();
                        startTag = reader.getLocalName();
                        switch (startTag) {
                            case WPT:
                                currentLevel = WPT;
                                waypoint = new Waypoint();
                                waypoint.setLatitude(Double.parseDouble(reader.getAttributeValue(0)));
                                waypoint.setLongitude(Double.parseDouble(reader.getAttributeValue(1)));
                                break;
                            case CACHE:
                                currentLevel = CACHE;
                                cache = new Cache();
                                cache.setMainWayPoint(waypoint);
                                waypoint.setCache(cache);
                                cache.setId(Long.parseLong(reader.getAttributeValue(0)));
                                cache.setAvailable(Boolean.parseBoolean(reader.getAttributeValue(1)));
                                cache.setArchived(Boolean.parseBoolean(reader.getAttributeValue(2)));
                                modelList.add(cache);
                                break;
                            case LOG:
                                currentLevel = LOG;
                                break;
                            case ATTRIBUTE:
                                cache.getAttributes().add(
                                        Attribute.groundspeakAttributeToAttribute(
                                                Long.parseLong(reader.getAttributeValue(0)),
                                                Boolean.parseBoolean(reader.getAttributeValue(1))));
                                break;
                            case SHORT_DESCRIPTION:
                                cache.setShortDescriptionHtml(Boolean.parseBoolean(reader.getAttributeValue(0)));
                                break;
                            case LONG_DESCRIPTION:
                                cache.setLongDescriptionHtml(Boolean.parseBoolean(reader.getAttributeValue(0)));
                                break;
                        }

                        break;

                    case XMLStreamConstants.CHARACTERS:
                        tagContent.append(reader.getText().trim());
                        break;

                    case XMLStreamConstants.END_ELEMENT:
                        switch (reader.getLocalName()) {
                            case WPT:
                                currentLevel = null;
                                break;
                            case CACHE:
                                cache = null;
                                currentLevel = WPT;
                                break;
                            case LOG:
                                currentLevel = CACHE;
                                break;
                            case NAME:
                                if (WPT.equals(currentLevel)) {
                                    waypoint.setName(tagContent.toString());
                                } else if (CACHE.equals(currentLevel)) {
                                    cache.setName(tagContent.toString());
                                }
                                break;
                            case DESC:
                                if (WPT.equals(currentLevel)) {
                                    waypoint.setDescription(tagContent.toString());
                                }
                                break;
                            case URL:
                                waypoint.setUrl(tagContent.toString());
                                break;
                            case URLNAME:
                                waypoint.setUrlName(tagContent.toString());
                                break;
                            case SYM:
                                waypoint.setSymbol(tagContent.toString());
                                break;
                            case TYPE:
                                if (CACHE.equals(currentLevel)) {
                                    cache.setType(Type.groundspeakStringToType(tagContent.toString()));
                                }
                                break;
                            case PLACED_BY:
                                cache.setPlacedBy(tagContent.toString());
                                break;
                            case OWNER:
                                break;
                            case CONTAINER:
                                cache.setContainer(Container.groundspeakStringToContainer(tagContent.toString()));
                                break;
                            case ATTRIBUTES:
                                // do nothing
                                break;
                            case DIFFICULTY:
                                cache.setDifficulty(tagContent.toString());
                                break;
                            case TERRAIN:
                                cache.setTerrain(tagContent.toString());
                                break;
                            case COUNTRY:
                                cache.setCountry(tagContent.toString());
                                break;
                            case STATE:
                                cache.setState(tagContent.toString());
                                break;
                            case SHORT_DESCRIPTION:
                                cache.setShortDescription(tagContent.toString());
                                break;
                            case LONG_DESCRIPTION:
                                cache.setLongDescription(tagContent.toString());
                                break;
                            case ENCODED_HINTS:
                                cache.setEncodedHints(tagContent.toString());
                                break;
                        }
                        break;
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new IOException(e.getMessage(), e);
        }

        long end = System.currentTimeMillis();
        LOGGER.info(modelList.size()+" Caches geladen in "+(end-start)+"ms");

        fireEvent(new ProgressEvent("GPX Reader",
                ProgressEvent.State.FINISHED,
                "Load File " + file.getName() + " finished."));
        return modelList;
    }

    private void fireEvent(ProgressEvent event) {
        listeners.stream().forEach((l) -> {
            l.progress(event);
        });
    }
}
