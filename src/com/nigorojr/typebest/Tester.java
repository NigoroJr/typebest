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

            LinkedHashMap<String, String> val = new LinkedHashMap<String, String>();
            val.put("USERNAME", "'the username was successfully changed'");
            rec.update(val, "WHERE USER_ID = 4");
            rec.delete("WHERE USER_ID = 5");

            System.out.println(rec.getAllRecords()[1].user_id
                    + rec.getAllRecords()[1].username);
            System.out.println(rec.getAllRecords()[0].user_id
                    + rec.getAllRecords()[0].username);

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
