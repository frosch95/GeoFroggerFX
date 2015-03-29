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
package de.geofroggerfx.dao.jdbc;

import de.geofroggerfx.dao.CacheDAO;
import de.geofroggerfx.dao.UserDAO;
import de.geofroggerfx.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.w3c.dom.Attr;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static de.geofroggerfx.dao.jdbc.JDBCUtils.*;
import static de.geofroggerfx.model.Container.groundspeakStringToContainer;
import static de.geofroggerfx.model.Type.groundspeakStringToType;

/**
 * Created by Andreas on 11.03.2015.
 */
@Repository
public class JdbcCacheDAO implements CacheDAO {

    private static final Logger LOGGER = Logger.getLogger("JdbcCacheDAO");
    private static final String CACHE_TABLE = "cache";
    private static final String WAYPOINT_TABLE = "waypoint";
    private static final String ATTRIBUTE_TABLE = "attribute";
    private static final String LOG_TABLE = "log";

    private String[] columnListCache = new String[] {
            "id",
            "name",
            "placed_by",
            "available",
            "archived",
            "found",
            "difficulty",
            "terrain",
            "country",
            "state",
            "short_description",
            "short_description_html",
            "long_description",
            "long_description_html",
            "encoded_hints",
            "container",
            "type",
            "user_id"};

    private String[] columnListWaypoint = new String[] {
            "name",
            "cache_id",
            "latitude",
            "longitude",
            "time",
            "description",
            "url",
            "urlName",
            "symbol",
            "type"};

    private String[] columnListAttribute = new String[] {
            "id",
            "cache_id"};

    private String[] columnListLog = new String[] {
            "id",
            "cache_id",
            "date",
            "user_id",
            "type",
            "text"};

    @Autowired
    private UserDAO userDAO;

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataConfig(DataConfig dataConfig) {
        this.jdbcTemplate = new JdbcTemplate(dataConfig.dataSource());
    }

    @Override
    public void save(List<Cache> listOfCaches) {
        deleteExistingCaches(listOfCaches);
        batchInsertCaches(listOfCaches);
        batchInsertAttributes(listOfCaches);
        batchInsertWaypoints(listOfCaches);
        batchInsertLogs(listOfCaches);
    }

    @Override
    public void update(Cache cache) {
        String updateSQL = generateUpdateSQL(CACHE_TABLE, columnListCache, "ID = ?");
        jdbcTemplate.update(updateSQL, valuesFromCache(cache, cache.getId()));
        updateSQL = generateUpdateSQL(WAYPOINT_TABLE, columnListWaypoint, "name = ?");
        jdbcTemplate.update(updateSQL, valuesFromWaypoint(cache, cache.getMainWayPoint().getName()));
    }

    @Override
    public List<CacheListEntry> getAllCacheEntriesSortBy(String name, String asc) {
        return this.jdbcTemplate.query(
                "SELECT c.id, c.name AS name, c.name AS code, c.difficulty, c.terrain, c.type FROM " + CACHE_TABLE + " c ORDER BY " + name + " " + asc,
                (rs, rowNum) -> {
                    return new CacheListEntry(
                            rs.getLong("id"),
                            rs.getString("name"),
                            rs.getString("code"),
                            rs.getString("difficulty"),
                            rs.getString("terrain"),
                            groundspeakStringToType(rs.getString("type")));
                });
    }

    @Override
    public Cache getCacheForId(long id) {
        return this.jdbcTemplate.queryForObject(
                generateSelectSQL(CACHE_TABLE, columnListCache) + " WHERE id=?",
                new Object[]{id},
                (rs, rowNum) -> {
                    Cache cache = getCacheFromResultSet(rs);
                    Waypoint waypoint = getWaypointForCacheId(cache.getId());
                    cache.setMainWayPoint(waypoint);
                    waypoint.setCache(cache);
                    List<Attribute> attributes = getAttributesForCacheId(cache.getId());
                    cache.getAttributes().setAll(attributes);
                    List<Log> logs = getLogsForCacheId(cache.getId());
                    cache.getLogs().setAll(logs);
                    return cache;
                });
    }

    @Override
    public List<Cache> getAllCaches() {
        return this.jdbcTemplate.query(
                generateSelectSQL(CACHE_TABLE, columnListCache),
                (rs, rowNum) -> {
                    return getCacheFromResultSet(rs);
                });
    }

    @Override
    public List<Cache> getAllCaches(String... where) {
        return this.jdbcTemplate.query(
                generateSelectSQL(CACHE_TABLE, columnListCache, where),
                (rs, rowNum) -> {
                    return getCacheFromResultSet(rs);
                });
    }


