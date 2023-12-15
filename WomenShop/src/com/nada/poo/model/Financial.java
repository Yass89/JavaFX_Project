package com.nada.poo.model;

import com.nada.poo.database.DatabaseUtil;

import java.sql.Connection;
import java.sql.SQLException;

public class Financial {
    public static double capital;
    public static double cost;

    public  static double income;

    static  {
        // get the data from the database
        try {
            Connection connection = DatabaseUtil.getConnection();
            String sql = "SELECT * FROM financials";
            java.sql.ResultSet resultSet = connection.createStatement().executeQuery(sql);
            if (resultSet.next()) {
                capital = resultSet.getDouble("capital");
                cost = resultSet.getDouble("total_cost");
                income = resultSet.getDouble("total_income");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
