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
    public Connection con = null;

    /**
     * Establishes a connection to the MySQL database.
     * Retries up to 10 times, waiting 30 seconds between attempts.
     */
    public void connect(String location, int delay) {
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
                Thread.sleep(delay);
                // Connect to database
                con = DriverManager.getConnection("jdbc:mysql://" + location
                                + "/world?allowPublicKeyRetrieval=true&useSSL=false",
                        "root", "example");
                System.out.println("Successfully connected");
                break;
            } catch (SQLException sqle) {
                System.out.println("Failed to connect to database attempt " +                                  Integer.toString(i));
                System.out.println(sqle.getMessage());
            } catch (InterruptedException ie) {
                System.out.println("Thread interrupted? Should not happen.");
            }

            if (con == null) {
                System.out.println("Could not establish database connection after retries.");
                System.exit(-1);
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

        if(args.length < 1){
            a.connect("localhost:3307", 0);
        }else{
            a.connect("db:3306", 3000);
        }

        // Connect to database

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

        // --- 1. Top N Capital Cities ---
        System.out.println("\n=== Top N All Capital Cities ===");
        ArrayList<City> nCapitals = capitalReport.getTopNCapitalCities(3);
        capitalReport.printCapitalCities(nCapitals);

        // --- 2. Top N Top N Capital Cities in a Continent ---
        System.out.println("\n=== Top N Capital Cities in Continent ===");
        ArrayList<City> nCapitalsContinent = capitalReport.getTopNCapitalCitiesByContinent("Asia",4);
        capitalReport.printCapitalCities(nCapitalsContinent);

        // --- 3. Capital Cities in a Region ---
        System.out.println("\n=== Top N Capital Cities in a Region ===");
        ArrayList<City> nCapitalsRegion = capitalReport.getTopNCapitalCitiesByRegion("North America",2);
        capitalReport.printCapitalCities(nCapitalsRegion);






        // Disconnect from database
        a.disconnect();
    }
}
