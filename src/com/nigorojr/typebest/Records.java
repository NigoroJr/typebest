package com.nigorojr.typebest;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class Records extends Database {

    public Records() throws SQLException {
        super("RECORDS");
    }

    /**
     * Executes a INSERT query for the given pair of column names and values.
     */
    @Override
    public void insert(HashMap<String, String> pair) {
        String query = String.format("INSERT INTO %s %s",
                super.getTableName(), super.formatInsertQuery(pair));
        try {
            statement.execute(query);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    ResultSet select(String condition) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    void update(HashMap<String, String> pair) {
        // TODO Auto-generated method stub
    }

    @Override
    void delete(String condition) {
        // TODO Auto-generated method stub
    }
}
