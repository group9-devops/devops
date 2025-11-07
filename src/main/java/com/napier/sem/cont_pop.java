package com.napier.sem;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class cont_pop {
    public ArrayList<City> getAllCitiesInContinent(String continent) {
        try {
            // Create an SQL statement using a prepared statement for safety
            String strSelect =
                    "SELECT city.Name AS CityName, " +
                            "country.Name AS Country, " +
                            "country.Continent AS Continent, " +
                            "city.Population AS Population " +
                            "FROM city " +
                            "JOIN country ON city.CountryCode = country.Code " +
                            "WHERE country.Continent = ? " +
                            "ORDER BY city.Population DESC";

            /*
            will fix for sunday night

            PreparedStatement pstmt = con.prepareStatement(strSelect);

            pstmt.setString(1, continent);

            // Execute SQL statement
            ResultSet rset = pstmt.executeQuery();

            // Extract city information
            ArrayList<City> cities = new ArrayList<>();
            while (rset.next()) {
                City city = new City();
                city.name = rset.getString("CityName");
                city.country = rset.getString("Country");
                city.district = rset.getString("Continent"); // optional, if City has this field
                city.population = rset.getInt("Population");
                cities.add(city);


            }

            return cities;

             */
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get the cities for continent: " + continent);
            return null;
        }
        //remove when working again
        return null;
    }
}
