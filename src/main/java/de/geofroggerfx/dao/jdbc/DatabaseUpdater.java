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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Andreas on 23.03.2015.
 */
@Service
public class DatabaseUpdater {

    private static final Logger LOGGER = Logger.getLogger("DatabaseUpdater");
    private static final String UPDATE_FOLDER = "/de/geofroggerfx/dao/jdbc/updates/";
    private static final String SQL_EXTENSION = ".sql";
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataConfig(DataConfig dataConfig) {
        this.jdbcTemplate = new JdbcTemplate(dataConfig.dataSource());
    }

    public void update() {
        Integer version = 1;

        try {
            version = jdbcTemplate.queryForObject("SELECT VERSION FROM VERSION", Integer.class);
            version++;
        } catch (DataAccessException e) {
            LOGGER.info("no database version found");
        }

        try {
            URL url = getUpdateFileUrl(version);
            while(url != null) {
                executeSQL(version);
                version++;
                url = getUpdateFileUrl(version);
            }
        } catch (URISyntaxException | IOException e) {
            LOGGER.log(Level.SEVERE, "file error", e);
            throw new RuntimeException(e);
        }
    }

    private URL getUpdateFileUrl(Integer version) {
        return DatabaseUpdater.class.getResource(UPDATE_FOLDER+version+SQL_EXTENSION);
    }

    private void executeSQL(Integer version) throws URISyntaxException, IOException {

        List<String> lines = new ArrayList<>();

        try(InputStream in = DatabaseUpdater.class.getResourceAsStream(UPDATE_FOLDER + version + SQL_EXTENSION);
            Reader reader = new InputStreamReader(in, Charset.defaultCharset());
            BufferedReader bufferedReader = new BufferedReader(reader)) {

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }

            List<String> statements = extractStatements(lines);
            statements.forEach(jdbcTemplate::execute);
        }
    }

    private List<String> extractStatements(List<String> lines) {
        List<String> statements = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for (String line: lines) {
            sb.append(line.trim());
            if (line.indexOf(';') > -1) {
                statements.add(sb.toString());
                sb = new StringBuilder();
            }
        }
        return statements;
    }


}
