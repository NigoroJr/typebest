package com.nigorojr.typebest;

import java.awt.Color;
import java.awt.Font;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;

public class Preferences extends Database {
    private long id;
    private String username;
    private String keyboardLayout;
    private Color toBeTyped;
    private Color alreadyTyped;
    private Color backgroundColor;
    private Color missTypeColor;
    private Font font;
    // The number of digits to show after decimal point
    private int speedFractionDigit;
    private int timeFractionDigit;

    // Force shuffle for now
    // private boolean shuffled;

    private static LinkedHashMap<String, String> columnNamesAndTypes = new LinkedHashMap<String, String>() {
        {
            put("ID", "INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)");
            put("USERNAME", "VARCHAR(100) NOT NULL");
            put("KEYBOARD_LAYOUT", "VARCHAR(100)");
            put("TO_BE_TYPED", "INT");
            put("ALREADY_TYPED", "INT");
            put("MISS_TYPE_COLOR", "INT");
            put("BACKGROUND_COLOR", "INT");
            put("FONT_FAMILY", "VARCHAR(30)");
            put("FONT_STYLE", "SMALLINT");
            put("FONT_SIZE", "SMALLINT");
            put("SPEED_FRACTION_DIGIT", "SMALLINT");
            put("TIME_FRACTION_DIGIT", "SMALLINT");
            put("CONSTRAINT primary_key", "PRIMARY KEY (id)");
        }
    };

    final String defaultUsername = "Unknown Username";

    public static final String tableName = "PREFERENCES";

    /**
     * Attempts to retrieve a set of preference from the given ID. If the ID
     * does not match, it'll create a new set of preference with the default
     * username.
     * 
     * @param id
     *            The user ID that will be used to retrieve the preference set
     *            for that user.
     * @throws SQLException
     *             When it failed to establish connection with the database
     *             file.
     */
    public Preferences(long id) throws SQLException {
        super(tableName, columnNamesAndTypes);
        if (!isIDExist(id)) {
            addPreferencesForUser(defaultUsername);
        }
    }

    /**
     * Creates a new set of preference using the given username. Note that the
     * String parameter is given only when a new set of preferences are to be
     * created. Also, the ID will be determined automatically by the database
     * manager.
     * 
     * @param username
     *            The username that the user chose.
     * @throws SQLException
     *             When it failed to establish a connection with the database
     *             file. Note that it has nothing to do with the table name.
     */
    public Preferences(String username) throws SQLException {
        super(tableName, columnNamesAndTypes);
        addPreferencesForUser(username);
    }

    /**
     * Queries the user preferences database to see if there are any preferences
     * for that user ID. Note that this method also updates the variables if
     * there is at least one match for that ID.
     * 
     * @param id
     *            The user ID that will be the identifier of the user.
     * @return True if there is at least one preference for the given ID, false
     *         if there is none. Note that there should be only one preference
     *         for each ID, however, no check is done in this method to ensure
     *         if that is the case.
     */
    public boolean isIDExist(long id) {
        String condition = String.format("ID = %d", id);
        String[] selectColumns = { "*" };
        ResultSet result = super.select(selectColumns, condition);
        boolean ret = false;
        try {
            ret = result.next();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public void createPreference(String username) {
        init();
        // TODO: insert into prefs (username, blah blah) values foo bar;
    }

    /**
     * Initializes all the variables to the default value.
     */
    public void init() {
        keyboardLayout = "QWERTY";
        toBeTyped = Color.BLUE;
        alreadyTyped = Color.RED;
        backgroundColor = Color.GRAY;
        missTypeColor = Color.CYAN;
        font = new Font("Arial", Font.PLAIN, 30);
        // The number of digits to show after decimal point
        speedFractionDigit = 8;
        timeFractionDigit = 9;
    }
}
