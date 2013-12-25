package com.nigorojr.typebest;

import java.sql.SQLException;
import java.util.LinkedHashMap;

public class Tester {

    public static void main(String[] args) {
        // This is to test the behavior of the Database class
        try {
            Records rec = new Records();
            rec.addNewRecord(5, "foooooobar", "Dvorak", 23456234, 34);

            // Test for Preferences class
            Preferences p = new Preferences("test_user");
            p.readPreferencesForID(1);
            System.out.println(p.getKeyboardLayout());
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
