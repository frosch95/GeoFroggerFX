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

import de.geofroggerfx.dao.UserDAO;
import de.geofroggerfx.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static de.geofroggerfx.dao.jdbc.JDBCUtils.addCommaToValue;
import static de.geofroggerfx.dao.jdbc.JDBCUtils.generateInsertSQL;
import static de.geofroggerfx.dao.jdbc.JDBCUtils.generateSelectSQL;

/**
 * Created by Andreas on 27.03.2015.
 */
@Repository
public class JdbcUserDAO implements UserDAO {

    private static final Logger LOGGER = Logger.getLogger("JdbcUserDAO");
    private static final String USER_TABLE = "user";

    private String[] columnListUser = new String[] {
            "id",
            "name"};

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataConfig(DataConfig dataConfig) {
        this.jdbcTemplate = new JdbcTemplate(dataConfig.dataSource());
    }


    @Override
    public void save(User user) {

    }

    @Override
    public void save(List<User> listOfUsers) {
        deleteExistingUsers(listOfUsers);
        batchInsertUsers(listOfUsers);
    }

    @Override
    public User getUser(Long id) {
        return this.jdbcTemplate.queryForObject(
                generateSelectSQL(USER_TABLE, columnListUser) + " WHERE id=?",
                new Object[]{id},
                (rs, rowNum) -> {
                    return getUserFromResultSet(rs);
                });
    }



    private void deleteExistingUsers(List<User> listOfUsers) {
        LOGGER.info("try to delete existing users");
        long startDelete = System.currentTimeMillis();
        StringBuffer sb = new StringBuffer();
        for (int current = 0; current < listOfUsers.size()-1; current++) {
            addCommaToValue(sb, Long.toString(listOfUsers.get(current).getId()));
        }
        sb.append(listOfUsers.get(listOfUsers.size()-1).getId());

        try {
            int result = jdbcTemplate.update("DELETE FROM "+USER_TABLE+" WHERE id IN ("+sb.toString()+")");
            LOGGER.info("deleted "+result+" caches");
        } catch (Throwable e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
        long endDelete = System.currentTimeMillis();
        LOGGER.info("try to delete existing users -> done in " + (endDelete - startDelete) + " ms");
    }

    private void batchInsertUsers(List<User> listOfCaches) {
        LOGGER.info("try to save " + listOfCaches.size() + " users");
        long startInsert = System.currentTimeMillis();

        List<Object[]> batch = listOfCaches.parallelStream().map(this::valuesFromUser).collect(Collectors.toList());

        try {
            String insertSQL = generateInsertSQL(USER_TABLE, columnListUser);
            int[] updateCounts = jdbcTemplate.batchUpdate(
                    insertSQL,
                    batch);
            int updatedRows = 0;
            for (int count:updateCounts) {
                updatedRows += count;
            }
            LOGGER.info("batch inserted "+updatedRows+" users");
        } catch (Throwable e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }

        long endInsert = System.currentTimeMillis();
        LOGGER.info("try to save " + listOfCaches.size() + " users -> done in " + (endInsert - startInsert) + " ms");
    }

    private Object[] valuesFromUser(User user) {
        return new Object[]{
                user.getId(),
                user.getName() };
    }

    private User getUserFromResultSet(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setName(rs.getString("name"));
        return user;
    }

}
