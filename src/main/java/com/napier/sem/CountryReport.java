package com.napier.sem;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.PreparedStatement;

/**
 * Class responsible for retrieving and printing lists of Country objects.
 */
public class CountryReport {

    /**
     * The active database connection.
     */
    private Connection con;

    /**
     * @param con The active database connection object.
     */
    public CountryReport(Connection con) {
        this.con = con;
    }

    /**
     * Retrieves all countries from the database, ordered by population.
     * @return An ArrayList of Country objects, or null on failure.
     */
    public ArrayList<Country> getCountriesByPopulation() {
        String sql = """
                SELECT Code, Name, Continent, Region, Population, Capital 
                FROM country 
                ORDER BY Population DESC
                """;
        return executeCountryQuery(sql);
    }

    /**
     * Retrieves all countries in a specific continent, ordered by population.
     * @param continent The name of the continent.
     * @return An ArrayList of Country objects, or null on failure.
     */
    public ArrayList<Country> getCountriesByContinent(String continent) {
        String sql = """
                SELECT Code, Name, Continent, Region, Population, Capital 
                FROM country 
                WHERE Continent = ? 
                ORDER BY Population DESC
                """;
        return executeCountryQuery(sql, continent);
    }

    /**
     * Retrieves all countries in a specific region, ordered by population.
     * @param region The name of the region.
     * @return An ArrayList of Country objects, or null on failure.
     */
    public ArrayList<Country> getCountriesByRegion(String region) {
        String sql = """
                SELECT Code, Name, Continent, Region, Population, Capital 
                FROM country 
                WHERE Region = ? 
                ORDER BY Population DESC
                """;
        return executeCountryQuery(sql, region);
    }

    /**
     * Private helper method to execute SQL queries and map results to Country objects.
     * @param sql The SQL query to execute.
     * @param params Optional query parameters for the PreparedStatement.
     * @return A list of Country objects, or null if an error occurs.
     */
    private ArrayList<Country> executeCountryQuery(String sql, String... params) {
        ArrayList<Country> countries = new ArrayList<>();

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            // Bind parameters if they are provided
            for (int i = 0; i < params.length; i++) {
                pstmt.setString(i + 1, params[i]);
            }

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                Country country = new Country();
                country.Code = rset.getString("Code");
                country.Name = rset.getString("Name");
                country.Continent = rset.getString("Continent");
                country.Population = rset.getInt("Population");
                country.Capital = rset.getString("Capital");
                country.Region = rset.getString("Region");
                countries.add(country);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to execute country query.");
            return null;
        }

        return countries;
    }

    /**
     * Prints a list of countries to the console in a formatted table.
     * (This method is unchanged as it doesn't use the database connection)
     * @param countries The list of countries to print.
     */
    public void printCountries(ArrayList<Country> countries) {
        // Check for null or empty list
        if (countries == null) {
            System.out.println("No countries found.");
            return;
        }

        if (countries.isEmpty()) {
            System.out.println("No countries found.");
            return;
        }

        System.out.printf("%-30s %-20s %-15s %-20s %-30s %15s\n",
                "Name", "Continent", "Region", "Capital", "Code", "Population");
        System.out.println("--------------------------------------------------------------------------------------------------------------");

        for (Country country : countries) {
            if (country == null) continue;

            System.out.printf("%-30s %-20s %-15s %-20s %-30s %,15d\n",
                    country.Name,
                    country.Continent,
                    country.Region,
                    country.Capital,
                    country.Code,
                    country.Population
            );
        }
    }
}