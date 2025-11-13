package com.napier.sem;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

public class UrbanReport {
    public double population;
    public double urbanPopulation;
    public double percentage;
    public void getPopulationOfWorld(Connection con) {

        try {
            Statement stmt = con.createStatement();
            String sqlStatement = "SELECT SUM(population) FROM country";
            ResultSet rset = stmt.executeQuery(sqlStatement);
            rset.next();
            population = rset.getDouble(1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get population");
        }
        System.out.println("Population of world: " + population);
    }
    public void getUrbanPopulation(Connection con) {
        try {
            Statement stmt = con.createStatement();
            String sqlStatement = "SELECT SUM(population) FROM city";
            ResultSet rset = stmt.executeQuery(sqlStatement);
            rset.next();
            urbanPopulation = rset.getDouble(1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get population");
        }
        System.out.println("Urban population: " + urbanPopulation);
        percentage = (urbanPopulation / population) * 100;
        System.out.println("Percentage of urban: " + percentage);
    }

}