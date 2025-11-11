package com.napier.sem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * The UrbanReport class is responsible for retrieving and displaying
 * information about urban and total populations for the world,
 * continents, and regions.
 */
public class UrbanReport {

    private Connection con;

    /**
     * Constructor to inject database connection.
     *
     * @param con the active database connection
     */
    public UrbanReport(Connection con) {
        this.con = con;
    }

    /**
     * Retrieves urban and total population for the world.
     *
     * @return A list containing total, urban population, and percentage data for the world.
     */
    public ArrayList<Country> getUrbanPopulationWorld() {
        String sql = """
                SELECT
                    'World' AS Name,
                    SUM(country.Population) AS TotalPopulation,
                    SUM(city.Population) AS UrbanPopulation,
                    (SUM(city.Population) / SUM(country.Population)) * 100 AS UrbanPercentage
                FROM country
                JOIN city ON country.Code = city.CountryCode
                """;
        return executeUrbanQuery(sql);
    }

    /**
     * Retrieves urban and total population for a specific continent.
     *
     * @param continent The name of the continent.
     * @return A list containing total, urban population, and percentage data for that continent.
     */
    public ArrayList<Country> getUrbanPopulationByContinent(String continent) {
        String sql = """
                SELECT
                    country.Continent AS Name,
                    SUM(country.Population) AS TotalPopulation,
                    SUM(city.Population) AS UrbanPopulation,
                    (SUM(city.Population) / SUM(country.Population)) * 100 AS UrbanPercentage
                FROM country
                JOIN city ON country.Code = city.CountryCode
                WHERE country.Continent = ?
                GROUP BY country.Continent
                ORDER BY UrbanPercentage DESC
                """;
        return executeUrbanQuery(sql, continent);
    }

    /**
     * Retrieves urban and total population for a specific region.
     *
     * @param region The name of the region.
     * @return A list containing total, urban population, and percentage data for that region.
     */
    public ArrayList<Country> getUrbanPopulationByRegion(String region) {
        String sql = """
                SELECT
                    country.Region AS Name,
                    SUM(country.Population) AS TotalPopulation,
                    SUM(city.Population) AS UrbanPopulation,
                    (SUM(city.Population) / SUM(country.Population)) * 100 AS UrbanPercentage
                FROM country
                JOIN city ON country.Code = city.CountryCode
                WHERE country.Region = ?
                GROUP BY country.Region
                ORDER BY UrbanPercentage DESC
                """;
        return executeUrbanQuery(sql, region);
    }

    /**
     * Executes SQL queries and maps results to Country objects.
     *
     * @param sql    The SQL query to execute.
     * @param params Optional query parameters.
     * @return A list of Country objects containing urban population data.
     */
    private ArrayList<Country> executeUrbanQuery(String sql, String... params) {
        ArrayList<Country> results = new ArrayList<>();

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            // Bind parameters if provided
            for (int i = 0; i < params.length; i++) {
                pstmt.setString(i + 1, params[i]);
            }

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                Country country = new Country();
                country.Name = rset.getString("Name");
                country.Population = rset.getLong("TotalPopulation");
                country.UrbanPopulation = rset.getLong("UrbanPopulation");
                country.UrbanPercentage = rset.getDouble("UrbanPercentage");
                results.add(country);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to execute urban population query.");
            return null;
        }

        return results;
    }

    /**
     * Prints a formatted list of urban population data.
     *
     * @param countries The list of countries, continents, or world data to print.
     */
    public void printUrbanPopulation(ArrayList<Country> countries) {
        if (countries == null || countries.isEmpty()) {
            System.out.println("No urban population data to display.");
            return;
        }

        // Table header
        System.out.printf("%-30s %20s %20s %20s%n",
                "Name", "Total Population", "Urban Population", "Urban Percentage");
        System.out.println("-----------------------------------------------------------------------------------------------");

        // Print rows
        for (Country c : countries) {
            if (c == null)
                continue;

            System.out.printf("%-30s %,20d %,20d %,19.2f%%%n",
                    c.Name, c.Population, c.UrbanPopulation, c.UrbanPercentage);
        }
    }
}
