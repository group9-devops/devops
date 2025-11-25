package com.napier.sem;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UrbanReport {
    public double population;
    public double urbanPopulation;

    /**
     * @param con the active database connection.
     */
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
    }

    /**
     * @param con the active database connection.
     */
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
    }

    /**
     * @param con the active database connection.
     * @param region the region used to define scope of report
     */
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

    /**
     * @param con the active database connection.
     * @param region the region used to define scope of report
     */
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

    /**
     * @param con the active database connection.
     * @param continent the continent used to define scope of report
     */
    public void getPopulationOfContinent(Connection con, String continent) {
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

    /**
     * @param con the active database connection.
     * @param continent the continent used to define scope of report
     */
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

    /**
     * @param con the active database connection.
     * @param country the country used to define scope of report
     */
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

    /**
     * @param con the active database connection.
     * @param country the country used to define scope of report
     */
    public void getUrbanPopulationOfCountry(Connection con, String country) {
        try {
            Statement stmt = con.createStatement();
            String sqlStatement = "SELECT SUM(city.population) FROM city " +
                    "JOIN country ON city.CountryCode = country.Code " +
                    "WHERE country.Name = " + "'" + country + "'";
            ResultSet rset = stmt.executeQuery(sqlStatement);
            rset.next();
            urbanPopulation = rset.getDouble(1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get population");
        }
    }

    /**
     * @param con the active database connection.
     * @param city the city used to define scope of report
     */
    public void getPopulationOfCity(Connection con, String city) {
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

    // TODO: Method for HTML report generation.
    // Generate list of cities, country, region, continent
    // Iterate through list and call report on each of them.

    public void generateReports(Connection con) {
        String[] continents = {
                "Africa","Antarctica","Asia","Europe",
                "North America","Oceania","South America"
        };


        try {
            // 1. Regions
            List<String> regions = new ArrayList<>();
            try (Statement stmt = con.createStatement()) {
                ResultSet rset = stmt.executeQuery(
                        "SELECT DISTINCT Region FROM country WHERE Region IS NOT NULL"
                );
                while (rset.next()) {
                    regions.add(rset.getString("Region"));
                }
            }

            // 2. Countries
            List<String> countries = new ArrayList<>();
            try (Statement stmt = con.createStatement()) {
                ResultSet rset = stmt.executeQuery(
                        "SELECT DISTINCT Name FROM country ORDER BY Name"
                );
                while (rset.next()) {
                    countries.add(rset.getString("Name"));
                }
            }

            // 3. Cities
            List<String> cities = new ArrayList<>();
            try (Statement stmt = con.createStatement()) {
                ResultSet rset = stmt.executeQuery(
                        "SELECT DISTINCT Name FROM city ORDER BY Name"
                );
                while (rset.next()) {
                    cities.add(rset.getString("Name"));
                }
            }

        } catch (Exception e) {
            System.out.println("List creation error");
            System.out.println(e.getMessage());
        }
        // Generate reports
    }
}