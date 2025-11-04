package com.napier.sem;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class CapitalCityReport {

    ArrayList<City> cities = new ArrayList<>();

    public void getAllCities(Connection con){
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

            while (rset.next())
            {
                City capitalCity = new City();
                capitalCity.Name = rset.getString("CapitalCity");
                capitalCity.Country = rset.getString("Country");
                capitalCity.Population = rset.getInt("Population");


                cities.add(capitalCity);
            }



        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get the capital cities");

        }

        // format of the table
        System.out.printf("%-30s %-20s %-15s %-20s %-30s %15s\n",
                "Capital City", "Country", "Population");
        System.out.println("--------------------------------------------------------------------------------------------------------------");

        // Print rows
        for (City capitalCity : cities) {
            System.out.printf("%-30s %-20s %-15s %-20s %-30s %,15d\n",
                    capitalCity.Name,
                    capitalCity.Country,
                    capitalCity.Population
            );
        }

    }


}
