package com.napier.sem;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.PreparedStatement;

/**
 * Class responsible for retrieving and printing lists of Country objects
 * from the database based on various criteria, such as population order,
 * continent, or region.
 */
public class PrintCountryValues {

    /**
     * Retrieves all countries from the database, orders them by population
     * in descending order, and prints the results to the console.
     * @param con The active database connection object.
     */
    public void getAllCountriesByPopulationDescending(Connection con) {
        // List to hold the retrieved Country objects
        ArrayList<Country> countries = new ArrayList<>();

        try {
            Statement stmt = con.createStatement();
            String sqlStatement = "SELECT Code, Name, Continent, Region, Population, Capital FROM country ORDER BY Population DESC";

            ResultSet rset = stmt.executeQuery(sqlStatement);

            // Loop through the result set and map data to Country objects
            while (rset.next()) {
                Country country = new Country();


                country.code = rset.getString("Code");
                country.name = rset.getString("Name");
                country.continent = rset.getString("Continent");
                country.population = rset.getInt("Population");
                country.capital = rset.getString("Capital");
                country.region = rset.getString("Region");

                countries.add(country);
            }

            rset.close();
            stmt.close();

        } catch (Exception e) {

            System.out.println(e.getMessage());
            System.out.println("Failed to get countries");
        }



        System.out.printf("%-30s %-20s %-15s %-20s %-30s %15s\n",
                "Name", "Continent", "Region", "Capital", "Code", "Population");


        System.out.println("--------------------------------------------------------------------------------------------------------------");

        for (Country country : countries) {
            System.out.printf("%-30s %-20s %-15s %-20s %-30s %,15d\n",
                    country.name,
                    country.continent,
                    country.region,
                    country.capital,
                    country.code,
                    country.population
            );
        }

    }

    /**
     * Retrieves all countries filtered by a specific column (place) and value (name),
     * ordered by population in descending order.
     * This method uses a PreparedStatement.
     *
     * @param con The active database connection object.
     * @param place The database column name to filter on (e.g., "Continent", "Region").
     * @param name The specific value to filter by (e.g., "Asia", "Caribbean").
     */
    public void getAllCountriesBySpecificPlace(Connection con, String place, String name) {
        ArrayList<Country> countries = new ArrayList<>();

        try {

            String sqlStatement = "SELECT Code, Name, Continent, Region, Population, Capital FROM country WHERE " + place + " = ? ORDER BY Population DESC";


            PreparedStatement pstmt = con.prepareStatement(sqlStatement);


            pstmt.setString(1, name);


            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                Country country = new Country();
                country.code = rset.getString("Code");
                country.name = rset.getString("Name");
                country.continent = rset.getString("Continent");
                country.population = rset.getInt("Population");
                country.capital = rset.getString("Capital");
                country.region = rset.getString("Region");
                countries.add(country);
            }

            // Close resources
            rset.close();
            pstmt.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get countries");
        }




        System.out.printf("%-30s %-20s %-15s %-20s %-30s %15s\n",
                "Name", "Continent", "Region", "Capital", "Code", "Population");


        System.out.println("--------------------------------------------------------------------------------------------------------------");


        for (Country country : countries) {
            System.out.printf("%-30s %-20s %-15s %-20s %-30s %,15d\n",
                    country.name,
                    country.continent,
                    country.region,
                    country.capital,
                    country.code,
                    country.population
            );
        }

    }

    /**
     * @param continent The name of the continent (e.g., "Asia").
     */
    public void getAllCountriesBySpecificContinent(Connection con, String continent) {

        getAllCountriesBySpecificPlace(con, "Continent", continent);
    }

    /**

     * @param region The name of the region (e.g., "Caribbean").
     */
    public void getAllCountriesBySpecificRegion(Connection con, String region) {

        getAllCountriesBySpecificPlace(con, "Region", region);
    }

}