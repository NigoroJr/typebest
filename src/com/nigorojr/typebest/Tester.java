package com.nigorojr.typebest;

import java.sql.SQLException;
import java.util.HashMap;

public class Tester {

    public static void main(String[] args) {
        // This is to test the behavior of the Database class
        try {
            Records rec = new Records();
            System.out.println("exist?\n" + rec.isTableExist());
            HashMap<String, String> pair = new HashMap<String, String>();
            pair.put("foo", "int");
            pair.put("hoge", "varchar(50)");
            if (!rec.isTableExist()) {
                rec.createTable(pair);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
