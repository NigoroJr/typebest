package com.nigorojr.typebest;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Records extends Database {

    public static final String tableName = "RECORDS";

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
        LinkedHashMap<String, String> columnNamesAndValues = new LinkedHashMap<String, String>();
        columnNamesAndValues.put("USER_ID", Long.toString(id));
        columnNamesAndValues.put("USERNAME", String.format("'%s'", username));
        columnNamesAndValues.put("KEYBOARD_LAYOUT",
                String.format("'%s'", keyboardLayout));
        columnNamesAndValues.put("TIME", Long.toString(time));
        columnNamesAndValues.put("MISS_TYPES", Integer.toString(miss));
        super.insert(columnNamesAndValues);
    }

    /**
     * Retrieves all the records that are in the table.
     * 
     * @return All the records in the table. Each record is stored using Record
     *         class.
     */
    public Record[] getAllRecords() {
        ResultSet queryResult = super.select(new String[] { "*" });
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
                Record r = new Record();
                r.user_id = resultSet.getLong("USER_ID");
                r.username = resultSet.getString("USERNAME");
                r.keyboardLayout = resultSet.getString("KEYBOARD_LAYOUT");
                r.time = resultSet.getLong("TIME");
                r.miss = resultSet.getInt("MISS_TYPES");

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