    private void batchInsertCaches(List<Cache> listOfCaches) {
        LOGGER.info("try to save " + listOfCaches.size() + " caches");
        long startInsert = System.currentTimeMillis();

        List<Object[]> batch = listOfCaches.parallelStream().map(this::valuesFromCache).collect(Collectors.toList());

        try {
            String insertSQL = generateInsertSQL(CACHE_TABLE, columnListCache);
            int[] updateCounts = jdbcTemplate.batchUpdate(
                    insertSQL,
                    batch);
            int updatedRows = 0;
            for (int count:updateCounts) {
                updatedRows += count;
            }
            LOGGER.info("batch inserted "+updatedRows+" caches");
        } catch (Throwable e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }

        long endInsert = System.currentTimeMillis();
        LOGGER.info("try to save " + listOfCaches.size() + " caches -> done in " + (endInsert - startInsert) + " ms");
    }

    private void deleteExistingCaches(List<Cache> listOfCaches) {
        LOGGER.info("try to delete existing caches");
        long startDelete = System.currentTimeMillis();
        StringBuffer sb = new StringBuffer();
        for (int current = 0; current < listOfCaches.size()-1; current++) {
            addCommaToValue(sb, Long.toString(listOfCaches.get(current).getId()));
        }
        sb.append(listOfCaches.get(listOfCaches.size()-1).getId());

        try {
            int result = jdbcTemplate.update("DELETE FROM "+CACHE_TABLE+" WHERE id IN ("+sb.toString()+")");
            LOGGER.info("deleted "+result+" caches");
        } catch (Throwable e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
        long endDelete = System.currentTimeMillis();
        LOGGER.info("try to delete existing caches -> done in " + (endDelete - startDelete) + " ms");
    }


    private void batchInsertWaypoints(List<Cache> listOfCaches) {
        LOGGER.info("try to save "+listOfCaches.size()+" waypoints");
        long startInsert = System.currentTimeMillis();

        List<Object[]> batch = listOfCaches.parallelStream().map(this::valuesFromWaypoint).collect(Collectors.toList());

        try {
            String insertSQL = generateInsertSQL(WAYPOINT_TABLE, columnListWaypoint);
            int[] updateCounts = jdbcTemplate.batchUpdate(
                    insertSQL,
                    batch);
            int updatedRows = 0;
            for (int count:updateCounts) {
                updatedRows += count;
            }
            LOGGER.info("batch inserted "+updatedRows+" waypoints");
        } catch (Throwable e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }

        long endInsert = System.currentTimeMillis();
        LOGGER.info("try to save "+listOfCaches.size()+" waypoints -> done in "+(endInsert-startInsert)+" ms");
    }

    private void batchInsertAttributes(List<Cache> listOfCaches) {
        long startInsert = System.currentTimeMillis();

        List<Object[]> batch = listOfCaches.
                parallelStream().
                flatMap(cache -> cache.getAttributes().stream().map(attribute -> valuesFromAttribute(cache, attribute))).
                collect(Collectors.toList());
        LOGGER.info("try to save "+batch.size()+" attributes");

        try {
            String insertSQL = generateInsertSQL(ATTRIBUTE_TABLE, columnListAttribute);
            int[] updateCounts = jdbcTemplate.batchUpdate(
                    insertSQL,
                    batch);
            int updatedRows = 0;
            for (int count:updateCounts) {
                updatedRows += count;
            }
            LOGGER.info("batch inserted "+updatedRows+" attributes");
        } catch (Throwable e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }

        long endInsert = System.currentTimeMillis();
        LOGGER.info("try to save "+batch.size()+" attributes -> done in "+(endInsert-startInsert)+" ms");
    }

    private void batchInsertLogs(List<Cache> listOfCaches) {
        long startInsert = System.currentTimeMillis();

        List<Object[]> batch = listOfCaches.
                parallelStream().
                flatMap(cache -> cache.getLogs().stream().map(log -> valuesFromLog(cache, log))).
                collect(Collectors.toList());
        LOGGER.info("try to save "+batch.size()+" logs");

        try {
            String insertSQL = generateInsertSQL(LOG_TABLE, columnListLog);
            int[] updateCounts = jdbcTemplate.batchUpdate(
                    insertSQL,
                    batch);
            int updatedRows = 0;
            for (int count:updateCounts) {
                updatedRows += count;
            }
            LOGGER.info("batch inserted "+updatedRows+" logs");
        } catch (Throwable e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }

        long endInsert = System.currentTimeMillis();
        LOGGER.info("try to save "+batch.size()+" logs -> done in "+(endInsert-startInsert)+" ms");
    }

    private Waypoint getWaypointForCacheId(long cacheId) {
        return this.jdbcTemplate.queryForObject(
                generateSelectSQL(WAYPOINT_TABLE, columnListWaypoint) + " WHERE cache_id=?",
                new Object[]{cacheId},
                (rs, rowNum) -> {
                    return getWaypointFromResultSet(rs);
                });

    }

    private List<Attribute> getAttributesForCacheId(long cacheId) {
        return this.jdbcTemplate.query(
                generateSelectSQL(ATTRIBUTE_TABLE, columnListAttribute) + " WHERE cache_id=?",
                new Object[]{cacheId},
                (rs, rowNum) -> {
                    return getAttributeFromResultSet(rs);
                });

    }

    private List<Log> getLogsForCacheId(long cacheId) {
        return this.jdbcTemplate.query(
                generateSelectSQL(LOG_TABLE, columnListLog) + " WHERE cache_id=?",
                new Object[]{cacheId},
                (rs, rowNum) -> {
                    return getLogFromResultSet(rs);
                });

    }

    private Cache getCacheFromResultSet(ResultSet rs) throws SQLException {
        Cache cache = new Cache();
        cache.setId(rs.getLong("id"));
        cache.setName(rs.getString("name"));
        cache.setDifficulty(rs.getString("difficulty"));
        cache.setTerrain(rs.getString("terrain"));
        cache.setPlacedBy(rs.getString("placed_by"));
        cache.setAvailable(rs.getBoolean("available"));
        cache.setArchived(rs.getBoolean("archived"));
        cache.setFound(rs.getBoolean("found"));
        cache.setCountry(rs.getString("country"));
        cache.setState(rs.getString("state"));
        cache.setShortDescription(rs.getString("short_description"));
        cache.setLongDescription(rs.getString("long_description"));
        cache.setEncodedHints(rs.getString("encoded_hints"));
        cache.setShortDescriptionHtml(rs.getBoolean("short_description_html"));
        cache.setLongDescriptionHtml(rs.getBoolean("long_description_html"));
        cache.setContainer(groundspeakStringToContainer(rs.getString("container")));
        cache.setType(groundspeakStringToType(rs.getString("type")));
        cache.setOwner(userDAO.getUser(rs.getLong("user_id")));
        return cache;
    }

    private Object[] valuesFromCache(Cache cache) {
        return new Object[]{
                cache.getId(),
                cache.getName(),
                cache.getPlacedBy(),
                cache.getAvailable(),
                cache.getArchived(),
                cache.getFound(),
                cache.getDifficulty(),
                cache.getTerrain(),
                cache.getCountry(),
                cache.getState(),
                cache.getShortDescription(),
                cache.getShortDescriptionHtml(),
                cache.getLongDescription(),
                cache.getLongDescriptionHtml(),
                cache.getEncodedHints(),
                cache.getContainer().toGroundspeakString(),
                cache.getType().toGroundspeakString(),
                cache.getOwner().getId() };
    }

    private Waypoint getWaypointFromResultSet(ResultSet rs) throws SQLException {
        Waypoint waypoint = new Waypoint();
        waypoint.setName(rs.getString("name"));
        waypoint.setLatitude(rs.getDouble("latitude"));
        waypoint.setLongitude(rs.getDouble("longitude"));
        waypoint.setTime(rs.getTimestamp("time"));
        waypoint.setDescription(rs.getString("description"));
        waypoint.setUrl(rs.getString("url"));
        waypoint.setUrlName(rs.getString("urlName"));
        waypoint.setSymbol(rs.getString("symbol"));
//        waypoint.setType(WaypointType.rs.getString("description"));
        return waypoint;
    }

    private Attribute getAttributeFromResultSet(ResultSet rs) throws SQLException {
        Attribute attribute = Attribute.getAttributeById(rs.getInt("id"));
        return attribute;
    }

    private Log getLogFromResultSet(ResultSet rs) throws SQLException {
        Log log = new Log();
        log.setId(rs.getLong("id"));
        log.setDate(rs.getDate("date"));
        log.setText(rs.getString("text"));
        log.setType(LogType.groundspeakStringToType(rs.getString("type")));
        log.setFinder(userDAO.getUser(rs.getLong("user_id")));
        return log;
    }


    private Object[] valuesFromWaypoint(Cache cache) {
        return new Object[]{
                cache.getMainWayPoint().getName(),
                cache.getId(),
                cache.getMainWayPoint().getLatitude(),
                cache.getMainWayPoint().getLongitude(),
                cache.getMainWayPoint().getTime(),
                cache.getMainWayPoint().getDescription(),
                cache.getMainWayPoint().getUrl(),
                cache.getMainWayPoint().getUrlName(),
                cache.getMainWayPoint().getSymbol(),
                null
        };
    }

    private Object[] valuesFromAttribute(Cache cache, Attribute attribute) {
        return new Object[]{
                attribute.getId(),
                cache.getId()
        };
    }

    private Object[] valuesFromLog(Cache cache, Log log) {
        return new Object[]{
                log.getId(),
                cache.getId(),
                log.getDate(),
                log.getFinder().getId(),
                log.getType().toGroundspeakString(),
                log.getText()
        };
    }

    private Object[] valuesFromCache(Cache cache, long id) {
        return Arrays.asList(valuesFromCache(cache), id).toArray();
    }

    private Object[] valuesFromWaypoint(Cache cache, String name) {
        return Arrays.asList(valuesFromWaypoint(cache), name).toArray();
    }

}
