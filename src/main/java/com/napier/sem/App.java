package com.napier.sem;

import java.sql.*;

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

    // TODO: Write queries for:
    //  Total world pop for percentage based calculations
    //  Percentages of Urban and Non-Urban populations
    //  Number of people in cities vs not in cities (are these two not just the same bloody thing?)

    // Will draft SQL statements in Datagrip and keep them in a .txt file for later implementation in program
    // Need to figure out menu system and data structure for program that is shared between all group members to allow for easier merging of individual feature branches later down the line
    // Use variables with data pulled using SQL queries as opposed to hard coded values to account for later modifications to the database
    // Explore use of ArrayList or other similar structure for retrieval of data from DB instead of using an SQL query each time

    public static void main(String[] args)
    {
        // Create new Application
        App a = new App();

        // Connect to database
        a.connect();

        // Disconnect from database
        a.disconnect();
    }


}