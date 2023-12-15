package com.nada.poo.dao;

import com.nada.poo.database.DatabaseUtil;

public class FinancialDaoImpl {

    // update capital
    public static void updateCapitalDatabase(){
        DatabaseUtil.executeUpdate("UPDATE financials SET capital = "+com.nada.poo.model.Financial.capital+"Where financial_id = 1");
    }

    // update income
    public static void updateIncomeDatabase(){
        DatabaseUtil.executeUpdate("UPDATE financials SET total_income = "+com.nada.poo.model.Financial.income+"Where financial_id = 1");
    }

    // update cost
    public static void updateCostDatabase(){
        DatabaseUtil.executeUpdate("UPDATE financials SET total_cost = "+com.nada.poo.model.Financial.cost+"Where financial_id = 1");
    }

    // update all
    public static void updateDatabase(){
        updateCapitalDatabase();
        updateIncomeDatabase();
        updateCostDatabase();
    }
}
