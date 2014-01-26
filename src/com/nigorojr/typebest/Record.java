package com.nigorojr.typebest;

import java.sql.Timestamp;

/**
 * This class represents one result. It serves as a container when passing the
 * results to a method/class.
 */

public class Record {
    public long user_id;
    public String username;
    public String keyboardLayout;
    public long time;
    public int miss;
    public Timestamp date;

    /**
     * Creates a Record instance without the date information. Use this when
     * adding to database because the date is added automatically.
     * 
     * @param user_id
     *            The user ID.
     * @param username
     *            The username.
     * @param keyboardLayout
     *            The keyboard layout used.
     * @param time
     *            The time it took to complete round.
     * @param miss
     *            The total number of mistypes.
     */
    public Record(long user_id, String username, String keyboardLayout,
            long time, int miss) {
        this(user_id, username, keyboardLayout, time, miss, null);
    }

    public Record(long user_id, String username, String keyboardLayout,
            long time, int miss, Timestamp date) {
        this.user_id = user_id;
        this.username = username;
        this.keyboardLayout = keyboardLayout;
        this.time = time;
        this.miss = miss;
        this.date = date;
    }
}
