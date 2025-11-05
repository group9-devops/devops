package com.napier.sem;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * The CapitalCityReport class is responsible for retrieving and displaying
 * information about capital cities from the database. It connects to the database via a
 * provided {@link java.sql.Connection}, executes an SQL query to obtain capital city data,
 * and prints the results in a formatted table.
 *  */

public class CapitalCityReport {

    /**
     * Method to print all capital cities in the world
     * in descending order of population*/

    public void printAllCapitalCities(Connection con){
        String sql = "SELECT city.Name AS CapitalCity, country.Name AS Country, " +
                "city.Population AS Population " +
                "FROM country " +
                "JOIN city ON country.Capital = city.ID " +
                "ORDER BY city.Population DESC";

        printResults(con, sql, "All capital Cities");
    }


    /**
     * Method to print all capital cities in a continent
     * in descending order of population*/
    public void printCapitalCitiesByContinent(Connection con, String continent){
        String sql = "SELECT city.Name AS CapitalCity, country.Name AS Country, " +
                "city.Population AS Population " +
                "FROM country " +
                "JOIN city ON country.Capital = city.ID " +
                "WHERE country.Continent = '"+continent+"'" +
                "ORDER BY city.Population DESC";

        printResults(con, sql, "Capital Cities in " + continent);
    }

    /**
     * Method to print all capital cities in a continent
     * in descending order of population*/
    public void printCapitalCitiesByRegion(Connection con, String region){
        String sql = "SELECT city.Name AS CapitalCity, country.Name AS Country, " +
                "city.Population AS Population " +
                "FROM country " +
                "JOIN city ON country.Capital = city.ID " +
                "WHERE country.Continent = '"+ region +"'" +
                "ORDER BY city.Population DESC";

        printResults(con, sql, "Capital Cities in " + region);
    }



    /**
     * Executes a SQL query to retrieve city data and prints the results in a formatted table.
     *
     * @param con   the database connection to use
     * @param sql   the SQL query to execute
     * @param title the title displayed above the printed results
     */
    private void printResults(Connection con, String sql, String title){
        ArrayList<City> cities = new ArrayList<>();

        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(sql);

            while (rset.next())
            {
                City capitalCity = new City();
                capitalCity.Name = rset.getString("CapitalCity");
                capitalCity.Country = rset.getString("Country");
                capitalCity.Population = rset.getInt("Population");


                cities.add(capitalCity);
            }



        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get the capital cities");

        }

        // Table header
        System.out.println("\n==== " + title + " ===\n");
        System.out.printf("%-30s %-20s %-15s%n",
                "Capital City", "Country", "Population");
        System.out.println("-------------------------------------------------------------------");

        // Print rows
        for (City capitalCity : cities) {
            System.out.printf("%-30s %-20s %,15d%n",
                    capitalCity.Name,
                    capitalCity.Country,
                    capitalCity.Population);
        }

    }
}




