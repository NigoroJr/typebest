package com.nigorojr.typebest;

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

    public Record(long user_id, String username, String keyboardLayout,
            long time, int miss) {
        this.user_id = user_id;
        this.username = username;
        this.keyboardLayout = keyboardLayout;
        this.time = time;
        this.miss = miss;
    }
}
