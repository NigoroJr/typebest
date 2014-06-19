package com.nigorojr.typebest;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Records extends Database {

    public static final String tableName = "RECORDS";

    @SuppressWarnings("serial")
    private static LinkedHashMap<String, String> columnNamesAndTypes = new LinkedHashMap<String, String>() {
        {
            put("RECORD_ID",
                    "INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)");
            put("USER_ID", "BIGINT NOT NULL");
            put("USERNAME", "VARCHAR(100) NOT NULL");
            put("KEYBOARD_LAYOUT", "VARCHAR(100)");
            put("TIME", "BIGINT");
            put("MISS_TYPES", "INT");
            put("DATE", "TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP");
        }
    };

    public Records() throws SQLException {
        super(tableName, columnNamesAndTypes, "RECORD_ID");
    }

    /**
     * Adds a new record to the database.
     * 
     * @param id
     *            The user ID determined by the PREFERENCES table. This is not
     *            used as a unique key for this RECORDS table.
     * @param username
     *            The username used when the record was achieved.
     * @param keyboardLayout
     *            The keyboard layout that was used when the record was
     *            achieved.
     * @param time
     *            The time it took to finish in nanoseconds.
     * @param miss
     *            The number of incorrect keystrokes.
     */
    public void addNewRecord(long id, String username, String keyboardLayout,
            long time, int miss) {
        String[] values = {
                Long.toString(id),
                String.format("'%s'", username),
                String.format("'%s'", keyboardLayout),
                Long.toString(time),
                Integer.toString(miss),
        };

        @SuppressWarnings("unchecked")
        LinkedHashMap<String, String> columnNames =
                (LinkedHashMap<String, String>) columnNamesAndTypes.clone();
        columnNames.remove("RECORD_ID");

        LinkedHashMap<String, String> columnNamesAndValues =
                super.createLinkedHashMap(columnNames, values);

        super.insert(columnNamesAndValues);
    }

    /**
     * Adds a new record to the table for the given Record.
     * 
     * @param record
     *            A Record instance containing the necessary information such as
     *            the username, time, number of misses etc.
     */
    public void addNewRecord(Record record) {
        addNewRecord(record.user_id, record.username, record.keyboardLayout,
                record.time, record.miss);
    }

    /**
     * Retrieves all the records that are in the table. The records are sorted
     * from the best record in terms of time. If there is more than one record
     * with the same time, the number of miss-types are compared next.
     * 
     * @return All the records in the table. Each record is stored using Record
     *         class.
     */
    public Record[] getAllRecords() {
        ResultSet queryResult = super.select("*", "ORDER BY TIME, MISS_TYPES");
        return resultSetToRecordArray(queryResult);
    }

    /**
     * Returns the Record object from the given user ID, username, layout, time,
     * and miss. This method is used to get the record that has the date.
     * 
     * @param id
     *            The user ID.
     * @param username
     * @param layout
     *            The keyboard layout.
     * @param time
     *            Time it took to complete the round.
     * @param miss
     *            Number of mistypes.
     * @return The Record object that has the date.
     */
    public Record getRecordWith(
            long id,
            String username,
            String layout,
            long time,
            int miss) {
        String cond = String
                .format("WHERE USER_ID = %s AND USERNAME = '%s' AND KEYBOARD_LAYOUT = '%s' AND TIME = %s AND MISS_TYPES = %s",
                        id, username, layout, time, miss);
        ResultSet queryResult = super.select("DATE", cond);
        Timestamp date = null;
        try {
            if (queryResult.next())
                date = queryResult.getTimestamp("DATE");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return new Record(id, username, layout, time, miss, date);
    }

    /**
     * Retrieves the top n records in the table. The records are sorted from the
     * shortest time and if more than one record has the same time, the number
     * of miss-types is compared.
     * 
     * @param n
     *            The number of records that will be retrieved. For example, if
     *            n is 5, the top five records will be returned.
     * @return An array with length n containing the results of the query.
     */
    public Record[] getTopRecords(int n) {
        ResultSet queryResult =
                super.select("*", "ORDER BY TIME, MISS_TYPES", n);
        return resultSetToRecordArray(queryResult);
    }

    /**
     * Converts the given ResultSet object to an array of Record, which is a
     * collection of information such as the time, miss-types, username etc.
     * 
     * @param resultSet
     *            A ResultSet object containing the result of a query.
     * @return An array of Record converted from the ResultSet.
     */
    private Record[] resultSetToRecordArray(ResultSet resultSet) {
        ArrayList<Record> records = new ArrayList<Record>();
        try {
            while (resultSet.next()) {
                Record r = new Record(
                        resultSet.getLong("USER_ID"),
                        resultSet.getString("USERNAME"),
                        resultSet.getString("KEYBOARD_LAYOUT"),
                        resultSet.getLong("TIME"),
                        resultSet.getInt("MISS_TYPES"),
                        resultSet.getTimestamp("DATE"));

                records.add(r);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        Record[] ret = new Record[records.size()];
        return records.toArray(ret);
    }
}