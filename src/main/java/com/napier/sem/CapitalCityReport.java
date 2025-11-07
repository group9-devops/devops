package com.napier.sem;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.PreparedStatement;

/**
 * The CapitalCityReport class is responsible for retrieving and displaying
 * information about capital cities from the database.
 */
public class CapitalCityReport
{
    private Connection con;

    /**
     * Constructor to inject database connection.
     * @param con the active database connection
     */
    public CapitalCityReport(Connection con)
    {
        this.con = con;
    }

    /**
     * Retrieves all capital cities in the world, ordered by population descending.
     * @return A list of all capital cities.
     */
    public ArrayList<City> getAllCapitalCities()
    {
        String sql = """
                SELECT city.Name AS CapitalCity, country.Name AS Country, city.Population AS Population
                FROM country
                JOIN city ON country.Capital = city.ID
                ORDER BY city.Population DESC
                """;
        return executeCapitalCityQuery(sql);
    }

    /**
     * Retrieves all capital cities in a specific continent.
     * @param continent The name of the continent.
     * @return A list of capital cities in that continent.
     */
    public ArrayList<City> getCapitalCitiesByContinent(String continent)
    {
        String sql = """
                SELECT city.Name AS CapitalCity, country.Name AS Country, city.Population AS Population
                FROM country
                JOIN city ON country.Capital = city.ID
                WHERE country.Continent = ?
                ORDER BY city.Population DESC
                """;
        return executeCapitalCityQuery(sql, continent);
    }

    /**
     * Retrieves all capital cities in a specific region.
     * @param region The name of the region.
     * @return A list of capital cities in that region.
     */
    public ArrayList<City> getCapitalCitiesByRegion(String region)
    {
        String sql = """
                SELECT city.Name AS CapitalCity, country.Name AS Country, city.Population AS Population
                FROM country
                JOIN city ON country.Capital = city.ID
                WHERE country.Region = ?
                ORDER BY city.Population DESC
                """;
        return executeCapitalCityQuery(sql, region);
    }

    /**
     * Executes SQL queries and maps results to City objects.
     * @param sql The SQL query to execute.
     * @param params Optional query parameters.
     * @return A list of City objects.
     */
    private ArrayList<City> executeCapitalCityQuery(String sql, String... params)
    {
        ArrayList<City> cities = new ArrayList<>();

        try (PreparedStatement pstmt = con.prepareStatement(sql))
        {
            // Bind parameters if provided
            for (int i = 0; i < params.length; i++)
            {
                pstmt.setString(i + 1, params[i]);
            }

            ResultSet rset = pstmt.executeQuery();

            while (rset.next())
            {
                City city = new City();
                city.Name = rset.getString("CapitalCity");
                city.Country = rset.getString("Country");
                city.Population = rset.getInt("Population");
                cities.add(city);
            }

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to execute capital city query.");
            return null;
        }

        return cities;
    }

    /**
     * Prints a formatted list of capital cities.
     * @param cities The list of cities to print.
     */
    public void printCapitalCities(ArrayList<City> cities)
    {
        if (cities == null)
        {
            System.out.println("No capital cities to display.");
            return;
        }

        // Table header
        System.out.printf("%-30s %-25s %15s%n",
                "Capital City", "Country", "Population");
        System.out.println("-----------------------------------------------------------------------");

        // Print rows
        for (City city : cities)
        {
            if (city == null)
                continue;

            System.out.printf("%-30s %-25s %,15d%n",
                    city.Name, city.Country, city.Population);
        }
    }

    public ArrayList<City> filterCitiesByCountry(ArrayList<City> cities, String country) {
        ArrayList<City> filtered = new ArrayList<>();
        for (City city : cities) {
            if (city != null && city.Country.equalsIgnoreCase(country)) {
                filtered.add(city);
            }
        }
        return filtered;
    }




}




