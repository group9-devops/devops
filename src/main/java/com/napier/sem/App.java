package com.napier.sem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The App class manages the connection between the application
 * and a MySQL database. It provides methods to establish and close the
 * connection, with retry handling for connection failures.
 */
public class App
{
    /**
     * Connection object for interacting with the MySQL database.
     */
    private Connection con = null;

    /**
     * Establishes a connection to the MySQL database.
     * Retries up to 10 times, waiting 30 seconds between attempts.
     */
    public void connect()
    {
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        int retries = 10;
        for (int i = 0; i < retries; ++i)
        {
            System.out.println("Connecting to database...");
            try
            {
                Thread.sleep(30000);
                con = DriverManager.getConnection(
                        "jdbc:mysql://db:3306/world?allowPublicKeyRetrieval=true&useSSL=false",
                        "root",
                        "example"
                );
                System.out.println("Connection established!");
                break;
            }
            catch (SQLException sqle)
            {
                System.out.println("Connection failed. Attempt: " + i);
                System.out.println(sqle.getMessage());
            }
            catch (InterruptedException ie)
            {
                System.out.println("Thread interrupted unexpectedly.");
            }
        }
    }
    public ArrayList<City> getCitiesByCountry(String countryName) {
        try {
            // Create an SQL prepared statement
            String strSelect =
                    "SELECT city.Name AS CityName, " +
                            "country.Name AS Country, " +
                            "city.Population AS Population " +
                            "FROM city " +
                            "JOIN country ON city.CountryCode = country.Code " +
                            "WHERE country.Name = ? " +   // Filter by country name
                            "ORDER BY city.Population DESC"; // Order by population descending

            PreparedStatement pstmt = con.prepareStatement(strSelect);
            pstmt.setString(1, countryName); // Set the country name parameter

            // Execute SQL statement
            ResultSet rset = pstmt.executeQuery();

            // Extract city information
            ArrayList<City> cities = new ArrayList<>();
            while (rset.next()) {
                City city = new City();
                city.name = rset.getString("CityName");
                city.country = rset.getString("Country");
                city.population = rset.getInt("Population");
                cities.add(city);
            }

            return cities;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get the cities for country: " + countryName);
            return null;
        }
    }
    /**
     * Closes the connection to the MySQL database if it is active.
     */
    public void disconnect()
    {
        if (con != null)
        {
            try
            {
                con.close();
            }
            catch (Exception e)
            {
                System.out.println("Error closing connection to database");
            }
        }
    }

    /**
     * Main entry point of the application.
     * Connects to the database and then disconnects.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args)
    {
        App a = new App();

        a.connect();

        ArrayList<City> ArrayList = a.getCitiesByCountry("");


        a.disconnect();
    }
}
