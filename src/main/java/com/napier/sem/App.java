package com.napier.sem;

import java.sql.*;

/**
 * The App class manages the connection between the application
 * and a MySQL database. It provides methods to establish and close the
 * connection, with retry handling for connection failures.
 */
public class App
{

    /**
     * Connection to MySQL database.
     */
    private Connection con = null;

    /**
     * Connects to the MySQL database. Connection string set w/ port 3306.
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

    //Class to print all countries in the world.
    public Country  getCountry(String name)
    {
        try {
            Country country = new Country();
            Statement stmt = con.createStatement();
            String sqlStatement =  "SELECT * FROM country ORDER BY population DESC";
            ResultSet rset = stmt.executeQuery(sqlStatement);
            if(rset.next()){
                country.code = rset.getString("Code");
                country.name = rset.getString("Name");
                country.continent = rset.getString("Continent");
                country.population = rset.getInt("Population");
                country.capital = rset.getString("Capital");
                country.region = rset.getString("Region");
                return country;
            } else {
                return null;
            }

        }
        catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println("Failed to get country");
            return null;
        }
    }

    public void printCountries(Country country)
    {
        if(country == null)
        {
            System.out.println("Country is null");
            return;
        }
        System.out.println("Code : " + country.code + " Name : " + country.name +  " Continent : "
                + country.continent + " Population : " +
                country.population +  " Capital : " + country.capital + " Region : " + country.region);
    }

    public static void main(String[] args)
    {
        // Create new Application
        App a = new App();

        // Connect to database
        a.connect();
        Country country = a.getCountry("Brazil");
        a.printCountries(country);
        // Disconnect from database
        a.disconnect();

    }


}