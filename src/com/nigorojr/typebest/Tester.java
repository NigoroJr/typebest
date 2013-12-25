package com.nigorojr.typebest;

import java.sql.SQLException;
import java.util.LinkedHashMap;

public class Tester {

    public static void main(String[] args) {
        // This is to test the behavior of the Database class
        try {
            Records rec = new Records();
            rec.addNewRecord(5, "foooooobar", "Dvorak", 23456234, 34);
            rec.addNewRecord(4, "hogehoge", "qwerty", 217934, 14);

            System.out.println(rec.getAllRecords()[1].username);
            System.out.println(rec.getAllRecords()[0].username);

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
