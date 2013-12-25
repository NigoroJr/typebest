package com.nigorojr.typebest;

import java.sql.SQLException;
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
}