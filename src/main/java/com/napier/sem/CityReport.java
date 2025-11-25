package com.napier.sem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * The CityReport class is responsible for retrieving and displaying
 * information about cities from the database.
 */
public class CityReport {
    private Connection con;

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
                FROM city
                JOIN country ON city.CountryCode = country.Code
                ORDER BY city.Population DESC
                """;
        return executeCityQuery(sql);
    }

    /**
     * Retrieves all cities in a continent, ordered by population descending.
     *
     * @return A list of all cities in a continent.
     */
    public ArrayList<City> printCitiesByContinent(String continent) {
        if (con == null) {
            return new ArrayList<>(); // Return empty list, not null
        }
        String sql = """
                SELECT city.Name AS CityName, country.Name AS Country, city.District AS District, city.Population AS Population
                FROM city
                JOIN country ON city.CountryCode = country.Code
                WHERE country.Continent = ?
                ORDER BY city.Population DESC
                """;
        return executeCityQuery(sql, continent);
    }

    /**
     * Retrieves all cities a region, ordered by population descending.
     *
     * @return A list of all cities in a region.
     */
    public ArrayList<City> printCitiesByRegion(String region) {
        if (con == null) {
            return new ArrayList<>(); // Return empty list, not null
        }
        String sql = """
                SELECT city.Name AS CityName, country.Name AS Country, city.District AS District, city.Population AS Population
                FROM city
                JOIN country ON city.CountryCode = country.Code
                WHERE country.Region = ?
                ORDER BY city.Population DESC
                """;
        return executeCityQuery(sql, region);
    }

    /**
     * Retrieves all cities in a district, ordered by population descending.
     *
     * @return A list of all cities in a district.
     */
    public ArrayList<City> printCitiesByDistrict(String district) {
        if (con == null) {
            return new ArrayList<>(); // Return empty list, not null
        }
        String sql = """
                SELECT city.Name AS CityName, country.Name AS Country, city.District AS District, city.Population AS Population
                FROM city
                JOIN country ON city.CountryCode = country.Code
                WHERE city.District = ?
                ORDER BY city.Population DESC
                """;
        return executeCityQuery(sql, district);
    }

    /**
     * Retrieves all Top N cities in the world, ordered by population descending.
     * @param n The limit of cities to be listed
     * @return A list of all Top N cities.
     */
    public ArrayList<City> getTopNCitiesInWorld(int n) {
        String sql = """
                SELECT city.Name AS CityName, country.Name AS Country, city.District AS District, city.Population AS Population
                FROM city
                JOIN country ON city.CountryCode = country.Code
                ORDER BY city.Population DESC
                LIMIT ?
                """;
        return executeCityQuery(sql, n);
    }

    /**
     * Retrieves all Top N cities in a continent, ordered by population descending.
     * @param n The limit of cities to be listed
     * @return A list of all Top N cities in a contient.
     */
    public ArrayList<City> getTopNCitiesByContinent(String continent, int n) {
        String sql = """
                SELECT city.Name AS CityName, country.Name AS Country, city.District AS District, city.Population AS Population
                FROM city
                JOIN country ON city.CountryCode = country.Code
                WHERE country.Continent = ?
                ORDER BY city.Population DESC
                LIMIT ?
                """;
        return executeCityQuery(sql, continent, n);
    }

    /**
     * Retrieves all Top N cities in a region, ordered by population descending.
     * @param n The limit of cities to be listed
     * @return A list of all Top N cities in a region.
     */
    public ArrayList<City> getTopNCitiesByRegion(String region, int n) {
        String sql = """
                SELECT city.Name AS CityName, country.Name AS Country, city.District AS District, city.Population AS Population
                FROM city
                JOIN country ON city.CountryCode = country.Code
                WHERE country.Region = ?
                ORDER BY city.Population DESC
                LIMIT ?
                """;
        return executeCityQuery(sql,region, n);
    }

    public ArrayList<City> getTopNCitiesByDistrict(String district, int n) {
        String sql = """
                SELECT city.Name AS CityName, country.Name AS Country, city.District AS District, city.Population AS Population
                FROM city
                JOIN country ON city.CountryCode = country.Code
                WHERE city.District = ?
                ORDER BY city.Population DESC
                LIMIT ?
                """;
        return executeCityQuery(sql, district, n);
    }

    /**
     * Executes SQL queries and maps results to City objects.
     *
     * @param sql    The SQL query to execute.
     * @param params Optional query parameters.
     * @return A list of City objects.
     */
    private ArrayList<City> executeCityQuery(String sql, String... params) {
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
     * Executes SQL queries and maps results to City objects.
     *
     * @param sql    The SQL query to execute.
     * @param params Optional query parameters.
     * @param limit  The limit of objects to be printed
     * @return A list of City objects.
     */
    private ArrayList<City> executeCityQuery(String sql, String params, int limit) {
        ArrayList<City> cities = new ArrayList<>();

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, params);  // e.g., continent or region
            pstmt.setInt(2, limit);     // the LIMIT parameter

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
     * Executes SQL queries and maps results to City objects.
     *
     * @param sql    The SQL query to execute.
     * @param limit Optional query parameters.
     * @return A list of City objects.
     */
    private ArrayList<City> executeCityQuery(String sql, int limit) {
        ArrayList<City> cities = new ArrayList<>();

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, limit);  // Bind the LIMIT parameter

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
    /**
     * Outputs a list of capital cities to a Markdown file.
     *
     * @param capitals List of capital cities to output
     * @param filename The name of the Markdown file to create
     */
    public void outputCapitalCities(ArrayList<City> capitals, String filename) {
        if (capitals == null || capitals.isEmpty()) {
            System.out.println("No capital cities to output.");
            return;
        }

        StringBuilder sb = new StringBuilder();
        // Markdown table header
        sb.append("| City | Country | Population |\r\n");
        sb.append("| --- | --- | --- |\r\n");

        // Loop through all capital cities
        for (City capital : capitals) {
            if (capital == null) continue;
            sb.append("| ")
                    .append(capital.Name).append(" | ")
                    .append(capital.Country).append(" | ")
                    .append(capital.Population).append(" |\r\n");
        }

        try {
            // Create reports folder if it does not exist
            new java.io.File("./reports/").mkdirs();

            // Write Markdown to file
            java.io.BufferedWriter writer = new java.io.BufferedWriter(
                    new java.io.FileWriter("./reports/" + filename));
            writer.write(sb.toString());
            writer.close();
            System.out.println("Capital cities report written to ./reports/" + filename);
        } catch (java.io.IOException e) {
            e.printStackTrace();
            System.out.println("Failed to write capital cities report.");
        }
    }
}
