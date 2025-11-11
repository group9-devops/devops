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
     * Connection object for interacting with the MySQL database.
     */
    private Connection con = null;

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

        PrintCountryValues PrintCountry = new PrintCountryValues();
        PrintCountry.getAllCountriesByPopulationDescending(a.con);



        // Disconnect from database
        a.disconnect();
    }
}
