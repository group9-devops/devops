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

    public void getPopulationOfRegion(Connection con, String region) {
        try {
            Statement stmt = con.createStatement();
            String sqlStatement = "SELECT SUM(population) FROM country WHERE Region = " + "'" + region + "'";
            ResultSet rset = stmt.executeQuery(sqlStatement);
            rset.next();
            population = rset.getDouble(1);
        } catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println("Failed to get population");
        }
    }

    public void getUrbanPopulationOfRegion(Connection con, String region) {
        try {
            Statement stmt = con.createStatement();
            String sqlStatement = "SELECT SUM(city.population) FROM city " +
                    "JOIN country ON city.CountryCode = country.Code " +
                    "WHERE country.Region = " + "'" + region + "'";
            ResultSet rset = stmt.executeQuery(sqlStatement);
            rset.next();
            urbanPopulation = rset.getDouble(1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get population");
        }
    }

    public void getPopulationOfCContinent(Connection con, String continent) {
        try {
            Statement stmt = con.createStatement();
            String sqlStatement = "SELECT SUM(population) FROM country WHERE Continent = " + "'" + continent + "'";
            ResultSet rset = stmt.executeQuery(sqlStatement);
            rset.next();
            population = rset.getDouble(1);
        } catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println("Failed to get population");
        }
    }

    public void getUrbanPopulationOfContinent(Connection con, String continent) {
        try {
            Statement stmt = con.createStatement();
            String sqlStatement = "SELECT SUM(city.population) FROM city " +
                    "JOIN country ON city.CountryCode = country.Code " +
                    "WHERE country.Continent = " + "'" + continent + "'";
            ResultSet rset = stmt.executeQuery(sqlStatement);
            rset.next();
            urbanPopulation = rset.getDouble(1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get population");
        }
    }

    public void getPopulationOfCountry(Connection con, String country) {
        try {
            Statement stmt = con.createStatement();
            String sqlStatement = "SELECT population FROM country WHERE Name = " + "'" + country + "'";
            ResultSet rset = stmt.executeQuery(sqlStatement);
            rset.next();
            population = rset.getDouble(1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get population");
        }
    }

    public void getUrbanPopulationOfCountry(Connection con, String country) {
        try {
            Statement stmt = con.createStatement();
            String sqlStatement = "SELECT SUM(city.population) FROM city " +
                    "JOIN country ON city.CountryCode = country.Code " +
                    "WHERE country.Name = " + "'" + country + "'";
            ResultSet rset = stmt.executeQuery(sqlStatement);
            rset.next();
            population = rset.getDouble(1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get population");
        }
    }
    public void getPopulationOfCCity(Connection con, String city) {
        try {
            Statement stmt = con.createStatement();
            String sqlStatement = "SELECT population FROM city WHERE Name = " + "'" + city + "'";
            ResultSet rset = stmt.executeQuery(sqlStatement);
            rset.next();
            population = rset.getDouble(1);
        } catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println("Failed to get population");
        }
    }
}