package com.nada.poo.dao;

import com.nada.poo.database.DatabaseUtil;
import com.nada.poo.model.Financial;

import java.sql.Connection;
import java.sql.ResultSet;

public class FinancialDaoImpl {

    static {
        // verify if the financials table is empty
        try {
            String sql = "SELECT * FROM financials";
            Connection connection = DatabaseUtil.getConnection();
            ResultSet resultSet = connection.createStatement().executeQuery(sql);
            if (!resultSet.next()) {
                sql = "INSERT INTO financials (capital, total_income, total_cost) VALUES (" + Financial.capital + "," + Financial.income + ","
                        + Financial.cost + ")";
                DatabaseUtil.executeUpdate(sql);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // update capital
    public static void updateCapitalDatabase(){
        DatabaseUtil.executeUpdate("UPDATE financials SET capital = "+com.nada.poo.model.Financial.capital+" Where financial_id = 1");
    }

    // update income
    public static void updateIncomeDatabase(){
        DatabaseUtil.executeUpdate("UPDATE financials SET total_income = "+com.nada.poo.model.Financial.income+" Where financial_id = 1");
    }

    // update cost
    public static void updateCostDatabase(){
        DatabaseUtil.executeUpdate("UPDATE financials SET total_cost = "+com.nada.poo.model.Financial.cost+" Where financial_id = 1");
    }

    // update all
    public static void updateDatabase(){
        updateCapitalDatabase();
        updateIncomeDatabase();
        updateCostDatabase();
    }
}
