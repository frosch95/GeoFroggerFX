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

/**
 * Utility class to generate SQL statements
 */
public class JDBCUtils {

    public static String generateSelectSQL(String table, String[] columns, String... where) {

        assert columns != null && columns.length > 0 : "columns must not be null or empty";

        StringBuffer sql = new StringBuffer();
        sql.append("SELECT ");

        for (int columnCounter = 0; columnCounter < columns.length-1; columnCounter++) {
            addCommaToValue(sql, columns[columnCounter]);
        }
        sql.append(columns[columns.length - 1]);
        sql.append(" FROM ").append(table);

        if (where != null && where.length > 0) {
            sql.append(" WHERE ");
            for (int whereCounter = 0; whereCounter < where.length - 1; whereCounter++) {
                sql.append(where[whereCounter]).append(" AND ");
            }
            sql.append(where[where.length - 1]);
        }

        return sql.toString();
    }


    public static String generateUpdateSQL(String table, String[] columns, String... where) {

        assert columns != null && columns.length > 0 : "columns must not be null or empty";

        StringBuffer sql = new StringBuffer();
        sql.append("UPDATE ").append(table).append(" SET ");

        for (int columnCounter = 0; columnCounter < columns.length-1; columnCounter++) {
            addCommaToValue(sql, columns[columnCounter]+"=? ");
        }
        sql.append(columns[columns.length-1]+"=? ");

        if (where != null && where.length > 0) {
            sql.append(" WHERE ");
            for (int whereCounter = 0; whereCounter < where.length - 1; whereCounter++) {
                sql.append(where[whereCounter]).append(" AND ");
            }
            sql.append(where[where.length - 1]);
        }
        return sql.toString();
    }

    public static String generateInsertSQL(String table, String... columns) {

        assert columns != null && columns.length > 0 : "columns must not be null or empty";

        StringBuffer sql = new StringBuffer();
        sql.append("INSERT INTO ").append(table).append("(");

        for (int columnCounter = 0; columnCounter < columns.length-1; columnCounter++) {
            addCommaToValue(sql, columns[columnCounter]);
        }
        sql.append(columns[columns.length-1]);

        sql.append(") ");
        sql.append("values(");

        for (int columnCounter = 0; columnCounter < columns.length-1; columnCounter++) {
            addCommaToValue(sql, "?");
        }
        sql.append("?");
        sql.append(") ");

        return sql.toString();
    }

    public static String generateInsertSQL(String table, String[] columns, Object[] values) {

        assert columns != null && columns.length > 0 : "columns must not be null or empty";

        StringBuffer sql = new StringBuffer();
        sql.append("INSERT INTO ").append(table).append("(");

        for (int columnCounter = 0; columnCounter < columns.length-1; columnCounter++) {
            addCommaToValue(sql, columns[columnCounter]);
        }
        sql.append(columns[columns.length-1]);

        sql.append(") ");
        sql.append("values(");

        for (int valuesCounter = 0; valuesCounter < values.length-1; valuesCounter++) {
            addCommaToValue(sql, "'"+values[valuesCounter]+"'");
        }
        sql.append("'"+values[values.length-1]+"'");
        sql.append(") ");

        return sql.toString();
    }


    public static void addCommaToValue(StringBuffer sql, String value) {
        sql.append(value);
        sql.append(",");
    }

}
