package com.napier.sem;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * The  PrintCityValues class is responsible for retrieving and displaying
 *  * information about cities from the database. It connects to the database via a
 *  * provided {@link java.sql.Connection}, executes an SQL query to obtain city data,
 *  * and prints the results in a formatted table.*/

public class PrintCityValues {

    //  All cities in the world
    public void printAllCities(Connection con) {
        String sql = "SELECT city.Name AS CityName, country.Name AS Country, city.Population AS Population " +
                "FROM city JOIN country ON city.CountryCode = country.Code " +
                "ORDER BY city.Population DESC";
        printResults(con, sql, "All Cities in the World");
    }

    // 2Cities in a continent
    public void printCitiesByContinent(Connection con, String continent) {
        String sql = "SELECT city.Name AS CityName, country.Name AS Country, city.Population AS Population " +
                "FROM city JOIN country ON city.CountryCode = country.Code " +
                "WHERE country.Continent = '" + continent + "' " +
                "ORDER BY city.Population DESC";
        printResults(con, sql, "Cities in " + continent);
    }

    //  Cities in a region
    public void printCitiesByRegion(Connection con, String region) {
        String sql = "SELECT city.Name AS CityName, country.Name AS Country, city.Population AS Population " +
                "FROM city JOIN country ON city.CountryCode = country.Code " +
                "WHERE country.Region = '" + region + "' " +
                "ORDER BY city.Population DESC";
        printResults(con, sql, "Cities in " + region);
    }

    // To print the results
    private void printResults(Connection con, String sql, String title) {
        ArrayList<City> cities = new ArrayList<>();
        try (Statement stmt = con.createStatement();
             ResultSet rset = stmt.executeQuery(sql)) {

            while (rset.next()) {
                City city = new City();
                city.name = rset.getString("CityName");
                city.country = rset.getString("Country");
                city.population = rset.getInt("Population");
                cities.add(city);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }

        // Print formatted output
        System.out.println("\n=== " + title + " ===");
        System.out.printf("%-30s %-20s %-15s%n", "City", "Country", "Population");
        System.out.println("-----------------------------------------------------");

        for (City c : cities) {
            System.out.printf("%-30s %-20s %,15d%n", c.name, c.country, c.population);
        }
    }
}

