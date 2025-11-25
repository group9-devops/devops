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
        if (con == null) {
            return new ArrayList<>(); // Return empty list, not null
        }
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
        if (con == null) {
            return new ArrayList<>(); // Return empty list, not null
        }
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
        if (con == null) {
            return new ArrayList<>(); // Return empty list, not null
        }
        String sql = """
                SELECT Code, Name, Continent, Region, Population, Capital 
                FROM country 
                WHERE Region = ? 
                ORDER BY Population DESC
                """;
        return executeCountryQuery(sql, region);
    }

    /**
     * Gets the top N most populated countries for a specific place and specific limit.
     * @param sql The sql provided to the function to retrieve the data
     * @param place The name of the region (e.g., "Caribbean", "Southern Europe").
     * @param limit  The number of countries to return.
     * @return An ArrayList of Country objects, or null on failure.
     */
    public ArrayList<Country> topNCountries(String sql,String place, int limit) {

        // SQL query now uses placeholders for *both* region and limit
        ArrayList<Country> countries = new ArrayList<>();

        // Use a try-with-resources block to auto-close the PreparedStatement
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {

            // 1. Bind the *first* '?' (Region) as a String
            pstmt.setString(1, place);

            // 2. Bind the *second* '?' (LIMIT) as an Int
            pstmt.setInt(2, limit);

            // Execute the query
            ResultSet rset = pstmt.executeQuery();

            // Loop through the results and build the Country objects
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
            System.out.println("Failed to get countries for place: " + place);
            return null; // Return null or an empty list
        }

        return countries;
    }
    /**
     * Gets the top N most populated countries for a specific region.
     *
     * @param region The name of the region (e.g., "Caribbean", "Southern Europe").
     * @param limit  The number of countries to return.
     */
    public ArrayList<Country> topNCountriesByRegion(String region, int limit) {
        String sql = """
            SELECT Code, Name, Continent, Region, Population, Capital 
            FROM country 
            WHERE Region = ?
            ORDER BY Population DESC LIMIT ?
            """;
        return topNCountries(sql, region, limit);
    }

    /**
     * Gets the top N most populated countries for a specific continent.
     *
     * @param continent The name of the region (e.g.,North America, South America).
     * @param limit  The number of countries to return.
     */
    public ArrayList<Country> topNCountriesByContinent(String continent, int limit) {
        String sql = """
            SELECT Code, Name, Continent, Region, Population, Capital 
            FROM country 
            WHERE Continent = ?
            ORDER BY Population DESC LIMIT ?
            """;
        return topNCountries(sql, continent, limit);
    }

    /**
     * Gets the top N most populated countries in the world.
     * @param limit  The number of countries to return.
     */
    public ArrayList<Country> topNCountriesInTheWorld(int limit) {
        String sql = """
            SELECT Code, Name, Continent, Region, Population, Capital 
            FROM country 
            ORDER BY Population DESC LIMIT ?
            """;
        // SQL query now uses placeholders for *both* region and limit
        ArrayList<Country> countries = new ArrayList<>();

        // Use a try-with-resources block to auto-close the PreparedStatement
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            //  Bind the '?' (LIMIT) as an Int
            pstmt.setInt(1, limit);

            // Execute the query
            ResultSet rset = pstmt.executeQuery();

            // Loop through the results and build the Country objects
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
            System.out.println("Failed to get countries for place ");
            return null; // Return null or an empty list
        }

        return countries;
    }



    /**
     * Private helper method to execute SQL queries and map results to Country objects.
     * @param sql The SQL query to execute.
     * @param params Optional query parameters for the PreparedStatement.
     * @return A list of Country objects, or null if an error occurs.
     */
    private ArrayList<Country> executeCountryQuery(String sql, String... params) {
        ArrayList<Country> countries = new ArrayList<>();

        if (con == null) return countries;

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

    /**
     * Outputs a list of Country objects to a Markdown file.
     * @param countries The ArrayList of Country objects to output.
     * @param filename The name of the file (e.g., "all_countries.md").
     */
    public void outputCountries(ArrayList<Country> countries, String filename) {
        if (countries == null || countries.isEmpty()) {
            System.out.println("No countries to output.");
            return;
        }

        StringBuilder sb = new StringBuilder();
        // Markdown table header
        sb.append("| Code | Name | Continent | Region | Population | Capital |\r\n");
        sb.append("| --- | --- | --- | --- | --- | --- |\r\n");

        // Loop through all countries
        for (Country country : countries) {
            if (country == null) continue;
            // Using String.format for better population formatting (optional, but good practice)
            String formattedPopulation = String.format("%,d", country.Population);
            sb.append("| ")
                    .append(country.Code).append(" | ")
                    .append(country.Name).append(" | ")
                    .append(country.Continent).append(" | ")
                    .append(country.Region).append(" | ")
                    .append(formattedPopulation).append(" | ")
                    .append(country.Capital).append(" |\r\n");
        }

        try {
            // Create reports folder if it does not exist
            new java.io.File("./reports/").mkdirs();

            // Write Markdown to file
            java.io.BufferedWriter writer = new java.io.BufferedWriter(
                    new java.io.FileWriter("./reports/" + filename));
            writer.write(sb.toString());
            writer.close();
            System.out.println("Country report written to ./reports/" + filename);
        } catch (java.io.IOException e) {
            e.printStackTrace();
            System.out.println("Failed to write country report.");
        }
    }
}