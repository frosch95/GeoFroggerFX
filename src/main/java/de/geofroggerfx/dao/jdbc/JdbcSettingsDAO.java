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

import de.geofroggerfx.dao.SettingsDAO;
import de.geofroggerfx.model.Settings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

import static de.geofroggerfx.dao.jdbc.JDBCUtils.*;

/**
 * Created by Andreas on 27.03.2015.
 */
@Repository
public class JdbcSettingsDAO implements SettingsDAO {

    private static final Logger LOGGER = Logger.getLogger("JdbcSettingsDAO");
    private static final String SETTINGS_TABLE = "settings";

    private String[] columnListSettings = new String[] {
            "username"};

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataConfig(DataConfig dataConfig) {
        this.jdbcTemplate = new JdbcTemplate(dataConfig.dataSource());
    }

    @Override
    public void save(Settings settings) {
        LOGGER.info("try to update settings");
        jdbcTemplate.update(generateUpdateSQL(SETTINGS_TABLE, columnListSettings), valuesFromSettings(settings));
        LOGGER.info("update settings done");
    }

    @Override
    public Settings getSettings() {
        return this.jdbcTemplate.queryForObject(
                generateSelectSQL(SETTINGS_TABLE, columnListSettings),
                (rs, rowNum) -> {
                    return getSettingsFromResultSet(rs);
                });
    }

    private Object[] valuesFromSettings(Settings settings) {
        return new Object[]{
                settings.getMyUsername() };
    }

    private Settings getSettingsFromResultSet(ResultSet rs) throws SQLException {
        Settings settings = new Settings();
        settings.setMyUsername(rs.getString("username"));
        return settings;
    }

}
