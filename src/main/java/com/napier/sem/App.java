package com.napier.sem;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

/**
 * The App class manages the connection between the application
 * and a MySQL database. It provides methods to establish and close the
 * connection, with retry handling for connection failures.
 */
public class App {

    /**
     * Connection to MySQL database.
     */
    private Connection con = null;

    /**
     * Connects to the MySQL database. Connection string set w/ port 3306.
     */
    public void connect() {
        try {
            // Load Database driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        int retries = 10;
        for (int i = 0; i < retries; ++i) {
            System.out.println("Connecting to database...");
            try {
                // Wait a bit for db to start
                Thread.sleep(30000);
                // Connect to database
                con = DriverManager.getConnection("jdbc:mysql://db:3306/world?allowPublicKeyRetrieval=true&useSSL=false", "root", "example");
                System.out.println("Connection established!");
                break;
            } catch (SQLException sqle) {
                System.out.println("Connection failed. Attempt:  " + Integer.toString(i));
                System.out.println(sqle.getMessage());
            } catch (InterruptedException ie) {
                System.out.println("Thread interrupted? Should not happen.");
            }
        }
    }

    /**
     * Disconnect from the MySQL database.
     */
    public void disconnect() {
        if (con != null) {
            try {
                // Close connection
                con.close();
            } catch (Exception e) {
                System.out.println("Error closing connection to database");
            }
        }
    }

    /**
     * Gets all cities and sorts them by population in descending order
     * returns a list of cities sorted by population in descending order, or return null if thrown exception
     */

    public ArrayList<City> getAllCities() {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();

            // Modified SQL query to get all cities with their country and population
            String strSelect =
                    "SELECT city.Name AS CityName, " +
                            "country.Name AS Country, " +
                            "city.Population AS Population " +
                            "FROM city " +
                            "JOIN country ON city.CountryCode = country.Code " +
                            "ORDER BY city.Population DESC";

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);

            // Extract city information
            ArrayList<City> cities = new ArrayList<City>();
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
            System.out.println("Failed to get the cities");
            return null;
        }

    }
    public void Display(List<City> cities) {
        if (cities != null && !cities.isEmpty()) {
            for (City c : cities) {
                System.out.println("City: " + c.name + ", Country: " + c.country + ", Population: " + c.population);
            }
        } else {
            System.out.println("No capital cities found.");
        }
    }
    public static void main(String[] args)
    {
        // Create new Application
        App a = new App();

        // Connect to database
        a.connect();

        // Extract city information
        ArrayList<City> cityList = a.getAllCities();
        a.Display(cityList);


        System.out.println(cityList.size());

        // Disconnect from database
        a.disconnect();
    }
}