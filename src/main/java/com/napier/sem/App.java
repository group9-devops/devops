package com.napier.sem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The App class manages the connection between the application
 * and a MySQL database. It provides methods to establish and close the
 * connection, with retry handling for connection failures.
 */
public class App {

    private Connection con = null;
    /**
     * Connects to the MySQL database. Connection string set with port 3306.
     */
    public void connect()
    {
        try
        {
            // Load Database driver
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
                // Wait a bit for db to start
                Thread.sleep(30000);
                // Connect to database
                con = DriverManager.getConnection("jdbc:mysql://db:3306/world?allowPublicKeyRetrieval=true&useSSL=false", "root", "example");
                System.out.println("Connection established!");
                break;
            }
            catch (SQLException sqle)
            {
                System.out.println("Connection failed. Attempt:  " + Integer.toString(i));
                System.out.println(sqle.getMessage());
            }
            catch (InterruptedException ie)
            {
                System.out.println("Thread interrupted? Should not happen.");
            }
        }
    }

    /**
     * Disconnect from the MySQL database.
     */
    public void disconnect()
    {
        if (con != null)
        {
            try
            {
                // Close connection
                con.close();
            }
            catch (Exception e)
            {
                System.out.println("Error closing connection to database");
            }
        }
    }

    /**
     * * Gets all capital cities by population globally
     * * @return A list of all capital cities in descending order, or null if there is an error.
     */
    public ArrayList<City> getCapitalCity() {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT city.Name AS CapitalCity, " +
                            "country.Name AS Country, " +
                            "city.Population AS Population " +
                            "FROM country " +
                            "JOIN city ON country.Capital = city.ID " +
                            "ORDER BY city.Population DESC";


            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Extract employee information
            ArrayList<City> city = new ArrayList<City>();
            while (rset.next())
            {
                City capitalCity = new City();
                capitalCity.Name = rset.getString("CapitalCity");
                capitalCity.Country = rset.getString("Country");
                capitalCity.Population = rset.getInt("Population");


                city.add(capitalCity);
            }

            return city;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get the capital cities");
            return null;
        }

    }

    //method to print the cities
    public void Display(List<City> cities) {
        if (cities != null && !cities.isEmpty()) {
            for (City c : cities) {
                System.out.println("Capital: " + c.Name + ", Country: " + c.Country + ", Population: " + c.Population);
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
        ArrayList<City> cityList = a.getCapitalCity();
        a.Display(cityList);


        System.out.println(cityList.size());

        // Disconnect from database
        a.disconnect();
    }


}