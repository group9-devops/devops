package com.napier.sem;

import java.sql.*;
import java.util.ArrayList;

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
        // Create new Application
        App a = new App();

        // Connect to database
        a.connect();

        // Create report instance
        CapitalCityReport capitalReport = new CapitalCityReport(a.con);

        // --- 1. All Capital Cities ---
        System.out.println("\n=== All Capital Cities ===");
        ArrayList<City> allCapitals = capitalReport.getAllCapitalCities();
        capitalReport.printCapitalCities(allCapitals);

        // --- 2. Capital Cities in a Continent ---
        System.out.println("\n=== Capital Cities in Continent ===");
        ArrayList<City> capitalInContinent = capitalReport.getCapitalCitiesByContinent("Asia");
        capitalReport.printCapitalCities(capitalInContinent);

        // --- 3. Capital Cities in a Region ---
        System.out.println("\n=== Capital Cities in a Region ===");
        ArrayList<City> capitalInRegion = capitalReport.getCapitalCitiesByRegion("North America");
        capitalReport.printCapitalCities(capitalInRegion);

        // Disconnect from database
        a.disconnect();
    }

}


