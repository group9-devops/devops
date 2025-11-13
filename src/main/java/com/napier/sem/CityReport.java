package com.napier.sem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * The  PrintCityValues class is responsible for retrieving and displaying
 *  * information about cities from the database. It connects to the database via a
 *  * provided {@link java.sql.Connection}, executes an SQL query to obtain city data,
 *  * and prints the results in a formatted table.*/

public class CityReport {

    public Connection con;

    /**
     * Constructor to inject database connection.
     *
     * @param con the active database connection
     */
    public CityReport(Connection con) {
        this.con = con;
    }

    /**
     * Retrieves all cities in the world, ordered by population descending.
     *
     * @return A list of all cities.
     */
    public ArrayList<City> printAllCities() {
        if (con == null) {
            return new ArrayList<>(); // Return empty list, not null
        }
        String sql = """
                SELECT city.Name AS CityName, country.Name AS Country, city.District AS District, city.Population AS Population
                FROM city JOIN country ON city.CountryCode = country.Code
                ORDER BY city.Population DESC
                """;
        return executeCapitalCityQuery(sql);
    }

    /**
     * Retrieves all cities in a specific continent.
     *
     * @param continent The name of the continent.
     * @return A list of cities in that continent.
     */
    public ArrayList<City> printCitiesByContinent(String continent) {
        if (con == null) {
            return new ArrayList<>(); // Return empty list, not null
        }
        String sql = """
                SELECT city.Name AS CityName, country.Name AS Country, city.District AS District,city.Population AS Population
                FROM city JOIN country ON city.CountryCode = country.Code
                WHERE country.Continent = ?
                ORDER BY city.Population DESC
                """;
        return executeCapitalCityQuery(sql,continent);
    }

    /**
     * Retrieves all cities in a specific region.
     *
     * @param region The name of the region.
     * @return A list of cities in that region.
     */
    public ArrayList<City> printCitiesByRegion(String region) {
        if (con == null) {
            return new ArrayList<>(); // Return empty list, not null
        }
        String sql = """
                SELECT city.Name AS CityName, country.Name AS Country,city.District AS District, city.Population AS Population 
                FROM city JOIN country ON city.CountryCode = country.Code 
                WHERE country.Region = ?
                ORDER BY city.Population DESC
                """;
        return executeCapitalCityQuery(sql,region);
    }

    /**
     * Retrieves all capital cities in a specific district.
     *
     * @param district The name of the district.
     * @return A list of cities in that district.
     */
    public ArrayList<City> printCitiesByDistrict(String district) {
        if (con == null) {
            return new ArrayList<>(); // Return empty list, not null
        }
        String sql = """
                SELECT city.Name AS CityName, country.Name AS Country,city.District AS District, city.Population AS Population 
                FROM city JOIN country ON city.CountryCode = country.Code
                WHERE city.District = ?
                ORDER BY city.Population DESC
                """;
        return executeCapitalCityQuery(sql,district);
    }


    /**
     * Executes SQL queries and maps results to City objects.
     *
     * @param sql    The SQL query to execute.
     * @param params Optional query parameters.
     * @return A list of City objects.
     */
    private ArrayList<City> executeCapitalCityQuery(String sql, String... params) {
        ArrayList<City> cities = new ArrayList<>();

        if (con == null) return cities;

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            // Bind parameters if provided
            for (int i = 0; i < params.length; i++) {
                pstmt.setString(i + 1, params[i]);
            }

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                City city = new City();
                city.Name = rset.getString("CityName");
                city.Country = rset.getString("Country");
                city.District = rset.getString("District");
                city.Population = rset.getInt("Population");
                cities.add(city);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to execute capital city query.");
            return null;
        }

        return cities;
    }

    /**
     * Prints a formatted list of capital cities.
     *
     * @param cities The list of cities to print.
     */
    public void printCities(ArrayList<City> cities) {
        if (cities == null) {
            System.out.println("No cities to display.");
            return;
        }

        // Table header
        System.out.printf("%-30s %-25s %-20s %15s%n",
                "Capital City", "Country", "District", "Population");
        System.out.println("------------------------------------------------------------------------------------------");

        // Print rows
        for (City city : cities) {
            if (city == null)
                continue;

            System.out.printf("%-30s %-25s %-20s %,15d%n",
                    city.Name, city.Country, city.District, city.Population);
        }

    }

}
